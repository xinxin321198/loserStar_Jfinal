/**
 * MTResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.loserstar.sms.webServiceClientEP;

public class MTResponse  implements java.io.Serializable {
    private java.lang.String batchID;

    private java.lang.String msgID;

    private java.lang.String customMsgID;

    private int state;

    private java.lang.String phone;

    private java.lang.Integer total;

    private java.lang.Integer number;

    private java.util.Calendar submitRespTime;

    private java.lang.String originResult;

    private java.lang.String reserve;

    private long id;

    public MTResponse() {
    }

    public MTResponse(
           java.lang.String batchID,
           java.lang.String msgID,
           java.lang.String customMsgID,
           int state,
           java.lang.String phone,
           java.lang.Integer total,
           java.lang.Integer number,
           java.util.Calendar submitRespTime,
           java.lang.String originResult,
           java.lang.String reserve,
           long id) {
           this.batchID = batchID;
           this.msgID = msgID;
           this.customMsgID = customMsgID;
           this.state = state;
           this.phone = phone;
           this.total = total;
           this.number = number;
           this.submitRespTime = submitRespTime;
           this.originResult = originResult;
           this.reserve = reserve;
           this.id = id;
    }


    /**
     * Gets the batchID value for this MTResponse.
     * 
     * @return batchID
     */
    public java.lang.String getBatchID() {
        return batchID;
    }


    /**
     * Sets the batchID value for this MTResponse.
     * 
     * @param batchID
     */
    public void setBatchID(java.lang.String batchID) {
        this.batchID = batchID;
    }


    /**
     * Gets the msgID value for this MTResponse.
     * 
     * @return msgID
     */
    public java.lang.String getMsgID() {
        return msgID;
    }


    /**
     * Sets the msgID value for this MTResponse.
     * 
     * @param msgID
     */
    public void setMsgID(java.lang.String msgID) {
        this.msgID = msgID;
    }


    /**
     * Gets the customMsgID value for this MTResponse.
     * 
     * @return customMsgID
     */
    public java.lang.String getCustomMsgID() {
        return customMsgID;
    }


    /**
     * Sets the customMsgID value for this MTResponse.
     * 
     * @param customMsgID
     */
    public void setCustomMsgID(java.lang.String customMsgID) {
        this.customMsgID = customMsgID;
    }


    /**
     * Gets the state value for this MTResponse.
     * 
     * @return state
     */
    public int getState() {
        return state;
    }


    /**
     * Sets the state value for this MTResponse.
     * 
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }


    /**
     * Gets the phone value for this MTResponse.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this MTResponse.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the total value for this MTResponse.
     * 
     * @return total
     */
    public java.lang.Integer getTotal() {
        return total;
    }


    /**
     * Sets the total value for this MTResponse.
     * 
     * @param total
     */
    public void setTotal(java.lang.Integer total) {
        this.total = total;
    }


    /**
     * Gets the number value for this MTResponse.
     * 
     * @return number
     */
    public java.lang.Integer getNumber() {
        return number;
    }


    /**
     * Sets the number value for this MTResponse.
     * 
     * @param number
     */
    public void setNumber(java.lang.Integer number) {
        this.number = number;
    }


    /**
     * Gets the submitRespTime value for this MTResponse.
     * 
     * @return submitRespTime
     */
    public java.util.Calendar getSubmitRespTime() {
        return submitRespTime;
    }


    /**
     * Sets the submitRespTime value for this MTResponse.
     * 
     * @param submitRespTime
     */
    public void setSubmitRespTime(java.util.Calendar submitRespTime) {
        this.submitRespTime = submitRespTime;
    }


    /**
     * Gets the originResult value for this MTResponse.
     * 
     * @return originResult
     */
    public java.lang.String getOriginResult() {
        return originResult;
    }


    /**
     * Sets the originResult value for this MTResponse.
     * 
     * @param originResult
     */
    public void setOriginResult(java.lang.String originResult) {
        this.originResult = originResult;
    }


    /**
     * Gets the reserve value for this MTResponse.
     * 
     * @return reserve
     */
    public java.lang.String getReserve() {
        return reserve;
    }


    /**
     * Sets the reserve value for this MTResponse.
     * 
     * @param reserve
     */
    public void setReserve(java.lang.String reserve) {
        this.reserve = reserve;
    }


    /**
     * Gets the id value for this MTResponse.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this MTResponse.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MTResponse)) return false;
        MTResponse other = (MTResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.batchID==null && other.getBatchID()==null) || 
             (this.batchID!=null &&
              this.batchID.equals(other.getBatchID()))) &&
            ((this.msgID==null && other.getMsgID()==null) || 
             (this.msgID!=null &&
              this.msgID.equals(other.getMsgID()))) &&
            ((this.customMsgID==null && other.getCustomMsgID()==null) || 
             (this.customMsgID!=null &&
              this.customMsgID.equals(other.getCustomMsgID()))) &&
            this.state == other.getState() &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.total==null && other.getTotal()==null) || 
             (this.total!=null &&
              this.total.equals(other.getTotal()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.submitRespTime==null && other.getSubmitRespTime()==null) || 
             (this.submitRespTime!=null &&
              this.submitRespTime.equals(other.getSubmitRespTime()))) &&
            ((this.originResult==null && other.getOriginResult()==null) || 
             (this.originResult!=null &&
              this.originResult.equals(other.getOriginResult()))) &&
            ((this.reserve==null && other.getReserve()==null) || 
             (this.reserve!=null &&
              this.reserve.equals(other.getReserve()))) &&
            this.id == other.getId();
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
        if (getBatchID() != null) {
            _hashCode += getBatchID().hashCode();
        }
        if (getMsgID() != null) {
            _hashCode += getMsgID().hashCode();
        }
        if (getCustomMsgID() != null) {
            _hashCode += getCustomMsgID().hashCode();
        }
        _hashCode += getState();
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getTotal() != null) {
            _hashCode += getTotal().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getSubmitRespTime() != null) {
            _hashCode += getSubmitRespTime().hashCode();
        }
        if (getOriginResult() != null) {
            _hashCode += getOriginResult().hashCode();
        }
        if (getReserve() != null) {
            _hashCode += getReserve().hashCode();
        }
        _hashCode += new Long(getId()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MTResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "MTResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batchID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "batchID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msgID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "msgID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customMsgID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "customMsgID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("total");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "total"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submitRespTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "submitRespTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "originResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reserve");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "reserve"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
