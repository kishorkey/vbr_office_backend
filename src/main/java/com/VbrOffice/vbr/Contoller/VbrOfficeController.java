package com.VbrOffice.vbr.Contoller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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

import com.VbrOffice.vbr.Entity.Client;
import com.VbrOffice.vbr.Entity.FileData;
import com.VbrOffice.vbr.Entity.UserDetails;
import com.VbrOffice.vbr.Entity.UserRole;
import com.VbrOffice.vbr.Repository.UserDetailsRepo;
import com.VbrOffice.vbr.Security.EncryptionUtil;
import com.VbrOffice.vbr.Service.VbrOfficeService;

@RestController
public class VbrOfficeController {
	
	@Autowired
	private VbrOfficeService  testdemoservice;
	
	@Autowired
	private EncryptionUtil decrypt;
	
	
    @GetMapping(path = "/getUser") 
    public ResponseEntity<Object> getUser(@RequestParam String username) 
    { 
    	 UserDetails responseBody = testdemoservice.getUserDetails(username);
    	 responseBody.setPassword(decrypt.decrypt(responseBody.getPassword()));
    	 return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } 
    
    @PostMapping(path = "/saveUser") 
    @CrossOrigin(origins = "http://192.168.0.2:3000")
    public ResponseEntity<Object> saveUser(@RequestBody UserDetails userDetails) 
    { 
    	 testdemoservice.saveUserDetails(userDetails);
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
       	 return new ResponseEntity<> (valid,HttpStatus.OK);
    	}
    	catch (Exception e) {
    		String valid =  testdemoservice.login(username, password);
          	 return new ResponseEntity<> (valid,HttpStatus.OK);
		}
    
    } 
    
    
    @GetMapping(path = "/getUserroles") 
    @CrossOrigin(origins = "http://192.168.0.2:3000")
    public ResponseEntity<Object> getUserRoles(@RequestParam String username) 
    { 
    	 UserRole responseBody = testdemoservice.getUserRoles(username);
    	 return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } 
    
    
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
    
    @GetMapping(path = "/getClientById") 
    public ResponseEntity<Object> getClientById(@RequestParam long id) 
    { 
    	 Client responseBody = testdemoservice.getClientById(id);
    	 
    	 return new ResponseEntity<>(responseBody, HttpStatus.OK);
    } 
    
    
}
    


