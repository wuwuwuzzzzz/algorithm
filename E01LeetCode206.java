/**
 * @author wxz
 * @date 11:49 2023/11/5
 */
public class E01LeetCode206
{
    /**
     * @param args
     * @author wxz
     * @date 11:56 2023/11/5
     */
    public static void main(String[] args)
    {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, null))));
        System.out.println(head);
        System.out.println(reverseList(head));
    }

    /**
     * 反转链表
     *
     * @param o1 旧链表
     * @return ListNode
     * @author wxz
     * @date 11:51 2023/11/5
     */
    public static ListNode reverseList(ListNode o1)
    {
        ListNode n1 = null;
        ListNode p = o1;
        while (p != null)
        {
            n1 = new ListNode(p.val, n1);
            p = p.next;
        }
        return n1;
    }
}
