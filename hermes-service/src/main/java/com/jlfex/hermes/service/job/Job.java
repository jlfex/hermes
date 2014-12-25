package com.jlfex.hermes.service.job;

import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Mailer;
import com.jlfex.hermes.common.exception.ServiceException;

/**
 * 任务
 * 
 * @author ultrafrog
 * @version 1.0, 2014-02-14
 * @since 1.0
 */
public abstract class Job {

	/** 运行 */
	public abstract Result run();
	
	/** 执行任务 */
	public void doJob() {
		try {
			// 初始化并执行
			Logger.info("start job '%s'", this.getClass().getSimpleName());
			Long start = System.currentTimeMillis();
			Result result = run();
			
			// 判断结果并进行处理
			if (!result.isSuccess()) throw new ServiceException(result.getMessage());
			if (result.isSendMail()) {
				Logger.info("sending mail...");
				Mailer mailer = new Mailer();
				mailer.setSubject("自动任务执行成功");
				mailer.setText("自动任务执行成功，任务名称：" + this.getClass().getSimpleName() + "，任务消息：" + result.getMessage());
				for (String address: App.config("address.job.notice").split(",")) mailer.addTo(address);
				mailer.send();
			}
			
			// 打印结束日志
			Logger.info("success do job '%s', spend '%s' millisecond.", this.getClass().getSimpleName(), System.currentTimeMillis() - start);
		} catch (Exception e) {
			// 处理异常
			ServiceException se = (e instanceof ServiceException) ? ServiceException.class.cast(e) : new ServiceException(e.getMessage(), e);
			Logger.error(se.getMessage(), se);
			
			// 发送邮件
			Mailer mailer = new Mailer();
			mailer.setSubject("自动任务执行失败");
			mailer.setText("自动任务执行失败，任务名称：" + this.getClass().getSimpleName() + "，异常代码：" + se.getCode() + "，任务消息：" + se.getMessage());
			for (String address: App.config("address.job.notice").split(",")) mailer.addTo(address);
			mailer.send();
			
			// 打印结束日志
			Logger.warn("failure do job '%s'", this.getClass().getSimpleName());
		}
	}
	
	/**
	 * 处理结果
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2014-02-18
	 * @since 1.0
	 */
	public static class Result {
		
		/** 是否成功 */
		private boolean success;
		
		/** 是否发送邮件 */
		private boolean sendMail;
		
		/** 消息 */
		private String message;
		
		/** 构造函数 */
		public Result() {}
		
		/**
		 * 构造函数
		 * 
		 * @param success
		 * @param sendMail
		 * @param message
		 */
		public Result(boolean success, boolean sendMail, String message) {
			this.success = success;
			this.sendMail = sendMail;
			this.message = message;
		}

		/**
		 * 读取是否成功
		 * 
		 * @return
		 * @see #success
		 */
		public boolean isSuccess() {
			return success;
		}

		/**
		 * 设置是否成功
		 * 
		 * @param success
		 * @see #success
		 */
		public void setSuccess(boolean success) {
			this.success = success;
		}

		/**
		 * 读取是否发送邮件
		 * 
		 * @return
		 * @see #sendMail
		 */
		public boolean isSendMail() {
			return sendMail;
		}

		/**
		 * 设置是否发送邮件
		 * 
		 * @param sendMail
		 * @see #sendMail
		 */
		public void setSendMail(boolean sendMail) {
			this.sendMail = sendMail;
		}

		/**
		 * 读取消息
		 * 
		 * @return
		 * @see #message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * 设置消息
		 * 
		 * @param message
		 * @see #message
		 */
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
