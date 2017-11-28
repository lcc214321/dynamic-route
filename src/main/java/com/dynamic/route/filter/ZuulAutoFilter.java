package com.dynamic.route.filter;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态路由；　过滤＋动态路由
 * @author weijinsheng
 * @date 2017/11/9 18:02
 */
public class ZuulAutoFilter extends ZuulFilter{

    private static final Logger log = LoggerFactory.getLogger(ZuulAutoFilter.class);

    /**
     * pre：可以在请求被路由之前调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
//        RequestContext rc = RequestContext.getCurrentContext();
//        HttpServletRequest request = rc.getRequest();
//
//        String uri =request.getRequestURI();
//
//        if(uri == null){
//            rc.setResponseStatusCode(404);
//            rc.setSendZuulResponse(false);
//            log.error("service does not exist：" + uri) ;
//        }else {
//
//        }
        return null;
    }
}
