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
	Barco p1 = new BarcoJugador(0, 0, 3, 0, Municion.NORMAL, 0);
	Barco p2 = new BarcoJugador(0, 0, 3, 0, Municion.INCENDIARIA, 0);
	Barco p3 = new BarcoJugador(0, 0, 3, 0, Municion.EXPLOSIVA, 0);
	Barco p4 = new BarcoJugador(0, 0, 3, 0, Municion.VENENOSA, 0);
	Barco p5 = new BarcoJugador(0, 0, 3, 0, Municion.ENCADENADA, 0);
	
	@Test
	public void municion() {
		assertEquals(Municion.NORMAL,bE.getMunicionEnUso());
	}
	
	@Test
	public void recibeDanyoN() {//TODO revisar despues de correción de eneko
		Bala bal = new Bala(0, 0, 0, 0, p1.getMunicionEnUso().getDanyo(), p1);
		bE.setVidaDelBarco(bE.getVidaDelBarco()-bal.getDanyo());
		assertEquals(2,bE.getVidaDelBarco(),2);
	}
	
	@Test
	public void recibeDanyoE() {//TODO revisar despues de correción de eneko
		Bala bal = new Bala(0, 0, 0, 0, p3.getMunicionEnUso().getDanyo(), p3);
		bE.setVidaDelBarco(bE.getVidaDelBarco()-bal.getDanyo());
		assertEquals(1,bE.getVidaDelBarco(),1);
	}
	
}
