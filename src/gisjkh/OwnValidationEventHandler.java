package gisjkh;

import java.io.BufferedWriter;
import java.io.IOException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 * Own ValidationEventHandler
 * @author SAV2
 */
public class OwnValidationEventHandler implements ValidationEventHandler
{
    BufferedWriter writer;
    
    public OwnValidationEventHandler(BufferedWriter writerInstance){
        super();
        writer = writerInstance;
    }
    
    @Override
    public boolean handleEvent(ValidationEvent ve) {

        if( ve.getSeverity()==ValidationEvent.FATAL_ERROR ||  
            ve .getSeverity()==ValidationEvent.ERROR )
        {
            ValidationEventLocator  locator = ve.getLocator();
            try{
                writer.append( "Error: at line " + locator.getLineNumber() + ", column " + locator.getColumnNumber() + " ------------------------" + "\r\n" );
                writer.append( ve.getMessage() + "\r\n" );
                writer.append( "-------------------------------------------------------" + "\r\n" );
            }catch(IOException ex) {
                System.out.println("Exception occured during writing in Validation Log. OwnValidationEventHandler --> Method: handleEvent(ValidationEvent ve):  " + ex);
            }
            
        }
        return true;
    }
}
