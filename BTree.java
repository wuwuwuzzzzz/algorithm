import java.util.Arrays;

/**
 * @author wxz
 * @date 14:20 2023/12/1
 */
public class BTree
{
    /**
     * 最小 key 数目
     */
    final int MIN_KEY_NUMBER;

    /**
     * 最大 key 数目
     */
    final int MAX_KEY_NUMBER;

    /**
     * 根节点
     */
    Node root;
    /**
     * 树中节点最小度数
     */
    int t;

    /**
     * 无参构造
     *
     * @author wxz
     * @date 14:44 2023/12/1
     */
    public BTree()
    {
        this(2);
    }

    /**
     * 有参构造
     *
     * @author wxz
     * @date 14:44 2023/12/1
     */
    public BTree(int t)
    {
        this.t = t;
        root = new Node(t);
        MAX_KEY_NUMBER = 2 * t - 1;
        MIN_KEY_NUMBER = t - 1;
    }

    /**
     * 是否找到待删除的索引
     *
     * @return boolean
     * @author wxz
     * @date 12:05 2023/12/3
     */
    private static boolean found(Node node, int key, int i)
    {
        return i < node.keyNumber && node.keys[i] == key;
    }

    /**
     * 是否存在
     *
     * @return boolean
     * @author wxz
     * @date 14:48 2023/12/1
     */
    public boolean contains(int key)
    {
        return root.get(key) != null;
    }

    /**
     * 新增
     *
     * @author wxz
     * @date 15:04 2023/12/1
     */
    public void put(int key)
    {
        doPut(root, key, null, 0);
    }

    /**
     * 新增
     *
     * @author wxz
     * @date 15:04 2023/12/1
     */
    private void doPut(Node node, int key, Node parent, int index)
    {
        int i = 0;
        while (i < node.keyNumber)
        {
            if (node.keys[i] == key)
            {
                return;
            }
            if (node.keys[i] > key)
            {
                break;
            }
            i++;
        }
        if (node.leaf)
        {
            node.insertKey(key, i);
        }
        else
        {
            doPut(node.children[i], key, node, i);
        }
        if (node.keyNumber == MAX_KEY_NUMBER)
        {
            split(node, parent, index);
        }
    }

    /**
     * 分裂节点
     *
     * @param left   待分裂节点
     * @param parent 父节点
     * @param index  节点索引
     * @author wxz
     * @date 10:42 2023/12/3
     */
    private void split(Node left, Node parent, int index)
    {
        // 分裂的是根节点
        if (parent == null)
        {
            Node newRoot = new Node(t);
            newRoot.leaf = false;
            newRoot.insertChild(left, 0);
            this.root = newRoot;
            parent = newRoot;
        }
        // 创建 right 节点，把 left 中 t 之后的 key 和 child 移动过去
        Node right = new Node(t);
        right.leaf = left.leaf;
        System.arraycopy(left.keys, t, right.keys, 0, t - 1);
        // 分裂节点是非叶子节点的情况
        if (!left.leaf)
        {
            System.arraycopy(left.children, t, right.children, 0, t);
        }
        right.keyNumber = left.keyNumber = t - 1;
        // 中间的 key（t - 1 处）插入到父节点
        int mid = left.keys[t - 1];
        parent.insertKey(mid, index);
        // right 节点作为父节点的孩子
        parent.insertChild(right, index + 1);
    }

    /**
     * 删除
     *
     * @author wxz
     * @date 11:57 2023/12/3
     */
    public void remove(int key)
    {
        doRemove(null, root, 0, key);
    }

    /**
     * 删除
     *
     * @author wxz
     * @date 11:57 2023/12/3
     */
    private void doRemove(Node parent, Node node, int index, int key)
    {
        int i = 0;
        while (i < node.keyNumber)
        {
            if (node.keys[i] >= key)
            {
                break;
            }
            i++;
        }
        // i 找到：代表待删除的 key 的索引
        // i 没找到：代表到第 i 个孩子继续查找
        if (node.leaf)
        {
            if (!found(node, key, i))
            {
                return;
            }
            else
            {
                node.removeKey(i);
            }
        }
        else
        {
            if (!found(node, key, i))
            {
                doRemove(node, node.children[i], i, key);
            }
            else
            {
                Node s = node.children[i + 1];
                while (!s.leaf)
                {
                    s = s.children[0];
                }
                int sKey = s.keys[0];
                // 替换待删除 key
                node.keys[i] = sKey;
                // 删除后继 key
                doRemove(node, node.children[i + 1], i + 1, sKey);
            }
        }
        // 调整平衡
        if (node.keyNumber < MIN_KEY_NUMBER)
        {
            balance(parent, node, index);
        }
    }

