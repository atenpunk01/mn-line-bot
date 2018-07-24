package com.aten.mn.line.bot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.aten.mn.line.charts.Charts;
import com.google.gson.Gson;

public class LineBot {	

	private final String cryptoBridgeTickerApi = "https://api.crypto-bridge.org/api/v1/ticker";
	private final String graviexTickerApi = "https://graviex.net:443//api/v2/tickers.json";
	private final String cryptopiaTickerApi = "https://www.cryptopia.co.nz/api/GetMarket/";
	private final String bxTickerApi = "https://bx.in.th/api/";
	private DecimalFormat df0 = new DecimalFormat("#,##0");
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private DecimalFormat df9 = new DecimalFormat("#,##0.000000000");
	private DecimalFormat df8 = new DecimalFormat("#,##0.00000000");
	private List<CoinModel> changeNode;
	private List<CoinModel> listGv = null;
	private List<CoinModel> listCb = null;
	private List<CoinModel> listCp = null;
	private List<CoinModel> listBx = null;
	private String messageMno = "";

	public static void main(String[] args) {
		LineBot bot = new LineBot();
		bot.genData("VYI");

		//		CoinModel coinModel = new CoinModel();
		//		coinModel.setName("BTC");
		//		bot.priceBX(coinModel);
	}

	public String genData(String coin) {
		String message = "";
		loadFixData();
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
			Runnable r4 = new Runnable() {
				@Override
				public void run() {
					CoinModel modelCp = new CoinModel();
					modelCp.setName(coin.toUpperCase());
					modelCp.setKey(coin.toUpperCase()+"_BTC");
					priceCryptopia(modelCp);
					System.out.println("r4 exiting.");
				}
			};
			Runnable r5 = new Runnable() {
				@Override
				public void run() {
					CoinModel modelBX = new CoinModel();
					modelBX.setName(coin.toUpperCase());
					modelBX.setKey(coin.toUpperCase());
					priceBX(modelBX);
					System.out.println("r5 exiting.");
				}
			};
			Thread t1 = new Thread(r1);
			t1.start();
			Thread t2 = new Thread(r2);
			t2.start();
			Thread t3 = new Thread(r3);
			t3.start();
			Thread t4 = new Thread(r4);
			t4.start();
			Thread t5 = new Thread(r5);
			t5.start();
			System.out.println("Thread One is alive: " + t1.isAlive());
			System.out.println("Thread Two is alive: " + t2.isAlive());
			System.out.println("Thread Three is alive: " + t3.isAlive());
			System.out.println("Thread Four is alive: " + t4.isAlive());	
			System.out.println("Thread Five is alive: " + t5.isAlive());			
			try {
				System.out.println("Waiting for threads to finish.");
				t1.join();
				t2.join();
				t3.join();
				t4.join();
				t5.join();
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
			for (CoinModel m : listCp) {
				message += m.getName() + "\n Buy : " + m.getBuy() + "\n Sell : " + m.getSell() + "\n";
			}				
			for (CoinModel m : listBx) {
				message += m.getName() + "\n Buy : " + m.getBuy() + "\n Sell : " + m.getSell() + "\n";
			}
			message += messageMno;

			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	private void loadFixData() {
		// Fix Data
		changeNode = new ArrayList<CoinModel>();
		CoinModel vyi = new CoinModel();
		vyi.setName("VYI");
		long vyiMaxsupply = 210000000L;
		int[] vyiBlock = new int[15];
		int[] vyiCollateral = new int[15];
		vyiBlock[0] = 150000;
		vyiCollateral[0] = 6000;
		vyiBlock[1] = 200000;
		vyiCollateral[1] = 8000;
		vyiBlock[2] = 300000;
		vyiCollateral[2] = 12000;
		vyiBlock[3] = 400000;
		vyiCollateral[3] = 17000;
		vyiBlock[4] = 500000;
		vyiCollateral[4] = 25000;
		vyiBlock[5] = 600000;
		vyiCollateral[5] = 30000;
		vyiBlock[6] = 700000;
		vyiCollateral[6] = 35000;
		vyiBlock[7] = 800000;
		vyiCollateral[7] = 40000;
		vyiBlock[8] = 900000;
		vyiCollateral[8] = 50000;
		vyiBlock[9] = 1000000;
		vyiCollateral[9] = 60000;
		vyiBlock[10] = 1500000;
		vyiCollateral[10] = 70000;
		vyiBlock[11] = 2500000;
		vyiCollateral[11] = 80000;
		vyiBlock[12] = 5000000;
		vyiCollateral[12] = 90000;
		vyiBlock[13] = 10000000;
		vyiCollateral[13] = 100000;
		vyiBlock[14] = 20000000;
		vyiCollateral[14] = 100000;
		vyi.setChangeBlock(vyiBlock);;
		vyi.setChangeCollateral(vyiCollateral);
		vyi.setMaxsupply(vyiMaxsupply);
		changeNode.add(vyi);

		CoinModel htrc = new CoinModel();
		htrc.setName("HTRC");
		long htrcMaxsupply = 210000000L;
		int[] htrcBlock = new int[6];
		int[] htrcCollateral = new int[6];
		htrcBlock[0] = 320000;
		htrcCollateral[0] = 100000;
		htrcBlock[1] = 500000;
		htrcCollateral[1] = 150000;
		htrcBlock[2] = 750000;
		htrcCollateral[2] = 200000;
		htrcBlock[3] = 1000000;
		htrcCollateral[3] = 250000;
		htrcBlock[4] = 1250000;
		htrcCollateral[4] = 250000;
		htrcBlock[5] = 1500000;
		htrcCollateral[5] = 250000;
		htrc.setChangeBlock(htrcBlock);;
		htrc.setChangeCollateral(htrcCollateral);
		htrc.setMaxsupply(htrcMaxsupply);
		changeNode.add(htrc);

		CoinModel goss = new CoinModel();
		goss.setName("GOSS");
		long gossMaxsupply = 50000000L;
		int[] gossBlock = new int[8];
		int[] gossCollateral = new int[8];
		gossBlock[0] = 100000;
		gossCollateral[0] = 10000;
		gossBlock[1] = 125000;
		gossCollateral[1] = 20000;
		gossBlock[2] = 150000;
		gossCollateral[2] = 30000;
		gossBlock[3] = 200000;
		gossCollateral[3] = 50000;
		gossBlock[4] = 400000;
		gossCollateral[4] = 75000;
		gossBlock[5] = 500000;
		gossCollateral[5] = 100000;
		gossBlock[6] = 600000;
		gossCollateral[6] = 150000;
		gossBlock[7] = 700000;
		gossCollateral[7] = 200000;
		goss.setChangeBlock(gossBlock);;
		goss.setChangeCollateral(gossCollateral);
		goss.setMaxsupply(gossMaxsupply);
		changeNode.add(goss);

		CoinModel cdm = new CoinModel();
		cdm.setName("CDM");
		long cdmMaxsupply = 3500000000L;
		int[] cdmBlock = new int[8];
		int[] cdmCollateral = new int[8];
		cdmBlock[0] = 75000;
		cdmCollateral[0] = 50000;
		cdmBlock[1] = 150000;
		cdmCollateral[1] = 100000;
		cdmBlock[2] = 300000;
		cdmCollateral[2] = 250000;
		cdmBlock[3] = 500000;
		cdmCollateral[3] = 300000;
		cdmBlock[4] = 1000000;
		cdmCollateral[4] = 400000;
		cdmBlock[5] = 2000000;
		cdmCollateral[5] = 500000;
		cdmBlock[6] = 5000000;
		cdmCollateral[6] = 750000;
		cdmBlock[7] = 10000000;
		cdmCollateral[7] = 1000000;		
		cdm.setChangeBlock(cdmBlock);;
		cdm.setChangeCollateral(cdmCollateral);
		cdm.setMaxsupply(cdmMaxsupply);
		changeNode.add(cdm);
	}


	public String masternodeOnline(String coin) {
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
			connection.setConnectTimeout(1000*5);
			connection.setReadTimeout(1000*5);
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
				int numberChart = 45;
				String[] responseChart = new String[numberChart];
				boolean chkChart = false;
				int indexChart = 0;				
				while ((inputLine = in.readLine()) != null) {
					if(inputLine.contains("<strong>Volume</strong>")) {
						chkVolume = true;
						response.append("Volume : ");
					}else if(inputLine.contains("ROI (annual):")) {
						chk = true;
						response.append("ROI: ");
					}else if(inputLine.contains("AVG masternode reward frequency:")) {
						chk = true;
						response.append("AVG Reward: ");
					}else if(inputLine.contains("Active masternodes:")) {
						chk = true;
						response.append(inputLine);
					}else if(inputLine.contains("Supply:")) {
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
						response.append("Required coins mn: ");
					}else if(inputLine.contains("Masternode worth:")) {
						chk = true;
						response.append("MW:");
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
					
					if(inputLine.contains("<canvas id=\"canvas\"")) {
						chkChart = true;
					}
					if(chkChart) {
						if(indexChart < numberChart) {
							responseChart[indexChart++] = inputLine.trim().replaceAll("	", "");
						}
					}
				}
				in.close();

				messageMno = response.toString().trim().replaceAll("	", "");
				messageMno = messageMno.replaceAll("</td>", "");
				messageMno = messageMno.replaceAll("<td>", "");
				messageMno = messageMno.replaceAll(":", " : ");
				messageMno = messageMno.replaceAll(coin+" ", "");
				double coinsLocked = 0;
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
									}else if(a.contains("Coins locked")) {
										String temp_01 = a.split(" : ")[1];
										String temp_02 = temp_01.split(" ")[1];
										String temp_03 = temp_02.replaceAll("\\(", "");
										String temp_04 = temp_03.replaceAll("%\\)", "");
										System.out.println("temp_01 : "+temp_01);
										System.out.println("temp_02 : "+temp_02);
										System.out.println("temp_03 : "+temp_03);
										System.out.println("temp_04 : "+temp_04);
										coinsLocked = Double.parseDouble(temp_04);
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
								if(model.getMaxsupply()>0)
									messageMno = messageMno + "\nMaxsupply "+df0.format(model.getMaxsupply());
								break;
							}
						}
					}
					
					
					String date = "";
					String node = "";
					String roi = "";
					String price = "";
					index = 0;
					for(String data:responseChart) {
						//					System.out.println((index)+" : "+data);
						index++;
						if(index==8) {
							date = (data.split(" ")[1])
									.replaceAll("\\[", "")
									.replaceAll("\\],", "")
									.replaceAll("\"", "");
						}
						if(index==16) {
							node = (data.split(" ")[1])
									.replaceAll("\\[", "")
									.replaceAll("\\],", "")
									.replaceAll("\"", "");
						}
						if(index==24) {
							roi = (data.split(" ")[1])
									.replaceAll("\\[", "")
									.replaceAll("\\],", "")
									.replaceAll("\"", "");
						}
						if(index==37) {
							price = (data.split(" ")[1])
									.replaceAll("\\[", "")
									.replaceAll("\\],", "")
									.replaceAll("\"", "");
						}					
					}
					System.out.println("date : "+date);
					System.out.println("node : "+node);
					System.out.println("roi : "+roi);
					System.out.println("price : "+price);
					String[] dateArray = date.split(",");
					String[] nodeArray = node.split(",");
					String[] roiArray = roi.split(",");
					String[] priceArray = price.split(",");
					String[] lable = new String[30];
					double[] value = new double[30];
					int[] node2 = new int[30];
					int begin = dateArray.length>30?dateArray.length-30:0;
					int j=0;
					double maxPrice = 0;
					for(int i=begin;i<dateArray.length;i++) {
//						Node model = new Node();
//						model.setCoinName(coin);
//						model.setDateTime(sdf.parse(dateArray[i]));
//						model.setNode(Integer.parseInt(nodeArray[i]));
//						model.setPrice(new BigDecimal(priceArray[i]));
//						model.setRoi(Double.parseDouble(roiArray[i]));
//						nodeList.add(model);
//						lable[j] = dateArray[i].split("-")[0]+"-"+dateArray[i].split("-")[1];
						lable[j] = dateArray[i].split("-")[1];
						value[j] = new BigDecimal(priceArray[i].split("\\.")[1]).doubleValue();
						if(maxPrice==0) {
							maxPrice = value[j];
						}
						if(maxPrice<value[j]) {
							maxPrice = value[j];
						}
						j++;								
					}
					for(int i=0;i<lable.length;i++) {
						System.out.println(i+" : "+lable[i]+", "+value[i]);
					}
					Charts charts = new Charts();
					maxPrice = maxPrice-(maxPrice % 10);
					charts.genImage(coin,lable,value,node2,coinsLocked,(maxPrice+(maxPrice/8)),(maxPrice+(maxPrice/8)));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				
				//				System.out.println(messageMno);
			} else {
				throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
			}
			connection.disconnect();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return messageMno;
	}

	public List<CoinModel> priceCryptopia(CoinModel coinModel) {
		listCp = new ArrayList<CoinModel>();
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
			URL url = new URL(cryptopiaTickerApi+coinModel.getKey());
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setConnectTimeout(1000*5);
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

				Gson gson = new Gson();
				CryptopiaTickerModel obj = gson.fromJson(response.toString(), CryptopiaTickerModel.class);
				if(obj!=null) {
					CoinModel model = new CoinModel();
					String buy = df8.format(obj.getData().getBidPrice());
					String sell = df8.format(obj.getData().getAskPrice());
					model.setName(coinModel.getName()+" (CP)");
					model.setBuy(buy);
					model.setSell(sell);
					listCp.add(model);
				}
			} else {
				throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
			}
			connection.disconnect();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listCp;
	}

	public List<CoinModel> priceGraviex(List<CoinModel> listGraviex) {
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
			connection.setConnectTimeout(1000*5);
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
			System.out.println(e.getMessage());
		}
		return listGv;
	}

	public List<CoinModel> priceCryptoBridge(List<CoinModel> listCryptoBridge) {
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
			connection.setConnectTimeout(1000*5);
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
			System.out.println(e.getMessage());
		}
		return listCb;
	}

	public List<CoinModel> priceBX(CoinModel coinModel) {
		listBx = new ArrayList<CoinModel>();
		try {
			//			TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
			//				@Override
			//				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			//					return null;
			//				}
			//
			//				@Override
			//				public void checkClientTrusted(X509Certificate[] certs,
			//						String authType) {
			//				}
			//
			//				@Override
			//				public void checkServerTrusted(X509Certificate[] certs,
			//						String authType) {
			//				}
			//			}};
			//			SSLContext sc = SSLContext.getInstance("SSL");
			//			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			//			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			URL url = new URL(bxTickerApi);
			//			URL url = new URL("https://bx.in.th/api/pairing/");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setConnectTimeout(1000*5);
			//			connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.addRequestProperty("Accept-Encoding", "UTF-8");
			//			connection.setRequestProperty("Content-Type", "text/javascript;charset=utf-8");
			//			connection.addRequestProperty("Accept-Language", "th,en-US;q=0.7,en;q=0.3");
			//			connection.addRequestProperty("Connection", "keep-alive");
			//			connection.addRequestProperty("Cookie", "__cfduid=d43bb2d54a4db06b0cc58316974df11c61508775856; bxsound=0;");
			//			connection.addRequestProperty("PHPSESSID", "csvvhiqg0vqup5kp4496k53dqn");
			//			connection.addRequestProperty("Cache-Control", "max-age=0");
			//			connection.addRequestProperty("Host", "bx.in.th");
			//			connection.addRequestProperty("Upgrade-Insecure-Requests", "1");
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
			connection.setDoOutput(true);
			connection.setDoInput(true);
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

				String jsonData = response.toString();
				JSONObject json = new JSONObject(jsonData);
				for(int id = 1; id < 100; id++) {
					try {
						JSONObject data = json.getJSONObject(id+"");
						//						String primary_currency = data.getString("primary_currency");
						String secondary_currency = data.getString("secondary_currency");
						//						System.out.println(secondary_currency);
						if (secondary_currency != null && secondary_currency.equals(coinModel.getName())) {
							JSONObject orderbook = data.getJSONObject("orderbook");
							JSONObject bids = orderbook.getJSONObject("bids");
							JSONObject asks = orderbook.getJSONObject("asks");
							CoinModel model = new CoinModel();
							model.setName(coinModel.getName()+" (BX)");
							model.setBuy(df8.format(bids.getDouble("highbid")));
							model.setSell(df8.format(asks.getDouble("highbid")));
							listBx.add(model);
							break;
						}
					}catch (Exception e) {
						//						System.out.println("Error : "+e.getMessage());
					}
				}
			} else {
				throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
			}
			connection.disconnect();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listBx;
	}
}
