/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.io.ByteStreams;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Controller
@SpringBootApplication
public class Main {

	

    private static final String lineApi = "https://api.line.me/v2/bot/message/reply";
    private static final String cryptoBridgeTickerApi = "https://api.crypto-bridge.org/api/v1/ticker";
    private static final String graviexTickerApi = "https://graviex.net:443//api/v2/tickers.json";
//    private static String lineToken = "pMs2I1d7uDeNrD5Hl33uQK3LuPkKhYA9BhimCMm370x";// Test
    private static String lineToken = "NunHl4Fn8vbyHv3Yyz06StJHzg7iNNIDjnhH5c3tHBK";// Masternode
    private static DecimalFormat df = new DecimalFormat("#,##0.00");
    private static DecimalFormat df9 = new DecimalFormat("#,##0.000000000");
    private static DecimalFormat df8 = new DecimalFormat("#,##0.00000000");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("mm", Locale.US);
    private static List<CoinModel> listGraviex;
    private static List<CoinModel> listCryptoBridge;
    
    
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) throws Exception {
//		SpringApplication.run(Main.class, args);
		
		String json = "{\"events\" : [{\"type\" : \"message\""
				+ ",\"replyToken\" : \"bd831a2ec52b4ae7b4819e548fb7b003\""
				+ ",\"source\" : {\"userId\" : \"U79470d76b4dc7374df59e6ac1f98cc39\""
				+ ",\"type\" : \"user\"}"
				+ ",\"timestamp\" : "+new Date().getTime()
				+ ",\"message\" : {\"type\" : \"text\""
				+ ",\"id\" : \"8239817892637\""
				+ ",\"text\" : \"dddd\"}}]}";		

		System.out.println(json);
		JSONObject jsonObj = new JSONObject(json);
		JSONArray events = jsonObj.getJSONArray("events");
        String replyToken = events.getJSONObject(0).getString("replyToken");
		JSONObject source = events.getJSONObject(0).getJSONObject("source");
        String userId = source.getString("userId");
		JSONObject message = events.getJSONObject(0).getJSONObject("message");
        String id = message.getString("id");
        String text = message.getString("text");
		System.out.println("replyToken : "+replyToken);
		System.out.println("userId     : "+userId);
		System.out.println("id         : "+id);
		System.out.println("text       : "+text);
        
		
//		String json = "{\"replyToken\":\"bd831a2ec52b4ae7b4819e548fb7b003\","
//				+ "\"messages\":[{"
//				+ "\"type\":\"text\","
//				+ "\"text\":\"Hello, user\"},{"
//				+ "\"type\":\"text\","
//				+ "\"text\":\"May I help you?\""
//				+ "}]}";
		
//		callEvent("RN44whRTGt70ipZGd3Ijb06zBaBdx9TVHl20patqS2Mxd9gEkeb6Eth6mWm+evAKiplSDHTeRZZhNhUDOlCCmwWMz4Pg1IxoCTCD6kUYyi6YlabByo0R2t3FYObvLobXSfVn+X3YrcUzHhJ9So14kAdB04t89/1O/w1cDnyilFU=", json);
	}

	@RequestMapping("/")
	String index(HttpServletRequest request, HttpServletResponse response) {
		try {
			String signature = request.getHeader("X-Line-Signature");
			System.out.println("signature : "+signature);
			byte[] data = ByteStreams.toByteArray(request.getInputStream());
			String json = new String(data);
			System.out.println("json : "+json);
			

			JSONObject jsonObj = new JSONObject(json);
			JSONArray events = jsonObj.getJSONArray("events");
	        String replyToken = events.getJSONObject(0).getString("replyToken");
			JSONObject source = events.getJSONObject(0).getJSONObject("source");
	        String userId = source.getString("userId");
			JSONObject message = events.getJSONObject(0).getJSONObject("message");
	        String id = message.getString("id");
	        String text = message.getString("text");
			System.out.println("replyToken : "+replyToken);
			System.out.println("userId     : "+userId);
			System.out.println("id         : "+id);
			System.out.println("text       : "+text);
			

			String jsonReturn = "{\"events\" : [{\"type\" : \"message\""
					+ ",\"replyToken\" : \""+replyToken+"\""
					+ ",\"source\" : {\"userId\" : \""+userId+"\""
					+ ",\"type\" : \"user\"}"
					+ ",\"timestamp\" : "+new Date().getTime()
					+ ",\"message\" : {\"type\" : \"text\""
					+ ",\"id\" : \""+id+"\""
					+ ",\"text\" : \""+text+"(By Server)"+"\"}}]}";		
			
			callEvent("RN44whRTGt70ipZGd3Ijb06zBaBdx9TVHl20patqS2Mxd9gEkeb6Eth6mWm+evAKiplSDHTeRZZhNhUDOlCCmwWMz4Pg1IxoCTCD6kUYyi6YlabByo0R2t3FYObvLobXSfVn+X3YrcUzHhJ9So14kAdB04t89/1O/w1cDnyilFU=", jsonReturn);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	public static boolean callEvent(String token, String message) {
        boolean result = false;
        try {
            message = replaceProcess(message);
            message = URLEncoder.encode(message, "UTF-8");
            URL url = new URL(lineApi);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            String parameterString = message;
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.print(parameterString);
            printWriter.close();
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                result = true;
            } else {
                throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String replaceProcess(String txt) {
        txt = replaceAllRegex(txt, "\\\\", "￥");		// \
        return txt;
    }

    private static String replaceAllRegex(String value, String regex, String replacement) {
        if (value == null || value.length() == 0 || regex == null || regex.length() == 0 || replacement == null) {
            return "";
        }
        return Pattern.compile(regex).matcher(value).replaceAll(replacement);
    }
	
	
	public static List<CoinModel> priceGraviex() {
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
//                System.out.println(response.toString());
                JSONObject json = new JSONObject(response.toString());
                for (CoinModel coinModel : listGraviex) {
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

    public static List<CoinModel> priceCryptoBridge() {
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
	

	@RequestMapping("/db")
	String db(Map<String, Object> model) {
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
			stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
			ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

			ArrayList<String> output = new ArrayList<String>();
			while (rs.next()) {
				output.add("Read from DB: " + rs.getTimestamp("tick"));
			}

			model.put("records", output);
			return "db";
		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "error";
		}
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}

}
