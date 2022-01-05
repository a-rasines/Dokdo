package objetos;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import objetos.barcos.Barco;

public class MiniMapa {
	
	private static int FACTOR = 70;
	
	private static ArrayList<Float> listaIslas = new ArrayList<Float>();
	private static ArrayList<Boolean> listaIslasEstado = new ArrayList<Boolean>();
	private static float barcoX;
	private static float barcoY;
	private static ShapeRenderer srB =  new ShapeRenderer();
	
	private static ShapeRenderer srIL = new ShapeRenderer(); //Isla Liberada
	private static ShapeRenderer srIO = new ShapeRenderer(); //Isla Ocupada
	
	/** Guarda la posicion de las islas y ajusta los shaperenderers porq solo hace falta llamarla una vez,
	 * asiq aprovecho y que el trabajo "pesado" se haga aqui.
	 * @param lista, lista de las islas a las q sacarle el valor
	 */
	public static void setPosIslas(List<Isla> lista) {
		
		//Dos posiciones cumplen una posicion de una isla
		for(Isla i : lista) {
			listaIslas.add(i.getX() / FACTOR); //Escalarlo a un tamaño menor
			listaIslas.add(i.getY() / FACTOR); //Escalarlo a un tamaño menor
			listaIslasEstado.add(i.isConquistada());
		}
		setBordeMapa();
		srB.setColor(Color.BROWN);
		srIL.setColor(Color.GREEN);
		srIO.setColor(Color.RED);
	}
	
	public static void setPosBarco(Barco barco) {
		barcoX = barco.getX() / FACTOR;
		barcoY = barco.getY() / FACTOR;
	}
	
	
	private static float[] vertices = new float[8];
	
	/** Metodo para dibujar el borde de manera provisional
	 * Se cambiara por una textura mas bonita
	 */
	private static SpriteBatch sb = new SpriteBatch();
	private static void setBordeMapa() {
		//Vertice 1
		vertices[0] = Gdx.graphics.getWidth();
		vertices[1] = 0;
		//Vertice 2
		vertices[2] = Gdx.graphics.getWidth();
		vertices[3] =  200;
		//Vertice 3
		vertices[4] = Gdx.graphics.getWidth() - 200;
		vertices[5] = 200;
		//Vertice 4
		vertices[6] = Gdx.graphics.getWidth() - 200;
		vertices[7] = 0;
		
	}
	public static void mapaRenderer() {
		sb.begin();
		srB.begin(ShapeRenderer.ShapeType.Filled);
		srIL.begin(ShapeRenderer.ShapeType.Filled);
		srIO.begin(ShapeRenderer.ShapeType.Filled);
		
	    
	    srB.setColor(Color.BROWN);
	    srB.circle(Gdx.graphics.getWidth() - 100 + barcoX, 100 + barcoY, 2);
	    for(int i = 0; i< listaIslasEstado.size(); i++) {
	    	if(listaIslasEstado.get(i)) {
	    		srIL.circle(Gdx.graphics.getWidth() - 100 + listaIslas.get(2* i), 100 + listaIslas.get(2*i+1), 2);
	    	} else {
	    		srIO.circle(Gdx.graphics.getWidth() - 100 + listaIslas.get(2* i), 100 +listaIslas.get(2*i+1), 2);
	    	}
	    }
	    TextureRegion b = new TextureRegion(new Texture("marco.png"));
	   // En orden, textura, x, y, CentroX, CentroY, Anchura, Altura, EscalaX, EscalaY, Angulo (En grados)
	    sb.draw(b, Gdx.graphics.getWidth() - 210, 0, 105,105, 210, 210, 1, 1, 0);
	    sb.end();
	    srIL.end();
	    srIO.end();
	
	    srB.end();
	}

}