    /**
     * 调整平衡
     *
     * @author wxz
     * @date 13:22 2023/12/3
     */
    private void balance(Node parent, Node node, int i)
    {
        if (node == root)
        {
            if (root.keyNumber == 0 && root.children[0] != null)
            {
                root = root.children[0];
            }
            return;
        }
        Node left = parent.childLeftSibling(i);
        Node right = parent.childRightSibling(i);
        // 左边富裕，右旋
        if (left != null && left.keyNumber > MIN_KEY_NUMBER)
        {
            // 父节点中前驱 key 旋转下来
            node.insertKey(parent.keys[i - 1], 0);
            if (!left.leaf)
            {
                // left 中最大的孩子换爹
                node.insertChild(left.removeRightmostChild(), 0);
            }
            // left 中最大的 key 旋转上去
            parent.keys[i - 1] = left.removeRightmostKey();
            return;
        }
        // 右边富裕，左旋
        if (right != null && right.keyNumber > MIN_KEY_NUMBER)
        {
            // 父节点中后继 key 旋转下来
            node.insertKey(parent.keys[i], node.keyNumber);
            if (!right.leaf)
            {
                // right 中最小的孩子换爹
                node.insertChild(right.removeLeftmostChild(), node.keyNumber + 1);
            }
            // right 中最小的 key 旋转上去
            parent.keys[i] = right.removeLeftmostKey();
            return;
        }
        // 两边都不够，向左合并
        if (left != null)
        {
            parent.removeChild(i);
            left.insertKey(parent.removeKey(i - 1), left.keyNumber);
            node.moveTarget(left);
        }
        else
        {
            parent.removeChild(i + 1);
            node.insertKey(parent.removeKey(i), node.keyNumber);
            right.moveTarget(node);
        }
    }

    static class Node
    {
        /**
         * 关键字
         */
        int[] keys;

        /**
         * 孩子
         */
        Node[] children;

        /**
         * 有效关键字数目
         */
        int keyNumber;

        /**
         * 是否是叶子节点
         */
        boolean leaf = true;

        /**
         * 最小度数（最小孩子数）
         */
        int t;

        /**
         * @param t t >= 2
         * @author wxz
         * @date 14:25 2023/12/1
         */
        public Node(int t)
        {
            this.t = t;
            this.children = new Node[2 * t];
            this.keys = new int[2 * t - 1];
        }

        /**
         * toString
         *
         * @return java.lang.String
         * @author wxz
         * @date 14:27 2023/12/1
         */
        @Override
        public String toString()
        {
            return Arrays.toString(Arrays.copyOfRange(keys, 0, keyNumber));
        }

        /**
         * 多路查找
         *
         * @return BTree.Node
         * @author wxz
         * @date 14:35 2023/12/1
         */
        public Node get(int key)
        {
            int i = 0;
            while (i < keyNumber)
            {
                if (keys[i] == key)
                {
                    return this;
                }
                if (keys[i] > key)
                {
                    break;
                }
                i++;
            }
            // 叶子节点
            if (leaf)
            {
                return null;
            }
            // 非叶子节点
            return children[i].get(key);
        }

        /**
         * 向 keys 指定索引处插入 key
         *
         * @author wxz
         * @date 14:38 2023/12/1
         */
        public void insertKey(int key, int index)
        {
            System.arraycopy(keys, index, keys, index + 1, keyNumber - index);
            keys[index] = key;
            keyNumber++;
        }

        /**
         * 向 children 指定索引处插入 child
         *
         * @author wxz
         * @date 14:40 2023/12/1
         */
        public void insertChild(Node child, int index)
        {
            System.arraycopy(children, index, children, index + 1, keyNumber - index);
            children[index] = child;
        }

        /**
         * 移除指定 index 处的 key
         *
         * @return int
         * @author wxz
         * @date 11:24 2023/12/3
         */
        public int removeKey(int index)
        {
            int t = keys[index];
            System.arraycopy(keys, index + 1, keys, index, --keyNumber - index);
            return t;
        }

        /**
         * 移除最左边的 key
         *
         * @return int
         * @author wxz
         * @date 11:26 2023/12/3
         */
        public int removeLeftmostKey()
        {
            return removeKey(0);
        }

        /**
         * 移除最右边的 key
         *
         * @return int
         * @author wxz
         * @date 11:26 2023/12/3
         */
        public int removeRightmostKey()
        {
            return removeKey(keyNumber - 1);
        }

        /**
         * 移除指定 index 处的 child
         *
         * @return BTree.Node
         * @author wxz
         * @date 11:28 2023/12/3
         */
        public Node removeChild(int index)
        {
            Node child = children[index];
            System.arraycopy(children, index + 1, children, index, --keyNumber - index);
            return child;
        }

        /**
         * 移除最左边的 child
         *
         * @return BTree.Node
         * @author wxz
         * @date 11:35 2023/12/3
         */
        public Node removeLeftmostChild()
        {
            return removeChild(0);
        }

        /**
         * 移除最右边的 child
         *
         * @return BTree.Node
         * @author wxz
         * @date 11:35 2023/12/3
         */
        public Node removeRightmostChild()
        {
            return removeChild(keyNumber);
        }

        /**
         * index 孩子处左边的兄弟
         *
         * @return BTree.Node
         * @author wxz
         * @date 11:30 2023/12/3
         */
        Node childLeftSibling(int index)
        {
            return index > 0 ? children[index - 1] : null;
        }

        /**
         * index 孩子处右边的兄弟
         *
         * @return BTree.Node
         * @author wxz
         * @date 11:30 2023/12/3
         */
        Node childRightSibling(int index)
        {
            return index == keyNumber ? null : children[index + 1];
        }

        /**
         * 复制当前节点的所有 key 和 child 到 target
         *
         * @author wxz
         * @date 11:32 2023/12/3
         */
        public void moveTarget(Node target)
        {
            int start = target.keyNumber;
            if (!leaf)
            {
                for (int i = 0; i < keyNumber; i++)
                {
                    target.children[start + i] = children[i];
                }
                for (int i = 0; i < keyNumber; i++)
                {
                    target.keys[target.keyNumber++] = keys[i];
                }
            }
        }
    }
}
