package gisjkh;

import gisjkh.engines.AccountFLRSOEngine;
import gisjkh.engines.AcknowledgmentEngine;
import gisjkh.engines.ApartamentHouseResidentalEngine;
import gisjkh.engines.BankAccountsEngine;
import gisjkh.engines.ChargeDeptPaymentEngine;
import gisjkh.engines.ContractRSOEngine;
import gisjkh.engines.HousesEngine;
import gisjkh.engines.LivingHouseEngine;
import gisjkh.engines.MeteringDeviceValuesEngine;
import gisjkh.engines.MeteringDevicesEngine;
import gisjkh.engines.MunicipalServicePaymentEngine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * GIS JKH alpha-version
 * @author SAV2
 * @version 0.9.0
 * @since 05.09.2017
 */
public class GisJkh
{
    public static void main(String[] args)
    {
        //localization settings -start:
        Locale currentLocale = new Locale("en");
        ResourceBundle messages;
        
        File fileConfig = new File("app.prop");
        Properties props = new Properties();
        BufferedReader br = null;
        
        try {
            if(fileConfig.exists())//file really exists
            {
                br = new BufferedReader( new FileReader(fileConfig) );

                props.load(br);

                currentLocale = new Locale(props.getProperty("language", "en"));
            }
        } catch (IOException ex) {
            System.out.println("Exception occured during configuration loading. GisJkh --> Method: main(String[] args): " + ex);
        } finally {
            try {
                if(br != null){br.close();}
            } catch (IOException ex) {
                System.out.println("Exception occured during configuration loading. GisJkh --> Method: main(String[] args): " + ex);
            }
        }
        
        messages = ResourceBundle.getBundle("localization.Messages", currentLocale);
        //localization settings -end;
        
        Options options = new Options();

        Option engine = new Option("e", "engine", true, messages.getString("engine_param_description"));
        engine.setRequired(true);
        options.addOption(engine);
        
        Option validate = new Option("v", "validate", true, messages.getString("validation_param_description"));
        validate.setRequired(false);
        options.addOption(validate);
                
            
        CommandLineParser parser = new DefaultParser();
        //HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            //formatter.printHelp("GIS-JKH v0.1.0", options);
            System.out.println("Usage: -e <arg> -v <arg>");
            System.out.println("------------------------------------------------------------------------------------");
            for(Option opt : options.getOptions()){
                System.out.println("-"+ opt.getOpt() +", "+"--"+ opt.getLongOpt() +" <arg>");
                System.out.println(opt.getDescription());
            }
            System.out.println("------------------------------------------------------------------------------------");

            System.exit(1);
            return;
        }

        String engineValue = cmd.getOptionValue("engine");
        String validateValue = cmd.getOptionValue("validate");
        
        switch(engineValue){
            case "1":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new HousesEngine()).streamToXML(true);
                }else{
                    (new HousesEngine()).streamToXML(false);
                }
                break;
            case "2":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new ApartamentHouseResidentalEngine()).streamToXML(true);
                }else{
                    (new ApartamentHouseResidentalEngine()).streamToXML(false);
                }
                break;
            case "3":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new LivingHouseEngine()).streamToXML(true);
                }else{
                    (new LivingHouseEngine()).streamToXML(false);
                }
                break;
            case "4":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new AccountFLRSOEngine()).streamToXML(true);
                }else{
                    (new AccountFLRSOEngine()).streamToXML(false);
                }
                break;
            case "5":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new MeteringDevicesEngine()).streamToXML(true);
                }else{
                    (new MeteringDevicesEngine()).streamToXML(false);
                }
                break;
            case "6":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new MeteringDeviceValuesEngine()).streamToXML(true);
                }else{
                    (new MeteringDeviceValuesEngine()).streamToXML(false);
                }
                break;
            case "7":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new BankAccountsEngine()).buildXML(true);
                }else{
                    (new BankAccountsEngine()).buildXML(false); 
                }
                break;
            case "8":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new MunicipalServicePaymentEngine()).streamToXML(true);
                }else{
                    (new MunicipalServicePaymentEngine()).streamToXML(false); 
                }
                break;
            case "9":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new ChargeDeptPaymentEngine()).streamToXML(true);
                }else{
                    (new ChargeDeptPaymentEngine()).streamToXML(false); 
                }
                break;
            case "10":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new AcknowledgmentEngine()).streamToXML(true);
                }else{
                    (new AcknowledgmentEngine()).streamToXML(false); 
                }
                break;
            case "11":
                if(validateValue!=null && validateValue.equals("yes")){
                    (new ContractRSOEngine()).streamToXML(true);
                }else{
                    (new ContractRSOEngine()).streamToXML(false); 
                }
                break;
        }      
    }  
}
