/**
 * MOMsg.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.loserstar.sms.webServiceClientEP;

public class MOMsg  implements java.io.Serializable {
    private java.lang.String phone;

    private java.lang.String content;

    private int msgType;

    private java.lang.String specNumber;

    private java.lang.String serviceType;

    private java.util.Calendar receiveTime;

    private java.lang.String reserve;

    public MOMsg() {
    }

    public MOMsg(
           java.lang.String phone,
           java.lang.String content,
           int msgType,
           java.lang.String specNumber,
           java.lang.String serviceType,
           java.util.Calendar receiveTime,
           java.lang.String reserve) {
           this.phone = phone;
           this.content = content;
           this.msgType = msgType;
           this.specNumber = specNumber;
           this.serviceType = serviceType;
           this.receiveTime = receiveTime;
           this.reserve = reserve;
    }


    /**
     * Gets the phone value for this MOMsg.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this MOMsg.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the content value for this MOMsg.
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }


    /**
     * Sets the content value for this MOMsg.
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }


    /**
     * Gets the msgType value for this MOMsg.
     * 
     * @return msgType
     */
    public int getMsgType() {
        return msgType;
    }


    /**
     * Sets the msgType value for this MOMsg.
     * 
     * @param msgType
     */
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }


    /**
     * Gets the specNumber value for this MOMsg.
     * 
     * @return specNumber
     */
    public java.lang.String getSpecNumber() {
        return specNumber;
    }


    /**
     * Sets the specNumber value for this MOMsg.
     * 
     * @param specNumber
     */
    public void setSpecNumber(java.lang.String specNumber) {
        this.specNumber = specNumber;
    }


    /**
     * Gets the serviceType value for this MOMsg.
     * 
     * @return serviceType
     */
    public java.lang.String getServiceType() {
        return serviceType;
    }


    /**
     * Sets the serviceType value for this MOMsg.
     * 
     * @param serviceType
     */
    public void setServiceType(java.lang.String serviceType) {
        this.serviceType = serviceType;
    }


    /**
     * Gets the receiveTime value for this MOMsg.
     * 
     * @return receiveTime
     */
    public java.util.Calendar getReceiveTime() {
        return receiveTime;
    }


    /**
     * Sets the receiveTime value for this MOMsg.
     * 
     * @param receiveTime
     */
    public void setReceiveTime(java.util.Calendar receiveTime) {
        this.receiveTime = receiveTime;
    }


    /**
     * Gets the reserve value for this MOMsg.
     * 
     * @return reserve
     */
    public java.lang.String getReserve() {
        return reserve;
    }


    /**
     * Sets the reserve value for this MOMsg.
     * 
     * @param reserve
     */
    public void setReserve(java.lang.String reserve) {
        this.reserve = reserve;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MOMsg)) return false;
        MOMsg other = (MOMsg) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent()))) &&
            this.msgType == other.getMsgType() &&
            ((this.specNumber==null && other.getSpecNumber()==null) || 
             (this.specNumber!=null &&
              this.specNumber.equals(other.getSpecNumber()))) &&
            ((this.serviceType==null && other.getServiceType()==null) || 
             (this.serviceType!=null &&
              this.serviceType.equals(other.getServiceType()))) &&
            ((this.receiveTime==null && other.getReceiveTime()==null) || 
             (this.receiveTime!=null &&
              this.receiveTime.equals(other.getReceiveTime()))) &&
            ((this.reserve==null && other.getReserve()==null) || 
             (this.reserve!=null &&
              this.reserve.equals(other.getReserve())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        _hashCode += getMsgType();
        if (getSpecNumber() != null) {
            _hashCode += getSpecNumber().hashCode();
        }
        if (getServiceType() != null) {
            _hashCode += getServiceType().hashCode();
        }
        if (getReceiveTime() != null) {
            _hashCode += getReceiveTime().hashCode();
        }
        if (getReserve() != null) {
            _hashCode += getReserve().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MOMsg.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "MOMsg"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "Phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "Content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msgType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "MsgType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "SpecNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "ServiceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiveTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "ReceiveTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reserve");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "Reserve"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
