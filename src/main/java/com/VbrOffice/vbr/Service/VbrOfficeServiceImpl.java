package com.VbrOffice.vbr.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.VbrOffice.vbr.Entity.CaseCategory;
import com.VbrOffice.vbr.Entity.CaseSubType;
import com.VbrOffice.vbr.Entity.Client;
import com.VbrOffice.vbr.Entity.ClientDTO;
import com.VbrOffice.vbr.Entity.ClientWithFilesDTO;
import com.VbrOffice.vbr.Entity.FileData;
import com.VbrOffice.vbr.Entity.UserDetails;
import com.VbrOffice.vbr.Entity.UserEmailVerification;
import com.VbrOffice.vbr.Entity.UserRole;
import com.VbrOffice.vbr.Repository.CaseCategoryRepository;
import com.VbrOffice.vbr.Repository.CaseSubTypeRepository;
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
	 private CaseCategoryRepository categoryRepo;
	
	 @Autowired
	 private CaseSubTypeRepository subTypeRepo;
	
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
			System.out.println("This is updated from backend"+ encryptionUtil.decrypt(user.getPassword()));
			valieduser = "login Failed";
		}
		return valieduser;
	}

//	@Override
//	public UserRole getUserRoles(String username) {
//		UserRole user = userrolerepo.getuserRolesByUsername(username);
////		set custome role
////		setRoles(user);
//		return user;
//
//	}
	
	 @Override
	    public UserRole getUserRoles(String username) {
	        try {
	            Optional<UserRole> userOpt = userrolerepo.getuserRolesByUsername(username);

	            if (userOpt.isPresent()) {
	                UserRole user = userOpt.get();
	                // Optional: set custom roles
	                // setRoles(user);
	                return user;
	            } else {
	                return null; // No role assigned
	            }

	        } catch (DataAccessException ex) {
	            // Handles all Spring DB-related errors
	            ex.printStackTrace(); // For production, use a logger instead
	            return null;
	        } catch (Exception ex) {
	            // Fallback for any unexpected exceptions
	            ex.printStackTrace();
	            return null;
	        }
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
	public void createClientWithFiles(String dto, List<MultipartFile> files) throws IOException {
	    Client client = new Client();
	    ClientDTO clientdto = new ClientDTO();
	    ObjectMapper mapper = new ObjectMapper();
	    clientdto = mapper.readValue(dto, ClientDTO.class);
	    
	    client.setUsername(clientdto.getUsername());
	    client.setNumber(clientdto.getMobile());

	    CaseCategory category = categoryRepo.findById(clientdto.getCategoryId())
	        .orElseThrow(() -> new RuntimeException("Invalid category"));

	    CaseSubType subType = subTypeRepo.findById(clientdto.getSubTypeId())
	        .orElseThrow(() -> new RuntimeException("Invalid sub type"));

	    client.setCategory(category);
	    client.setSubType(subType);

	    List<FileData> fileEntities = new ArrayList<>();
	    for (MultipartFile file : files) {
	        FileData data = new FileData();
	        data.setFileName(file.getOriginalFilename());
	        data.setFileType(file.getContentType());
	        data.setData(file.getBytes());
	        data.setClient(client);
	        fileEntities.add(data);
	    }

	    client.setFiles(fileEntities);
	    clientRepository.save(client);
	}



	
//	@Override
//	public List<ClientWithFilesDTO> searchClients(String name, String category, String subtype) {
//	    List<Object[]> results = clientRepository.searchClientsRaw(name, category, subtype);
//	    System.out.println("this is result :- " + results);
//
//	    return results.stream().map(row -> {
//	        ClientWithFilesDTO dto = new ClientWithFilesDTO();
//	        dto.setUserId(((Number) row[0]).longValue());
//	        dto.setUsername((String) row[1]);
//	        dto.setNumber(((Number) row[2]).longValue());
//	        dto.setCategoryName((String) row[3]);
//	        dto.setSubtypeName((String) row[4]);
//
//	        List<FileData> files = fileDataRepository.getFilesByClintId(dto.getUserId());
//	        List<FileData> fileDTOs = files.stream().map(file -> {
//	        	FileData f = new FileData();
//	            f.setId(file.getId());
//	            f.setFileName(file.getFileName());
//	            f.setFileType(file.getFileType());
//	            f.setData(file.getData());
//	            return f;
//	        }).toList();
//
//	        dto.setFiles(fileDTOs);
//	        return dto;
//	    }).toList();
//	}
	
	@Override
	public Page<Client> getClientsPage( int page, int size) {
		 Pageable pageable = PageRequest.of(page, size);
		
		return clientRepository.getClients(pageable);
	}

	
	
	@Override
	public Page<ClientWithFilesDTO> searchClients(String name, String category, String subtype, int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<Object[]> results = clientRepository.searchClientsPaged(name, category, subtype, pageable);

	    List<ClientWithFilesDTO> dtos = results.getContent().stream().map(row -> {
	        ClientWithFilesDTO dto = new ClientWithFilesDTO();
	        dto.setUserId(((Number) row[0]).longValue());
	        dto.setUsername((String) row[1]);
	        dto.setNumber(((String) row[2]));
//	        if interger is generated insteard of string we need to cast
//	        dto.setCategoryName(row[3] != null ? row[3].toString() : null);
//	        dto.setSubtypeName(row[4] != null ? row[4].toString() : null);
	        dto.setCategoryName((String) row[3]);
	        dto.setSubtypeName((String) row[4]);

	        List<FileData> files = fileDataRepository.getFilesByClintId(dto.getUserId());
	        List<FileData> fileDTOs = files.stream().map(file -> {
	            FileData f = new FileData();
	            f.setId(file.getId());
	            f.setFileName(file.getFileName());
	            f.setFileType(file.getFileType());
	            f.setData(file.getData());
	            return f;
	        }).toList();

	        dto.setFiles(fileDTOs);
	        return dto;
	    }).toList();

	    return new PageImpl<>(dtos, pageable, results.getTotalElements());
	}

	

	
	
	@Override
	public Client getClientById(long id) {
		
		return clientRepository.getById(id);
	}

	@Override
	public List<Client> getClients() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
