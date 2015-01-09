package com.jlfex.hermes.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.exception.ServiceException;

/**
 * 公共仓库
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-04
 * @since 1.0
 */
@Component
@Transactional
public class CommonRepository {

	/** 实体管理器 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * 通过持久化脚本查询数据
	 * 
	 * @param jpql
	 * @param params
	 * @param requiredType
	 * @return
	 */
	public <T> List<T> findByJpql(String jpql, Map<String, Object> params,
			Class<T> requiredType) {
		TypedQuery<T> query = em.createQuery(jpql, requiredType);
		for (Entry<String, Object> entry : params.entrySet())
			query.setParameter(entry.getKey(), entry.getValue());
		return query.getResultList();
	}

	/**
	 * 通过持久化脚本分页查询数据
	 * 
	 * @param jpql
	 * @param params
	 * @param startPosition
	 * @param maxResult
	 * @param requiredType
	 * @return
	 */
	public <T> List<T> pageByJpql(String jpql, Map<String, Object> params,
			Integer startPosition, Integer maxResult, Class<T> requiredType) {
		TypedQuery<T> query = em.createQuery(jpql, requiredType);
		query.setFirstResult(startPosition);
		query.setMaxResults(maxResult);
		for (Entry<String, Object> entry : params.entrySet())
			query.setParameter(entry.getKey(), entry.getValue());
		return query.getResultList();
	}

	/**
	 * 统计记录数量
	 * 
	 * @param jpql
	 * @param params
	 * @return
	 */
	public Long count(String jpql, Map<String, Object> params) {
		Query query = em.createQuery(jpql);
		for (Entry<String, Object> entry : params.entrySet())
			query.setParameter(entry.getKey(), entry.getValue());
		return Long.class.cast(query.getSingleResult());
	}

	/**
	 * 通过原生脚本查询数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<?> findByNativeSql(String sql, Map<String, Object> params) {
		Query query = em.createNativeQuery(sql);
		for (Entry<String, Object> entry : params.entrySet())
			query.setParameter(entry.getKey(), entry.getValue());
		return query.getResultList();
	}

	/**
	 * 通过原生脚本分页查询数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<?> findByNativeSql(String sql, Map<String, Object> params,
			Integer startPosition, Integer maxResult) {
		Query query = em.createNativeQuery(sql);
		query.setFirstResult(startPosition);
		query.setMaxResults(maxResult);
		for (Entry<String, Object> entry : params.entrySet())
			query.setParameter(entry.getKey(), entry.getValue());
		return query.getResultList();
	}

	/**
	 * 读取脚本文件
	 * 
	 * @param file
	 * @return
	 */
	public String readScriptFile(String path) {
		// 初始化
		String line = null;
		StringBuilder script = new StringBuilder();

		// 读取文件
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					CommonRepository.class.getResourceAsStream(path), "utf-8"));
			while ((line = reader.readLine()) != null)
				script.append(line.trim()).append(" ");
			return script.toString();
		} catch (UnsupportedEncodingException e) {
			throw new ServiceException("cannot read script file '" + path
					+ "'.", e);
		} catch (IOException e) {
			throw new ServiceException("cannot read script file '" + path
					+ "'.", e);
		}
	}

	/**
	 * 通过原生脚本文件查询数据
	 * 
	 * @param path
	 * @param params
	 * @return
	 */
	public List<?> findByNativeFile(String path, Map<String, Object> params) {
		return findByNativeSql(readScriptFile(path), params);
	}

	/**
	 * 脚本文件定义
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-12-25
	 * @since 1.0
	 */
	public static final class Script {

		public static final String countByLoanStatus 			= "/script/count-by-loan-status.sql";
		public static final String countByLoanOverdue			= "/script/count-by-loan-overdue.sql";
		public static final String searchByLoan 				= "/script/search-by-loan.sql";
		public static final String countSearchByLoan 			= "/script/count-search-by-loan.sql";
		public static final String searchByInvestProfit 		= "/script/search-by-invest-profit.sql";
		public static final String countSearchByInvestProfit 	= "/script/count-search-by-invest-profit.sql";
		public static final String searchUser 					= "/script/search-user.sql";
		public static final String countSearchUser 				= "/script/count-search-user.sql";
		public static final String searchByLoanAudit 			= "/script/search-by-loan-audit.sql";
		public static final String countSearchByLoanAudit 		= "/script/count-search-by-loan-audit.sql";
		public static final String initData						= "/sql/h2/data.sql";
		public static final String searchByParameterSet 		= "/script/search-by-parameterset.sql";
		public static final String countSearchByParameterSet 	= "/script/count_search-by-parameterset.sql";

	}
	
	public void executeNative(String path){
		em.createNativeQuery(readScriptFile(path)).executeUpdate();
	}
}
