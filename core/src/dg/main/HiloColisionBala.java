package dg.main;

import java.util.ArrayList;

import objetos.Bala;
import objetos.Barco;

public class HiloColisionBala extends Thread{
	private ArrayList<Barco> barcoEnem;
	private Bala balaDisp;
	/** Hilo que comprobara la colision entre una bala disparada y los barcos del juego
	 * @param barcosEnemigos
	 * @param balaDisparada
	 */
	public HiloColisionBala(ArrayList<Barco> barcosEnemigos, Bala balaDisparada) {
		this.barcoEnem=barcosEnemigos;
		this.balaDisp=balaDisparada;
	}
	public ArrayList<Barco> getbComp() {
		return barcoEnem;
	}
	public void setbComp(ArrayList<Barco> bComp) {
		this.barcoEnem = bComp;
	}
	public Bala getbDisp() {
		return balaDisp;
	}
	public void setbDisp(Bala bDisp) {
		this.balaDisp = bDisp;
	}
	public void run() {
		if (balaDisp.collidesWith(barcoEnem)) {
			for(Barco b :barcoEnem) {
				if(balaDisp.collidesWith(b)) {
					b.setVidaDelBarco(b.getVidaDelBarco()-balaDisp.getDanyo());
					//FALTA FORMA DE HACER BORRAR LA BALA.
					break;
				}
			}
		}
	}
}
