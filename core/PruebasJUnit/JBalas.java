import static org.junit.Assert.assertEquals;
import org.junit.Test;


import objetos.Bala;

public class JBalas {
	private Bala b1 = new Bala(1,2,3,4,5); 

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

}
