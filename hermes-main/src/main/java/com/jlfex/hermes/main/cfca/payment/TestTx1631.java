//package com.jlfex.hermes.main.cfca.payment;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import cfca.payment.api.system.TxMessenger;
//import cfca.payment.api.tx.Tx1361Request;
//import cfca.payment.api.tx.Tx1361Response;
//import cfca.util.StringUtil;
//
//import com.jlfex.hermes.common.Logger;
//
//@Controller
//@RequestMapping("/payTest")
//public class TestTx1631 {
//	
//	@RequestMapping("/test")
//	public  String test(Model model) throws Exception {
//		
//		String institutionID ="000020" ; //request.getParameter("InstitutionID");
//        String txSN = "1503161359301557234"; //request.getParameter("TxSN");
//        String orderNo ="1426485561634"; //request.getParameter("OrderNo");
//        String amount ="1";  // request.getParameter("Amount");
//        String bankID ="700";  //request.getParameter("BankID");
//        String cvn2 = "123"; //request.getParameter("CVN2");
//        String validDate ="1503"; //request.getParameter("ValidDate");
//        String accountName = "开发测试"; //request.getParameter("AccountName");
//        String accountNumber = "2424234242342";//request.getParameter("AccountNumber");
//        String branchName ="测试数据"; // request.getParameter("BranchName");
//        String province = "北京"; //request.getParameter("Province");
//        String city = "北京"; // request.getParameter("City");
//        String accountType ="11"; // request.getParameter("AccountType");
//        String note ="测试"; //request.getParameter("Note");
//        String phoneNumber = "18702168699"; //request.getParameter("PhoneNumber");
//        String email = "wiseyl@sina.com"; //request.getParameter("Email");
//        String identificationType = "X";  //request.getParameter("IdentificationType");
//        String identificationNumber = "123456"; //request.getParameter("IdentificationNumber");
//        String contractUserID = "1234565"; //request.getParameter("ContractUserID");
//        String payees = "张三" ;//request.getParameter("Payees");
//		
//	   Tx1361Request tx1361Request = new Tx1361Request();
//	   tx1361Request.setInstitutionID(institutionID);
//       tx1361Request.setTxSN(txSN);
//       tx1361Request.setOrderNo(orderNo);
//       tx1361Request.setAmount(StringUtil.isEmpty(amount) ? 0l : Long.parseLong(amount));
//       tx1361Request.setBankID(bankID);
//       tx1361Request.setCvn2(cvn2);
//       tx1361Request.setValidDate(validDate);
//       tx1361Request.setAccountName(accountName);
//       tx1361Request.setAccountNumber(accountNumber);
//       tx1361Request.setBranchName(branchName);
//       tx1361Request.setProvince(province);
//       tx1361Request.setAccountType(Integer.parseInt(accountType));
//       tx1361Request.setCity(city);
//       tx1361Request.setNote(note);
//       tx1361Request.setPhoneNumber(phoneNumber);
//       tx1361Request.setEmail(email);
//       tx1361Request.setIdentificationNumber(identificationNumber);
//       tx1361Request.setIdentificationType(identificationType);
//       tx1361Request.setContractUserID(contractUserID);
//       payees = payees.replaceAll("；", ";");
//       if (null != payees && payees.length() > 0) {
//           String[] payeeList = payees.split(";");
//           for (int i = 0; i < payeeList.length; i++) {
//               tx1361Request.addPayee(payeeList[i]);
//           }
//       }
//
//       // 3.执行报文处理
//       tx1361Request.process();
//       
//       String message = tx1361Request.getRequestMessage();
//       String signature = tx1361Request.getRequestSignature();
//       String txCode = "1361";
//       String txName = "市场订单单笔代收";
//
//       
//       
//       // 与支付平台进行通讯
//       TxMessenger txMessenger = new TxMessenger();
//       String[] respMsg = txMessenger.send(message, signature);// 0:message;
//
//       // 1:signature
//       String plainText = new String(cfca.util.Base64.decode(respMsg[0]), "UTF-8");
//
//       Logger.info("[message]=[" + respMsg[0] + "]");
//       Logger.info("[signature]=[" + respMsg[1] + "]");
//       Logger.info("[plainText]=[" + plainText + "]");
//       
//       if ("1361".equals(txCode)) {
//           Tx1361Response txResponse = new Tx1361Response(respMsg[0], respMsg[1]);
////           request.setAttribute("txCode", txCode);
////           request.setAttribute("txName", txName);
////           request.setAttribute("plainText", txResponse.getResponsePlainText());
//           if ("2000".equals(txResponse.getCode())) {
//               Logger.info("[Message]=[" + txResponse.getMessage() + "]");
//               Logger.info("[InstitutionID]=[" + txResponse.getInstitutionID() + "]");
//               Logger.info("[TxSN]=[" + txResponse.getTxSN() + "]");
//               Logger.info("[Amount]=[" + txResponse.getAmount() + "]");
//               Logger.info("[Status]=[" + txResponse.getStatus() + "]");
//               Logger.info("[BankTxTime]=[" + txResponse.getBankTxTime() + "]");
//               Logger.info("[ResponseCode]=[" + txResponse.getResponseCode() + "]");
//               Logger.info("[ResponseMessage]=[" + txResponse.getResponseMessage() + "]");
//               Logger.info("[OrderNo]=[" + txResponse.getOrderNo() + "]");
//
//               // 处理业务
//           }
//       } 
//       
//		return "";
//	} 
//	
//	public static  void main() throws Exception {
//
//		TestTx1631 obj = new  TestTx1631();
//		obj.test(null);
//		
//	}
//
//}
