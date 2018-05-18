//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.01 at 02:33:32 PM MSK 
//


package org.whissper.accountflrso;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.whissper.accountflrso package. 
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

    private final static QName _Accounts_QNAME = new QName("", "accounts");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.whissper.accountflrso
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AccountFLRSOType }
     * 
     */
    public AccountFLRSOType createAccountFLRSOType() {
        return new AccountFLRSOType();
    }

    /**
     * Create an instance of {@link AccountsFLRSOType }
     * 
     */
    public AccountsFLRSOType createAccountsFLRSOType() {
        return new AccountsFLRSOType();
    }

    /**
     * Create an instance of {@link AccountFLRSOType.Contracts }
     * 
     */
    public AccountFLRSOType.Contracts createAccountFLRSOTypeContracts() {
        return new AccountFLRSOType.Contracts();
    }

    /**
     * Create an instance of {@link AccountFLRSOType.Premises }
     * 
     */
    public AccountFLRSOType.Premises createAccountFLRSOTypePremises() {
        return new AccountFLRSOType.Premises();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountsFLRSOType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "accounts")
    public JAXBElement<AccountsFLRSOType> createAccounts(AccountsFLRSOType value) {
        return new JAXBElement<AccountsFLRSOType>(_Accounts_QNAME, AccountsFLRSOType.class, null, value);
    }

}
