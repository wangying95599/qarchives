package org.quetzaco.archives.util.propelToOA;

public class WSTodoOABeanProxy implements WSTodoOABean {
  private String _endpoint = null;
  private WSTodoOABean wSTodoOABean = null;
  
  public WSTodoOABeanProxy() {
    _initWSTodoOABeanProxy();
  }
  
  public WSTodoOABeanProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSTodoOABeanProxy();
  }
  
  private void _initWSTodoOABeanProxy() {
    try {
      wSTodoOABean = (new WSTodoOABeanServiceLocator()).getTodoOABeanService();
      if (wSTodoOABean != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSTodoOABean)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSTodoOABean)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSTodoOABean != null)
      ((javax.xml.rpc.Stub)wSTodoOABean)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public WSTodoOABean getWSTodoOABean() {
    if (wSTodoOABean == null)
      _initWSTodoOABeanProxy();
    return wSTodoOABean;
  }
  
  public int saveTodoData(String usercode, String password, String actiontype, String referenceid, String activityinstid, String processinstname, String orgname, String executor, String bizactivity, String bizprocess, String agent, String processinstid, String activityinstname, String bizsystem, String participanttype, String signstate, String actionurl) throws java.rmi.RemoteException{
    if (wSTodoOABean == null)
      _initWSTodoOABeanProxy();
    return wSTodoOABean.saveTodoData(usercode, password, actiontype, referenceid, activityinstid, processinstname, orgname, executor, bizactivity, bizprocess, agent, processinstid, activityinstname, bizsystem, participanttype, signstate, actionurl);
  }
  
  public int updateTodoFlag(String usercode, String password, String referenceid, String bizsystem) throws java.rmi.RemoteException{
    if (wSTodoOABean == null)
      _initWSTodoOABeanProxy();
    return wSTodoOABean.updateTodoFlag(usercode, password, referenceid, bizsystem);
  }
  
  public String getAgents(String usercode, String password, String emplogin, String instid) throws java.rmi.RemoteException{
    if (wSTodoOABean == null)
      _initWSTodoOABeanProxy();
    return wSTodoOABean.getAgents(usercode, password, emplogin, instid);
  }
  
  
}