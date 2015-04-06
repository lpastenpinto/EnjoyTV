package com.example.enjoytv;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


import com.example.enjoytv.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;



public class ActividadReproducirPlaylist extends Activity {
	final Handler myHandler = new Handler();
	Context mContext = this;
	private VideoView reproductor;

	private ArrayList<String> Playlist;

	
	
	String ruta_video;
	String ruta_playlist;	
	String ruta_imagen_espera;
	Context contexto;
	
	
	String server_ftp;
	String user_ftp;
	String pass_ftp;
	String ruta_base_servidor;
	String Tiempo_actualizacion;
	int hora_actualizacion;
	int minuto_actualizacion;
	int segundos_actualizacion;
	
	Reproductor reproductorVideo;
	
	private Timer timer ;
	private TimerTask timerTask ;
    private Calendar today;
    TextView texto_descarga;
	
	@Override public void onCreate(Bundle savedInstanceState){		
			
		super.onCreate(savedInstanceState);
		contexto=getApplicationContext();//this;
		try{
			setContentView(R.layout.reproducir_activity);		
			reproductor =(VideoView)findViewById(R.id.videoView);
			
			texto_descarga=(TextView)findViewById(R.id.textViewDescarga);				
			
			ruta_video=getIntent().getExtras().getString("ruta_video");
			ruta_playlist=getIntent().getExtras().getString("ruta_playlist");			
			ruta_imagen_espera=getIntent().getExtras().getString("ruta_imagen_espera");
			
			server_ftp=getIntent().getExtras().getString("server_ftp");
			user_ftp=getIntent().getExtras().getString("user_ftp");
			pass_ftp=getIntent().getExtras().getString("pass_ftp");
			ruta_base_servidor=getIntent().getExtras().getString("ruta_base_servidor");
			Tiempo_actualizacion=getIntent().getExtras().getString("hora_actualizacion");
						 								
			reproductorVideo = new Reproductor(ruta_video+"/",reproductor);
			
			 today = Calendar.getInstance();
		    
						
			
			 Reproducir();
							
			 timerTask = new TimerTask() 
		     { 
				
		         public void run()  
		         { 
		        	 VerificarHoraActualizacion();
		         } 
		     }; 
			
		     
		    
		   
		     timer = new Timer(); 
		     //today.getTime()
		     timer.schedule(timerTask,today.getTime(),3000);//1000 //TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) //60000 un minuto //3600000 hora					     		     	
		
		}catch(Exception e){
			
			Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();		
			
		}	
	 }
		
	
	
	
	
		
	public String retornar_fecha_actual(){
		
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
	
	
	
	
	
	public void Reproducir(){
			
		LectorArchivoJson lector=new LectorArchivoJson();
		Playlist=lector.leerPlaylist(ruta_playlist+ retornar_fecha_actual()+".json");			
					
		//imagenEspera.setImageBitmap(null);
		//imagenEspera.destroyDrawingCache();
		//
				
		reproductorVideo.reproducir(Playlist);
		
	}
	
	public void DescargarPlayListDiario(){	
		
		 Intent i = new Intent(this, ActividadDescargar.class );
			
	     startActivity(i);
		/*ArrayList<String> parametros= new ArrayList<String>();
		parametros.add(ruta_video);
		parametros.add(ruta_playlist);
		parametros.add(server_ftp);
		parametros.add(user_ftp);
		parametros.add(pass_ftp);
		parametros.add(ruta_base_servidor);
		parametros.add(ruta_imagen_espera);
		parametros.add(Tiempo_actualizacion);
		
		new DescargaSegundoPlano(contexto).execute(parametros);
		
		 /*DownloaderNuevoPlaylist archivos = new DownloaderNuevoPlaylist(ruta_video,ruta_playlist,contexto,server_ftp,user_ftp,pass_ftp,ruta_base_servidor);									
		 		
		 archivos.conectarFTP();
		 archivos.descargarPlaylistFTP();
		 archivos.borrarPlaylistAnterior(); 
		 archivos.borrar_videos_inncesarios();
		 archivos.descargar_nuevos_videos();
		 archivos.desconectarFTP();*/
		 			
	}
	
	public void VerificarHoraActualizacion(){
		
		 myHandler.post(myRunnable);
	}
		

	   final Runnable myRunnable = new Runnable() {
	       public void run() {
	    	  	    	 
	    	  String[] separated = Tiempo_actualizacion.split(":");
	    	  hora_actualizacion=Integer.parseInt(separated[0]); 
	    	  minuto_actualizacion=Integer.parseInt(separated[1]); 
	    	  segundos_actualizacion=Integer.parseInt(separated[2]); 
	    	  
	    	  Calendar cal = Calendar.getInstance();
	    	  int hora = cal.get(Calendar.HOUR_OF_DAY);
	    	  int minuto = cal.get(Calendar.MINUTE);
	    	  int segundos=cal.get(Calendar.SECOND);
	    	  if((hora==hora_actualizacion) && (minuto==minuto_actualizacion)&&((segundos>0)&&segundos< (segundos_actualizacion+5))){
	    		
	    		  
	    	  
	    		  reproductorVideo.parar(); 
	    		  DescargarPlayListDiario();
	    		 // File img_btn = new File(ruta_imagen_espera);				
	    		 // Bitmap myBitmap_btn = BitmapFactory.decodeFile(img_btn .getAbsolutePath());
	    		  //imagenEspera.setImageBitmap(myBitmap_btn);	
	    		 // texto_descarga.setText("Descargando Playlist...");
	    		  /*Handler handler = new Handler();
	    		  handler.postDelayed(new Runnable() {
	    			  public void run() {
		            	
	    				  DescargarPlayListDiario();	  
	    				  //texto_descarga.setText("");
		   	    	      //imagenEspera.setImageBitmap(null);
		   	    	      //Reproducir();
		            	 	
	    			  }
	    		  }, 5000);*/
	    	 
	    	  	}
	      	}
	   };
		
}
