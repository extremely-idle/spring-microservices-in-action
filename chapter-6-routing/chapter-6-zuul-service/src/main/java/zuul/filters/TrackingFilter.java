package zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static java.util.UUID.randomUUID;
import static zuul.filters.FilterUtils.PRE_FILTER_TYPE;

@Component
public class TrackingFilter extends ZuulFilter {

    public static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FITLER = true;
    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FITLER;
    }

    @Override
    public Object run() {
        if (isCorrelationIdPresent()) {
            logger.debug("tmx-correlation-id found in tracking filter: {}.", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generateCorrelationId());

            logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }

        final RequestContext ctx = getCurrentContext();

        logger.debug("Processing incoming request for {}.", ctx.getRequest().getRequestURI());

        return null;
    }

    private String generateCorrelationId() {
        return randomUUID().toString();
    }

    private boolean isCorrelationIdPresent() {
        return filterUtils.getCorrelationId() != null;
    }
}