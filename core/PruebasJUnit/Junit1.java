import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import org.junit.Test;


import objetos.Bala;

public class Junit1 {
	
	@Test
	public void probarBala() {
		//Texture t = new Texture(Gdx.files.internal("tileSetBala.png"));
		Bala b1 = new Bala(1,0,0,0,1); //las texturas no cargan sin el libgdx por lo que no se hace la prueba
		assertEquals(1,b1.getDanyo());
		
	}

}
