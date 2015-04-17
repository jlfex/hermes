package com.jlfex.hermes.service.security;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import com.jlfex.hermes.common.Logger;

/**
 * 安全过滤器
 */
public class SecurityFilter extends AbstractSecurityInterceptor implements Filter {

	/** 安全元数据 */
	private SecurityMetadataSource securityMetadataSource;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.info("initialize security filter.");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		InterceptorStatusToken token = beforeInvocation(req);
		chain.doFilter(req, resp);
		afterInvocation(token, null);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("destroy security filter.");
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#getSecureObjectClass()
	 */
	@Override
	public Class<?> getSecureObjectClass() {
		return ServletRequest.class;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#obtainSecurityMetadataSource()
	 */
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return securityMetadataSource;
	}
	
	/**
	 * 设置安全元数据
	 * 
	 * @param securityMetadataSource
	 */
	public void setSecurityMetadataSource(SecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
}
