package com.VbrOffice.vbr.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.VbrOffice.vbr.Entity.FileData;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
	
}
