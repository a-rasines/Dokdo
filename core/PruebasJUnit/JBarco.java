import static org.junit.Assert.assertEquals;
import org.junit.Test;

import objectos.barcos.Barco;
import objetos.Municion;

public class JBarco {
	
	private Barco b1 = new Barco(5, 1, 0, 3, Municion.EXPLOSIVA);
	
	@Test
	public void Vida() {
		b1.setVidaDelBarco(3);
		assertEquals(3,b1.getVidaDelBarco());
	}
	
	@Test
	public void getNivel() {
		assertEquals(b1.getNivelDelBarco(), 1);
		b1.setNivelDelBarco(5);
		assertEquals(b1.getNivelDelBarco(),5);
	}
	
	@Test
	public void getCoordenadas() {
		assertEquals(0,b1.getX(), 0);
		assertEquals(3,b1.getY(), 3);
	}
	
	@Test
	public void municion() {
		assertEquals("Explosiva", Municion.EXPLOSIVA , b1.getMunicionEnUso());
		b1.setMunicionEnUso(Municion.NORMAL);
		assertEquals("Explosiva", Municion.NORMAL , b1.getMunicionEnUso());
	}

}
