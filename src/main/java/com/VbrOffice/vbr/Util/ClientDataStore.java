package com.VbrOffice.vbr.Util;


import com.VbrOffice.vbr.Entity.ClientDTO;
import com.VbrOffice.vbr.Service.VbrOfficeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ClientDataStore {
	
//	@Autowired
//	private VbrOfficeService  testdemoservice;

    private final List<ClientDTO> clients = Collections.synchronizedList(new ArrayList<>());

    public void add(ClientDTO clientDTO) {
        clients.add(clientDTO);
    }

    public List<ClientDTO> getAll() {
    	
        return new ArrayList<>(clients);
    }
}
