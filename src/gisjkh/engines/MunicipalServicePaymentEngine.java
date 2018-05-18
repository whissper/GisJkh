package gisjkh.engines;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.whissper.municipalservicepayment.PaymentType;
import org.whissper.municipalservicepayment.PaymentsType;

/**
 * MunicipalServicePaymentEngine class
 * @author SAV2
 */
public class MunicipalServicePaymentEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 2;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    //fields
    private static final String MONTH = "I_MONTH";
    private static final String YEAR = "I_YEAR";
    private static final String ID = "ID";
    private static final String ACCOUNT_ID = "ACCOUNTID";
    private static final String BANK_ACCOUNT_ID = "BANKACCOUNTID";
    private static final String PAYMENT_DOCUMENT_NUMBER = "PAYMENTDOCUMENTNUMBER";
    private static final String CODE_SERVICE = "CODESERVICE";
    private static final String RATE = "RATE";
    private static final String TOTAL_PAYABLE = "TOTALPAYABLE";
    private static final String ACCOUNTING_PERIOD_TOTAL = "ACCOUNTINGPERIODTOTAL";
    private static final String CALC_EXPLANATION = "CALCEXPLANATION";
    private static final String MONEY_RECALCULATION = "MONEYRECALCULATION";
    
    //private static final String INDIVIDUAL_CONSUMPTION_CURRENT_VALUE = "FIELD";//absent
    //private static final String HOUSEOVERALL_NEEDS_CURRENT_VALUE = "FIELD";//absent
    
    private static final String HOUSE_TOTAL_INDIVIDUAL_CONSUMPTION = "HOUSE_TOTAL_INDIVID";
    private static final String HOUSE_TOTAL_HOUSEOVERALL_NEEDS = "HOUSE_TOTAL_ODN";
    
    private static final String HOUSEOVERALL_NEEDS_NORM = "ODN_NORM";
    private static final String INDIVIDUAL_CONSUMPTION_NORM = "INDIVIDUAL_NORM";
    
    private static final String VOLUME1 = "VOLUME1";
    private static final String VOLUME2 = "VOLUME2";

    private static final String MUNICIPAL_SERVICE_INDIVIDUAL_CONSUMPTION_PAYABLE = "M_SERVICE_INDIVIDUAL_OPL";
    private static final String MUNICIPAL_SERVICE_COMMUNAL_CONSUMPTION_PAYABLE = "M_SERVICE_ODN_OPL";
    
    private static final String DEBT_PREVIOUS_PERIODS = "DEBT_PREVIOUS_PERIODS";
    private static final String ADVANCE_BLLING_PERIOD = "ADVANCE_BILLING_PERIOD";
    private static final String TOTAL_PAYMENT_SUM = "TOTALPAYMENTSUM";
    
    private static final String EXPOSE = "EXPOSE";
    private static final String ACTION = "ACT";
    
    //other "PEACEMEAL..." shit
    private static final String PAYMENT_PERIOD_PIECEMEAL_PAYMENT_SUM = "PAYMENTPERIODPIECEMEALPAYMENTSU";
    private static final String PAST_PAYMENT_PERIOD_PIECEMEAL_PAYMENT_SUM = "PASTPAYMENTPERIODPIECEMEALPAYME";
    private static final String PIECEMEAL_PAYMENT_PERCENT_RUB = "PIECEMEALPAYMENTPERCENTRUB";
    private static final String PIECEMEAL_PAYMENT_PERCENT = "PIECEMEALPAYMENTPERCENT";
    private static final String PIECE_MEAL_PAYMENT_SUM = "PIECEMEALPAYMENTSUM";
    private static final String RECALCULATION_REASON = "RECALCULATION_REASON";
    private static final String RECALCULATION_SUM = "RECALCULATION_SUM";
    
    private PaymentsType payments;

    public MunicipalServicePaymentEngine() {
        super(
            "org.whissper.municipalservicepayment",//CONTEXT_PATH
            "MunicipalServicePayment",//ENTITY_CODE
            "xml-resources/jaxb/MunicipalServicePayment/MunicipalServicePayment.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_MUNICIPALSERVICEPAYMENT"//QUERY_STRING
        );
    }
    
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
        PaymentType payment = payments.getPayment().get(counter);
        
        if(resultSet.getString(ID) != null){
            payment.setId(resultSet.getString(ID));
        }
        
        if(resultSet.getString(ACCOUNT_ID) != null){
            payment.setAccountId(resultSet.getString(ACCOUNT_ID));
        }
        
        if(resultSet.getString(BANK_ACCOUNT_ID) != null){
            payment.setBankAccountId(resultSet.getString(BANK_ACCOUNT_ID));
        }
        
        if(resultSet.getString(PAYMENT_DOCUMENT_NUMBER) != null){
            payment.setPaymentDocumentNumber(resultSet.getString(PAYMENT_DOCUMENT_NUMBER));
        }
        
        if(resultSet.getString(CODE_SERVICE) != null){
            payment.setCodeService(resultSet.getString(CODE_SERVICE).trim());
        }

        payment.setRate(new BigDecimal(resultSet.getDouble(RATE)).setScale(ROUND_SCALE, ROUND_MODE));
        
        payment.setTotalPayable(new BigDecimal(resultSet.getDouble(TOTAL_PAYABLE)).setScale(ROUND_SCALE, ROUND_MODE));
        
        payment.setAccountingPeriodTotal(new BigDecimal(resultSet.getDouble(ACCOUNTING_PERIOD_TOTAL)).setScale(ROUND_SCALE, ROUND_MODE));
        
        if(resultSet.getString(CALC_EXPLANATION) != null){
            payment.setCalcExplanation(resultSet.getString(CALC_EXPLANATION));
        }
        
        if(resultSet.getDouble(MONEY_RECALCULATION) != 0){
            payment.setMoneyRecalculation(new BigDecimal(resultSet.getDouble(MONEY_RECALCULATION)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        /*
        if(resultSet.getDouble(INDIVIDUAL_CONSUMPTION_CURRENT_VALUE) != 0){
            payment.setIndividualConsumptionCurrentValue(new BigDecimal(resultSet.getDouble(INDIVIDUAL_CONSUMPTION_CURRENT_VALUE)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        if(resultSet.getDouble(HOUSEOVERALL_NEEDS_CURRENT_VALUE) != 0){
            payment.setHouseOverallNeedsCurrentValue(new BigDecimal(resultSet.getDouble(HOUSEOVERALL_NEEDS_CURRENT_VALUE)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        */
        if(resultSet.getDouble(HOUSE_TOTAL_INDIVIDUAL_CONSUMPTION) != 0){
            payment.setHouseTotalIndividualConsumption(new BigDecimal(resultSet.getDouble(HOUSE_TOTAL_INDIVIDUAL_CONSUMPTION)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        if(resultSet.getDouble(HOUSE_TOTAL_HOUSEOVERALL_NEEDS) != 0){
            payment.setHouseTotalHouseOverallNeeds(new BigDecimal(resultSet.getDouble(HOUSE_TOTAL_HOUSEOVERALL_NEEDS)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(HOUSEOVERALL_NEEDS_NORM) != 0){
            payment.setHouseOverallNeedsNorm(new BigDecimal(resultSet.getDouble(HOUSEOVERALL_NEEDS_NORM)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        if(resultSet.getDouble(INDIVIDUAL_CONSUMPTION_NORM) != 0){
            payment.setIndividualConsumptionNorm(new BigDecimal(resultSet.getDouble(INDIVIDUAL_CONSUMPTION_NORM)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        PaymentType.Volume1 vol1 = new PaymentType.Volume1();
        //vol1.setType("I");
        vol1.setValue(new BigDecimal(resultSet.getDouble(VOLUME1)).setScale(ROUND_SCALE, ROUND_MODE));
        payment.setVolume1(vol1);
        
        PaymentType.Volume2 vol2 = new PaymentType.Volume2();
        //vol2.setType("I");
        vol2.setValue(new BigDecimal(resultSet.getDouble(VOLUME2)).setScale(ROUND_SCALE, ROUND_MODE));
        payment.setVolume2(vol2);
        
        if(resultSet.getDouble(MUNICIPAL_SERVICE_INDIVIDUAL_CONSUMPTION_PAYABLE) != 0){
            payment.setMunicipalServiceIndividualConsumptionPayable(new BigDecimal(resultSet.getDouble(MUNICIPAL_SERVICE_INDIVIDUAL_CONSUMPTION_PAYABLE)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        if(resultSet.getDouble(MUNICIPAL_SERVICE_COMMUNAL_CONSUMPTION_PAYABLE) != 0){
            payment.setMunicipalServiceCommunalConsumptionPayable(new BigDecimal(resultSet.getDouble(MUNICIPAL_SERVICE_COMMUNAL_CONSUMPTION_PAYABLE)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(DEBT_PREVIOUS_PERIODS) != 0){
            payment.setDebtPreviousPeriods(new BigDecimal(resultSet.getDouble(DEBT_PREVIOUS_PERIODS)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(ADVANCE_BLLING_PERIOD) != 0){
            payment.setAdvanceBllingPeriod(new BigDecimal(resultSet.getDouble(ADVANCE_BLLING_PERIOD)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(TOTAL_PAYMENT_SUM) != 0){
            payment.setTotalPaymentSum(new BigDecimal(resultSet.getDouble(TOTAL_PAYMENT_SUM)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        payment.setExpose(resultSet.getBoolean(EXPOSE));
        
        if(resultSet.getString(ACTION) != null){
            payment.setAction(resultSet.getString(ACTION).trim());
        }
        
        //other "PEACEMEAL..." shit
        if(resultSet.getDouble(PAYMENT_PERIOD_PIECEMEAL_PAYMENT_SUM) != 0){
            payment.setPaymentPeriodPiecemealPaymentSum(new BigDecimal(resultSet.getDouble(PAYMENT_PERIOD_PIECEMEAL_PAYMENT_SUM)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(PAST_PAYMENT_PERIOD_PIECEMEAL_PAYMENT_SUM) != 0){
            payment.setPastPaymentPeriodPiecemealPaymentSum(new BigDecimal(resultSet.getDouble(PAST_PAYMENT_PERIOD_PIECEMEAL_PAYMENT_SUM)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(PIECEMEAL_PAYMENT_PERCENT_RUB) != 0){
            payment.setPiecemealPaymentPercentRub(new BigDecimal(resultSet.getDouble(PIECEMEAL_PAYMENT_PERCENT_RUB)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(PIECEMEAL_PAYMENT_PERCENT) != 0){
            payment.setPiecemealPaymentPercent(new BigDecimal(resultSet.getDouble(PIECEMEAL_PAYMENT_PERCENT)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(PIECE_MEAL_PAYMENT_SUM) != 0){
            payment.setPiecemealPaymentSum(new BigDecimal(resultSet.getDouble(PIECE_MEAL_PAYMENT_SUM)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(RECALCULATION_SUM) != 0){
            payment.setRecalculationSum(new BigDecimal(resultSet.getDouble(RECALCULATION_SUM)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getString(RECALCULATION_REASON) != null){
            payment.setRecalculationReason(resultSet.getString(RECALCULATION_REASON));
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
        int count_payments = 0;
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            <payment>
            ...
            </payment>
            */
            buildPaymentNode(rs, count_payments);
            count_payments++;
            
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
        
        while(inProgress)
        {
            payments.getPayment().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);

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
                initJAXBElement("payment", paymentNode);
                marshaller.marshal(jaxbElement, xmlStreamWriter);
            }
        }
        
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </devices>
        xmlStreamWriter.writeEndDocument();
    }
    
}
