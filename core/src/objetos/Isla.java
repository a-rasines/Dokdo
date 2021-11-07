package objetos;

import java.util.ArrayList;
/**
 * Representa las islas en el mapa
 *
 */
public class Isla {
	protected float posicionEnX;
	protected float posicionEnY;
	protected int nivelRecomendado;
	protected ArrayList<Barco> barcos;
	protected int botin;
	/** Islas generales del juego.
	 * @param posX Posicion en X de la isla
	 * @param posY Posicion en Y de la isla
	 * @param nivel Nivel recomendado para ser invadida
	 * @param barcosProtegiendo Cantidad de barcos protegiendo la lista
	 * @param botin Botin que dara la isla
	 */
	public Isla(float posX, float posY, int nivel, ArrayList<Barco> barcosProtegiendo, int botin) {
		this.posicionEnX=posX;
		this.posicionEnY=posY;
		this.nivelRecomendado=nivel;
		this.barcos=barcosProtegiendo;
		this.botin=botin;
	}
	public float getPosicionEnX() {
		return posicionEnX;
	}
	public void setPosicionEnX(float posicionEnX) {
		this.posicionEnX = posicionEnX;
	}
	public float getPosicionEnY() {
		return posicionEnY;
	}
	public void setPosicionEnY(float posicionEnY) {
		this.posicionEnY = posicionEnY;
	}
	public int getNivelRecomendado() {
		return nivelRecomendado;
	}
	public void setNivelRecomendado(int nivelRecomendado) {
		this.nivelRecomendado = nivelRecomendado;
	}
	public ArrayList<Barco> getBarcos() {
		return barcos;
	}
	public void setBarcos(ArrayList<Barco> barcos) {
		this.barcos = barcos;
	}
	public int getBotin() {
		return botin;
	}
	public void setBotin(int botin) {
		this.botin = botin;
	}
}
