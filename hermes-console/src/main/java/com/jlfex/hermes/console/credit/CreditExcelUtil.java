package com.jlfex.hermes.console.credit;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.console.pojo.CreditInfoVo;
import com.jlfex.hermes.console.pojo.RepayPlanVo;

public class CreditExcelUtil {
	
	public static final String ERROR_KIND = "单元格格式错误, ";
	public static final String ERROR_EMPTY = "值为空";
    public static final String ERROR_DATE_FORMAT_EXP = "时间格式化异常";
	public static final String VAR_PERCENT = "%";
	public static final String FORMAT_TXT = "请设置为文本格式";
	public static final String FORMAT_NUMBER = "请设置为数字格式";
	public static final String FORMAT_NUMBER_OR_TXT = "请设置为文本格式或数字格式";
	public static final String FORMAT_RATE = "请设置为文本格式:如 10% 格式的字符串,利率值0-100";
	public static final String FORMAT_STR_DATE = "请设置为文本格式:如 yyyy-mm-dd 格式的字符串";
	public static final String SHEET_CREIDT_NAME = "债权信息";
	public static final String SHEET_REPAY_NAME = "还款计划表";
	public static final String CREDIT_KIND = "信用,质押,不动产抵押,动产抵押,应收账款,票据";  //债权类型
	public static final String CREDITOR_CERTIFICATE_KIND = "身份证,组织机构代码";  //借款人证件类型
	public static final String IDENTITY_CARD =  "身份证";
	public static final String ORGANIZATION_CODE =  "组织机构代码";
	public static final String CREDIT_REPAY_WAY = "等额本息";
	
