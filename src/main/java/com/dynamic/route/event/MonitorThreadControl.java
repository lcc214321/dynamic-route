package com.dynamic.route.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weijinsheng
 * @date 2017/11/13 17:55
 */
public class MonitorThreadControl {

    private static final Logger log = LoggerFactory.getLogger(MonitorThreadControl.class);

    MonitorThreadControl(){}

    public static int state = 0;
    public static Object objLock = new Object();

    public static int getState(){
        synchronized (objLock){
            if(state == 0){
                try {
                    objLock.wait(5000);
                } catch (InterruptedException e) {
                    log.error("", e);
                }
            }
            return state;
        }
    }

    public static void setState(int n){
        synchronized (objLock){
            state = n;
            objLock.notifyAll();
        }
    }
}
