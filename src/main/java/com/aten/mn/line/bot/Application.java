package com.aten.mn.line.bot;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.aten.mn.line.charts.Data;
import com.aten.mn.line.model.Coin;
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

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmssSSS",Locale.US);

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
				if(Data.coinList == null) {
					Data.coinList = new ArrayList<Coin>();
				}
				LineBot lineBot = new LineBot(); 
				String message = lineBot.genData(coin,true);
				List<String> imageList = lineBot.getImageBx();
				if(message==null || message.equals("")) {
					message = coin.toUpperCase()+" not found data";
				}
				String replyToken = messageEvent.getReplyToken();
				balasChatDenganRandomJawaban(replyToken, message,coin.toUpperCase(),imageList);
			}else if(pesan.equals("atenpunk")){
				String jawaban = getRandomJawaban();
				String replyToken = messageEvent.getReplyToken();
				balasChatDenganRandomJawaban(replyToken, jawaban,null,null);
			}
		}catch (Exception e) {
			e.printStackTrace();
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

	private void balasChatDenganRandomJawaban(String replyToken, String jawaban, String coin, List<String> imageList){
		List<Message> messages = new ArrayList<Message>();
		if(replyToken!=null && !replyToken.trim().equals("")) {
			TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
			messages.add(jawabanDalamBentukTextMessage);
		}
		System.out.println("send coin : "+coin);
		if(coin!=null && !coin.trim().equals("")) {
			for(Coin coinModel:Data.coinList) {
				if(coinModel.getCoin().equals(coin)) {
					String url = "https://mn-line-bot.herokuapp.com/img/"+(sdf.format(new Date())+"?coin="+coin);
					System.out.println("send url : "+url);
					ImageMessage imageMessage = new ImageMessage(url,url);
					//				ImageMessage imageMessage = new ImageMessage("https://cdn4.iconfinder.com/data/icons/network-and-sharing-line-icons-vol-1/48/02-512.png", "https://cdn4.iconfinder.com/data/icons/network-and-sharing-line-icons-vol-1/48/02-512.png");
					messages.add(imageMessage);
					break;
				}
			}
			if(imageList!=null) {
				for(String url:imageList) {
					if(url!=null) {
						ImageMessage imageMessage = new ImageMessage(url,url);
						messages.add(imageMessage);
					}
				}
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

