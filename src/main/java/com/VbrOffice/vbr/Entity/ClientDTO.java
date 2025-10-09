package com.VbrOffice.vbr.Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClientDTO implements Serializable {
	
    private static final long serialVersionUID = 1L; 

    private String username;
    private String mobile;
    private Long categoryId;
    private Long subTypeId;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getSubTypeId() {
		return subTypeId;
	}
	public void setSubTypeId(Long subTypeId) {
		this.subTypeId = subTypeId;
	}
    

	  public ClientDTO(String username, String mobile, Long categoryId, Long subTypeId) {
	        this.username = username;
	        this.mobile = mobile;
	        this.categoryId = categoryId;
	        this.subTypeId = subTypeId;
	    }
	public ClientDTO() {
		// TODO Auto-generated constructor stub
	}

}
