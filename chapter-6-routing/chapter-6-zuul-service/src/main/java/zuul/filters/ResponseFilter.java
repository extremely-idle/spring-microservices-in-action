package zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static zuul.filters.FilterUtils.CORRELATION_ID;
import static zuul.filters.FilterUtils.POST_FILTER_TYPE;

public class ResponseFilter extends ZuulFilter {

    public static final int FILTER_ORDER = 1;
    public static final boolean SHOULD_FILTER = true;
    public static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);


    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
       final RequestContext ctx = getCurrentContext();

       final String correlationId = filterUtils.getCorrelationId();

       logger.debug("Adding the correlation id to the outbound headers. {}", correlationId);

       ctx.getResponse().addHeader(CORRELATION_ID, correlationId);

       return null;
    }
}