/**
 * @author wxz
 * @date 15:42 2023/11/30
 */
public class RedBlackTree
{
    /**
     * 根节点
     */
    private Node root;

    /**
     * 判断红色
     *
     * @return boolean
     * @author wxz
     * @date 16:10 2023/11/30
     */
    boolean isRed(Node node)
    {
        return node != null && node.color == Color.RED;
    }

    /**
     * 判断黑色
     *
     * @return boolean
     * @author wxz
     * @date 16:11 2023/11/30
     */
    boolean isBlack(Node node)
    {
        return node == null || node.color == Color.BLACK;
    }

    /**
     * 右旋 1. parent 处理 2. 旋转后新根的父子关系
     *
     * @author wxz
     * @date 16:13 2023/11/30
     */
    private void rightRotate(Node pink)
    {
        Node parent = pink.parent;
        Node yellow = pink.left;
        Node green = yellow.right;
        if (green != null)
        {
            green.parent = pink;
        }
        yellow.right = pink;
        yellow.parent = parent;
        pink.left = green;
        pink.parent = yellow;
        if (parent == null)
        {
            root = yellow;
        }
        else if (parent.left == pink)
        {
            parent.left = yellow;
        }
        else
        {
            parent.right = yellow;
        }
    }

    /**
     * 左旋 1. parent 处理 2. 旋转后新根的父子关系
     *
     * @author wxz
     * @date 16:23 2023/11/30
     */
    private void leftRotate(Node pink)
    {
        Node parent = pink.parent;
        Node yellow = pink.right;
        Node green = yellow.left;
        if (green != null)
        {
            green.parent = pink;
        }
        yellow.left = pink;
        yellow.parent = parent;
        pink.right = green;
        pink.parent = yellow;
        if (parent == null)
        {
            root = yellow;
        }
        else if (parent.left == pink)
        {
            parent.left = yellow;
        }
        else
        {
            parent.right = yellow;
        }
    }

    /**
     * 新增或更新 正常增 遇到红红不平衡进行调整
     *
     * @author wxz
     * @date 16:24 2023/11/30
     */
    public void put(int key, Object value)
    {
        Node p = root;
        Node parent = null;
        while (p != null)
        {
            parent = p;
            if (key < p.key)
            {
                p = p.left;
            }
            else if (p.key < key)
            {
                p = p.right;
            }
            else
            {
                p.value = value;
                return;
            }
        }
        Node inserted = new Node(key, value);
        if (parent == null)
        {
            root = inserted;
        }
        else if (key < parent.key)
        {
            parent.left = inserted;
            inserted.parent = parent;
        }
        else
        {
            parent.right = inserted;
            inserted.parent = parent;
        }
        fixRedRed(inserted);
    }

    /**
     * 调整红红情况
     * <p>
     * 1. 插入的节点为根节点，将根节点变黑
     * 2. 插入节点的父亲若为黑色，树的红黑性质不变，无需调整
     * 3. 插入节点的父亲为红色，触发红红相邻
     * 4. 叔叔为红色
     * 5. 叔叔为黑色
     *
     * @author wxz
     * @date 16:31 2023/11/30
     */
    void fixRedRed(Node node)
    {
        // 插入的节点为根节点，将根节点变黑
        if (node == root)
        {
            node.color = Color.BLACK;
        }
        // 插入节点的父亲若为黑色，树的红黑性质不变，无需调整
        if (isBlack(node.parent))
        {
            return;
        }
        Node parent = node.parent;
        Node uncle = node.uncle();
        Node grandparent = node.parent.parent;
        // 父亲为红色，叔叔为红色
        if (isRed(uncle))
        {
            // 父亲变黑色
            parent.color = Color.BLACK;
            assert uncle != null;
            // 叔叔变黑色
            uncle.color = Color.BLACK;
            // 祖父变红色
            grandparent.color = Color.RED;
            // 递归调整
            fixRedRed(grandparent);
            return;
        }
        // 父亲为红色，叔叔为黑色。父亲左孩子，插入节点也是左孩子，右旋
        if (parent.isLeftChild() && node.isLeftChild())
        {
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
            rightRotate(grandparent);
        }
        // 父亲左孩子，插入节点是右孩子，先左旋后右旋
        if (parent.isLeftChild() && !node.isLeftChild())
        {
            leftRotate(parent);
            node.color = Color.BLACK;
            grandparent.color = Color.RED;
            rightRotate(grandparent);
        }
        // 父亲右孩子，插入节点也是右孩子，左旋
        if (!parent.isLeftChild() && !node.isLeftChild())
        {
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
            leftRotate(grandparent);
        }
        // 父亲右孩子，插入节点是左孩子，先右旋后左旋
        if (!parent.isLeftChild() && node.isLeftChild())
        {
            rightRotate(parent);
            node.color = Color.BLACK;
            grandparent.color = Color.RED;
            leftRotate(grandparent);
        }
    }

    /**
     * 删除 正常删会用到李代桃僵技巧，遇到黑黑不平衡进行调整
     *
     * @author wxz
     * @date 09:33 2023/12/1
     */
    public void remove(int key)
    {
        Node deleted = find(key);
        if (deleted == null)
        {
            return;
        }
        doRemove(deleted);
    }

