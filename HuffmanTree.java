import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * HuffmanTree 树以及编码
 *
 * @author wxz
 * @date 13:44 2023/12/9
 */
public class HuffmanTree
{
    /**
     * 字符串
     */
    String str;

    /**
     * 统计频次 map 集合
     */
    Map<Character, Node> map = new HashMap<>();

    /**
     * 根节点
     */
    Node root;

    /**
     * 统计字符串字符出现频次
     *
     * @param str 字符串
     * @return null
     * @author wxz
     * @date 14:13 2023/12/9
     */
    public HuffmanTree(String str)
    {
        this.str = str;
        // 统计频次
        char[] chars = str.toCharArray();
        for (char c : chars)
        {
            Node node = map.computeIfAbsent(c, Node::new);
            node.freq++;
        }
        // 构造树
        PriorityQueue<Node> queue = new PriorityQueue<>(
                Comparator.comparingInt(Node::freq)
        );
        queue.addAll(map.values());
        while (queue.size() >= 2)
        {
            Node x = queue.poll();
            Node y = queue.poll();
            assert y != null;
            int freq = x.freq + y.freq;
            queue.offer(new Node(freq, x, y));
        }
        root = queue.poll();
        // 计算编码
        assert root != null;
        int sum = dfs(root, new StringBuilder());
        for (Node node : map.values())
        {
            System.out.println(node + " " + node.code);
        }
        System.out.println("总共会占用 bits:" + sum);
    }

    /**
     * @author wxz
     * @date 14:14 2023/12/9
     */
    public static void main(String[] args)
    {
        HuffmanTree tree = new HuffmanTree("abbccccccc");
        String encode = tree.encode();
        System.out.println(encode);
        String decode = tree.decode(encode);
        System.out.println(decode);

    }

    /**
     * 递归遍历计算编码
     *
     * @return int
     * @author wxz
     * @date 14:37 2023/12/9
     */
    private int dfs(Node node, StringBuilder code)
    {
        int sum = 0;
        if (node.isLeaf())
        {
            // 找到编码
            node.code = code.toString();
            sum = node.freq * code.length();
        }
        else
        {
            sum += dfs(node.left, code.append("0"));
            sum += dfs(node.right, code.append("1"));
        }
        if (!code.isEmpty())
        {
            code.deleteCharAt(code.length() - 1);
        }
        return sum;
    }

    /**
     * 编码
     *
     * @return java.lang.String
     * @author wxz
     * @date 14:40 2023/12/9
     */
    public String encode()
    {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars)
        {
            sb.append(map.get(c).code);
        }
        return sb.toString();
    }

    /**
     * 解码
     *
     * @return java.lang.String
     * @author wxz
     * @date 14:40 2023/12/9
     */
    public String decode(String str)
    {
        char[] chars = str.toCharArray();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        Node node = root;
        while (i < chars.length)
        {
            if (!node.isLeaf())
            {
                if (chars[i] == '0')
                {
                    node = node.left;
                }
                else
                {
                    node = node.right;
                }
                i++;
            }
            if (node.isLeaf())
            {
                sb.append(node.ch);
                node = root;
            }
        }
        return sb.toString();
    }

    /**
     * 节点类
     */
    static class Node
    {
        /**
         * 字符
         */
        Character ch;

        /**
         * 频次
         */
        int freq;

        /**
         * 左孩子
         */
        Node left;

        /**
         * 右孩子
         */
        Node right;

        /**
         * 编码
         */
        String code;

        /**
         * 构造方法
         *
         * @author wxz
         * @date 13:52 2023/12/9
         */
        public Node(Character ch)
        {
            this.ch = ch;
        }

        /**
         * 构造方法
         *
         * @author wxz
         * @date 13:52 2023/12/9
         */
        public Node(int freq, Node left, Node right)
        {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        /**
         * 获取频次
         *
         * @return int
         * @author wxz
         * @date 13:51 2023/12/9
         */
        int freq()
        {
            return freq;
        }

        /**
         * 是否是叶子节点
         *
         * @return boolean
         * @author wxz
         * @date 13:51 2023/12/9
         */
        boolean isLeaf()
        {
            return left == null;
        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "ch=" + ch +
                    ", freq=" + freq +
                    '}';
        }
    }
}
