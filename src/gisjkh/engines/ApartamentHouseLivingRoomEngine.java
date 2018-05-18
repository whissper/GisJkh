package gisjkh.engines;

import gisjkh.OwnUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import org.whissper.apartamenthouselivingroom.ApartamentHouseLivingRoomType;
import org.whissper.apartamenthouselivingroom.ApartamentHousesLivingRoomType;
import org.whissper.apartamenthouselivingroom.CadastralInfoType;
import org.whissper.apartamenthouselivingroom.HousesLivingRoomType;

/**
 * ApartamentHouseLivingRoomEngine class
 * @author SAV2
 * @deprecated 
 */
public class ApartamentHouseLivingRoomEngine extends AbstractEngine
{
    //constants
    static final int ROUND_SCALE = 4;
    static final int ROUND_MODE = BigDecimal.ROUND_HALF_UP;
    
    private boolean inProgress = true;
    
    private static final String ID_HOUSE = "ID_HOME";
    private static final String ID_FIAS = "ID_FIAS";
    private static final String ID_APARTMENT = "ID";
    private static final String CADASTRAL_NUMBER = "CADASTRALNUMBER";
    private static final String NO_RSO_GKN_EGRP_REGISTERED = "NO_RSO_GKN_EGRP_REGISTERED";
    private static final String NO_RSO_GKN_EGRP_DATA = "NO_RSO_GKN_EGRP_DATA";
    private static final String PREMISE_NUM = "PREMISENUM";
    private static final String ID_PARENT = "ID_PARENT";
    private static final String ENTRANCE_NUM = "ENTRANCENUM";
    private static final String ROOM_NUM = "ROOMNUM";
    private static final String SQUARE = "TOTALSQUARE";
    private static final String TERMINATION_DATE = "TERMINATIONDATE";
    private static final String ACTION = "ACT";
    
    //RootElement
    private HousesLivingRoomType houses;
    //AfterRootElement
    private ApartamentHousesLivingRoomType house;
    
    public ApartamentHouseLivingRoomEngine() {
        super(
            "org.whissper.apartamenthouselivingroom",//CONTEXT_PATH
            "ApartamentHouseLivingRoom",//ENTITY_CODE
            "xml-resources/jaxb/ApartamentHouseLivingRoom/ApartamentHouseLivingRoom.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_APARTAMENTHOUSELIVINGROOM"//QUERY_STRING
        );
    }
    
    /**
     * Build Houses Node @RootElement
     */
    private void buildHousesNode() {
        //<houses>
        houses = new HousesLivingRoomType();
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
        houses.getHouse().add(new ApartamentHousesLivingRoomType());
        house = houses.getHouse().get(counter);
        //<houses> - <house> - <houseID>
        if(resultSet.getString(ID_HOUSE) != null){
            house.setHouseID(resultSet.getString(ID_HOUSE));
        }
        //<houses> - <house> - <FIASID>
        if(resultSet.getString(ID_FIAS) != null){
            house.setHouseID(resultSet.getString(ID_FIAS));
        }
    }
    
    private void buildApartmentNode(ResultSet resultSet, int counter) throws SQLException, DatatypeConfigurationException {
        //<houses> - <house> - <apartamentHousesLivingRoom>
        house.getApartamentHousesLivingRoom().add(new ApartamentHouseLivingRoomType());
        ApartamentHouseLivingRoomType apartment = house.getApartamentHousesLivingRoom().get(counter);
        //<houses> - <house> - <apartamentHousesLivingRoom> - <id>
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
            apartment.setPremiseNum(resultSet.getString(PREMISE_NUM));
        }
        //<houses> - <house> - <apartamentHousesResidental> - <parentId>
        if(resultSet.getString(ID_PARENT) != null){
            apartment.setPremiseNum(resultSet.getString(ID_PARENT));
        }else{
            apartment.setPremiseNum(resultSet.getString(ID_HOUSE));
        }
        //<houses> - <house> - <apartamentHousesResidental> - <entranceNum>
        if(resultSet.getByte(ENTRANCE_NUM) != 0){
            apartment.setEntranceNum(resultSet.getByte(ENTRANCE_NUM));
        }else{
            apartment.setEntranceNum((byte)1);
        }
        //<houses> - <house> - <apartamentHousesResidental> - <roomNumber>
        if(resultSet.getString(ROOM_NUM) != null){
            apartment.setPremiseNum(resultSet.getString(ROOM_NUM));
        }
        //<houses> - <house> - <apartamentHousesResidental> - <square>
        if(resultSet.getDouble(SQUARE) != 0){
            apartment.setSquare( new BigDecimal(resultSet.getDouble(SQUARE)).setScale(ROUND_SCALE, ROUND_MODE) );
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
                    <apartamentHousesLivingRoom>
                    ...
                    </apartamentHousesLivingRoom>
                ...
                </house>
            }
            else {
                if next_row contains the same house_id {
                    <apartamentHousesLivingRoom>
                    ...
                    </apartamentHousesLivingRoom>
                }
                else {
                    <house>
                        <apartamentHousesLivingRoom>
                        ...
                        </apartamentHousesLivingRoom>
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
    
    @Override
    protected void processForBuild() throws SQLException, DatatypeConfigurationException, JAXBException {
        //RootElement
        /*
        buildHousesNode();
        //build object
        buildObject(resultSet, 0);
        //terminate connection
        terminateConnection();
        //init jaxb Element
        initJAXBElement("houses", houses);
        //marshall
        marshaller.marshal(jaxbElement, fileXML);
        */
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
