package objetos;

import com.badlogic.gdx.graphics.Texture;

/**
 * Representa todo objeto movil
 *
 */
public abstract class Sprite {
	public static Texture tMap;
	protected float x = 0;
	protected float y = 0;
	protected float v = 0; //velocidad
	protected float angle = 0;
	/**
	 * Mueve el objeto por velocidad en el ángulo especificado
	 */
	public void move() {
		move((float)(v*Math.cos(angle)),(float)(v*Math.sin(angle)));
	}
	/**
	 * Mueve el objeto coordenadas personalizadas
	 * @param x movimiento en x
	 * @param y movimiento en y
	 */
	public void move(float x, float y) {
		this.x+=x;
		this.y+=y;
	}
	/**
	 * Rota el objeto q grados
	 * @param q grados a girar
	 */
	public void rotate(double q) {
		angle = (float) ((angle + q)%360);
	}
	/**
	 * Hace un print de la posición y la orientación del objeto
	 */
	public void printPos() {
		System.out.println("( "+x+" , "+y+" ) "+angle+"º, v="+v);
	}
}
