package com.dynamic.route.event;

import com.dynamic.route.service.ZuulRouteVoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author weijinsheng
 * @date 2017/11/13 18:02
 */
@Service
public class MonitorThread extends Thread{

    private static final Logger log = LoggerFactory.getLogger(MonitorThread.class);

    @Value("${ebtce.routeRefresh}")
    private long refreshTime;

    @Autowired
    private RefreshRouteService refreshRouteService;

    @Override
    public void run(){
        while (true){
            int state = MonitorThreadControl.getState();
            if(state == 0)
                continue;

            if(state < 0){
                log.warn("============ MonitorThread EXIT  ===============");
                return;
            }

            try {
                // do some
//                log.info("========= monitor running ==========");

                // ensure that the last route refresh is completed
                if(ZuulRouteVoService.ZUUL_ROUTE_VO_MAP.isEmpty()){
                    refreshRouteService.refreshRoute();
                }

            }catch (Exception e){
                log.error("", e);
            }

            try {
                Thread.sleep(refreshTime * (60 * 1000));
            } catch (InterruptedException e) {
               log.error("", e);
            }
        }
    }
}
