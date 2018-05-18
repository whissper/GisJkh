package gisjkh.engines;

import gisjkh.OwnUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import org.whissper.contractrso.AttachmentType;
import org.whissper.contractrso.ContractType;
import org.whissper.contractrso.ContractType.Contract;
import org.whissper.contractrso.ContractType.Period;
import org.whissper.contractrso.ContractType.SecondSide;
import org.whissper.contractrso.ContractsType;

/**
 * ContractRSOEngine class
 * @author SAV2
 */
public class ContractRSOEngine extends AbstractEngine
{
    private boolean inProgress = true;
    
    //fields
    private static final String ID = "ID";
    private static final String COMPTETION_DATE = "COMPTETION_DATE";
    private static final String START_DATE = "START_DATE";
    private static final String START_NEXT_MONTH = "START_NEXT_MONTH";
    private static final String END_DATE = "END_DATE";
    private static final String END_NEXT_MONTH = "END_NEXT_MONTH";
    private static final String OFFER = "OFFER";
    private static final String HOUSE_TYPE = "HOUSE_TYPE";
    private static final String HOUSEID = "HOUSEID";
    private static final String APARTMENTNUMBER = "APARTMENTNUMBER";
    private static final String ROOMNUMBER = "ROOMNUMBER";
    private static final String SERVICE_TYPE = "SERVICE_TYPE";
    private static final String MUNICIPAL_RESOURCE = "MUNICIPAL_RESOURCE";
    private static final String START_SUPPLY_DATE = "START_SUPPLY_DATE";
    private static final String END_SUPPLY_DATE = "END_SUPPLY_DATE";
    private static final String OPENORNOT = "OPENORNOT";
    private static final String CENTRALIZEDORNOT = "CENTRALIZEDORNOT";
    private static final String ISCONTRACT = "ISCONTRACT";
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String BILLING_DATE = "BILLING_DATE";
    private static final String PAYMENT_DATE = "PAYMENT_DATE";
    private static final String ACTION = "ACTION";

    public ContractRSOEngine() {
        super(
            "org.whissper.contractrso",//CONTEXT_PATH
            "ContractRSO",//ENTITY_CODE
            "xml-resources/jaxb/ContractRSO/ContractRSO.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_CONTRACTRSO"//QUERY_STRING
        );
    }
    
    private ContractsType contracts;
    private ContractType contract;
    
    private void buildContractsNode() {
        contracts = new ContractsType();
        contracts.setSystemID(SYSTEM_ID);
    }
    
    private void buildContractNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        contracts.getContract().add(new ContractType());
        contract = contracts.getContract().get(counter);
        
        if(resultSet.getString(ID) != null){
            contract.setId(resultSet.getString(ID));
        }
        
