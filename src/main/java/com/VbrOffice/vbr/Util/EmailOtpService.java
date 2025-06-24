package com.VbrOffice.vbr.Util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.VbrOffice.vbr.Entity.UserEmailVerification;
import com.VbrOffice.vbr.Repository.userEmailVerificationRepository;


	@Service
	public class EmailOtpService {
		
		 @Autowired
		 private JavaMailSender mailSender;
		 
		 @Autowired
			private userEmailVerificationRepository  emailverification;

		    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

		    public String generateOtp(String email) {
		        String otp = String.format("%06d", new Random().nextInt(999999));
		        otpStorage.put(email, otp);

		        // Send email
		        SimpleMailMessage message = new SimpleMailMessage();
		        message.setTo(email);
		        message.setSubject("Your OTP Code");
		        message.setText("Your OTP for VBR Associate login is: " + otp);
		        mailSender.send(message);

		        return otp;
		    }

		    public boolean verifyOtp(String email, String otp) {
		        String storedOtp = otpStorage.get(email);
		        UserEmailVerification verifyMail = new UserEmailVerification();
		        if (storedOtp != null && storedOtp.equals(otp)) {
		            otpStorage.remove(email);
		           verifyMail.setEmail(email);
		           verifyMail.setUsername("testUser");
		           verifyMail.setStatus("T");
		            emailverification.save(verifyMail);
		            return true;
		        }
		        verifyMail.setEmail(email);
		        verifyMail.setUsername("testUser");
		        verifyMail.setStatus("F");
		        return false;
		    }
		}