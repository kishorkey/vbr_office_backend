package com.VbrOffice.vbr.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClientDTO {
    private String username;
    private long mobile;
    private Long categoryId;
    private Long subTypeId;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
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
    
	
}
