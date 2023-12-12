import java.util.ArrayList;
import java.util.List;

/**
 * @author wxz
 * @date 10:36 2023/12/12
 */
public class LeetCode22
{
    /**
     *
     * @author wxz
     * @date 10:50 2023/12/12
     */
    public static void main(String[] args)
    {
        System.out.println(generateParenthesis(3));
    }

    /**
     * @return java.util.List<java.lang.String>
     * @author wxz
     * @date 10:37 2023/12/12
     */

    @SuppressWarnings("unchecked")
    public static List<String> generateParenthesis(int n)
    {
        ArrayList<String>[] dp = new ArrayList[n + 1];
        dp[0] = new ArrayList<>(List.of(""));
        dp[1] = new ArrayList<>(List.of("()"));
        for (int j = 2; j < n + 1; j++)
        {
            dp[j] = new ArrayList<>();
            for (int i = 0; i < j; i++)
            {
                for (String k1 : dp[i])
                {
                    for (String k2 : dp[j - 1 - i])
                    {
                        dp[j].add("(" + k1 + ")" + k2);
                    }
                }
            }
        }
        return dp[n];
    }
}
