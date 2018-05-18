//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.01 at 02:33:26 PM MSK 
//


package org.whissper.houses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HouseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HouseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="houseType" type="{}TypeHouse"/>
 *         &lt;element name="FIASID" type="{}GUIDType"/>
 *         &lt;element name="id" type="{}IdType"/>
 *         &lt;element name="OKTMO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="11"/>
 *               &lt;pattern value="\d{11}|\d{8}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="cadastralInfo" type="{}CadastralInfoType"/>
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
@XmlType(name = "HouseType", propOrder = {
    "houseType",
    "fiasid",
    "id",
    "oktmo",
    "cadastralInfo",
    "action"
})
public class HouseType {

    @XmlElement(required = true)
    protected String houseType;
    @XmlElement(name = "FIASID", required = true)
    protected String fiasid;
    @XmlElement(required = true)
    protected String id;
    @XmlElement(name = "OKTMO", required = true)
    protected String oktmo;
    @XmlElement(required = true)
    protected CadastralInfoType cadastralInfo;
    @XmlElement(required = true)
    protected String action;

    /**
     * Gets the value of the houseType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseType() {
        return houseType;
    }

    /**
     * Sets the value of the houseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseType(String value) {
        this.houseType = value;
    }

    /**
     * Gets the value of the fiasid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIASID() {
        return fiasid;
    }

    /**
     * Sets the value of the fiasid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIASID(String value) {
        this.fiasid = value;
    }

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
     * Gets the value of the oktmo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOKTMO() {
        return oktmo;
    }

    /**
     * Sets the value of the oktmo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOKTMO(String value) {
        this.oktmo = value;
    }

    /**
     * Gets the value of the cadastralInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CadastralInfoType }
     *     
     */
    public CadastralInfoType getCadastralInfo() {
        return cadastralInfo;
    }

    /**
     * Sets the value of the cadastralInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CadastralInfoType }
     *     
     */
    public void setCadastralInfo(CadastralInfoType value) {
        this.cadastralInfo = value;
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

}
