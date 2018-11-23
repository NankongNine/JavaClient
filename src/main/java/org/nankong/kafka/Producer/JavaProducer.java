package org.nankong.kafka.Producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.nankong.kafka.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class JavaProducer {
    public static Logger logger = LoggerFactory.getLogger(JavaProducer.class);
    public static void main(String args[]){
//        String propName = "producer-first.properties";
        String propName =args[0];
        String confName = args[1];
        String topic = args[2];
        Properties props = FileUtil.readProperties(propName);
        if(propName==null) {
            logger.error("配置文件读取失败");
            return;
        }
        FileUtil.setJaasConf(confName);
        sendMsg(props,topic);
    }

    public static void sendMsg(Properties props,String topic){
        Producer<String, String> producer = new KafkaProducer<>(props);
        boolean flag = true;
        Scanner input = new Scanner(System.in);
        do{
            System.out.println("请输入key和value的格式");
            producer.send(new ProducerRecord<String, String>(topic, input.nextLine(), input.nextLine()));
        }while(flag);
        producer.close();
    }
}
