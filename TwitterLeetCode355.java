import java.util.*;

/**
 * 简易版推特
 *
 * @author wxz
 * @date 20:45 2023/12/15
 */
public class TwitterLeetCode355
{
    /**
     * 发布时间
     */
    private static int time;
    /**
     * 用户集合
     */
    private final Map<Integer, User> userMap = new HashMap<>();

    /**
     * 发布文章
     *
     * @author wxz
     * @date 20:54 2023/12/15
     */
    public void postTweet(int userId, int tweetId)
    {
        User user = userMap.computeIfAbsent(userId, User::new);
        user.head.next = new Tweet(tweetId, time++, user.head.next);
    }

    /**
     * 新增关注
     *
     * @author wxz
     * @date 20:59 2023/12/15
     */
    public void follow(int userId, int followeeId)
    {
        User user = userMap.computeIfAbsent(userId, User::new);
        User followee = userMap.computeIfAbsent(followeeId, User::new);
        user.fllowees.add(followee.id);
    }

    /**
     * 取消关注
     *
     * @author wxz
     * @date 21:00 2023/12/15
     */
    public void unfollow(int userId, int followeeId)
    {
        User user = userMap.get(userId);
        if (user != null)
        {
            user.fllowees.remove(followeeId);
        }
    }

    /**
     * 获取最新 10 篇文章（包括自己和关注用户）
     *
     * @return java.util.List<java.lang.Integer>
     * @author wxz
     * @date 21:02 2023/12/15
     */
    public List<Integer> getNewsFeed(int userId)
    {
        User user = userMap.get(userId);
        if (user == null)
        {
            return Collections.emptyList();
        }
        // 创建优先级队列
        PriorityQueue<Tweet> queue = new PriorityQueue<>(Comparator.comparingInt(t -> -t.time));
        // 将用户文章加入队列
        if (user.head.next != null)
        {
            queue.offer(user.head.next);
        }
        // 将关注用户文章加入队列
        for (Integer followeeId : user.fllowees)
        {
            User followee = userMap.get(followeeId);
            if (followee != null)
            {
                queue.offer(followee.head.next);
            }
        }
        // 取出前 10 篇文章
        List<Integer> result = new ArrayList<>(10);
        while (result.size() < 10 && !queue.isEmpty())
        {
            Tweet tweet = queue.poll();
            result.add(tweet.id);
            if (tweet.next != null)
            {
                queue.offer(tweet.next);
            }
        }
        return result;
    }

    /**
     * 文章类
     */
    static class Tweet
    {
        /**
         * 主键
         */
        int id;

        /**
         * 发布时间
         */
        int time;

        /**
         * next 指针
         */
        Tweet next;

        /**
         * 构造方法
         *
         * @author wxz
         * @date 20:49 2023/12/15
         */
        public Tweet(int id, int time, Tweet next)
        {
            this.id = id;
            this.time = time;
            this.next = next;
        }
    }

    /**
     * 用户类
     */
    static class User
    {

        /**
         * 主键
         */
        int id;
        /**
         * 关注的用户
         */
        Set<Integer> fllowees = new HashSet<>();
        /**
         * 头节点
         */
        Tweet head = new Tweet(-1, -1, null);

        /**
         * 构造方法
         *
         * @author wxz
         * @date 20:50 2023/12/15
         */
        public User(int id)
        {
            this.id = id;
        }
    }

}
