package com.VbrOffice.vbr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VbrOffice.vbr.Entity.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {

}
