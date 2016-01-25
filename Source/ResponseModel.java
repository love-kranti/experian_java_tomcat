package com.app.rest;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class ResponseModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1566089018564185277L;

	private String errorMessage;
	
	private Map responseMap;
	
	private String xmlResponse;

	private String totalResponse;
	
	private String uniqueId;
	
	private String exceptionString;
	
	public void setuniqueId(String uniqueId){
		this.uniqueId = uniqueId;
	}

	public String getuniqueId(){
		return this.uniqueId;
	}
	

	public void setTotalResponse(String totalResponse){
		this.totalResponse = totalResponse;
	}

	public String getTotalResponse(){
		return this.totalResponse;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

	public String getXmlResponse() {
		return xmlResponse;
	}

	public void setXmlResponse(String xmlResponse) {
		this.xmlResponse = xmlResponse;
	}

	
	public Map getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map responseMap) {
		this.responseMap = responseMap;
	}


	@Override
	public String toString() {
		return "ResponseModel [errorMessage=" + errorMessage + ", responseMap="
				+ responseMap + ", xmlResponse=" + xmlResponse +  ", totalResponse=" + totalResponse + "]";
	}

	public String getExceptionString() {
		return exceptionString;
	}

	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}		
	

}
