package gisjkh.engines;

import gisjkh.OwnUtils;
import gisjkh.OwnValidationEventHandler;
import gisjkh.constants.GlobalConstants;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javanet.staxutils.IndentingXMLStreamWriter;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.SchemaFactory;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

/**
 * Abstract Engine
 * @author SAV2
 */
abstract class AbstractEngine {
    //constants
    protected final String CONTEXT_PATH;
    protected final String ENTITY_CODE;
    protected final String VALIDATION_SCHEME_FILE;//with path
    protected final String XML_FILE_PATH;
    protected final String LOG_FILE_PATH;
    protected final String QUERY_STRING;
    
    //properties
    protected int BUFFERED_ROWS;
    protected String APP_LOG_FILE_PATH;
    protected String SYSTEM_ID;
    protected String DB_URL;
    protected String DB_USER;
    protected String DB_PASSWORD;
    protected String DB_ENCODING;
    protected String DB_ROLE;
    
    //common vars
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;
    protected File fileXML;
    protected XMLStreamWriter xmlStreamWriter;
    protected Marshaller marshaller;
    protected BufferedWriter logWriter;
    protected JAXBElement jaxbElement;
    protected String xmlFileName;
    protected BufferedWriter appLogWriter;
    protected boolean executionSuccess;
    protected ResourceBundle messages;

    //constructor
    protected AbstractEngine( String contextPath,
                              String entityCode,
                              String validationSchemeFile,
                              String xmlFilePath,
                              String logFilePath,
                              String queryString
                            )
    {
        CONTEXT_PATH           = contextPath;
        ENTITY_CODE            = entityCode;
        VALIDATION_SCHEME_FILE = validationSchemeFile;
        XML_FILE_PATH          = xmlFilePath;
        LOG_FILE_PATH          = logFilePath;
        QUERY_STRING           = queryString;
        
        executionSuccess = true;
        
        //set up properties
        loadConfig();
    }
    
    /**
     * set up properties
     */
    private void loadConfig() {
        File fileConfig = new File("app.prop");
        
        Properties props = new Properties();
        BufferedWriter bw = null;
        BufferedReader br = null;
        Locale currentLocale = new Locale("en");
        
        BUFFERED_ROWS        = GlobalConstants.BUFFERED_ROWS;
        APP_LOG_FILE_PATH    = GlobalConstants.APP_LOG_FILE_PATH;
        SYSTEM_ID            = GlobalConstants.SYSTEM_ID;
        DB_URL               = GlobalConstants.DB_URL;
        DB_USER              = GlobalConstants.DB_USER;
        DB_PASSWORD          = GlobalConstants.DB_PASSWORD;
        DB_ENCODING          = GlobalConstants.DB_ENCODING;
        DB_ROLE              = GlobalConstants.DB_ROLE;
        
        try {
            if( fileConfig.createNewFile() )//file doesn't exist and it was created atm
            {
                bw = new BufferedWriter( new FileWriter(fileConfig, false) );
                
                props.setProperty("buffered_rows", Integer.toString(GlobalConstants.BUFFERED_ROWS));
                props.setProperty("app_log_file_path", GlobalConstants.APP_LOG_FILE_PATH);
                props.setProperty("system_id", GlobalConstants.SYSTEM_ID);
                props.setProperty("db_url", GlobalConstants.DB_URL);
                props.setProperty("db_user", GlobalConstants.DB_USER);
                props.setProperty("db_password", GlobalConstants.DB_PASSWORD);
                props.setProperty("db_encoding", GlobalConstants.DB_ENCODING);
                props.setProperty("db_role", GlobalConstants.DB_ROLE);
                props.setProperty("language", GlobalConstants.LANG);
                
                props.store(bw, "Colon (:) and backslashes (\\) are special characters in properties file. So, they have to be escaped.");
            }
            else {
                if(fileConfig.exists())//file really exists
                {
                    br = new BufferedReader( new FileReader(fileConfig) );
                    
                    props.load(br);
                    
                    BUFFERED_ROWS        = Integer.parseInt( props.getProperty("buffered_rows", "50000") );
                    APP_LOG_FILE_PATH    = props.getProperty("app_log_file_path", "");
                    SYSTEM_ID            = props.getProperty("system_id", "8702");
                    DB_URL               = props.getProperty("db_url", "jdbc:firebirdsql://some-domain:3050/d:\\fbbases\\Komunalka.FDB");
                    DB_USER              = props.getProperty("db_user", "***");
                    DB_PASSWORD          = props.getProperty("db_password", "***");
                    DB_ENCODING          = props.getProperty("db_encoding", "UTF8");
                    DB_ROLE              = props.getProperty("db_role", "READONLY");
                    
                    currentLocale = new Locale(props.getProperty("language", "en"));
                }
            }
        } catch (IOException ex) {
            System.out.println("Exception occured during configuration loading. AbstractEngine --> Method: loadConfig(): " + ex);
        } finally {
            try {
                if(bw != null){bw.close();}
                if(br != null){br.close();}
            } catch (IOException ex) {
                System.out.println("Exception occured during configuration loading. AbstractEngine --> Method: loadConfig(): " + ex);
            }
        }
        
        messages = ResourceBundle.getBundle("localization.Messages", currentLocale);
    }
    
