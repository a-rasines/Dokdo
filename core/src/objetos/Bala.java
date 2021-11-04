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
	public Bala(float x0, float y0, float vel, float angulo, int daño) {
		super.x = x0;
		super.y =  y0;
		super.v = vel;
		super.angle = angulo;
		this.daño = daño;
		//TODO hacer el bucle de movimiento
	}
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ GETTERS/SETTERS ▓▓▓▓▓▓▓▓▓▓ 
	 */
	public int getDaño() {
		return daño;
	}
	
}
