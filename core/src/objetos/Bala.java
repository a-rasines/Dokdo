package objetos;
/**
 * Representa una bala disparada por un {@link objetos.Cañon}.
 *
 */
public class Bala extends Sprite{
	private int danyo;
	/**
	 * Bala disparada por un {@link objetos.Cañon Cañon}
	 * @param x0 x inicial
	 * @param y0 y inicial
	 * @param saltoX salto en X cada vuelta
	 * @param saltoY salto en Y cada vuelta
	 * @param daño daño que ocasiona al colisionar
	 */
	public Bala(float x0, float y0, float vel, float angulo, int danyo) {
		super(x0, y0, vel, angulo, 8, 8);
		this.danyo = danyo;
		//TODO hacer el bucle de movimiento
	}
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ GETTERS/SETTERS ▓▓▓▓▓▓▓▓▓▓ 
	 */
	public int getDanyo() {
		return danyo;
	}
	
}
