package gisjkh.engines;

import gisjkh.OwnUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import org.whissper.meteringdevices.DeviceType;
import org.whissper.meteringdevices.DevicesType;

/**
 * MeteringDevicesEngine class
 * @author SAV2
 */
public class MeteringDevicesEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 4;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    //fields
    private static final String ID = "ID";
    private static final String HOUSEID = "HOUSEID";
    private static final String DEVICENUMBER = "DEVICENUMBER";
    private static final String STAMP = "STAMP";
    private static final String MODEL = "MODEL";
    private static final String INSTALLDATE = "INSTALLDATE";
    private static final String COMMISSDATE = "COMMISSDATE";
    private static final String REMOTEMODE = "REMOTEMODE";
    private static final String FACTORYSEALDATE = "FACTORYSEALDATE";
    private static final String TEMPERATURESENSOR = "TEMPERATURESENSOR";
    private static final String PRESSURESENSOR = "PRESSURESENSOR";
    private static final String FIRSTVERIFICATIONDATE = "FIRSTVERIFICATIONDATE";
    private static final String VERIFICATIONINTERVAL = "VERIFICATIONINTERVAL";
    private static final String COLLECTIVEDEVICE = "COLLECTIVEDEVICE";
    private static final String REMOTEMETERINGINFO = "REMOTEMETERINGINFO";
    private static final String TEMPERATURESENSINGELEMENTINFO = "TEMPERATURESENSINGELEMENTINFO";
    private static final String PRESSURESENSINGELEMENTINFO = "PRESSURESENSINGELEMENTINFO";
    private static final String ACCOUNTID = "ACCOUNTID";
    private static final String PREMISEID = "PREMISEID";
    private static final String VALUE1 = "VALUE1";
    private static final String RESOURCE1 = "RESOURCE1";
    private static final String VALUE2 = "VALUE2";
    private static final String RESOURCE2 = "RESOURCE2";
    private static final String VALUE3 = "VALUE3";
    private static final String RESOURCE3 = "RESOURCE3";
    private static final String VALUET1 = "VALUET1";
    private static final String VALUET2 = "VALUET2";
    private static final String VALUET3 = "VALUET3";
    private static final String READOUTDATE = "READOUTDATE";
    private static final String READINGSOURCE = "READINGSOURCE";
    private static final String ARCHIVINGREASON = "ARCHIVINGREASON";
    private static final String ARCHIVINGDATE = "ARCHIVINGDATE";
    private static final String ACTION = "ACT";
    
    private DevicesType devices;
    
    public MeteringDevicesEngine() {
        super(
            "org.whissper.meteringdevices",//CONTEXT_PATH
            "MeteringDevices",//ENTITY_CODE
            "xml-resources/jaxb/MeteringDevices/MeteringDevices.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_METERINGDEVICES"//QUERY_STRING
        );
    }
    
    private void buildDevicesNode() {
        devices = new DevicesType();
        devices.setSystemID(SYSTEM_ID);
    }
    
    private void buildDeviceNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        devices.getDevice().add(new DeviceType());
        DeviceType device = devices.getDevice().get(counter);
        
        if(resultSet.getString(ID) != null){
            device.setId(resultSet.getString(ID));
        }
        
        if(resultSet.getString(HOUSEID) != null){
            device.setHouseID(resultSet.getString(HOUSEID));
        }
        
        if(resultSet.getString(DEVICENUMBER) != null){
            device.setDeviceNumber(resultSet.getString(DEVICENUMBER));
        }
        
        if(resultSet.getString(STAMP) != null){
            device.setStamp(resultSet.getString(STAMP));
        }
        
        if(resultSet.getString(MODEL) != null){
            device.setModel(resultSet.getString(MODEL));
        }
        
        if(resultSet.getDate(INSTALLDATE) != null){
            device.setInstallDate( DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(INSTALLDATE))) );
        }    
        
        if(resultSet.getDate(COMMISSDATE) != null){
            device.setCommissDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(COMMISSDATE))) );
        }
        
        device.setRemoteMode(resultSet.getBoolean(REMOTEMODE));
        
        if(resultSet.getDate(FACTORYSEALDATE) != null){
            device.setFactorySealDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(FACTORYSEALDATE))) );
        }
        
        device.setTemperatureSensor(resultSet.getBoolean(TEMPERATURESENSOR));
        
        device.setPressureSensor(resultSet.getBoolean(PRESSURESENSOR));
        
        if(resultSet.getDate(FIRSTVERIFICATIONDATE) != null){
            device.setFirstVerificationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(FIRSTVERIFICATIONDATE))) );
        }
        
        if(resultSet.getString(VERIFICATIONINTERVAL) != null){
            device.setVerificationInterval(resultSet.getString(VERIFICATIONINTERVAL).trim());
        }
        
        /*
            DeviceType class :
            @XmlElement(name = "collectiveDevice")
            @XmlJavaTypeAdapter( BooleanAdapter.class )
            protected Boolean collectiveDevice;
        */
        device.setCollectiveDevice(resultSet.getBoolean(COLLECTIVEDEVICE));
        
        if( resultSet.getBoolean(REMOTEMODE) || 
            resultSet.getBoolean(PRESSURESENSOR) ||
            resultSet.getBoolean(TEMPERATURESENSOR)
        ){
            DeviceType.CollectiveDeviceInfo collectiveDeviceInfo = new DeviceType.CollectiveDeviceInfo();
            device.setCollectiveDeviceInfo(collectiveDeviceInfo);
            
            if( resultSet.getBoolean(REMOTEMODE) && (resultSet.getString(REMOTEMETERINGINFO)!=null) ){
                collectiveDeviceInfo.setRemoteMeteringInfo( resultSet.getString(REMOTEMETERINGINFO) );
            }
            if( resultSet.getBoolean(PRESSURESENSOR) && (resultSet.getString(PRESSURESENSINGELEMENTINFO)!=null) ){
                collectiveDeviceInfo.setPressureSensingElementInfo( resultSet.getString(PRESSURESENSINGELEMENTINFO) );
            }
            if( resultSet.getBoolean(TEMPERATURESENSOR) && (resultSet.getString(TEMPERATURESENSINGELEMENTINFO)!=null) ){
                collectiveDeviceInfo.setTemperatureSensingElementInfo( resultSet.getString(TEMPERATURESENSINGELEMENTINFO) );
            }
        }
        
        if(resultSet.getString(ACCOUNTID) != null){
            device.getAccountId().add(resultSet.getString(ACCOUNTID));
        }
        
        if(resultSet.getString(PREMISEID) != null){
            device.getPremiseId().add(resultSet.getString(PREMISEID));
        }
        
        if(resultSet.getDouble(VALUE1) != 0){
            device.setValue1(new BigDecimal(resultSet.getDouble(VALUE1)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getString(RESOURCE1) != null){
            device.setResource1(resultSet.getString(RESOURCE1).trim());
        }
        
        if(resultSet.getDouble(VALUE2) != 0){
            device.setValue2(new BigDecimal(resultSet.getDouble(VALUE2)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getString(RESOURCE2) != null){
            device.setResource2(resultSet.getString(RESOURCE2).trim());
        }
        
        if(resultSet.getDouble(VALUE3) != 0){
            device.setValue3(new BigDecimal(resultSet.getDouble(VALUE3)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getString(RESOURCE3) != null){
            device.setResource3(resultSet.getString(RESOURCE3).trim());
        }
        
        if(resultSet.getDouble(VALUET1) != 0){
            device.setValueT1(new BigDecimal(resultSet.getDouble(VALUET1)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(VALUET2) != 0){
            device.setValueT2(new BigDecimal(resultSet.getDouble(VALUET2)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDouble(VALUET3) != 0){
            device.setValueT3(new BigDecimal(resultSet.getDouble(VALUET3)).setScale(ROUND_SCALE, ROUND_MODE));
        }
        
        if(resultSet.getDate(READOUTDATE) != null){
            device.setReadoutDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDatetimeToXMLFormat(resultSet.getDate(READOUTDATE))) );
        }
        
        if(resultSet.getString(READINGSOURCE) != null){
            device.setReadingSource(resultSet.getString(READINGSOURCE));
        }
        
        if(resultSet.getString(ARCHIVINGREASON) != null){
            device.setArchivingReason(resultSet.getString(ARCHIVINGREASON));
        }
        
        if(resultSet.getDate(ARCHIVINGDATE) != null){
            device.setArchivingDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDatetimeToXMLFormat(resultSet.getDate(ARCHIVINGDATE))) );
        }
        
        if(resultSet.getString(ACTION) != null){
            device.setAction(resultSet.getString(ACTION).trim());
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
        int count_devices = 0;
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            <device>
            ...
            </device>
            */
            buildDeviceNode(rs, count_devices);
            count_devices++;
            
            if( (buffRowsVal != 0) && (count_rows > buffRowsVal) ){
                break;
            }
        }
    }
    
    @Override
    protected void processForBuild() throws SQLException, DatatypeConfigurationException, JAXBException {
        //RootElement
        buildDevicesNode();
        //build object
        buildObject(resultSet, 0);
        //terminate connection
        terminateConnection();
        //init jaxb Element
        initJAXBElement("devices", devices);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
    }

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        
        buildDevicesNode();
        
        xmlStreamWriter.writeStartDocument("utf-8", "1.0");//XML header
        xmlStreamWriter.writeStartElement("devices");// <devices>
        xmlStreamWriter.writeStartElement("systemID");// <systemID>
        xmlStreamWriter.writeCharacters(devices.getSystemID());
        xmlStreamWriter.writeEndElement();// </systemID>
        
        while(inProgress)
        {
            devices.getDevice().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);
            
            for(DeviceType deviceNode : devices.getDevice()){
                initJAXBElement("device", deviceNode);
                marshaller.marshal(jaxbElement, xmlStreamWriter);
            }
        }
        
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </devices>
        xmlStreamWriter.writeEndDocument();
    }
    
}
