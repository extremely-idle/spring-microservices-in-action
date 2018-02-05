import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;
import static org.springframework.boot.SpringApplication.run;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SpringBootApplication
@RestController
@RequestMapping(value = "hello")
public class Application {
    public static void main(String[] args) {
        run(Application.class, args);
    }

    @RequestMapping(value = "/{firstName}/{lastName}", method = GET)
    public String hello(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return format("{\"message\":\"Hello %s %s\"}", firstName, lastName);
    }
}