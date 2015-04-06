package com.example.enjoytv;
import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.VideoView;

public class Reproductor extends Activity  {
	VideoView VideoReproductor;
	private int tamano;
	private int tamano_list;
	private ArrayList<String> PlayList;	
	
	String ruta_videos;
	public Reproductor(String ruta_vid, VideoView v){
		ruta_videos=ruta_vid;
		VideoReproductor=v;	
	}
	
	public void reproducir(ArrayList<String> list){
		
		PlayList=list;
			
		tamano=0;
		tamano_list=PlayList.size();
		
		try{
			VideoReproductor.setVideoPath(ruta_videos+PlayList.get(0));		
			VideoReproductor.start();
		}catch(Exception e){
			
			File file = new File(ruta_videos+"/"+PlayList.get(0));
    		file.delete();
			PlayList.remove(0);
			tamano_list=tamano_list-1;
			
			
			VideoReproductor.setVideoPath(ruta_videos+PlayList.get(0));		
			VideoReproductor.start();
			
		}
	
	
		VideoReproductor.setOnPreparedListener (new OnPreparedListener() {                    
			@Override
			public void onPrepared(MediaPlayer mp) {
				//mp.setLooping(true);
			}
		});
		
		VideoReproductor.setOnErrorListener(new OnErrorListener () {
		    @Override
		    public boolean onError(MediaPlayer mp, int what, int extra) {
		    	File file = new File(ruta_videos+"/"+PlayList.get(tamano));
	    		file.delete();
				PlayList.remove(tamano);
				tamano_list=tamano_list-1;
				if(tamano<(tamano_list-1)){
					VideoReproductor.setVideoPath(ruta_videos+PlayList.get(tamano));				
					VideoReproductor.start();
				}else{
					tamano=0;
					VideoReproductor.setVideoPath(ruta_videos+PlayList.get(tamano));				
					VideoReproductor.start();
				}				
		    	
		        return true;
		        
		    }
		});
	
		VideoReproductor.setOnCompletionListener(new OnCompletionListener() {
			@Override
			  public void onCompletion(MediaPlayer mp) {
			//	mp.reset();
				//reproductor.stopPlayback();
				//finish();
				if(tamano<(tamano_list-1)){
					tamano=tamano+1;
					try{
						VideoReproductor.setVideoPath(ruta_videos+PlayList.get(tamano));				
						VideoReproductor.start();
					}catch(Exception e){
						File file = new File(ruta_videos+"/"+PlayList.get(tamano));
			    		file.delete();
						PlayList.remove(tamano);
						tamano_list=tamano_list-1;
												
						VideoReproductor.setVideoPath(ruta_videos+PlayList.get(tamano));				
						VideoReproductor.start();
					}					
				}else{
					/*posicion_playlist++;
					PlayList=(ArrayList<String>) PlayListTotal.get(posicion_playlist);
					tamano=0;
					tamano_list=PlayList.size();
					
					VideoReproductor.setVideoPath(PlayList.get(0));		
					VideoReproductor.start();*/
					tamano=0;
					VideoReproductor.setVideoPath(ruta_videos+PlayList.get(tamano));				
					VideoReproductor.start();
					
				}													
			 }
		});		
	}
	
	public void parar(){
	    VideoReproductor.stopPlayback();
		//VideoReproductor.clearAnimation();
				
	}
}
