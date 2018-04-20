package configserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        System.out.println(System.getenv("ENCRYPT_KEY"));
        System.out.println(System.getenv("JAVA_HOME"));
        run(ConfigServerApplication.class, args);
    }
}
