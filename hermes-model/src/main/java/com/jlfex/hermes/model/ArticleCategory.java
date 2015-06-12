package com.jlfex.hermes.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 文章分类信息模型
 */
@Entity
@Table(name = "hm_article_category",uniqueConstraints = { @UniqueConstraint(columnNames = "code") })
@JsonIgnoreProperties({ "parent", "children" })
public class ArticleCategory extends Model {

	private static final long serialVersionUID = -2372084266057798310L;

	/** 上级 */
	@ManyToOne
	@JoinColumn(name = "parent")
	private ArticleCategory parent;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 代码 */
	@Column(name = "code")
	private String code;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/** 子集 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private List<ArticleCategory> children = new LinkedList<ArticleCategory>();

	
	/** 分类级别 */
	@Column(name = "level_num")
	private String level;

	/**
	 * 读取上级
	 * 
	 * @return
	 * @see #parent
	 */
	public ArticleCategory getParent() {
		return parent;
	}

	/**
	 * 设置上级
	 * 
	 * @param parent
	 * @see #parent
	 */
	public void setParent(ArticleCategory parent) {
		this.parent = parent;
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
	 * 读取状态
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取子集
	 * 
	 * @return
	 * @see #children
	 */
	public List<ArticleCategory> getChildren() {
		return children;
	}

	@Transient
	public Integer getArticleCount() {
		if (children != null) {
			return children.size();
		}
		return 0;
	}

	/**
	 * 读取关系集合
	 * 
	 * @return
	 * @see #references
	 */

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
