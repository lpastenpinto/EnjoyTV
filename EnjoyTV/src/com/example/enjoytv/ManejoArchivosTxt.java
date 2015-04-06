package com.example.enjoytv;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ManejoArchivosTxt {
	
	 List<List<String>> tiempo_banners; 
	String ruta_playlist;//="/storage/sdcard0/enjoytv/playlist/";
	public ManejoArchivosTxt(String ruta_playl){
		ruta_playlist=ruta_playl;
		
	}
	public ManejoArchivosTxt(){
		//ruta_playlist=ruta_playl;
		
	}
	public ArrayList<String> lector_archivo_conf(String archivo){
		
		ArrayList<String> playlist = new ArrayList<String>();
		
		File file = new File(archivo);
	

		try {
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;		   
		    while ((line = br.readLine()) != null) {		    
		    	playlist.add(line);///storage/sdcard0/enjoytv/videos/"+		    		       
		    }	
		    br.close();
		}
		catch (Exception e) {
			
		}				
									
		return playlist;
	}
	
public ArrayList<String> lector_playlist(String nombre_archivo){
		
		ArrayList<String> playlist = new ArrayList<String>();
		
		File file = new File(ruta_playlist+nombre_archivo+".txt");
	

		try {
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;		   
		    while ((line = br.readLine()) != null) {		    
		    	playlist.add(line);///storage/sdcard0/enjoytv/videos/"+		    		       
		    }	
		    br.close();
		}
		catch (Exception e) {
			
		}				
									
		return playlist;
	}

public boolean GuardarArchivoConfiguracion(String ruta_archivo,String ruta_servidor,String user_servidor,String pass_servidor,String base_servidor,String hora_actualizar,String nombre_imagen_espera){
	try{
		String saltiLinea="\r\n";
		File file = new File(ruta_archivo);
		FileOutputStream fos = new FileOutputStream(file);
	
		MCrypt mcrypt = new MCrypt();
		String ruta_servidorEncrypted = MCrypt.bytesToHex( mcrypt.encrypt(ruta_servidor) );
		String user_servidorEncrypted = MCrypt.bytesToHex( mcrypt.encrypt(user_servidor) );
		String pass_servidorEncrypted = MCrypt.bytesToHex( mcrypt.encrypt(pass_servidor) );
		String base_servidorEncrypted = MCrypt.bytesToHex( mcrypt.encrypt(base_servidor) );
		String nombre_imagen_esperaEncrypted = MCrypt.bytesToHex( mcrypt.encrypt(nombre_imagen_espera) );
		String hora_actualizarEncrypted = MCrypt.bytesToHex( mcrypt.encrypt(hora_actualizar) );

		fos.write(ruta_servidorEncrypted.getBytes());
		fos.write(saltiLinea.getBytes());
		fos.write(user_servidorEncrypted.getBytes());
		fos.write(saltiLinea.getBytes());
		fos.write(pass_servidorEncrypted.getBytes());
		fos.write(saltiLinea.getBytes());
		fos.write(base_servidorEncrypted.getBytes());
		fos.write(saltiLinea.getBytes());
		fos.write(nombre_imagen_esperaEncrypted.getBytes());
		fos.write(saltiLinea.getBytes());
		fos.write(hora_actualizarEncrypted.getBytes());		
		fos.close();
	}catch(Exception ex){
		
		return false;
	}
	
	
	return true;
}
	
}


