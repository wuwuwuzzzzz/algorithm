import java.util.Arrays;

/**
 * @author wxz
 * @date 21:52 2023/11/27
 */
public class E01LeetCode105
{
    /**
     * @author wxz
     * @date 21:52 2023/11/27
     */
    public static void main(String[] args)
    {
        int[] preorder = {1, 2, 4, 3, 6, 7};
        int[] inorder = {4, 2, 1, 6, 3, 7};

        TreeNode root = buildTree(preorder, inorder);
        System.out.println(root);
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder)
    {
        if (preorder.length == 0 || inorder.length == 0)
        {
            return null;
        }
        // 创建根节点
        int rootValue = preorder[0];
        TreeNode root = new TreeNode(rootValue);
        // 区分左右子树
        for (int i = 0; i < inorder.length; i++)
        {
            if (inorder[i] == rootValue)
            {
                // 0 ~ i 左子树
                // i + 1 ~ inorder.length - 1 右子树
                int[] inLeft = Arrays.copyOfRange(inorder, 0, i);
                int[] inRight = Arrays.copyOfRange(inorder, i + 1, inorder.length);

                int[] preLeft = Arrays.copyOfRange(preorder, 1, i + 1);
                int[] preRight = Arrays.copyOfRange(preorder, i + 1, preorder.length);

                root.left = buildTree(preLeft, inLeft);
                root.right = buildTree(preRight, inRight);

                break;
            }
        }
        return root;
    }
}
