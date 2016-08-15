package com.zyifly.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhaoyifei on 16/8/12.
 */
public class SystemParameter {
    private static final Logger logger = LoggerFactory.getLogger(SystemParameter.class);

    public Properties loadConfiguration(String fileName){
        String ConfigurationDir = System.getenv("CONFIG_DIR");
        if ("".equalsIgnoreCase(ConfigurationDir.trim())){
            logger.error("CONFIG_DIR hasn't been set correct!");
            return null;
        }
        Properties propertie = new Properties();
        try {
            if (ConfigurationDir.indexOf(ConfigurationDir.length()-1) != '/'){
                ConfigurationDir = ConfigurationDir + "/";
            }
            InputStream inputFile = new FileInputStream(ConfigurationDir+fileName);
            propertie.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex){
            logger.error("Read Properties File --->Failure! Reason: File Path Error or File not exist! Name:" + ConfigurationDir+"/"+fileName);
            ex.printStackTrace();
        } catch (IOException ex){
            logger.error("Load Configuration File--->Failure! Name:" + ConfigurationDir+"/"+fileName);
            ex.printStackTrace();
        }
        return propertie;
    }
}
