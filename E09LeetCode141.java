/**
 * @author wxz
 * @date 17:19 2023/11/7
 */
public class E09LeetCode234
{
    /**
     * @param args args
     * @author wxz
     * @date 11:08 2023/11/8
     */
    public static void main(String[] args)
    {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1, null))));
        System.out.println(head);
        System.out.println(new E09LeetCode234().isPalindrome(head));
    }

    /**
     * 判断是否是回环链表
     *
     * @param head head
     * @return boolean
     * @author wxz
     * @date 17:20 2023/11/7
     */
    public boolean isPalindrome(ListNode head)
    {
        // 慢
        ListNode p1 = head;
        // 快
        ListNode p2 = head;
        // 新头
        ListNode n1 = null;
        // 旧头
        ListNode o1 = head;
        while (p2 != null && p2.next != null)
        {
            p1 = p1.next;
            p2 = p2.next.next;

            // 反转链表
            o1.next = n1;
            n1 = o1;
            o1 = p1;
        }

        // 奇数节点
        if (p2 != null)
        {
            p1 = p1.next;
        }

        while (n1 != null)
        {
            if (n1.val != p1.val)
            {
                return false;
            }
            n1 = n1.next;
            p1 = p1.next;
        }
        return true;
    }
}
