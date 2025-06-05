package com.VbrOffice.vbr.Entity;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

	@Entity
	@Table(name = "file_data")
	public class FileData {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "filename")
	    private String fileName;
	    
	    @Column(name = "filetype")
	    private String fileType;
	    
	    @Column(name = "data")
	    private byte[] data;
	    
	    @ManyToOne(optional = false)
	    @JoinColumn(name="client_id", nullable=false)
	    @JsonBackReference
	    private Client client;
	    
	    
	    // Getters and Setters    
	    
		public Long getId() {
			return id;
		}
		public Client getClient() {
			return client;
		}
		public void setClient(Client client) {
			this.client = client;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFileType() {
			return fileType;
		}
		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
		public byte[] getData() {
			return data;
		}
		public void setData(byte[] data) {
			this.data = data;
		}
		@Override
		public String toString() {
			return "FileData [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", data="
					+ Arrays.toString(data) + ", client=" + client + "]";
		}

	 
	    
	}