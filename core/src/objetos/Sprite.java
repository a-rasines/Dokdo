package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
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
	private float angle = 0.0f;
	private int sizeX;
	private int sizeY;
	private int textureX = 0;
	private int textureY = 0;
	private Polygon bounds;
	
	protected Sprite(float x, float y, float v, int sizeX, int sizeY) {
		try {
			sb2 = new SpriteBatch();
		} catch (Throwable e) {
			sb2=null;
		}
		this.x = x;
		this.y = y;
		this.v = v;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		refreshBounds();
		
	}
	/**
	 * Trata al Sprite como un objeto de clase hija. Sirve para metodos de Sprite que dependen de otros
	 * @return This en modo hijo
	 */
	 @SuppressWarnings("unchecked")
		private <T extends Sprite>T getAsChild(){
			 return (T) this;
		 }
	
	//DETECCIÓN
	
	/**
	 * Esta función se ejecuta cuando un objeto entra en el rango del jugador
	 */
	public abstract void onRangeOfPlayer();
	
	/**
	 * Esta función se ejecuta cuando el objeto sale del rango del jugador
	 */
	public abstract void onExitFromRange();
	
	//COLISIONES
	
	
	/** Funcion para comparar Circulos con Polygonos
	 * (Inspiracion: https://stackoverflow.com/questions/15323719/circle-and-polygon-collision-with-libgdx)
	 * @param poly, Polygono que se desea comparar
	 * @param circle, Circulo que se desea comparar
	 * @return boolean, true si toca, false si no toca
	 */
	public static boolean overlapCirclePolygon(Polygon poly, Circle circle) {
		float []vertices=poly.getTransformedVertices();
	    Vector2 center=new Vector2(circle.x, circle.y);
	    float squareRadius=circle.radius*circle.radius;
	    for (int i=0;i<vertices.length;i+=2){
	        if (i==0){
	            if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length - 2], vertices[vertices.length - 1]), new Vector2(vertices[i], vertices[i + 1]), center, squareRadius))
	                return true;
	        } else {
	            if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
	                return true;
	        }
	    }
	    
	    return poly.contains(circle.x, circle.y);
	}
	/**
	 * Busca si hay interseccion entre dos segmentos
	 * @param v1 punto 1 de linea 1
	 * @param v2 punto 2 de linea 1
	 * @param p poligono a intersectar
	 * @return true si hay intersección y ocurre en el espacio indicado
	 */
	public boolean linePoligonIntersection(Vector2 v1, Vector2 v2, Polygon p) {
		/*
		 * f(x) = nx + m
		 * g(x) = ax + b
		 * nx + m = ax + b
		 * (n-a)x = b-m
		 * x = b-m/(n-a)
		 * n = y2-y1/(x2-x1)
		 * m = y1 - x1*n
		 * 
		 */
		float[] v = p.getTransformedVertices();
		float x1 = v1.x;
		float y1 = v1.y;
		float x2 = v2.x;
		float y2 = v2.y;
		for(int i = 0; i<p.getTransformedVertices().length/2-1;i++) {
			float x3 = v[i*2];
			float y3 = v[i*2+1];
			float x4 = v[i*2+2];
			float y4 = v[i*2+3];
			float n1 = (y2- y1)/(x2 - x1);
			float n2 = (y4 - y3)/(x4 - x3);
			float m1 = y1 - x1*n1;
			float m2 = y3 - x3*n2;
			
			if (n1 == n2 && m1 == m2)return true; //Coincidentes
			else if(n1 == n2)return false; //Paralelas no coincidentes
			if(x1 == x2) { //Linea 1 vertical
				if(x1*n2+m2 > Math.min(y1, y2) && x1*n2+m2 < Math.max(y1, y2)) {
					return true;
				}
			}else if (x3 == x4) { //Linea 2 verical
				if(x3*n1+m1 > Math.min(y3, y4) && x3*n1+m1 < Math.max(y3, y4)) {
					return true;
				}
			}else {
				float x = (m1 - m2)/(n2 - n1);
				if( x> Math.min(x1, x2) && x< Math.max(x1, x2) && x>Math.min(x3, x4) && x<Math.max(x3, x4))return true;
			}
		}
		return false;
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
	 * Devuelve el objeto de la lista con el que colisiona
	 * @param c lista a buscar
	 * @return objeto que toca/null
	 */
	@SuppressWarnings("unchecked")
	public <T extends Sprite> T getCollidesWith(Iterable<T> c) {
		for(Sprite s : c)if (collidesWith(s))return (T) s;
		return null;
	}
	
	
	/**
	 * Refresca la posición y rotación de la caja de colisiones
	 */
	protected void refreshBounds() {
		if(bounds == null) {
			bounds = new Polygon(new float[]{x,y,sizeX+x,y,sizeX+x,sizeY+y,x,sizeY+y});
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
	
	//MOVIMIENTO
	
	/**
	 * Mueve el objeto por velocidad en el ángulo especificado
	 */
	public void move() {
		getAsChild().move((float)(v*Math.sin(Math.toRadians(angle))),(float)(v*Math.cos(Math.toRadians(angle))));
	}
	/**
	 * Mueve el objeto coordenadas personalizadas
	 * @param x movimiento en x
	 * @param y movimiento en y
	 * @return Sprite en clase hija
	 */
	@SuppressWarnings("unchecked")
	public <T> T move(float x, float y) {
		this.x+=x;
		this.y+=y;
		refreshBounds();
		return (T)this;
	}
	/**
	 * Teletransporta al barco
	 * @param x pos X nueva
	 * @param y pos Y nueva
	 * @return Sprite en clase hija
	 */
	@SuppressWarnings("unchecked")
	public <T> T tpTo(float x, float y) {
		this.x = x;
		this.y = y; 
		refreshBounds();
		return (T)this;
	}
	/**
	 * Rota el objeto q grados
	 * @param q grados a girar
	 * @return El propio objeto para poder ser usado para rotación inicial
	 */
	@SuppressWarnings("unchecked")
	public <T extends Sprite>T rotate(double q) {
		angle = (float) ((angle + q)%360);
		refreshBounds();
		return (T) this;
	}
	public void rotateTo(float q) {
		angle = (float) (angle%360);
		refreshBounds();
	}
	
	//LOGGER
	
	/**
	 * Hace un print de la posición y la orientación del objeto
	 */
	public void printPos() {
		System.out.println("( "+x+" , "+y+" ) "+angle+"º, v="+v);
	}
	public String getInfo() {
		return "( "+x+" , "+y+" ) "+angle+"º, v="+v;
	}
	
	
	//DIBUJADO
	
	/**
	 * Establece la posición de la textura a coger, (0,0) por defecto
	 * @param x posicion X del borde izquierdo
	 * @param y posición Y del borde superior
	 * @return El propio Sprite para concatenar funciones
	 */
	@SuppressWarnings("unchecked")
	public <T extends Sprite> T setTexturePos(int x, int y) {
		this.textureX = x;
		this.textureY = y;
		return (T) this;
	}
	
	private SpriteBatch sb2 ; // se ha movido al constructor para poder usar el Junit
	/** Dibuja sprites especificos de un mapa de Sprites. 64 px = Isla, 32 px = Barco, 16 px = cañon, 8 px = bala
	 * @param columna, Columna en la que se encuentra el sprite (Horizontal)
	 * @param fila, Fila en la que se encuentra el sprite (Vertical)
	 */
	public void dibujar() {
		int columna = textureX; int fila = textureY;
		if(sb2==null || tMap == null ) {
			return;
		}//esto es para evitar nullpointer con los junit
		
			int size = Math.max(sizeX, sizeY);
	
			//En filas y columnas, se indica la posicioin *32 (32x32 pixeles cada barco) +1 ya que en la textura habra un pixel entre estos
			//Empieza a contar en 0 las filas, aunque el primer sprite se encontrara en el pixel 1-1
			//Mirando en GIMP, el primer sprite empieza en 1-1, por eso el mas 1, pero el segundo comienza en 34-34, asiq el +1 se qeuda corto
			//Esto va escalando, ya que el sprite 3 estaria en 67 (size * 2 +2), sumando la propia columna en la que esta se soluciona
			//Los ejemplos son con texturas de 32 px, pero afecta igual a las texturas de cualquier tamaño
			TextureRegion sprite = new TextureRegion(tMap, fila*size +1+fila, columna*size +1+columna, size, size);
			
			sb2.begin();
			//En orden, textura, x, y, CentroX, CentroY, Anchura, Altura, EscalaX, EscalaY, Angulo (En grados)
			sb2.draw(sprite, x + MainScreen.vp.getScreenWidth() / 2 - MainScreen.barco.getX(), y + MainScreen.vp.getScreenHeight() / 2 - MainScreen.barco.getY(), size/2, size/2, size, size, 1, 1, -angle);
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
	public int getSizeX() {
		return sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	
}
