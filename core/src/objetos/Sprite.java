package objetos;
/**
 * Representa todo objeto movil
 *
 */
public abstract class Sprite {
	protected float x;
	protected float y;
	/**
	 * Mueve el objeto q posiciones
	 * @param qx cantidad a cambiar en x
	 * @param qy cantidad a cambiar en y
	 */
	public void move(double qx, double qy) {
		x += qx;
		y += qy;
		//TODO
	}
}