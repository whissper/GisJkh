package gisjkh.engines;

import gisjkh.OwnUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import org.whissper.accountflrso.AccountFLRSOType;
import org.whissper.accountflrso.AccountsFLRSOType;
import org.whissper.accountflrso.RespType;

/**
 * AccountFLRSO Engine
 * @author SAV2
 */
public class AccountFLRSOEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 4;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    //fields names
    private static final String RESPONSIBILITY     = "RESPONSIBILITY";
    private static final String SOURCE_OGRN        = "SOURCES_OGRN";
    private static final String RKC_OGRN           = "RKC_OGRN";
    
    private static final String ID_ACCOUNT         = "ID";
    private static final String ID_CONTRACT        = "ID_CONTRACT";
    private static final String CREATION_DATE      = "CREATIONDATE";
    private static final String LIVING_PERSONS_NUM = "LIVINGPERSONNUMBER";
    private static final String CLOSE_DATE         = "CLOSEDATE";
    private static final String CLOSE_REASON       = "CLOSEREASON";
    
    private static final String PREMISE_TYPE       = "PREMISETYPE";
    private static final String PREMISE_ID         = "PREMISEID";
    private static final String FIAS_ID            = "FIASID";
    private static final String HOUSE_ID           = "HOISE_ID";
    private static final String PREMISE_NUM        = "FIELD";//absent
    private static final String ROOM_NUM           = "FIELD";//absent
    
    private static final String IS_RENTER          = "ISRENTER";
    private static final String FIRST_NAME         = "FIRSTNAME";
    private static final String LAST_NAME          = "LASTNAME";
    private static final String MIDDLE_NAME        = "MIDDLENAME";
    private static final String SEX                = "SEX";
    private static final String DATE_OF_BIRTH      = "DATEOFBIRTH";
    private static final String SNILS              = "SNILS";
    private static final String DOC_TYPE           = "DOCTYPE";
    private static final String DOC_SERIES         = "DOCNUMBER";
    private static final String DOC_NUMBER         = "DOCSERIAL";
    private static final String DOC_BEGIN_DATE     = "DOCBEGINDATE";
    private static final String TOTAL_SQUARE       = "TOTAL_SQUARE";
    private static final String RESIDENTIAL_SQUARE = "RESIDENTIAL_SQUARE";
    private static final String HEATED_AREA        = "HEATED_AREA";
    //private static final String ACTION             = "ACT";
    
    //RootElement
    private AccountsFLRSOType accounts;
    private AccountFLRSOType account;
    private AccountFLRSOType.Contracts contract;
            
    public AccountFLRSOEngine() {
        super(
            "org.whissper.accountflrso",//CONTEXT_PATH
            "AccountFLRSO",//ENTITY_CODE
            "xml-resources/jaxb/AccountFLRSO/AccountFLRSO.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_ACCOUNTFLRSO"//QUERY_STRING
        );
    }
    
    private void buildAccountsNode() {
        accounts = new AccountsFLRSOType();
        accounts.setSystemID(SYSTEM_ID);
    }
    
    private void buildAccountNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        accounts.getAccountFL().add(new AccountFLRSOType());
        account = accounts.getAccountFL().get(counter);

        //RC because of temporary decision (each account got this parameter like default)
        if(resultSet.getString(RESPONSIBILITY) != null){
            switch(resultSet.getString(RESPONSIBILITY)){
                case "UK" :
                    account.setResponsibility(RespType.UK);
                    break;
                case "RSO" :
                    account.setResponsibility(RespType.RSO);
                    break;
                case "RC" :
                    account.setResponsibility(RespType.RC);
                    break;    
            } 
        }
        
        if(resultSet.getString(SOURCE_OGRN) != null){
            account.setSourceOGRN(resultSet.getString(SOURCE_OGRN));
        }
        
        if(resultSet.getString(RKC_OGRN) != null){
            account.setRkcOGRN(resultSet.getString(RKC_OGRN));
        }
        
        if(resultSet.getString(ID_ACCOUNT) != null){
            account.setId(resultSet.getString(ID_ACCOUNT));
        }
        
        //empty contracts Node
        contract = new AccountFLRSOType.Contracts();
        /*if(resultSet.getString(ID_CONTRACT) != null){
            AccountFLRSOType.Contracts contract = new AccountFLRSOType.Contracts();
            contract.getContractId().add(resultSet.getString(ID_CONTRACT));
            account.setContracts(contract);
        }*/
        
        if(resultSet.getDate(CREATION_DATE) != null){
            account.setCreationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDatetimeToXMLFormat(resultSet.getDate(CREATION_DATE))) );
        }
        
        if(resultSet.getByte(LIVING_PERSONS_NUM) != 0){
            account.setLivingPersonsNumber(resultSet.getByte(LIVING_PERSONS_NUM));
        }
        
        if(resultSet.getDate(CLOSE_DATE) != null){
            account.setCloseDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(CLOSE_DATE))) );
        }
        
        if(resultSet.getString(CLOSE_REASON) != null){
            account.setCloseReason(resultSet.getString(CLOSE_REASON).trim());
        }
        
        //premise 
        account.getPremises().add(new AccountFLRSOType.Premises());
        AccountFLRSOType.Premises premises = account.getPremises().get(0);
        
        if(resultSet.getString(PREMISE_TYPE) != null){
            premises.setPremiseType(resultSet.getString(PREMISE_TYPE).trim());
        }
        
        if(resultSet.getString(PREMISE_ID) != null){
            premises.setPremiseId( resultSet.getString(PREMISE_ID) );
        }

        if(resultSet.getString(FIAS_ID) != null){
            premises.setFIASID(resultSet.getString(FIAS_ID) );
        }
        
        if(resultSet.getString(HOUSE_ID) != null){
            premises.setHouseId(resultSet.getString(HOUSE_ID) );
        }
        /*
        if(resultSet.getString(PREMISE_NUM) != null){
            premises.setPremiseNum(resultSet.getString(PREMISE_NUM));
        }
        
        if(resultSet.getString(ROOM_NUM) != null){
            premises.setRoomNum(resultSet.getString(ROOM_NUM));
        }
        */ 

        //premise 
        
        if(resultSet.getBoolean(IS_RENTER)){
            account.setIsRenter(Boolean.TRUE);
        }
        
        if(resultSet.getString(FIRST_NAME) != null){
            account.setFirstName(resultSet.getString(FIRST_NAME));
        }
        account.setFirstName("-");//
        
        if(resultSet.getString(LAST_NAME) != null){
            account.setLastName(resultSet.getString(LAST_NAME));
        }
        account.setLastName("-");//
        
        if(resultSet.getString(MIDDLE_NAME) != null){
            account.setMiddleName(resultSet.getString(MIDDLE_NAME));
        }
        
        if(resultSet.getString(SEX) != null){
            account.setSex(resultSet.getString(SEX));
        }
        
        if(resultSet.getDate(DATE_OF_BIRTH) != null){
            account.setDateOfBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(DATE_OF_BIRTH))) );
        }
        
        if(resultSet.getString(SNILS) != null){
            account.setSNILS(resultSet.getString(SNILS));
        }else{
            if(resultSet.getString(DOC_TYPE) != null){
                account.setDocType(resultSet.getString(DOC_TYPE));
            }

            if(resultSet.getString(DOC_SERIES) != null){
                account.setDocSeries(resultSet.getString(DOC_SERIES));
            }

            if(resultSet.getString(DOC_NUMBER) != null){
                account.setDocNumber(resultSet.getString(DOC_NUMBER));
            }

            if(resultSet.getDate(DOC_BEGIN_DATE) != null){
                account.setDocBeginDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(DOC_BEGIN_DATE))) );
            }
        }
        
        if(resultSet.getDouble(TOTAL_SQUARE) != 0){
            account.setTotalSquare(new BigDecimal(resultSet.getDouble(TOTAL_SQUARE)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        
        if(resultSet.getDouble(RESIDENTIAL_SQUARE) != 0){
            account.setResidentialSquare(new BigDecimal(resultSet.getDouble(RESIDENTIAL_SQUARE)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        
        if(resultSet.getDouble(HEATED_AREA) != 0){
            account.setHeatedArea(new BigDecimal(resultSet.getDouble(HEATED_AREA)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        
    }
    
    private void buildContractNode(ResultSet resultSet) throws SQLException, DatatypeConfigurationException {
        if(resultSet.getString(ID_CONTRACT) != null){
            contract.getContractId().add(resultSet.getString(ID_CONTRACT));
            account.setContracts(contract);
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
        /*int count_accounts = 0;
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            
            //<accountFL>
            //...
            //</accountFL>
            
            buildAccountNode(rs, count_accounts);
            count_accounts++;
            
            if( (buffRowsVal != 0) && (count_rows > buffRowsVal) ){
                break;
            }
        }
        */
        int count_accounts = 0;
        String account_id = "-1";//negative value for special purpose when comparing it with first house_id
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            if getting first_row {
                <accountFL>
                    <contracts>
			<contractId>
                        ...
                        </contractId>
                    </contracts>
                    ...
                </accountFL>
            }
            else {
                if next_row contains the same payment_id {
                    <contracts>
			<contractId>
                        ...
                        </contractId>
                    </contracts>
                }
                else {
                    <accountFL>
                        <contracts>
                            <contractId>
                            ...
                            </contractId>
                        </contracts>
                        ...
                    </accountFL>
                }
            }
            */
            if(rs.getRow()==1){
                account_id = rs.getString(ID_ACCOUNT);
                buildAccountNode(rs, count_accounts);
                count_accounts++;
                buildContractNode(rs);
            }
            else{
                if( rs.getString(ID_ACCOUNT).equals(account_id) ){
                    buildContractNode(rs);
                }
                else{
                    account_id = rs.getString(ID_ACCOUNT);
                    buildAccountNode(rs, count_accounts);
                    count_accounts++;
                    buildContractNode(rs);
                }
            }
            
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
        initJAXBElement("accounts", accounts);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
    }

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        
        buildAccountsNode();
        
        xmlStreamWriter.writeStartDocument("utf-8", "1.0");//XML header
        xmlStreamWriter.writeStartElement("accounts");// <accounts>
        xmlStreamWriter.writeStartElement("systemID");// <systemID>
        xmlStreamWriter.writeCharacters(accounts.getSystemID());
        xmlStreamWriter.writeEndElement();// </systemID>
        
        //"buffered" previous Account node data after end of buffered object
        AccountFLRSOType previousAccountNode = null;
        
        while(inProgress)
        {
            accounts.getAccountFL().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);
            
            /*for(AccountFLRSOType accountNode : accounts.getAccountFL()){
                initJAXBElement("accountFL", accountNode);
                marshaller.marshal(jaxbElement, xmlStreamWriter);
            }*/
            
            int countObjectItem = 0;
            
            for(AccountFLRSOType accountNode : accounts.getAccountFL()){
                countObjectItem++;
                
                //the first item
                if(countObjectItem == 1){
                    if(previousAccountNode != null){
                        //prev equals curr
                        if( (previousAccountNode.getId()).equals(accountNode.getId()) ){
                            for(String contractIdNode : accountNode.getContracts().getContractId()){
                                previousAccountNode.getContracts().getContractId().add(contractIdNode);
                            }
                            
                            initJAXBElement("accountFL", previousAccountNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                        }
                        //prev DOESN'T equal curr
                        else{
                            initJAXBElement("accountFL", previousAccountNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                            
                            initJAXBElement("accountFL", accountNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                        }
                    }
                    else{
                        initJAXBElement("accountFL", accountNode);
                        marshaller.marshal(jaxbElement, xmlStreamWriter);
                    }
                }
                //the last item
                else if(countObjectItem == accounts.getAccountFL().size()){
                    //do nothing
                }
                //others
                else{
                    initJAXBElement("accountFL", accountNode);
                    marshaller.marshal(jaxbElement, xmlStreamWriter);
                }
                
                previousAccountNode = accountNode;
            }
        }
        //kinda the most final item
        initJAXBElement("accountFL", previousAccountNode);
        marshaller.marshal(jaxbElement, xmlStreamWriter);
        
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </accounts>
        xmlStreamWriter.writeEndDocument();
    }
    
}
