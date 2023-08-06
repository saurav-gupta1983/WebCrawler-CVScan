package Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class ConfigUtil {

    //object of properties to persist through program
    private Properties propsPersist;
    private static Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);
    //static method to get config directly
    public static String getConfig(String configKey){

            LOG.info("Loading config property.. Config.properties");
        try{    InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream("Config.properties");
            Properties props=new Properties();
            props.load(is);
            LOG.info("Looking for property "+configKey);
            return props.getProperty(configKey);
        } catch (FileNotFoundException e) {
            LOG.error("config properties file not found");
            return null;
        } catch (IOException e) {
            LOG.error("");
            e.printStackTrace();
            return null;
        }

    }

    //static method overloaded to get config using property file and config key
    public static String getConfig(String propertyFile,String configKey){

        LOG.info("Loading config property.. "+propertyFile);
        try{
            InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(propertyFile+".properties");
            Properties props=new Properties();
            props.load(is);
            LOG.info("Looking for property "+configKey);
            return props.getProperty(configKey);
        } catch (FileNotFoundException e) {
            LOG.error(propertyFile+" not found");
            return null;
        } catch (IOException e) {
            LOG.error("");
            e.printStackTrace();
            return null;
        }

    }



    public ConfigUtil(){
        propsPersist=new Properties();
    }

    //returns the object with property object and loaded properties from prop file
    public ConfigUtil load(){
        try{
            if(this.propsPersist.equals(null)){
                this.propsPersist=new Properties();
            }
            propsPersist.load(ConfigUtil.class.getClassLoader().getResourceAsStream("Config.properties"));
            return this;
        } catch (IOException e){
            LOG.error("Could not load the config properties");
            return null;
        }
    }

    //overloaded method to load prop file with another name
    public ConfigUtil load(String propertyFileName){
        try{
            if(this.propsPersist.equals(null)){
                this.propsPersist=new Properties();
            }
            propsPersist.load(ConfigUtil.class.getClassLoader().getResourceAsStream(propertyFileName+".properties"));
            return this;
        } catch (IOException e){
            LOG.error("Could not load the config properties");
            return null;
        }
    }

    public Set<Object> getKeySet(){
        if(this.propsPersist==null){
            this.load();
        }
        return this.propsPersist.keySet();
    }

    public String getProperty(String configKey){
        if(this.propsPersist.equals(null)){
            this.load();
        }
        return this.propsPersist.getProperty(configKey);
    }

}
