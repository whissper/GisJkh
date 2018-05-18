//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.01 at 02:33:21 PM MSK 
//


package org.whissper.meteringdevices;

import gisjkh.BooleanAdapter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DeviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{}IdType"/>
 *         &lt;element name="HouseID" type="{}IdType"/>
 *         &lt;element name="deviceNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="stamp">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="model">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="installDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="commissDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="remoteMode" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="factorySealDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="temperatureSensor" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pressureSensor" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="firstVerificationDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="verificationInterval" type="{}IdType"/>
 *         &lt;element name="collectiveDevice" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="collectiveDeviceInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RemoteMeteringInfo" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="2000"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TemperatureSensingElementInfo" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="2000"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="PressureSensingElementInfo" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="2000"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="accounts" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="accountId" type="{}IdType" maxOccurs="unbounded"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="accountId" type="{}IdType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="premiseId" type="{}IdType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="value1" type="{}MeteringValueType" minOccurs="0"/>
 *         &lt;element name="resource1" type="{}IdType" minOccurs="0"/>
 *         &lt;element name="value2" type="{}MeteringValueType" minOccurs="0"/>
 *         &lt;element name="resource2" type="{}IdType" minOccurs="0"/>
 *         &lt;element name="value3" type="{}MeteringValueType" minOccurs="0"/>
 *         &lt;element name="resource3" type="{}IdType" minOccurs="0"/>
 *         &lt;element name="valueT1" type="{}MeteringValueType" minOccurs="0"/>
 *         &lt;element name="valueT2" type="{}MeteringValueType" minOccurs="0"/>
 *         &lt;element name="valueT3" type="{}MeteringValueType" minOccurs="0"/>
 *         &lt;element name="transformationRatio" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;fractionDigits value="2"/>
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="999999999999999.99"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="readoutDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="readingSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="archivingReason" type="{}IdType" minOccurs="0"/>
 *         &lt;element name="archivingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="action" type="{}ActionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceType", propOrder = {
    "id",
    "houseID",
    "deviceNumber",
    "stamp",
    "model",
    "installDate",
    "commissDate",
    "remoteMode",
    "factorySealDate",
    "temperatureSensor",
    "pressureSensor",
    "firstVerificationDate",
    "verificationInterval",
    "collectiveDevice",
    "collectiveDeviceInfo",
    "accounts",
    "accountId",
    "premiseId",
    "value1",
    "resource1",
    "value2",
    "resource2",
    "value3",
    "resource3",
    "valueT1",
    "valueT2",
    "valueT3",
    "transformationRatio",
    "readoutDate",
    "readingSource",
    "archivingReason",
    "archivingDate",
    "action"
})
public class DeviceType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(name = "HouseID", required = true)
    protected String houseID;
    @XmlElement(required = true)
    protected String deviceNumber;
    @XmlElement(required = true)
    protected String stamp;
    @XmlElement(required = true)
    protected String model;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar installDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar commissDate;
    protected boolean remoteMode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar factorySealDate;
    protected boolean temperatureSensor;
    protected boolean pressureSensor;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar firstVerificationDate;
    @XmlElement(required = true)
    protected String verificationInterval;
    @XmlElement(name = "collectiveDevice")
    @XmlJavaTypeAdapter( BooleanAdapter.class )
    protected Boolean collectiveDevice;
    protected DeviceType.CollectiveDeviceInfo collectiveDeviceInfo;
    protected List<DeviceType.Accounts> accounts;
    protected List<String> accountId;
    protected List<String> premiseId;
    protected BigDecimal value1;
    protected String resource1;
    protected BigDecimal value2;
    protected String resource2;
    protected BigDecimal value3;
    protected String resource3;
    protected BigDecimal valueT1;
    protected BigDecimal valueT2;
    protected BigDecimal valueT3;
    protected BigDecimal transformationRatio;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar readoutDate;
    protected String readingSource;
    protected String archivingReason;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar archivingDate;
    @XmlElement(required = true)
    protected String action;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the houseID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseID() {
        return houseID;
    }

    /**
     * Sets the value of the houseID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseID(String value) {
        this.houseID = value;
    }

    /**
     * Gets the value of the deviceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceNumber() {
        return deviceNumber;
    }

    /**
     * Sets the value of the deviceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceNumber(String value) {
        this.deviceNumber = value;
    }

    /**
     * Gets the value of the stamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStamp() {
        return stamp;
    }

    /**
     * Sets the value of the stamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStamp(String value) {
        this.stamp = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the installDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInstallDate() {
        return installDate;
    }

    /**
     * Sets the value of the installDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInstallDate(XMLGregorianCalendar value) {
        this.installDate = value;
    }

    /**
     * Gets the value of the commissDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCommissDate() {
        return commissDate;
    }

    /**
     * Sets the value of the commissDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCommissDate(XMLGregorianCalendar value) {
        this.commissDate = value;
    }

    /**
     * Gets the value of the remoteMode property.
     * 
     */
    public boolean isRemoteMode() {
        return remoteMode;
    }

    /**
     * Sets the value of the remoteMode property.
     * 
     */
    public void setRemoteMode(boolean value) {
        this.remoteMode = value;
    }

    /**
     * Gets the value of the factorySealDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFactorySealDate() {
        return factorySealDate;
    }

    /**
     * Sets the value of the factorySealDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFactorySealDate(XMLGregorianCalendar value) {
        this.factorySealDate = value;
    }

    /**
     * Gets the value of the temperatureSensor property.
     * 
     */
    public boolean isTemperatureSensor() {
        return temperatureSensor;
    }

    /**
     * Sets the value of the temperatureSensor property.
     * 
     */
    public void setTemperatureSensor(boolean value) {
        this.temperatureSensor = value;
    }

    /**
     * Gets the value of the pressureSensor property.
     * 
     */
    public boolean isPressureSensor() {
        return pressureSensor;
    }

    /**
     * Sets the value of the pressureSensor property.
     * 
     */
    public void setPressureSensor(boolean value) {
        this.pressureSensor = value;
    }

    /**
     * Gets the value of the firstVerificationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFirstVerificationDate() {
        return firstVerificationDate;
    }

    /**
     * Sets the value of the firstVerificationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFirstVerificationDate(XMLGregorianCalendar value) {
        this.firstVerificationDate = value;
    }

    /**
     * Gets the value of the verificationInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerificationInterval() {
        return verificationInterval;
    }

    /**
     * Sets the value of the verificationInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerificationInterval(String value) {
        this.verificationInterval = value;
    }

    /**
     * Gets the value of the collectiveDevice property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCollectiveDevice() {
        return collectiveDevice;
    }

    /**
     * Sets the value of the collectiveDevice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCollectiveDevice(Boolean value) {
        this.collectiveDevice = value;
    }

    /**
     * Gets the value of the collectiveDeviceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceType.CollectiveDeviceInfo }
     *     
     */
    public DeviceType.CollectiveDeviceInfo getCollectiveDeviceInfo() {
        return collectiveDeviceInfo;
    }

    /**
     * Sets the value of the collectiveDeviceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceType.CollectiveDeviceInfo }
     *     
     */
    public void setCollectiveDeviceInfo(DeviceType.CollectiveDeviceInfo value) {
        this.collectiveDeviceInfo = value;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeviceType.Accounts }
     * 
     * 
     */
    public List<DeviceType.Accounts> getAccounts() {
        if (accounts == null) {
            accounts = new ArrayList<DeviceType.Accounts>();
        }
        return this.accounts;
    }

    /**
     * Gets the value of the accountId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accountId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccountId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAccountId() {
        if (accountId == null) {
            accountId = new ArrayList<String>();
        }
        return this.accountId;
    }

    /**
     * Gets the value of the premiseId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the premiseId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPremiseId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPremiseId() {
        if (premiseId == null) {
            premiseId = new ArrayList<String>();
        }
        return this.premiseId;
    }

    /**
     * Gets the value of the value1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValue1() {
        return value1;
    }

    /**
     * Sets the value of the value1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValue1(BigDecimal value) {
        this.value1 = value;
    }

    /**
     * Gets the value of the resource1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResource1() {
        return resource1;
    }

    /**
     * Sets the value of the resource1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResource1(String value) {
        this.resource1 = value;
    }

    /**
     * Gets the value of the value2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValue2() {
        return value2;
    }

    /**
     * Sets the value of the value2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValue2(BigDecimal value) {
        this.value2 = value;
    }

    /**
     * Gets the value of the resource2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResource2() {
        return resource2;
    }

    /**
     * Sets the value of the resource2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResource2(String value) {
        this.resource2 = value;
    }

    /**
     * Gets the value of the value3 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValue3() {
        return value3;
    }

    /**
     * Sets the value of the value3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValue3(BigDecimal value) {
        this.value3 = value;
    }

    /**
     * Gets the value of the resource3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResource3() {
        return resource3;
    }

    /**
     * Sets the value of the resource3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResource3(String value) {
        this.resource3 = value;
    }

    /**
     * Gets the value of the valueT1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValueT1() {
        return valueT1;
    }

    /**
     * Sets the value of the valueT1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValueT1(BigDecimal value) {
        this.valueT1 = value;
    }

    /**
     * Gets the value of the valueT2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValueT2() {
        return valueT2;
    }

    /**
     * Sets the value of the valueT2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValueT2(BigDecimal value) {
        this.valueT2 = value;
    }

    /**
     * Gets the value of the valueT3 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValueT3() {
        return valueT3;
    }

    /**
     * Sets the value of the valueT3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValueT3(BigDecimal value) {
        this.valueT3 = value;
    }

    /**
     * Gets the value of the transformationRatio property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTransformationRatio() {
        return transformationRatio;
    }

    /**
     * Sets the value of the transformationRatio property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTransformationRatio(BigDecimal value) {
        this.transformationRatio = value;
    }

    /**
     * Gets the value of the readoutDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReadoutDate() {
        return readoutDate;
    }

    /**
     * Sets the value of the readoutDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReadoutDate(XMLGregorianCalendar value) {
        this.readoutDate = value;
    }

    /**
     * Gets the value of the readingSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReadingSource() {
        return readingSource;
    }

    /**
     * Sets the value of the readingSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReadingSource(String value) {
        this.readingSource = value;
    }

    /**
     * Gets the value of the archivingReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArchivingReason() {
        return archivingReason;
    }

    /**
     * Sets the value of the archivingReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArchivingReason(String value) {
        this.archivingReason = value;
    }

    /**
     * Gets the value of the archivingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArchivingDate() {
        return archivingDate;
    }

    /**
     * Sets the value of the archivingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArchivingDate(XMLGregorianCalendar value) {
        this.archivingDate = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="accountId" type="{}IdType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "accountId"
    })
    public static class Accounts {

        @XmlElement(required = true)
        protected List<String> accountId;

        /**
         * Gets the value of the accountId property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the accountId property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccountId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getAccountId() {
            if (accountId == null) {
                accountId = new ArrayList<String>();
            }
            return this.accountId;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RemoteMeteringInfo" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="2000"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TemperatureSensingElementInfo" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="2000"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="PressureSensingElementInfo" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="2000"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "remoteMeteringInfo",
        "temperatureSensingElementInfo",
        "pressureSensingElementInfo"
    })
    public static class CollectiveDeviceInfo {

        @XmlElement(name = "RemoteMeteringInfo")
        protected String remoteMeteringInfo;
        @XmlElement(name = "TemperatureSensingElementInfo")
        protected String temperatureSensingElementInfo;
        @XmlElement(name = "PressureSensingElementInfo")
        protected String pressureSensingElementInfo;

        /**
         * Gets the value of the remoteMeteringInfo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRemoteMeteringInfo() {
            return remoteMeteringInfo;
        }

        /**
         * Sets the value of the remoteMeteringInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRemoteMeteringInfo(String value) {
            this.remoteMeteringInfo = value;
        }

        /**
         * Gets the value of the temperatureSensingElementInfo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTemperatureSensingElementInfo() {
            return temperatureSensingElementInfo;
        }

        /**
         * Sets the value of the temperatureSensingElementInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTemperatureSensingElementInfo(String value) {
            this.temperatureSensingElementInfo = value;
        }

        /**
         * Gets the value of the pressureSensingElementInfo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPressureSensingElementInfo() {
            return pressureSensingElementInfo;
        }

        /**
         * Sets the value of the pressureSensingElementInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPressureSensingElementInfo(String value) {
            this.pressureSensingElementInfo = value;
        }

    }

}
