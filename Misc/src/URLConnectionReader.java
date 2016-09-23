import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import org.json.*;

public class URLConnectionReader {
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
        
        for(JSONObject jsonData: arl){
        	JSONArray products = jsonData.getJSONArray("products");
        	for(int i = 0; i < products.length(); i++){
	        	JSONObject product = (JSONObject)products.get(i);
	        	JSONArray variants = product.getJSONArray("variants");
	        	for(int j = 0; j < variants.length(); j++){
	        		JSONObject variant = (JSONObject)variants.get(j);
	        		String price = variant.getString("price");
	        		totalPrice += Float.parseFloat(price);
	        		System.out.println(price);
	        	}
        	}
        	
        	
        }
        
        System.out.println(totalPrice);
        
        /*JSONArray myJSArr = new JSONArray(strWebData);
        JSONObject jsobj = (JSONObject) myJSONObj.get("products");
        JSONArray variantArr;*/
       
    }
}