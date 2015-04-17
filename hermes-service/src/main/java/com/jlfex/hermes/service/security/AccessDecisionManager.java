package com.jlfex.hermes.service.security;
import java.util.Collection;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 访问决策管理器

 */
public class AccessDecisionManager implements org.springframework.security.access.AccessDecisionManager {

	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		// 判断目标是否在权限控制内
		if (configAttributes == null) return;
		
		// 遍历权限
		for (ConfigAttribute configAttribute: configAttributes) {
			// 将权限与用户角色进行匹配
			String role = configAttribute.getAttribute();
			for (GrantedAuthority grantedAuthority: authentication.getAuthorities()) {
				Logger.debug("match between %s and %s.", role, grantedAuthority.getAuthority());
				if (Strings.equals(role, grantedAuthority.getAuthority())) {
					Logger.debug("matched! access allow.");
					return;
				}
			}
		}
		
		// 无法匹配权限抛出异常
		Logger.info("denied!");
		throw new AccessDeniedException("no authority.");
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#supports(org.springframework.security.access.ConfigAttribute)
	 */
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
