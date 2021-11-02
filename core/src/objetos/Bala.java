package objetos;
/**
 * Representa una bala disparada por un {@link objetos.Cañon}.
 *
 */
public class Bala extends Sprite{
	private double vX;
	private double vY;
	private int daño;
	/**
	 * Bala disparada por un {@link objetos.Cañon Cañon}
	 * @param x0 x inicial
	 * @param y0 y inicial
	 * @param saltoX salto en X cada vuelta
	 * @param saltoY salto en Y cada vuelta
	 * @param daño daño que ocasiona al colisionar
	 */
	public Bala(double x0, double y0, double saltoX, double saltoY, int daño) {
		super.x = x0;
		super.y = y0;
		vX = saltoX;
		vY = saltoY;
		this.daño = daño;
		//TODO hacer el bucle de movimiento
	}
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ GETTERS/SETTERS ▓▓▓▓▓▓▓▓▓▓ 
	 */
	
	public double getvX() {
		return vX;
	}
	public void setvX(double vX) {
		this.vX = vX;
	}
	public double getvY() {
		return vY;
	}
	public void setvY(double vY) {
		this.vY = vY;
	}
	public int getDaño() {
		return daño;
	}
	
}
