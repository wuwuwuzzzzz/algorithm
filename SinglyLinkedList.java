import java.util.Iterator;
import java.util.function.Consumer;

/**
 * 单向链表
 *
 * @author wxz
 * @date 20:10 2023/10/22
 */
public class SinglyLinkedList implements Iterable<Integer>
{
    /**
     * 头指针
     */
    private Node head = null;

    public static void main(String[] args)
    {
        SinglyLinkedList list = new SinglyLinkedList();

        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addLast(5);
        list.addLast(6);
        list.addLast(7);

        list.loop1(System.out::print);
        System.out.println();
        list.loop2(System.out::print);
        System.out.println();
        for (Integer i : list)
        {
            System.out.print(i);
        }
    }

    /**
     * 向链表头部添加
     *
     * @param value value
     * @author wxz
     * @date 20:19 2023/10/22
     */
    public void addFirst(int value)
    {
        // 链表为空
//        head = new Node(value, null);
        // 链表非空
        head = new Node(value, head);
    }

    /**
     * 遍历链表
     *
     * @param consumer consumer
     * @author wxz
     * @date 20:21 2023/10/22
     */
    public void loop1(Consumer<Integer> consumer)
    {
        Node p = head;
        while (p != null)
        {
            consumer.accept(p.value);
            p = p.next;
        }
    }

    /**
     * 遍历链表
     *
     * @param consumer consumer
     * @author wxz
     * @date 20:21 2023/10/22
     */
    public void loop2(Consumer<Integer> consumer)
    {
        for (Node p = head; p != null; p = p.next)
        {
            consumer.accept(p.value);
        }
    }

    /**
     * 找到最后一个元素
     *
     * @return SinglyLinkedList.Node
     * @author wxz
     * @date 13:54 2023/10/29
     */
    private Node findLast()
    {
        // 空链表
        if (head == null)
        {
            return null;
        }
        Node p = head;
        while (p.next != null)
        {
            p = p.next;
        }
        return p;
    }

    /**
     * 插入到链表最后
     *
     * @param value value
     * @author wxz
     * @date 13:55 2023/10/29
     */
    private void addLast(int value)
    {
        Node last = findLast();
        if (last == null) {
            addFirst(value);
            return;
        }
        last.next = new Node(value, null);
    }

    @Override
    public Iterator<Integer> iterator()
    {
        return new Iterator<>()
        {
            Node p = head;

            @Override
            public boolean hasNext()
            {
                return p != null;
            }

            @Override
            public Integer next()
            {
                int v = p.value;
                p = p.next;
                return v;
            }
        };
    }

    /**
     * 节点类
     *
     * @author wxz
     * @date 20:14 2023/10/22
     */
    private static class Node
    {
        int value; // 值

        Node next; // 下一节点指针

        public Node(int value, Node next)
        {
            this.value = value;
            this.next = next;
        }
    }
}
