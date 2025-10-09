package com.VbrOffice.vbr.Util;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.VbrOffice.vbr.Entity.ClientDTO;

@Service
public class ClientConsumer {

    private final ClientDataStore clientDataStore;

    public ClientConsumer(ClientDataStore clientDataStore) {
        this.clientDataStore = clientDataStore;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "client-group")
    public void consume(ClientDTO clientDTO) {
        System.out.println("📩 Received client data from Kafka: " + clientDTO);
        clientDataStore.add(clientDTO); // save message
    }
}
