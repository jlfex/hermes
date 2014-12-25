package com.jlfex.hermes.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jlfex.hermes.common.exception.ServiceException;

/**
 * 文件工具
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-26
 * @since 1.0
 */
public abstract class Files {

	private static final Pattern PATTERN_EXT 	= Pattern.compile("^.*\\.([^.]+)$");
	private static final String PATH_MIMETYPES	= "/mimetypes.properties";
	
	private static Properties mimeTypes;
	
	/**
	 * 读取文件
	 * 
	 * @param path
	 * @return
	 */
	public static File getFile(String path) {
		return new File(Files.class.getResource(path).getPath());
	}
	
	/**
	 * 读取输入流
	 * 
	 * @param path
	 * @return
	 */
	public static InputStream getInputStream(String path) {
		return Files.class.getResourceAsStream(path);
	}
	
	/**
	 * 读取文件扩展名
	 * 
	 * @param file
	 * @return
	 */
	public static String getExt(String filename) {
		Matcher matcher = PATTERN_EXT.matcher(filename.toLowerCase());
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return "";
	}
	
	/**
	 * 读取扩展类型
	 * 
	 * @return
	 */
	public static Properties getMimeTypes() {
		if (mimeTypes == null) {
			try {
				mimeTypes = new Properties();
				mimeTypes.load(Files.class.getResourceAsStream(PATH_MIMETYPES));
			} catch (IOException e) {
				throw new ServiceException("cannot load '" + PATH_MIMETYPES + "'.", e);
			}
		}
		return mimeTypes;
	}
	
	/**
	 * 读取扩展类型
	 * 
	 * @param filename
	 * @return
	 */
	public static String getMimeType(String filename) {
		return getMimeTypes().getProperty(getExt(filename));
	}
}
