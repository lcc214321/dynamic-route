package com.dynamic.route.event;

import com.dynamic.route.service.ZuulRouteVoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author weijinsheng
 * @date 2017/11/13 10:25
 */
@Service
public class RefreshRouteService {

    private static final Logger log = LoggerFactory.getLogger(RefreshRouteService.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private ZuulRouteVoService zuulRouteVoService;

    public void refreshRoute(){
        zuulRouteVoService.initZuulRouteVoMap();

        if(!ZuulRouteVoService.ZUUL_ROUTE_VO_MAP.isEmpty()){
            RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
            publisher.publishEvent(routesRefreshedEvent);
            log.info("== dynamic route refresh success! ==");
        }else{
            log.info("== routing information is not updated ==");
        }
    }
}
