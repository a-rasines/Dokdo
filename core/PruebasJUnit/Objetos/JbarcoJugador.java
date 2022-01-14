package Objetos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import objetos.Bala;
import objetos.Municion;
import objetos.barcos.Barco;
import objetos.barcos.BarcoJugador;

public class JbarcoJugador {
	
	BarcoJugador bj = new BarcoJugador(0, 0, 3, 0, Municion.NORMAL);
	
	@Test
	public void nivel() {//TODO hacer cuando funcionenen los botines
		assertEquals(0,bj.getNivelDelBarco());
		bj.setNivelDelBarco(3);
		assertEquals(3,bj.getNivelDelBarco());
		
		
	}

}
