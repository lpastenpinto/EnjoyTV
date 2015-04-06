package com.example.enjoytv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;



public class LectorArchivoJson{
	
	public ArrayList<String> leerPlaylist(String archivo){
		
		
		ArrayList<String> playlist = new ArrayList<String>(); 
		
		 try{
	 			File file = new File(archivo);
	 			BufferedReader reader = new BufferedReader(new FileReader(file));
	 	
	 			 StringBuilder sb = new StringBuilder();
	 			 String line="";
	 			 while ((line = reader.readLine()) != null) {
	 				 sb.append(line + "\n");
	 			 }
	 			 reader.close();
	 			
	 			 String json = sb.toString(); 			 
	 			
	 			MCrypt mcrypt = new MCrypt();
	 			String decrypted = new String( mcrypt.decrypt( json) );
	 			JSONObject Objeto= new JSONObject(decrypted);
	 			 	 
	 				
	 			 JSONArray ArrayVideos = Objeto.getJSONArray("Videos");
	 				 	 
	 			
	 			 for(int x=0;x<ArrayVideos.length();x++){
	 					
	 					 JSONObject Video = ArrayVideos.getJSONObject(x);
	 						 				
	 					 
	 					 for(int z=0;z<Video.length();z++){
	 					
	 						playlist.add(Video.getString("video"));
	 											
	 					 }
	 					 
	 					 
	 						 				 		 					 	 					 	 						 		
	 					
	                 	 
	              }                   	 		
	 			 			 	 			 	 	
	 	 }catch(Exception ex){
	 				
	 		//Toast.makeText(getApplicationContext(), 
              //      ex+"", Toast.LENGTH_LONG).show();
	 	 }
		return playlist;
		
		
	}		

	 
	 
}
