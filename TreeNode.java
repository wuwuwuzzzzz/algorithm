/**
 *
 * @author wxz
 * @date 16:41 2023/11/8
 */
public class TreeNode
{
    /**
     * 值
     */
    public int val;

    /**
     * 左节点
     */
    public TreeNode left;

    /**
     * 右节点
     */
    public TreeNode right;

    public TreeNode(int val)
    {
        this.val = val;
    }

    public TreeNode(TreeNode left, int val, TreeNode right)
    {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.val);
    }
}
