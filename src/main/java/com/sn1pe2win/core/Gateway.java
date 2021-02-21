package com.sn1pe2win.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Gateway {
	
	public static final String API_ROOT_PATH = "https://www.bungie.net/Platform";
	public static String X_API_KEY = "";
	public static String USER_AGENT = "Mozilla/5.0";
	
	private static Response<JsonObject> lastResponse;
	
	public static Response<JsonObject> sendPOST(String url, String body) {
		return sendPOST(url, null, body);
	}
	
	public static Response<JsonObject> sendPOST(String url, String accessToken, String POSTBody) {
		URL obj;
		HttpURLConnection httpURLConnection;
		try {
			obj = new URL(API_ROOT_PATH + url);
			httpURLConnection = (HttpURLConnection) obj.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
			httpURLConnection.setRequestProperty("X-API-KEY", X_API_KEY);
			if(accessToken != null) httpURLConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
			httpURLConnection.setDoOutput(true);
		} catch (Exception e) {
			return new Response<JsonObject>(null, 404, e.toString(), "Failed to connect to url " + API_ROOT_PATH + url, 0);
		}

		try {
			OutputStream os = httpURLConnection.getOutputStream();
			os.write(POSTBody.getBytes());
			os.flush();
			os.close();
		} catch(Exception e) {
			return new Response<JsonObject>(null, 404, e.toString(), "Failed to send POST request to " + API_ROOT_PATH + url, 0);
		}

		String inputLine;
		StringBuilder responseCollector = new StringBuilder();
		int responseCode = 500;
		
		try {
			responseCode = httpURLConnection.getResponseCode();
			InputStream stream;
			if(responseCode >= 200 && responseCode < 400) stream = httpURLConnection.getInputStream();
			else stream = httpURLConnection.getErrorStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(stream));
			while ((inputLine = in.readLine()) != null) responseCollector.append(inputLine);
			in.close();
			
			JsonObject response;
			try {
				response = (JsonObject) JsonParser.parseString(responseCollector.toString());
			} catch(Exception e) {
				return new Response<JsonObject>(null, 500, e.toString(), "Unable to create JsonData with content: " + responseCollector.toString(), 0);
			}
			JsonPrimitive errorStatus = response.getAsJsonPrimitive("ErrorStatus");
			JsonPrimitive errorMessage = response.getAsJsonPrimitive("Message");
			JsonPrimitive errorCode = response.getAsJsonPrimitive("ErrorCode");
			lastResponse = new Response<JsonObject>(response, 
					responseCode, 
					errorStatus == null ? "" : errorStatus.getAsString(), 
					errorMessage == null ? "" : errorMessage.getAsString(), 
					errorCode == null ? 0 : errorCode.getAsInt());
			return lastResponse;
		} catch(Exception e) {
			return new Response<JsonObject>(null, responseCode, e.toString(), "Failed to recieve response from " + API_ROOT_PATH + url, 0);
		}
	}
	
	public static Response<JsonObject> sendFullGet(String url) {
		return sendFullGet(url, null);
	}
	
	public static Response<JsonObject> sendFullGet(String fullURL, String accessToken) {
		URL obj;
		HttpURLConnection con;
		try {
			obj = new URL(fullURL);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
		} catch (Exception e) {
			lastResponse = new Response<JsonObject>(null, 404, e.toString(), "", 0);
			return lastResponse;
		}
		
		con.setRequestProperty("X-API-KEY", X_API_KEY);
		if(accessToken != null) con.setRequestProperty("Authorization", "Bearer " + accessToken);
		
		int responseCode = 404;
		String inputLine;
		StringBuilder responseCollector = new StringBuilder();
		
		try {
			responseCode = con.getResponseCode();
			InputStream stream;
			if(responseCode >= 200 && responseCode < 400) stream = con.getInputStream();
			else stream = con.getErrorStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(stream));
			while ((inputLine = in.readLine()) != null) responseCollector.append(inputLine);
			in.close();
		} catch (IOException e) {
			lastResponse = new Response<JsonObject>(null, responseCode, (responseCode == 404 ? "Endpoint not found: " : ""), "", 0);
			return lastResponse;
		}
		
		JsonObject response;
		try {
			response = (JsonObject) JsonParser.parseString(responseCollector.toString());
		} catch(Exception e) {
			return new Response<JsonObject>(null, 500, e.toString(), "Unable to create JsonData", 0);
		}
		
		String errorStatus = response.getAsJsonPrimitive("ErrorStatus").getAsString();
		JsonPrimitive errorMessage = response.getAsJsonPrimitive("Message");
		int errorCode = response.getAsJsonPrimitive("ErrorCode").getAsInt();
		lastResponse = new Response<JsonObject>(response, 
				responseCode, 
				errorStatus, 
				errorMessage == null ? "" : errorMessage.getAsString(), 
				errorCode);
		return lastResponse;
	}
	
	public static Response<JsonObject> sendGet(String url, String accessToken) {
		return sendFullGet(API_ROOT_PATH + url, accessToken);
	}
	
	public static Response<JsonObject> sendGet(String url) {
		return sendFullGet(API_ROOT_PATH + url, null);
	}
	
	public static Response<?> lastResponse() {
		return lastResponse;
	}
}
