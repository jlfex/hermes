package com.jlfex.hermes.common.identity;

import java.rmi.RemoteException;

public class QueryValidatorServicesStub implements QueryValidatorServices {

	@Override
	public String querySingle(String userName_, String password_, String type_, String param_) throws RemoteException {
		// RPCServiceClient serviceClient = new RPCServiceClient();
		// Options options = serviceClient.getOptions();
		// options.setUserName(userName_);
		// options.setPassword(password_);
		// QName qName = new
		// QName("http://app.service.validator.businesses.gboss.id5.cn",
		// "querySingle");
		return null;
	}

	@Override
	public String queryBatch(String userName_, String password_, String type_, String param_) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
