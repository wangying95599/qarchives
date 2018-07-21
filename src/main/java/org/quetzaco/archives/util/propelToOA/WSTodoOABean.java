/**
 * WSTodoOABean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.quetzaco.archives.util.propelToOA;

public interface WSTodoOABean extends java.rmi.Remote {
    public int saveTodoData(String usercode, String password, String actiontype, String referenceid, String activityinstid, String processinstname, String orgname, String executor, String bizactivity, String bizprocess, String agent, String processinstid, String activityinstname, String bizsystem, String participanttype, String signstate, String actionurl) throws java.rmi.RemoteException;
    public int updateTodoFlag(String usercode, String password, String referenceid, String bizsystem) throws java.rmi.RemoteException;
    public String getAgents(String usercode, String password, String emplogin, String instid) throws java.rmi.RemoteException;
}
