//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.01 at 02:33:21 PM MSK 
//


package org.whissper.meteringdevices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.whissper.meteringdevices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Devices_QNAME = new QName("", "devices");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.whissper.meteringdevices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeviceType }
     * 
     */
    public DeviceType createDeviceType() {
        return new DeviceType();
    }

    /**
     * Create an instance of {@link DevicesType }
     * 
     */
    public DevicesType createDevicesType() {
        return new DevicesType();
    }

    /**
     * Create an instance of {@link DeviceType.CollectiveDeviceInfo }
     * 
     */
    public DeviceType.CollectiveDeviceInfo createDeviceTypeCollectiveDeviceInfo() {
        return new DeviceType.CollectiveDeviceInfo();
    }

    /**
     * Create an instance of {@link DeviceType.Accounts }
     * 
     */
    public DeviceType.Accounts createDeviceTypeAccounts() {
        return new DeviceType.Accounts();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DevicesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "devices")
    public JAXBElement<DevicesType> createDevices(DevicesType value) {
        return new JAXBElement<DevicesType>(_Devices_QNAME, DevicesType.class, null, value);
    }

}
