package com.jlfex.hermes.main.freemark;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Strings;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 字符串模版加载器
 */
public class ModelLoader implements TemplateLoader {

	private static Configuration configuration;
	private static String classPath;
	static {
	    try {
	    	classPath = ModelLoader.class.getResource("/").getPath();
		    configuration = new Configuration();
			configuration.setDirectoryForTemplateLoading(new File(classPath + "templates"));
			configuration.setObjectWrapper(new DefaultObjectWrapper());
			configuration.setDefaultEncoding("UTF-8");
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		} catch (IOException e) {
			Logger.error("配置模板路径异常：", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see freemarker.cache.TemplateLoader#findTemplateSource(java.lang.String)
	 */
	@Override
	public Object findTemplateSource(String name) throws IOException {
		return new StringReader(Strings.empty(name, ""));
	}
	
	/* (non-Javadoc)
	 * @see freemarker.cache.TemplateLoader#closeTemplateSource(java.lang.Object)
	 */
	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		StringReader.class.cast(templateSource).close();
	}
	
	/* (non-Javadoc)
	 * @see freemarker.cache.TemplateLoader#getLastModified(java.lang.Object)
	 */
	@Override
	public long getLastModified(Object templateSource) {
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see freemarker.cache.TemplateLoader#getReader(java.lang.Object, java.lang.String)
	 */
	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return Reader.class.cast(templateSource);
	}
	
	/**
	 * 解析模版
	 * 
	 * @param html
	 * @param root
	 * @return
	 */
	public static String process(String html, Map<String, Object> root) {
		try {
			Template template = configuration.getTemplate(html);
			StringWriter writer = new StringWriter();
			template.process(root, writer);
			return writer.toString();
		} catch (IOException e) {
			throw new ServiceException("cannot parse template.", "exception.freemarker.process", e);
		} catch (TemplateException e) {
			throw new ServiceException("cannot parse template.", "exception.freemarker.process", e);
		}
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void main(String... args) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("name", "ufrog");
		root.put("aaaa", "go");
		System.out.println(ModelLoader.process("hello: ${name}, ${aaaa}", root));
	}
}
