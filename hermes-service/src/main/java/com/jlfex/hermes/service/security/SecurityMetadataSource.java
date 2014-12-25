package com.jlfex.hermes.service.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.web.WebApp;
import com.jlfex.hermes.model.Navigation;
import com.jlfex.hermes.model.RoleResource;
import com.jlfex.hermes.service.NavigationService;
import com.jlfex.hermes.service.RoleService;

/**
 * 安全元数据
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-12
 * @since 1.0
 */
public class SecurityMetadataSource implements org.springframework.security.access.SecurityMetadataSource {

	/** 属性映射 */
	private static Map<String, Collection<ConfigAttribute>> attributesMap;

	/** 角色业务接口 */
	@Autowired
	private RoleService roleService;

	/** 导航业务接口 */
	@Autowired
	private NavigationService navigationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#getAttributes
	 * (java.lang.Object)
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String uri = HttpServletRequest.class.cast(object).getRequestURI();
		uri = uri.replaceFirst(WebApp.getAppPath(), "");
		for (Entry<String, Collection<ConfigAttribute>> entry : getAttributesMap().entrySet()) {
			if (Strings.equals(uri, entry.getKey())) {
				Logger.debug("match uri '%s' with pattern '%s', the config attributes is: %s", uri, entry.getKey(), entry.getValue());
				return entry.getValue();
			}
		}
		Logger.debug("cannot match uri '%s'", uri);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.access.SecurityMetadataSource#
	 * getAllConfigAttributes()
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
		for (Entry<String, Collection<ConfigAttribute>> entry : getAttributesMap().entrySet()) {
			attributes.addAll(entry.getValue());
		}
		return attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#supports(java
	 * .lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ServletRequest.class.isAssignableFrom(clazz);
	}

	/**
	 * 读取属性映射
	 * 
	 * @return
	 */
	protected Map<String, Collection<ConfigAttribute>> getAttributesMap() {
		// 仅当属性映射为空时处理
		if (attributesMap == null) {
			Logger.info("find security config attributes from database...");

			attributesMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<RoleResource> roleResources = roleService.findRoleResourceByType(RoleResource.Type.NAV);
			putConfigAttributes(roleResources);

			Logger.info("put all attributes.");
		}

		// 返回映射表
		return attributesMap;
	}

	/**
	 * 设置配置参数
	 * 
	 * @param roleResource
	 */
	protected void putConfigAttributes(List<RoleResource> roleResources) {
		// 初始化
		Map<String, Collection<ConfigAttribute>> sources = new HashMap<String, Collection<ConfigAttribute>>();

		// 遍历数据进行归并
		for (RoleResource roleResource : roleResources) {
			// 判断映射中是否已经添加相关资源
			// 若尚未添加则进行处理
			if (!sources.containsKey(roleResource.getResource())) {
				// 添加数据
				sources.put(roleResource.getResource(), new LinkedList<ConfigAttribute>());

				// 加载并处理导航信息
				Navigation navigation = navigationService.loadById(roleResource.getResource());
				if (navigation != null && navigation.isAppPath()) {
					attributesMap.put(navigation.getTruePath(), sources.get(roleResource.getResource()));
					Logger.info("find path '%s'.", navigation.getTruePath());
				}
			}

			// 追加配置参数
			sources.get(roleResource.getResource()).add(new SecurityConfig(roleResource.getRole().getAuthCode()));
		}
	}

	/**
	 * 清空属性缓存
	 */
	public static void clearAttributesMap() {
		attributesMap = null;
		Logger.info("clear security config attributes.");
	}
}
