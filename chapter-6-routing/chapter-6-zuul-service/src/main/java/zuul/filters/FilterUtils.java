package zuul.filters;

import com.netflix.zuul.context.RequestContext;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

public class FilterUtils {

    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";
    public static final String CORRELATION_ID = "tmx-correlation-id";
    private static final String SERVICE_ID = "serviceId";

    public String getCorrelationId() {
        final RequestContext ctx = getCurrentContext();

        final String correlationId = ctx.getRequest()
                                        .getHeader(CORRELATION_ID);
        if (correlationId != null) {
            return correlationId;
        } else {
            return ctx.getZuulRequestHeaders()
                      .get(CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId) {
        final RequestContext ctx = getCurrentContext();

        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    public String getServiceId() {
        final RequestContext ctx = getCurrentContext();

        final String serviceId = (String) ctx.get(SERVICE_ID);

        return serviceId;
    }
}