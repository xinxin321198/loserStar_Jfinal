/**
 * MTPacks.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.loserstar.sms.webServiceClientEP;

public class MTPacks  implements java.io.Serializable {
    private java.lang.String uuid;

    private java.lang.String batchID;

    private java.lang.String batchName;

    private int sendType;

    private int msgType;

    private com.loserstar.sms.webServiceClientEP.MediaItems[] medias;

    private com.loserstar.sms.webServiceClientEP.MessageData[] msgs;

    private int bizType;

    private boolean distinctFlag;

    private long scheduleTime;

    private java.lang.String remark;

    private java.lang.String customNum;

    private long deadline;

    public MTPacks() {
    }

    public MTPacks(
           java.lang.String uuid,
           java.lang.String batchID,
           java.lang.String batchName,
           int sendType,
           int msgType,
           com.loserstar.sms.webServiceClientEP.MediaItems[] medias,
           com.loserstar.sms.webServiceClientEP.MessageData[] msgs,
           int bizType,
           boolean distinctFlag,
           long scheduleTime,
           java.lang.String remark,
           java.lang.String customNum,
           long deadline) {
           this.uuid = uuid;
           this.batchID = batchID;
           this.batchName = batchName;
           this.sendType = sendType;
           this.msgType = msgType;
           this.medias = medias;
           this.msgs = msgs;
           this.bizType = bizType;
           this.distinctFlag = distinctFlag;
           this.scheduleTime = scheduleTime;
           this.remark = remark;
           this.customNum = customNum;
           this.deadline = deadline;
    }


    /**
     * Gets the uuid value for this MTPacks.
     * 
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this MTPacks.
     * 
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }


    /**
     * Gets the batchID value for this MTPacks.
     * 
     * @return batchID
     */
    public java.lang.String getBatchID() {
        return batchID;
    }


    /**
     * Sets the batchID value for this MTPacks.
     * 
     * @param batchID
     */
    public void setBatchID(java.lang.String batchID) {
        this.batchID = batchID;
    }


    /**
     * Gets the batchName value for this MTPacks.
     * 
     * @return batchName
     */
    public java.lang.String getBatchName() {
        return batchName;
    }


    /**
     * Sets the batchName value for this MTPacks.
     * 
     * @param batchName
     */
    public void setBatchName(java.lang.String batchName) {
        this.batchName = batchName;
    }


    /**
     * Gets the sendType value for this MTPacks.
     * 
     * @return sendType
     */
    public int getSendType() {
        return sendType;
    }


    /**
     * Sets the sendType value for this MTPacks.
     * 
     * @param sendType
     */
    public void setSendType(int sendType) {
        this.sendType = sendType;
    }


    /**
     * Gets the msgType value for this MTPacks.
     * 
     * @return msgType
     */
    public int getMsgType() {
        return msgType;
    }


    /**
     * Sets the msgType value for this MTPacks.
     * 
     * @param msgType
     */
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }


    /**
     * Gets the medias value for this MTPacks.
     * 
     * @return medias
     */
    public com.loserstar.sms.webServiceClientEP.MediaItems[] getMedias() {
        return medias;
    }


    /**
     * Sets the medias value for this MTPacks.
     * 
     * @param medias
     */
    public void setMedias(com.loserstar.sms.webServiceClientEP.MediaItems[] medias) {
        this.medias = medias;
    }


    /**
     * Gets the msgs value for this MTPacks.
     * 
     * @return msgs
     */
    public com.loserstar.sms.webServiceClientEP.MessageData[] getMsgs() {
        return msgs;
    }


    /**
     * Sets the msgs value for this MTPacks.
     * 
     * @param msgs
     */
    public void setMsgs(com.loserstar.sms.webServiceClientEP.MessageData[] msgs) {
        this.msgs = msgs;
    }


    /**
     * Gets the bizType value for this MTPacks.
     * 
     * @return bizType
     */
    public int getBizType() {
        return bizType;
    }


    /**
     * Sets the bizType value for this MTPacks.
     * 
     * @param bizType
     */
    public void setBizType(int bizType) {
        this.bizType = bizType;
    }


    /**
     * Gets the distinctFlag value for this MTPacks.
     * 
     * @return distinctFlag
     */
    public boolean isDistinctFlag() {
        return distinctFlag;
    }


    /**
     * Sets the distinctFlag value for this MTPacks.
     * 
     * @param distinctFlag
     */
    public void setDistinctFlag(boolean distinctFlag) {
        this.distinctFlag = distinctFlag;
    }


    /**
     * Gets the scheduleTime value for this MTPacks.
     * 
     * @return scheduleTime
     */
    public long getScheduleTime() {
        return scheduleTime;
    }


    /**
     * Sets the scheduleTime value for this MTPacks.
     * 
     * @param scheduleTime
     */
    public void setScheduleTime(long scheduleTime) {
        this.scheduleTime = scheduleTime;
    }


    /**
     * Gets the remark value for this MTPacks.
     * 
     * @return remark
     */
    public java.lang.String getRemark() {
        return remark;
    }


    /**
     * Sets the remark value for this MTPacks.
     * 
     * @param remark
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }


    /**
     * Gets the customNum value for this MTPacks.
     * 
     * @return customNum
     */
    public java.lang.String getCustomNum() {
        return customNum;
    }


    /**
     * Sets the customNum value for this MTPacks.
     * 
     * @param customNum
     */
    public void setCustomNum(java.lang.String customNum) {
        this.customNum = customNum;
    }


    /**
     * Gets the deadline value for this MTPacks.
     * 
     * @return deadline
     */
    public long getDeadline() {
        return deadline;
    }


    /**
     * Sets the deadline value for this MTPacks.
     * 
     * @param deadline
     */
    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MTPacks)) return false;
        MTPacks other = (MTPacks) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.uuid==null && other.getUuid()==null) || 
             (this.uuid!=null &&
              this.uuid.equals(other.getUuid()))) &&
            ((this.batchID==null && other.getBatchID()==null) || 
             (this.batchID!=null &&
              this.batchID.equals(other.getBatchID()))) &&
            ((this.batchName==null && other.getBatchName()==null) || 
             (this.batchName!=null &&
              this.batchName.equals(other.getBatchName()))) &&
            this.sendType == other.getSendType() &&
            this.msgType == other.getMsgType() &&
            ((this.medias==null && other.getMedias()==null) || 
             (this.medias!=null &&
              java.util.Arrays.equals(this.medias, other.getMedias()))) &&
            ((this.msgs==null && other.getMsgs()==null) || 
             (this.msgs!=null &&
              java.util.Arrays.equals(this.msgs, other.getMsgs()))) &&
            this.bizType == other.getBizType() &&
            this.distinctFlag == other.isDistinctFlag() &&
            this.scheduleTime == other.getScheduleTime() &&
            ((this.remark==null && other.getRemark()==null) || 
             (this.remark!=null &&
              this.remark.equals(other.getRemark()))) &&
            ((this.customNum==null && other.getCustomNum()==null) || 
             (this.customNum!=null &&
              this.customNum.equals(other.getCustomNum()))) &&
            this.deadline == other.getDeadline();
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
        if (getUuid() != null) {
            _hashCode += getUuid().hashCode();
        }
        if (getBatchID() != null) {
            _hashCode += getBatchID().hashCode();
        }
        if (getBatchName() != null) {
            _hashCode += getBatchName().hashCode();
        }
        _hashCode += getSendType();
        _hashCode += getMsgType();
        if (getMedias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMedias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMedias(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMsgs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMsgs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMsgs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getBizType();
        _hashCode += (isDistinctFlag() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += new Long(getScheduleTime()).hashCode();
        if (getRemark() != null) {
            _hashCode += getRemark().hashCode();
        }
        if (getCustomNum() != null) {
            _hashCode += getCustomNum().hashCode();
        }
        _hashCode += new Long(getDeadline()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MTPacks.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "MTPacks"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "uuid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batchID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "batchID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batchName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "batchName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "sendType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msgType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "msgType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "medias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "MediaItems"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.139130.net", "MediaItems"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msgs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "msgs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "MessageData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.139130.net", "MessageData"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bizType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "bizType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distinctFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "distinctFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scheduleTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "scheduleTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remark");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "remark"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "customNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deadline");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "deadline"));
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
