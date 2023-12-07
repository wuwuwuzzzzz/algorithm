/**
 * @author wxz
 * @date 17:06 2023/12/7
 */
public class E01LeetCode322
{
    /**
     * @author wxz
     * @date 17:10 2023/12/7
     */
    public static void main(String[] args)
    {
        System.out.println(coinChange(new int[]{25, 10, 5, 1}, 41));
    }

    /**
     * 贪心算法
     *
     * @param coins  硬币数组
     * @param amount 期待数值
     * @return int
     * @author wxz
     * @date 17:09 2023/12/7
     */
    public static int coinChange(int[] coins, int amount)
    {
        int remainder = amount;
        int count = 0;
        for (int coin : coins)
        {
            while (remainder >= coin)
            {
                remainder -= coin;
                count++;
            }
        }
        if (remainder > 0)
        {
            return -1;
        }
        else
        {
            return count;
        }
    }
}
