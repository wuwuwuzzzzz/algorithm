/**
 * @author wxz
 * @date 18:27 2023/12/3
 */
public class HashTable
{
    /**
     * 数组
     */
    Entry[] table = new Entry[16];
    /**
     * 元素个数
     */
    int size = 0;

    /*


     */

    /**
     * 根据 hash 码获取 value
     * <p>
     * - 求模运算替换为位运算
     * - 前提：数组长度是 2 的 n 次方
     * - hash % 数组长度 => hash & (数组长度 - 1)
     *
     * @return java.lang.Object
     * @author wxz
     * @date 18:31 2023/12/3
     */
    public Object get(int hash, Object key)
    {
        int idx = hash & (table.length - 1);
        if (table[idx] == null)
        {
            return null;
        }
        Entry p = table[idx];
        while (p != null)
        {
            if (p.key.equals(key))
            {
                return p.value;
            }
            p = p.next;
        }
        return null;
    }

    /**
     * 向 hash 表中存入新 key value，如果 key 重复，则更新 value
     *
     * @author wxz
     * @date 18:49 2023/12/3
     */
    public void put(int hash, Object key, Object value)
    {
        int idx = hash & (table.length - 1);
        if (table[idx] == null)
        {
            // idx 处有空位，直接新增
            table[idx] = new Entry(hash, key, value);
        }
        else
        {
            // idx 处无空位，沿链表查找，有重复 key 更新，否则新增
            Entry p = table[idx];
            while (true)
            {
                if (p.key.equals(key))
                {
                    // 更新
                    p.value = value;
                    return;
                }
                if (p.next == null)
                {
                    break;
                }
                p = p.next;
            }
            // 新增
            p.next = new Entry(hash, key, value);
        }
        size++;
    }

    /**
     * 根绝 hash 码删除，返回删除的 value
     *
     * @return java.lang.Object
     * @author wxz
     * @date 18:50 2023/12/3
     */
    public Object remove(int hash, Object key)
    {
        int idx = hash & (table.length - 1);
        if (table[idx] == null)
        {
            return null;
        }
        Entry p = table[idx];
        Entry prev = null;
        while (p != null)
        {
            if (p.key.equals(key))
            {
                // 删除
                if (prev == null)
                {
                    table[idx] = p.next;
                }
                else
                {
                    prev.next = p.next;
                }
                size--;
                return p.value;
            }
            prev = p;
            p = p.next;
        }
        return null;
    }

    /**
     * 节点类
     */
    static class Entry
    {
        /**
         * 哈希码
         */
        int hash;

        /**
         * 键
         */
        Object key;

        /**
         * 值
         */
        Object value;

        /**
         * 指针
         */
        Entry next;

        /**
         * 构造方法
         *
         * @author wxz
         * @date 18:28 2023/12/3
         */
        public Entry(int hash, Object key, Object value)
        {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }
}
