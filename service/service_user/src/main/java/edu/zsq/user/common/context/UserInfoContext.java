package edu.zsq.user.common.context;

import edu.zsq.user.entity.User;

/**
 * @author zhangsongqi
 * @date 1:56 下午 2021/4/7
 */
public class UserInfoContext {

    private static final ThreadLocal<User> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    private UserInfoContext() {
    }

    public static void set(User user) {
        USER_INFO_THREAD_LOCAL.set(user);
    }

    public static User get() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    public static void remove() {
        USER_INFO_THREAD_LOCAL.remove();
    }
}
