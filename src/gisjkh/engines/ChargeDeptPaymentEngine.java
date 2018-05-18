package gisjkh.engines;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.whissper.chargedeptpayment.PaymentType;
import org.whissper.chargedeptpayment.PaymentsType;
import org.whissper.chargedeptpayment.ServiceDebtType;
import org.whissper.chargedeptpayment.ServiceSingleDebtType;

/**
 * ChargeDeptPaymentEngine class
 * @author SAV2
 */
public class ChargeDeptPaymentEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 2;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    //fields
    private static final String MONTH = "I_MONTH";
    private static final String YEAR = "I_YEAR";
    private static final String ID = "ID";
    private static final String BANK_ACCOUNT_ID = "BANK_ACCOUNT_ID";
    private static final String ACCOUNT_ID = "ACCOUNT_ID";
    private static final String PAYMENT_DOCUMENT_NUMBER = "PAYMENT_DOCUMENT_NUMBER";
    private static final String CHARGEDEPT_MONTH = "CHARGEDEPT_MONTH";
    private static final String CHARGEDEPT_YEAR = "CHARGEDEPT_YEAR";
    private static final String CHARGEDEPT_CODESERVICE = "CHARGEDEPT_CODESERVICE";
    private static final String CHARGEDEPT_TOTALPAYABLE = "CHARGEDEPT_TOTALPAYABLE";
    private static final String DEBT_PREVIOUS_PERIODS = "DEBT_PREVIOUS_PERIODS";
    private static final String ADVANCE_BLLING_PERIOD = "ADVANCE_BLLING_PERIOD";
    private static final String TOTAL_PIECEMEAL_PAYMENT_SUM = "TOTAL_PIECEMEAL_PAYMENT_SUM";
    private static final String EXPOSE = "EXPOSE";
    private static final String ACTION = "ACT";

    public ChargeDeptPaymentEngine() {
        super(
            "org.whissper.chargedeptpayment",//CONTEXT_PATH
            "ChargeDeptPayment",//ENTITY_CODE
            "xml-resources/jaxb/ChargeDeptPayment/ChargeDeptPayment.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_CHARGEDEPTPAYMENT"//QUERY_STRING
        );
    }
    
    private PaymentsType payments;
    private PaymentType payment;
    
    private void buildPaymentsNode() {
        payments = new PaymentsType();
        payments.setSystemID(SYSTEM_ID);
    }
    
    private void buildPaymentNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        if(resultSet.getRow() == 1){
            payments.setMonth(resultSet.getInt(MONTH));
            payments.setYear(resultSet.getShort(YEAR));
        }
        
        payments.getPayment().add(new PaymentType());
        payment = payments.getPayment().get(counter);
        
        if(resultSet.getString(ID) != null){
            payment.setId(resultSet.getString(ID));
        }
        
        if(resultSet.getString(BANK_ACCOUNT_ID) != null){
            payment.setBankAccountId(resultSet.getString(BANK_ACCOUNT_ID));
        }
        
        if(resultSet.getString(ACCOUNT_ID) != null){
            payment.setAccountId(resultSet.getString(ACCOUNT_ID));
        }
        
        if(resultSet.getString(PAYMENT_DOCUMENT_NUMBER) != null){
            payment.setPaymentDocumentNumber(resultSet.getString(PAYMENT_DOCUMENT_NUMBER));
        }
        
        if(resultSet.getDouble(DEBT_PREVIOUS_PERIODS) != 0){
            payment.setDebtPreviousPeriods(new BigDecimal(resultSet.getDouble(DEBT_PREVIOUS_PERIODS)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(ADVANCE_BLLING_PERIOD) != 0){
            payment.setAdvanceBllingPeriod(new BigDecimal(resultSet.getDouble(ADVANCE_BLLING_PERIOD)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(TOTAL_PIECEMEAL_PAYMENT_SUM) != 0){
            payment.setTotalPiecemealPaymentSum(new BigDecimal(resultSet.getDouble(TOTAL_PIECEMEAL_PAYMENT_SUM)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        payment.setExpose(resultSet.getBoolean(EXPOSE));
        
        if(resultSet.getString(ACTION) != null){
            payment.setAction(resultSet.getString(ACTION).trim());
        }
    }
    
    private void buildChargeServiceNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        //<ChargeService>
	//  <municipalService>
        payment.getChargeService().add(new ServiceDebtType());
        ServiceDebtType service = payment.getChargeService().get(counter);
        
        ServiceSingleDebtType municipalService = new ServiceSingleDebtType();
        
        municipalService.setMonth(resultSet.getInt(CHARGEDEPT_MONTH));
        
        municipalService.setYear(resultSet.getShort(CHARGEDEPT_YEAR));
        
        if(resultSet.getString(CHARGEDEPT_CODESERVICE) != null){
            municipalService.setCodeService(resultSet.getString(CHARGEDEPT_CODESERVICE).trim());
        }
        
        municipalService.setTotalPayable(new BigDecimal(resultSet.getDouble(CHARGEDEPT_TOTALPAYABLE)).setScale(ROUND_SCALE, ROUND_MODE));
        
        service.setMunicipalService(municipalService);
        //  </municipalService>
	//</ChargeService>
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
        int count_payments = 0;
        int count_chargeservices = 0;
        String payment_id = "-1";//negative value for special purpose when comparing it with first house_id
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            if getting first_row {
                <payment>
                    <ChargeService>
			<municipalService>
                        ...
                        </municipalService>
                    </ChargeService>
                    ...
                </payment>
            }
            else {
                if next_row contains the same payment_id {
                    <ChargeService>
			<municipalService>
                        ...
                        </municipalService>
                    </ChargeService>
                }
                else {
                    <payment>
                        <ChargeService>
                            <municipalService>
                            ...
                            </municipalService>
                        </ChargeService>
                        ...
                    </payment>
                }
            }
            */
            if(rs.getRow()==1){
                payment_id = rs.getString(ID);
                buildPaymentNode(rs, count_payments);
                count_payments++;
                buildChargeServiceNode(rs, count_chargeservices);
                count_chargeservices++;
            }
            else{
                if( rs.getString(ID).equals(payment_id) ){
                    buildChargeServiceNode(rs, count_chargeservices);
                    count_chargeservices++;
                }
                else{
                    count_chargeservices=0;
                    payment_id = rs.getString(ID);
                    buildPaymentNode(rs, count_payments);
                    count_payments++;
                    buildChargeServiceNode(rs, count_chargeservices);
                    count_chargeservices++;
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
        buildPaymentsNode();
        //build object
        buildObject(resultSet, 0);
        //terminate connection
        terminateConnection();
        //init jaxb Element
        initJAXBElement("payments", payments);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
    }

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {

        buildPaymentsNode();
        
        xmlStreamWriter.writeStartDocument("utf-8", "1.0");//XML header
        xmlStreamWriter.writeStartElement("payments");// <payments>
        xmlStreamWriter.writeStartElement("systemID");// <systemID>
        xmlStreamWriter.writeCharacters(payments.getSystemID());
        xmlStreamWriter.writeEndElement();// </systemID> 
        
        boolean isStartDocument = true;
        
        //"buffered" previous payment node data after end of buffered object
        PaymentType previousPaymentNode = null;
        
        while(inProgress)
        {
            payments.getPayment().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);
            
            int countObjectItem = 0;
            
            if(isStartDocument){
                isStartDocument = false;
                xmlStreamWriter.writeStartElement("month");// <month>
                xmlStreamWriter.writeCharacters( Integer.toString(payments.getMonth()) );
                xmlStreamWriter.writeEndElement();// </month>
                xmlStreamWriter.writeStartElement("year");// <year>
                xmlStreamWriter.writeCharacters( Integer.toString(payments.getYear()) );
                xmlStreamWriter.writeEndElement();// </year>
            }
            
            for(PaymentType paymentNode : payments.getPayment()){
                countObjectItem++;
                
                //the first item
                if(countObjectItem == 1){
                    if(previousPaymentNode != null){
                        //prev equals curr
                        if( (previousPaymentNode.getId()).equals(paymentNode.getId()) )
                        {
                            for(ServiceDebtType serviceNode : paymentNode.getChargeService()){
                                previousPaymentNode.getChargeService().add(serviceNode);
                            }

                            initJAXBElement("payment", previousPaymentNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                        }
                        //prev DOESN'T equal curr
                        else{
                            initJAXBElement("payment", previousPaymentNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                            
                            initJAXBElement("payment", paymentNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                        }
                    }
                    else{
                        initJAXBElement("payment", paymentNode);
                        marshaller.marshal(jaxbElement, xmlStreamWriter);
                    }
                }
                //the last item
                else if(countObjectItem == payments.getPayment().size()){
                    //do nothing
                }
                //others
                else{
                    initJAXBElement("payment", paymentNode);
                    marshaller.marshal(jaxbElement, xmlStreamWriter);
                }
                
                previousPaymentNode = paymentNode;
            }
        }
        //kinda the most final item
        initJAXBElement("payment", previousPaymentNode);
        marshaller.marshal(jaxbElement, xmlStreamWriter);
        
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </payments>
        xmlStreamWriter.writeEndDocument();
    }
    
}
