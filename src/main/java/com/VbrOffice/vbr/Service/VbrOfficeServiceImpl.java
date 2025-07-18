package com.VbrOffice.vbr.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.VbrOffice.vbr.Entity.Client;
import com.VbrOffice.vbr.Entity.FileData;
import com.VbrOffice.vbr.Entity.UserDetails;
import com.VbrOffice.vbr.Entity.UserEmailVerification;
import com.VbrOffice.vbr.Entity.UserRole;
import com.VbrOffice.vbr.Repository.ClientRepository;
import com.VbrOffice.vbr.Repository.FileDataRepository;
import com.VbrOffice.vbr.Repository.UserDetailsRepo;
import com.VbrOffice.vbr.Repository.UserRoleRepo;
import com.VbrOffice.vbr.Repository.userEmailVerificationRepository;
import com.VbrOffice.vbr.Security.EncryptionUtil;
import com.VbrOffice.vbr.Util.EmailOtpService;
import com.VbrOffice.vbr.Util.ExcelUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VbrOfficeServiceImpl implements VbrOfficeService {

	@Autowired
	private UserDetailsRepo userdetailsrepo;

	@Autowired
	private UserRoleRepo userrolerepo;

	@Autowired
	private FileDataRepository fileDataRepository;

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private userEmailVerificationRepository  emailverification;
	 
	 @Autowired
	private EmailOtpService emailOtpService;

	@Autowired
	private EncryptionUtil encryptionUtil;

	@Override
	public UserDetails getUserDetails(String username) {
		UserDetails user = userdetailsrepo.getuserDetailsByUser(username);
//		user.setPassword(encryptionUtil.decrypt(user.getPassword()));
		return user;
	}

	@Override
	public UserDetails saveUserDetails(UserDetails userdetails) {
		
		userdetails.setPassword(encryptionUtil.encrypt(userdetails.getPassword()));
		return userdetailsrepo.save(userdetails);
	}
	
	@Override
	public String createUser(UserEmailVerification createUser) {
		 emailOtpService.generateOtp(createUser.getEmail());
		 return "user created";
	}
	
	@Override
	public boolean verifyUser(UserEmailVerification verifyUser,String otp) {
		 verifyUser.setStatus("T");
		 emailverification.save(verifyUser);
		 boolean isValid = emailOtpService.verifyOtp(verifyUser.getEmail(), otp);
	     return isValid ? true: false;
	}

	@Override
	public String login(String username, String password) {
		String valieduser;
		UserDetails user = userdetailsrepo.getuserDetailsByUser(username);
		user.setPassword(encryptionUtil.decrypt(user.getPassword()));
		if (password.equalsIgnoreCase(user.getPassword())) {
			valieduser = "Login Successfull";
		} else {
			valieduser = "login Failed";
		}
		return valieduser;
	}

	@Override
	public UserRole getUserRoles(String username) {
		UserRole user = userrolerepo.getuserRolesByUsername(username);
//		set custome role
//		setRoles(user);
		return user;

	}

	@Override
	public List<UserRole> getAllUserRoles() {
		List<UserRole> user = userrolerepo.getAllUserRoles();

		return user;

	}

//	
//	public void setRoles(UserRole user )
//	{
//		 List<String> newList = new ArrayList<>();
//		 
//	        // Add elements to the ArrayList
//	        newList.add("Admin");
//	        newList.add("Viewer");
//		user.setRoles(newList);
//		
//	}

	public ByteArrayInputStream getExcel() {

		List<UserRole> user = userrolerepo.getAllUserRoles();

		return ExcelUtils.generateExcelFile(user);
	}

	public void save(FileData fileData) {
		System.out.println("this is data " + fileData);

		fileDataRepository.save(fileData);
	}

	public FileData getFileDataById(Long id) {
		return fileDataRepository.findById(id).orElse(null);
	}

	@Override
	public UserRole saveUserRoles(UserRole usertbs) {
		return userrolerepo.save(usertbs);
	}

	@Override
	public Client saveClientdata(String data, List<MultipartFile> files) throws IOException {
		Client client = new Client();

		List<FileData> filesList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			// Convert JSON to Client
			client = mapper.readValue(data, Client.class);
			if (files != null) {
				for (MultipartFile file : files) {
					FileData fileData = new FileData();
					fileData.setFileName(file.getOriginalFilename());
					fileData.setFileType(file.getContentType());
					fileData.setData(file.getBytes());
					// ✅ Set the client reference inside each FileData
					fileData.setClient(client);
					filesList.add(fileData);
				}
			}

			// ✅ Set the files on the client (bidirectional sync)
			client.setFiles(filesList);

			// ✅ Now save client with all files linked properly
			clientRepository.save(client);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return client;

	}
	
	@Override
	public Client getClientById(long id) {
		
		return clientRepository.getById(id);
	}

}
