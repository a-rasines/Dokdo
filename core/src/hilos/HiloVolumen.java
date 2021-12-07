package hilos;

import java.awt.desktop.ScreenSleepEvent;
import java.util.logging.Logger;

import dg.main.AudioPlayer;
import objetos.Sprite;
import objetos.barcos.Barco;

public class HiloVolumen extends Thread {
	private AudioPlayer selCancion;
	private float vDestino;
	private boolean DirVol;
	private boolean cambios=false;
	private static Logger logger= Logger.getLogger("MenuOp");
	private static String audio;
	
	/**public HiloVolumen(boolean subirObajar, int v) {
		this.vDestino=v;
		this.x=subirObajar;		
	}**/
	public void setvDestino(float v){
		this.vDestino=v;
	}
	
	
	public AudioPlayer getSelCancion() {
		return this.selCancion;
	}


	public void setSelCancion(AudioPlayer selCancion) {
		this.selCancion = selCancion;
	}


	/**
	 * Se usa para definir si se sube o se baja el volumen
	 * @param sOb true para bajar, false para subir
	 */
	public void setDireccion(boolean sOb){
		this.DirVol=sOb;
	}
	
	public void setCambios(boolean SiNo){
		this.cambios=SiNo;
	}
	
	public void setFichero(String f) {
		audio = f;
	}
		
	public void run() {
		//AudioPlayer.Reproducir(audio);
		while(true){
			if(cambios) {
				if(DirVol) {					
					cambios=bajarVolumen(this.vDestino);
				}else {
					cambios=subirVolumen(this.vDestino);
				}				
			}	
		}
	}
	

	public boolean bajarVolumen(float v ) {
		for(float i = AudioPlayer.getVolumen();i>v;i-=0.01) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo detener el hilo");
			}
			AudioPlayer.setVolumen(i);
		}System.out.println("Acabado bajada");
		return false;
	}
	public boolean subirVolumen(float v) {
		for(float i = AudioPlayer.getVolumen();i<v;i+=0.01) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo detener el hilo");
			}
			AudioPlayer.setVolumen(i);
		}System.out.println("Acabado Subida");
		return false;
	}

	
	
}