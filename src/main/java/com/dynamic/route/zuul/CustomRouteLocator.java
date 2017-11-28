package com.dynamic.route.zuul;

import com.alibaba.fastjson.JSON;
import com.dynamic.route.service.ZuulRouteVoService;
import com.dynamic.route.vo.ZuulRouteVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由探测器
 * @author weijinsheng
 * @date 2017/11/10 17:33
 */
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator{

    private static final Logger log = LoggerFactory.getLogger(CustomRouteLocator.class);

    private ZuulProperties properties;

    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    @Override
    public void refresh() {
        super.doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes(){
        // 加载配置信息
        LinkedHashMap<String, ZuulRouteVo> routeLinkedHashMap = ZuulRouteVoService.ZUUL_ROUTE_VO_MAP;

        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        log.info("========= dynamic route info ========== ");
        for(Map.Entry<String, ZuulRouteVo> entry : routeLinkedHashMap.entrySet()){
            String path = entry.getKey();
            if(!path.startsWith("/")){
                path = "/" + path;
            }

            if(StringUtils.hasText(this.properties.getPrefix())){
                path = this.properties.getPrefix();
                if(!path.startsWith("/")){
                    path = "/" + path;
                }
            }

            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            BeanUtils.copyProperties(entry.getValue(), zuulRoute);
            values.put(path, zuulRoute);

            log.info(JSON.toJSONString(zuulRoute));
        }

        ZuulRouteVoService.clearRouteMap();
        return values;
    }
}
