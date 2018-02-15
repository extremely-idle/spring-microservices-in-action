package licenses;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@RefreshScope
public class Application {
    public static void main(String[] args) {
        run(Application.class, args);
    }
}