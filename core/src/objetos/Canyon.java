package objetos;

import java.util.logging.Logger;

/**
 * Esta clase representa un cañon de un {@link objetos.Barco Barco}
 *
 */
public class Canyon extends Mejoras{
	private static Logger loggerCanyon = Logger.getLogger("Canyon");
	/** Cañones que usaran los barcos del juego
	 * @param nivel Nivel necesario para adquirir el cañon
	 * @param precio Precio necesario para comprar el cañon
	 * @param x Posicion en X
	 * @param y Posicion en Y
	 */
	public Canyon(int nivel, int precio) {
		super(nivel, precio);
	}
	public void disparar(Municion m, float posX, float posY, float angle) {
		
	}
}
