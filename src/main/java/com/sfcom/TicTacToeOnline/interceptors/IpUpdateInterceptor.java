package com.sfcom.TicTacToeOnline.interceptors;

import com.sfcom.TicTacToeOnline.services.UserManagerService;
import com.sfcom.TicTacToeOnline.utils.HeadersUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;

public class IpUpdateInterceptor implements HandlerInterceptor {

    @Autowired
    public UserManagerService userManager;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(isNull(request.getCookies())) {
            return true;
        }

        for (Cookie cookie : request.getCookies()) {
            if (!"userId".equals(cookie.getName())) {
                continue;
            }

            String value = cookie.getValue();
            if (!value.matches("[0-9]+")) {
                break;
            }

            int userId = Integer.parseInt(value);
            if(!userManager.exists(userId)) {
                break;
            }

            String ip = HeadersUtils.getClientIp(request);
            userManager.updateIp(userId, ip);
        }

        return true;
    }

}
