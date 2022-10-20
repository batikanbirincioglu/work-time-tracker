package com.wtt.commondependencies.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

@Slf4j
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private Object filterObject;
    private Object returnObject;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return new Object();
    }

    public boolean isManager() {
        boolean manager = SecurityUtils.isManager();
        if (manager) {
            log.info("Authenticated person = {} is manager", SecurityUtils.getName());
        } else {
            log.info("Authenticated person = {} is not manager", SecurityUtils.getName());
        }
        return manager;
    }
}
