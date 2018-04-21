package licenses.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ThreadLocalConfiguration {
    @Autowired(required = false)
    private HystrixConcurrencyStrategy existingConcurrencyStrategy;

    @PostConstruct
    public void init() {
        final HystrixPlugins hystrixPlugins = HystrixPlugins.getInstance();

        final HystrixEventNotifier hystrixEventNotifier = hystrixPlugins.getEventNotifier();
        final HystrixMetricsPublisher hystrixMetricsPublisher = hystrixPlugins.getMetricsPublisher();
        final HystrixPropertiesStrategy hystrixPropertiesStrategy = hystrixPlugins.getPropertiesStrategy();
        final HystrixCommandExecutionHook hystrixCommandExecutionHook = hystrixPlugins.getCommandExecutionHook();

        hystrixPlugins.reset();

        hystrixPlugins.registerConcurrencyStrategy(new ThreadLocalAwareStrategy(existingConcurrencyStrategy));
        hystrixPlugins.registerEventNotifier(hystrixEventNotifier);
        hystrixPlugins.registerMetricsPublisher(hystrixMetricsPublisher);
        hystrixPlugins.registerPropertiesStrategy(hystrixPropertiesStrategy);
        hystrixPlugins.registerCommandExecutionHook(hystrixCommandExecutionHook);
    }
}
