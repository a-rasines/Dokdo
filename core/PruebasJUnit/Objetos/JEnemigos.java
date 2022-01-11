package Objetos;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import objetos.Bala;
import objetos.Municion;
import objetos.barcos.Barco;
import objetos.barcos.BarcoEnemigo;
import objetos.barcos.BarcoJugador;

public class JEnemigos {
	
	BarcoEnemigo bE = new BarcoEnemigo(3, 1, 0, 0, false, Municion.NORMAL);
	BarcoJugador p1 = new BarcoJugador(0, 0, 3, 0, Municion.INCENDIARIA);
	
	@Test
	public void municion() {
		assertEquals(Municion.NORMAL,bE.getMunicionEnUso());
	}
	
	@Test
	public void recibeDanyo() {//TODO revisar despues de correción de eneko
		Bala bal = new Bala(0, 0, 0, 0, 0, p1);
		//bE.getVidaDelBarco();
		System.out.println(bal.getDanyo());
	}
}
