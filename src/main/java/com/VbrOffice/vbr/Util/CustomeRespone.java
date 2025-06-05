package com.VbrOffice.vbr.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomeRespone {
	public static ResponseEntity<Object> generateResponse(String message,HttpStatus status,Object responseObject) {
		
		        Map<String ,Object> map = new HashMap<String, Object>();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				map.put("message", message);
				map.put("status", status.value());
				map.put("result", responseObject);
				map.put("timestamp", formatter.format(date));
				return new ResponseEntity<Object>(map,status);
		
	}

}
