package com.aten.mn.line.bot;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aten.mn.line.charts.Data;

@RestController
public class HomeController {

	@Autowired
	ServletContext context; 
	
    @RequestMapping("/")
    public String home(){
        return "";
    }
    
    @RequestMapping(value = "/img/GOSS", method = RequestMethod.GET)
    public void getImageGOSS(HttpServletResponse response) throws IOException {
//        InputStream in = new ClassPathResource("static/WEB-INF/img/GOSS.png").getInputStream();
//        response.setContentType(MediaType.IMAGE_PNG_VALUE);
    	InputStream in = new ByteArrayInputStream(Data.goss);
        IOUtils.copy(in, response.getOutputStream());
    }
    
    @RequestMapping(value = "/img/CDM", method = RequestMethod.GET)
    public void getImageCDM(HttpServletResponse response) throws IOException {
//        InputStream in = new ClassPathResource("static/WEB-INF/img/CDM.png").getInputStream();
//        response.setContentType(MediaType.IMAGE_PNG_VALUE);
    	InputStream in = new ByteArrayInputStream(Data.cdm);
        IOUtils.copy(in, response.getOutputStream());
    }
    
    @RequestMapping(value = "/img/VYI", method = RequestMethod.GET)
    public void getImageVYI(HttpServletResponse response) throws IOException {
//        InputStream in = new ClassPathResource("static/WEB-INF/img/VYI.png").getInputStream();
//        response.setContentType(MediaType.IMAGE_PNG_VALUE);
    	InputStream in = new ByteArrayInputStream(Data.vyi);
        IOUtils.copy(in, response.getOutputStream());
    }
}