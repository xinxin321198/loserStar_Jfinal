/**
 * BPMHelperServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.loserstar.bpm.webServiceClient;

public class BPMHelperServiceServiceLocator extends org.apache.axis.client.Service implements com.loserstar.bpm.webServiceClient.BPMHelperServiceService {

    public BPMHelperServiceServiceLocator() {
    }


    public BPMHelperServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BPMHelperServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BPMHelperService
    private java.lang.String BPMHelperService_address = "http://10.96.45.23:8088/BPMFormExternal/services/BPMHelperService";

    public java.lang.String getBPMHelperServiceAddress() {
        return BPMHelperService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BPMHelperServiceWSDDServiceName = "BPMHelperService";

    public java.lang.String getBPMHelperServiceWSDDServiceName() {
        return BPMHelperServiceWSDDServiceName;
    }

    public void setBPMHelperServiceWSDDServiceName(java.lang.String name) {
        BPMHelperServiceWSDDServiceName = name;
    }

    public com.loserstar.bpm.webServiceClient.BPMHelperService getBPMHelperService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BPMHelperService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBPMHelperService(endpoint);
    }

    public com.loserstar.bpm.webServiceClient.BPMHelperService getBPMHelperService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.loserstar.bpm.webServiceClient.BPMHelperServiceSoapBindingStub _stub = new com.loserstar.bpm.webServiceClient.BPMHelperServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getBPMHelperServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBPMHelperServiceEndpointAddress(java.lang.String address) {
        BPMHelperService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.loserstar.bpm.webServiceClient.BPMHelperService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.loserstar.bpm.webServiceClient.BPMHelperServiceSoapBindingStub _stub = new com.loserstar.bpm.webServiceClient.BPMHelperServiceSoapBindingStub(new java.net.URL(BPMHelperService_address), this);
                _stub.setPortName(getBPMHelperServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BPMHelperService".equals(inputPortName)) {
            return getBPMHelperService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.kaen.com", "BPMHelperServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.kaen.com", "BPMHelperService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BPMHelperService".equals(portName)) {
            setBPMHelperServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
