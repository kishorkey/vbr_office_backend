package com.VbrOffice.vbr.Contoller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.VbrOffice.vbr.Dto.PagedResponse;
import com.VbrOffice.vbr.Entity.Client;
import com.VbrOffice.vbr.Entity.ClientDTO;
import com.VbrOffice.vbr.Entity.ClientWithFilesDTO;
import com.VbrOffice.vbr.Entity.FileData;
import com.VbrOffice.vbr.Entity.UserDetails;
import com.VbrOffice.vbr.Entity.UserEmailVerification;
import com.VbrOffice.vbr.Entity.UserRole;
import com.VbrOffice.vbr.Repository.UserDetailsRepo;
import com.VbrOffice.vbr.Security.EncryptionUtil;
import com.VbrOffice.vbr.Security.JwtUtil;
import com.VbrOffice.vbr.Service.VbrOfficeService;
import com.VbrOffice.vbr.Util.EmailOtpService;

@RestController
public class VbrOfficeController {
	
	@Autowired
	private VbrOfficeService  testdemoservice;
	
	@Autowired
	private EncryptionUtil decrypt;
	
	@Autowired
	private EmailOtpService emailOtpService;
	
	 @Autowired
	private JwtUtil jwtUtil;

	 
	
	
    @GetMapping(path = "/getUser") 
    public ResponseEntity<Object> getUser(@RequestParam String username) 
    { 
    	 UserDetails responseBody = testdemoservice.getUserDetails(username);
    	 responseBody.setPassword(decrypt.decrypt(responseBody.getPassword()));
    	 return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } 
    
    
    @PostMapping(path = "/createUser") 
    public ResponseEntity<Object> createUser(@RequestBody UserEmailVerification createUser) 
    { 
    	 testdemoservice.createUser(createUser);
    	 return new ResponseEntity<> (HttpStatus.OK);
    }
    
//    @PostMapping(path = "/verifyUser")
//    public ResponseEntity<Object> verifyUser(@RequestBody UserEmailVerification verifyUser,@RequestParam String otp)
//    { 
//    	 testdemoservice.verifyUser(verifyUser,otp);
//    	 return new ResponseEntity<> (HttpStatus.OK);
//    } 
    
