package spring.cloud.microservice.zuul.api.gateway;

import com.netflix.zuul.ZuulFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

@Log4j2
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.error("Exception occured");

        return null;
    }
}
