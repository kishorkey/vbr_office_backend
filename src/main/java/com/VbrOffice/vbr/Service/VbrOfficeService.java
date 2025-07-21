package com.VbrOffice.vbr.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.VbrOffice.vbr.Entity.Client;
import com.VbrOffice.vbr.Entity.ClientDTO;
import com.VbrOffice.vbr.Entity.ClientWithFilesDTO;
import com.VbrOffice.vbr.Entity.FileData;
import com.VbrOffice.vbr.Entity.UserDetails;
import com.VbrOffice.vbr.Entity.UserEmailVerification;
import com.VbrOffice.vbr.Entity.UserRole;

public interface VbrOfficeService {

	public UserDetails getUserDetails(String username);

	public UserDetails saveUserDetails(UserDetails userdetails);
	
	public String createUser(UserEmailVerification createUser);
	
	public boolean verifyUser(UserEmailVerification verifyUser, String otp);

	public String login(String username, String password);

	public UserRole getUserRoles(String username);

	public UserRole saveUserRoles(UserRole usertbs);
	
	public ByteArrayInputStream getExcel();

	public List<UserRole> getAllUserRoles();
	
	public void save(FileData fileData);
	
	public FileData getFileDataById(Long id);

	public Client saveClientdata(String data, List<MultipartFile> files) throws IOException;

	public Client getClientById(long id);

	List<Client> getClients();

	void createClientWithFiles(String dto, List<MultipartFile> files) throws IOException;

//	List<ClientWithFilesDTO> searchClients(String name, String category, String subType);

	Page<ClientWithFilesDTO> searchClients(String name, String category, String subtype, int page, int size);


	Page<Client> getClientsPage(int page, int size);

}
