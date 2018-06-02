package zuul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableZuulServer
public class ZuulServer {
    public static void main(String[] args) {
        run(ZuulServer.class, args);
    }
}
