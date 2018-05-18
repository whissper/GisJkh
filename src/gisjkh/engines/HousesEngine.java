package gisjkh.engines;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.whissper.houses.CadastralInfoType;
import org.whissper.houses.HouseType;
import org.whissper.houses.HousesType;

/**
 * HousesEngine class
 * @author SAV2
 */
public class HousesEngine extends AbstractEngine
{
    //constants
    private boolean inProgress = true;
    
    //fields
    private static final String ID = "ID";
    private static final String FIAS_ID = "FIASID";
    private static final String HOUSE_TYPE = "HOUSETYPE";
    private static final String OKTMO = "OKTMO";
    private static final String CADASTRAL_NUMBER = "CADASTRALNUMBER";
    private static final String NO_RSO_GKN_EGRP_REGISTERED = "NO_RSO_GKN_EGRP_REGISTERED";
    private static final String NO_RSO_GKN_EGRP_DATA = "NO_RSO_GKN_EGRP_DATA";
    private static final String ACTION = "ACT";
    
    private HousesType houses;
    
    public HousesEngine() {
        super(
            "org.whissper.houses",//CONTEXT_PATH
            "Houses",//ENTITY_CODE
            "xml-resources/jaxb/Houses/Houses.xsd",//VALIDATION_SCHEME_FILE
            "output/",//XML_FILE_PATH
            "output/",//LOG_FILE_PATH
            "SELECT * FROM GIS_HOUSES"//QUERY_STRING
        );
    }
    
    private void buildHousesNode() {
        houses = new HousesType();
        houses.setSystemID(SYSTEM_ID);
    }
    
    private void buildHouseNode(ResultSet resultSet, int counter) throws SQLException {
        houses.getHouse().add(new HouseType());
        HouseType house = houses.getHouse().get(counter);
        
        if(resultSet.getString(ID) != null){
            house.setId(resultSet.getString(ID));
        }
        
        if(resultSet.getString(FIAS_ID) != null){
            house.setFIASID(resultSet.getString(FIAS_ID));
        }
        
        if(resultSet.getString(HOUSE_TYPE) != null){
            house.setHouseType(resultSet.getString(HOUSE_TYPE).trim());
        }
        
        if(resultSet.getString(OKTMO) != null){
            house.setOKTMO(resultSet.getString(OKTMO));
        }
        
        CadastralInfoType cadastralInfo = new CadastralInfoType();
        house.setCadastralInfo(cadastralInfo);
        
        if(resultSet.getString(CADASTRAL_NUMBER) != null)
        {
            cadastralInfo.setCadastralNumber(resultSet.getString(CADASTRAL_NUMBER));
        }else{
            if(resultSet.getBoolean(NO_RSO_GKN_EGRP_DATA)){
                cadastralInfo.setNoRSOGKNEGRPData(Boolean.TRUE);
            }
        }
        
        if(resultSet.getString(ACTION) != null){
            house.setAction(resultSet.getString(ACTION).trim());
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
        
        int count_rows = 0;

        while(inProgress=rs.next()) {
            count_rows++;
            /*
            <house>
            ...
            </house>
            */
            buildHouseNode(rs, count_houses);
            count_houses++;
            
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
        
        while(inProgress)
        {
            houses.getHouse().clear();
            
            buildObject(resultSet, BUFFERED_ROWS);
            
            for(HouseType houseNode : houses.getHouse()){
                initJAXBElement("house", houseNode);
                marshaller.marshal(jaxbElement, xmlStreamWriter);
            }
        }
        
        terminateConnection();
        
        xmlStreamWriter.writeEndElement();// </houses>
        xmlStreamWriter.writeEndDocument();
    }
    
}
