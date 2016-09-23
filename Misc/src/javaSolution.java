

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import org.json.*;

public class javaSolution {
	/*private JSONObject getJSONData(String strURL){
		JSONObject myJSONObj;
		String strWebData = "";
		URL myURL = new URL(null);
		URLConnection myURLConnection;
		BufferedReader brWebData;
		String strBuffer;
		
		try {
			myURL = new URL(strURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myURLConnection = myURL.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}*/
    public static void main(String[] args) throws Exception {
    	String strWebData = "";
    	float totalPrice = 0;
    	int pageNum = 1;
    	JSONObject myJSONObj;
        ArrayList<JSONObject> arl = new ArrayList<JSONObject>();
    	
        URL myURL;
        URLConnection myURLConnection;
        BufferedReader webData;
        String inputLine;
        myURL = new URL("http://shopicruit.myshopify.com/products.json?page=" + String.valueOf(pageNum));
        myURLConnection = myURL.openConnection();
        webData = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
        while ((inputLine = webData.readLine()) != null) 
            strWebData += inputLine;
        webData.close();
        myJSONObj = new JSONObject(strWebData);
        System.out.println(myJSONObj.getJSONArray("products").length());
        while(myJSONObj.getJSONArray("products").length()>0){
        	arl.add(myJSONObj);
        	pageNum++;
        	strWebData = "";
        	myURL = new URL("http://shopicruit.myshopify.com/products.json?page=" + String.valueOf(pageNum));
            myURLConnection = myURL.openConnection();
            webData = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            while ((inputLine = webData.readLine()) != null) 
                strWebData += inputLine;
            webData.close();
            myJSONObj = new JSONObject(strWebData);
        	
        }
        String productType;
        
        for(JSONObject jsonData: arl){
        	JSONArray products = jsonData.getJSONArray("products");
        	for(int i = 0; i < products.length(); i++){
	        	JSONObject product = (JSONObject)products.get(i);
	        	productType = product.getString("product_type");
	        	System.out.println(productType);
	        	if(productType.compareToIgnoreCase("clock") == 0 || productType.compareToIgnoreCase("watch") == 0){
		        	JSONArray variants = product.getJSONArray("variants");
		        	for(int j = 0; j < variants.length(); j++){
		        		JSONObject variant = (JSONObject)variants.get(j);
		        		String price = variant.getString("price");
		        		totalPrice += Float.parseFloat(price);
		        		System.out.println(price);
		        	}
	        	}
        	}
        	
        	
        }
        
        System.out.printf("$%.2f",totalPrice);
        
        /*JSONArray myJSArr = new JSONArray(strWebData);
        JSONObject jsobj = (JSONObject) myJSONObj.get("products");
        JSONArray variantArr;*/
       
    }
}