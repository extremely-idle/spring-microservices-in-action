package licenses.hystrix;

import licenses.utils.UserContext;
import licenses.utils.UserContextHolder;

import java.util.concurrent.Callable;

public class DelegatingUserContextCallable<T> implements Callable<T> {
    private final Callable<T> delegate;
    private UserContext context;

    public DelegatingUserContextCallable(Callable<T> delegate, UserContext context) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    public T call() throws Exception {
        UserContextHolder.setContext(context);

        try {
            return delegate.call();
        } finally {
            this.context = null;
        }
    }

    public static <T> Callable<T> create(Callable<T> delegate, UserContext userContext) {
        return new DelegatingUserContextCallable<>(delegate, userContext);
    }
}