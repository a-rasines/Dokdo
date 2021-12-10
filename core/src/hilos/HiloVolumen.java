package hilos;

import java.awt.desktop.ScreenSleepEvent;
import java.util.logging.Logger;

import dg.main.AudioPlayer;
import objetos.Sprite;
import objetos.barcos.Barco;

public class HiloVolumen extends Thread {
	private static HiloVolumen instance;
	public static HiloVolumen getInstance() {
		if(instance == null) instance = new HiloVolumen();
		return instance;
	}
	
	
	private AudioPlayer selCancion;
	private float vDestino;
	private boolean DirVol;
	private boolean cambios=false;
	private static Logger logger= Logger.getLogger("MenuOp");
	
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
	
		
	public void run() {
		//AudioPlayer.Reproducir(audio);
		while(true){
			
			if(cambios) {
				System.out.println("se cami");
				if(DirVol) {					
					cambios=bajarVolumen(this.vDestino, this.selCancion);
				}else {
					cambios=subirVolumen(this.vDestino,this.selCancion);
				}				
			}	
		}
	}
	

	public boolean bajarVolumen(float v, AudioPlayer c ) {
		for(float i = c.getVolumen();i>v;i-=0.01) {
			System.out.println("bajo");
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo detener el hilo");
			}
			c.setVolumen(i);
		}System.out.println("Acabado bajada");
		return false;
	}
	public boolean subirVolumen(float v, AudioPlayer c ) {
		c.setVolumen(0f);
		for(float i = c.getVolumen();i<v;i+=0.01) {
			System.out.println("subo");
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo detener el hilo");
			}
			c.setVolumen(i);
		}System.out.println("Acabado Subida");
		return false;
	}

	
	
}