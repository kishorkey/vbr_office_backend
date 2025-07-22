package com.VbrOffice.vbr.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.VbrOffice.vbr.Entity.Client;
import com.VbrOffice.vbr.Entity.UserDetails;


public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query( value = "select * FROM public.client_details",countQuery = "SELECT COUNT(*) FROM client_details c", nativeQuery = true)
	public Page<Client> getClients(Pageable pageable);
	

	
	@Query(value = "SELECT * FROM client_details WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
    List<Client> searchByName(String name);

    @Query(value = "SELECT * FROM client_details WHERE category_id = :categoryId", nativeQuery = true)
    List<Client> findByCategoryId(Long categoryId);

    @Query(value = "SELECT * FROM client_details WHERE sub_type_id = :subTypeId", nativeQuery = true)
    List<Client> findBySubTypeId(Long subTypeId);
    
//    @Query(value = """
//    	    SELECT DISTINCT 
//    	        c.client_id AS userId,
//    	        c.name AS username,
//    	        c.mobile AS number,
//    	        cat.name AS categoryName,
//    	        sub.name AS subtypeName
//    	    FROM client_details c
//    	    LEFT JOIN case_category cat ON c.category_id = cat.id
//    	    LEFT JOIN case_subtype sub ON c.subtype_id = sub.id
//    	    WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
//    	AND (COALESCE(:category, '') = '' OR LOWER(cat.name) = LOWER(:category))
//        AND (COALESCE(:subtype, '') = '' OR LOWER(sub.name) = LOWER(:subtype))
//    	    """, nativeQuery = true)
//    	List<Object[]> searchClientsRaw(@Param("name") String name,
//    	                                @Param("category") String category,
//    	                                @Param("subtype") String subtype);
    	
    	
    	@Query(value = """
    		    SELECT DISTINCT  
    		    c.client_id AS userId,
    	        c.name AS username,
    	        c.mobile AS number,
    	        cat.name AS categoryName,
    	        sub.name AS subtypeName
    		    FROM client_details c
    		    LEFT JOIN case_category cat ON c.category_id = cat.id
    		    LEFT JOIN case_subtype sub ON c.subtype_id = sub.id
    		    WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
    		     	AND (COALESCE(:category, '') = '' OR LOWER(cat.name) = LOWER(:category))
                    AND (COALESCE(:subtype, '') = '' OR LOWER(sub.name) = LOWER(:subtype))
    		    """,
    		    countQuery = """
    		    SELECT COUNT(*)
    		    FROM client_details c
    		    LEFT JOIN case_category cat ON c.category_id = cat.id
    		    LEFT JOIN case_subtype sub ON c.subtype_id = sub.id
    		    WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
    		       	AND (COALESCE(:category, '') = '' OR LOWER(cat.name) = LOWER(:category))
                    AND (COALESCE(:subtype, '') = '' OR LOWER(sub.name) = LOWER(:subtype))
    		    """,
    		    nativeQuery = true)
    		Page<Object[]> searchClientsPaged(
    		    @Param("name") String name,
    		    @Param("category") String category,
    		    @Param("subtype") String subtype,
    		    Pageable pageable
    		);




}