    /**
     * initialize JAXB Element
     * @param qname tag name i.e. "qualified name"
     * @param object instance of class which is being serialized
     */
    protected void initJAXBElement(String qname, Object object) {
        jaxbElement = new JAXBElement(new QName(qname), object.getClass(), object);
    }
    
    /**
     * form ResultSet (and keep it open)
     * @param queryStr query string
     */
    protected void formResultSet(String queryStr) {
        connection = null;
        statement = null;
        resultSet = null;
        
        String url = DB_URL;
        
        Properties conProps = new Properties();
        conProps.setProperty("userName", DB_USER);
        conProps.setProperty("password", DB_PASSWORD);
        conProps.setProperty("encoding", DB_ENCODING);
        conProps.setProperty("roleName", DB_ROLE);
        
        System.out.println( messages.getString("result_set_start") );
        
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            connection = DriverManager.getConnection(url, conProps);
            statement = connection.createStatement();       
            resultSet = statement.executeQuery(queryStr);
            
            System.out.println( messages.getString("done") );
        }
        catch (ClassNotFoundException | SQLException ex){
            System.out.println( "Exception occured during formation of Result set. AbstractEngine --> Method formResultSet(String queryStr): " + ex );
            writeErrorAppLog("Exception occured during formation of Result set. AbstractEngine --> Method formResultSet(String queryStr): " + ex);
        }
    }
    
    /**
     * close ResultSet
     * close Statement
     * close Connection
     */
    protected void terminateConnection() {
        try {
            if(resultSet!=null)resultSet.close();
            if(statement!=null)statement.close();
            if(connection!=null)connection.close();
        }
        catch (SQLException ex){
            System.out.println("Exception occured during termination of connection. AbstractEngine --> Method: terminateConnection(): " + ex);
            writeErrorAppLog("Exception occured during termination of connection. AbstractEngine --> Method: terminateConnection(): " + ex);
        }
    }
    
    /**
     * start Application Log
     */
    protected void startAppLog(){
        File fileLog = new File(APP_LOG_FILE_PATH + "ApplicationLog" +".log");
        if(fileLog.getParentFile()!=null){ fileLog.getParentFile().mkdirs(); }// Will create parent directories if not exists
        try {
            fileLog.createNewFile();
            appLogWriter = new BufferedWriter( new FileWriter(fileLog, true) );
            appLogWriter.append(ENTITY_CODE +" Engine started at "+ OwnUtils.getCurrentTimeStamp(2) +"\r\n");
        } catch (IOException ex) {
            System.out.println("Exception occured during application logging process. AbstractEngine --> Method: startAppLog(): " + ex);
        }
    }
    
    /**
     * write error in Application Log
     * @param errorStr Exception description
     */
    protected void writeErrorAppLog(String errorStr){
        executionSuccess = false;
        try {
            appLogWriter.append("Error: "+ errorStr +"\r\n");
        } catch (IOException ex) {
            System.out.println("Exception occured during application logging process. AbstractEngine --> Method: writeErrorAppLog(): " + ex);
        }
    }
    
    /**
     * end Application Log
     */
    protected void endAppLog(){
        try{
            if(executionSuccess){
                appLogWriter.append(ENTITY_CODE +" Engine ended at "+ OwnUtils.getCurrentTimeStamp(2) +"\r\n");
                appLogWriter.append("Execution result: SUCCESS" +"\r\n");
                appLogWriter.append("--------------------------------------------------" +"\r\n\r\n");
            } else {
                appLogWriter.append(ENTITY_CODE +" Engine ended at "+ OwnUtils.getCurrentTimeStamp(2) +"\r\n");
                appLogWriter.append("Execution result: FAIL" +"\r\n");
                appLogWriter.append("--------------------------------------------------" +"\r\n\r\n");
            }
            appLogWriter.close();
        } catch (IOException ex) {
            System.out.println("Exception occured during application logging process. AbstractEngine --> Method: endAppLog(): " + ex);
        }
    }
    
    /**
     * open OR create XML-doc for output action
     */
    protected void initXMLFile() {
        System.out.println( messages.getString("init_XML_file") );
        
        fileXML = new File( XML_FILE_PATH + ENTITY_CODE +"_"+ SYSTEM_ID +"_"+ OwnUtils.getCurrentTimeStamp(1) +".xml" );
        if(fileXML.getParentFile()!=null){ fileXML.getParentFile().mkdirs(); }// Will create parent directories if not exists
        try {
            fileXML.createNewFile();
            System.out.println( messages.getString("done") );
        }
        catch (IOException ex) {
            System.out.println("Exception occured during initialization of XML-file. AbstractEngine --> Method: initXMLFile(): " + ex);
            writeErrorAppLog("Exception occured during initialization of XML-file. AbstractEngine --> Method: initXMLFile(): " + ex);
        }
        xmlFileName = FilenameUtils.removeExtension(fileXML.getName());
    }
    
    /**
     * prepare XML-file and open StreamWriter for it
     */
    protected void prepareXMLdocOutput() {
        //XML-doc to output
        initXMLFile();
        //XML Stream writer
        System.out.println( messages.getString("prepare_XML_doc_output") );
        
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(fileXML);
        } catch (FileNotFoundException ex) {
            System.out.println("Exception occured during preparation of XML output stream. AbstractEngine --> Method: prepareXMLdocOutput(): " + ex);
            writeErrorAppLog("Exception occured during preparation of XML output stream. AbstractEngine --> Method: prepareXMLdocOutput(): " + ex);
        }
        if(fs != null){
            OutputStreamWriter osw = new OutputStreamWriter(fs, StandardCharsets.UTF_8);
            //BufferedOutputStream bfs = new BufferedOutputStream(fs);
            BufferedWriter bw = new BufferedWriter(osw);
            XMLOutputFactory xof = XMLOutputFactory.newFactory();
            try {
                //xmlStreamWriter = xof.createXMLStreamWriter(fs);// <-- too slow!
                //xmlStreamWriter = xof.createXMLStreamWriter(bfs);
                xmlStreamWriter = xof.createXMLStreamWriter(bw);
                xmlStreamWriter = new IndentingXMLStreamWriter(xmlStreamWriter);
                
                System.out.println( messages.getString("done") );
            } catch (XMLStreamException ex) {
                System.out.println("Exception occured during preparation of XML output stream. AbstractEngine --> Method: prepareXMLdocOutput(): " + ex);
                writeErrorAppLog("Exception occured during preparation of XML output stream. AbstractEngine --> Method: prepareXMLdocOutput(): " + ex);
            } 
        }
    }
    
    /**
     * 1) Create Log-file
     * 2) open it with file writer
     */
    protected void prepareValidationLog() {
        File fileLog = new File(LOG_FILE_PATH +"LOG_"+ xmlFileName +".log");
        if(fileLog.getParentFile()!=null){ fileLog.getParentFile().mkdirs(); }// Will create parent directories if not exists
        try {
            fileLog.createNewFile();
            //logWriter = new FileWriter(fileLog, false);
            logWriter = new BufferedWriter( new FileWriter(fileLog, false) );
            logWriter.append("XML_FILE: " + xmlFileName +".xml" + "\r\n\r\n");
        } catch (IOException ex) {
            System.out.println("Exception occured during preparation of Validation LOG. AbstractEngine --> Method: prepareValidationLog(): " + ex);
            writeErrorAppLog("Exception occured during preparation of Validation LOG. AbstractEngine --> Method: prepareValidationLog(): " + ex);
        }
    }
    
    /**
     * validate on File
     * @param f file to check out
     */
    protected void validateOnFile(File f) {
        System.out.println( messages.getString("validate_on_file") );
        
        //Log-file output + File writer
        prepareValidationLog();
        if(logWriter != null){
            try {
                //Unmarshaller
                JAXBContext jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                unmarshaller.setSchema( SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(VALIDATION_SCHEME_FILE)) );
                unmarshaller.setEventHandler( new OwnValidationEventHandler(logWriter) );
                //unmarshal
                unmarshaller.unmarshal(f);
                //close Log-file writer
                logWriter.close();
                
                System.out.println( messages.getString("done") );
            } catch (JAXBException | SAXException | IOException ex) {
                System.out.println("Exception occured during validation on file. AbstractEngine --> Method: validateOnFile(File f): " + ex);
                writeErrorAppLog("Exception occured during validation on file. AbstractEngine --> Method: validateOnFile(File f): " + ex);
            }
        }
    }
    
    /**
     * initializing marshaler
     */
    protected void prepareMarshall() {
        System.out.println( messages.getString("prepare_marshall") );
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            System.out.println( messages.getString("done") );
        } catch (JAXBException ex) {
            System.out.println("Exception occured during initialization of marshaler. AbstractEngine --> Method: prepareMarshall(): " + ex);
            writeErrorAppLog("Exception occured during initialization of marshaler. AbstractEngine --> Method: prepareMarshall(): " + ex);
        }
    }
    
    /**
     * initializing marshaler for streaming to XML
     */
    protected void prepareMarshallForStream() {
        System.out.println( messages.getString("prepare_marshall") );
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            
            System.out.println( messages.getString("done") );
        } catch (JAXBException ex) {
            System.out.println("Exception occured during initialization of marshaler. AbstractEngine --> Method: prepareMarshallForStream(): " + ex);
            writeErrorAppLog("Exception occured during initialization of marshaler. AbstractEngine --> Method: prepareMarshallForStream(): " + ex);
        }
    }
    
    /**
     * process own Build and Write logic for "all at once" output
     * @throws SQLException
     * @throws DatatypeConfigurationException
     * @throws JAXBException 
     */
    protected abstract void processForBuild() throws SQLException, DatatypeConfigurationException, JAXBException;
    
    /**
     * process own Build and Write logic for Streaming output
     * @throws XMLStreamException
     * @throws SQLException
     * @throws DatatypeConfigurationException
     * @throws JAXBException 
     */
    protected abstract void processForStream() throws XMLStreamException, SQLException, DatatypeConfigurationException, JAXBException;
    
    /**
     * build XML-doc
     * @param needToValidate boolean parameter for validation action
     */
    public void buildXML(boolean needToValidate) {
        startAppLog();
        
        //ResultSet
        formResultSet(QUERY_STRING);
        //if resultSet is "null" value
        if(resultSet == null){
            endAppLog();
            return;//stop the next actions
        }
        
        //XML-doc to output
        initXMLFile();
        //if fileXML doesn't exist
        if(!fileXML.exists()){
            endAppLog();
            return;//stop the next actions
        }
        
        //initializing marshaler
        prepareMarshall();
        //if marshaller is "null" value
        if(marshaller == null){
            endAppLog();
            return;//stop the next actions
        }
        
        //process own Build and Write logic for "all at once" output
        System.out.println( messages.getString("process_and_write") );
        try {
            processForBuild();
            System.out.println( messages.getString("done") );
        } catch (SQLException | DatatypeConfigurationException | JAXBException ex) {
            System.out.println("Exception occured during building XML-file. AbstractEngine --> Method: buildXML(boolean needToValidate): " + ex);
            writeErrorAppLog("Exception occured during building XML-file. AbstractEngine --> Method: buildXML(boolean needToValidate): " + ex);
            endAppLog();
            return;
        }
        
        //validation on file
        if(needToValidate){
            validateOnFile(fileXML);
        }
        
        endAppLog();
    }
    
    /**
     * Stream Data to XML-doc
     * @param needToValidate boolean parameter for validation action 
     */
    public void streamToXML(boolean needToValidate) {
        startAppLog();
        
        //ResultSet
        formResultSet(QUERY_STRING);
        //if resultSet is "null" value
        if(resultSet == null){
            endAppLog();
            return;//stop the next actions
        }
        
        //XML-doc to output + XML Stream writer
        prepareXMLdocOutput();
        //if xmlStreamWriter is "null" value
        if(xmlStreamWriter == null){
            endAppLog();
            return;//stop the next actions
        }
        
        //initializing marshaler
        prepareMarshallForStream();
        //if marshaller is "null" value
        if(marshaller == null){
            endAppLog();
            return;//stop the next actions
        }
        
        //process own Build and Write logic for Streaming output
        System.out.println( messages.getString("process_and_write") );
        try {
            processForStream();
            //close XML Stream writer
            xmlStreamWriter.close();
            System.out.println( messages.getString("done") );
        } catch (XMLStreamException | SQLException | DatatypeConfigurationException | JAXBException ex) {
            System.out.println("Exception occured during streaming to XML-file. AbstractEngine --> Method: streamToXML(boolean needToValidate): " + ex);
            writeErrorAppLog("Exception occured during streaming to XML-file. AbstractEngine --> Method: streamToXML(boolean needToValidate): " + ex);
            endAppLog();
            return;
        }

        //validation on file
        if(needToValidate){
            validateOnFile(fileXML);
        }
        
        endAppLog();
    }
}
