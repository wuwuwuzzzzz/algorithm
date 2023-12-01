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

    }

    /**
     * 新增
     *
     * @author wxz
     * @date 15:04 2023/12/1
     */
    private void doPut(Node node, int key)
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
            doPut(node.children[i], key);
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
    }
}
