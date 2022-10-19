package com.wtt.commondependencies.utils;

import com.wtt.commondependencies.model.Principal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getUserId() {
        return getPrincipal().getId();
    }

    public static String getName() {
        return getPrincipal().getName();
    }

    public static boolean isManager() {
        return getPrincipal().isManager();
    }

    private static Principal getPrincipal() {
        return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
