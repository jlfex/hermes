package com.jlfex.hermes.console.credit;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class ExcelUtil {
	
	public static final String ERROR_KIND = "单元格格式错误, ";
	public static final String ERROR_EMPTY = "值为空";
    public static final String ERROR_DATE_FORMAT_EXP = "时间格式化异常";
	public static final String VAR_PERCENT = "%";
	public static final String FORMAT_TXT = "请设置为文本格式";
	public static final String FORMAT_NUMBER = "请设置为数字格式";
	public static final String FORMAT_NUMBER_OR_TXT = "请设置为文本格式或数字格式";
	public static final String FORMAT_RATE = "请设置为文本格式:如 10% 格式的字符串";
	public static final String FORMAT_STR_DATE = "请设置为文本格式:如 yyyy-mm-dd 格式的字符串";
	public static final String SHEET_CREIDT_NAME = "债权信息";
	public static final String SHEET_REPAY_NAME = "还款计划表";
	
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
	 * 获取同一个2003版Excel文件的不同sheet页数据
	 * @param fileInp
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> entranceExcel2003(FileInputStream fileInp)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		HSSFWorkbook xssf = new HSSFWorkbook(fileInp);
		for (int numSheet = 0; numSheet < xssf.getNumberOfSheets(); numSheet++) {
			if("债权信息".equals(xssf.getSheetName(numSheet))){
				List<CreditInfoVo> creditList=debtRightexcel2003(fileInp,xssf,xssf.getSheetName(numSheet));//获取债权信息Sheet页数据
				map.put("creditList",creditList);
			}
			if("还款计划表".equals(xssf.getSheetName(numSheet))){
				List<RepayPlanVo> repayList=repayMentexcel2003(fileInp,xssf,xssf.getSheetName(numSheet));
				map.put("repayList",repayList);
			}
			
		}
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
					vo.setAmount(BigDecimal.valueOf(cell9.getNumericCellValue()));
				}else if(cell9.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setAmount(BigDecimal.valueOf(Double.parseDouble(cell9.getStringCellValue().trim())));
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
					String val = cell10.getStringCellValue().trim();
					if(val.contains(VAR_PERCENT)){
						val = val.replace(VAR_PERCENT, "");
					}
					vo.setRate(BigDecimal.valueOf(Double.parseDouble(val)));
				}else if(cell10.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					vo.setRate(BigDecimal.valueOf(cell10.getNumericCellValue()));
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
					if(!Strings.empty(val)){
						try{
						    vo.setDeadTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
						}catch(Exception e){
							Logger.error("债权导入：解析excel"+cellName+"格式化异常:",e);
							errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_STR_DATE);
							errFlag = true;
						}
					}
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
						try{
						    vo.setBusinessTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
						}catch(Exception e){
							Logger.error("债权导入：解析excel"+cellName+"格式化异常:",e);
							errMsg.append(cellName).append(ERROR_KIND).append(FORMAT_STR_DATE);
							errFlag = true;
						}
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
		     * 债权信息2003Excel
		     * @param fileInp
		     * @param xssf
		     * @param sheetName
		     * @return
		     * @throws Exception
		     */
			public static List<CreditInfoVo> debtRightexcel2003(FileInputStream fileInp,HSSFWorkbook xssf,String sheetName)throws Exception{
				List<CreditInfoVo> creditList = new ArrayList<CreditInfoVo>();
				HSSFSheet sheet = xssf.getSheet(sheetName);
				for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
					boolean errFlag = false;
					StringBuilder errMsg = new StringBuilder("第"+rowNumber+"行：");
					HSSFRow xssRow = sheet.getRow(rowNumber);
					if (xssRow == null) {
						errMsg.append("为空");
						continue;
					}
					CreditInfoVo vo = new CreditInfoVo();
					HSSFCell cell0=xssRow.getCell(0);  //债权人编号
					if(cell0==null){
						errMsg.append("债权人编号为空");
						errFlag = true;
					}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCreditorNo(cell0.getStringCellValue());
					}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setCreditorNo(""+cell0.getNumericCellValue());
					}
					
					HSSFCell cell1=xssRow.getCell(1); //债权编号
					if(cell1==null){
						errMsg.append("债权编号为空,");
						errFlag = true;
					}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCreditCode(cell1.getStringCellValue());
					}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setCreditCode(""+cell1.getNumericCellValue());
					}
					
					HSSFCell cell2=xssRow.getCell(2); //债权类型
					if(cell2==null){
						errMsg.append("债权类型为空,");
						errFlag = true;
					}else if(cell2.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCreditKind(cell2.getStringCellValue());
					}
					
					HSSFCell cell3=xssRow.getCell(3);  //借款人
					if(cell3==null){
						errMsg.append("借款人为空,");
						errFlag = true;
					}else if(cell3.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setBorrower(cell3.getStringCellValue());
					}
					
					HSSFCell cell4=xssRow.getCell(4);//借款人证件类型
					if(cell4==null){
						errMsg.append("借款人证件类型为空,");
						errFlag = true;
					}else if(cell4.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCertType(cell4.getStringCellValue());
					}
					
					HSSFCell cell5=xssRow.getCell(5);//借款人证件号码
					if(cell5==null){
						errMsg.append("借款人证件号码为空,");
						errFlag = true;
					}else if(cell5.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCertificateNo(cell5.getStringCellValue());
					}else if(cell5.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setCertificateNo(cell5.getNumericCellValue()+"");
					}
					HSSFCell cell6=xssRow.getCell(6);//行业
					if(cell6==null){
					}else if(cell6.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setWorkType(""+cell6.getRichStringCellValue());
					}
					
					HSSFCell cell7=xssRow.getCell(7);//省份
					if(cell7==null){
					}else if(cell7.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setProvince(""+cell7.getRichStringCellValue());
					}
					HSSFCell cell8=xssRow.getCell(8);//城市
					if(cell8==null){
						
					}else if(cell8.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCity(""+cell8.getRichStringCellValue());
					}
					HSSFCell cell9=xssRow.getCell(9);//借款金额
					if(cell9==null){
						errMsg.append("借款金额为空,");
						errFlag = true;
					}else if(cell9.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setAmount(BigDecimal.valueOf(cell9.getNumericCellValue()));
					}else if(cell9.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setAmount(BigDecimal.valueOf(Double.parseDouble(cell9.getStringCellValue())));
					}
					HSSFCell cell10=xssRow.getCell(10);//年利率
					if(cell10==null){
						errMsg.append("年利率为空,");
						errFlag = true;
					}else if(cell10.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRate(BigDecimal.valueOf(cell10.getNumericCellValue()));
					}else if(cell10.getCellType()==XSSFCell.CELL_TYPE_STRING){
						vo.setRate(BigDecimal.valueOf(Double.parseDouble(cell10.getStringCellValue())));
					}
					
					HSSFCell cell11=xssRow.getCell(11);//借款期限
					if(cell11==null){
						errMsg.append("借款期限为空,");
						errFlag = true;
					}else if(cell11.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setPeriod(""+cell11.getStringCellValue());
					}
					
					HSSFCell cell12=xssRow.getCell(12);//借款用途
					if(cell12==null){
						errMsg.append("借款用途为空,");
						errFlag = true;
					}else {
						vo.setPurpose(cell12.getStringCellValue());
					}
					
					HSSFCell cell13=xssRow.getCell(13);//还款方式
					if(cell13==null){
						errMsg.append("还款方式为空,");
						errFlag = true;
					}else if(cell13.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setPayType(cell13.getStringCellValue());
					}
					
					HSSFCell cell14=xssRow.getCell(14);//债权到期日
					if(cell14==null){
						errMsg.append("债权到期日为空,");
						errFlag = true;
					}else if(cell14.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setDeadTime(cell14.getDateCellValue());
					}
				
					HSSFCell cell15=xssRow.getCell(15);//放款日期
					if(cell15==null){
						errMsg.append("放款日期为空,");
						errFlag = true;
					}else if(cell15.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setBusinessTime(cell15.getDateCellValue());
					}
					if(vo!=null){
						creditList.add(vo);
					}
				  }
				  fileInp.close();
				  return creditList;
			 }
			
			/**
			 * 还款计划2003Excel
			 * @param fileInp
			 * @param xssf
			 * @param sheetName
			 * @return
			 * @throws Exception
			 */
			 public static List<RepayPlanVo> repayMentexcel2003(FileInputStream fileInp,HSSFWorkbook xssf,String sheetName) throws Exception{
				    List<RepayPlanVo> repayList = new ArrayList<RepayPlanVo>();
					HSSFSheet sheet = xssf.getSheet(sheetName);
					for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
						boolean errFlag = false;
						StringBuilder errMsg = new StringBuilder("第"+rowNumber+"行：");
						HSSFRow xssRow = sheet.getRow(rowNumber);
						if (xssRow == null) {
							errMsg.append("为空");
							errFlag = true;
							continue;
						}
						RepayPlanVo vo = new RepayPlanVo();
						HSSFCell cell0=xssRow.getCell(0);  //债权人编号
						if(cell0==null){
							errMsg.append("债权人编号为空,");
							errFlag = true;
						}else if(cell0.getCellType() == HSSFCell.CELL_TYPE_STRING){
							vo.setCreditorNo(cell0.getStringCellValue());
						}else if(cell0.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							vo.setCreditorNo(""+cell0.getNumericCellValue());
						}
						
						HSSFCell cell1=xssRow.getCell(1);//债权编号
						if(cell1==null){
							errMsg.append("债权编号为空,");
							errFlag = true;
						}else if(cell1.getCellType() == HSSFCell.CELL_TYPE_STRING){
							vo.setCreditCode(cell1.getStringCellValue());
						}else if(cell1.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							vo.setCreditCode(""+cell1.getNumericCellValue());
						}
						
						HSSFCell cell2=xssRow.getCell(2);//期数
						if(cell2==null){
							errMsg.append("期数为空,");
							errFlag = true;
						}else if(cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							vo.setPeriod(Integer.parseInt((""+cell2.getNumericCellValue()).trim()));
						}
						HSSFCell cell3=xssRow.getCell(3);//应还日期
						if(cell3==null){
							errMsg.append("应还日期为空,");
							errFlag = true;
						}else if(cell3.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							vo.setRepayTime(cell3.getDateCellValue());
						}
						HSSFCell cell4=xssRow.getCell(4);//应还本金
						if(cell4==null){
							errMsg.append("应还本金为空,");
							errFlag = true;
						}else if(cell4.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
							vo.setRepayPrincipal(BigDecimal.valueOf(cell4.getNumericCellValue()));
						}else if(cell4.getCellType() == HSSFCell.CELL_TYPE_STRING){
							vo.setRepayPrincipal(BigDecimal.valueOf(Double.parseDouble(cell4.getStringCellValue())));
						}
						HSSFCell cell5=xssRow.getCell(5);//应还利息
						if(cell5==null){
							errMsg.append("应还总额为空,");
							errFlag = true;
						}else if(cell5.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
							vo.setRepayInterest(BigDecimal.valueOf(cell5.getNumericCellValue()));
						}else if(cell5.getCellType() == HSSFCell.CELL_TYPE_STRING){
							vo.setRepayInterest(BigDecimal.valueOf(Double.parseDouble(cell5.getStringCellValue())));
						
						HSSFCell cell6=xssRow.getCell(6);//应还总额
						if(cell6==null){
							errMsg.append("应还总额为空,");
							errFlag = true;
						}else if(cell6.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
							vo.setRepayAllmount(BigDecimal.valueOf(cell6.getNumericCellValue()));
						}else if(cell6.getCellType() == HSSFCell.CELL_TYPE_STRING){
							vo.setRepayAllmount(BigDecimal.valueOf(Double.parseDouble(cell6.getStringCellValue())));
						}
						HSSFCell cell7=xssRow.getCell(7);//剩余本金
						if(cell7==null){
							errMsg.append("剩余本金为空,");
							errFlag = true;
						}else if(cell7.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
							vo.setRemainPrincipal(BigDecimal.valueOf(cell7.getNumericCellValue()));
						}else if(cell7.getCellType() == HSSFCell.CELL_TYPE_STRING){
							vo.setRemainPrincipal(BigDecimal.valueOf(Double.parseDouble(cell7.getStringCellValue())));
						}
						//获取对象，放入集合
						repayList.add(vo);
					 }
				}
					 fileInp.close();
					 return repayList;
			 }	
			 
}
