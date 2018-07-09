package com.aten.mn.line.bot;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.io.ByteStreams;

@Controller
public class HelloController {

	@GetMapping("/hello")
	String hello(HttpServletRequest request, HttpServletResponse response) {
		try {
			String signature = request.getHeader("X-Line-Signature");
			System.out.println("signature : "+signature);
			final byte[] data = ByteStreams.toByteArray(request.getInputStream());
			String json = new String(data);
			System.out.println("json : "+json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "hello";// views/hello.jsp
	}
}