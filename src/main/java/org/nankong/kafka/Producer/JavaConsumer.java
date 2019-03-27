package org.nankong.kafka.Producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.nankong.kafka.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

public class JavaConsumer {
    public static Logger logger = LoggerFactory.getLogger(JavaConsumer.class);
    public static void main(String args[]){
        String krb5conf = "E:/krb5conf/krb5.conf";
        String propName ="E:/krb5conf/kafka/consumer-first-krb5.properties";
        String confName = "E:/krb5conf/kafka/jaas.conf";
        String topics = args[2];
        Properties props = FileUtil.readProperties(propName);
        if(propName==null) {
            logger.error("配置文件读取失败");
            return;
        }
        FileUtil.setJaasConf(confName);
        FileUtil.setKrb5Conf(krb5conf);
        fetchMsg(props,topics);
    }
    public static void fetchMsg(Properties props,String topics){
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topics));
        logger.info("start consumering");
        while (true) {
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record : records) {
//              在这里进行插入数据库操作，数据在valus里的json格式
                System.out.println("分区："+record.partition()+" offset:"+record.offset()+",key:"+record.key()+",value:"+record.value());
//              同步提交
                consumer.commitSync();
            }
        }
    }
}
