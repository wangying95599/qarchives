/**
 * WSTodoOABeanServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.quetzaco.archives.util.propelToOA;

public class WSTodoOABeanServiceLocator extends org.apache.axis.client.Service implements WSTodoOABeanService {

    public WSTodoOABeanServiceLocator() {
    }


    public WSTodoOABeanServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSTodoOABeanServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TodoOABeanService
    private String TodoOABeanService_address = "http://10.10.20.67:83/axis/services/TodoOABeanService";

    public String getTodoOABeanServiceAddress() {
        return TodoOABeanService_address;
    }

    // The WSDD service name defaults to the port name.
    private String TodoOABeanServiceWSDDServiceName = "TodoOABeanService";

    public String getTodoOABeanServiceWSDDServiceName() {
        return TodoOABeanServiceWSDDServiceName;
    }

    public void setTodoOABeanServiceWSDDServiceName(String name) {
        TodoOABeanServiceWSDDServiceName = name;
    }

    public WSTodoOABean getTodoOABeanService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TodoOABeanService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTodoOABeanService(endpoint);
    }

    public WSTodoOABean getTodoOABeanService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            TodoOABeanServiceSoapBindingStub _stub = new TodoOABeanServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getTodoOABeanServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTodoOABeanServiceEndpointAddress(String address) {
        TodoOABeanService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WSTodoOABean.class.isAssignableFrom(serviceEndpointInterface)) {
                TodoOABeanServiceSoapBindingStub _stub = new TodoOABeanServiceSoapBindingStub(new java.net.URL(TodoOABeanService_address), this);
                _stub.setPortName(getTodoOABeanServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
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
        String inputPortName = portName.getLocalPart();
        if ("TodoOABeanService".equals(inputPortName)) {
            return getTodoOABeanService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.10.20.67:83/axis/services/TodoOABeanService", "WSTodoOABeanService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.10.20.67:83/axis/services/TodoOABeanService", "TodoOABeanService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("TodoOABeanService".equals(portName)) {
            setTodoOABeanServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
