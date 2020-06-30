package com.loserstar.bpm.webServiceClient;

public class BPMHelperServiceProxy implements com.loserstar.bpm.webServiceClient.BPMHelperService {
  private String _endpoint = null;
  private com.loserstar.bpm.webServiceClient.BPMHelperService bPMHelperService = null;
  
  public BPMHelperServiceProxy() {
    _initBPMHelperServiceProxy();
  }
  
  public BPMHelperServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initBPMHelperServiceProxy();
  }
  
  private void _initBPMHelperServiceProxy() {
    try {
      bPMHelperService = (new com.loserstar.bpm.webServiceClient.BPMHelperServiceServiceLocator()).getBPMHelperService();
      if (bPMHelperService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bPMHelperService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bPMHelperService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bPMHelperService != null)
      ((javax.xml.rpc.Stub)bPMHelperService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.loserstar.bpm.webServiceClient.BPMHelperService getBPMHelperService() {
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService;
  }
  
  public java.lang.String finishTask(java.lang.String userId, int taskId, java.lang.String data) throws java.rmi.RemoteException{
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService.finishTask(userId, taskId, data);
  }
  
  public java.lang.String suspendProcess(java.lang.String piid) throws java.rmi.RemoteException{
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService.suspendProcess(piid);
  }
  
  public java.lang.String runFlow(java.lang.String bpdId, java.lang.String processAppId, java.lang.String data) throws java.rmi.RemoteException{
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService.runFlow(bpdId, processAppId, data);
  }
  
  public java.lang.String exitModify(java.lang.String nextUsers, java.lang.String piid, java.lang.String nextDepts, java.lang.String sendUser) throws java.rmi.RemoteException{
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService.exitModify(nextUsers, piid, nextDepts, sendUser);
  }
  
  public java.lang.String terminateProcess(java.lang.String piid) throws java.rmi.RemoteException{
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService.terminateProcess(piid);
  }
  
  public java.lang.String getTodoTasks(java.lang.String userId) throws java.rmi.RemoteException{
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService.getTodoTasks(userId);
  }
  
  public java.lang.String getTaskVariables(java.lang.String userId, int taskId) throws java.rmi.RemoteException{
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService.getTaskVariables(userId, taskId);
  }
  
  public java.lang.String getProcessVariables(java.lang.String userId, int piid) throws java.rmi.RemoteException{
    if (bPMHelperService == null)
      _initBPMHelperServiceProxy();
    return bPMHelperService.getProcessVariables(userId, piid);
  }
  
  
}