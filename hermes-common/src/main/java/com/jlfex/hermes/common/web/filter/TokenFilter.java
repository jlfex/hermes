package com.jlfex.hermes.common.web.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.web.WebApp;

/**
 * 令牌过滤器
 * 
 */
public class TokenFilter implements Filter {

	private static final String PARAM_TOKEN = "token";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.info("token filter is working...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebApp.isResource(req)) {
			String token = req.getParameter(PARAM_TOKEN);
			if (!Strings.empty(token)) {
				if (!Strings.equals(token, App.current().getToken())) {
					Logger.warn("token令牌不匹配： %s and %s.", token, App.current().getToken());
					throw new ServiceException("token令牌不匹配：", "app.exception.token");
				}
				App.current().updateToken();
			}
		}
		chain.doFilter(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("destroyed token filter!");
	}
}
