package com.jlfex.hermes.common.identity;

import javax.xml.namespace.QName;

public class QueryValidatorServicesServiceLocator extends org.apache.axis.client.Service implements
		QueryValidatorServicesService {

	
	private static final long serialVersionUID = -31386259409418801L;

	public QueryValidatorServicesServiceLocator() {
	}

	public QueryValidatorServicesServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public QueryValidatorServicesServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for QueryValidatorServices
	private java.lang.String QueryValidatorServices_address = "http://gboss.id5.cn/services/QueryValidatorServices";

	@Override
	public java.lang.String getQueryValidatorServicesAddress() {
		return QueryValidatorServices_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String QueryValidatorServicesWSDDServiceName = "QueryValidatorServices";

	public java.lang.String getQueryValidatorServicesWSDDServiceName() {
		return QueryValidatorServicesWSDDServiceName;
	}

	public void setQueryValidatorServicesWSDDServiceName(java.lang.String name) {
		QueryValidatorServicesWSDDServiceName = name;
	}

	@Override
	public QueryValidatorServices getQueryValidatorServices() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(QueryValidatorServices_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getQueryValidatorServices(endpoint);
	}

	@Override
	public QueryValidatorServices getQueryValidatorServices(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			QueryValidatorServicesSoapBindingStub _stub = new QueryValidatorServicesSoapBindingStub(portAddress, this);
			_stub.setPortName(getQueryValidatorServicesWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setQueryValidatorServicesEndpointAddress(java.lang.String address) {
		QueryValidatorServices_address = address;
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (QueryValidatorServices.class.isAssignableFrom(serviceEndpointInterface)) {
				QueryValidatorServicesSoapBindingStub _stub = new QueryValidatorServicesSoapBindingStub(
						new java.net.URL(QueryValidatorServices_address), this);
				_stub.setPortName(getQueryValidatorServicesWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("QueryValidatorServices".equals(inputPortName)) {
			return getQueryValidatorServices();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	@Override
	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://app.service.validator.businesses.gboss.id5.cn",
				"QueryValidatorServicesService");
	}

	private java.util.HashSet<QName> ports = null;

	@SuppressWarnings("rawtypes")
	@Override
	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet<QName>();
			ports.add(new javax.xml.namespace.QName("http://app.service.validator.businesses.gboss.id5.cn",
					"QueryValidatorServices"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("QueryValidatorServices".equals(portName)) {
			setQueryValidatorServicesEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
