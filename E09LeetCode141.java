/**
 * @author wxz
 * @date 17:19 2023/11/7
 */
public class E09LeetCode141
{
    /**
     * @param args args
     * @author wxz
     * @date 11:08 2023/11/8
     */
    public static void main(String[] args)
    {
        // 创建环形链表
        ListNode n4 = new ListNode(4, null);
        ListNode n3 = new ListNode(3, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);
        n4.next = n1;
        System.out.println(new E09LeetCode141().hasCycle(n1));
        System.out.println(new E09LeetCode141().detectCycle(n1).val);
    }

    /**
     * 检测链表是否有环
     *
     * @param head head
     * @return boolean
     * @author wxz
     * @date 17:20 2023/11/7
     */
    public boolean hasCycle(ListNode head)
    {
        // 兔
        ListNode h = head;
        // 龟
        ListNode t = head;
        while (h != null && h.next != null)
        {
            t = t.next;
            h = h.next.next;
            if (h == t)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测环的入口
     *
     * @param head head
     * @return ListNode
     * @author wxz
     * @date 11:36 2023/11/8
     */
    public ListNode detectCycle(ListNode head)
    {
        // 兔
        ListNode h = head;
        // 龟
        ListNode t = head;
        while (h != null && h.next != null)
        {
            t = t.next;
            h = h.next.next;
            if (h == t)
            {
                t = head;
                while (true) {
                    if (t == h) {
                        return t;
                    }
                    t = t.next;
                    h = h.next;
                }
            }
        }
        return null;
    }
}
