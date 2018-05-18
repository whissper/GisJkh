package gisjkh.engines;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.whissper.bankaccounts.BankAccountType;
import org.whissper.bankaccounts.BankAccountsType;
import org.whissper.bankaccounts.PaymentInformationType;

/**
 * BankAccountsEngine class
 * @author SAV2
 */
public class BankAccountsEngine extends AbstractEngine
{
    private boolean inProgress = true;
    
    //fields
    private static final String ID = "ID";
    private static final String RECIPIENT_INN = "RECIPIENTINN";
    private static final String RECIPIENT_KPP = "RECIPIENTKPP";
    private static final String BANK_NAME = "BANKNAME";
    private static final String PAYMENT_RECIPIENT = "PAIMENTRECIPIENT";
    private static final String BANK_BIC = "BANKBIC";
    private static final String OPERATING_ACCOUNT_NUMBER = "OPERATIONACCOUNTNUMBER";
    private static final String CORRESPONDENT_BANK_ACCOUNT = "CORRESPONDENTBANKACCOUNT";
    private static final String ACTION = "ACT";
    
    private BankAccountsType accounts;
    
    public BankAccountsEngine() {
        super(
            "org.whissper.bankaccounts",//CONTEXT_PATH
            "BankAccounts",//ENTITY_CODE
            "xml-resources/jaxb/BankAccounts/BankAccounts.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_BANKACCOUNT"//QUERY_STRING
        );
    }
    
    private void buildAccountsNode() {
        accounts = new BankAccountsType();
        accounts.setSystemID(SYSTEM_ID);
    }
    
    private void buildAccountNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        accounts.getBankAccount().add(new BankAccountType());
        BankAccountType account = accounts.getBankAccount().get(counter);
        
        if(resultSet.getString(ID) != null){
            account.setId(resultSet.getString(ID));
        }
        
        PaymentInformationType paymentInfo = new PaymentInformationType();
        account.setAccount(paymentInfo);
        
        if(resultSet.getString(RECIPIENT_INN) != null){
            paymentInfo.setRecipientINN(resultSet.getString(RECIPIENT_INN));
        }
        
        if(resultSet.getString(RECIPIENT_KPP) != null){
            paymentInfo.setRecipientKPP(resultSet.getString(RECIPIENT_KPP));
        }
        
        if(resultSet.getString(BANK_NAME) != null){
            paymentInfo.setBankName(resultSet.getString(BANK_NAME));
        }
        
        if(resultSet.getString(PAYMENT_RECIPIENT) != null){
            paymentInfo.setPaymentRecipient(resultSet.getString(PAYMENT_RECIPIENT));
        }
        
        if(resultSet.getString(BANK_BIC) != null){
            paymentInfo.setBankBIC(resultSet.getString(BANK_BIC));
        }
        
        if(resultSet.getString(OPERATING_ACCOUNT_NUMBER) != null){
            paymentInfo.setOperatingAccountNumber(resultSet.getString(OPERATING_ACCOUNT_NUMBER));
        }
        
        if(resultSet.getString(CORRESPONDENT_BANK_ACCOUNT) != null){
            paymentInfo.setCorrespondentBankAccount(resultSet.getString(CORRESPONDENT_BANK_ACCOUNT));
        }
        
        if(resultSet.getString(ACTION) != null){
            paymentInfo.setAction(resultSet.getString(ACTION).trim());
        }
    }
    
    /**
     * build Object from ResultSet data
     * i.e. fill "RootElement" with Lists
     * @param rs ResultSet
     * @param buffRowsVal Buffered Rows Value -- i.e. amount of rows to build "buffered" object
     *                    if buffRowsVal = 0 that means fetch full ResultSet
     * @throws SQLException
     * @throws DatatypeConfigurationException 
     */
    private void buildObject(ResultSet rs, int buffRowsVal) throws SQLException, DatatypeConfigurationException {
        int count_accounts = 0;
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            <bankAccount>
            ...
            </bankAccount>
            */
            buildAccountNode(rs, count_accounts);
            count_accounts++;
            
            if( (buffRowsVal != 0) && (count_rows > buffRowsVal) ){
                break;
            }
        }
    }
    
    @Override
    protected void processForBuild() throws SQLException, DatatypeConfigurationException, JAXBException {
        //RootElement
        buildAccountsNode();
        //build object
        buildObject(resultSet, 0);
        //terminate connection
        terminateConnection();
        //init jaxb Element
        initJAXBElement("bankAccounts", accounts);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
    }

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