        if(resultSet.getDate(COMPTETION_DATE) != null){
            contract.setComptetionDate( DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(COMPTETION_DATE))) );
        }
        
        Period.Start start = new Period.Start();
        if(resultSet.getByte(START_DATE) != 0){
            start.setStartDate(resultSet.getByte(START_DATE));
        }
        if(resultSet.getBoolean(START_NEXT_MONTH)){
            start.setNextMonth(resultSet.getBoolean(START_NEXT_MONTH));
        }
        
        Period.End end = new Period.End();
        if(resultSet.getByte(END_DATE) != 0){
            end.setEndDate(resultSet.getByte(END_DATE));
        }
        if(resultSet.getBoolean(END_NEXT_MONTH)){
            end.setNextMonth(resultSet.getBoolean(END_NEXT_MONTH));
        }
        
        Period period = new Period();
        period.setStart(start);
        period.setEnd(end);
        
        contract.setPeriod(period);
        
        SecondSide secondSide = new SecondSide();
        if(resultSet.getBoolean(OFFER)){
            secondSide.setOffer(resultSet.getBoolean(OFFER));
        }
        //otherwise -- secondSide.setSides(sides);
        
        contract.setSecondSide(secondSide);
        
        contract.getObjectAddress().add(new ContractType.ObjectAddress());
        
        if(resultSet.getString(HOUSE_TYPE) != null){
            contract.getObjectAddress().get(0).setHouseType(resultSet.getString(HOUSE_TYPE));
        }
        
        if(resultSet.getString(HOUSEID) != null){
            contract.getObjectAddress().get(0).setHouseID(resultSet.getString(HOUSEID));
        }
        
        if(resultSet.getString(APARTMENTNUMBER) != null){
            contract.getObjectAddress().get(0).setApartmentNumber(resultSet.getString(APARTMENTNUMBER));
        }
        
        if(resultSet.getByte(ROOMNUMBER) != 0){
            contract.getObjectAddress().get(0).setRoomNumber(resultSet.getByte(ROOMNUMBER));
        }
        
        Contract contractInfo = new Contract();
        contractInfo.setIsContract(resultSet.getBoolean(ISCONTRACT));
        contract.setContract(contractInfo);
        
        if( resultSet.getString(NAME) != null ||
            resultSet.getString(DESCRIPTION) != null
        ){
            contract.getContract().getContractAttachment().add(new AttachmentType());
            
            if(resultSet.getString(NAME) != null){
                contract.getContract().getContractAttachment().get(0).setName(resultSet.getString(NAME).trim());
            }
            
            if(resultSet.getString(DESCRIPTION) != null){
                contract.getContract().getContractAttachment().get(0).setDescription(resultSet.getString(DESCRIPTION).trim());
            }
            //temp solution
            contract.getContract().getContractAttachment().get(0).setAttachmentID("1");
            contract.getContract().getContractAttachment().get(0).setAttachmentHASH("1");
        }
        
        if(resultSet.getByte(BILLING_DATE) != 0){
            contract.getContract().setBillingDate(resultSet.getByte(BILLING_DATE));
        }

        if(resultSet.getByte(PAYMENT_DATE) != 0){
            contract.getContract().setPaymentDate(resultSet.getByte(PAYMENT_DATE));
        }
        
        if(resultSet.getString(ACTION) != null){
            contract.setAction(resultSet.getString(ACTION));
        }
    }
    
    private void buildPairNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        contract.getObjectAddress().get(0).getPair().add(new ContractType.ObjectAddress.Pair());
        
        if(resultSet.getString(SERVICE_TYPE) != null){
            contract.getObjectAddress().get(0).getPair().get(counter).setServiceType(resultSet.getString(SERVICE_TYPE));
        }
        
        if(resultSet.getString(MUNICIPAL_RESOURCE) != null){
            contract.getObjectAddress().get(0).getPair().get(counter).setMunicipalResource(resultSet.getString(MUNICIPAL_RESOURCE));
        }
        
        if(resultSet.getDate(START_SUPPLY_DATE) != null){
            contract.getObjectAddress().get(0).getPair().get(counter).setStartSupplyDate( DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(START_SUPPLY_DATE))) );
        }
        
        if(resultSet.getDate(END_SUPPLY_DATE) != null){
            contract.getObjectAddress().get(0).getPair().get(counter).setEndSupplyDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(END_SUPPLY_DATE))) );
        }
        
        contract.getObjectAddress().get(0).getPair().get(counter).setHeatingSystemType(new ContractType.ObjectAddress.Pair.HeatingSystemType());
        
        if(resultSet.getString(OPENORNOT) != null){
            contract.getObjectAddress().get(0).getPair().get(counter).getHeatingSystemType().setOpenOrNot(resultSet.getString(OPENORNOT));
        }
        
        if(resultSet.getString(CENTRALIZEDORNOT) != null){
            contract.getObjectAddress().get(0).getPair().get(counter).getHeatingSystemType().setCentralizedOrNot(resultSet.getString(CENTRALIZEDORNOT));
        }
    }
    
    private void buildContractSubjectNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        contract.getContract().getContractSubject().add(new ContractType.Contract.ContractSubject());
        
        if(resultSet.getString(SERVICE_TYPE) != null){
            contract.getContract().getContractSubject().get(counter).setServiceType(resultSet.getString(SERVICE_TYPE));
        }
        
        if(resultSet.getString(MUNICIPAL_RESOURCE) != null){
            contract.getContract().getContractSubject().get(counter).setMunicipalResource(resultSet.getString(MUNICIPAL_RESOURCE));
        }
        
        if(resultSet.getDate(START_SUPPLY_DATE) != null){
            contract.getContract().getContractSubject().get(counter).setStartSupplyDate( DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(START_SUPPLY_DATE))) );
        }
        
        if(resultSet.getDate(END_SUPPLY_DATE) != null){
            contract.getContract().getContractSubject().get(counter).setEndSupplyDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(END_SUPPLY_DATE))) );
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
        int count_contracts = 0;
        int count_pairs = 0;
        String contract_id = "-1";//negative value for special purpose when comparing it with first house_id
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            if getting first_row {
                <contract>
                    <ObjectAddress>
                        ...
			<Pair>
                        ...
                        </Pair>
                    </ObjectAddress>
                    ...
                    <Contract>
                        ...
			<ContractSubject>
                        ...
                        </ContractSubject>
                    </Contract>
                    ...
                </contract>
            }
            else {
                if next_row contains the same payment_id {
                    <ObjectAddress>
                        ...
			<Pair>
                        ...
                        </Pair>
                    </ObjectAddress>
            
                    <Contract>
                        ...
			<ContractSubject>
                        ...
                        </ContractSubject>
                    </Contract>
                }
                else {
                    <contract>
                        <ObjectAddress>
                            ...
                            <Pair>
                            ...
                            </Pair>
                        </ObjectAddress>
                        ...
                        <Contract>
                            ...
                            <ContractSubject>
                            ...
                            </ContractSubject>
                        </Contract>
                        ...
                    </contract>
                }
            }
            */
            if(rs.getRow()==1){
                contract_id = rs.getString(ID);
                buildContractNode(rs, count_contracts);
                count_contracts++;
                buildPairNode(rs, count_pairs);
                buildContractSubjectNode(rs, count_pairs);
                count_pairs++;
            }
            else{
                if( rs.getString(ID).equals(contract_id) ){
                    buildPairNode(rs, count_pairs);
                    buildContractSubjectNode(rs, count_pairs);
                    count_pairs++;
                }
                else{
                    count_pairs=0;
                    contract_id = rs.getString(ID);
                    buildContractNode(rs, count_contracts);
                    count_contracts++;
                    buildPairNode(rs, count_pairs);
                    buildContractSubjectNode(rs, count_pairs);
                    count_pairs++;
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
        buildContractsNode();
        //build object
        buildObject(resultSet, 0);
        //terminate connection
        terminateConnection();
        //init jaxb Element
        initJAXBElement("contracts", contracts);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
    }

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        
        buildContractsNode();
        
        xmlStreamWriter.writeStartDocument("utf-8", "1.0");//XML header
        xmlStreamWriter.writeStartElement("contracts");// <contracts>
        xmlStreamWriter.writeStartElement("systemID");// <systemID>
        xmlStreamWriter.writeCharacters(contracts.getSystemID());
        xmlStreamWriter.writeEndElement();// </systemID>
        
        //"buffered" previous contract node data after end of buffered object
        ContractType previousContractNode = null;
        
        while(inProgress)
        {
            contracts.getContract().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);
            
            int countObjectItem = 0;
            
            for(ContractType contractNode : contracts.getContract()){
                countObjectItem++;
                
                //the first item
                if(countObjectItem == 1){
                    if(previousContractNode != null){
                        //prev equals curr
                        if( (previousContractNode.getId()).equals(contractNode.getId()) )
                        {
                            for(ContractType.ObjectAddress.Pair pairNode : contractNode.getObjectAddress().get(0).getPair()){
                                previousContractNode.getObjectAddress().get(0).getPair().add(pairNode);
                            }

                            for(ContractType.Contract.ContractSubject contractSubjectNode : contractNode.getContract().getContractSubject()){
                                previousContractNode.getContract().getContractSubject().add(contractSubjectNode);
                            }

                            initJAXBElement("contract", previousContractNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                        }
                        //prev DOESN'T equal curr
                        else{
                            initJAXBElement("contract", previousContractNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);

                            initJAXBElement("contract", contractNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                        }
                    }
                    else{
                        initJAXBElement("contract", contractNode);
                        marshaller.marshal(jaxbElement, xmlStreamWriter);
                    }
                }
                //the last item
                else if(countObjectItem == contracts.getContract().size()){
                    //do nothing
                }
                //others
                else{
                    initJAXBElement("contract", contractNode);
                    marshaller.marshal(jaxbElement, xmlStreamWriter);
                }
                
                previousContractNode = contractNode;
            }
        }
        //kinda the most final item
        initJAXBElement("contract", previousContractNode);
        marshaller.marshal(jaxbElement, xmlStreamWriter);
        
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </contracts>
        xmlStreamWriter.writeEndDocument();
    }
      
}
