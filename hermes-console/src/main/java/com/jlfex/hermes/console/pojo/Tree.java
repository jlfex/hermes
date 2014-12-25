package com.jlfex.hermes.console.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.model.Navigation;

/**
 * 树型信息模型
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-28
 * @since 1.0
 */
public class Tree implements Serializable {

	private static final long serialVersionUID = 2125167020273901774L;
	
	private static final String TYPE_DIR	= "dir";
	private static final String TYPE_NODE	= "node";

	/** 编号 */
	private String id;
	
	/** 名称 */
	private String name;
	
	/** 代码 */
	private String code;
	
	/** 地址 */
	private String url;
	
	/** 目标 */
	private String target;
	
	/** 类型 */
	private String type;
	
	/** 下级 */
	private List<Tree> children = new LinkedList<Tree>();

	/**
	 * 读取编号
	 * 
	 * @return
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置编号
	 * 
	 * @param id
	 * @see #id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 读取名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取代码
	 * 
	 * @return
	 * @see #code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 * @see #code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 读取地址
	 * 
	 * @return
	 * @see #url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置地址
	 * 
	 * @param url
	 * @see #url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 读取目标
	 * 
	 * @return
	 * @see #target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 设置目标
	 * 
	 * @param target
	 * @see #target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 读取类型
	 * 
	 * @return
	 * @see #type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 读取下级
	 * 
	 * @return
	 * @see #children
	 */
	public List<Tree> getChildren() {
		return children;
	}
	
	/**
	 * 从导航转换
	 * 
	 * @param navigations
	 * @return
	 */
	public static List<Tree> fromNavigation(List<Navigation> navigations) {
		// 验证并初始化
		if (navigations == null) return null;
		List<Tree> trees = new ArrayList<Tree>(navigations.size());
		
		// 遍历处理数据
		for (Navigation navigation: navigations) {
			// 初始化并设置基本属性
			Tree tree = new Tree();
			tree.setId(navigation.getId());
			tree.setName(navigation.getName());
			tree.setCode(navigation.getCode());
			tree.setUrl(navigation.getFormattedPath());
			tree.setTarget(navigation.getTarget());
			trees.add(tree);
			
			// 子集判断并处理
			try {
				List<Navigation> children = navigation.getChildren();
				tree.setType((children.size() == 0) ? TYPE_NODE : TYPE_DIR);
				tree.getChildren().addAll(fromNavigation(children));
			} catch (Exception e) {
				Logger.debug("cannot get navigation children.");
			}
		}
		
		// 返回结果
		return trees;
	}
}
