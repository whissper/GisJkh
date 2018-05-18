package gisjkh.engines;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.whissper.acknowledgment.AcknowledgmentType;
import org.whissper.acknowledgment.AcknowledgmentsType;

/**
 * AcknowledgmentEngine class
 * @author SAV2
 */
public class AcknowledgmentEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 2;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    //fields
    private static final String ID = "ID";
    private static final String ORDER_ID = "ORDER_ID";
    private static final String ORDER_INN = "ORDER_INN";
    private static final String ORDER_KPP = "ORDER_KPP";
    private static final String ACK_IS_POSSIBLE = "ACK_IS_POSSIBLE";
    
    private static final String PAYMENT_DOCUMENT_ID = "PAYMENT_DOCUMENT_ID";
    //ACK_IS_POSSIBLE true
    private static final String MSTYPE = "MSTYPE";
    private static final String AMOUNT = "AMOUNT";
    private static final String PAYMENT_DOCUMENT_NUMBER = "PAYMENT_DOCUMENT_NUMBER";
    
    private static final String ACTION = "ACT";
    
    private AcknowledgmentsType acknowledgments;
    
    public AcknowledgmentEngine() {
        super(
            "org.whissper.acknowledgment",//CONTEXT_PATH
            "Acknowledgment",//ENTITY_CODE
            "xml-resources/jaxb/Acknowledgment/Acknowledgment.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_ACKNOWLEDGMENT"//QUERY_STRING
        );
    }
    
    private void buildAcknowledgmentsNode() {
        acknowledgments = new AcknowledgmentsType();
        acknowledgments.setSystemID(SYSTEM_ID);
    }
    
    private void buildAcknowledgmentNode(ResultSet resultSet, int counter)throws SQLException, DatatypeConfigurationException {
        acknowledgments.getAcknowledgment().add(new AcknowledgmentType());
        AcknowledgmentType acknowledgment = acknowledgments.getAcknowledgment().get(counter);
        
        if(resultSet.getString(ID) != null){
            acknowledgment.setId(resultSet.getString(ID));
        }
        
        if(resultSet.getString(ORDER_ID) != null){
            acknowledgment.setOrderID(resultSet.getString(ORDER_ID).replace("'", ""));//some banks send order_id data wrapped with single quotes
        }
        
        if(resultSet.getString(ORDER_INN) != null){
            acknowledgment.setOrderINN(resultSet.getString(ORDER_INN));
        }
        
        if(resultSet.getString(ORDER_KPP) != null){
            acknowledgment.setOrderKPP(resultSet.getString(ORDER_KPP));
        }
        
        if(resultSet.getBoolean(ACK_IS_POSSIBLE))
        {
            AcknowledgmentType.AckPossible ackPossible = new AcknowledgmentType.AckPossible();
            
            if(resultSet.getString(PAYMENT_DOCUMENT_ID) != null){
                ackPossible.setPaymentDocumentId(resultSet.getString(PAYMENT_DOCUMENT_ID));
            }
            
            if(resultSet.getString(MSTYPE) != null){
                ackPossible.setMsType(resultSet.getString(MSTYPE).trim());
            }
            
            ackPossible.setAmount(new BigDecimal(resultSet.getDouble(AMOUNT)).setScale(ROUND_SCALE, ROUND_MODE));
            
            if(resultSet.getString(PAYMENT_DOCUMENT_NUMBER) != null){
                ackPossible.setPaymentDocumentNumber(resultSet.getString(PAYMENT_DOCUMENT_NUMBER));
            }
            
            acknowledgment.setAckPossible(ackPossible);
        }
        else{
            AcknowledgmentType.AckImpossible ackImpossible = new AcknowledgmentType.AckImpossible();
            
            if(resultSet.getString(PAYMENT_DOCUMENT_ID) != null){
                ackImpossible.setPaymentDocumentId(resultSet.getString(PAYMENT_DOCUMENT_ID));
            }
        }
        //Only "Add"
        if(resultSet.getString(ACTION) != null){
            acknowledgment.setAction(resultSet.getString(ACTION).trim());
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
        int count_acknowledgments = 0;
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            <acknowledgment>
            ...
            </acknowledgment>
            */
            buildAcknowledgmentNode(rs, count_acknowledgments);
            count_acknowledgments++;
            
            if( (buffRowsVal != 0) && (count_rows > buffRowsVal) ){
                break;
            }
        }
    }
    
    @Override
    protected void processForBuild() throws SQLException, DatatypeConfigurationException, JAXBException {
        //RootElement
        buildAcknowledgmentsNode();
        //build object
        buildObject(resultSet, 0);
        //terminate connection
        terminateConnection();
        //init jaxb Element
        initJAXBElement("acknowledgments", acknowledgments);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
    }

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        
        buildAcknowledgmentsNode();
        
        xmlStreamWriter.writeStartDocument("utf-8", "1.0");//XML header
        xmlStreamWriter.writeStartElement("acknowledgments");// <devices>
        xmlStreamWriter.writeStartElement("systemID");// <systemID>
        xmlStreamWriter.writeCharacters(acknowledgments.getSystemID());
        xmlStreamWriter.writeEndElement();// </systemID>
        
        while(inProgress)
        {
            acknowledgments.getAcknowledgment().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);
            
            for(AcknowledgmentType acknowledgmentNode : acknowledgments.getAcknowledgment()){
                initJAXBElement("acknowledgment", acknowledgmentNode);
                marshaller.marshal(jaxbElement, xmlStreamWriter);
            }
        }
        
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </devices>
        xmlStreamWriter.writeEndDocument();
    }
    
}
