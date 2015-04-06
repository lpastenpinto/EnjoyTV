package com.example.enjoytv;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




public class ActividadDescargar extends Activity {
	
	
	private Boolean firstTime = null;
	private ImageView imagenEspera;
	
	  
     String ruta_video;
	 String ruta_playlist;
	 					
	 String ruta_imagen_espera;				
	 String ruta_archivo_configuracion;
	 
	 Context contexto;
	 public static TextView texto_descargar;
	 
	 String server_ftp;
	 String user_ftp;
	 String pass_ftp;
	 String ruta_base_servidor;
	 
	 
	 String hora_actualizacion;
	 MCrypt mcrypt;
	 
	 
	
	@Override public void onCreate(Bundle savedInstanceState){		
		
		
		super.onCreate(savedInstanceState);		
		
		mcrypt = new MCrypt();
		
		contexto=getApplicationContext();
		
		ruta_video=Environment.getExternalStorageDirectory().toString()+"/enjoytv/videos/";
		ruta_playlist=Environment.getExternalStorageDirectory().toString()+"/enjoytv/playlist/";		
		ruta_archivo_configuracion=Environment.getExternalStorageDirectory().toString()+"/enjoytv/";
		
		
		
		if(isFirstTime(contexto)){
			
			 Intent i = new Intent(this, ActividadConfiguracion.class );					
			 i.putExtra("ruta_archivo_configuracion", ruta_archivo_configuracion);			
			 startActivity(i);
			 finish();
			 
		}else{			
			
			try{
				setContentView(R.layout.descargar_activity);
				
				LeerConfiguracion(ruta_archivo_configuracion);
				
				
				imagenEspera=(ImageView) findViewById(R.id.imageViewEspera);
				texto_descargar=(TextView) findViewById(R.id.textDescarga);
				
				File img_btn = new File(ruta_imagen_espera);
				
				Bitmap myBitmap_btn = BitmapFactory.decodeFile(img_btn.getAbsolutePath());
				imagenEspera.setImageBitmap(myBitmap_btn);
				
							
									
				Handler handler = new Handler();
		        handler.postDelayed(new Runnable() {
		            public void run() {
		            	try{		            	
		            			DescargarPlaylistDelDia();		            					            	
		            		
		            	}catch(Exception e){
		            		
		            		if (Build.VERSION.SDK_INT >= 11) {
		            		    recreate();
		            		} else {
		            		    Intent intent = getIntent();
		            		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            		    finish();		            		   
		            		    startActivity(intent);
		            		
		            		}
		            	}
		            }
		        }, 3000);

		        
		        
		        
			}catch(Exception e){
			
				Toast.makeText(getApplicationContext(), 
	                    "Error: Planifique Playlist del Dia", Toast.LENGTH_LONG).show();
				 IniciarActividadReproducir();
			}
												
		} 			 			
		
	 }
	
	
	
	
	
	
	private boolean isFirstTime(Context context) {
		if (firstTime == null) {
			SharedPreferences mPreferences = this.getSharedPreferences("first_time", context.MODE_PRIVATE);
			firstTime = mPreferences.getBoolean("firstTime", true);
			if (firstTime) { 
			    SharedPreferences.Editor editor = mPreferences.edit();
			    editor.putBoolean("firstTime", false);
			    editor.commit();
			}
		}
		return firstTime;
	}
	
	
	
	
	
	public void DescargarPlaylistDelDia(){
									
			ArrayList<String> parametros= new ArrayList<String>();
			parametros.add(ruta_video);
			parametros.add(ruta_playlist);
			parametros.add(server_ftp);
			parametros.add(user_ftp);
			parametros.add(pass_ftp);
			parametros.add(ruta_base_servidor);
			parametros.add(ruta_imagen_espera);
			parametros.add(hora_actualizacion);
			new DescargaSegundoPlano(contexto).execute(parametros);	    						
	    
	}
	
	
	
	public void LeerConfiguracion(String ruta){
		ManejoArchivosTxt lector = new ManejoArchivosTxt(ruta);		
		ArrayList<String> listConfiguracionFTP = new ArrayList<String>();
			
		listConfiguracionFTP = lector.lector_archivo_conf(ruta_archivo_configuracion+"configuracion.txt");
		try{
			String Servdecrypted = new String( mcrypt.decrypt( listConfiguracionFTP.get(0) ) );
			String Userdecrypted = new String( mcrypt.decrypt( listConfiguracionFTP.get(1) ) );
			String Passdecrypted = new String( mcrypt.decrypt( listConfiguracionFTP.get(2)) );
			String Basedecrypted = new String( mcrypt.decrypt( listConfiguracionFTP.get(3) ) );
			String Imagendecrypted = new String( mcrypt.decrypt( listConfiguracionFTP.get(4)) );
			String Horadecrypted = new String( mcrypt.decrypt( listConfiguracionFTP.get(5)) );
			
			ruta_imagen_espera=Environment.getExternalStorageDirectory().toString()+"/enjoytv/imagen/"+Imagendecrypted;//listConfiguracion.get(1);
			server_ftp = Servdecrypted;//"ftp.elquiweb.net";	    
		    user_ftp = Userdecrypted;//"elquiweb";
		    pass_ftp = Passdecrypted;//"LG2nvrD92+n}";
			ruta_base_servidor=Basedecrypted;//"/public_html/enjoytv/";
			hora_actualizacion=Horadecrypted;//"12:00:00";
			
		
		}catch(Exception e){
			
			Toast.makeText(getApplicationContext(), 
                    "Error al leer archivo de configuracion. Compruebe formato correcto.", Toast.LENGTH_LONG).show();
			finish();
		}
		
		
	}
	
	
	public void IniciarActividadReproducir(){
		
		 Intent i = new Intent(this, ActividadReproducirPlaylist.class );
		 i.putExtra("ruta_video", ruta_video);
		 i.putExtra("ruta_playlist", ruta_playlist);		
		 i.putExtra("ruta_imagen_espera", ruta_imagen_espera);			 
		 i.putExtra("server_ftp", server_ftp);
		 i.putExtra("user_ftp", user_ftp);
		 i.putExtra("pass_ftp", pass_ftp);
		 i.putExtra("ruta_base_servidor",  ruta_base_servidor);
		 i.putExtra("hora_actualizacion",  hora_actualizacion);
		 
	     startActivity(i);
		
	}
			
		
}
/*
 * 	server_ftp = Servdecrypted;//"ftp.elquiweb.net";	    
		    user_ftp = Userdecrypted;//"elquiweb";
		    pass_ftp = Passdecrypted;//"LG2nvrD92+n}";
			ruta_base_servidor=Basedecrypted;//"/public_html/enjoytv/";
			hora_actualizacion=Horadecrypted;//"12:00:00";*/

