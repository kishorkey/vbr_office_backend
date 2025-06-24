package com.VbrOffice.vbr.Entity;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name  = "useremailverification")
public class UserEmailVerification {
	
	@Id
	@Column(name = "username")
	private String username;
	
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "status")
	private String status;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserEmailVerification [username=" + username + ", email=" + email + ", status=" + status + "]";
	}
		

}
