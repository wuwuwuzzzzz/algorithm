import java.util.Arrays;

/**
 * 数独
 *
 * @author wxz
 * @date 16:09 2023/12/13
 */
public class SudokuLeetCode37
{
    /**
     * @author wxz
     * @date 16:09 2023/12/13
     */
    static void solveSudoku(char[][] table)
    {
        // 行冲突状态
        boolean[][] ca = new boolean[9][9];
        // 列冲突状态
        boolean[][] cb = new boolean[9][9];
        // 九宫格冲突状态
        boolean[][] cc = new boolean[9][9];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                char ch = table[i][j];
                // 初始化冲突状态
                if (ch != '.')
                {
                    ca[i][ch - '1'] = true;
                    cb[j][ch - '1'] = true;
                    cc[i / 3 * 3 + j / 3][ch - '1'] = true;
                }
            }
        }
        dfs(0, 0, table, ca, cb, cc);
    }

    /**
     * 递归调用
     *
     * @return boolean
     * @author wxz
     * @date 16:22 2023/12/13
     */
    static boolean dfs(int i, int j, char[][] table, boolean[][] ca, boolean[][] cb, boolean[][] cc)
    {
        // 查找下一个空格
        while (table[i][j] != '.')
        {
            if (++j >= 9) {
                j = 0;
                i++;
            }
            if (i >= 9) {
                return true;
            }
        }
        // 填空
        for (int x = 1; x <= 9; x++)
        {
            if (ca[i][x - 1] || cb[j][x - 1] || cc[i / 3 * 3 + j / 3][x - 1]) {
                continue;
            }
            table[i][j] = (char) (x + '0');
            ca[i][x - 1] = cb[j][x - 1] = cc[i / 3 * 3 + j / 3][x - 1] = true;
            if (dfs(i, j, table, ca, cb, cc))
            {
                return true;
            }
            table[i][j] = '.';
            ca[i][x - 1] = cb[j][x - 1] = cc[i / 3 * 3 + j / 3][x - 1] = false;
        }
        return false;
    }

    /**
     * @author wxz
     * @date 16:15 2023/12/13
     */
    public static void main(String[] args)
    {
        char[][] table = {
                {'.', '.', '.', '5', '.', '.', '6', '.', '.'},
                {'.', '2', '.', '7', '.', '.', '.', '.', '.'},
                {'.', '.', '8', '.', '2', '6', '.', '.', '3'},
                {'9', '.', '.', '.', '.', '.', '.', '.', '8'},
                {'.', '.', '.', '6', '.', '.', '.', '.', '.'},
                {'.', '5', '.', '.', '4', '1', '3', '.', '.'},
                {'.', '.', '5', '.', '.', '.', '.', '4', '.'},
                {'.', '4', '.', '.', '3', '2', '1', '.', '.'},
                {'.', '.', '.', '.', '7', '.', '.', '.', '.'}
        };
        solveSudoku(table);
        System.out.println(Arrays.deepToString(table));
    }
}