    @PostMapping(path = "/verifyUser")
    public ResponseEntity<Boolean> verifyUser(@RequestBody UserEmailVerification verifyUser, @RequestParam String otp) {
        try {
            boolean isVerified = testdemoservice.verifyUser(verifyUser,otp);
            return new ResponseEntity<>(isVerified, HttpStatus.OK);
        } catch (Exception e) {
            // Optionally log the error
            e.printStackTrace(); // or use a logger
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(path = "/saveUser") 
    @CrossOrigin(origins = "http://192.168.0.2:3000")
    public ResponseEntity<Object> saveUser(@RequestBody UserDetails userDetails) 
    { 
    	 testdemoservice.saveUserDetails(userDetails);
    	 System.out.println(userDetails);
    	 return new ResponseEntity<> (HttpStatus.OK);
    } 
    
    @GetMapping(path = "/login")
    @CrossOrigin(origins = "http://192.168.0.2:3000")
    public ResponseEntity<String> login(@RequestParam String username , @RequestParam String password) 
    { 
    	try
    	{
    		String pass = decrypt.decrypt(password);
    		System.out.println(pass);
    		String valid =  testdemoservice.login(username, pass);
    		System.out.println(valid);
       	 return new ResponseEntity<> (valid,HttpStatus.OK);
    	}
    	catch (Exception e) {
    		String valid =  testdemoservice.login(username, password);
    		return new ResponseEntity<>("user Not Found", HttpStatus.NOT_FOUND);
		}
    
    } 
    
    @GetMapping(path = "/getUserroles")
    @CrossOrigin(origins = "http://192.168.0.2:3000")
    public ResponseEntity<Object> getUserRoles(@RequestParam String username) {
        try {
            UserRole responseBody = testdemoservice.getUserRoles(username);

            if (responseBody == null) {
                return new ResponseEntity<>("No role assigned for user", HttpStatus.NOT_FOUND);
            }

            // Extract username and role from the responseBody
            List<String> role = responseBody.getRoles(); // make sure this exists
            String user = responseBody.getUsername(); // make sure this exists

            // Generate JWT token
            System.out.println(responseBody);
            
            String token = jwtUtil.generateToken(user, responseBody.getRoles());
            
            System.out.println(token);

            // Build response
            Map<String, Object> response = new HashMap<>();
            response.put("username", user);
            response.put("role", role);
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("No role assigned for user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    
    
//    @GetMapping(path = "/getUserroles")
//    @CrossOrigin(origins = "http://192.168.0.2:3000")
//    public ResponseEntity<Object> getUserRoles(@RequestParam String username) {
//        try {
//            UserRole responseBody = testdemoservice.getUserRoles(username);
//
//            if (responseBody == null) {
//                return new ResponseEntity<>("No role assigned for user", HttpStatus.NOT_FOUND);
//            }
//
//            return new ResponseEntity<>(responseBody, HttpStatus.OK);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("No role assigned for user", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    
    
    @PostMapping(path = "/saveUserRole") 
    public ResponseEntity<Object> saveUserRole(@RequestBody UserRole userrole) 
    { 
    	 testdemoservice.saveUserRoles(userrole);
    	 return new ResponseEntity<> (HttpStatus.OK);
    } 
    
//    @GetMapping(path = "/runThread") 
//    public String runThread() 
//    { 
////    	MyThread t1 = new MyThread();
//    	ThreadImpl myThread = new MyThread1();
//    	myThread.start();
//        
//    	 return null;
//    }
    
    @GetMapping(path = "/getAllUserRoles") 
    @CrossOrigin(origins = "http://192.168.0.2:3000")
    public ResponseEntity<Object> getAllUserRoles() 
    { 
    	 List<UserRole> responseBody = testdemoservice.getAllUserRoles();
    	 return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } 
    
    @GetMapping(path = "/getExcel") 
    @CrossOrigin(origins = "http://192.168.0.2:3000")
	public ResponseEntity<Object> getExcel()
	{
		ByteArrayInputStream user = testdemoservice.getExcel();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type","application/vnd.ms-excel");
		headers.set("Content-disposition", "attachment;filename=test.xlsx");
		 return ResponseEntity.ok().headers(headers)
				 .body(new InputStreamResource(user));
		
	}
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileData fileData = new FileData();
            fileData.setFileName(file.getOriginalFilename());
            fileData.setFileType(file.getContentType());
            fileData.setData(file.getBytes());
            testdemoservice.save(fileData);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
    
    @GetMapping("/getFileById")
    public ResponseEntity<byte[]> getFile(@RequestParam Long id) {
        FileData fileData = testdemoservice.getFileDataById(id);
        
        if (fileData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileData.getFileType()));
        headers.setContentDispositionFormData("attachment", fileData.getFileName());

        return new ResponseEntity<>(fileData.getData(), headers, HttpStatus.OK);
    }
    
    @PostMapping(path = "/saveClientdata") 
    public ResponseEntity<Object> saveClientdata(@RequestPart(value = "data",required = false)  String data,
    		@RequestPart(value = "files",required = true)  List<MultipartFile> files) throws IOException
    { 
    	 testdemoservice.saveClientdata(data,files);
    	 return new ResponseEntity<> (HttpStatus.OK);
    } 
    
    @PostMapping(value = "/saveclientsDataWithCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveClient(
    		 @RequestPart("client") String clientDTO,
    	     @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
    	testdemoservice.createClientWithFiles(clientDTO, files);
        return ResponseEntity.ok("Client created");
    }
    
//    
//    @GetMapping("/searchClient")
//    public List<ClientWithFilesDTO> searchClients(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) String subType) {
//    	System.out.println(name);
//        return testdemoservice.searchClients(name, category, subType);
//    }


    
    @GetMapping("/searchClient")
    public ResponseEntity<PagedResponse<ClientWithFilesDTO>> searchClients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subtype,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ClientWithFilesDTO> clientPage = testdemoservice.searchClients(name, category, subtype, page, size);

        PagedResponse<ClientWithFilesDTO> response = new PagedResponse<>(
                clientPage.getContent(),
                clientPage.getNumber(),
                clientPage.getSize(),
                clientPage.getTotalElements(),
                clientPage.getTotalPages(),
                clientPage.isLast()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getClients") 
    public ResponseEntity<Object> getClients( @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) 
    { 
    	Page<Client> responseBody = testdemoservice.getClientsPage(page, size);
    	 
    	 return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
    

    
    @GetMapping(path = "/getClientById") 
    public ResponseEntity<Object> getClientById(@RequestParam long id) 
    { 
    	 Client responseBody = testdemoservice.getClientById(id);
    	 
    	 return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } 
    
   
    
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
    	emailOtpService.generateOtp(email);
        return ResponseEntity.ok("OTP sent to " + email);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = emailOtpService.verifyOtp(email, otp);
        return isValid ? ResponseEntity.ok("OTP verified") : ResponseEntity.badRequest().body("Invalid OTP");
    }
    
}
    


