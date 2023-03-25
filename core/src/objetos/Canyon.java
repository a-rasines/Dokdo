package objetos;

import dg.main.MainScreen;
import objetos.barcos.Barco;

/**
 * Esta clase representa un cañon de un {@link objetos.barcos.Barco Barco}
 *
 */
public class Canyon extends Mejoras{
	/** Cañones que usaran los barcos del juego
	 * @param nivel Nivel necesario para adquirir el cañon
	 * @param precio Precio necesario para comprar el cañon
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
	public void disparar(Municion m, float posX, float posY, float angle, Barco barco) {
		MainScreen.balasDisparadas.add(new Bala(posX, posY, 10f, angle, m.getDanyo(), barco));
	}
}