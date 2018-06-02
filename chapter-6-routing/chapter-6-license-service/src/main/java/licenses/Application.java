package licenses;

import com.google.common.collect.ImmutableList;
import licenses.utils.UserContextInterceptor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class Application {

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        final RestTemplate template = new RestTemplate();
        final List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();

        if (interceptors == null) {
            template.setInterceptors(of(new UserContextInterceptor()));
        } else {
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }

        return template;
    }

    public static void main(String[] args) {
        run(Application.class, args);
    }
}