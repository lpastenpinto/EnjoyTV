package com.example.enjoytv;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ActividadConfiguracion  extends Activity {

	TextView servtxt;
	TextView usertxt;
	TextView passtxt;
	TextView basetxt;
	TextView imagentxt;
	TextView horatxt;
	
	String ruta_archivo_configuracion;
	
	EditText editServer;
	EditText editUser;
	EditText editPass;
	EditText editImagen;
	EditText editHora;
	EditText editbaseServ;
	
	Button boton_guardar;
	Button salir;
	
	String ruta_servidor;
	String nombre_imagen_espera;
	String pass_servidor;
	String user_servidor;
	String hora_actualizar;
	String base_servidor;
	MCrypt mcrypt;
	
	@Override public void onCreate(Bundle savedInstanceState){		
			
		super.onCreate(savedInstanceState);
		
		
		mcrypt = new MCrypt();
		
		setContentView(R.layout.configuracion_activity);
		ruta_archivo_configuracion=getIntent().getExtras().getString("ruta_archivo_configuracion");
		
		editServer=(EditText) findViewById(R.id.editTextServ);
		editUser=(EditText) findViewById(R.id.editTextUserServ);
		editPass=(EditText) findViewById(R.id.editTextPassServ);
		editImagen=(EditText) findViewById(R.id.editTextRutaImage);
		editHora=(EditText) findViewById(R.id.editTextTime);		
		editbaseServ=(EditText) findViewById(R.id.editTextBaseServ);
		
		
		servtxt=(TextView) findViewById(R.id.textViewServ);
		usertxt=(TextView) findViewById(R.id.textViewUser);
		passtxt=(TextView) findViewById(R.id.textViewPass);
		basetxt=(TextView) findViewById(R.id.textViewBase);
		imagentxt=(TextView) findViewById(R.id.textViewImagen);
		horatxt=(TextView) findViewById(R.id.textViewHora);
		
		boton_guardar=(Button) findViewById(R.id.buttonSave);
		LeerConfiguracion(ruta_archivo_configuracion);
		
		salir=(Button) findViewById(R.id.buttonExit);
		boton_guardar.setOnClickListener(new OnClickListener() {
			 
			  public void onClick(View arg0) {
				  
					
						GuardarConfiguracion(ruta_archivo_configuracion+"configuracion.txt");
					
				
			  }
		});
		salir.setOnClickListener(new OnClickListener() {
			 
			  public void onClick(View arg0) {
				  
				  
					  IniciarActividadDescargar();
				  	
				
			  }
		});
		
	
	}
	
	
	
	
	
	
	
	
	
	
	public void LeerConfiguracion(String ruta){
		ManejoArchivosTxt lector = new ManejoArchivosTxt(ruta);
		ArrayList<String> listConfiguracion = new ArrayList<String>();
		listConfiguracion = lector.lector_archivo_conf(ruta_archivo_configuracion+"configuracion.txt");
		
		if(listConfiguracion.size()>0){
			try{
				String Servdecrypted = new String( mcrypt.decrypt( listConfiguracion.get(0) ) );
				String Userdecrypted = new String( mcrypt.decrypt( listConfiguracion.get(1) ) );
				String Passdecrypted = new String( mcrypt.decrypt( listConfiguracion.get(2)) );
				String Basedecrypted = new String( mcrypt.decrypt( listConfiguracion.get(3) ) );
				String Imagendecrypted = new String( mcrypt.decrypt( listConfiguracion.get(4)) );
				String Horadecrypted = new String( mcrypt.decrypt( listConfiguracion.get(5)) );
				
				editServer.setText(Servdecrypted);
				editUser.setText(Userdecrypted);
				editPass.setText(Passdecrypted);
				editbaseServ.setText(Basedecrypted );
				editImagen.setText(Imagendecrypted);
				editHora.setText(Horadecrypted );
			}catch(Exception ex){
				
				Toast.makeText(this, 
		                  ""+ex, Toast.LENGTH_LONG).show();
			}
			
		}
		
	
	}
	
	
	
	public ArrayList<String>  LeerConfiguracionFTP(String ruta){
		
		ManejoArchivosTxt lector = new ManejoArchivosTxt(ruta);
		ArrayList<String> listConfiguracion = new ArrayList<String>();
		listConfiguracion = lector.lector_archivo_conf(ruta_archivo_configuracion+"configuracion.txt");
		
		ArrayList<String> ConfiguracionFTP = new ArrayList<String>();
		if(listConfiguracion.size()>0){
			try{
				String Servdecrypted = new String( mcrypt.decrypt( listConfiguracion.get(0) ) );
				String Userdecrypted = new String( mcrypt.decrypt( listConfiguracion.get(1) ) );
				String Passdecrypted = new String( mcrypt.decrypt( listConfiguracion.get(2)) );
				String Basedecrypted = new String( mcrypt.decrypt( listConfiguracion.get(3) ) );
				String Imagendecrypted = new String( mcrypt.decrypt( listConfiguracion.get(4)) );
				String Horadecrypted = new String( mcrypt.decrypt( listConfiguracion.get(5)) );
				
				ConfiguracionFTP.add(Servdecrypted);
				ConfiguracionFTP.add(Userdecrypted);
				ConfiguracionFTP.add(Passdecrypted);
				ConfiguracionFTP.add(Basedecrypted);
				ConfiguracionFTP.add(Imagendecrypted);
				ConfiguracionFTP.add(Horadecrypted);
			}catch(Exception ex){
				
				Toast.makeText(this, 
		                  ""+ex, Toast.LENGTH_LONG).show();
				return ConfiguracionFTP;
			}
			
		}
		
		return ConfiguracionFTP;
	}
	
	
	
	public void IniciarActividadDescargar(){
		try{
			Intent i = new Intent(this, ActividadDescargar.class );
			this.finish();		
			startActivity(i);
		}catch(Exception ex){
			
			Toast.makeText(this, 
	                  ""+ex, Toast.LENGTH_LONG).show();
		}
	}
	
	
	
	
	
	public void GuardarConfiguracion(String ruta_archivo) {
		  
		try {
			
			
			ruta_servidor=editServer.getText().toString();
			nombre_imagen_espera=editImagen.getText().toString();
			pass_servidor=editPass.getText().toString();
			user_servidor=editUser.getText().toString();
			hora_actualizar=editHora.getText().toString();
			base_servidor=editbaseServ.getText().toString();
			
			if(!(ruta_servidor=="")&&!(nombre_imagen_espera=="")&& !(pass_servidor=="")&&!( user_servidor=="")&&!( hora_actualizar=="")&&!( base_servidor==""))
			{
				//ManejoArchivosTxt archivoconfiguracion = new ManejoArchivosTxt();
				//boolean verificar_guardado=archivoconfiguracion.GuardarArchivoConfiguracion(ruta_archivo,ruta_servidor,user_servidor,pass_servidor,base_servidor,hora_actualizar,nombre_imagen_espera);
				if(!(hora_actualizar.length()==6)){
					
					
				
					String saltiLinea="\r\n";
					File file = new File(ruta_archivo);
					FileOutputStream fos = new FileOutputStream(file);
				
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
					Toast.makeText(this, 
			                   "Guardado con exito!", Toast.LENGTH_LONG).show();
					fos.close();
				}else{
					
					Toast.makeText(this, 
			                   "El formato de la hora esta incorrecto.", Toast.LENGTH_LONG).show();
				}
	        
	        
			}else{
				 Toast.makeText(this, 
		                   "No debe estar ningun campo vacio..", Toast.LENGTH_LONG).show();
			}
		} catch (FileNotFoundException e) {
			 Toast.makeText(this, 
                  "nose encuentra archivo!", Toast.LENGTH_LONG).show();
			//e.printStackTrace();
		}catch(IOException error){
			Toast.makeText(this, 
	                  "no se puede escribir", Toast.LENGTH_LONG).show();
			
		}catch(Exception ex){
			Toast.makeText(this, 
	                  ex+"", Toast.LENGTH_LONG).show();
			
		}
		
		
             
		
	}

}
