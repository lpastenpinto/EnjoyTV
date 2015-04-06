package com.example.enjoytv;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Calendar;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.content.Context;

import android.os.StrictMode;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownloaderNuevoPlaylist {
	
	String ruta_video;//="/storage/sdcard0/enjoytv/videos";
	String ruta_playlist;//="/storage/sdcard0/enjoytv/playlist/";
	//String ruta_servidor;//="http://elquiweb.net/enjoytv/";
	Context context;
	ProgressBar barraProgreso;
	
	
	String server_ftp;// = "ftp.elquiweb.net";    
    String user_ftp;// = "elquiweb";
    String pass_ftp;// = "LG2nvrD92+n}";
    String ruta_base_servidor;
	TextView txt;
	public FTPClient ftpClient ;
	
	public DownloaderNuevoPlaylist(String ruta_vid,String ruta_playl,Context contx,String serverftp,String userftp,String passftp,String rutaServer){
		
		ruta_video=ruta_vid;
		ruta_playlist=ruta_playl;		
		context=contx;
		
		server_ftp=serverftp;
		user_ftp=userftp;
		pass_ftp=passftp;
		ruta_base_servidor=rutaServer;
		
		ftpClient= new FTPClient();
		
		
	}
	
	
	public void conectarFTP(){
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}			
		try{
			ftpClient.connect(server_ftp);
			ftpClient.login(user_ftp, pass_ftp);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(1024*1024);
			
        }
		catch (Exception ex) {
			 Toast.makeText(context, 
	 		            "Imposible Conectar FTP:"+ex, Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void desconectarFTP(){
		try{
			ftpClient.logout();
			ftpClient.disconnect();
		}catch (Exception ex) {
			Toast.makeText(context, 
 		            "Imposible Desconectar FTP:"+ex, Toast.LENGTH_LONG).show();
        	 
        }
		
	}
	public void descargarVideoFTP(String nombre_video){
		
				
        try {
 
        	if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}        
            
            String remoteFile2 = ruta_base_servidor+"videos/"+nombre_video;
            File downloadFile2 = new File(ruta_video+ nombre_video);
            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));                       
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
            byte[] bytesArray = new byte[1024];
            int bytesRead = 0;
            outputStream2.flush();
            
            while ((bytesRead = inputStream.read(bytesArray)) >0) {
                outputStream2.write(bytesArray, 0, bytesRead);
                outputStream2.flush();
            }
 
            ftpClient.completePendingCommand();
           
            outputStream2.close();
            inputStream.close();
 
        } catch (Exception ex) {
        	 Toast.makeText(context, 
 		            "Error al descargar Video:"+ex, Toast.LENGTH_LONG).show();
        } 
		
		
	} 
	
	
	
	public void descargarPlaylistFTP(){
					
		//FTPClient ftpClient = new FTPClient();
        try {
 
        	if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
        	
          //  ftpClient.connect(server_ftp);
          //  ftpClient.login(user_ftp, pass_ftp);
          //  ftpClient.enterLocalPassiveMode();
          //  ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            String remoteFile2 = ruta_base_servidor+"playlist/"+retornar_fecha_actual()+".json";
            File downloadFile2 = new File(ruta_playlist+ retornar_fecha_actual()+".json");
            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
            byte[] bytesArray = new byte[1024];
            int bytesRead = 0;            
            while ((bytesRead = inputStream.read(bytesArray)) >0) {
                outputStream2.write(bytesArray, 0, bytesRead);
            }
 
            ftpClient.completePendingCommand();
           
            outputStream2.close();
            inputStream.close();
            //ftpClient.logout();
            //ftpClient.disconnect();
 
        } catch (Exception ex) {
        	 Toast.makeText(context, 
 		            "Error al descargar Playlist:"+ex, Toast.LENGTH_LONG).show();
        } 
	}
	
	
	
	
	public void borrarPlaylistAnterior(){				
					
	       		
		File directorio = new File(ruta_playlist);
		File[] ficheros = directorio.listFiles();
		int cantidad_ficheros= ficheros.length;
		
		
		if(cantidad_ficheros>1){
			for(int i=0;i<cantidad_ficheros;i++){
			
				if(!ficheros[i].getName().equals(retornar_fecha_actual()+".json")){
					ficheros[i].delete();
				}
			}
		}else{
			File archivonuevo = new File(directorio ,retornar_fecha_actual()+".json");
			ficheros[0].renameTo(archivonuevo);
			//ficheros[0].delete();
		}
		
		
		
		
	}
	public void borrarTodosPlaylist(){				
		   		
		File directorio = new File(ruta_playlist);
		File[] ficheros = directorio.listFiles();
		int cantidad_ficheros= ficheros.length;
					
		for(int i=0;i<cantidad_ficheros;i++){
						
			ficheros[i].delete();
				
		}						
		
	}
	
	
	
	public void descargar_nuevos_videos(){
						 
		ArrayList<String> videos_existentes= new ArrayList<String>();
		ArrayList<String> playlist= new ArrayList<String>();
		
		LectorArchivoJson lector=new LectorArchivoJson();
		playlist=lector.leerPlaylist(ruta_playlist+ retornar_fecha_actual()+".json");		
							   
	    File f = new File(ruta_video);
	    	    	   
	    File[] files = f.listFiles();
	    	   
	 	    
	        for (int i = 0; i < files.length; i++)	
	        {
	            
	            File file = files[i];	 	   
	          
	            videos_existentes.add(file.getName());//"/storage/sdcard0/enjoytv/videos/"+
	           
	        }
	        
	        /* 
	        for(int i=0;i<videos_existentes.size();i++){
	        	String nombre_video=videos_existentes.get(i);
	        	if(!playlist.contains(nombre_video)){
	        	
	        		File file = new File(ruta_video+"/"+nombre_video);
	        		file.delete();
	        	}	        
	        }*/
	        
	        for(int i=0;i<playlist.size();i++){
	        	String nombre_video=playlist.get(i);
	        	if(!videos_existentes.contains(nombre_video)){	        			        		
	        		descargarVideoFTP(nombre_video);	        			        		
	        	}
	        	
	        }
	        
	        	       	
	 		
	}
	

	
	

	public void borrar_videos_inncesarios(){
	
		ArrayList<String> videos_existentes= new ArrayList<String>();
		ArrayList<String> playlist= new ArrayList<String>();
		LectorArchivoJson lector=new LectorArchivoJson();
		playlist=lector.leerPlaylist(ruta_playlist+ retornar_fecha_actual()+".json");
	
						   
		File f = new File(ruta_video);
    	    		
		File[] files = f.listFiles(); 	   
		for (int i = 0; i < files.length; i++)	
		{
            
            File file = files[i];                    
            videos_existentes.add(file.getName());//"/storage/sdcard0/enjoytv/videos/"+                       
		}
        		
		
        for(int i=0;i<videos_existentes.size();i++){
        	String nombre_video=videos_existentes.get(i);
        	if(!playlist.contains(nombre_video)){
        		
        		File file = new File(ruta_video+"/"+nombre_video);
        
        		file.delete();
        	}	        
        }
                      
	}	
	
	
	
	
public final static String retornar_fecha_actual(){
		
		final Calendar c = Calendar.getInstance();
		int anio = c.get(Calendar.YEAR); 
		int mes = c.get(Calendar.MONTH);		
		mes = mes + 1;
		int dia = c.get(Calendar.DAY_OF_MONTH); 
		
		String dia_s=""+dia;
		String mes_s=""+mes;
		if(mes<10){
			mes_s="0"+mes_s;
		}
		if(dia<10){
			dia_s="0"+dia_s;
		}
		return dia_s+"-"+mes_s+"-"+anio;
		
				
	}
	
}