	/**
	 * 债权导入 Excel 文件解析
	 * @param fileName
	 * @param fileInp
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> analysisExcel(String fileName, FileInputStream fileInp)throws Exception{	
		Map<String,Object> map= null;
		if(Strings.empty(fileName) || fileInp == null){
			throw new Exception("上传文件名称为空");
		}
		fileName = fileName.trim();
		if ("xlsx".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".") + 1))) {
			map=analysisExcel2007(fileInp,fileName);// 调用2007版Excel方法
		}else{
			throw new Exception("文件格式不支持，请下载模板文件");
		}
		return map;
	}
	/**
	 * 解析sheet 页数据 
	 * @param fileInp
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> analysisExcel2007(FileInputStream fileInp,String fileName) throws Exception{
		boolean sheet_first_exists = false;
		boolean sheet_second_exists = false;
		Map<String,Object> map = new HashMap<String,Object>();
		XSSFWorkbook xssf = new XSSFWorkbook(fileInp);
		for (int numSheet = 0; numSheet < xssf.getNumberOfSheets(); numSheet++) {
			if(SHEET_CREIDT_NAME.equals(xssf.getSheetName(numSheet))){
				sheet_first_exists = true;
			}
			if(SHEET_REPAY_NAME.equals(xssf.getSheetName(numSheet))){
				sheet_second_exists = true;
			}
		}
		if(!sheet_first_exists){
			throw new Exception(SHEET_CREIDT_NAME+"不存在,无法解析");
		}
		if(!sheet_second_exists){
			throw new Exception(SHEET_REPAY_NAME+"不存在,无法解析");
		}
		//1:获取债权信息Sheet页数据
		List<CreditInfoVo> creditList=creditSheetExcel2007(fileInp,fileName,xssf,xssf.getSheetName(0));
		map.put("creditList",creditList);
		//2:获取还款Sheet页数据
		List<RepayPlanVo> repayList=repaySheetExcel2007(fileInp,fileName,xssf,xssf.getSheetName(1));
		map.put("repayList",repayList);
		return map;
	}
	
	    /**
	     * 债权信息2007Excel
	     * @param fileInp
	     * @param xssf
	     * @param sheetName
	     * @return
	     * @throws Exception
	     */
		public static List<CreditInfoVo> creditSheetExcel2007(FileInputStream fileInp,String fileName, XSSFWorkbook xssf,String sheetName) throws Exception{
			List<CreditInfoVo> creditorList = new ArrayList<CreditInfoVo>();
			XSSFSheet sheet = xssf.getSheet(sheetName);
			for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
				boolean errFlag = false;
				String cellName = "";
				StringBuilder errMsg = new StringBuilder("第"+rowNumber+"行：");
				XSSFRow xssRow = sheet.getRow(rowNumber);
				if (xssRow == null){
					errMsg.append(ERROR_EMPTY);
					Logger.warn(errMsg.toString()+" 跳过处理。");
					continue;
				}
				CreditInfoVo vo = new CreditInfoVo();
				cellName = "_债权人编号:";
				XSSFCell cell0= xssRow.getCell(0);  //债权人编号
				if(cell0 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCreditorNo(cell0.getStringCellValue().trim());
				}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					vo.setCreditorNo((""+cell0.getNumericCellValue()).trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_NUMBER_OR_TXT);
					errFlag = true;
				}
				cellName = "_债权编号:";
				XSSFCell cell1=xssRow.getCell(1); //债权编号
				if(cell1.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCreditCode(cell1.getStringCellValue().trim());
				}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					vo.setCreditCode((""+cell1.getNumericCellValue()).trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
					errFlag = true;
				}
				cellName = "_债权类型:";
				XSSFCell cell2=xssRow.getCell(2); //债权类型
				if(cell2 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell2.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCreditKind(cell2.getStringCellValue().trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
					errFlag = true;
				}
				cellName = "_借款人:";
				XSSFCell cell3=xssRow.getCell(3);  //借款人
				if(cell3 == null ){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell3.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setBorrower(cell3.getStringCellValue().trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
					errFlag = true;
				}
				cellName = "_借款人证件类型:";
				XSSFCell cell4=xssRow.getCell(4);//借款人证件类型
				if(cell4 == null ){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell4.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCertType(cell4.getStringCellValue().trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
					errFlag = true;
				}
				cellName = "_借款人证件号码:";
				XSSFCell cell5=xssRow.getCell(5);//借款人证件号码
				if(cell5 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell5.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCertificateNo(cell5.getStringCellValue().trim());
				}else if(cell5.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					vo.setCertificateNo((""+cell5.getNumericCellValue()).trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
					errFlag = true;
				}
				cellName = "_行业:";
				XSSFCell cell6=xssRow.getCell(6);//行业
			    if(cell6 !=null && (cell6.getCellType() == XSSFCell.CELL_TYPE_STRING)){
					vo.setWorkType((""+cell6.getRichStringCellValue()).trim());
				}
			   
			    cellName = "_省份:";
				XSSFCell cell7=xssRow.getCell(7);//省份
				if(cell7!=null && (cell7.getCellType() == XSSFCell.CELL_TYPE_STRING)){
					vo.setProvince((""+cell7.getRichStringCellValue()).trim());
				}
				
				cellName = "_城市";
				XSSFCell cell8=xssRow.getCell(8);//城市
				if(cell8!=null && (cell8.getCellType() == XSSFCell.CELL_TYPE_STRING)){
					vo.setCity((""+cell8.getRichStringCellValue()).trim());
				}
				
				cellName = "_借款金额:";
				XSSFCell cell9=xssRow.getCell(9);//借款金额
				if(cell9 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell9.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					vo.setAmount(""+cell9.getNumericCellValue());
				}else if(cell9.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setAmount(cell9.getStringCellValue().trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_NUMBER_OR_TXT);
					errFlag = true;
				}
				cellName = "_年利率:";
				XSSFCell cell10=xssRow.getCell(10);//年利率
				if(cell10 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell10.getCellType()==XSSFCell.CELL_TYPE_STRING){
					vo.setRate(cell10.getStringCellValue().trim());
				}else if(cell10.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					vo.setRate(""+cell10.getNumericCellValue());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_RATE);
					errFlag = true;
				}
				cellName = "_借款期限:";
				XSSFCell cell11=xssRow.getCell(11);//借款期限
				if(cell11 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell11.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setPeriod(cell11.getStringCellValue().trim());
				}else if(cell11.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					vo.setPeriod((""+cell11.getNumericCellValue()).trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
					errFlag = true;
				}
				cellName = "_借款用途:";
				XSSFCell cell12=xssRow.getCell(12);//借款用途
				if(cell12 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell12.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setPurpose(cell12.getStringCellValue().trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
					errFlag = true;
				}
				cellName = "_还款方式:";
				XSSFCell cell13=xssRow.getCell(13);//还款方式
				if(cell13 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell13.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setPayType(cell13.getStringCellValue().trim());
				}else{
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
					errFlag = true;
				}
				cellName = "_债权到期日:";
				XSSFCell cell14=xssRow.getCell(14);//债权到期日
				if(cell14 == null ){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell14.getCellType() == XSSFCell.CELL_TYPE_STRING){
					String val = cell14.getStringCellValue().trim();
					vo.setDeadTime(val.trim());
				}else {
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_STR_DATE);
					errFlag = true;
				}
				cellName = "_放款日期:";
				XSSFCell cell15=xssRow.getCell(15);//放款日期
				if(cell15 == null){
					errMsg.append(cellName).append(ERROR_EMPTY);
					errFlag = true;
				}else if(cell15.getCellType() == XSSFCell.CELL_TYPE_STRING){
					String val = cell15.getStringCellValue().trim();
					if(!Strings.empty(val)){
						 vo.setBusinessTime(val.trim());
					}
				}else {
					errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_STR_DATE);
					errFlag = true;
				}
				if(vo !=null){
					vo.setFileName(fileName);
					vo.setStatus(errFlag?CreditInfoVo.Status.INVALID:CreditInfoVo.Status.VALID);
				    vo.setRemark(errMsg.toString());
					creditorList.add(vo);
				}
			 }
			 return creditorList;
		}
		 /**
		  * 还款计划2007Excel
		  * @param fileInp
		  * @param xssf
		  * @param sheetName
		  * @return
		  * @throws Exception
		  */
		 public static List<RepayPlanVo> repaySheetExcel2007(FileInputStream fileInp,String fileName,XSSFWorkbook xssf,String sheetName)throws Exception{
			    List<RepayPlanVo> repayList = new ArrayList<RepayPlanVo>();
				XSSFSheet sheet = xssf.getSheet(sheetName);
				for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
					boolean errFlag = false;
					String cellName = "";
					StringBuilder errMsg = new StringBuilder("第"+rowNumber+"行：");
					XSSFRow xssRow = sheet.getRow(rowNumber);
					if (xssRow == null) {
						errMsg.append(ERROR_EMPTY);
						Logger.warn(errMsg.toString()+" 跳过处理。");
						continue;
					}
					RepayPlanVo vo = new RepayPlanVo();
				    cellName = "_债权人编号";
					XSSFCell cell0=xssRow.getCell(0);  //债权人编号
					if(cell0==null){
						errMsg.append(cellName).append(ERROR_EMPTY);
						errFlag = true;
					}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCreditorNo(cell0.getStringCellValue().trim());
					}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setCreditorNo((""+cell0.getNumericCellValue()).trim());
					}else{
						errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
						errFlag = true;
					}
					cellName = "_债权编号";
					XSSFCell cell1=xssRow.getCell(1);//债权编号
					if(cell1==null){
						errMsg.append(cellName).append(ERROR_EMPTY);
						errFlag = true;
					}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCreditCode(cell1.getStringCellValue().trim());
					}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setCreditCode((""+cell1.getNumericCellValue()).toString().trim());
					}else{
						errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_TXT);
						errFlag = true;
					}
					cellName = "_期数";
					XSSFCell cell2=xssRow.getCell(2);//期数
					if(cell2==null){
						errMsg.append(cellName).append(ERROR_EMPTY);
						errFlag = true;
					}else if(cell2.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCreditCode(cell1.getStringCellValue().trim());
					}else{
						errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_NUMBER_OR_TXT);
						errFlag = true;
					}
					cellName = "_应还日期";
					XSSFCell cell3=xssRow.getCell(3);//应还日期
					if(cell3==null){
						errMsg.append(cellName).append(ERROR_EMPTY);
						errFlag = true;
					}else if(cell3.getCellType() == XSSFCell.CELL_TYPE_STRING){
						String val = cell3.getStringCellValue();
						if(!Strings.empty(val)){
							try{
							   vo.setRepayTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
							}catch(Exception e){
								Logger.error("债权导入：解析excel应还日期格式化异常:",e);
								errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_STR_DATE);
								errFlag = true;
							}
						}
					}else{
						errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_STR_DATE);
						errFlag = true;
					}
					cellName = "_应还本金";
					XSSFCell cell4=xssRow.getCell(4);//应还本金
					if(cell4==null){
						errMsg.append(cellName).append(ERROR_EMPTY);
						errFlag = true;
					}else if(cell4.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRepayPrincipal(BigDecimal.valueOf(cell4.getNumericCellValue()));
					}else if(cell4.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setRepayPrincipal(BigDecimal.valueOf(Double.parseDouble(cell4.getStringCellValue().trim())));
					}else{
						errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_NUMBER_OR_TXT);
						errFlag = true;
					}
					cellName = "_应还利息";
					XSSFCell cell5=xssRow.getCell(5);//应还利息
					if(cell5==null){
						errMsg.append(cellName).append(ERROR_EMPTY);
						errFlag = true;
					}else if(cell5.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRepayInterest(BigDecimal.valueOf(cell5.getNumericCellValue()));
					}else if(cell5.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setRepayInterest(BigDecimal.valueOf(Double.parseDouble(cell5.getStringCellValue().trim())));
					}else{
						errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_NUMBER_OR_TXT);
						errFlag = true;
					}
					cellName = "_应还总额";
					XSSFCell cell6=xssRow.getCell(6);//应还总额
					if(cell6==null){
						errMsg.append(cellName).append(ERROR_EMPTY);
						errFlag = true;
					}else if(cell6.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRepayAllmount(BigDecimal.valueOf(cell6.getNumericCellValue()));
					}else if(cell6.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setRepayAllmount(BigDecimal.valueOf(Double.parseDouble(cell6.getStringCellValue().trim())));
					}else{
						errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_NUMBER_OR_TXT);
						errFlag = true;
					}
					cellName = "_剩余本金";
					XSSFCell cell7=xssRow.getCell(7);//剩余本金
					if(cell7==null){
						errMsg.append(cellName).append(ERROR_EMPTY);
						errFlag = true;
					}else if(cell7.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRemainPrincipal(BigDecimal.valueOf(cell7.getNumericCellValue()));
					}else if(cell7.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setRemainPrincipal(BigDecimal.valueOf(Double.parseDouble(cell7.getStringCellValue().trim())));
					}else{
						errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_NUMBER_OR_TXT);
						errFlag = true;
					}
					if(vo !=null){
						vo.setFileName(fileName);
						vo.setStatus(errFlag?CreditInfoVo.Status.INVALID:CreditInfoVo.Status.VALID);
						if(!errFlag){
							vo.setRemark(errMsg.toString());
						}
						repayList.add(vo);
					}
				}
				 fileInp.close();
				 return repayList;
			}
		   
			
	 /**
	  * 验证 身份证 是否有效		 
	  * @param identifyId
	  * @return
	  */
     public static  boolean  checkIdentityCode(String identifyId){
    	 String el = "(\\d{18}|(\\d{17}[\\d|X]))";
    	 return !el.matches(identifyId) ;
     }
    
     /**  
      * 检查整数  
      * @param num  
      * @param type "0+":非负整数 "+":正整数 "-0":非正整数 "-":负整数 "":整数  
      * @return  
      */  
     public static boolean checkNumber(String num,String type){   
         String eL = "";   
         if(type.equals("0+")){
        	 eL = "^\\d+$";//非负整数   
         }else if(type.equals("+")){
        	 eL = "^\\d*[1-9]\\d*$";//正整数   
         }else if(type.equals("-0")){
        	 eL = "^((-\\d+)|(0+))$";//非正整数   
         }else if(type.equals("-")){
        	 eL = "^-\\d*[1-9]\\d*$";//负整数   
         }else if(type.equals("0-100")){ // 0-100的正整数
        	 eL = "^(?:0|[1-9][0-9]?|100)$";
         } else{
        	 eL = "^-?\\d+$";//整数   
         }
         return Pattern.compile(eL).matcher(num).matches();   
     }   
    	
			 
}
