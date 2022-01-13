package dg.main;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import databasePack.DatabaseHandler;
import objetos.Bala;
import objetos.Canyon;
import objetos.Isla;
import objetos.MiniMapa;
import objetos.Municion;
import objetos.Sprite;
import objetos.barcos.Barco;
import objetos.barcos.BarcoEnemigo;
import objetos.barcos.BarcoJugador;
import objetos.barcos.Barco.PosicionCanyon;

//Pantalla en la que se va desarrollar el juego
public class MainScreen extends FormatoMenus{
	protected Skin skin;
	public static MainScreen m1;
	public static AudioPlayer cFondo = new AudioPlayer();//TODO cambiar esto por la cacion principal
	private static Logger logger= Logger.getLogger("MainScreen");
	public static BarcoJugador barco;
	public static List<Isla> islaList = new LinkedList<>();
	public static List<BarcoEnemigo> barcosEnemigos = new ArrayList<>();
	public static List<BarcoEnemigo> barEneBorrar = new ArrayList<>();
	public static List<Bala> balasDisparadas = new ArrayList<>();
	public static Map<Bala, BarcoEnemigo> danoContinuo = new HashMap<>();
	public static List<Bala> balasBorrar = new ArrayList<>();
	public static List<Sprite> onRange = new ArrayList<>();
	public static List<Sprite> offRange = new ArrayList<>();

	public static float tiempo = 0;
	public static Random ran = new Random();
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Viewport vp = new FillViewport((float)screenSize.getWidth()-50, (float)screenSize.getHeight()-50);
	public static Stage stage = new Stage(vp);
	private static MainScreen instance;
	private static boolean regresoM;
	public static MainScreen getInstance() {
		if (instance == null) instance = new MainScreen();
		return instance;
	}
	public static void setreinicio() {
    	//instance.dispose();
    	instance=null;
    }
	
	public MainScreen() {
		m1=this;
		setOrdenCanciones(true);
		//Musica normal//TODO sonido
    	try {
    		getcSecundaria().setCancion("Sonidos//Battle.mp3");
    		getcSecundaria().Reproducir();
    		getcSecundaria().setVolumen(volumenes[1]);
        	logger.info("Cancion secundaria cargada sin problemas");
		} catch (Exception a) {
			a.printStackTrace();
        	logger.info("Fallo al cargar la Cancion secundaria");
		}
    	try {
    		getcPrincipal().setCancion("Sonidos//Overworld.mp3");
    		getcPrincipal().Reproducir();
        	logger.info("Cancion principal cargada sin problemas");
		} catch (Exception e) {
        	logger.info("Fallo al cargar la Cancion principal");
        	getcSecundaria().setVolumen(volumenes[0]);
		}

	}
	
	 public static void setregresoM() {
	    	regresoM=true;
	    }
	
	public void barcosAltaMar() {
		Random r = new Random();
		
		boolean spawn = false;
		
		
		while (!spawn) {
			
			
			float x = barco.getX() + r.nextInt(1500) - 750;
			float y = barco.getY() + r.nextInt(1500) - 750;
			
			if((barco.getX() - x >= 500 || barco.getX() - x <= -500) && (barco.getY() - y >= 500 || barco.getY() - y <= -500)) {

				BarcoEnemigo b = BarcoEnemigo.lvl1(x,  y, false).setTexturePos(0, 1);
				barcosEnemigos.add(b);
				offRange.add(b);
				spawn = true;
			}
		}
		
		
		logger.log(Level.INFO, "Barco generado");
		
	} 
	
	/** Genera islas de manera repartida por un mundo de 10000x10000 ( 5000 hacia cada lado ) 
	 * 
	 */
	public void generarIslas() {
		Random r = new Random();
		//Ahora mismo se generan 5 islas por cuadrante
		
		logger.info("Iniciada Generacion de islas");
		List<Vector2> isles = new LinkedList<>();
		Integer[][]cuadrants = {{1,1},{-1,1},{-1,-1},{1,-1}};
		for(int i = 0; i< 4; i++) {			
			for(int j = 0; j < 5; j++) {
				boolean next = false;
				while (!next) {
					int x = cuadrants[i][0]*(r.nextInt(4500)+500);
					int y = cuadrants[i][1]*(r.nextInt(4500)+500);
					Vector2 v = new Vector2(x, y);
					boolean valid = true;
					for(Vector2 k: isles) {
						if(k.dst(v)<500) {
							valid = false;
							break;
						}
					}
					if (valid) {
						islaList.add(
							new Isla(0,(float) x,(float) y, 0, 0, false)
						);
						next = true;
					}
				}
			}
		}
		for (int i = 0; i<islaList.size(); i++) {
			
			Isla is = islaList.get(i).setTexturePos(i%10>=7?1:0, i%10%7);
			is.setId(i);
			DatabaseHandler.SQL.addValue(
					"Islas", 
					String.valueOf(i), 
					String.valueOf(DatabaseHandler.JSON.getString("actualUser")), 
					String.valueOf(is.getX()), 
					String.valueOf(is.getY()), 
					is.isConquistada()?"1":"0",
					String.valueOf(is.getNivelRecomendado()),
					String.valueOf(is.getBotin()),
					String.valueOf(i%10)
			);
		}
		
		for (Isla i: islaList) {
			barcosEnemigos.addAll(i.getBarcos());
			offRange.addAll(i.getBarcos());
		}
		
		
		logger.info("Generacion completa");
	}
	
