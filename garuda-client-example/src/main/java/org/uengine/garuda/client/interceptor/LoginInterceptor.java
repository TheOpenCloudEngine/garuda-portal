package org.uengine.garuda.client.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.uengine.garuda.client.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hoo.lim on 6/24/2015.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        String requestUri = request.getRequestURI();

        if(user == null && requestUri.contains("main")){
            request.getRequestDispatcher("/user/login").forward(request, response);
            return false;
        }else if(user !=null && requestUri.contains("login")){
            request.getRequestDispatcher("/user/main").forward(request, response);
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
