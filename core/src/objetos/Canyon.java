package objetos;

import java.util.logging.Logger;


import dg.main.MainScreen;

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
	
	/** FALTA ARREGLARLO
	 * @param m
	 * @param posX
	 * @param posY
	 * @param angle
	 */
	public void disparar(Municion m, float posX, float posY, float angle) {
		MainScreen.balasDisparadas.add(new Bala(posX, posY, 10f, angle, m.getDanyo()));
	}
}