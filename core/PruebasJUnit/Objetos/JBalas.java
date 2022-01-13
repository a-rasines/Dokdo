package Objetos;


import static org.junit.Assert.assertEquals;
import org.junit.Test;


import objetos.Bala;
import objetos.barcos.Barco;

public class JBalas {
	private Barco bar=new Barco(0, 0, 0, 0);
	private Bala b1 = new Bala(1,2,3,4,5, bar); 

	@Test
	public void getDanyo() {
		assertEquals(5,b1.getDanyo());
	}
	
	@Test
	public void getAngle() {
		assertEquals(4,b1.getAngle(),4);
	}
	
	@Test
	public void getX() {
		assertEquals(1,b1.getX(),1);
	}
	
	@Test
	public void getY() {
		assertEquals(2,b1.getY(),2);
	}
	
	@Test
	public void velocidadBala() {
		assertEquals(3,b1.getVelocidad(),3);
	}
	
	@Test
	public void recarga() {
		//si ha pasado el tiempo de recarga
		assertEquals(true, b1.canDamage());
		//si no ha pasado el tiempo de recarga
		b1.getDanyo();
		assertEquals(false, b1.canDamage());
		
	}

}
