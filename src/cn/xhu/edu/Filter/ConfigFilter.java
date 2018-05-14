package cn.xhu.edu.Filter;

import com.google.gson.Gson;

import cn.xhu.edu.domain.MyResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class ConfigFilter implements Filter {
    private FilterConfig mFilterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.mFilterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        MyResponse myResponse = new MyResponse();
        try {
            response.setContentType("application/json;charset=utf-8");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse.setCode(5);
            myResponse.setMessage("服务器错�?");
            response.getWriter().print(new Gson().toJson(myResponse));
        }
    }

    @Override
    public void destroy() {
        this.mFilterConfig = null;
    }
}
