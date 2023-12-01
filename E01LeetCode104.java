import static java.lang.Math.max;

/**
 * @author wxz
 * @date 16:33 2023/11/27
 */
public class E01LeetCode104
{
    /**
     * @author wxz
     * @date 16:38 2023/11/27
     */
    public static void main(String[] args)
    {
        TreeNode root = new TreeNode(new TreeNode(new TreeNode(15), 9, new TreeNode(7)), 3, new TreeNode(20));
        System.out.println(maxDepth(root));
    }

    /**
     * 求出二叉树最大深度
     *
     * @param root root
     * @return int
     * @author wxz
     * @date 16:37 2023/11/27
     */
    public static int maxDepth(TreeNode root)
    {
        if (root == null)
        {
            return 0;
        }

        int d1 = maxDepth(root.left);
        int d2 = maxDepth(root.right);

        return max(d1, d2) + 1;
    }
}
