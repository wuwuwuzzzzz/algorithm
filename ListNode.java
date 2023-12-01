/**
 * @author wxz
 * @date 11:44 2023/11/5
 */
public class ListNode
{
    /**
     * 值
     */
    public int val;

    /**
     * 下一个节点
     */
    public ListNode next;

    /**
     * 构造函数
     *
     * @param val
     * @param next
     * @author wxz
     * @date 11:48 2023/11/5
     */
    public ListNode(int val, ListNode next)
    {
        this.val = val;
        this.next = next;
    }

    /**
     * @return java.lang.String
     * @author wxz
     * @date 11:48 2023/11/5
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(64);
        sb.append('[');
        ListNode p = this;
        while (p != null)
        {
            sb.append(p.val);
            if (p.next != null)
            {
                sb.append(", ");
            }
            p = p.next;
        }
        sb.append(']');
        return sb.toString();
    }
}
