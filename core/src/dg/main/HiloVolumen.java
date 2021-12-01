package dg.main;

import java.awt.desktop.ScreenSleepEvent;

import objetos.Sprite;
import objetos.barcos.Barco;

public class HiloVolumen extends Thread {
	private float vDestino;
	private boolean x;
	
	/**public HiloVolumen(boolean subirObajar, int v) {
		this.vDestino=v;
		this.x=subirObajar;		
	}**/
	public void setvDestino(float v){
		this.vDestino=v;
	}
	public void setDireccion(boolean sOb){
		this.x=sOb;
	}
	
	public void run() {
		System.out.println(x);
		if(x==true) {
			bajarVolumen(this.vDestino);
		}else {
			System.out.println("prueba");
			subirVolumen(this.vDestino);
		}
		System.out.println("acabado");
		Thread.currentThread().interrupt();
	}

	public void bajarVolumen(float v) {
		for(float i = AudioPlayer.getVolumen();i>v;i-=0.1) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo detener el hilo");
			}
			AudioPlayer.setVolumen(i);
		}System.out.println("Acabado bajada");
	}
	public void subirVolumen(float v) {
		for(float i = AudioPlayer.getVolumen();i<v;i+=0.1) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo detener el hilo");
			}
			AudioPlayer.setVolumen(i);
		}System.out.println("Acabado Subida");
	}

	
	
}