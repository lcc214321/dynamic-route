package com.dynamic.route.service;

import com.dynamic.route.dao.ZuulRouteVoDao;
import com.dynamic.route.vo.ZuulRouteVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ZuulRouteVoService{

    private static final Logger log = LoggerFactory.getLogger(ZuulRouteVoService.class);

    public static LinkedHashMap<String, ZuulRouteVo> ZUUL_ROUTE_VO_MAP = new LinkedHashMap<>();

    @Resource
    private ZuulRouteVoDao zuulRouteVoDao;

    /**
     * initialize and refresh dynamic route Map
     */
    public void initZuulRouteVoMap(){
        LinkedHashMap<String, ZuulRouteVo> tempMap = queryAll();
        if(tempMap != null && !tempMap.isEmpty()){
            synchronized (ZUUL_ROUTE_VO_MAP){
                ZUUL_ROUTE_VO_MAP.putAll(tempMap);
            }

            // update is_read status
            tempMap.forEach((key, zuulRouteVo) ->{
                updateStatusById(zuulRouteVo.getId());
            });
        }
    }


    /**
     * dynamic routing is cleared after production
     */
    public static void clearRouteMap(){
        synchronized (ZUUL_ROUTE_VO_MAP){
            ZUUL_ROUTE_VO_MAP.clear();
        }
    }

    public LinkedHashMap<String, ZuulRouteVo> queryAll(){
        LinkedHashMap<String, ZuulRouteVo> zuulRouteVoMap = new LinkedHashMap<>();
        try {
            List<ZuulRouteVo> zuulRouteVoList = zuulRouteVoDao.queryAll();
            zuulRouteVoList.stream().forEach(zuulRouteVo -> {
                String path = zuulRouteVo.getPath();
                zuulRouteVoMap.put(path, new ZuulRouteVo(zuulRouteVo.getId() ,path, zuulRouteVo.getHost()));
            });
            log.info("== query dynamic routing information is successful");
        }catch (Exception e){
            log.error("== query dynamic routing information failed: " + e);
        }
        return zuulRouteVoMap;
    }

    public void updateStatusById(String id){
        try {
            zuulRouteVoDao.updateStatusById(id);
        }catch (Exception e){
            log.error("= update route status error:" + e);
        }
    }

}
