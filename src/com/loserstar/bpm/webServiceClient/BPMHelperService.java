/**
 * BPMHelperService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.loserstar.bpm.webServiceClient;

public interface BPMHelperService extends java.rmi.Remote {
    public java.lang.String finishTask(java.lang.String userId, int taskId, java.lang.String data) throws java.rmi.RemoteException;
    public java.lang.String suspendProcess(java.lang.String piid) throws java.rmi.RemoteException;
    public java.lang.String runFlow(java.lang.String bpdId, java.lang.String processAppId, java.lang.String data) throws java.rmi.RemoteException;
    public java.lang.String exitModify(java.lang.String nextUsers, java.lang.String piid, java.lang.String nextDepts, java.lang.String sendUser) throws java.rmi.RemoteException;
    public java.lang.String terminateProcess(java.lang.String piid) throws java.rmi.RemoteException;
    public java.lang.String getTodoTasks(java.lang.String userId) throws java.rmi.RemoteException;
    public java.lang.String getTaskVariables(java.lang.String userId, int taskId) throws java.rmi.RemoteException;
    public java.lang.String getProcessVariables(java.lang.String userId, int piid) throws java.rmi.RemoteException;
}
