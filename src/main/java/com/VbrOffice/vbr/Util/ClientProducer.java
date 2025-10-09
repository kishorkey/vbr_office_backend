package com.VbrOffice.vbr.Util;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.VbrOffice.vbr.Entity.ClientDTO;

@Service
public class ClientProducer {

    private final KafkaTemplate<String, ClientDTO> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public ClientProducer(KafkaTemplate<String, ClientDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendClientDataToPartition(ClientDTO clientDTO, int partition) {
        CompletableFuture<SendResult<String, ClientDTO>> future =
            kafkaTemplate.send(topicName, partition, clientDTO.getUsername(), clientDTO);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("❌ Failed to send message: " + ex.getMessage());
            } else {
                System.out.println("✅ Sent to partition: " +
                    result.getRecordMetadata().partition() +
                    ", offset: " + result.getRecordMetadata().offset() +
                    ", client: " + clientDTO);
            }
        });
    }

}
