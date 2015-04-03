package com.jlfex.hermes.common;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 处理结果
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-05
 * @since 1.0
 */
public class Result<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 7819032131690945024L;

	/** 类型 */
	private Type type;

	/** 消息列表 */
	private List<String> messages;

	/** 数据 */
	private T data;

	/**
	 * 构造函数
	 */
	public Result() {
		this.messages = new LinkedList<String>();
	}

	/**
	 * 构造函数
	 * 
	 * @param type
	 * @param message
	 * @param data
	 */
	public Result(Type type, String message, T data) {
		this();
		this.type = type;
		this.messages.add(message);
		this.data = data;
	}

	/**
	 * 构造函数
	 * 
	 * @param type
	 * @param message
	 */
	public Result(Type type, String message) {
		this(type, message, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param type
	 */
	public Result(Type type) {
		this();
		this.type = type;
	}

	/**
	 * 读取类型
	 * 
	 * @return
	 * @see #type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 读取类型名称
	 * 
	 * @return
	 */
	public String getTypeName() {
		return type.toString();
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 * @see #type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 读取消息列表
	 * 
	 * @return
	 * @see #messages
	 */
	public List<String> getMessages() {
		return messages;
	}

	/**
	 * 读取第一条消息
	 * 
	 * @return
	 */
	public String getFirstMessage() {
		return (messages.size() >= 1) ? messages.get(0) : "";
	}

	/**
	 * 添加消息
	 * 
	 * @param message
	 */
	public void addMessage(String message) {
		messages.add(message);
	}

	/**
	 * 添加消息
	 * 
	 * @param index
	 * @param message
	 */
	public void addMessage(Integer index, String message) {
		messages.add(index, message);
	}

	/**
	 * 读取数据
	 * 
	 * @return
	 * @see #data
	 */
	public T getData() {
		return data;
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 * @see #data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	public boolean success() {
		return Type.SUCCESS.equals(type);
	}

	/**
	 * 结果标识<br>
	 * 用于判断是否为结果
	 * 
	 * @return
	 */
	public boolean getResult() {
		return true;
	}

	/**
	 * 类型
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2013-12-05
	 * @since 1.0
	 */
	public static enum Type {
		SUCCESS("success"), WARNING("warning"), FAILURE("failure"), CELLPHNOE_NOTAUTH("cellphone_notauth"), NAME_NOTAUTH("name_notauth"), BANKCARD_NOTAUTH("bankcard_notauth"),
		/**
		 * 余额不足
		 */
		BALANCE_INSUFFICIENT("balance_insufficient"),
		/**
		 * 代扣处理中
		 */
		WITHHOLDING_PROCESSING("withholding_processing");

		/** 名称 */
		private String name;

		/**
		 * 构造函数
		 * 
		 * @param name
		 */
		private Type(String name) {
			this.name = name;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return name;
		}
	}
}
