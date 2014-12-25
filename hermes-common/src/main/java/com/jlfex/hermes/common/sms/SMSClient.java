package com.jlfex.hermes.common.sms;

import java.util.Collection;

import wzsoft.sms.client.AccountReq;
import wzsoft.sms.client.AccountRsp;
import wzsoft.sms.client.Protocol;
import wzsoft.sms.client.ProtocolFactory;
import wzsoft.sms.client.RecoverableProtocolException;
import wzsoft.sms.client.SmsClient;
import wzsoft.sms.client.SmsDRReq;
import wzsoft.sms.client.SmsDRRsp;
import wzsoft.sms.client.SmsEventListener;
import wzsoft.sms.client.SmsMOReq;
import wzsoft.sms.client.SmsMORsp;
import wzsoft.sms.client.SmsMTReq;
import wzsoft.sms.client.SmsMTRsp;

import com.jlfex.hermes.common.Logger;

public class SMSClient implements SmsEventListener {
	private SmsClient client = null;

	private String username;

	private String password;

	private String serverAddress;

	private int port;

	private boolean running = false;

	public int initialize(String username, String password, String serverAddress, int port) {
		// 取得客户端生成工厂
		ProtocolFactory factory = ProtocolFactory.getFactory();
		// 生成客户端
		client = factory.getClient(Protocol.SOMP);

		// 设置客户端参数
		client.setUsername(username);
		client.setPassword(password);
		client.setServerAddress(serverAddress);
		client.setPort(port);

		// 设置回调接口
		client.setListener(this);

		// 使用同步发送请使用该句代码
		client.setSendTimeout(30000);

		// 设置空闲时间
		client.setIdleTimeout(60000);
		client.setWindowSize(30);
		client.setWindowTimeout(30000);
		while (true) {
			try {
				client.bind();
				break;
			} catch (Throwable t) {
				t.printStackTrace();
			}
			System.out.println("Begin bind fail, retrying...");
			try {
				Thread.sleep(5000);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		this.username = username;
		this.password = password;
		this.serverAddress = serverAddress;
		this.port = port;

		running = true;
		return 0;
	}

	public int reInitialize() {
		return initialize(username, password, serverAddress, port);
	}
	@Override
	public void onAccountRsp(AccountReq arg0, AccountRsp arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBinding() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBound(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSmsDR(SmsDRReq arg0, SmsDRRsp arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSmsMO(SmsMOReq req, SmsMORsp arg1) {
		// process mo message, 处理手机上行信息

		String content = null;

		try {
			String codeType = "GB2312";
			if (req.getDataCoding() == 8) {
				codeType = "UTF-16BE";
			}
			content = new String(req.getContent(), codeType);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void onSmsMTRsp(Object key, SmsMTRsp arg1) {
		if (key != null) {
			SmsMTReq req = (SmsMTReq) key; // req 是此结果对应的短信内容对象

			System.out.println("Catalogue: " + req.getCatalogue()); // 客户端编号，即发送方定义的此条短信的唯一编号，根据此编号更新发送结果
		}

	}

	@Override
	public void onUnbound(Collection arg0) {
		if (running) { // 仍在运行，重新绑定
			System.out.println("onUnbound, rebouding....");
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			reInitialize();
		} else { // 已停止
			System.out.println("onUnbound");
		}
	}

	public int send(long msgId, String number, String content) throws Exception {

		// 生成消息实例
		SmsMTReq req = (SmsMTReq) client.createReq(SmsMTReq.class);

		// 设置客户端编号，即发送方定义的此条短信的唯一编号
		req.setCatalogue(msgId);

		// 设置内容，跟下面的编码方式对应
		// 如果是wappush内容的格式如下代码所示(内容总长不能超过112个字节)
		// content="网页搜索http://www.google.com.hk/"
		req.setContent(content.getBytes("GB2312"));

		// 英文格式0,中文请填写15(GB2312);如果日文/韩文请转成Unicode,并填写8
		// 如果是wappush如下代码
		// req.setDataCoding(128)
		req.setDataCoding(15);

		// 设置发送的目标号码;多个用分号分割
		req.setDestAddr(number);
		req.setSourceAddr("0000"); // 源地址必须以企业特服号开头,否则会被服务器忽略;比如此例 88 + 1234

		SmsMTRsp rsp = null;

		boolean isOk = false;
		for (int i = 0; i < 3; i++) { // 尝试发送3次
			try {
				rsp = client.send(req, req);
				isOk = true;
				break;
			} catch (RecoverableProtocolException re) {
				System.out.println(re.getMessage());
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				isOk = true;
				break;
			}
		}
		if (!isOk) { // 连续发送3次都失败，可能连接失效，重新连接
			System.out.println("close, rebouding....");
			close();
			reInitialize();
		}
		if (rsp == null) { // 提交失败
			System.out.println("send fail");
			Logger.error("短信发送失败,参数： msgId=" + msgId + ",number=" + number + ",content=" + content);
			return -1;
		} else if (!isOk) { // 重新绑定，需重新提交此条短信
			System.out.println("need reSend");
			Logger.error("需重新提交此条短信");
			return -2;
		} else { // 提交完成
			System.out.println(rsp.getMessageID() + "; " + rsp.getResult());
			Logger.info("短信发送成功,返回参数---------------------->" + rsp.getMessageID() + "; " + rsp.getResult());
			return rsp.getResult();
			// return 1;
		}
	}

	public void close() {
		// 关闭客户端
		running = false;
		client.close();
	}

	public void launch2(String[] args) throws Exception {
		// 如果仅仅接收状态报告和上行信息,请看以下代码
		client.bind();
		// 查询余额信息
		Object req = client.createReq(AccountReq.class);
		client.sendReq(req);
		// 等待信息上行
		Thread.sleep(27000);
		// 关闭客户端
		client.close();
	}

	public static int parseInt(String value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			int v = Integer.parseInt(value);
			return v;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return defaultValue;
		}
	}

}
