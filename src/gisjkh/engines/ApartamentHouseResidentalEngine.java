package gisjkh.engines;

import gisjkh.OwnUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import org.whissper.apartamenthouseresidental.ApartamentHouseResidentalType;
import org.whissper.apartamenthouseresidental.ApartamentHousesResidentalType;
import org.whissper.apartamenthouseresidental.CadastralInfoType;
import org.whissper.apartamenthouseresidental.HousesResidentalType;

/**
 * ApartamentHouseResidentalEngine class
 * @author SAV2
 */
public class ApartamentHouseResidentalEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 1;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    //fields names
    /*
    private static final String ID_HOUSE =                   "ID_HOUSE";
    private static final String ID_APARTMENT =               "ID_APARTMENT";
    private static final String CADASTRAL_NUMBER =           "CADASTRAL_NUMBER";
    private static final String NO_RSO_GKN_EGRP_REGISTERED = "NO_RSO_GKN_EGRP_REGISTERED";
    private static final String NO_RSO_GKN_EGRP_DATA =       "NO_RSO_GKN_EGRP_DATA";
    private static final String PREMISE_NUM =                "PREMISE_NUM";
    private static final String ENTRANCE_NUM =               "ENTRANCE_NUM";
    private static final String TOTAL_SQUARE =               "TOTAL_SQUARE";
    private static final String RESIDENTIAL_SQUARE =         "RESIDENTIAL_SQUARE";
    private static final String HEATED_AREA =                "HEATED_AREA";
    private static final String TERMINATION_DATE =           "TERMINATION_DATE";
    private static final String ACTION =                     "ACTION";
    */
    private static final String ID_HOUSE =                   "ID_HOME";
    private static final String ID_APARTMENT =               "ID";
    private static final String CADASTRAL_NUMBER =           "CADASTRALNUMBER";
    private static final String NO_RSO_GKN_EGRP_REGISTERED = "NO_RSO_GKN_EGRP_REGISTERED";
    private static final String NO_RSO_GKN_EGRP_DATA =       "NO_RSO_GKN_EGRP_DATA";
    private static final String PREMISE_NUM =                "PREMISESNUM";
    private static final String ENTRANCE_NUM =               "ENTRANCENUM";
    private static final String TOTAL_SQUARE =               "TOTALSQUARE";
    private static final String RESIDENTIAL_SQUARE =         "RESIDENALSQUARE";
    private static final String HEATED_AREA =                "HEATEDAREA";
    private static final String TERMINATION_DATE =           "TERMINATIONDATE";
    private static final String ACTION =                     "ACT";
    
    //RootElement
    private HousesResidentalType houses;
    //AfterRootElement
    private ApartamentHousesResidentalType house;
    
    //constructor
    public ApartamentHouseResidentalEngine() {
        super(
            "org.whissper.apartamenthouseresidental",//CONTEXT_PATH
            "ApartamentHouseResidental",//ENTITY_CODE
            "xml-resources/jaxb/ApartamentHouseResidental/ApartamentHouseResidental.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_APARTAMENTHOUSERESIDENTAL"//QUERY_STRING
            //"SELECT * FROM NEW_PROCEDURE";
        );
    }
    
    /**
     * Build Houses Node @RootElement
     */
    private void buildHousesNode() {
        //<houses>
        houses = new HousesResidentalType();
        //<houses> - <systemID>
        houses.setSystemID(SYSTEM_ID);
    }
    
    /**
     * build House Node
     * @param resultSet
     * @param counter
     * @throws SQLException 
     */
    private void buildHouseNode(ResultSet resultSet, int counter) throws SQLException {
        //<houses> - <house>
        houses.getHouse().add(new ApartamentHousesResidentalType());
        house = houses.getHouse().get(counter);
        //<houses> - <house> - <houseID>
        if(resultSet.getString(ID_HOUSE) != null){
            house.setHouseID(resultSet.getString(ID_HOUSE));
        }
    }
    
    /**
     * build Apartment Node
     * @param resultSet
     * @param counter
     * @throws DatatypeConfigurationException
     * @throws SQLException 
     */
    private void buildApartmentNode(ResultSet resultSet, int counter) throws DatatypeConfigurationException, SQLException {
        //<houses> - <house> - <apartamentHousesResidental>
        house.getApartamentHousesResidental().add(new ApartamentHouseResidentalType());
        ApartamentHouseResidentalType apartment = house.getApartamentHousesResidental().get(counter);
        
        //<houses> - <house> - <apartamentHousesResidental> - <id>
        if(resultSet.getString(ID_APARTMENT) != null){
            apartment.setId(resultSet.getString(ID_APARTMENT));
        }
        //<houses> - <house> - <apartamentHousesResidental> - <cadastralInfo>
        CadastralInfoType cadastralInfo = new CadastralInfoType();
        apartment.setCadastralInfo(cadastralInfo);
        //<houses> - <house> - <apartamentHousesResidental> - <cadastralInfo> - <cadastralNumber>
        if(resultSet.getString(CADASTRAL_NUMBER) != null){
            cadastralInfo.setCadastralNumber(resultSet.getString(CADASTRAL_NUMBER));
        }else{
            //<houses> - <house> - <apartamentHousesResidental> - <cadastralInfo> - <no_RSO_GKN_EGRP_Registered>
            //if(resultSet.getBoolean(NO_RSO_GKN_EGRP_REGISTERED)){
            //    cadastralInfo.setNoRSOGKNEGRPRegistered(Boolean.TRUE);
            //}
            //<houses> - <house> - <apartamentHousesResidental> - <cadastralInfo> - <no_RSO_GKN_EGRP_Data>
            if(resultSet.getBoolean(NO_RSO_GKN_EGRP_DATA)){
                cadastralInfo.setNoRSOGKNEGRPData(Boolean.TRUE);
            }
        }
        //<houses> - <house> - <apartamentHousesResidental> - <premiseNum>
        if(resultSet.getString(PREMISE_NUM) != null){
            apartment.setPremiseNum(resultSet.getString(PREMISE_NUM).trim());
        }
        //<houses> - <house> - <apartamentHousesResidental> - <entranceNum>
        if(resultSet.getByte(ENTRANCE_NUM) != 0){
            apartment.setEntranceNum(resultSet.getByte(ENTRANCE_NUM));
        }else{
            apartment.setEntranceNum((byte)1);
        }
        //<houses> - <house> - <apartamentHousesResidental> - <totalSquare>
        if(resultSet.getDouble(TOTAL_SQUARE) != 0){
            apartment.setTotalSquare( new BigDecimal(resultSet.getDouble(TOTAL_SQUARE)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        //<houses> - <house> - <apartamentHousesResidental> - <residentialSquare>
        if(resultSet.getDouble(RESIDENTIAL_SQUARE) != 0){
            apartment.setResidentialSquare( new BigDecimal(resultSet.getDouble(RESIDENTIAL_SQUARE)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        //<houses> - <house> - <apartamentHousesResidental> - <heatedArea>
        if(resultSet.getDouble(HEATED_AREA) != 0){
            apartment.setHeatedArea( new BigDecimal(resultSet.getDouble(HEATED_AREA)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        //<houses> - <house> - <apartamentHousesResidental> - <terminationDate>
        if(resultSet.getDate(TERMINATION_DATE) != null){
            apartment.setTerminationDate( DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(TERMINATION_DATE))) );
        }
        //<houses> - <house> - <apartamentHousesResidental> - <action>
        if(resultSet.getString(ACTION) != null){
            apartment.setAction(resultSet.getString(ACTION).trim());
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
        int count_houses = 0;
        int count_apartment = 0;
        String house_id = "-1";//negative value for special purpose when comparing it with first house_id
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            if getting first_row {
                <house>
                    <apartamentHousesResidental>
                    ...
                    </apartamentHousesResidental>
                ...
                </house>
            }
            else {
                if next_row contains the same house_id {
                    <apartamentHousesResidental>
                    ...
                    </apartamentHousesResidental>
                }
                else {
                    <house>
                        <apartamentHousesResidental>
                        ...
                        </apartamentHousesResidental>
                    ...
                    </house>
                }
            }
            */
            if(rs.getRow()==1){
                house_id = rs.getString(ID_HOUSE);
                buildHouseNode(rs, count_houses);
                count_houses++;
                buildApartmentNode(rs, count_apartment);
                count_apartment++;
            }
            else{
                if( rs.getString(ID_HOUSE).equals(house_id) ){
                    buildApartmentNode(rs, count_apartment);
                    count_apartment++;
                }
                else{
                    count_apartment=0;
                    house_id = rs.getString(ID_HOUSE);
                    buildHouseNode(rs, count_houses);
                    count_houses++;
                    buildApartmentNode(rs, count_apartment);
                    count_apartment++;
                }
            }
            
            if( (buffRowsVal != 0) && (count_rows > buffRowsVal) ){
                break;
            }
        }
    }

    /**
     * process own Build and Write logic for "all at once" output
     * @throws SQLException
     * @throws DatatypeConfigurationException
     * @throws JAXBException 
     */
    @Override
    protected void processForBuild() throws SQLException, DatatypeConfigurationException, JAXBException {
        //RootElement
        buildHousesNode();
        //build object
        buildObject(resultSet, 0);
        //terminate connection
        terminateConnection();
        //init jaxb Element
        initJAXBElement("houses", houses);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
    }
    
    /**
     * process own Build and Write logic for Streaming output
     * @throws XMLStreamException
     * @throws SQLException
     * @throws DatatypeConfigurationException
     * @throws JAXBException 
     */
    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        //RootElement
        buildHousesNode();
        
        xmlStreamWriter.writeStartDocument("utf-8", "1.0");//XML header
        xmlStreamWriter.writeStartElement("houses");// <houses>
        xmlStreamWriter.writeStartElement("systemID");// <systemID>
        xmlStreamWriter.writeCharacters(houses.getSystemID());
        xmlStreamWriter.writeEndElement();// </systemID>
        
        //"buffered" previous house node data after end of buffered object
        ApartamentHousesResidentalType previousHouseNode = null;
        
        while(inProgress)
        {
            //clear buffered object
            houses.getHouse().clear();
            //build buffered object
            buildObject(resultSet, BUFFERED_ROWS);
            
            int countObjectItem = 0;

            for(ApartamentHousesResidentalType houseNode : houses.getHouse()){
                countObjectItem++;
                
                //the first item
                if(countObjectItem == 1){
                    if(previousHouseNode != null){
                        //prev equals curr
                        if( (previousHouseNode.getHouseID()).equals(houseNode.getHouseID()) )
                        {
                            for(ApartamentHouseResidentalType apartmentNode : houseNode.getApartamentHousesResidental()){
                                previousHouseNode.getApartamentHousesResidental().add(apartmentNode);
                            }

                            initJAXBElement("house", previousHouseNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                        }
                        //prev DOESN'T equal curr
                        else{
                            initJAXBElement("house", previousHouseNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                            
                            initJAXBElement("house", houseNode);
                            marshaller.marshal(jaxbElement, xmlStreamWriter);
                        }
                    }
                    else{
                        initJAXBElement("house", houseNode);
                        marshaller.marshal(jaxbElement, xmlStreamWriter);
                    }
                }
                //the last item
                else if(countObjectItem == houses.getHouse().size()){
                    //do nothing
                }
                //others
                else{
                    initJAXBElement("house", houseNode);
                    marshaller.marshal(jaxbElement, xmlStreamWriter);
                }
                
                previousHouseNode = houseNode;
            }
        }
        //kinda the most final item
        initJAXBElement("house", previousHouseNode);
        marshaller.marshal(jaxbElement, xmlStreamWriter);
        //terminate connection to database
        terminateConnection();
        //close tags and finish XML-doc
        xmlStreamWriter.writeEndElement();// </houses>
        xmlStreamWriter.writeEndDocument();
    }
}
