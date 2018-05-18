package gisjkh.engines;

import gisjkh.OwnUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import org.whissper.meteringdevicevalues.DeviceValueType;
import org.whissper.meteringdevicevalues.DeviceValuesType;
import org.whissper.meteringdevicevalues.MeteringDeviceValueType;

/**
 * MeteringDeviceValuesEngine class
 * @author SAV2
 */
public class MeteringDeviceValuesEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 7;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    //fields
    private static final String ID = "ID";
    private static final String HOUSEID = "HOUSEID";
    private static final String METERDEVICEID = "METERDEVICEID";
    
    private static final String CURR_METR_V_VALUE1 = "CURRMV1";
    private static final String CURR_METR_V_VALUE2 = "CURRMV2";//absent
    //private static final String CURR_METR_V_VALUE3 = "FIELD";//absent
    private static final String CURR_METR_V_RESOURCE1 = "CURRESOURCE1";
    private static final String CURR_METR_V_RESOURCE2 = "CURRESOURCE2";//absent
    //private static final String CURR_METR_V_RESOURCE3 = "FIELD";//absent
    private static final String CURR_METR_V_VALUET1 = "CURRMVT1";
    private static final String CURR_METR_V_VALUET2 = "CURRMVT2";
    private static final String CURR_METR_V_VALUET3= "CURRMVT3";
    private static final String CURR_METR_V_READOUTDATE = "CURRMVREADOUTDATE";
    private static final String CURR_METR_V_READINGSSOURCE = "CURRMVREADINGSOURCE";
    /*
    private static final String CTRL_METR_V_VALUE1 = "CTRLMV1";
    private static final String CTRL_METR_V_VALUE2 = "FIELD";//absent
    private static final String CTRL_METR_V_VALUE3 = "FIELD";//absent
    private static final String CTRL_METR_V_RESOURCE1 = "FIELD";//absent
    private static final String CTRL_METR_V_RESOURCE2 = "FIELD";//absent
    private static final String CTRL_METR_V_RESOURCE3 = "FIELD";//absent
    private static final String CTRL_METR_V_VALUET1 = "CTRLMVT1";
    private static final String CTRL_METR_V_VALUET2 = "CTRLMVT2";
    private static final String CTRL_METR_V_VALUET3= "CTRLMVT3";
    private static final String CTRL_METR_V_READOUTDATE = "CTRLMVREADOUTDATE";
    private static final String CTRL_METR_V_READINGSSOURCE = "CTRLMVREADINGSOURCE";
    
    private static final String START_VER_V_VALUE1 = "FIELD";//absent
    private static final String START_VER_V_VALUE2 = "FIELD";//absent
    private static final String START_VER_V_VALUE3 = "FIELD";//absent
    private static final String START_VER_V_RESOURCE1 = "FIELD";//absent
    private static final String START_VER_V_RESOURCE2 = "FIELD";//absent
    private static final String START_VER_V_RESOURCE3 = "FIELD";//absent
    private static final String START_VER_V_VALUET1 = "STRTVVT1";
    private static final String START_VER_V_VALUET2 = "STRTVVT2";
    private static final String START_VER_V_VALUET3= "STRTVVT3";
    private static final String START_VER_V_READOUTDATE = "STRTVVREADOUTDATE";
    private static final String START_VER_V_READINGSSOURCE = "STRTVVREADINGSOURCE";
    
    private static final String END_VER_V_VALUE1 = "FIELD";//absent
    private static final String END_VER_V_VALUE2 = "FIELD";//absent
    private static final String END_VER_V_VALUE3 = "FIELD";//absent
    private static final String END_VER_V_RESOURCE1 = "FIELD";//absent
    private static final String END_VER_V_RESOURCE2 = "FIELD";//absent
    private static final String END_VER_V_RESOURCE3 = "FIELD";//absent
    private static final String END_VER_V_VALUET1 = "ENDVVT1";
    private static final String END_VER_V_VALUET2 = "ENDVVT2";
    private static final String END_VER_V_VALUET3= "ENDVVT3";
    private static final String END_VER_V_READOUTDATE = "ENDVVREADOUTDATE";
    private static final String END_VER_V_READINGSSOURCE = "ENDVVREADINGSOURCE";
    
    private static final String SEALDATE = "FIELD";//absent
    private static final String PLANNEDVERIFICATION = "PLANNEDVERIFICATION";
    private static final String VERIFICATIONREASON = "VERIFICATIONREASON";
    */
    private static final String ACTION = "ACT";
    
    private DeviceValuesType deviceValues;
    
    public MeteringDeviceValuesEngine() {
        super(
            "org.whissper.meteringdevicevalues",//CONTEXT_PATH
            "MeteringDeviceValues",//ENTITY_CODE
            "xml-resources/jaxb/MeteringDeviceValues/MeteringDeviceValues.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_METERINGDEVICEVALUES"//QUERY_STRING
        );
    }
    
    private void buildValuesNode(){
        deviceValues = new DeviceValuesType();
        deviceValues.setSystemID(SYSTEM_ID);
    }
    
    private void buildValueNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException{
        deviceValues.getDeviceValue().add(new DeviceValueType());
        DeviceValueType deviceValue = deviceValues.getDeviceValue().get(counter);
        
        if(resultSet.getString(ID) != null){
            deviceValue.setId(resultSet.getString(ID));
        }
        
        if(resultSet.getString(HOUSEID) != null){
            deviceValue.setHouseID(resultSet.getString(HOUSEID));
        }
        
        if(resultSet.getString(METERDEVICEID) != null){
            deviceValue.setMeterDeviceId(resultSet.getString(METERDEVICEID));
        }
        
        if(
            resultSet.getDouble(CURR_METR_V_VALUE1)!=0 ||
            resultSet.getDouble(CURR_METR_V_VALUE2)!=0 ||
            //resultSet.getDouble(CURR_METR_V_VALUE3)!=0 ||
            resultSet.getString(CURR_METR_V_RESOURCE1)!=null ||
            resultSet.getString(CURR_METR_V_RESOURCE2)!=null ||
            //resultSet.getString(CURR_METR_V_RESOURCE3)!=null ||
            resultSet.getDouble(CURR_METR_V_VALUET1)!=0 ||
            resultSet.getDouble(CURR_METR_V_VALUET2)!=0 ||
            resultSet.getDouble(CURR_METR_V_VALUET3)!=0 ||
            resultSet.getDate(CURR_METR_V_READOUTDATE)!=null ||
            resultSet.getString(CURR_METR_V_READINGSSOURCE)!=null   
        ){
            MeteringDeviceValueType currVals = new MeteringDeviceValueType();
            deviceValue.setCurrentMeteringValue(currVals);
            
            if(resultSet.getDouble(CURR_METR_V_VALUE1) != 0){
                currVals.setValue1(new BigDecimal(resultSet.getDouble(CURR_METR_V_VALUE1)).setScale(ROUND_SCALE, ROUND_MODE));
            }
            if(resultSet.getDouble(CURR_METR_V_VALUE2) != 0){
                currVals.setValue2(new BigDecimal(resultSet.getDouble(CURR_METR_V_VALUE2)).setScale(ROUND_SCALE, ROUND_MODE));
            }
            //currVals.setValue3(new BigDecimal(resultSet.getDouble(CURR_METR_V_VALUE3)).setScale(ROUND_SCALE, ROUND_MODE));
            if(resultSet.getString(CURR_METR_V_RESOURCE1) != null){
                currVals.setResource1(resultSet.getString(CURR_METR_V_RESOURCE1).trim());
            }
            if(resultSet.getString(CURR_METR_V_RESOURCE2) != null){
                currVals.setResource2(resultSet.getString(CURR_METR_V_RESOURCE2));
            }
            //currVals.setResource3(resultSet.getString(CURR_METR_V_RESOURCE3));
            if(resultSet.getDouble(CURR_METR_V_VALUET1) != 0){
                currVals.setValueT1(new BigDecimal(resultSet.getDouble(CURR_METR_V_VALUET1)).setScale(ROUND_SCALE, ROUND_MODE));
            }
            if(resultSet.getDouble(CURR_METR_V_VALUET2) != 0){
                currVals.setValueT2(new BigDecimal(resultSet.getDouble(CURR_METR_V_VALUET2)).setScale(ROUND_SCALE, ROUND_MODE));
            }
            if(resultSet.getDouble(CURR_METR_V_VALUET3) != 0){
                currVals.setValueT3(new BigDecimal(resultSet.getDouble(CURR_METR_V_VALUET3)).setScale(ROUND_SCALE, ROUND_MODE));
            }
            if(resultSet.getDate(CURR_METR_V_READOUTDATE) != null){
                currVals.setReadoutDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDatetimeToXMLFormat(resultSet.getDate(CURR_METR_V_READOUTDATE))) );
            }
            if(resultSet.getString(CURR_METR_V_READINGSSOURCE) != null){
                currVals.setReadingsSource(resultSet.getString(CURR_METR_V_READINGSSOURCE));
            }
        }
        
        if(resultSet.getString(ACTION) != null){
            deviceValue.setAction(resultSet.getString(ACTION).trim());
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
        int count_values = 0;
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            <deviceValue>
            ...
            </deviceValue>
            */
            buildValueNode(rs, count_values);
            count_values++;
            
            if( (buffRowsVal != 0) && (count_rows > buffRowsVal) ){
                break;
            }
        }
    }
    
    @Override
    protected void processForBuild() throws SQLException, DatatypeConfigurationException, JAXBException {
        //RootElement
        buildValuesNode();
        //build object
        buildObject(resultSet, 0);
        //terminate connection
        terminateConnection();
        //init jaxb Element
        initJAXBElement("deviceValues", deviceValues);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
    }

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        
        buildValuesNode();
        
        xmlStreamWriter.writeStartDocument("utf-8", "1.0");//XML header
        xmlStreamWriter.writeStartElement("deviceValues");// <deviceValues>
        xmlStreamWriter.writeStartElement("systemID");// <systemID>
        xmlStreamWriter.writeCharacters(deviceValues.getSystemID());
        xmlStreamWriter.writeEndElement();// </systemID>
        
        while(inProgress)
        {
            deviceValues.getDeviceValue().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);
            
            for(DeviceValueType deviceNode : deviceValues.getDeviceValue()){
                initJAXBElement("deviceValue", deviceNode);
                marshaller.marshal(jaxbElement, xmlStreamWriter);
            }
        }
        
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </deviceValues>
        xmlStreamWriter.writeEndDocument();
    }
    
}
