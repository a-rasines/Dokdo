package pack;

import com.badlogic.gdx.Gdx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import objetos.Barco;

public class BarcoTest {
	public static Barco b = new Barco(10,0,0,0,null);
	
	@Test
	public void testVida() {
		int vida = b.getVidaDelBarco();
		assertEquals(0, vida);
	}
	
	@Test
	public void testNivel() {
		int nivel = b.getNivelDelBarco();
		assertEquals(0,nivel);
	}
	
	@Test
	public void testPosX() {
		float x = b.getX();
		assertEquals(0, x);
	}
	
	@Test
	public void TestPosY() {
		float y = b.getY();
		assertEquals(0,y);
	}

}
