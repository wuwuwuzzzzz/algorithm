/**
 * 零钱兑换 - 动态规划
 *
 * @author wxz
 * @date 11:28 2023/12/11
 */
public class ChangeMakingProblemLeetCode322
{
    /**
     * @author wxz
     * @date 11:40 2023/12/11
     */
    public static void main(String[] args)
    {
        System.out.println(coinChange(new int[]{1, 2, 5}, 5));
    }

    /**
     * @return int
     * @author wxz
     * @date 11:33 2023/12/11
     */
    public static int coinChange(int[] coins, int amount)
    {
        int[][] dp = new int[coins.length][amount + 1];
        for (int j = 1; j < amount + 1; j++)
        {
            if (j >= coins[0])
            {
                dp[0][j] = dp[0][j - coins[0]] + 1;
            }
            else
            {
                dp[0][j] = amount + 1;
            }
        }
        for (int i = 1; i < coins.length; i++)
        {
            for (int j = 1; j < amount + 1; j++)
            {
                if (j >= coins[i])
                {
                    dp[i][j] = Integer.min(dp[i - 1][j], dp[i][j - coins[i]] + 1);
                }
                else
                {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[coins.length - 1][amount] < amount + 1 ? dp[coins.length - 1][amount] : -1;
    }
}
