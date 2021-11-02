package objetos;

import java.util.ArrayList;
import java.util.HashMap;

public class Barco {
	public static enum Tipo{};
	public enum PosicionCañon{IZQUIERDA,DERECHA,DELANTE,ATRAS};
	protected int vidaDelBarco;
	protected float posicionEnX;
	protected float posicionEnY;
	protected int nivelDelBarco;
	protected Municion municionEnUso;
	protected HashMap<PosicionCañon,ArrayList<Cañon>> cañonesEnElBarco;
	/** Barcos del juego
	 * @param vida Vida actual del barco
	 * @param nivel Nivel actual del barco
	 * @param posX Posicion en X del barco
	 * @param posY Posicion en Y del barco
	 * @param municionActual Municion que esta usando el barco
	 */
	public Barco(int vida, int nivel, float posX, float posY, Municion municionActual) {
		this.vidaDelBarco=vida;
		this.nivelDelBarco=nivel;
		this.posicionEnX=posX;
		this.posicionEnY=posY;
		this.municionEnUso=municionActual;
	}
	public int getVidaDelBarco() {
		return vidaDelBarco;
	}
	public void setVidaDelBarco(int vidaDelBarco) {
		this.vidaDelBarco = vidaDelBarco;
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
	public int getNivelDelBarco() {
		return nivelDelBarco;
	}
	public void setNivelDelBarco(int nivelDelBarco) {
		this.nivelDelBarco = nivelDelBarco;
	}
	public Municion getMunicionEnUso() {
		return municionEnUso;
	}
	public void setMunicionEnUso(Municion municionEnUso) {
		this.municionEnUso = municionEnUso;
	}
}
