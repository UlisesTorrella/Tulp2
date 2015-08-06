package com.example.ulises.tulp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceCall {
	private String baseUrl;
	private String sessionId = null;
	
	public ServiceCall(String baseUrl) {
		this.baseUrl = baseUrl;
		this.sessionId = null;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String get(String destUrl) throws IOException
	{
		URL url;
		String result = null;
		url = new URL(baseUrl + destUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(3500);
		conn.setReadTimeout(3500);
		//conn.addRequestProperty("user", userName);
		//conn.connect();
		if(null != sessionId) {
			conn.addRequestProperty("Cookie", "id=" + sessionId);
		}
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()), 1024);
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null)
			{
				sb.append(line);
			}
			rd.close();
			result = sb.toString();
		}
		catch (Exception e) {
			int errorCode = conn.getResponseCode();
			result = Integer.toString(errorCode);
		}
		return result;
	}

	public String put(String destUrl) throws IOException
	{
		URL url;
		String result;
		url = new URL(baseUrl + destUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3500);
		conn.setReadTimeout(3500);
		conn.setRequestMethod("PUT");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		if(null != sessionId) {
			conn.addRequestProperty("Cookie", "id=" + sessionId);
		}
		
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write("".getBytes("UTF-8"));
		outputStream.close();

/*		@SuppressWarnings("unused")
		int responseCode = conn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
		}*/
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()), 1024);
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null)
		{
			sb.append(line);
		}
		rd.close();
		result = sb.toString();
		return result;
	}

	public String post(String destUrl) throws IOException
	{
		URL url;
		String result;
		url = new URL(baseUrl + destUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3500);
		conn.setReadTimeout(3500);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-Type", "application/json");
		if(null != sessionId) {
			conn.addRequestProperty("Cookie", "id=" + sessionId);
		}
		int responseCode = conn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
		}


	/*		
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(data.getBytes("UTF-8"));
		outputStream.close();
 */
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()), 1024);
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null)
		{
			sb.append(line);
		}
		rd.close();
		result = sb.toString();
		return result;
	}

}
