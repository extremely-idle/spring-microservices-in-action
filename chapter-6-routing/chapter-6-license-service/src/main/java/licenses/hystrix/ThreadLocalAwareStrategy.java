package licenses.hystrix;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import licenses.utils.UserContext;
import licenses.utils.UserContextHolder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {
    private HystrixConcurrencyStrategy existingHystrixConcurrentStrategy;

    public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existingHystrixConcurrentStrategy) {
        this.existingHystrixConcurrentStrategy = existingHystrixConcurrentStrategy;
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return hasExistingConcurrencyStrategy()
                ? existingHystrixConcurrentStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
                : super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return hasExistingConcurrencyStrategy()
                ? existingHystrixConcurrentStrategy.getBlockingQueue(maxQueueSize)
                : super.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        final Callable<T> callableWrapper = new DelegatingUserContextCallable(callable, UserContextHolder.getContext());

        return hasExistingConcurrencyStrategy()
                ? existingHystrixConcurrentStrategy.wrapCallable(callableWrapper)
                : super.wrapCallable(callableWrapper);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        return hasExistingConcurrencyStrategy()
                ? existingHystrixConcurrentStrategy.getRequestVariable(rv)
                : super.getRequestVariable(rv);
    }

    private boolean hasExistingConcurrencyStrategy() {
        return existingHystrixConcurrentStrategy != null;
    }
}