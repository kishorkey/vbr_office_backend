package com.VbrOffice.vbr.Entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client {
	
	@Id
	@Column(name = "client_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_Id;
	
	@Column(name = "name")
	private String username;
	
	@Column(name = "mobile")
	private long number;
	

	 @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	 @JsonManagedReference
     private List<FileData> files;


	public long getUser_Id() {
		return user_Id;
	}


	public void setUser_Id(long user_Id) {
		this.user_Id = user_Id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public long getNumber() {
		return number;
	}


	public void setNumber(long number) {
		this.number = number;
	}


	public List<FileData> getFiles() {
		return files;
	}


	public void setFiles(List<FileData> files) {
		this.files = files;
	}


	@Override
	public String toString() {
		return "Client [user_Id=" + user_Id + ", username=" + username + ", number=" + number + ", files=" + files
				+ "]";
	}
	

}