	@Override
	public void show() {
	 
		ResultSet pos0 = DatabaseHandler.SQL.get("Jugadores", "Vida, BarcoX, BarcoY, Rotacion", "ID = "+DatabaseHandler.JSON.getString("actualUser"));
		try {
			barco = new BarcoJugador(pos0.getFloat("BarcoX"), pos0.getFloat("BarcoY"), 10/*pos0.getInt("Vida")*/,0, Municion.INCENDIARIA, pos0.getInt("Dinero")).rotate(pos0.getFloat("Rotacion"));
		} catch (NumberFormatException | SQLException e1) {
			logger.severe("Error cargando el barco principal: "+e1.getMessage());
			e1.printStackTrace();
			int value =new Random().nextInt(899999)+100000;
			while(DatabaseHandler.SQL.existsValue("Jugadores", "ID", String.valueOf(value))) {
				value =new Random().nextInt(899999)+100000;
				System.out.println(value);
			}
			DatabaseHandler.JSON.write("users", value, false);
			DatabaseHandler.JSON.write("actualUser", value, true);
			DatabaseHandler.SQL.addValue("Jugadores(ID)", Integer.toString(value));
			barco = new BarcoJugador(0.0f ,0.0f ,1000000,0 ,Municion.INCENDIARIA,100);
		}
    	barco.setCanyones(PosicionCanyon.DELANTE, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.ATRAS, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.DERECHA, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.IZQUIERDA, new Canyon(0,0));
    	
    	
    	
    	if(!DatabaseHandler.SQL.existsValue("Islas", "ID_Jugador", DatabaseHandler.JSON.getString("actualUser"))) {
    		generarIslas();
    	} else {
    		ResultSet res = DatabaseHandler.SQL.get("Islas", "*");
    		try {
				while (res.next()) {
					islaList.add(new Isla(res.getInt("ID"),res.getFloat("X"),res.getFloat("Y"),res.getInt("Nivel"),res.getInt("Botin"), res.getString("Conquistada")=="1").setTexturePos(res.getInt("Textura")>=7?1:0, res.getInt("Textura")%7));
				}
				for (Isla i: islaList) { 
					barcosEnemigos.addAll(i.getBarcos());
					offRange.addAll(i.getBarcos());
				}
				logger.info("Islas Cargadas");
			} catch (SQLException e) {
				logger.severe("Error Cargando las islas: "+e.getMessage());
				e.printStackTrace();
			}
    		
    	}
    	
    	MiniMapa.setPosIslas(islaList);
		
    	
	}

