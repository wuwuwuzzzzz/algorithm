import java.util.Arrays;

/**
 * 二分查找
 *
 * @author wxz
 * @date 16:14 2023/10/19
 */
public class BinarySearch
{
    public static void main(String[] args)
    {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        int[] b = {1, 2, 4, 4, 4, 5, 6, 7};
        System.out.println(binarySearch(a, 21));
        System.out.println(binarySearch2(a, 21));
        System.out.println(binarySearch3(a, 100));
        System.out.println(binarySearch4(b, 4));
        System.out.println(binarySearch5(b, 4));
    }

    /**
     * 二分查找 - 基础版
     *
     * <p>
     * 1. 为什么是 i <= j 而不是 i < j ? answer: i == j 时，还有一个元素没有判断
     * 2. (i + j) / 2 有没有问题 ? answer: 有，当 i 和 j 都很大时，i + j 可能会溢出
     * </p>
     *
     * @param a      生序数组
     * @param target 目标值
     * @return int
     * @author wxz
     * @date 16:15 2023/10/19
     */
    public static int binarySearch(int[] a, int target)
    {
        // 设置指针初值
        int i = 0, j = a.length;
        // 范围内有东西
        while (i < j)
        {
            // 中间值
            int m = (i + j) >>> 1;
            // 目标在左边
            if (target < a[m])
            {
                j = m;
            }
            // 目标在右边
            else if (target > a[m])
            {
                i = m + 1;
            }
            // 目标值等于中间值
            else
            {
                return m;
            }
        }
        return -1;
    }

    /**
     * 二分查找 - 平衡板
     *
     * @param a      生序数组
     * @param target 目标值
     * @return int
     * @author wxz
     * @date 09:51 2023/10/20
     */
    public static int binarySearch2(int[] a, int target)
    {
        // 设置指针初值
        int i = 0, j = a.length;
        // 范围内有东西
        while (1 < j - i)
        {
            // 中间值
            int m = (i + j) >>> 1;
            // 目标在左边
            if (target < a[m])
            {
                j = m;
            }
            // 目标在右边
            else
            {
                i = m;
            }
        }
        // 找到了
        if (a[i] == target)
        {
            return i;
        }
        // 没找到
        else
        {
            return -1;
        }
    }

    /**
     * 二分查找 - JAVA版
     *
     * @param a      生序数组
     * @param target 目标值
     * @return int
     * @author wxz
     * @date 10:04 2023/10/20
     */
    public static int binarySearch3(int[] a, int target)
    {
        // 使用 JDK 自带的二分查找
        int i = Arrays.binarySearch(a, target);
        // 如果没找到则插入
        if (i < 0)
        {
            // 插入点索引
            int insertIndex = Math.abs(i + 1);
            // 创建新数组 b
            int[] b = new int[a.length + 1];
            // 拷贝数据
            System.arraycopy(a, 0, b, 0, insertIndex);
            // 插入数据
            b[insertIndex] = target;
            // 重新拷贝数据
            System.arraycopy(a, insertIndex, b, insertIndex + 1, a.length - insertIndex);
            System.out.println(Arrays.toString(b));
            // 返回插入点索引
            return insertIndex;
        }
        // 否则返回下标
        else
        {
            return i;
        }
    }

    /**
     * 二分查找 - Leftmost
     *
     * @param a      生序数组
     * @param target 目标值
     * @return int
     * @author wxz
     * @date 10:24 2023/10/20
     */
    public static int binarySearch4(int[] a, int target)
    {
        // 设置指针初值
        int i = 0, j = a.length;
        // 候选位置
        int candidate = -1;
        // 范围内有东西
        while (i < j)
        {
            // 中间值
            int m = (i + j) >>> 1;
            // 目标在左边
            if (target < a[m])
            {
                j = m;
            }
            // 目标在右边
            else if (target > a[m])
            {
                i = m + 1;
            }
            // 目标值等于中间值
            else
            {
                // 记录候选位置
                candidate = j = m;
            }
        }
        return candidate;
    }

    /**
     * 二分查找 - Rightmost
     *
     * @param a      生序数组
     * @param target 目标值
     * @return int
     * @author wxz
     * @date 10:24 2023/10/20
     */
    public static int binarySearch5(int[] a, int target)
    {
        // 设置指针初值
        int i = 0, j = a.length;
        // 候选位置
        int candidate = -1;
        // 范围内有东西
        while (i < j)
        {
            // 中间值
            int m = (i + j) >>> 1;
            // 目标在左边
            if (target < a[m])
            {
                j = m;
            }
            // 目标在右边
            else if (target > a[m])
            {
                i = m + 1;
            }
            // 目标值等于中间值
            else
            {
                // 记录候选位置
                candidate = m;
                // 继续向右查找
                i = m + 1;
            }
        }
        return candidate;
    }
}
