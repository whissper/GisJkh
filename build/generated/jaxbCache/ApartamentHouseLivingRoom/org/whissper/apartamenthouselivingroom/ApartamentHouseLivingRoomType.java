//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.01 at 02:33:18 PM MSK 
//


package org.whissper.apartamenthouselivingroom;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ApartamentHouseLivingRoomType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApartamentHouseLivingRoomType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{}IdType"/>
 *         &lt;element name="cadastralInfo" type="{}CadastralInfoType"/>
 *         &lt;element name="premiseNum" type="{}PremisesNumType"/>
 *         &lt;element name="parentId" type="{}IdType" minOccurs="0"/>
 *         &lt;element name="entranceNum" type="{}EntranceNumType"/>
 *         &lt;element name="roomNumber" type="{}RoomNumberType"/>
 *         &lt;element name="square" type="{}SquareType"/>
 *         &lt;element name="terminationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
@XmlType(name = "ApartamentHouseLivingRoomType", propOrder = {
    "id",
    "cadastralInfo",
    "premiseNum",
    "parentId",
    "entranceNum",
    "roomNumber",
    "square",
    "terminationDate",
    "action"
})
public class ApartamentHouseLivingRoomType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected CadastralInfoType cadastralInfo;
    @XmlElement(required = true)
    protected String premiseNum;
    protected String parentId;
    protected byte entranceNum;
    @XmlElement(required = true)
    protected String roomNumber;
    @XmlElement(required = true)
    protected BigDecimal square;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar terminationDate;
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
     * Gets the value of the premiseNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremiseNum() {
        return premiseNum;
    }

    /**
     * Sets the value of the premiseNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremiseNum(String value) {
        this.premiseNum = value;
    }

    /**
     * Gets the value of the parentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Sets the value of the parentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentId(String value) {
        this.parentId = value;
    }

    /**
     * Gets the value of the entranceNum property.
     * 
     */
    public byte getEntranceNum() {
        return entranceNum;
    }

    /**
     * Sets the value of the entranceNum property.
     * 
     */
    public void setEntranceNum(byte value) {
        this.entranceNum = value;
    }

    /**
     * Gets the value of the roomNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the value of the roomNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomNumber(String value) {
        this.roomNumber = value;
    }

    /**
     * Gets the value of the square property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSquare() {
        return square;
    }

    /**
     * Sets the value of the square property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSquare(BigDecimal value) {
        this.square = value;
    }

    /**
     * Gets the value of the terminationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTerminationDate() {
        return terminationDate;
    }

    /**
     * Sets the value of the terminationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTerminationDate(XMLGregorianCalendar value) {
        this.terminationDate = value;
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