package com.example;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.io.ByteStreams;

@Controller
public class HelloController {

	@GetMapping("/hello")
	String hello(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getHeader("X-Line-Signature");
		System.out.println("signature : "+signature);
		byte[] data = null;
		try {
			data = ByteStreams.toByteArray(request.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String json = new String(data);
		System.out.println("json : "+json);
		return "hello";
	}
}