package com.VbrOffice.vbr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VbrOffice.vbr.Entity.UserEmailVerification;

public interface userEmailVerificationRepository extends JpaRepository<UserEmailVerification, String> {

}
