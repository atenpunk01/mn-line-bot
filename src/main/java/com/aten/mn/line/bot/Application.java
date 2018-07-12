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
import java.util.concurrent.TimeUnit;

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
	private static DecimalFormat df0 = new DecimalFormat("#,##0");
	private static DecimalFormat df = new DecimalFormat("#,##0.00");
	private static DecimalFormat df9 = new DecimalFormat("#,##0.000000000");
	private static DecimalFormat df8 = new DecimalFormat("#,##0.00000000");
	private static List<CoinModel> changeNode;
	private static List<CoinModel> listGv = null;
	private static List<CoinModel> listCb = null;
	private static String messageMno = "";

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {

		// Fix Data
		changeNode = new ArrayList<CoinModel>();
		CoinModel vyi = new CoinModel();
		vyi.setName("VYI");
		int[] vyiBlock = new int[3];
		int[] vyiCollateral = new int[3];
		vyiBlock[0] = 150000;
		vyiCollateral[0] = 6000;
		vyiBlock[1] = 200000;
		vyiCollateral[1] = 8000;
		vyiBlock[2] = 300000;
		vyiCollateral[2] = 12000;
		vyi.setChangeBlock(vyiBlock);;
		vyi.setChangeCollateral(vyiCollateral);
		changeNode.add(vyi);

		CoinModel htrc = new CoinModel();
		htrc.setName("HTRC");
		int[] htrcBlock = new int[3];
		int[] htrcCollateral = new int[3];
		htrcBlock[0] = 320000;
		htrcCollateral[0] = 100000;
		htrcBlock[1] = 500000;
		htrcCollateral[1] = 150000;
		htrcBlock[2] = 750000;
		htrcCollateral[2] = 200000;
		htrc.setChangeBlock(htrcBlock);;
		htrc.setChangeCollateral(htrcCollateral);
		changeNode.add(htrc);

		CoinModel goss = new CoinModel();
		goss.setName("GOSS");
		int[] gossBlock = new int[3];
		int[] gossCollateral = new int[3];
		gossBlock[0] = 100000;
		gossCollateral[0] = 10000;
		gossBlock[1] = 150000;
		gossCollateral[1] = 20000;
		gossBlock[2] = 200000;
		gossCollateral[2] = 30000;
		goss.setChangeBlock(gossBlock);;
		goss.setChangeCollateral(gossCollateral);
		changeNode.add(goss);


		CoinModel cdm = new CoinModel();
		cdm.setName("CDM");
		int[] cdmBlock = new int[3];
		int[] cdmCollateral = new int[3];
		cdmBlock[0] = 75000;
		cdmCollateral[0] = 50000;
		cdmBlock[1] = 150000;
		cdmCollateral[1] = 100000;
		cdmBlock[2] = 300000;
		cdmCollateral[2] = 250000;
		cdm.setChangeBlock(cdmBlock);;
		cdm.setChangeCollateral(cdmCollateral);
		changeNode.add(cdm);

		SpringApplication.run(Application.class, args);
		
//		try {
//			final String coin = "goss";
//			Runnable r1 = new Runnable() {			
//				@Override
//				public void run() {				
//					List<CoinModel> listGraviex = new ArrayList<CoinModel>();
//					CoinModel modelG = new CoinModel();
//					modelG.setName(coin.toUpperCase());
//					modelG.setKey(coin.toLowerCase()+"btc");
//					listGraviex.add(modelG);
//					priceGraviex(listGraviex);
//					System.out.println("r1 exiting.");
//				}
//			};
//			Runnable r2 = new Runnable() {			
//				@Override
//				public void run() {
//					List<CoinModel> listCryptoBridge = new ArrayList<CoinModel>();
//					CoinModel modelC = new CoinModel();
//					modelC.setName(coin.toUpperCase());
//					modelC.setKey(coin.toUpperCase()+"_BTC");
//					listCryptoBridge.add(modelC);
//					priceCryptoBridge(listCryptoBridge);
//					System.out.println("r2 exiting.");
//				}
//			};
//			Runnable r3 = new Runnable() {
//				@Override
//				public void run() {
//					masternodeOnline(coin.toUpperCase());
//					System.out.println("r3 exiting.");
//				}
//			};
//			Thread t1 = new Thread(r1);
//			t1.start();
//			//			t1.join();
//			Thread t2 = new Thread(r2);
//			t2.start();
//			//			t2.join();
//			Thread t3 = new Thread(r3);
//			t3.start();
//			//			t3.join();
//
//			System.out.println("Thread One is alive: "
//					+ t1.isAlive());
//			System.out.println("Thread Two is alive: "
//					+ t2.isAlive());
//			System.out.println("Thread Three is alive: "
//					+ t3.isAlive());
//			try {
//				System.out.println("Waiting for threads to finish.");
//				t1.join();
//				t2.join();
//				t3.join();
//			} catch (InterruptedException e) {
//				System.out.println("Main thread Interrupted");
//			}
//
//			System.out.println("Main thread exiting.");
//
//			String message = "";
//			for (CoinModel m : listGv) {
//				message += m.getName() + "\n Buy : " + m.getBuy() + "\n Sell : " + m.getSell() + "\n";
//			}
//			for (CoinModel m : listCb) {
//				message += m.getName() + "\n Buy : " + m.getBuy() + "\n Sell : " + m.getSell() + "\n";
//			}
//			message += messageMno;
//
//			System.out.println(message);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@EventMapping
	public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent){
		try {
			String pesan = messageEvent.getMessage().getText().toLowerCase();

			if(pesan.startsWith("/")) {
				String coin = pesan.startsWith("/p ")?pesan.split(" ")[1]:pesan.substring(1, pesan.length());				
				System.out.println("coin : "+coin);
				String message = "";
				try {
					Runnable r1 = new Runnable() {			
						@Override
						public void run() {				
							List<CoinModel> listGraviex = new ArrayList<CoinModel>();
							CoinModel modelG = new CoinModel();
							modelG.setName(coin.toUpperCase());
							modelG.setKey(coin.toLowerCase()+"btc");
							listGraviex.add(modelG);
							priceGraviex(listGraviex);
							System.out.println("r1 exiting.");
						}
					};
					Runnable r2 = new Runnable() {			
						@Override
						public void run() {
							List<CoinModel> listCryptoBridge = new ArrayList<CoinModel>();
							CoinModel modelC = new CoinModel();
							modelC.setName(coin.toUpperCase());
							modelC.setKey(coin.toUpperCase()+"_BTC");
							listCryptoBridge.add(modelC);
							priceCryptoBridge(listCryptoBridge);
							System.out.println("r2 exiting.");
						}
					};
					Runnable r3 = new Runnable() {
						@Override
						public void run() {
							masternodeOnline(coin.toUpperCase());
							System.out.println("r3 exiting.");
						}
					};
					Thread t1 = new Thread(r1);
					t1.start();
					Thread t2 = new Thread(r2);
					t2.start();
					Thread t3 = new Thread(r3);
					t3.start();
					System.out.println("Thread One is alive: "
							+ t1.isAlive());
					System.out.println("Thread Two is alive: "
							+ t2.isAlive());
					System.out.println("Thread Three is alive: "
							+ t3.isAlive());
					try {
						System.out.println("Waiting for threads to finish.");
						t1.join();
						t2.join();
						t3.join();
					} catch (InterruptedException e) {
						System.out.println("Main thread Interrupted");
					}
					System.out.println("Main thread exiting.");

					for (CoinModel m : listGv) {
						message += m.getName() + "\n Buy : " + m.getBuy() + "\n Sell : " + m.getSell() + "\n";
					}
					for (CoinModel m : listCb) {
						message += m.getName() + "\n Buy : " + m.getBuy() + "\n Sell : " + m.getSell() + "\n";
					}
					message += messageMno;

					System.out.println(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String replyToken = messageEvent.getReplyToken();
				balasChatDenganRandomJawaban(replyToken, message);
			}else if(pesan.equals("atenpunk")){
				String jawaban = getRandomJawaban();
				String replyToken = messageEvent.getReplyToken();
				balasChatDenganRandomJawaban(replyToken, jawaban);
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
		TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
		try {
			lineMessagingClient
			.replyMessage(new ReplyMessage(replyToken, jawabanDalamBentukTextMessage))
			.get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Ada error chat");
		}
	}

	public static String masternodeOnline(String coin) {
		messageMno = "";
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
			URL url = new URL("https://masternodes.online/currencies/"+coin+"/");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			//			connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			//			connection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
			connection.addRequestProperty("Accept-Language", "th,en-US;q=0.7,en;q=0.3");
			//			connection.addRequestProperty("Connection", "keep-alive");
			//			connection.addRequestProperty("Cookie", "XSRF-TOKEN=AbLt5dZOyRlPWqot3nBGz0fmBh%2F5Sy%2FzkOGRblJUMmo%3D; _peatio_session=5efb8487874d373d2c253b94ae9c9f11; lang=en");
			//			connection.addRequestProperty("Host", "graviex.net");
			//			connection.addRequestProperty("If-None-Match", "b7fcd10e801effd9b708c4ee625911e7");
			//			connection.addRequestProperty("Upgrade-Insecure-Requests", "1");
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
				boolean chk = false;
				boolean chkVolume = false;
				int index = 0;
				while ((inputLine = in.readLine()) != null) {
					if(inputLine.contains("<strong>Volume</strong>")) {
						chkVolume = true;
						response.append("Volume : ");
					}else if(inputLine.contains("ROI (annual):")) {
						chk = true;
						response.append("ROI : ");
					}else if(inputLine.contains("Active masternodes:")) {
						chk = true;
						response.append(inputLine);
					}else if(inputLine.contains("Coins locked:")) {
						chk = true;
						response.append(inputLine);
					}else if(inputLine.contains("Last "+coin+" block")) { 						
						response.append(inputLine.split("generated")[0]);
					}else if(inputLine.contains("AVG block time:")) {
						chk = true;
						response.append(inputLine);
					}else if(inputLine.contains("Required coins for masternode:")) {
						chk = true;
						response.append("Required coins mn : ");
					}else if(inputLine.contains("Masternode worth:")) {
						chk = true;
						response.append(inputLine);
					}else {
						if(chk) {
							chk = false;
							response.append(inputLine+"\n");
						}
						if(chkVolume) {
							index++;
							if(index==6) {
								chkVolume = false;
								response.append(inputLine+"\n");
							}
						}
					}					
				}
				in.close();

				messageMno = response.toString().trim().replaceAll("	", "");
				messageMno = messageMno.replaceAll("</td>", "");
				messageMno = messageMno.replaceAll("<td>", "");
				messageMno = messageMno.replaceAll(":", " : ");
				messageMno = messageMno.replaceAll(coin+" ", "");
				try {
					if(changeNode!=null && changeNode.size()>0) {
						for(CoinModel model:changeNode) {
							if(model.getName().equals(coin)) {
								int lastBlock = 0;
								int avgBlock = 0;
								String[] dataArray = messageMno.split("\n");
								for(String a:dataArray) {
									if(a.contains("Last block")) {
										lastBlock = Integer.parseInt(a.split(" ")[2].replaceAll(",", ""));
									}else if(a.contains("AVG block time")) {
										String temp_01 = a.split(" : ")[1];
										String[] temp_02 = temp_01.split(" ");
										for(String time:temp_02) {
											if(time.contains("m")) {
												avgBlock += (Integer.parseInt(time.replaceAll("m", ""))*60);
											}else if(time.contains("s")) {
												avgBlock += Integer.parseInt(time.replaceAll("s", ""));
											}
										}
									}
								}
								int changeCollateral = 0;
								int changeBlock = 0;
								for(int i=0;i<model.getChangeBlock().length;i++) {
									int block = model.getChangeBlock()[i];
									int collateral = model.getChangeCollateral()[i];
									if(block > lastBlock) {
										changeCollateral = collateral;
										changeBlock = block;
										break;
									}
								}
								int blockLeft = changeBlock-lastBlock;
								int seconds = blockLeft*avgBlock;
								int day = (int)TimeUnit.SECONDS.toDays(seconds);        
								long hours = (TimeUnit.SECONDS.toHours(seconds) - (day*24));
								long minute = (TimeUnit.SECONDS.toMinutes(seconds) - ((day*24*60)+(hours*60)));
								messageMno = messageMno + "\nCollateral "+df0.format(changeCollateral)+" at block "+df0.format(changeBlock);
								messageMno = messageMno + "\nTime left "+day+" day "+hours+" hour "+minute+" minute";
								break;
							}
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				//				System.out.println(messageMno);
			} else {
				throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
			}
			connection.disconnect();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return messageMno;
	}

	public static List<CoinModel> priceGraviex(List<CoinModel> listGraviex) {
		listGv = new ArrayList<CoinModel>();
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
				//				String responseMsg = connection.getResponseMessage();
				//				System.out.println(responseMsg);
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
					//					System.out.println(coinModel.getKey());
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
						model.setName(coinModel.getName()+" (GV)");
						model.setBuy(buy);
						model.setSell(sell);
						model.setChange(df.format(Double.parseDouble(change)));
						listGv.add(model);
					}
				}
			} else {
				throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listGv;
	}

	public static List<CoinModel> priceCryptoBridge(List<CoinModel> listCryptoBridge) {
		listCb = new ArrayList<CoinModel>();
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
				//				String responseMsg = connection.getResponseMessage();
				//				System.out.println(responseMsg);
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
								model.setName(coinModel.getName()+" (CB)");
								model.setBuy(df8.format(Double.parseDouble(bid)));
								model.setSell(df8.format(Double.parseDouble(ask)));
								model.setChange(df.format(Double.parseDouble("0")));
								listCb.add(model);
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
		return listCb;
	}
}

