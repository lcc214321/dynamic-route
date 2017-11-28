package com.dynamic.route.vo;

/**
 * @author weijinsheng
 * @date 2017/11/10 18:07
 */
public class ZuulRouteVo {
    private String id;
    private String path;
    private String host;
    private boolean stripPrefix = true;
    private boolean customSensitiveHeaders = false;
    private String url;

    public ZuulRouteVo(String id, String path, String url){
        this.id = id;
        this.path = path;
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public boolean isCustomSensitiveHeaders() {
        return customSensitiveHeaders;
    }

    public void setCustomSensitiveHeaders(boolean customSensitiveHeaders) {
        this.customSensitiveHeaders = customSensitiveHeaders;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
