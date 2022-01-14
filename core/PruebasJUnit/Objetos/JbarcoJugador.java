package Objetos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import objetos.Bala;
import objetos.Isla;
import objetos.Municion;
import objetos.barcos.Barco;
import objetos.barcos.BarcoJugador;

public class JbarcoJugador {
	
	BarcoJugador bj = new BarcoJugador(0, 0, 3, 2, Municion.NORMAL, 10);
	
	@Test
	public void nivel() {//TODO hacer cuando funcionenen los botines
		assertEquals(2,bj.getNivelDelBarco());
		bj.setNivelDelBarco(3);
		assertEquals(3,bj.getNivelDelBarco());
	}
	
	@Test
	public void Islas() {//TODO hacer cuando funcionenen los botines
		Isla ip = new Isla(0, 0, 0, 0, 10, false);
		if(ip.getBarcos()==null) {
			ip.conquistar(bj);
		}
		assertEquals(ip.getBotin(),bj.getDineros());
		
		
	}

}
