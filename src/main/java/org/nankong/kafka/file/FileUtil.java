package org.nankong.kafka.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class FileUtil {
    public static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static Properties readProperties(String fileName){
        Properties properties = null;
        try{
         properties= new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream inStream = new FileInputStream(fileName);
        // 使用properties对象加载输入流
        properties.load(inStream);
        }catch (IOException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return properties;
    }
    public static void setJaasConf(String confName){
        logger.info(confName);
        System.setProperty("java.security.auth.login.config",confName);
    }

    public static void setKrb5Conf(String confName){
        logger.info(confName);
        System.setProperty("java.security.krb5.conf",confName);

    }
}
