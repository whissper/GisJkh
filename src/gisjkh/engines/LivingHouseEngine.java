package gisjkh.engines;

import gisjkh.OwnUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import org.whissper.livinghouse.CadastralInfoType;
import org.whissper.livinghouse.HousesLivingHouseType;
import org.whissper.livinghouse.LivingHouseType;
import org.whissper.livinghouse.LivingHousesType;

/**
 * LivingHouseEngine class
 * @author SAV2
 */
public class LivingHouseEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 4;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    private static final String ID                         = "ID";
    private static final String ID_HOUSE                   = "HOUSEID";
    private static final String CADASTRAL_NUMBER           = "CADASTRALNUMBER";
    private static final String NO_RSO_GKN_EGRP_REGISTERED = "NO_RSO_GKN_EGRP_REGISTERED";
    private static final String NO_RSO_GKN_EGRP_DATA       = "NO_RSO_GKN_EGRP_DATA";
    private static final String ROOM_NUM                   = "ROOMNUM";
    private static final String TOTAL_SQUARE               = "TOTALSQUARE";
    private static final String RESIDENTIAL_SQUARE         = "RESIDENALSQUARE";
    private static final String HEATED_AREA                = "HEATEDAREA";
    private static final String TERMINATION_DATE           = "TERMINATIONDATE";
    private static final String ACTION                     = "ACT";
    
    HousesLivingHouseType houses;
    LivingHousesType house;
    
    public LivingHouseEngine() {
        super(
            "org.whissper.livinghouse",//CONTEXT_PATH
            "LivingHouse",//ENTITY_CODE
            "xml-resources/jaxb/LivingHouse/LivingHouse.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_LIVINGHOUSE"//QUERY_STRING
        );
    }
    
    private void buildHousesNode() {
        houses = new HousesLivingHouseType();
        houses.setSystemID(SYSTEM_ID);
    }
    
    private void buildHouseNode(ResultSet resultSet, int counter) throws SQLException {
        houses.getHouse().add(new LivingHousesType());
        house = houses.getHouse().get(counter);
        if(resultSet.getString(ID_HOUSE) != null){
            house.setHouseID(resultSet.getString(ID_HOUSE));
        }
    }
    
    private void buildLivingHouseNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        house.getLivingHouse().add(new LivingHouseType());
        LivingHouseType livingHouse = house.getLivingHouse().get(counter);
       
        if(resultSet.getString(ID) != null){
            livingHouse.setId(resultSet.getString(ID));
        }
       
        CadastralInfoType cadastralInfo = new CadastralInfoType();
        livingHouse.setCadastralInfo(cadastralInfo);
       
        if(resultSet.getString(CADASTRAL_NUMBER) != null){
            cadastralInfo.setCadastralNumber(resultSet.getString(CADASTRAL_NUMBER));
        }else{
            //if(resultSet.getBoolean(NO_RSO_GKN_EGRP_REGISTERED)){
            //    cadastralInfo.setNoRSOGKNEGRPRegistered(Boolean.TRUE);
            //}

            if(resultSet.getBoolean(NO_RSO_GKN_EGRP_DATA)){
                cadastralInfo.setNoRSOGKNEGRPData(Boolean.TRUE);
            }
        }
        
        if(resultSet.getString(ROOM_NUM) != null){
            livingHouse.setRoomNumber(resultSet.getString(ROOM_NUM));
        }
        
        if(resultSet.getDouble(TOTAL_SQUARE) != 0){
            livingHouse.setTotalSquare( new BigDecimal(resultSet.getDouble(TOTAL_SQUARE)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        
        if(resultSet.getDouble(RESIDENTIAL_SQUARE) != 0){
            livingHouse.setResidentialSquare( new BigDecimal(resultSet.getDouble(RESIDENTIAL_SQUARE)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        
        if(resultSet.getDouble(HEATED_AREA) != 0){
            livingHouse.setHeatedArea( new BigDecimal(resultSet.getDouble(HEATED_AREA)).setScale(ROUND_SCALE, ROUND_MODE) );
        }
        
        if(resultSet.getDate(TERMINATION_DATE) != null){
            livingHouse.setTerminationDate( DatatypeFactory.newInstance().newXMLGregorianCalendar(OwnUtils.convertDateToXMLFormat(resultSet.getDate(TERMINATION_DATE))) );
        }
        
        if(resultSet.getString(ACTION) != null){
            livingHouse.setAction(resultSet.getString(ACTION).trim());
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
        int count_living_houses = 0;
        String house_id = "-1";//negative value for special purpose when comparing it with first house_id
        
        int count_rows = 0;
        
        while(inProgress=rs.next()) {
            count_rows++;
            /*
            if getting first_row {
                <house>
                    <livingHouse>
                    ...
                    </livingHouse>
                ...
                </house>
            }
            else {
                if next_row contains the same house_id {
                    <livingHouse>
                    ...
                    </livingHouse>
                }
                else {
                    <house>
                        <livingHouse>
                        ...
                        </livingHouse>
                    ...
                    </house>
                }
            }
            */
            if(rs.getRow()==1){
                house_id = rs.getString(ID_HOUSE);
                buildHouseNode(rs, count_houses);
                count_houses++;
                buildLivingHouseNode(rs, count_living_houses);
                count_living_houses++;
            }
            else{
                if( rs.getString(ID_HOUSE).equals(house_id) ){
                    buildLivingHouseNode(rs, count_living_houses);
                    count_living_houses++;
                }
                else{
                    count_living_houses=0;
                    house_id = rs.getString(ID_HOUSE);
                    buildHouseNode(rs, count_houses);
                    count_houses++;
                    buildLivingHouseNode(rs, count_living_houses);
                    count_living_houses++;
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

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        
        buildHousesNode();
        
        xmlStreamWriter.writeStartDocument("utf-8", "1.0");//XML header
        xmlStreamWriter.writeStartElement("houses");// <houses>
        xmlStreamWriter.writeStartElement("systemID");// <systemID>
        xmlStreamWriter.writeCharacters(houses.getSystemID());
        xmlStreamWriter.writeEndElement();// </systemID>
        
        //"buffered" previous house node data after end of buffered object
        LivingHousesType previousHouseNode = null;
        
        while(inProgress)
        {
            houses.getHouse().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);
            
            int countObjectItem = 0;
            
            for(LivingHousesType houseNode : houses.getHouse()){
                countObjectItem++;
                
                //the first item
                if(countObjectItem == 1){
                    if(previousHouseNode != null){
                        //prev equals curr
                        if( (previousHouseNode.getHouseID()).equals(houseNode.getHouseID()) )
                        {
                            for(LivingHouseType livingHouseNode : houseNode.getLivingHouse()){
                                previousHouseNode.getLivingHouse().add(livingHouseNode);
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
                            
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </houses>
        xmlStreamWriter.writeEndDocument();
    }
}
