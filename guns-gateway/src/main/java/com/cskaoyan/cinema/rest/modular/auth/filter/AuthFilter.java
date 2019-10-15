package com.cskaoyan.cinema.rest.modular.auth.filter;

import com.cskaoyan.cinema.core.base.tips.ErrorTip;
import com.cskaoyan.cinema.core.util.RenderUtil;
import com.cskaoyan.cinema.rest.common.exception.BizExceptionEnum;
import com.cskaoyan.cinema.rest.config.properties.JwtProperties;
import com.cskaoyan.cinema.rest.modular.auth.util.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:04
 */
public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private Jedis jedis;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String servletPath = request.getServletPath();
        String ignoreUrls = jwtProperties.getIgnoreUrl();
        String[] split = ignoreUrls.split(",");
        for (String ignoreUrl : split) {
            //如果url结尾为通配符
            if (ignoreUrl.endsWith("*")) {
                //判断url是否以其开头
                if (servletPath.startsWith(ignoreUrl.substring(0, ignoreUrl.length() - 1))) {
                    chain.doFilter(request, response);
                    return;
                }
            } else {
                //否则判断是否为相同url
                if (ignoreUrl.equals(servletPath)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            /*try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return;
            }*/

            String userId = jedis.get(authToken);
            if (StringUtils.isEmpty(userId)) {
                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return;
            } else {
                //刷新用户缓存时间
                jedis.expire(authToken, 18000);
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return;
        }
        chain.doFilter(request, response);
    }
}