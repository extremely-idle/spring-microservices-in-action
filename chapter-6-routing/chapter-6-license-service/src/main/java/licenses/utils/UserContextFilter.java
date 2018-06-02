package licenses.utils;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

import static licenses.utils.UserContext.*;
import static licenses.utils.UserContextHolder.getContext;

@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = Logger.getLogger(UserContextFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        final UserContext context = getContext();
        context.setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID));
        context.setAuthToken(httpServletRequest.getHeader(AUTH_TOKEN));
        context.setUserId(httpServletRequest.getHeader(USER_ID));
        context.setOrganisationId(httpServletRequest.getHeader(ORG_ID));

        logger.fine("UserContextFilter Correlation id: " + context.getCorrelationId());;

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}