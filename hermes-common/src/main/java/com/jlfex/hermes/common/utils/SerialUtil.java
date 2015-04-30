package com.jlfex.hermes.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class SerialUtil
{
  private static Random random = new Random();
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
  
  public static String generateGUID() {
    long l1 = System.currentTimeMillis();
    long l2 = random.nextLong();

    StringBuffer localStringBuffer = new StringBuffer("www.cfca.com.cn");
    localStringBuffer.append(l1).append(l2);

    MessageDigest localMessageDigest = null;
    try {
      localMessageDigest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
      localNoSuchAlgorithmException.printStackTrace();
    }
    localMessageDigest.update(localStringBuffer.toString().getBytes());
    return Strings.bytes2hex(localMessageDigest.digest());
  }

  public static synchronized String getTxNo20() {
    String str = simpleDateFormat.format(Calendar.getInstance().getTime());
    return str + RandomNumberUtil.getRandomNumber(10);
  }

  public static synchronized String getTxNo16() {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMdd");
    String str = localSimpleDateFormat.format(Calendar.getInstance().getTime());
    return str + RandomNumberUtil.getRandomNumber(10);
  }
  
  public static synchronized String getSerialByDate10AndRandomLen(int randomNumberLen) {
	    String str = simpleDateFormat.format(Calendar.getInstance().getTime());
	    return str + RandomNumberUtil.getRandomNumber(randomNumberLen);
  }
}