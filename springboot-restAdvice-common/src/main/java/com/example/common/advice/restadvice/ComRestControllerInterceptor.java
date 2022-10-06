package com.example.common.advice.restadvice;

import com.example.common.advice.annotation.ComRestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ComRestControllerInterceptor implements HandlerInterceptor {

    public static final String REST_CONTROLLER_ANNO = "REST_CONTROLLER_ANNO";

    /* 在rest接口前执行该方法 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getBeanType();
            request.setAttribute(REST_CONTROLLER_ANNO, clazz.isAnnotationPresent(ComRestController.class));
        }
        // 返回true时, 执行拦截器链上下一个拦截器, 直到所有拦截器的preHandle方法执行完成后, 再执行被拦截的请求
        // 返回false时, 不再执行拦截器链后续的preHandle方法以及被拦截的请求
        return true;
    }
}
