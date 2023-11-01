package com.loserstar.sms.webServiceClientEP;

public class WebServiceSoapProxy implements com.loserstar.sms.webServiceClientEP.WebServiceSoap {
  private String _endpoint = null;
  private com.loserstar.sms.webServiceClientEP.WebServiceSoap webServiceSoap = null;
  
  public WebServiceSoapProxy() {
    _initWebServiceSoapProxy();
  }
  
  public WebServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebServiceSoapProxy();
  }
  
  private void _initWebServiceSoapProxy() {
    try {
      webServiceSoap = (new com.loserstar.sms.webServiceClientEP.WebServiceLocator()).getWebServiceSoap();
      if (webServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)webServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)webServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webServiceSoap != null)
      ((javax.xml.rpc.Stub)webServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.loserstar.sms.webServiceClientEP.WebServiceSoap getWebServiceSoap() {
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap;
  }
  
  public com.loserstar.sms.webServiceClientEP.MOMsg[] getMOMessage(java.lang.String account, java.lang.String password, int pagesize) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.getMOMessage(account, password, pagesize);
  }
  
  public com.loserstar.sms.webServiceClientEP.BusinessType[] getBusinessType(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.getBusinessType(account, password);
  }
  
  public com.loserstar.sms.webServiceClientEP.AccountInfo getAccountInfo(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.getAccountInfo(account, password);
  }
  
  public int modifyPassword(java.lang.String account, java.lang.String old_password, java.lang.String new_password) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.modifyPassword(account, old_password, new_password);
  }
  
  public com.loserstar.sms.webServiceClientEP.GsmsResponse post(java.lang.String account, java.lang.String password, com.loserstar.sms.webServiceClientEP.MTPacks mtpack) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.post(account, password, mtpack);
  }
  
  public com.loserstar.sms.webServiceClientEP.MTResponse[] getResponse(java.lang.String account, java.lang.String password, int pageSize) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.getResponse(account, password, pageSize);
  }
  
  public com.loserstar.sms.webServiceClientEP.MTReport[] getReport(java.lang.String account, java.lang.String password, int pageSize) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.getReport(account, password, pageSize);
  }
  
  public com.loserstar.sms.webServiceClientEP.MTResponse[] findResponse(java.lang.String account, java.lang.String password, java.lang.String batchid, java.lang.String mobile, int pageindex, int flag) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.findResponse(account, password, batchid, mobile, pageindex, flag);
  }
  
  public com.loserstar.sms.webServiceClientEP.MTReport[] findReport(java.lang.String account, java.lang.String password, java.lang.String batchid, java.lang.String mobile, int pageindex, int flag) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.findReport(account, password, batchid, mobile, pageindex, flag);
  }
  
  public com.loserstar.sms.webServiceClientEP.MediaItems[] setMedias(java.lang.String fullPath) throws java.rmi.RemoteException{
    if (webServiceSoap == null)
      _initWebServiceSoapProxy();
    return webServiceSoap.setMedias(fullPath);
  }
  
  
}