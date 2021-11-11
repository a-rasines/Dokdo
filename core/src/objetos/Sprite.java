package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import dg.main.MainScreen;

/**
 * Representa todo objeto movil
 *
 */
public abstract class Sprite {
	public Texture tMap;
	private float x;
	private float y;
	protected float v; //velocidad
	private float angle;
	private int sizeX;
	private int sizeY;
	private Polygon bounds;
	protected Sprite(float x, float y, float v, float angle, int sizeX, int sizeY) {
		this.x = x;
		this.y = y;
		this.v = v;
		this.angle = angle;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		refreshBounds();
	}
	/**
	 * Comprueba si el Sprite colisiona con otro
	 * @param o El Sprite contra el que se comprueba la colisión
	 * @return True = Colisiona
	 */
	public boolean collidesWith(Sprite o) {
		if(o==null)return false;
		return Intersector.overlapConvexPolygons(bounds, o.getBounds());
	}
	/**
	 * Comprueba si algun Sprite colisiona con este
	 * @param c La colección contra el que se comprueba la colisión
	 * @return True = Colisiona
	 */
	public boolean collidesWith(Iterable<? extends Sprite> c) {
		for(Sprite s : c)if (collidesWith(s))return true;
		return false;
	}
	/**
	 * Refresca la posición y rotación de la caja de colisiones
	 */
	protected void refreshBounds() {
		if(bounds == null) {
			bounds = new Polygon(new float[]{x,y,sizeX+x,x,sizeX+x,sizeY+y,y,sizeY+y});
			bounds.setOrigin(x + sizeX/2, y + sizeY/2);
		}else 
			bounds.setPosition(x, y);
		bounds.setRotation(-angle);
		
	}
	/**
	 * Dibuja en pantalla la caja de colisiones. Función solo para debug
	 * @param shapeRenderer
	 */
	public void drawCollisions(ShapeRenderer shapeRenderer) {
	    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    shapeRenderer.polygon(bounds.getTransformedVertices());
	    shapeRenderer.end();
	}
	/**
	 * Devuelve un {@link com.badlogic.gdx.math.Polygon Polygon} para aplicar lógica de colisiones
	 * @return El {@link com.badlogic.gdx.math.Polygon Polygon} del Sprite
	 */
	public Polygon getBounds() {
		return bounds;
	}
	/**
	 * Mueve el objeto por velocidad en el ángulo especificado
	 */
	public void move() {
		move((float)(v*Math.sin(Math.toRadians(angle))),(float)(v*Math.cos(Math.toRadians(angle))));
	}
	/**
	 * Mueve el objeto coordenadas personalizadas
	 * @param x movimiento en x
	 * @param y movimiento en y
	 */
	public void move(float x, float y) {
		this.x+=x;
		this.y+=y;
		refreshBounds();
	}
	/**
	 * Rota el objeto q grados
	 * @param q grados a girar
	 */
	public void rotate(double q) {
		angle = (float) ((angle + q)%360);
		refreshBounds();
	}
	/**
	 * Hace un print de la posición y la orientación del objeto
	 */
	public void printPos() {
		System.out.println("( "+x+" , "+y+" ) "+angle+"º, v="+v);
	}
	public String getInfo() {
		return "( "+x+" , "+y+" ) "+angle+"º, v="+v;
	}
	

	private SpriteBatch sb2 = new SpriteBatch();
	/** Dibuja sprites especificos de un mapa de Sprites. 64 px = Isla, 32 px = Barco, 16 px = cañon, 8 px = bala
	 * @param columna, Columna en la que se encuentra el sprite (Vertical)
	 * @param fila, Fila en la que se encuentra el sprite (Horizontal)
	 */
	public void dibujar(int columna, int fila) {
		
			int size = Math.max(sizeX, sizeY);
	
			//En filas y columnas, se indica la posicioin *32 (32x32 pixeles cada barco) +1 ya que en la textura habra un pixel entre estos
			//Empieza a contar en 0 las filas, aunque el primer sprite se encontrara en el pixel 1-1
			TextureRegion sprite = new TextureRegion(tMap, fila*size +1, columna*size +1, size, size);
			
			sb2.begin();
			//En orden, textura, x, y, CentroX, CentroY, Anchura, Altura, EscalaX, EscalaY, Angulo (En grados)
			sb2.draw(sprite, x + Gdx.graphics.getWidth() / 2 - MainScreen.barco.getX(), y + Gdx.graphics.getHeight() / 2 - MainScreen.barco.getY(), size/2, size/2, size, size, 1, 1, -angle);
			sb2.end();
		
	}
		
	
	
	//Getters 
	public Texture getTexture() {
		return tMap;
	}
	
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getAngle() {
		return angle;
	}
	
	//Setters
	
	public void setTexture(Texture textura){
		tMap = textura;
	}
	
}
