package com.VbrOffice.vbr.Util;

import org.springframework.stereotype.Component;
import com.VbrOffice.vbr.Entity.ClientDTO;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClientDataInitializer {

    private final ClientProducer clientProducer;

    public ClientDataInitializer(ClientProducer clientProducer) {
        this.clientProducer = clientProducer;
    }

   
    public void generateAndSendClients() {
        List<SubTypeMapping> mappingList = buildSubTypeMapping();

        int totalClients = 90;
        int partitions = 3;

        for (int i = 0; i < totalClients; i++) {
            SubTypeMapping mapping = mappingList.get(i % mappingList.size());

            ClientDTO clientDTO = new ClientDTO(
                    "Client_" + (i + 1),
                    "90000000" + String.format("%02d", i + 1),
                    mapping.getCategoryId(),
                    mapping.getSubTypeId()
            );

            int partition = i % partitions; // distribute equally
            clientProducer.sendClientDataToPartition(clientDTO, partition);
        }
    }

    private List<SubTypeMapping> buildSubTypeMapping() {
        List<SubTypeMapping> list = new ArrayList<>();
        list.add(new SubTypeMapping(1L, 1L, "Property Disputes"));
        list.add(new SubTypeMapping(2L, 1L, "Contract Disputes"));
        list.add(new SubTypeMapping(3L, 1L, "Family Law"));
        list.add(new SubTypeMapping(4L, 1L, "Consumer Protection"));
        list.add(new SubTypeMapping(5L, 2L, "Theft"));
        list.add(new SubTypeMapping(6L, 2L, "Assault"));
        list.add(new SubTypeMapping(7L, 2L, "Fraud"));
        list.add(new SubTypeMapping(8L, 2L, "Murder"));
        list.add(new SubTypeMapping(9L, 3L, "Mergers & Acquisitions"));
        list.add(new SubTypeMapping(10L, 3L, "Intellectual Property"));
        list.add(new SubTypeMapping(11L, 3L, "Compliance"));
        list.add(new SubTypeMapping(12L, 4L, "Fundamental Rights"));
        list.add(new SubTypeMapping(13L, 4L, "Writ Petitions"));
        list.add(new SubTypeMapping(14L, 5L, "Wage Disputes"));
        list.add(new SubTypeMapping(15L, 5L, "Wrongful Termination"));
        list.add(new SubTypeMapping(16L, 6L, "Income Tax"));
        list.add(new SubTypeMapping(17L, 6L, "GST"));
        list.add(new SubTypeMapping(18L, 7L, "Pollution Control"));
        list.add(new SubTypeMapping(19L, 7L, "Forest Rights"));
        list.add(new SubTypeMapping(20L, 8L, "Cyber Law"));
        list.add(new SubTypeMapping(21L, 8L, "Education Law"));
        list.add(new SubTypeMapping(22L, 8L, "Banking Law"));
        return list;
    }

    private static class SubTypeMapping {
        private final Long subTypeId;
        private final Long categoryId;
        private final String name;

        public SubTypeMapping(Long subTypeId, Long categoryId, String name) {
            this.subTypeId = subTypeId;
            this.categoryId = categoryId;
            this.name = name;
        }

        public Long getSubTypeId() {
            return subTypeId;
        }

        public Long getCategoryId() {
            return categoryId;
        }

        public String getName() {
            return name;
        }
    }
}
