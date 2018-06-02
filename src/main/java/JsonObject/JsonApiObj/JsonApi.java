package JsonObject.JsonApiObj;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

public class JsonApi {

	
	
	public void apitesting() throws Exception
	{
		try
		{
			URL url =new URL("https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false");
			HttpURLConnection conn =(HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode()!=200)
			{
				throw new RuntimeException("HTTP error code:"+conn.getResponseCode());
			}
			
			Scanner scan =new Scanner(url.openStream());
			String entireResponse =new String();
			
			while (scan.hasNext())
				entireResponse+=scan.nextLine();
			//System.out.println("Response:"+entireResponse);

			scan.close();
			
			JSONObject obj = new JSONObject(entireResponse);	
					
			String responseCode= obj.getString("Name");
			System.out.println("Name:"+responseCode);
			
			Boolean responseC =obj.getBoolean("CanRelist");
			System.out.println("CanRelist:"+responseC);
			
			JSONArray arr =obj.getJSONArray("Promotions");
			for(int i=0;i<arr.length();i++)
			{
				String name =arr.getJSONObject(i).getString("Name");
				String Actual =name;
				String Expected ="Gallery";
				 
				String description =arr.getJSONObject(i).getString("Description");
				
				String Expected1="2x larger image";
			    
				if(Actual.contains(Expected) && description.contains(Expected1))
				{
					System.out.println("Name:"+Actual);
					System.out.println("Description:"+description);
					
					Assert.assertEquals(name, Expected);
					
					break;
				}
				
			}
		conn.disconnect();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
