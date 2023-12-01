import java.util.Arrays;
import java.util.Queue;

/**
 * @author wxz
 * @date 11:52 2023/11/8
 */
public class E09Merge2SortedArray
{
    /**
     * @param args args
     * @author wxz
     * @date 11:53 2023/11/8
     */
    public static void main(String[] args)
    {
        int[] a1 = {1, 5, 6, 2, 4, 10, 11};
        int[] a2 = new int[a1.length];
        merge(a1, 0, 2, 3, 6, a2, 0);
        merge2(a1, 0, 2, 3, 6, a2);
        System.out.println(Arrays.toString(a2));
        System.out.println(Integer.MAX_VALUE);
        double x = 30;
        double log2x = Math.log(x) / Math.log(2);
        System.out.println(log2x);
    }

    /**
     * @param a1   原始数组
     * @param i    第一个有序区间的起点
     * @param iEnd 第一个有序区间的终点
     * @param j    第二个有序区间的起点
     * @param jEnd 第二个有序区间的终点
     * @param a2   结果数组
     * @author wxz
     * @date 14:00 2023/11/8
     */
    public static void merge2(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2)
    {
        int k = 0;
        while (i <= iEnd && j <= jEnd)
        {
            if (a1[i] < a1[j])
            {
                a2[k] = a1[i];
                i++;
            } else
            {
                a2[k] = a1[j];
                j++;
            }
            k++;
        }
        if (i > iEnd)
        {
            System.arraycopy(a1, j, a2, k, jEnd - j + 1);
        }
        if (j > jEnd)
        {
            System.arraycopy(a1, i, a2, k, jEnd - i + 1);
        }
    }

    /**
     * @param a1   原始数组
     * @param i    第一个有序区间的起点
     * @param iEnd 第一个有序区间的终点
     * @param j    第二个有序区间的起点
     * @param jEnd 第二个有序区间的终点
     * @param a2   结果数组
     * @param k    结果数组的起点
     * @author wxz
     * @date 11:54 2023/11/8
     */
    public static void merge(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2, int k)
    {
        if (i > iEnd)
        {
            for (; j <= jEnd; j++, k++)
            {
                a2[k] = a1[j];
            }
            return;
        }
        if (j > jEnd)
        {
            for (; i <= iEnd; i++, k++)
            {
                a2[k] = a1[i];
            }
            return;
        }
        if (a1[i] < a1[j])
        {
            a2[k] = a1[i];
            merge(a1, i + 1, iEnd, j, jEnd, a2, k + 1);
        } else
        {
            a2[k] = a1[j];
            merge(a1, i, iEnd, j + 1, jEnd, a2, k + 1);
        }
    }
}
