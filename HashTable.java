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

    /**
     * 负载因子
     */
    float loadFactor = 0.75f;

    /**
     * 阈值
     */
    int threshold = (int) (loadFactor * table.length);

    /**
     * 获取哈希值
     *
     * @return int
     * @author wxz
     * @date 10:35 2023/12/4
     */
    private static int getHash(Object key)
    {
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

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
        if (size > threshold)
        {
            resize();
        }
    }

    /**
     * 扩容
     *
     * @author wxz
     * @date 09:48 2023/12/4
     */
    private void resize()
    {
        Entry[] newTable = new Entry[table.length << 1];
        for (int i = 0; i < table.length; i++)
        {
            Entry p = table[i];
            if (p != null)
            {
                // 拆分链表，移动到新数组
                Entry a = null;
                Entry b = null;
                Entry aHead = null;
                Entry bHead = null;
                while (p != null)
                {
                    if ((p.hash & table.length) == 0)
                    {
                        if (a != null)
                        {
                            a.next = p;
                        }
                        else
                        {
                            aHead = p;
                        }
                        a = p;
                    }
                    else
                    {
                        if (b != null)
                        {
                            b.next = p;
                        }
                        else
                        {
                            bHead = p;
                        }
                        b = p;
                    }
                    p = p.next;
                }
                if (a != null)
                {
                    a.next = null;
                    newTable[i] = aHead;
                }
                if (b != null)
                {
                    b.next = null;
                    newTable[i + table.length] = bHead;
                }
            }
        }
        table = newTable;
        threshold = (int) (loadFactor * table.length);
    }

    /**
     * 根据 hash 码删除，返回删除的 value
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
     * @return java.lang.Object
     * @author wxz
     * @date 10:32 2023/12/4
     */
    public Object get(Object key)
    {
        int hash = getHash(key);
        return get(hash, key);
    }

    /**
     * @author wxz
     * @date 10:33 2023/12/4
     */
    public void put(Object key, Object value)
    {
        int hash = getHash(key);
        put(hash, key, value);
    }

    /**
     * @return java.lang.Object
     * @author wxz
     * @date 10:34 2023/12/4
     */
    public Object remove(Object key)
    {
        int hash = getHash(key);
        return remove(hash, key);
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
