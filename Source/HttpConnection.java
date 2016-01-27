package com.app.rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HttpConnection {
	
	private static final Logger logger = Logger.getLogger(HttpConnection.class);

	public static String landingPageSubmit(String request) throws Exception{

		String url = "https://consumer.experian.in:8444/ECV/content/landingPageSubmit.action";
		logger.info("Call url : " + url);
		logger.info(request);
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
		        new OutputStreamWriter(os, "UTF-8"));
		writer.write(request);
		writer.flush();
		writer.close();
		os.close();
		
		int status = conn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			Map<String, List<String>> rHeaders = conn.getHeaderFields();
			List<String> cookies = rHeaders.get("Set-Cookie");
			
			for(String cookie : cookies) {
				if(cookie.indexOf("JSESSIONID") >= 0) {
					return cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
				}
			}
		}
		logger.info(conn);
		return "";
	}
	
	public static String openCustomerDetailsFormAction(String jsessionId, String request) throws Exception {

		String url = "https://consumer.experian.in:8444/ECV/content/openCustomerDetailsFormAction.action";
		logger.info("Call url : " + url);
		logger.info(jsessionId);
		logger.info(request);

		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty( "Content-Type", "application/json" );
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("Cookie","JSESSIONID=" + jsessionId);
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
		        new OutputStreamWriter(os, "UTF-8"));
		writer.write(request);
		writer.flush();
		writer.close();
		os.close();
		int status = conn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			
			Map map = parseJson(print(conn));
			return map.get("stageOneRequestId").toString();
		}
		return "";
	}
	
	public static void fetchScreenMetaDataAction(String jsessionId, String request) throws Exception {

		String url = "https://consumer.experian.in:8444/ECV/content/fetchScreenMetaDataAction.action";
		logger.info("Call url : " + url);
		logger.info(jsessionId);
		logger.info(request);


		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty( "Content-Type", "application/json" );
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("Cookie","JSESSIONID=" + jsessionId);
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
		        new OutputStreamWriter(os, "UTF-8"));
		writer.write(request);
		writer.flush();
		writer.close();
		os.close();
		int status = conn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			
			Map map = parseJson(print(conn));
		}
	}
	
	public static String submitRequest(String jsessionId, String params) throws Exception {

		String url = "https://consumer.experian.in:8444/ECV/content/submitRequest.action";
		logger.info("Call url : " + url);
		logger.info(jsessionId);
		logger.info(params);


		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");
		conn.setRequestProperty("Cookie","JSESSIONID=" + jsessionId);
		
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
		        new OutputStreamWriter(os, "UTF-8"));
		writer.write(params);
		writer.flush();
		writer.close();
		os.close();
		
		int status = conn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			
			Map map = parseJson(print(conn));
			if(map.get("errorString").toString() != "" && map.get("errorString").toString() != null){
				return "error "+map.get("errorString").toString();				
			}
			return map.get("url").toString();
		}
		return "";
	}
	
	public static String directCRQRequest(String url, String jsessionId, ArrayList<NameValuePair> params) throws Exception {
	
		logger.info("Call url : " + url);
		logger.info(jsessionId);
		logger.info(params);

		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty( "Content-Type", "application/json" );
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");
		conn.setRequestProperty("Accept", "*/*");
		
		int status = conn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			String read = print(conn);
			
			int indexOfStage2Id = -1;
			if (read.indexOf("stage2Id\" value=") != -1)
				indexOfStage2Id = read.indexOf("stage2Id\" value=")+17;
			int indexOfStageIdEnd = read.substring(indexOfStage2Id).indexOf("\"")+indexOfStage2Id;
			return read.substring(indexOfStage2Id, indexOfStageIdEnd);
		}
		return "";
	}
		
	public static String paymentSubmitRequest(String params) throws Exception {

		String url = "https://consumer.experian.in:8445/ECV-P2/content/paymentSubmitRequest.action?"+params;
		logger.info("Call url : " + url);
		logger.info(params);

		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");
		conn.setRequestProperty("Accept", "*/*");
		
		int status = conn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			
			Map map = parseJson(print(conn));
			if(map.containsKey("errorMessage") && map.get("errorMessage") == null && map.get("errorString").toString() == "error"){
				return "customError";
			}
			if(map.containsKey("errorMessage") &&  map.get("errorMessage") != null){
				if(map.get("errorMessage").toString().equalsIgnoreCase("Invalid Voucher Code")){			
				  return map.get("errorMessage").toString();
				}
			}
			Map<String, List<String>> rHeaders = conn.getHeaderFields();
			List<String> cookies = rHeaders.get("Set-Cookie");
			
			for(String cookie : cookies) {
				if(cookie.indexOf("JSESSIONID") >= 0) {
					return cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
				}
			}
		}
		return "";
	}
	
	public static Map generateQuestionForConsumer(String jsessionId, String request) throws Exception {

		String url = "https://consumer.experian.in:8445/ECV-P2/content/generateQuestionForConsumer.action?"+request;
		logger.info("Call url : " + url);
		logger.info(jsessionId);
		logger.info(request);

		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty( "Content-Type", "application/json" );
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("Cookie","JSESSIONID=" + jsessionId);
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
		        new OutputStreamWriter(os, "UTF-8"));
		writer.write(request);
		writer.flush();
		writer.close();
		os.close();
		int status = conn.getResponseCode();
		logger.info("generatequestioncustomer~"+status);
		if (status == HttpURLConnection.HTTP_OK) {
			
			return parseJson(print(conn));
		}
		
		return null;
	}
	
	public static String downloadPdfForCreditReport(String jsessionId, String request) throws Exception {

		String url = "https://consumer.experian.in:8445/ECV-P2/content/downloadPdfForCreditReport.action?"+request;
		logger.info("Call url : " + url);
		logger.info(jsessionId);
		logger.info(request);

		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty( "Content-Type", "application/json" );
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("Cookie","JSESSIONID=" + jsessionId);
		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
		        new OutputStreamWriter(os, "UTF-8"));
		writer.write(request);
		writer.flush();
		writer.close();
		os.close();
		int status = conn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			
			String data =  print(conn);
			String response = "";
			Document doc = Jsoup.parse(data);
		    for (Element e : doc.select("INProfileResponse")) {
		    	response = e.toString();		      
		    }
		    return response;
		}
		return "";
	}
	
	private static Map parseJson(String json) throws Exception{
		
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, Map.class);
	}

	private static String print(HttpURLConnection conn) throws Exception{
		
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) 
		{
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		return response.toString();
	}

}