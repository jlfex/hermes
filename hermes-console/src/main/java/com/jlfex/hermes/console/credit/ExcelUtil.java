package com.jlfex.hermes.console.credit;

import java.io.File;
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

import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.console.pojo.CreditInfoVo;
import com.jlfex.hermes.console.pojo.RepayPlanVo;

public class ExcelUtil {
	
	
	/**
	 * 导入Excel入口，只需要调用此方法即可
	 * 根据导入的Excel版本不同，调用不同方法
	 * @param fileName
	 * @param fileInp
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> entranceExcelTemplate(String fileName, FileInputStream fileInp)throws Exception{	
		Map<String,Object> map= null;
		if ("xlsx".equals(fileName.substring(fileName.lastIndexOf(".") + 1))) {
			map=entranceExcel2007(fileInp);// 调用2007版Excel方法
		}else if ("xls".equals(fileName.substring(fileName.lastIndexOf(".") + 1))) {
			map=entranceExcel2003(fileInp);// 调用2003版Excel方法
		}
		return map;
	}
	/**
	 * 获取同一个2007版Excel文件的不同sheet页数据
	 * @param fileInp
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> entranceExcel2007(FileInputStream fileInp)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		XSSFWorkbook xssf = new XSSFWorkbook(fileInp);
		for (int numSheet = 0; numSheet < xssf.getNumberOfSheets(); numSheet++) {
			if("债权信息".equals(xssf.getSheetName(numSheet))){
				//获取债权信息Sheet页数据
				List<CreditInfoVo> creditList=debtRightexcel2007(fileInp,xssf,xssf.getSheetName(numSheet));
				map.put("creditList",creditList);
			}
			if("还款计划表".equals(xssf.getSheetName(numSheet))){
				//获取还款Sheet页数据
				List<RepayPlanVo> repayList=repayMentexcel2007(fileInp,xssf,xssf.getSheetName(numSheet));
				map.put("repayList",repayList);
			}
		}
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
		public static List<CreditInfoVo> debtRightexcel2007(FileInputStream fileInp,XSSFWorkbook xssf,String sheetName)throws Exception{
			List<CreditInfoVo> creditorList = new ArrayList<CreditInfoVo>();
			XSSFSheet sheet = xssf.getSheet(sheetName);
			for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
				boolean errFlag = false;
				StringBuilder errMsg = new StringBuilder("第"+rowNumber+"行：");
				XSSFRow xssRow = sheet.getRow(rowNumber);
				if (xssRow == null){
					errMsg.append("为空");
					continue;
				}
				CreditInfoVo vo = new CreditInfoVo();
				XSSFCell cell0=xssRow.getCell(0);  //债权人编号
				if(cell0==null){
					errMsg.append("债权人编号为空,");
					errFlag = true;
				}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_STRING){
					String aa = cell0.getStringCellValue();
					vo.setCreditorNo(cell0.getStringCellValue());
				}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					vo.setCreditorNo(""+cell0.getNumericCellValue());
				}
				XSSFCell cell1=xssRow.getCell(1); //债权编号
				if(cell1==null){
					errMsg.append("债权编号为空,");
					errFlag = true;
				}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCreditCode(cell1.getStringCellValue());
				}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					vo.setCreditCode(""+cell1.getNumericCellValue());
				}
				XSSFCell cell2=xssRow.getCell(2); //债权类型
				if(cell2==null){
					errMsg.append("债权类型为空,");
					errFlag = true;
				}else if(cell2.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCreditKind(cell2.getStringCellValue());
				}
				
				XSSFCell cell3=xssRow.getCell(3);  //借款人
				if(cell3==null){
					errMsg.append("借款人为空,");
					errFlag = true;
				}else if(cell3.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setBorrower(cell3.getStringCellValue());
				}
				XSSFCell cell4=xssRow.getCell(4);//借款人证件类型
				if(cell4==null){
					errMsg.append("借款人证件类型为空,");
					errFlag = true;
				}else if(cell4.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCertType(cell4.getStringCellValue());
				}
				XSSFCell cell5=xssRow.getCell(5);//借款人证件号码
				if(cell5==null){
					errMsg.append("借款人证件号码为空,");
					errFlag = true;
				}else if(cell5.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCertificateNo(cell5.getStringCellValue());
				}else if(cell5.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					vo.setCertificateNo(""+cell5.getNumericCellValue());
				}
				XSSFCell cell6=xssRow.getCell(6);//行业
				if(cell6==null){
					//errMsg.append("行业为空,");
					//errFlag = true;
				}else if(cell6.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setWorkType(""+cell6.getRichStringCellValue());
				}
				XSSFCell cell7=xssRow.getCell(7);//省份
				if(cell7==null){
					//errMsg.append("省份为空,");
					//errFlag = true;
				}else if(cell7.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setProvince(""+cell7.getRichStringCellValue());
				}
				XSSFCell cell8=xssRow.getCell(8);//城市
				if(cell8==null){
					//errMsg.append("城市为空,");
					//errFlag = true;
				}else if(cell8.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setCity(""+cell8.getRichStringCellValue());
				}
				XSSFCell cell9=xssRow.getCell(9);//借款金额
				if(cell9==null){
					errMsg.append("借款金额为空,");
					errFlag = true;
				}else if(cell9.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					vo.setAmount(BigDecimal.valueOf(cell9.getNumericCellValue()));
				}else if(cell9.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setAmount(BigDecimal.valueOf(Double.parseDouble(cell9.getStringCellValue())));
				}
				XSSFCell cell10=xssRow.getCell(10);//年利率
				if(cell10==null){
					errMsg.append("年利率为空,");
					errFlag = true;
				}else if(cell10.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					vo.setRate(BigDecimal.valueOf(cell10.getNumericCellValue()));
				}else if(cell10.getCellType()==XSSFCell.CELL_TYPE_STRING){
					vo.setRate(BigDecimal.valueOf(Double.parseDouble(cell10.getStringCellValue())));
				}
				XSSFCell cell11=xssRow.getCell(11);//借款期限
				if(cell11==null){
					errMsg.append("借款期限为空,");
					errFlag = true;
				}else if(cell11.getCellType() == XSSFCell.CELL_TYPE_STRING){
					vo.setPeriod(""+cell11.getStringCellValue());
				}
				XSSFCell cell12=xssRow.getCell(12);//借款用途
				if(cell12==null){
					errMsg.append("借款用途为空,");
					errFlag = true;
				}else{
					vo.setPurpose(cell12.getStringCellValue());
				}
				XSSFCell cell13=xssRow.getCell(13);//还款方式
				if(cell13==null){
					errMsg.append("还款方式为空,");
					errFlag = true;
				}else{
					vo.setPayType(cell13.getStringCellValue());
				}
				XSSFCell cell14=xssRow.getCell(14);//债权到期日
				if(cell14==null){
					errMsg.append("债权到期日为空,");
					errFlag = true;
				}else if(cell14.getCellType() == XSSFCell.CELL_TYPE_STRING){
					String val = cell14.getStringCellValue();
					if(!Strings.empty(val)){
						vo.setDeadTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
					}
				}else if(cell14.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					String val = ""+cell14.getNumericCellValue();
					if(!Strings.empty(val)){
						vo.setDeadTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
					}
					
				}
				XSSFCell cell15=xssRow.getCell(15);//放款日期
				if(cell15==null){
					errMsg.append("放款日期为空,");
					errFlag = true;
				}else if(cell15.getCellType() == XSSFCell.CELL_TYPE_STRING){
					String val = cell15.getStringCellValue();
					if(!Strings.empty(val)){
						vo.setBusinessTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
					}
				}else if(cell15.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					String val = ""+cell15.getNumericCellValue();
					if(!Strings.empty(val)){
						vo.setBusinessTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
					}
				}
				if(vo!=null && !errFlag){
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
		 public static List<RepayPlanVo> repayMentexcel2007(FileInputStream fileInp,XSSFWorkbook xssf,String sheetName)throws Exception{
			    List<RepayPlanVo> repayList = new ArrayList<RepayPlanVo>();
				XSSFSheet sheet = xssf.getSheet(sheetName);
				for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
					boolean errFlag = false;
					StringBuilder errMsg = new StringBuilder("第"+rowNumber+"行：");
					XSSFRow xssRow = sheet.getRow(rowNumber);
					if (xssRow == null) {
						errMsg.append("为空");
						continue;
					}
					RepayPlanVo vo = new RepayPlanVo();
					XSSFCell cell0=xssRow.getCell(0);  //债权人编号
					if(cell0==null){
						errMsg.append("债权人编号为空,");
						errFlag = true;
					}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCreditorNo(cell0.getStringCellValue());
					}else if(cell0.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setCreditorNo(""+cell0.getNumericCellValue());
					}
					XSSFCell cell1=xssRow.getCell(1);//债权编号
					if(cell1==null){
						errMsg.append("债权编号为空,");
						errFlag = true;
					}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setCreditCode(cell1.getStringCellValue());
					}else if(cell1.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setCreditCode(""+cell1.getNumericCellValue());
					}
					XSSFCell cell2=xssRow.getCell(2);//期数
					if(cell2==null){
						errMsg.append("期数为空,");
						errFlag = true;
					}else if(cell2.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						vo.setPeriod((long)cell2.getNumericCellValue());
					}
					XSSFCell cell3=xssRow.getCell(3);//应还日期
					if(cell3==null){
						errMsg.append("应还日期为空,");
						errFlag = true;
					}else if(cell3.getCellType() == XSSFCell.CELL_TYPE_STRING){
						String val = cell3.getStringCellValue();
						if(!Strings.empty(val)){
							vo.setRepayTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
						}
					}else if(cell3.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						String val =""+ cell3.getNumericCellValue();
						if(!Strings.empty(val)){
							vo.setRepayTime(new SimpleDateFormat("yyyy-MM-dd").parse(val.trim()));
						}
					}
					XSSFCell cell4=xssRow.getCell(4);//应还本金
					if(cell4==null){
						errMsg.append("应还本金为空,");
						errFlag = true;
					}else if(cell4.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRepayPrincipal(BigDecimal.valueOf(cell4.getNumericCellValue()));
					}else if(cell4.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setRepayPrincipal(BigDecimal.valueOf(Double.parseDouble(cell4.getStringCellValue())));
					}
					XSSFCell cell5=xssRow.getCell(5);//应还利息
					if(cell5==null){
						errMsg.append("应还利息为空,");
						errFlag = true;
					}else if(cell5.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRepayInterest(BigDecimal.valueOf(cell5.getNumericCellValue()));
					}else if(cell5.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setRepayInterest(BigDecimal.valueOf(Double.parseDouble(cell5.getStringCellValue())));
					}
					
					XSSFCell cell6=xssRow.getCell(6);//应还总额
					if(cell6==null){
						errMsg.append("应还总额为空,");
						errFlag = true;
					}else if(cell6.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRepayAllmount(BigDecimal.valueOf(cell6.getNumericCellValue()));
					}else if(cell6.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setRepayAllmount(BigDecimal.valueOf(Double.parseDouble(cell6.getStringCellValue())));
					}
					XSSFCell cell7=xssRow.getCell(7);//剩余本金
					if(cell7==null){
						errMsg.append("剩余本金为空,");
						errFlag = true;
					}else if(cell7.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
						vo.setRemainPrincipal(BigDecimal.valueOf(cell7.getNumericCellValue()));
					}else if(cell7.getCellType() == XSSFCell.CELL_TYPE_STRING){
						vo.setRemainPrincipal(BigDecimal.valueOf(Double.parseDouble(cell7.getStringCellValue())));
					}
					if(errFlag){
						System.out.println("==导入文件校验信息：=="+errMsg.toString());
					}
					if(vo!=null && !errFlag){
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
							vo.setPeriod((long)cell2.getNumericCellValue());
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
			 
			  public static void main(String[] args)throws Exception{
					File file = new File("D:\\test.xlsx");
					FileInputStream input = new FileInputStream(file);
					Map<String,Object> map=entranceExcelTemplate(file.getName(),input);
					List<CreditInfoVo> creditList=(List<CreditInfoVo>)map.get("creditList");
					List<RepayPlanVo>  repayList =(List<RepayPlanVo>)map.get("repayList");
					System.out.println("sheet1数据行数："+creditList.size());
					System.out.println("sheet2数据行数"+repayList.size());
				}
}
