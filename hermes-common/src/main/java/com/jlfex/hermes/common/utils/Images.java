package com.jlfex.hermes.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;

/**
 * 图形工具
 * 
 * @author ultrafrog
 * @version 1.0, 2013-12-26
 * @since 1.0
 */
public abstract class Images {

	private static final String BASE64 = "data:%s;base64,%s";

	/**
	 * 转换图片
	 * 
	 * @param file
	 * @return
	 */
	public static String toBase64(File image) {
		InputStream is = null;
		try {
			is = new FileInputStream(image);
			byte[] bytes = new byte[(int) image.length()];
			is.read(bytes);
			return toBase64(Files.getMimeType(image.getName()), bytes);
		} catch (FileNotFoundException e) {
			throw new ServiceException("cannot convert '" + image.getName() + "' to base64", e);
		} catch (IOException e) {
			throw new ServiceException("cannot convert '" + image.getName() + "' to base64", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Logger.warn(e.getMessage());
				}
			}
		}
	}

	/**
	 * 转换图片
	 * 
	 * @param mimeType
	 * @param bytes
	 * @return
	 */
	public static String toBase64(String mimeType, byte[] bytes) {
		return String.format(BASE64, mimeType, new String(Base64.encodeBase64(bytes)));
	}

	/**
	 * Resize an image
	 * 
	 * @param originalImage
	 *            The image file
	 * @param to
	 *            The destination file
	 * @param w
	 *            The new width (or -1 to proportionally resize) or the maxWidth
	 *            if keepRatio is true
	 * @param h
	 *            The new height (or -1 to proportionally resize) or the
	 *            maxHeight if keepRatio is true
	 * @param keepRatio
	 *            : if true, resize will keep the original image ratio and use w
	 *            and h as max dimensions
	 */
	public static void resize(File originalImage, File to, int w, int h, boolean keepRatio) {
		try {
			BufferedImage source = ImageIO.read(originalImage);
			int owidth = source.getWidth();
			int oheight = source.getHeight();
			double ratio = (double) owidth / oheight;

			int maxWidth = w;
			int maxHeight = h;

			if (w < 0 && h < 0) {
				w = owidth;
				h = oheight;
			}
			if (w < 0 && h > 0) {
				w = (int) (h * ratio);
			}
			if (w > 0 && h < 0) {
				h = (int) (w / ratio);
			}

			if (keepRatio) {
				h = (int) (w / ratio);
				if (h > maxHeight) {
					h = maxHeight;
					w = (int) (h * ratio);
				}
				if (w > maxWidth) {
					w = maxWidth;
					h = (int) (w / ratio);
				}
			}

			String mimeType = "image/jpeg";
			if (to.getName().endsWith(".png")) {
				mimeType = "image/png";
			}
			if (to.getName().endsWith(".gif")) {
				mimeType = "image/gif";
			}
			// out
			BufferedImage dest = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Image srcSized = source.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			Graphics graphics = dest.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, w, h);
			graphics.drawImage(srcSized, 0, 0, null);

			ImageWriter writer = ImageIO.getImageWritersByMIMEType(mimeType).next();
			ImageWriteParam params = writer.getDefaultWriteParam();
			FileImageOutputStream toFs = new FileImageOutputStream(to);
			writer.setOutput(toFs);
			IIOImage image = new IIOImage(dest, null, null);
			writer.write(null, image, params);
			toFs.flush();
			toFs.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Crop an image
	 * 
	 * @param originalImage
	 *            The image file
	 * @param to
	 *            The destination file
	 * @param x1
	 *            The new x origin
	 * @param y1
	 *            The new y origin
	 * @param width
	 *            The new width
	 * @param height
	 *            The new height
	 * @param imgWidth
	 *            The widht of img
	 * @param imgHeight
	 *            The height of img
	 */
	public static Map<String, String> crop(MultipartFile originalImage, int x1, int y1, int width, int height, int imgWidth, int imgHeight) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(originalImage.getBytes());
			MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
			BufferedImage source = ImageIO.read(mciis);
			int owidth = source.getWidth();// 图片原始宽度
			int oheight = source.getHeight();// 图片原始长度
			double ratioW = (double) width / imgWidth; // 原始图片与前台图片显示宽度的比例
			double ratioH = (double) height / imgHeight;

			int cutW = (int) (owidth * ratioW);// 裁剪图片的真实宽度
			int cutH = (int) (oheight * ratioH);

			double ratioX = (double) x1 / imgWidth; // x坐标所在位置的比例
			double ratioY = (double) y1 / imgHeight;

			int xo = (int) (owidth * ratioX);// 图片裁剪开始的真实X坐标
			int yo = (int) (oheight * ratioY);
			// crop 图片
			BufferedImage dest = new BufferedImage(cutW, cutH, BufferedImage.TYPE_INT_RGB);
			Image croppedImage = source.getSubimage(xo, yo, cutW, cutH);
			Graphics graphics = dest.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, cutW, cutH);
			graphics.drawImage(croppedImage, 0, 0, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(dest, Files.getExt(originalImage.getOriginalFilename()), baos);
			byte[] bytes = baos.toByteArray();
			String avatar_lg = String.format(BASE64, Files.getMimeType(originalImage.getOriginalFilename()), new String(Base64.encodeBase64(bytes)));

			// resize图片
			BufferedImage destResize = new BufferedImage(46, 46, BufferedImage.TYPE_INT_RGB);
			Image resizeImage = croppedImage.getScaledInstance(46, 46, Image.SCALE_SMOOTH);
			Graphics graphicsResize = destResize.getGraphics();
			graphicsResize.setColor(Color.WHITE);
			graphicsResize.fillRect(0, 0, 46, 46);
			graphicsResize.drawImage(resizeImage, 0, 0, null);
			ByteArrayOutputStream baosResize = new ByteArrayOutputStream();
			ImageIO.write(destResize, Files.getExt(originalImage.getOriginalFilename()), baosResize);
			byte[] bytesResize = baosResize.toByteArray();
			String avatar = String.format(BASE64, Files.getMimeType(originalImage.getOriginalFilename()), new String(Base64.encodeBase64(bytesResize)));

			Map<String, String> map = new HashMap<String, String>();
			map.put("avatar_lg", avatar_lg);
			map.put("avatar", avatar);
			return map;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 获取压缩图片后的 base64码
	 * 
	 * @param originalImage
	 * @param x1
	 * @param y1
	 * @param width
	 * @param height
	 * @return
	 */
	public static String resizeImageToBase64(MultipartFile originalImage, int x1, int y1, int width, int height) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(originalImage.getBytes());
			MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
			BufferedImage source = ImageIO.read(mciis);
			int owidth = source.getWidth();// 图片原始宽度
			int oheight = source.getHeight();// 图片原始长度
			int imgWidth = source.getWidth();
			int imgHeight = source.getHeight();
			double ratioW = (double) width / imgWidth; // 原始图片与前台图片显示宽度的比例
			double ratioH = (double) height / imgHeight;
			if (ratioW >= 1) {
				ratioW = 1;
			}
			if (ratioH >= 1) {
				ratioH = 1;
			}
			int cutW = (int) (owidth * ratioW);// 裁剪图片的真实宽度
			int cutH = (int) (oheight * ratioH);
			double ratioX = (double) x1 / imgWidth; // x坐标所在位置的比例
			double ratioY = (double) y1 / imgHeight;
			int xo = (int) (owidth * ratioX);// 图片裁剪开始的真实X坐标
			int yo = (int) (oheight * ratioY);
			BufferedImage dest = new BufferedImage(cutW, cutH, BufferedImage.TYPE_INT_RGB);
			Image croppedImage = source.getSubimage(xo, yo, cutW, cutH);
			Graphics graphics = dest.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, cutW, cutH);
			graphics.drawImage(croppedImage, 0, 0, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(dest, Files.getExt(originalImage.getOriginalFilename()), baos);
			byte[] bytes = baos.toByteArray();
			return String.format(BASE64, Files.getMimeType(originalImage.getOriginalFilename()), new String(Base64.encodeBase64(bytes)));
		} catch (Exception e) {
			Logger.error("压缩图片异常：", e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * 获取压缩图片后的 base64码
	 * @param originalImage
	 * @param x1
	 * @param y1
	 * @param width
	 * @param height
	 * @return
	 */
	public static String resizeImageToBase64(MultipartFile originalImage, int x1, int y1, int width, int height) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(originalImage.getBytes());
			MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
			BufferedImage source = ImageIO.read(mciis);
			int owidth = source.getWidth();// 图片原始宽度
			int oheight = source.getHeight();// 图片原始长度
			int imgWidth = source.getWidth();
			int imgHeight = source.getHeight();
			double ratioW = (double) width / imgWidth; // 原始图片与前台图片显示宽度的比例
			double ratioH = (double) height / imgHeight;
			if(ratioW >= 1){
				ratioW = 1;
			}
			if(ratioH >= 1){
				ratioH = 1;
			}
			int cutW = (int) (owidth * ratioW);// 裁剪图片的真实宽度
			int cutH = (int) (oheight * ratioH);
			double ratioX = (double) x1 / imgWidth; // x坐标所在位置的比例
			double ratioY = (double) y1 / imgHeight;
			int xo = (int) (owidth * ratioX);// 图片裁剪开始的真实X坐标
			int yo = (int) (oheight * ratioY);
			BufferedImage dest = new BufferedImage(cutW, cutH, BufferedImage.TYPE_INT_RGB);
			Image croppedImage = source.getSubimage(xo, yo, cutW, cutH);
			Graphics graphics = dest.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, cutW, cutH);
			graphics.drawImage(croppedImage, 0, 0, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(dest, Files.getExt(originalImage.getOriginalFilename()), baos);
			byte[] bytes = baos.toByteArray();
			return String.format(BASE64, Files.getMimeType(originalImage.getOriginalFilename()), new String(Base64.encodeBase64(bytes)));
		} catch (Exception e) {
			Logger.error("压缩图片异常：",e);
			throw new RuntimeException(e);
		}

	}
	/**
	 * @param args
	 */
	public static void main(String... args) {
		System.out.println(toBase64(Files.getFile("unionpay.png")));
	}
}
