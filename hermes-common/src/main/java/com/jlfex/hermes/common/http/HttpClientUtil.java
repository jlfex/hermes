package com.jlfex.hermes.common.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Files;
import com.jlfex.hermes.common.utils.Strings;

/**
 * 
 * @author Administrator
 *
 */
public class HttpClientUtil {

	private static  HttpClient httpClient;
	private static  String key_store_client_path ;
	private static  String key_store_password ;
	private static  String key_store_trust_path ;
	private static  String key_store_trust_password;
	private static  boolean key_init_flag = false;
	private static  final  int  CONNECTION_TIMEOUT = 10000; 
	private static  final  int  SO_TIMEOUT = 20000;
	private static  final  String DIRECTORY = "/certificate/";
	private static  final  int  RSP_200 = 200;
	private static  final  int  RSP_400 = 400;
	
	static {
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT); //设置连接超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,SO_TIMEOUT);//设置读取超时
	}
	
	/**
	 * 初始化参数
	 * @param keyStoreName
	 * @param keyStorePwd
	 * @param trustStoreName
	 * @param trustStorePwd
	 */
	public static void initHttps(String keyStoreName,String keyStorePwd,String trustStoreName,String trustStorePwd){
		if(!key_init_flag){
			key_store_client_path = DIRECTORY+keyStoreName;
			key_store_password = keyStorePwd;
			key_store_trust_path = DIRECTORY + trustStoreName;
			key_store_trust_password = trustStorePwd;
			key_init_flag = true;
		}
	}

	/**
	 * https post请求
	 * @param url
	 * @param formParamMap
	 * @return
	 */
	public static String doPostHttps(String url,Map<String, String> formParamMap) throws  Exception {
		initSSLContext();
		HttpPost post = new HttpPost(url);
		StringBuilder stringBuilder = new StringBuilder();
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		if(formParamMap !=null){
			for(Map.Entry<String, String> entity: formParamMap.entrySet()){
				nameValuePairList.add(new BasicNameValuePair(entity.getKey(), entity.getValue())) ;
			}
		}
		post.setEntity(new UrlEncodedFormEntity(nameValuePairList, HermesConstants.CHARSET_UTF8));
		HttpResponse  response = httpClient.execute(post);
		HttpEntity respEntity = response.getEntity();
		int  responseCode = response.getStatusLine().getStatusCode();
		Logger.info("httpClient post 响应状态responseCode="+responseCode+", 请求 URL="+url);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(respEntity.getContent(), "UTF-8"));
		String text = null;
		if(responseCode == RSP_200 || responseCode == RSP_400){
			while ((text = bufferedReader.readLine()) != null) {
				stringBuilder.append(text);
			}
		}else{
		    throw new Exception("接口请求异常：接口响应状态="+responseCode);
		}
		bufferedReader.close();
		post.releaseConnection();
		return stringBuilder.toString();
	}
	/**
	 * https get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String doGetHttps(String url) throws  Exception {
		initSSLContext();
		StringBuilder stringBuilder = new StringBuilder();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		int responseCode = response.getStatusLine().getStatusCode();
		Logger.info("httpClient get 响应状态responseCode="+responseCode+", 请求 URL="+url);
		HttpEntity respEntity = response.getEntity();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(respEntity.getContent(), "UTF-8"));
		String text;
		if(responseCode == RSP_200 || responseCode == RSP_400){
			while ((text = bufferedReader.readLine()) != null) {
				stringBuilder.append(text);
			}
		}else{
			throw new Exception("接口请求异常：接口响应状态="+responseCode);
		}
		bufferedReader.close();
		httpGet.releaseConnection();
		return stringBuilder.toString();
	}
    /**
     * 初始化SSL连接
     */
	private static void initSSLContext() {
		SSLSocketFactory sslSocketFactory = getSSLSocketFactory(key_store_client_path, key_store_password, key_store_trust_path, key_store_trust_password);
		Scheme sch = new Scheme(HermesConstants.SCHEME_HTTPS, HermesConstants.HTTPS_PORT, sslSocketFactory);
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
	}
	
	/**
	 * 初始化SSLSocketFactory
	 * @param keyStoreFile 客户端证书
	 * @param keyStorePass 证书保护密码
	 * @param trustoreFile 服务端证书信任库
	 * @param trustorePass 信任库保护密码
	 * @return
	 */
	private static SSLSocketFactory getSSLSocketFactory(String keyStoreFile, String keyStorePass, String trustoreFile, String trustorePass) {
		SSLSocketFactory socketFactory = null;
		InputStream keyStoreIns = null;
		InputStream trustoreIns = null;
		try{
			keyStoreIns = Files.class.getResourceAsStream(keyStoreFile);
			trustoreIns = Files.class.getResourceAsStream(trustoreFile);
			KeyStore keyStore = KeyStore.getInstance(HermesConstants.KEY_STORE_TYPE_P12);
			KeyStore trustStore = KeyStore.getInstance(HermesConstants.KEY_STORE_TYPE_JKS);
			keyStore.load(keyStoreIns, keyStorePass.toCharArray());
			trustStore.load(trustoreIns, trustorePass.toCharArray());
			socketFactory = new SSLSocketFactory(keyStore, keyStorePass, trustStore);
		} catch (Exception e) {
			Logger.error("初始化SSLSocketFactory异常：",e);
		} finally {
			if(keyStoreIns!=null) {
				try {
					keyStoreIns.close();
				} catch (IOException e) {
					keyStoreIns = null;
				}
			}
			if(trustoreIns!=null) {
				try {
					trustoreIns.close();
				} catch (IOException e) {
					trustoreIns = null;
				}
			}
		}
		return socketFactory;
	}
	
	/**
	 * 解析 文件流响应
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static InputStream analyseFileResponse(HttpResponse response) throws Exception {
		return response.getEntity().getContent();
	}
	
	/**
	 * post请求公共参数创建
	 * @param methodName
	 * @param serialNo
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String>  buildPostCommonParam(String methodName, String serialNo) throws Exception{
		if(checkParamNull(methodName, serialNo)){
			return null;
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("method", methodName.trim());
		map.put("serial_no", serialNo);
		map.put("timestamp", Calendars.format(HermesConstants.FORMAT_19));
		map.put("format", HermesConstants.MESSAGE_FORMAT);
		map.put("v", HermesConstants.MESSAGE_VERSION);
		return map;
	}
	/**
	 * get请求公共参数创建
	 * @param methodName
	 * @param serialNo
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer buildGetCommonParam(String methodName, String serialNo) throws Exception{
		if(checkParamNull(methodName, serialNo)){
			return null;
		}
		StringBuffer  sbuff = new StringBuffer();
		sbuff.append("?").append("method=").append(methodName.trim()).append("&");
		sbuff.append("serial_no=").append(serialNo).append("&");
		sbuff.append("timestamp=").append( URLEncoder.encode(Calendars.format(HermesConstants.FORMAT_19),"UTF-8")).append("&");
		sbuff.append("format=").append(HermesConstants.MESSAGE_FORMAT).append("&");
		sbuff.append("v=").append(HermesConstants.MESSAGE_VERSION).append("&");
		return sbuff;
	}

	private static boolean checkParamNull(String methodName, String serialNo) {
		boolean flag = false;
		if(Strings.empty(methodName)){
			Logger.error("接口方法为空");
			flag =  true;
		}
		if(Strings.empty(serialNo)){
			Logger.error("流水号为空");
			flag =  true;
		}
		return flag;
	}
	/**
	 * 获取二进制文件流
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream doFileGetHttps(String url) throws  Exception {
		initSSLContext();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		int responseCode = response.getStatusLine().getStatusCode();
		Logger.info("httpClient get 响应状态responseCode="+responseCode+", 请求 URL="+url);
		HttpEntity respEntity = response.getEntity();
		if(responseCode == RSP_200 || responseCode == RSP_400){
			InputStream ins = respEntity.getContent();
			httpGet.releaseConnection();
			return ins;
		}else{
			httpGet.releaseConnection();
			throw new Exception("接口请求异常：接口响应状态="+responseCode);
		}
	}
	
	
}
