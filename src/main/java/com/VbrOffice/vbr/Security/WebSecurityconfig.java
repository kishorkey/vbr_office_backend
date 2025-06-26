package com.VbrOffice.vbr.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityconfig implements WebMvcConfigurer {
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // or "https://your-frontend.netlify.app"
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
    
   
}