    /**
     * 处理双黑
     *
     * @author wxz
     * @date 10:11 2023/12/1
     */
    private void fixDoubleBlack(Node node)
    {
        if (node == root)
        {
            return;
        }
        Node parent = node.parent;
        Node sibling = node.sibling();
        // 兄弟节点是红色
        if (isRed(sibling))
        {
            if (node.isLeftChild())
            {
                leftRotate(parent);
            }
            else
            {
                rightRotate(parent);
            }
            parent.color = Color.RED;
            assert sibling != null;
            sibling.color = Color.BLACK;
            fixDoubleBlack(node);
            return;
        }
        // 兄弟节点是黑色
        if (sibling != null)
        {
            // 两个侄子是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right))
            {
                sibling.color = Color.RED;
                if (isRed(parent))
                {
                    parent.color = Color.BLACK;
                }
                else
                {
                    fixDoubleBlack(parent);
                }
            }
            // 侄子有红色的
            else
            {
                if (sibling.isLeftChild() && isRed(sibling.left))
                {
                    rightRotate(parent);
                    sibling.left.color = Color.BLACK;
                    sibling.color = parent.color;
                }
                if (sibling.isLeftChild() && isRed(sibling.right))
                {
                    sibling.right.color = parent.color;
                    leftRotate(sibling);
                    rightRotate(parent);
                }
                if (!sibling.isLeftChild() && isRed(sibling.left))
                {
                    sibling.left.color = parent.color;
                    rightRotate(sibling);
                    leftRotate(parent);
                }
                if (!sibling.isLeftChild() && isRed(sibling.right))
                {
                    leftRotate(parent);
                    sibling.right.color = Color.BLACK;
                    sibling.color = parent.color;
                }
                parent.color = Color.BLACK;
            }
        }
        else
        {
            fixDoubleBlack(parent);
        }
    }

    /**
     * 删除节点
     *
     * @author wxz
     * @date 09:41 2023/12/1
     */
    private void doRemove(Node deleted)
    {
        Node replaced = findReplaced(deleted);
        Node parent = deleted.parent;
        // 没有孩子
        if (replaced == null)
        {
            if (deleted == root)
            {
                root = null;
            }
            else
            {
                if (isBlack(deleted))
                {
                    fixDoubleBlack(deleted);
                }
                if (deleted.isLeftChild())
                {
                    parent.left = null;
                }
                else
                {
                    parent.right = null;
                }
                deleted.parent = null;
            }
            return;
        }
        // 有一个孩子
        if (deleted.left == null || deleted.right == null)
        {
            if (deleted == root)
            {
                root.key = replaced.key;
                root.value = replaced.value;
                root.left = root.right = null;
            }
            else
            {
                if (deleted.isLeftChild())
                {
                    parent.left = replaced;
                }
                else
                {
                    parent.right = replaced;
                }
                replaced.parent = parent;
                deleted.left = deleted.right = deleted.parent = null;
            }
            if (isBlack(deleted) && isBlack(replaced))
            {
                fixDoubleBlack(replaced);
            }
            else
            {
                replaced.color = Color.BLACK;
            }
        }
        // 有两个孩子
        int t = deleted.key;
        deleted.key = replaced.key;
        replaced.key = t;

        Object v = deleted.value;
        deleted.value = replaced.value;
        replaced.value = v;

        doRemove(replaced);
    }

    /**
     * 查找删除节点
     *
     * @return RedBlackTree.Node
     * @author wxz
     * @date 09:35 2023/12/1
     */
    private Node find(int key)
    {
        Node p = root;
        while (p != null)
        {
            if (key < p.key)
            {
                p = p.left;
            }
            else if (p.key < key)
            {
                p = p.right;
            }
            else
            {
                return p;
            }
        }
        return null;
    }

    /**
     * 查找剩余节点
     *
     * @param deleted 删除节点
     * @return RedBlackTree.Node
     * @author wxz
     * @date 09:40 2023/12/1
     */
    private Node findReplaced(Node deleted)
    {
        if (deleted.left == null && deleted.right == null)
        {
            return null;
        }
        if (deleted.left == null)
        {
            return deleted.right;
        }
        if (deleted.right == null)
        {
            return deleted.left;
        }
        Node s = deleted.right;
        while (s.left != null)
        {
            s = s.left;
        }
        return s;
    }

    /**
     * 节点颜色
     */
    enum Color
    {
        /**
         * 红色节点
         */
        RED,

        /**
         * 黑色节点
         */
        BLACK;
    }

    private static class Node
    {
        /**
         * 节点key
         */
        int key;

        /**
         * 节点值
         */
        Object value;

        /**
         * 左孩子
         */
        Node left;

        /**
         * 右孩子
         */
        Node right;

        /**
         * 父节点
         */
        Node parent;

        /**
         * 节点颜色
         */
        Color color = Color.RED;

        /**
         * 构造方法
         *
         * @author wxz
         * @date 16:27 2023/11/30
         */
        public Node(int key, Object value)
        {
            this.key = key;
            this.value = value;
        }

        /**
         * 是否是左孩子
         *
         * @return boolean
         * @author wxz
         * @date 16:06 2023/11/30
         */
        boolean isLeftChild()
        {
            return parent != null && parent.left == this;
        }

        /**
         * 叔叔节点
         *
         * @return RedBlackTree.Node
         * @author wxz
         * @date 16:06 2023/11/30
         */
        Node uncle()
        {
            if (parent == null || parent.parent == null)
            {
                return null;
            }
            if (parent.isLeftChild())
            {
                return parent.parent.right;
            }
            else
            {
                return parent.parent.left;
            }
        }

        /**
         * 兄弟节点
         *
         * @return RedBlackTree.Node
         * @author wxz
         * @date 16:06 2023/11/30
         */
        Node sibling()
        {
            if (parent == null)
            {
                return null;
            }
            if (isLeftChild())
            {
                return parent.right;
            }
            else
            {
                return parent.left;
            }
        }
    }
}
