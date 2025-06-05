package com.VbrOffice.vbr.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name  = "userrole")
public class UserRole {
		
		@Id
		@Column(name = "username")
		private String username;
		
		
		@Column(name = "roles")
		private List<String> roles;


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public List<String> getRoles() {
			return roles;
		}


		public void setRoles(List<String> roles) {
			this.roles = roles;
		}


		@Override
		public String toString() {
			return "UserTabs [username=" + username + ", roles=" + roles + "]";
		}
		
}
