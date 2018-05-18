package gisjkh;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * BooleanAdapter 
 * true writes "true"
 * null AND false write ""
 * @author SAV2
 */
public class BooleanAdapter extends XmlAdapter<String, Boolean>
{
    /*
    unmarshal : null and other_string = false; "true" = true  
    */
    @Override
    public Boolean unmarshal(String s) {
        return s == null ? false : s.equals("true");
    }
    /*
    marshal null and false = ""; true = "true"
    */
    @Override
    public String marshal(Boolean b) {
        return b == null ? "" : b ? "true" : "";
    }
    
}
