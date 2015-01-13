package com.jlfex.hermes.common.identity;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Cryptos;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 身份认证
 * 
 * 
 * @author Aether
 * @version 1.0, 2014-2-19
 * @since 1.0
 */
public class Identity {
	public static String verify(String param) {
		if (Strings.empty(param)) {
			return "-1";
		}
		String xmlStr = proxy(param);
		if (Strings.empty(xmlStr)) {
			return "-1";
		}
		Document doc = creXmlDoc(xmlStr);
		if (doc == null) {
			return "-2";
		}
		return parseXML(doc);
	}

	public static String parseXML(Document doc) {
		List statusList = doc.selectNodes("data/policeCheckInfos/policeCheckInfo/compStatus");
		if ((statusList == null) || (statusList.size() == 0)) {
			return "";
		}
		List nameList = doc.selectNodes("data/policeCheckInfos/policeCheckInfo/name");
		List identitycardList = doc.selectNodes("data/policeCheckInfos/policeCheckInfo/identitycard");
		String name = ((Element) nameList.get(0)).getText();
		String cardId = ((Element) identitycardList.get(0)).getText();
		Element e = (Element) statusList.get(0);
		String compStatusCode = e.getText();
		int status;
		try {
			status = Integer.parseInt(compStatusCode);
			if (status == 3) {
				Logger.info("The result of compare is consistent");
				return "";
			} else if (status == 2) {
				Logger.info("The result of compare is inconsistent");
				return "";
			} else if (status == 1) {
				Logger.info("The result of comapre is inexistence");
				return "";
			} else {
				Logger.info("The return data of XML is error");
				return "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	private static String proxy(String param) {
		try {
			// 获得WebServices的代理对象
			QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy();
			proxy.setEndpoint("http://gboss.id5.cn/services/QueryValidatorServices");
			QueryValidatorServices service = proxy.getQueryValidatorServices();
			// 对调用的参数进行加密
			String key = "12345678";
			String userName = Cryptos.encryptDES("zhengdajiekou", key); // 用户名
			String password = Cryptos.encryptDES("zhengdajiekou_CDG3_@l(", key);// 密码
			System.setProperty("javax.net.ssl.trustStore", "keystore");
			// 查询返回结果
			String resultXML = "";
			/*------身份信息核查比对-------*/
			String datasource = Cryptos.encryptDES("1A020201", key); // 数据类型
			resultXML = service.querySingle(userName, password, datasource, Cryptos.encryptDES(param, key));

			resultXML = Cryptos.encryptDES(resultXML, key);
			return resultXML;
		} catch (Exception e) {
			Logger.info("WebService接口调用错误,错误信息：" + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	private static Document creXmlDoc(String xmlStr) {
		if (Strings.empty(xmlStr)) {
			throw new RuntimeException("参数不能为空");
		}
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
			Element rootElement = document.getRootElement();
			String getXMLEncoding = document.getXMLEncoding();
			String rootname = rootElement.getName();
			System.out.println("getXMLEncoding>>>" + getXMLEncoding + ",rootname>>>" + rootname);
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML字符集编码 */
			format.setEncoding("UTF-8");
			Logger.info(xmlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public static void main(String[] args) {
		String res = verify("季惠,32128319880721504X");
	}
}
