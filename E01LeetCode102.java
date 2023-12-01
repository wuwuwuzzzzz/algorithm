import java.util.Deque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 二叉树层序遍历
 *
 * @author wxz
 * @date 16:43 2023/11/8
 */
public class E01LeetCode102
{
    /**
     * @author wxz
     * @date 16:44 2023/11/8
     */
    public static void main(String[] args)
    {
        TreeNode root = new TreeNode(
                new TreeNode(
                        new TreeNode(4),
                        2,
                        new TreeNode(5)
                ),
                1,
                new TreeNode(
                        new TreeNode(6),
                        3,
                        new TreeNode(7)
                )
        );

        LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.offer(root);
        while (!queue.isEmpty())
        {
            TreeNode n = queue.poll();
            System.out.println(n);
            if (n.left != null)
            {
                queue.offer(n.left);
            }
            if (n.right != null)
            {
                queue.offer(n.right);
            }
        }
    }
}
