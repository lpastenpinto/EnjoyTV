package com.example.enjoytv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class Main extends Activity {
	
	
	
	
	@Override public void onCreate(Bundle savedInstanceState){		
		
		
		super.onCreate(savedInstanceState);
		
		 Intent i = new Intent(this, ActividadDescargar.class );
		
	     startActivity(i);
		
	 }
	
	
	
	
}

