package com.app.rest;
 
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
 
@Path("/rest/services")
public class ExperianIntegrationService{	
	
	private static final Logger logger = Logger.getLogger(ExperianIntegrationService.class);
	private static final String LOG_MARKER = "LOG_MARKER";
	@POST
	@Path("/landingPageSubmit")	
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseModel getlandingPageDetails(String inputJsonObj) {	
		
		//BasicConfigurator.configure();
		String logMarker = null;
		ResponseModel responseMap = new ResponseModel();
		try {
			
			
			//String requestParams = (String) inputJsonObj.get("input");
			Map map = parseJson(inputJsonObj);
			logger.info("getlandingPageDetails  ~ "+map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER")+" ~Entry");
			logMarker = map.get("LOG_MARKER").toString();
			//String voucherCode = "CMD1UjUz9";
			
			if( map.get("clientName").toString() == null){
				responseMap.setErrorMessage("Client Name is blank");
				return responseMap;
			}
			
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("clientName", map.get("clientName").toString()));
			params.add(new BasicNameValuePair("allowInput", map.get("allowInput").toString()));
			params.add(new BasicNameValuePair("allowEdit", map.get("allowEdit").toString()));
			params.add(new BasicNameValuePair("allowCaptcha", map.get("allowCaptcha").toString()));
			params.add(new BasicNameValuePair("allowConsent", map.get("allowConsent").toString()));
			params.add(new BasicNameValuePair("allowConsent_additional", map.get("allowConsent_additional").toString()));
			params.add(new BasicNameValuePair("allowEmailVerify", map.get("allowEmailVerify").toString()));
			params.add(new BasicNameValuePair("allowVoucher", map.get("allowVoucher").toString()));
			params.add(new BasicNameValuePair("voucherCode", map.get("voucherCode").toString()));
			params.add(new BasicNameValuePair("firstName", map.get("firstName").toString()));
			params.add(new BasicNameValuePair("surname", map.get("surName").toString()));
			params.add(new BasicNameValuePair("dateOfBirth", map.get("dateOfBirth").toString()));
			params.add(new BasicNameValuePair("gender", map.get("gender").toString()));
			params.add(new BasicNameValuePair("mobileNo", map.get("mobileNo").toString()));
			params.add(new BasicNameValuePair("email", map.get("email").toString()));
			params.add(new BasicNameValuePair("flatno", map.get("flatno").toString()));
			params.add(new BasicNameValuePair("city", map.get("city").toString()));
			params.add(new BasicNameValuePair("state", map.get("state").toString()));
			params.add(new BasicNameValuePair("pincode", map.get("pincode").toString()));
			params.add(new BasicNameValuePair("pan", map.get("pan").toString()));
			params.add(new BasicNameValuePair("reason", map.get("reason").toString()));			
			params.add(new BasicNameValuePair("middleName", map.get("middleName").toString()));
			params.add(new BasicNameValuePair("telephoneNo", map.get("telephoneNo").toString()));
			params.add(new BasicNameValuePair("telephoneType", map.get("telephoneType").toString()));
			params.add(new BasicNameValuePair("passport", map.get("passport").toString()));
			params.add(new BasicNameValuePair("voterid", map.get("voterid").toString()));
			params.add(new BasicNameValuePair("aadhaar", map.get("aadhaar").toString()));
			params.add(new BasicNameValuePair("driverlicense", map.get("driverlicense").toString()));			
			String request = getQuery(params);
			

			
			String jsessionId = HttpConnection.landingPageSubmit(request);

			
			
			String stageOneRequestId = HttpConnection.openCustomerDetailsFormAction(jsessionId,"");
			
			
			responseMap.setuniqueId(stageOneRequestId.toString());
			
			logger.info("getlandingPageDetails  ~ "+(map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER"))
					+" ~ request : "+(request == null? "null":request)
					+" jsessionId: "+ (jsessionId ==null?"null":jsessionId)
					+" stageOneRequestId: " + (stageOneRequestId ==null?"null":stageOneRequestId)
					+"~ Log Marker 1");
			params.clear();
			
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("clientName", map.get("clientName").toString()));
			params.add(new BasicNameValuePair("allowInput", map.get("allowInput").toString()));
			params.add(new BasicNameValuePair("allowEdit", map.get("allowEdit").toString()));
			params.add(new BasicNameValuePair("allowCaptcha", map.get("allowCaptcha").toString()));
			params.add(new BasicNameValuePair("allowConsent", map.get("allowConsent").toString()));
			params.add(new BasicNameValuePair("allowConsent_additional", map.get("allowConsent_additional").toString()));
			params.add(new BasicNameValuePair("allowEmailVerify", map.get("allowEmailVerify").toString()));
			params.add(new BasicNameValuePair("allowVoucher", map.get("allowVoucher").toString()));
			params.add(new BasicNameValuePair("voucherCode", map.get("voucherCode").toString()));
			params.add(new BasicNameValuePair("firstName", map.get("firstName").toString()));
			params.add(new BasicNameValuePair("surname", map.get("surName").toString()));
			params.add(new BasicNameValuePair("dob", map.get("dateOfBirth").toString()));
			params.add(new BasicNameValuePair("gender", map.get("gender").toString()));
			params.add(new BasicNameValuePair("mobileNo", map.get("mobileNo").toString()));
			params.add(new BasicNameValuePair("email", map.get("email").toString()));
			params.add(new BasicNameValuePair("flatPlotHouseNo", map.get("flatno").toString()));
			params.add(new BasicNameValuePair("city", map.get("city").toString()));
			params.add(new BasicNameValuePair("state", map.get("stateid").toString()));
			params.add(new BasicNameValuePair("pincode", map.get("pincode").toString()));
			params.add(new BasicNameValuePair("panNo", map.get("pan").toString()));
			params.add(new BasicNameValuePair("reason", map.get("reason").toString()));
			params.add(new BasicNameValuePair("requestReason", map.get("reason").toString()));
			params.add(new BasicNameValuePair("middleName", map.get("middleName").toString()));
			params.add(new BasicNameValuePair("telephoneNo", map.get("telephoneNo").toString()));
			params.add(new BasicNameValuePair("telephoneType", map.get("telephoneType").toString()));
			params.add(new BasicNameValuePair("passportNo", map.get("passport").toString()));
			params.add(new BasicNameValuePair("voterIdNo", map.get("voterid").toString()));
			params.add(new BasicNameValuePair("universalIdNo", map.get("aadhaar").toString()));
			params.add(new BasicNameValuePair("driverLicenseNo", map.get("driverlicense").toString()));
			params.add(new BasicNameValuePair("hitId", stageOneRequestId));
			request = getQuery(params);
			
			logger.info("getlandingPageDetails  ~ "+ (map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER"))
					+" ~ request : "+ (request == null? "null":request)
					+" jsessionId: "+ (jsessionId ==null?"null":jsessionId)
					+" stageOneRequestId: " + (stageOneRequestId ==null?"null":stageOneRequestId)
					+ "~ Log Marker 2");
			
			HttpConnection.fetchScreenMetaDataAction(jsessionId,   request);

			String resp = HttpConnection.submitRequest(jsessionId,  request);
					
			
			logger.info("getlandingPageDetails  ~ "+ (map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER"))
					+" ~ request : "+ (request == null? "null":request)
					+" jsessionId: "+ (jsessionId ==null?"null":jsessionId)
					+" stageOneRequestId: " + (stageOneRequestId ==null?"null":stageOneRequestId)
					+" resp: " + (resp ==null?"null":resp)
					+ "~ Log Marker 4");
			
			if(resp ==null){
				responseMap.setErrorMessage("RESPONSE_NULL");
				return responseMap;
			}
			
			if(resp.equals("")){
				responseMap.setErrorMessage("RESPONSE_BLANK");
				return responseMap;
			}
			
			if(resp.startsWith("error")){
				responseMap.setErrorMessage(resp.replace("error ", ""));	
				return responseMap;
			}
			
			params.clear();
			
			String stageTwoRequestId = HttpConnection.directCRQRequest(resp, jsessionId, params);

			logger.info("getlandingPageDetails  ~ "+(map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER"))
					+" ~ request : "+(request == null? "null":request)
					+" jsessionId: "+ (jsessionId ==null?"null":jsessionId)
					+" stageOneRequestId: " + (stageOneRequestId ==null?"null":stageOneRequestId)
					+" stageTwoRequestId: " + (stageTwoRequestId ==null?"null":stageTwoRequestId)
					+ "~ Log Marker 5");
			
			params.add(new BasicNameValuePair("captchCode", "-999"));
			params.add(new BasicNameValuePair("payFlag", "true"));
			params.add(new BasicNameValuePair("voucherCode", map.get("voucherCode").toString()));			
			params.add(new BasicNameValuePair("stgOneHitId", stageOneRequestId));
			params.add(new BasicNameValuePair("stgTwoHitId", stageTwoRequestId));
			request = getQuery(params);
						
			
			String jsessionIdResp = HttpConnection.paymentSubmitRequest(request);
				
			logger.info("getlandingPageDetails  ~ "+ (map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER"))
					+" ~ request : "+(request == null? "null":request)
					+" jsessionId: "+ (jsessionId ==null?"null":jsessionId)
					+" stageOneRequestId:  " + (stageOneRequestId ==null?"null":stageOneRequestId)
					+" stageTwoRequestId: " + (stageTwoRequestId ==null?"null":stageTwoRequestId)
					+" jsessionIdResp: " + (jsessionIdResp ==null?"null":jsessionIdResp)	
					+ "~ Log Marker 6");			
			
			if(jsessionIdResp.equalsIgnoreCase("customError")){
				responseMap.setErrorMessage(jsessionIdResp);
				logger.info("getlandingPageDetails  ~ "+(map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER")	)
						+ "~ customError ~ Log Marker 6");		
				return responseMap;				
			}
			
			if(jsessionIdResp.equalsIgnoreCase("Invalid Voucher Code")){
				responseMap.setErrorMessage("voucherExpired");
				logger.info("getlandingPageDetails  ~ "+(map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER")	)
						+ "~ voucherExpired ~ Log Marker 6");
				return responseMap;				
			}
			String responseJson = null;
			
			String message = "";			
			String answer = "";					
			String qId = "";									
		
			while (true){
				logger.info("getlandingPageDetails  ~ "+(map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER")	)
						+ "~ Log Marker 6");
				params.clear();
				params = new ArrayList<NameValuePair>();				
				
				params.add(new BasicNameValuePair("stgOneHitId", stageOneRequestId));
				params.add(new BasicNameValuePair("stgTwoHitId", stageTwoRequestId));
				request = getQuery(params);
								
				Map questionMap = HttpConnection.generateQuestionForConsumer(jsessionIdResp,  request);
					
				responseJson = (String) questionMap.get("responseJson");
				logger.info("getlandingPageDetails  ~ "+(map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER"))
						+" ~ request : "+(request == null? "null":request)
						+" jsessionId: "+ (jsessionId ==null?"null":jsessionId)
						+" stageOneRequestId: " + (stageOneRequestId ==null?"null":stageOneRequestId)
						+" stageTwoRequestId: " + (stageTwoRequestId ==null?"null":stageTwoRequestId)
						+" responseJson: " + (responseJson ==null?"null":responseJson)		
						+ "~ Log Marker 7");
				
				if (responseJson.equalsIgnoreCase("passedReport")){
					String pdfData =  (String) questionMap.get("showHtmlReportForCreditReport");					
					Document doc = Jsoup.parse(pdfData);
					Element input = doc.select("input[name=xmlResponse]").first();
					String response = input.attr("value");	
					responseMap.setXmlResponse(response);					
				}
				
				
				if (responseJson.equalsIgnoreCase("next")){	
					questionMap.put("jsessionId2", jsessionIdResp);
					responseMap.setResponseMap(questionMap);										
				}
				
				if (responseJson.equalsIgnoreCase("systemError")){					
					responseMap.setErrorMessage("systemError");							
				}
				
				if (responseJson.equalsIgnoreCase("inCorrectAnswersGiven")){
					responseMap.setErrorMessage("inCorrectAnswersGiven");					
				}
				
				if (responseJson.equalsIgnoreCase("insufficientQuestion")){
					responseMap.setErrorMessage("insufficientQuestion");						
				}
				
				if (responseJson.equalsIgnoreCase("creditReportEmpty")){
					responseMap.setErrorMessage("creditReportEmpty");				
				}
				if (responseJson.equalsIgnoreCase("error")){
					responseMap.setErrorMessage("error");				
				}
				
				return responseMap;
			}
			
		} catch (Exception e) {							
			logger.info("getlandingPageDetails  ~ "+(logMarker== null?"NOT_GIVEN":logMarker)
					+e.toString()
					+ "~ Log Marker 8 ");

			responseMap.setErrorMessage("Error occured");	
			responseMap.setExceptionString(e.toString());
			return responseMap;
		}			
	}
	
	@POST
	@Path("/questionForCustomer")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseModel getQuestion(String inputJsonObj) {
				
		ResponseModel responseMap = new ResponseModel();
		
		//String requestParams = (String) inputJsonObj.get("input");		
		
		String message = "";			
		String jsessionId2 = "";				
		String responseJson = null;		
		String logMarker=null;
		try {
			Map map = parseJson(inputJsonObj);
			logMarker = map.get("LOG_MARKER").toString();
			jsessionId2 = map.get("jsessionId2").toString();
			
			logger.info("getQuestion  ~ "+(map.get("LOG_MARKER")== null?"NOT_GIVEN":map.get("LOG_MARKER"))
					+" ~ jsessionId2: "+ (jsessionId2==null?"null":jsessionId2)
					+ "~Log Marker 1");
			
		    while (true){		
			
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();	
				
				params.add(new BasicNameValuePair("answer", map.get("answer").toString()));									
				params.add(new BasicNameValuePair("questionId", map.get("qid").toString()));
				params.add(new BasicNameValuePair("stgOneHitId", map.get("stgOneHitId").toString()));
				params.add(new BasicNameValuePair("stgTwoHitId", map.get("stgTwoHitId").toString()));
				String request = getQuery(params);							
				Map questionMap = HttpConnection.generateQuestionForConsumer(jsessionId2,  request);	
						
				responseJson = (String) questionMap.get("responseJson");
				
				if (responseJson.equalsIgnoreCase("passedReport")){
					String pdfData =  (String) questionMap.get("showHtmlReportForCreditReport");					
					Document doc = Jsoup.parse(pdfData);
					Element input = doc.select("input[name=xmlResponse]").first();
					String response = input.attr("value");
					responseMap.setErrorMessage("Success");
					responseMap.setXmlResponse(response);	
					responseMap.setTotalResponse(pdfData);					
				}				
				
				if (responseJson.equalsIgnoreCase("next")){	
					questionMap.put("jsessionId2", jsessionId2);
					responseMap.setResponseMap(questionMap);										
				}
				
				if (responseJson.equalsIgnoreCase("systemError")){					
					responseMap.setErrorMessage("systemError");							
				}
				
				if (responseJson.equalsIgnoreCase("inCorrectAnswersGiven")){
					responseMap.setErrorMessage("inCorrectAnswersGiven");					
				}
				
				if (responseJson.equalsIgnoreCase("insufficientQuestion")){
					responseMap.setErrorMessage("insufficientQuestion");						
				}
				
				if (responseJson.equalsIgnoreCase("error") || responseJson.equalsIgnoreCase("creditReportEmpty")){
					responseMap.setErrorMessage("creditReportEmpty");				
				}
			 return responseMap;
		  }
		
		} catch (Exception e) {	
			logger.info("getQuestion  ~ "+(logMarker== null?"NOT_GIVEN":logMarker)
					+" ~ jsessionId2: "+ (jsessionId2==null?"null":jsessionId2)
					+ "~Log Marker 2");
			responseMap.setErrorMessage("Error occured");	
			responseMap.setExceptionString(e.toString());
			return responseMap;
		}
				
	}
	
	
    private static Map parseJson(String json) throws Exception{
		
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, Map.class);
	}
	
	private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}		
 
}