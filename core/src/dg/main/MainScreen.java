package dg.main;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import DataBase.DatabaseHandler;
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
	public static List<Bala> balasDannyoContinuo = new ArrayList<>();
	public static List<Bala> balasBorrar = new ArrayList<>();
	public static List<Sprite> onRange = new ArrayList<>();
	public static List<Sprite> offRange = new ArrayList<>();
	private static Table menuPausa;
	BarcoEnemigo barco2 = BarcoEnemigo.lvl1(0, 0, false).setTexturePos(0,1);

	
	public static Random ran = new Random();
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Viewport vp = new FillViewport((float)screenSize.getWidth()-50, (float)screenSize.getHeight()-50);
	public static Stage stage = new Stage(vp);
	private static MainScreen instance;
	public static MainScreen getInstance() {
		if (instance == null) instance = new MainScreen();
		return instance;
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

	/**
	 * Genera un array a partir de valores
	 * @param v valores en el array
	 * @return Array de los objetos metidos. Si es nativo sale como su versión que extiende de Object
	 */
	public <T> T[] arrayBuilder(@SuppressWarnings("unchecked") T... v){
		return v;
	}
	
	public void barcosAltaMar() {
		Random r = new Random();
		BarcoEnemigo b = BarcoEnemigo.lvl1(barco.getX() + r.nextInt(1000) -500,  barco.getY() + r.nextInt(1000) - 500, false).setTexturePos(0, 1);
		barcosEnemigos.add(b);
		offRange.add(b);
	} 
	
	public void asignarTexturasAIslas() {
		for (int i = 0; i<islaList.size(); i++) {
			int text = new Random().nextInt(10);
			Isla is = islaList.get(i).setTexturePos(text>=7?1:0, text%7);
			/*
			 * new TableBuilder("Islas")
				.addColumn("ID", DataType.INT, "2")
				.addColumn("ID_Jugador", "Player1", DataType.INT, "6")
				.addColumn("X", "0.0", DataType.DEC, "9", "4")
				.addColumn("Y", "0.0", DataType.DEC, "9", "4")
				.addColumn("Conquistada", "0", DataType.INT, "1")
				.addColumn("Nivel", "0", DataType.INT, "2")
				.addColumn("Botin", "0", DataType.INT, "5")
				.addColumn("Textura", DataType.INT, "2")
			 */
			DatabaseHandler.SQL.addValue(
					"Islas", 
					String.valueOf(i), 
					String.valueOf(DatabaseHandler.JSON.getString("actualUser")), 
					String.valueOf(is.getX()), 
					String.valueOf(is.getY()), 
					is.isConquistada()?"1":"0",
					String.valueOf(is.getNivelRecomendado()),
					String.valueOf(is.getBotin()),
					String.valueOf(text)
			);
		}

	}
	
	/** Genera islas de manera repartida por un mundo de 10000x10000 ( 5000 hacia cada lado ) 
	 * 
	 */
	public void generarIslas() {
		Random r = new Random();
		//Ahora mismo se generan 5 islas por cuadrante
		
		logger.info("Iniciada Generacion de islas");
		List<Vector2> isles = new LinkedList<>();
		Integer[][]cuadrants = arrayBuilder(arrayBuilder(1,1), arrayBuilder(-1, 1), arrayBuilder(-1, -1), arrayBuilder(1, -1));
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
							new Isla((float) x,(float) y, 0, 0, false)
						);
						next = true;
					}
				}
			}
		}
		
		asignarTexturasAIslas();
		
		for (Isla i: islaList) { //TODO añadiendolos funciona bien la IA y todo
			barcosEnemigos.addAll(i.getBarcos());
			offRange.addAll(i.getBarcos());
		}
		
		
		logger.info("Generacion completa");
	}
	
	@Override
	public void show() {
		//Bont�n para regresar al menu principal
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	menuPausa = new Table();
    	menuPausa.setFillParent(true);
    	menuPausa.center();
        TextButton boton1 = new TextButton("Jugar", skin);
        TextButton boton2 = new TextButton("Volumen", skin);
        TextButton boton3 = new TextButton("Salir", skin);
        
        menuPausa.add(boton1).width(80).pad(5);
        menuPausa.row();
        menuPausa.add(boton2).width(80).pad(5);
        menuPausa.row();
        menuPausa.add(boton3).width(80).pad(5);
        
    	stage.addActor(menuPausa);
    	
    	
	
		
	

    	
		ResultSet pos0 = DatabaseHandler.SQL.get("Jugadores", "Vida, BarcoX, BarcoY, Rotacion", "ID = "+DatabaseHandler.JSON.getString("actualUser"));
		try {
			barco = new BarcoJugador(pos0.getFloat("BarcoX"), pos0.getFloat("BarcoY"), 3/*pos0.getInt("Vida")*/,0, Municion.INCENDIARIA).rotate(pos0.getFloat("Rotacion"));
		} catch (NumberFormatException | SQLException e1) {
			logger.severe("Error cargando el barco principal: "+e1.getMessage());
			e1.printStackTrace();
			int value =new Random().nextInt(899999)+100000;
			while(DatabaseHandler.SQL.existsValue("Jugadores", "ID", String.valueOf(value))) {value =new Random().nextInt(899999)+100000;}
			DatabaseHandler.JSON.write("users", value, false);
			DatabaseHandler.JSON.write("actualUser", value, true);
			DatabaseHandler.SQL.addValue("Jugadores(ID)", Integer.toString(value));
			barco = new BarcoJugador(0.0f ,0.0f ,10 ,0 ,Municion.INCENDIARIA);
		}
    	barco.setCanyones(PosicionCanyon.DELANTE, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.ATRAS, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.DERECHA, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.IZQUIERDA, new Canyon(0,0));
    	barcosEnemigos.add(barco2);
    	offRange.add(barco2);
    	
    	
    	
    	if(!DatabaseHandler.SQL.existsValue("Islas", "ID_Jugador", DatabaseHandler.JSON.getString("actualUser"))) {
    		generarIslas();
    	} else {
    		ResultSet res = DatabaseHandler.SQL.get("Islas", "*");
    		try {
				while (res.next()) {
					islaList.add(new Isla(res.getFloat("X"),res.getFloat("Y"),res.getInt("Nivel"),res.getInt("Botin"), res.getInt("Conquistada")==1).setTexturePos(res.getInt("Textura")>=7?1:0, res.getInt("Textura")%7));
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
		ScreenUtils.clear(0.0f, 0.5f, 1f,0); //Necesario para updatear correctamente la pantalla
		
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
		/*logger.info("pressed:"+
				" W= "+Gdx.input.isKeyPressed(Input.Keys.W)+
				" A= "+Gdx.input.isKeyPressed(Input.Keys.A)+
				" S= "+Gdx.input.isKeyPressed(Input.Keys.S)+
				" D= "+Gdx.input.isKeyPressed(Input.Keys.D)
				);*/
		if (barco.collidesWith(islaList)) {
			barco.undoMove();
			barco.stop();
		}
		if(Gdx.input.isKeyPressed(MenuOp.getvTeclas(3))) 
			barco.right();
		if(Gdx.input.isKeyPressed(MenuOp.getvTeclas(2)) || barco.collidesWith(islaList)) 
			barco.left();
		if(barco.collidesWith(islaList))
			barco.right();
		//logger.info("barco: "+barco.getInfo());
		//logger.info("collision:"+String.valueOf(barco.collidesWith(barco2)));
		if (Gdx.input.isKeyJustPressed(MenuOp.getvTeclas(4))) {
			barco.dispararLado(PosicionCanyon.DELANTE);
		}else if(Gdx.input.isKeyJustPressed(MenuOp.getvTeclas(6))) {
			barco.dispararLado(PosicionCanyon.IZQUIERDA);
		}else if(Gdx.input.isKeyJustPressed(MenuOp.getvTeclas(5))) {
			barco.dispararLado(PosicionCanyon.ATRAS);
		}else if(Gdx.input.isKeyJustPressed(MenuOp.getvTeclas(7))) {
			barco.dispararLado(PosicionCanyon.DERECHA);
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			System.out.println("Pausa");
			MenuOp.setInstanciaDeLlamada(instance);
			Dokdo.getInstance().setScreen(MenuOp.getInstance());           	
		}
		//DAÑO A LOS BARCOS ENEMIGOS.
		
		for (Bala i: balasDisparadas){
			BarcoEnemigo b = i.getCollidesWith(barcosEnemigos);
			if(b != null && i.barcoDisparo(barco)) {
				if(barco.getMunicionEnUso().getInstantaneo())
					b.recibeDanyo(i);
				else{
					b.recibeDanyo(i);
					 balasDannyoContinuo.add(i);
				}
			} else if(i.collidesWith(barco) && !i.barcoDisparo(barco)) {
				System.out.println("jugador tocado");
				barco.recibeDanyo(i);
			}
			if(i.collidesWith(islaList)) {
				System.out.println("isla tocada");
				balasBorrar.add(i);
			}
			i.decelerate();
			i.dibujar();
		}
		for(Bala z: balasDannyoContinuo) {
			balasDisparadas.remove(z);
			if(z.getVeces()==0) {
				balasBorrar.add(z);
			}else {
				if(z.barcoDisparo(barco)) barco2.recibeDanyoContinuo(z);
				else barco.recibeDanyoContinuo(z);
			}
		}
		for(Bala i: balasBorrar) {
			balasDisparadas.remove(i);
			 balasDannyoContinuo.remove(i);
		}
		for(Barco j: barEneBorrar) {//TODO
			System.out.println("Eliminado");
			setOrdenCanciones(true);
			IntercambioSonido(getOrdenCanciones());
			barcosEnemigos.remove(j);
			onRange.remove(j);
			offRange.remove(j);
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
			b.IAMove();
		}
		
		//Dibujado
		
		barco.dibujar();
		barcosEnemigos.forEach(v->v.dibujar());
		islaList.forEach(v->v.dibujar());
		
		
		
		//TODO Prueba de lineas
		
		if(barco2.tocaLinea(barco) != null) {
			barco2.dispararLado(barco2.tocaLinea(barco));
			
		}
		
		
		detectaCanon();
	
		
		
		for (Isla i : islaList) {
			
			if (i.getBarcos().size() == 0) {
				i.conquistar();
			}
			for (BarcoEnemigo b : i.getBarcos()) {
				b.dibujar();
				if(b.tocaLinea(barco) != null) {
					b.dispararLado(b.tocaLinea(barco));
					
				}
			}
			
			
			
		}
		
		
		
		if (ran.nextInt(10000) > 9990) {
			System.out.println("spawn");
			barcosAltaMar();
		}
		
		//Minimapa
		MiniMapa.mapaRenderer();
		MiniMapa.setPosBarco(barco);
		//stage.draw();
		
	}
	
	public void detectaCanon() {
		for (BarcoEnemigo b : barcosEnemigos) {
			if (b.tocaLinea(barco) != null) {

				b.dispararLado(b.tocaLinea(barco));
			}
		}
	}
	
	public boolean IntercambioSonido(boolean x) {//TODO sonido
		if(x) {
			getcPrincipal().setVolumen(volumenes[0]);
			getcSecundaria().setVolumen(volumenes[1]);
			return false;
		}else {
			System.out.println("Falso");
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
		// TODO Auto-generated method stub
		
	}
}