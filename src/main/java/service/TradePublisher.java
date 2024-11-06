package service;

import model.Trade;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class TradePublisher {
    Properties properties ;
    KafkaProducer<String, Trade> producer;
    public TradePublisher(){
        String bootstrapServers = "127.0.0.1:9092";
        properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
         producer = new KafkaProducer<>(properties);
    }
    void publishToExchange(Trade trade)
    {


        ProducerRecord<String, Trade> producerRecord = new ProducerRecord<>("exchange", trade);
        producer.send(producerRecord);
        producer.flush();
        producer.close();

    }
}
