package Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigController {

    public static Map<String,String> configurations;
    public ConfigController(){
        if(configurations==null)
            configurations=new HashMap<String, String>();
    }

    public static void loadConfig(){
        configurations=new HashMap<String, String>();
        ConfigUtil configs=new ConfigUtil();
        configs.load("Config");
        Iterator it= configs.getKeySet().iterator();
        while(it.hasNext()) {
            String key= (String) it.next();
            configurations.put(key,configs.getProperty(key));
        }
    }

}
