package com.jlfex.hermes.common;

/**
 * 调用信息
 */
public class Caller {

	/** 类型名称 */
	private String className;
	
	/** 方法名称 */
	private String methodName;
	
	/**
	 * 构造函数
	 * 
	 * @param className
	 * @param methodName
	 */
	public Caller(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}

	/**
	 * 读取类型名称
	 * 
	 * @return
	 * @see #className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 设置类型名称
	 * 
	 * @param className
	 * @see #className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 读取方法名称
	 * 
	 * @return
	 * @see #methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * 设置方法名称
	 * 
	 * @param methodName
	 * @see #methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * 获取调用信息
	 * 
	 * @param level
	 * @return
	 */
	public static Caller caller(int level) {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		StackTraceElement caller = elements[level];
		return new Caller(caller.getClassName(), caller.getMethodName());
	}
}