	Boolean cambioEstado = true;

	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.0f, 0.5f, 1f,0); //Necesario para updatear correctamente la pantall
		dibujarFondo();
		
		//Control de teclas
		if(Gdx.input.isKeyPressed(Input.Keys.R))
			barco.tpTo(0, 0);
		if (Gdx.input.isKeyPressed(MenuOp.getvTeclas(0)) && Gdx.input.isKeyPressed(MenuOp.getvTeclas(1)))
			barco.decelerate();
		else if(Gdx.input.isKeyPressed(MenuOp.getvTeclas(0)))
			barco.forward();
		else if(Gdx.input.isKeyPressed(MenuOp.getvTeclas(1)))
			barco.backwards();
		else 
			barco.decelerate();
		if(Gdx.input.isKeyPressed(MenuOp.getvTeclas(3))) 
			barco.right();
		if(Gdx.input.isKeyPressed(MenuOp.getvTeclas(2)) || barco.collidesWith(islaList)) 
			barco.left();
		if(barco.collidesWith(islaList))
			barco.right();
		if (Gdx.input.isKeyJustPressed(MenuOp.getvTeclas(4))) {
			barco.dispararLado(PosicionCanyon.DELANTE);
		}else if(Gdx.input.isKeyJustPressed(MenuOp.getvTeclas(6))) {
			barco.dispararLado(PosicionCanyon.IZQUIERDA);
		}else if(Gdx.input.isKeyJustPressed(MenuOp.getvTeclas(5))) {
			barco.dispararLado(PosicionCanyon.ATRAS);
		}else if(Gdx.input.isKeyJustPressed(MenuOp.getvTeclas(7))) {
			barco.dispararLado(PosicionCanyon.DERECHA);
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			barcosEnemigos.forEach(b->b.stop());
			MenuOp.setInstanciaDeLlamada(instance);
			Dokdo.getInstance().setScreen(MenuOp.getInstance());           	
		}
		//COLISIONES
		if (barco.collidesWith(islaList)) {
			barco.undoMove();
			barco.stop();
		}
		for(Bala z: danoContinuo.keySet()) {
			if(balasDisparadas.contains(z)) balasDisparadas.remove(z);
			if(z.getVeces()==0) {
				balasBorrar.add(z);
			}else {
				danoContinuo.get(z).recibeDanyoContinuo(z);
			}
		}
		for(Bala i: balasBorrar) {
			danoContinuo.remove(i);
		}
		balasBorrar.clear();
		for (Bala i: balasDisparadas){
			BarcoEnemigo b = i.getCollidesWith(barcosEnemigos);
			if(b != null && i.barcoDisparo(barco)) {
				if(barco.getMunicionEnUso().getInstantaneo()) {
					b.recibeDanyo(i);
					balasBorrar.add(i);
				}
				else{
					b.recibeDanyo(i);
					danoContinuo.put(i, b);
					balasBorrar.add(i);
				}
			} else if(i.collidesWith(barco) && !i.barcoDisparo(barco)) {
				barco.recibeDanyo(i);
				balasBorrar.add(i);
			}
			if(i.collidesWith(islaList)) {
				balasBorrar.add(i);
			}
			i.decelerate();
			i.dibujar();
		}
		
		for(Bala i: balasBorrar) {
			balasDisparadas.remove(i);
		}
		for(Barco j: barEneBorrar) {
			barcosEnemigos.remove(j);
			onRange.remove(j);
			offRange.remove(j);
			j.onExitFromRange();
		}
		
		barEneBorrar.clear();
		balasBorrar.clear();
		
		//Actualización de rango
		
		List<Sprite> move = new LinkedList<>();
		//System.out.println(offRange.size());
		for(Sprite b : barco.getEnRango(offRange)) {
			move.add(b);
			b.onRangeOfPlayer();
		}
		onRange.addAll(move);
		offRange.removeAll(move);
		move.clear();
		for(Sprite s : onRange)
			if(!barco.enRango(s)) {
				move.add(s);
				s.onExitFromRange();
			}
		offRange.addAll(move);
		onRange.removeAll(move);
		move.clear();
		for (BarcoEnemigo b : barcosEnemigos) {
			if (b.tocaLinea(barco) != null) {
				b.dispararLado(b.tocaLinea(barco));
			}
			b.IAMove();
			b.dibujar();
		}
		
		//Dibujado
		barco.dibujar();		
		
		for (Isla i : islaList) {
			i.dibujar();
			if (i.getBarcos().size() == 0) {
				i.conquistar(barco);
			}
			for (BarcoEnemigo b : i.getBarcos()) {
				b.dibujar();
				if(b.tocaLinea(barco) != null) {
					b.dispararLado(b.tocaLinea(barco));
					
				}
				
				if (!barcosEnemigos.contains(b)) { //Eliminar el barco de la lista de la Isla
					i.getBarcos().remove(b);
					break;
				}  
			}			
		}
				
		
		if (onRange.size() < 3 && tiempo > 30) {
			
			barcosAltaMar();
			tiempo=0;
		}
		
		tiempo = (tiempo + 1*Gdx.graphics.getDeltaTime()) % 31;
		
		//Minimapa
		MiniMapa.actualizarEstado(islaList);
		MiniMapa.mapaRenderer();
		MiniMapa.setPosBarco(barco);
		//stage.draw();
		
	}
	//volumenes = {0.5f,0.0f}
	public boolean IntercambioSonido(boolean x) {//TODO sonido
		if(x) {
			getcPrincipal().setVolumen(volumenes[0]);
			getcSecundaria().setVolumen(volumenes[1]);
			return false;
		}else {
			getcPrincipal().setVolumen(volumenes[1]);
			getcSecundaria().setVolumen(volumenes[0]);
			return true;
		}
	}

	private SpriteBatch sb = new SpriteBatch();
	private Texture fondo = new Texture("FondoJuego.png");
	private TextureRegion tr = new TextureRegion(fondo);
	public void dibujarFondo() {
		sb.begin();
		
		//Hay doble render para cubrir bien la pantalla, ya que sino era facil salirse de la imagen del fondo
		//Plan se veia donde acababa la imagen 
		sb.draw(tr, -512 - (barco.getX() % 512), -512 - (barco.getY() % 512), 0, 0, 1024, 1024, 2, 2, 0);
		sb.draw(tr, 512 - (barco.getX() % 512), -512 - (barco.getY() % 512), 0, 0, 1024, 1024, 2, 2, 0);
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
		vp.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO agregar las cosas a detener para poder reinciar el juego
		
	}
}