package com.VbrOffice.vbr.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.VbrOffice.vbr.Entity.FileData;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


public interface FileDataRepository extends JpaRepository<FileData, Long> {
	
		
	@Query(nativeQuery = true, value = "SELECT * FROM public.file_data where client_id = :clintId")
	public List<FileData> getFilesByClintId(Long clintId);
	


    @Modifying
    @Transactional
    @Query( nativeQuery = true, value = "SET my.app_user = :deletedBy" )
    void setAppUser(String deletedBy);

    @Modifying
    @Transactional
    @Query(nativeQuery = true ,value = "DELETE FROM public.file_data WHERE id = :id")
    void softDeleteFileById(int id);
    
	
}
