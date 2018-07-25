package com.aten.mn.line.bot;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.aten.mn.line.charts.Data;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication
@LineMessageHandler
public class Application extends SpringBootServletInitializer {

	@Autowired
	private LineMessagingClient lineMessagingClient;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	@EventMapping
	public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent){
		try {
			String pesan = messageEvent.getMessage().getText().toLowerCase();

			if(pesan.startsWith("/")) {
				String coin = pesan.startsWith("/p ")?pesan.split(" ")[1]:pesan.substring(1, pesan.length());				
				System.out.println("coin : "+coin);
				if(pesan.startsWith("/p")) {
					String message = new LineBot().genData(coin,true);
					if(message==null || message.equals("")) {
						message = coin.toUpperCase()+" not found data";
					}
					String replyToken = messageEvent.getReplyToken();
					balasChatDenganRandomJawaban(replyToken, message,coin.toUpperCase());
				}else {
					String message = new LineBot().genData(coin,false);
					if(message==null || message.equals(""))
						message = coin.toUpperCase()+" not found data";
					String replyToken = messageEvent.getReplyToken();
					balasChatDenganRandomJawaban(replyToken, message);
				}
			}else if(pesan.equals("atenpunk")){
				String jawaban = getRandomJawaban();
				String replyToken = messageEvent.getReplyToken();
				balasChatDenganRandomJawaban(replyToken, jawaban,null);
			}
		}catch (Exception e) {
			System.out.println("ERROR : "+e.getMessage());
		}

	}

	private String getRandomJawaban(){
		String jawaban = "";
		int random = new Random().nextInt();
		if(random%2==0){
			jawaban = "Hi";
		} else{
			jawaban = "Hello";
		}
		return jawaban;
	}

	private void balasChatDenganRandomJawaban(String replyToken, String jawaban){
		List<Message> messages = new ArrayList<Message>();
		TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
		messages.add(jawabanDalamBentukTextMessage);
		try {
			lineMessagingClient
			.replyMessage(new ReplyMessage(replyToken, messages))
			.get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Ada error chat");
		}
	}

	private void balasChatDenganRandomJawaban(String replyToken, String jawaban, String coin){
		List<Message> messages = new ArrayList<Message>();
		if(replyToken!=null && !replyToken.trim().equals("")) {
			TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
			messages.add(jawabanDalamBentukTextMessage);
		}
		if(coin!=null && !coin.trim().equals("")) {
			if(coin.equals(Data.coin)) {
				ImageMessage imageMessage = new ImageMessage("https://mn-line-bot.herokuapp.com/img", "https://mn-line-bot.herokuapp.com/img");
				//				ImageMessage imageMessage = new ImageMessage("https://cdn4.iconfinder.com/data/icons/network-and-sharing-line-icons-vol-1/48/02-512.png", "https://cdn4.iconfinder.com/data/icons/network-and-sharing-line-icons-vol-1/48/02-512.png");
				messages.add(imageMessage);
			}
		}
		try {
			lineMessagingClient
			.replyMessage(new ReplyMessage(replyToken, messages))
			.get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Ada error chat");
		}
	}

}

