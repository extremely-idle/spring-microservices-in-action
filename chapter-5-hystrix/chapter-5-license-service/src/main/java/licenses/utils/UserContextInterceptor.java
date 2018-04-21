package licenses.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.logging.Logger;

import static licenses.utils.UserContext.AUTH_TOKEN;
import static licenses.utils.UserContext.CORRELATION_ID;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger logger = Logger.getLogger(UserContextInterceptor.class.getName());

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution
            clientHttpRequestExecution) throws IOException {
        final HttpHeaders headers = httpRequest.getHeaders();

        final UserContext context = UserContextHolder.getContext();

        headers.add(CORRELATION_ID, context.getCorrelationId());
        headers.add(AUTH_TOKEN, context.getAuthToken());

        return clientHttpRequestExecution.execute(httpRequest, body);
    }
}