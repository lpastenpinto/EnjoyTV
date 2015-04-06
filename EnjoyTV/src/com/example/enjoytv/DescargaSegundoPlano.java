package com.example.enjoytv;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;



class DescargaSegundoPlano extends AsyncTask<ArrayList<String>, Float, String>{
	 
	private Context context;
	
	String ruta_video;
	String ruta_playlist;
	String server_ftp;
	String user_ftp;
	String pass_ftp;
	String ruta_base_servidor;
	String ruta_imagen_espera;
	String hora_actualizacion;
	
	
	//in constructor:
	public DescargaSegundoPlano(Context context){
	        this.context=context;
	}
	
    protected void onPreExecute() {
    	
       
     }

     protected  String doInBackground(ArrayList<String>... params) {
    	 ArrayList<String>  parametros = new ArrayList<String>();    	 
    	 parametros= params[0];
    	 
    	 ruta_video=parametros.get(0);
    	 ruta_playlist=parametros.get(1);
    	 server_ftp=parametros.get(2);
    	 user_ftp=parametros.get(3);;
    	 pass_ftp=parametros.get(4);
    	 ruta_base_servidor=parametros.get(5);
    	 ruta_imagen_espera=parametros.get(6);
    	 hora_actualizacion=parametros.get(7);
    	
    	 if(conectadoWifi()){
    	 
    		 final DownloaderNuevoPlaylist DescargarPlaylist = new DownloaderNuevoPlaylist(ruta_video,ruta_playlist,context,server_ftp,user_ftp,pass_ftp,ruta_base_servidor);									
			
    		 try{
							
				 DescargarPlaylist.conectarFTP();
				 DescargarPlaylist.descargarPlaylistFTP();
				 DescargarPlaylist.borrarPlaylistAnterior(); 				
				 DescargarPlaylist.borrar_videos_inncesarios();
				 DescargarPlaylist.descargar_nuevos_videos();
				 DescargarPlaylist.desconectarFTP();
										
				
				}catch(Exception ex){
					
					return "Error al descargar:"+ex;
					
				}	
    		 
			}else{
				
				return "Imposible descargar Nuevo Playlist.No hay coneccion a Internet. ";
			}
    	 
    	return "Comenzara la reproduccion del playlist...";
       
     }

     protected void onProgressUpdate (Float... valores) {
      
     }

     protected void onPostExecute(String respuesta) {
    	     	 
    	 super.onPostExecute(respuesta);    	 		     	
    
    	 Toast.makeText(context, ""+respuesta, Toast.LENGTH_LONG).show();
    		    	 
         Intent intent = new Intent(context, ActividadReproducirPlaylist.class);
         intent.putExtra("ruta_video", ruta_video);
		 intent.putExtra("ruta_playlist", ruta_playlist);		
		 intent.putExtra("ruta_imagen_espera", ruta_imagen_espera);			 
		 intent.putExtra("server_ftp", server_ftp);
		 intent.putExtra("user_ftp", user_ftp);
		 intent.putExtra("pass_ftp", pass_ftp);
		 intent.putExtra("ruta_base_servidor",  ruta_base_servidor);
		 intent.putExtra("hora_actualizacion",  hora_actualizacion);
       		
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
         context.startActivity(intent);
    	
     }
     
     protected boolean conectadoWifi(){
 		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
 		if (connectivity != null) {
 			NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
 				if (info != null) {
 						if (info.isConnected()) {
 								return true;
 						}
 				}
 		}
 		return false;
 	}
}
