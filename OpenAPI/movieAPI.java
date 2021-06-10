package jblee.project01;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class movieAPI {


    @SuppressWarnings("resource")
	public static void main(String[] args) throws ParseException {
        String clientId = "XFDlGaaBTow3L682gP1N"; //���ø����̼� Ŭ���̾�Ʈ ���̵�"
        String clientSecret = "2LUEyHD251"; //���ø����̼� Ŭ���̾�Ʈ ��ũ����"

        Scanner scan = new Scanner(System.in);
  
        String text = null;
        System.out.print("�˻�� �Է��ϼ���: ");
        String query = scan.nextLine();
        
        try {
        	text = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("�˻��� ���ڵ� ����",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/movie?query=" + text;   // json���
        //String apiURL = "https://openapi.naver.com/v1/search/movie.xml?query="+ text; // xml ���


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);
        
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
        JSONArray infoArray = (JSONArray) jsonObject.get("items");
        
        for(int i = 0; i < infoArray.size(); i++) {
        	System.out.println("=item_" + i + "=========================================================");
        	JSONObject itemObject = (JSONObject)infoArray.get(i);
        	System.out.printf("%-20s%s\n", "title:", itemObject.get("title"));
        	System.out.printf("%-20s%s\n", "subtitle:", itemObject.get("subtitle"));
        	System.out.printf("%-20s%s\n", "director:", itemObject.get("director"));
        	System.out.printf("%-20s%s\n", "actor:", itemObject.get("actor"));
        	System.out.printf("%-20s%s\n\n", "userRating:", itemObject.get("userRating"));
        }
        scan.close();
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // ���� ȣ��
                return readBody(con.getInputStream());
            } else { // ���� �߻�
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ��û�� ���� ����", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL�� �߸��Ǿ����ϴ�. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("������ �����߽��ϴ�. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API ������ �дµ� �����߽��ϴ�.", e);
        }
    }
}
