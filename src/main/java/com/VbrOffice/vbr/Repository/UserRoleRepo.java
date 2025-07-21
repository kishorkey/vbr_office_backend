package com.VbrOffice.vbr.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.VbrOffice.vbr.Entity.UserRole;

public interface UserRoleRepo  extends JpaRepository<UserRole, String>{
	
	@Query(nativeQuery = true, value = "select * from public.UserRole where username = :username")
	 Optional<UserRole> getuserRolesByUsername(String username);

	@Query(nativeQuery = true, value = "select * from public.UserRole")
	public List<UserRole> getAllUserRoles();
	
}
