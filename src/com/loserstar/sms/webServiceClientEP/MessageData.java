/**
 * MessageData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.loserstar.sms.webServiceClientEP;

public class MessageData  implements java.io.Serializable {
    private java.lang.String phone;

    private java.lang.String content;

    private boolean vipFlag;

    private java.lang.String customMsgID;

    private com.loserstar.sms.webServiceClientEP.MediaItems[] medias;

    public MessageData() {
    }

    public MessageData(
           java.lang.String phone,
           java.lang.String content,
           boolean vipFlag,
           java.lang.String customMsgID,
           com.loserstar.sms.webServiceClientEP.MediaItems[] medias) {
           this.phone = phone;
           this.content = content;
           this.vipFlag = vipFlag;
           this.customMsgID = customMsgID;
           this.medias = medias;
    }


    /**
     * Gets the phone value for this MessageData.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this MessageData.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the content value for this MessageData.
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }


    /**
     * Sets the content value for this MessageData.
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }


    /**
     * Gets the vipFlag value for this MessageData.
     * 
     * @return vipFlag
     */
    public boolean isVipFlag() {
        return vipFlag;
    }


    /**
     * Sets the vipFlag value for this MessageData.
     * 
     * @param vipFlag
     */
    public void setVipFlag(boolean vipFlag) {
        this.vipFlag = vipFlag;
    }


    /**
     * Gets the customMsgID value for this MessageData.
     * 
     * @return customMsgID
     */
    public java.lang.String getCustomMsgID() {
        return customMsgID;
    }


    /**
     * Sets the customMsgID value for this MessageData.
     * 
     * @param customMsgID
     */
    public void setCustomMsgID(java.lang.String customMsgID) {
        this.customMsgID = customMsgID;
    }


    /**
     * Gets the medias value for this MessageData.
     * 
     * @return medias
     */
    public com.loserstar.sms.webServiceClientEP.MediaItems[] getMedias() {
        return medias;
    }


    /**
     * Sets the medias value for this MessageData.
     * 
     * @param medias
     */
    public void setMedias(com.loserstar.sms.webServiceClientEP.MediaItems[] medias) {
        this.medias = medias;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessageData)) return false;
        MessageData other = (MessageData) obj;
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
            this.vipFlag == other.isVipFlag() &&
            ((this.customMsgID==null && other.getCustomMsgID()==null) || 
             (this.customMsgID!=null &&
              this.customMsgID.equals(other.getCustomMsgID()))) &&
            ((this.medias==null && other.getMedias()==null) || 
             (this.medias!=null &&
              java.util.Arrays.equals(this.medias, other.getMedias())));
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
        _hashCode += (isVipFlag() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCustomMsgID() != null) {
            _hashCode += getCustomMsgID().hashCode();
        }
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessageData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "MessageData"));
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
        elemField.setFieldName("vipFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "vipFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField.setFieldName("medias");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "medias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "MediaItems"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.139130.net", "MediaItems"));
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
