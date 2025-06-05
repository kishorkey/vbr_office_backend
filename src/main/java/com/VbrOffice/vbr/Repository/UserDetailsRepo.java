package com.VbrOffice.vbr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.VbrOffice.vbr.Entity.UserDetails;

public interface UserDetailsRepo extends JpaRepository<UserDetails, String> {
	
	
	@Query(nativeQuery = true, value = "select * from public.userdetails where username = :username")
	public UserDetails getuserDetailsByUser(String username);
}
