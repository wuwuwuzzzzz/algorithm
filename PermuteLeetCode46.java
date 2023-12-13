import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 全排列 回溯
 *
 * @author wxz
 * @date 10:34 2023/12/13
 */
public class PermuteLeetCode46
{
    /**
     * @return java.util.List<java.util.List < java.lang.Integer>>
     * @author wxz
     * @date 10:40 2023/12/13
     */
    static List<List<Integer>> permute(int[] nums)
    {
        List<List<Integer>> result = new ArrayList<>();
        dfs(nums, new boolean[nums.length], new LinkedList<>(), result);
        return result;
    }

    /**
     * @author wxz
     * @date 10:40 2023/12/13
     */
    private static void dfs(int[] nums, boolean[] visited, LinkedList<Integer> stack, List<List<Integer>> result)
    {
        if (stack.size() == nums.length)
        {
            result.add(new ArrayList<>(stack));
            return;
        }
        // 遍历 nums 数组，发现没有被使用的数字，则将其标记为使用，并加入 stack
        for (int i = 0; i < nums.length; i++)
        {
            if (!visited[i])
            {
                stack.push(nums[i]);
                visited[i] = true;
                dfs(nums, visited, stack, result);
                visited[i] = false;
                stack.pop();
            }
        }
    }

    /**
     * @author wxz
     * @date 10:40 2023/12/13
     */
    public static void main(String[] args)
    {
        List<List<Integer>> permute = permute(new int[]{1, 2, 3});
        for (List<Integer> list : permute)
        {
            System.out.println(list);
        }
    }
}
