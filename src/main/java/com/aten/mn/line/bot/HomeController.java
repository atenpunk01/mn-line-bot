package com.aten.mn.line.bot;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aten.mn.line.charts.Data;
import com.aten.mn.line.model.Coin;

@RestController
public class HomeController {

	@Autowired
	ServletContext context; 
	
    @RequestMapping("/")
    public String home(){
        return "";
    }
    
    @RequestMapping(value = "/img/**", method = RequestMethod.GET)
    public void getImage(@RequestParam("coin") String coin, HttpServletResponse response) throws IOException {
    	
    	System.out.println("getImage : "+coin);
    	System.out.println("Data.coinList size : "+Data.coinList.size());
    	for(Coin coinModel:Data.coinList) {
			if(coinModel.getCoin().equals(coin)) {
				System.out.println("found : "+coinModel.getCoin());
		    	InputStream in = new ByteArrayInputStream(coinModel.getData());
		        IOUtils.copy(in, response.getOutputStream());
		        break;
			}
		}
    }
    
//    @RequestMapping(value = "/img/CDM", method = RequestMethod.GET)
//    public void getImageCDM(HttpServletResponse response) throws IOException {
////        InputStream in = new ClassPathResource("static/WEB-INF/img/CDM.png").getInputStream();
////        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//    	InputStream in = new ByteArrayInputStream(Data.cdm);
//        IOUtils.copy(in, response.getOutputStream());
//    }
}