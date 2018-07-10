package com.aten.mn.line.bot;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication
@LineMessageHandler
public class Application extends SpringBootServletInitializer {

	@Autowired
	private LineMessagingClient lineMessagingClient;


	private static final String cryptoBridgeTickerApi = "https://api.crypto-bridge.org/api/v1/ticker";
	private static final String graviexTickerApi = "https://graviex.net:443//api/v2/tickers.json";
	private static DecimalFormat df = new DecimalFormat("#,##0.00");
	private static DecimalFormat df9 = new DecimalFormat("#,##0.000000000");
	private static DecimalFormat df8 = new DecimalFormat("#,##0.00000000");

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@EventMapping
	public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent){
		String pesan = messageEvent.getMessage().getText().toLowerCase();
		String[] pesanSplit = pesan.split(" ");

		if(pesanSplit[0].contains("/price")) {
			String price = "/price goss";
			String coin = price.split(" ")[1];
			System.out.println("coin : "+coin);
			List<CoinModel> listGraviex = new ArrayList<CoinModel>();
			List<CoinModel> listCryptoBridge = new ArrayList<CoinModel>();
			CoinModel modelG = new CoinModel();
			modelG.setName(coin.toUpperCase());
			modelG.setKey(coin.toLowerCase()+"btc");
			listGraviex.add(modelG);
			CoinModel modelC = new CoinModel();
			modelC.setName(coin.toUpperCase());
			modelC.setKey(coin.toUpperCase()+"_BTC");
			listCryptoBridge.add(modelC);
			List<CoinModel> list1 = priceGraviex(listGraviex);
			List<CoinModel> list2 = priceCryptoBridge(listCryptoBridge);
			String message = "";
			for (CoinModel m : list1) {
				message += m.getName() + "\n Buy : " + m.getBuy() + "\n Sell : " + m.getSell() + "\n";
			}
			for (CoinModel m : list2) {
				message += m.getName() + "\n Buy : " + m.getBuy() + "\n Sell : " + m.getSell() + "\n";
			}
			String replyToken = messageEvent.getReplyToken();
			balasChatDenganRandomJawaban(replyToken, message);
		}else if(pesanSplit[0].equals("atenpunk")){
			String jawaban = getRandomJawaban();
			String replyToken = messageEvent.getReplyToken();
			balasChatDenganRandomJawaban(replyToken, jawaban);
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
		TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
		try {
			lineMessagingClient
			.replyMessage(new ReplyMessage(replyToken, jawabanDalamBentukTextMessage))
			.get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Ada error chat");
		}
	}


	public static List<CoinModel> priceGraviex(List<CoinModel> listGraviex) {
		List<CoinModel> list = new ArrayList<CoinModel>();
		try {
			TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			}};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			URL url = new URL(graviexTickerApi);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
			connection.addRequestProperty("Accept-Language", "th,en-US;q=0.7,en;q=0.3");
			connection.addRequestProperty("Connection", "keep-alive");
			connection.addRequestProperty("Cookie", "XSRF-TOKEN=AbLt5dZOyRlPWqot3nBGz0fmBh%2F5Sy%2FzkOGRblJUMmo%3D; _peatio_session=5efb8487874d373d2c253b94ae9c9f11; lang=en");
			connection.addRequestProperty("Host", "graviex.net");
			connection.addRequestProperty("If-None-Match", "b7fcd10e801effd9b708c4ee625911e7");
			connection.addRequestProperty("Upgrade-Insecure-Requests", "1");
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
			connection.setDoOutput(true);
			connection.connect();

			int statusCode = connection.getResponseCode();
			if (statusCode == 200) {
				String responseMsg = connection.getResponseMessage();
				System.out.println(responseMsg);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
//				System.out.println(response.toString());
//				System.out.println("listGraviex.size : "+listGraviex.size());

				JSONObject json = new JSONObject(response.toString());
				for (CoinModel coinModel : listGraviex) {
					System.out.println(coinModel.getKey());
					JSONObject head = json.getJSONObject(coinModel.getKey());
					if (head != null) {
						JSONObject ticker = head.getJSONObject("ticker");
						String buy = ticker.getString("buy");
						String sell = ticker.getString("sell");
						String change = ticker.getString("change");
						CoinModel model = new CoinModel();
						buy = df9.format(Double.parseDouble(buy));
						buy = (buy.substring(0, buy.length() - 1) + "'" + buy.substring(buy.length() - 1, buy.length()));
						sell = df9.format(Double.parseDouble(sell));
						sell = (sell.substring(0, sell.length() - 1) + "'" + sell.substring(sell.length() - 1, sell.length()));
						model.setName(coinModel.getName());
						model.setBuy(buy);
						model.setSell(sell);
						model.setChange(df.format(Double.parseDouble(change)));
						list.add(model);
					}
				}
			} else {
				throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<CoinModel> priceCryptoBridge(List<CoinModel> listCryptoBridge) {
		List<CoinModel> list = new ArrayList<CoinModel>();
		try {
			TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			}};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			URL url = new URL(cryptoBridgeTickerApi);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.connect();

			int statusCode = connection.getResponseCode();
			if (statusCode == 200) {
				String responseMsg = connection.getResponseMessage();
				System.out.println(responseMsg);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				String jsonData = "{\"dataArray\":" + response.toString() + "}";
				//                System.out.println(jsonData);

				JSONObject json = new JSONObject(jsonData);
				JSONArray dataArray = json.getJSONArray("dataArray");
				if (dataArray != null) {
					for (CoinModel coinModel : listCryptoBridge) {
						for (int i = 0; i < dataArray.length(); i++) {
							JSONObject data = dataArray.getJSONObject(i);
							String id = data.getString("id");
							if (id != null && id.equals(coinModel.getKey())) {
								String bid = data.getString("bid");
								String ask = data.getString("ask");
								CoinModel model = new CoinModel();
								model.setName(coinModel.getName());
								model.setBuy(df8.format(Double.parseDouble(bid)));
								model.setSell(df8.format(Double.parseDouble(ask)));
								model.setChange(df.format(Double.parseDouble("0")));
								list.add(model);
								break;
							}
						}
					}
				}
			} else {
				throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

