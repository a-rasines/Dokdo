package objectos.barcos;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import dg.main.MainScreen;
import objetos.Bala;
import objetos.Sprite;
/**
 * Representa un barco enemigo. Esta clase contiene un barco con herramientas para IA propia
 * @author algtc
 *
 */
public class BarcoEnemigo extends Barco{
	private Polygon lineaFrente;
	private Polygon lineaIzquierda;
	private Polygon lineaDerecha;
	private Polygon lineaAtras;
	private float rango = 150; //Rango de accion
	
	public BarcoEnemigo(int vida, int nivel, float posX, float posY) {
		super(vida, nivel, posX, posY);
		refreshLineas();
	}
	protected void refreshLineas() {
		if(lineaFrente == null) {
			lineaFrente = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX() +1, (float) this.getY() ,
					(float) this.getX() , (float) this.getY()  + rango
			});//Esquinas
			
			lineaAtras = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY() ,
					(float) this.getX() , (float) this.getY() - rango
			});//Esquinas
			
			lineaDerecha = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY() ,
					(float) this.getX()  + rango, (float) this.getY() 
			});//Esquinas
			
			lineaIzquierda = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY(),
					(float) this.getX()  - rango, (float) this.getY() 
			});//Esquinas
			
			lineaFrente.setOrigin((float) this.getX(), (float) this.getY());//Pos barco
			lineaAtras.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
			lineaDerecha.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
			lineaIzquierda.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
		}else 
			lineaFrente.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaFrente.setRotation(-getAngle()); //Get angulo
		
		lineaAtras.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaAtras.setRotation(-getAngle()); //Get angulo
		
		lineaDerecha.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaDerecha.setRotation(-getAngle()); //Get angulo
		
		lineaIzquierda.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaIzquierda.setRotation(-getAngle()); //Get angulo
		
	}
	
	/** Compara las lineas del llamador con el bounding del objeto pasado
	 * @param o Sprite con el que se desea comparar (Objeto Pasado)
	 * @return True si toca, False si no
	 */
	//TODO Se añade return polygon y asi sabemos que linea toca, Meterselo al hijo ;)
	public Polygon tocaLinea(Sprite o) {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    shapeRenderer.polygon(lineaDerecha.getTransformedVertices());
	    shapeRenderer.polygon(lineaIzquierda.getTransformedVertices());
	    shapeRenderer.polygon(lineaFrente.getTransformedVertices());
	    shapeRenderer.polygon(lineaAtras.getTransformedVertices());
	    shapeRenderer.end();
	    if(Intersector.overlapConvexPolygons(lineaDerecha, o.getBounds())) {
	    	return lineaDerecha;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaIzquierda, o.getBounds())) {
	    	return lineaIzquierda;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaFrente, o.getBounds())) {
	    	return lineaFrente;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaAtras, o.getBounds())) {
	    	return lineaAtras;
	    }
	    
	    return null;
		
	}
	
	@Override
	public void move(float x, float y) {
		super.move(x, y);
		refreshLineas();
	}
	@Override
	public void rotate(double q) {
		super.rotate(q);
		refreshLineas();
	}
	@Override
	public void recibeDaño(Bala bullet) {
		super.recibeDaño(bullet);
		if(vida<=0) {
			MainScreen.barEneBorrar.add(this);
		}
	}

}
