package com.VbrOffice.vbr.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.VbrOffice.vbr.Entity.FileData;


public interface FileDataRepository extends JpaRepository<FileData, Long> {
	
		
	@Query(nativeQuery = true, value = "SELECT * FROM public.file_data where client_id = :clintId")
	public List<FileData> getFilesByClintId(Long clintId);
	
}
