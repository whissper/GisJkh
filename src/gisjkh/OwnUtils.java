package gisjkh;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Own utilities
 * @author SAV2
 */
public final class OwnUtils {   
    //constructor
    private OwnUtils(){}
    
    /**
     * check for empty
     * @param s String value
     * @return "is empty" statement
     */
    public static boolean empty(String s) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }
    
    /**
     * get current TimeStamp
     * @param format 1 - for XML file naming usage
     *               2 - for ApplicationLog file usage
     * @return String value of formated current date
     */
    public static String getCurrentTimeStamp(int format) {
        SimpleDateFormat sdfDate;
        
        switch(format){
            case 1:
                sdfDate = new SimpleDateFormat("dd.MM.yyyy.HH.mm");
                break;
            case 2:
                sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                break;
            default:
                sdfDate = new SimpleDateFormat("dd.MM.yyyy.HH.mm");
                break;
        }
        
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    
    /**
     * convert Date To XML-format
     * @param d
     * @return formated date-String for XML standart
     */
    public static String convertDateToXMLFormat(Date d) {
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }
    
    /**
     * convert DateTime To XML-format
     * @param dt
     * @return formated date-String for XML standart
     */
    public static String convertDatetimeToXMLFormat(Date dt) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dt);
    }
}
