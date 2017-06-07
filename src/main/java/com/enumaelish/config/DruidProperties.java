package com.enumaelish.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Druid 配置信息，从application.properties读取.
 */
@Component
public class DruidProperties {

    @Value("${druid.allow}")
    private String allow;

    @Value("${druid.deny}")
    private String deny;

    @Value("${druid.path}")
    private String path;

    @Value("${druid.loginUsername}")
    private String loginUsername;

    @Value("${druid.loginPassword}")
    private String loginPassword;

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getDeny() {
        return deny;
    }

    public void setDeny(String deny) {
        this.deny = deny;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
