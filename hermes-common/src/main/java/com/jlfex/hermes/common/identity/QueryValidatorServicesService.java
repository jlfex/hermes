
package com.jlfex.hermes.common.identity;

public interface QueryValidatorServicesService extends javax.xml.rpc.Service {
    public java.lang.String getQueryValidatorServicesAddress();

	public QueryValidatorServices getQueryValidatorServices() throws javax.xml.rpc.ServiceException;

	public QueryValidatorServices getQueryValidatorServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
