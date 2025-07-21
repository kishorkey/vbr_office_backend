package com.VbrOffice.vbr.Entity;

import java.util.List;

public class ClientWithFilesDTO {

	    private Long userId;
	    private String username;
	    private Long number;
	    private String categoryName;
	    private String subtypeName;
	    private List<FileData> files;
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public Long getNumber() {
			return number;
		}
		public void setNumber(Long number) {
			this.number = number;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		public String getSubtypeName() {
			return subtypeName;
		}
		public void setSubtypeName(String subtypeName) {
			this.subtypeName = subtypeName;
		}
		public List<FileData> getFiles() {
			return files;
		}
		public void setFiles(List<FileData> files) {
			this.files = files;
		}

	    
	    
	    
}
