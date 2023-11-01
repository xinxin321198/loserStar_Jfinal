/**
 * AccountInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.loserstar.sms.webServiceClientEP;

public class AccountInfo  implements java.io.Serializable {
    private java.lang.String account;

    private java.lang.String name;

    private java.lang.String identify;

    private java.lang.String[] bizNames;

    private java.lang.String userbrief;

    private java.math.BigDecimal balance;

    private java.lang.String reserve;

    public AccountInfo() {
    }

    public AccountInfo(
           java.lang.String account,
           java.lang.String name,
           java.lang.String identify,
           java.lang.String[] bizNames,
           java.lang.String userbrief,
           java.math.BigDecimal balance,
           java.lang.String reserve) {
           this.account = account;
           this.name = name;
           this.identify = identify;
           this.bizNames = bizNames;
           this.userbrief = userbrief;
           this.balance = balance;
           this.reserve = reserve;
    }


    /**
     * Gets the account value for this AccountInfo.
     * 
     * @return account
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this AccountInfo.
     * 
     * @param account
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the name value for this AccountInfo.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this AccountInfo.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the identify value for this AccountInfo.
     * 
     * @return identify
     */
    public java.lang.String getIdentify() {
        return identify;
    }


    /**
     * Sets the identify value for this AccountInfo.
     * 
     * @param identify
     */
    public void setIdentify(java.lang.String identify) {
        this.identify = identify;
    }


    /**
     * Gets the bizNames value for this AccountInfo.
     * 
     * @return bizNames
     */
    public java.lang.String[] getBizNames() {
        return bizNames;
    }


    /**
     * Sets the bizNames value for this AccountInfo.
     * 
     * @param bizNames
     */
    public void setBizNames(java.lang.String[] bizNames) {
        this.bizNames = bizNames;
    }


    /**
     * Gets the userbrief value for this AccountInfo.
     * 
     * @return userbrief
     */
    public java.lang.String getUserbrief() {
        return userbrief;
    }


    /**
     * Sets the userbrief value for this AccountInfo.
     * 
     * @param userbrief
     */
    public void setUserbrief(java.lang.String userbrief) {
        this.userbrief = userbrief;
    }


    /**
     * Gets the balance value for this AccountInfo.
     * 
     * @return balance
     */
    public java.math.BigDecimal getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this AccountInfo.
     * 
     * @param balance
     */
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }


    /**
     * Gets the reserve value for this AccountInfo.
     * 
     * @return reserve
     */
    public java.lang.String getReserve() {
        return reserve;
    }


    /**
     * Sets the reserve value for this AccountInfo.
     * 
     * @param reserve
     */
    public void setReserve(java.lang.String reserve) {
        this.reserve = reserve;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccountInfo)) return false;
        AccountInfo other = (AccountInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.identify==null && other.getIdentify()==null) || 
             (this.identify!=null &&
              this.identify.equals(other.getIdentify()))) &&
            ((this.bizNames==null && other.getBizNames()==null) || 
             (this.bizNames!=null &&
              java.util.Arrays.equals(this.bizNames, other.getBizNames()))) &&
            ((this.userbrief==null && other.getUserbrief()==null) || 
             (this.userbrief!=null &&
              this.userbrief.equals(other.getUserbrief()))) &&
            ((this.balance==null && other.getBalance()==null) || 
             (this.balance!=null &&
              this.balance.equals(other.getBalance()))) &&
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
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getIdentify() != null) {
            _hashCode += getIdentify().hashCode();
        }
        if (getBizNames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBizNames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBizNames(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUserbrief() != null) {
            _hashCode += getUserbrief().hashCode();
        }
        if (getBalance() != null) {
            _hashCode += getBalance().hashCode();
        }
        if (getReserve() != null) {
            _hashCode += getReserve().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccountInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "AccountInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identify");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "Identify"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bizNames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "BizNames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.139130.net", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userbrief");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "Userbrief"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "Balance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
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
