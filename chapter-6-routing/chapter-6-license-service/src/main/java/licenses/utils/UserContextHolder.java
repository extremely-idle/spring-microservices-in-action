package licenses.utils;

import static org.springframework.util.Assert.notNull;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static final UserContext getContext() {
        UserContext context = userContext.get();

        if (context == null) {
            context = createEmptyContext();
            userContext.set(context);
        }

        return context;
    }

    public static final void setContext(UserContext context) {
        notNull(context, "Only non-null UserContext instances are permitted");

        userContext.set(context);;
    }

    public static final UserContext createEmptyContext() {
        return new UserContext();
    }
}
