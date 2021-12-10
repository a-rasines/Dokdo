package dg.main;

import java.util.logging.Logger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hilos.HiloVolumen;

public class MenuP implements Screen{
	
	private static boolean orden=true;
	private final Dokdo game;
	private Screen padre;
	private Screen mar;
	public static AudioPlayer cMenu= new AudioPlayer();
	public static AudioPlayer Menu8Bits= new AudioPlayer();
	private static Logger logger= Logger.getLogger("Menu");
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    protected Skin skin;
    private HiloVolumen s1 = HiloVolumen.getInstance();

    
    public MenuP() {
    	this.padre = this;
    	this.mar= new MainScreen();
    	this.game=Dokdo.getInstance();
    	s1.start();
    	try {
    		cMenu.setCancion("Sonidos//DrunkenSailor.mp3");
    		cMenu.Reproducir();
        	logger.info("Cancion principal cargada sin problemas");
		} catch (Exception e) {
        	logger.info("Fallo al cargar la Cancion principal");
        	//En caso de que falle la primera
        	try {
        		Menu8Bits.setCancion("Sonidos//D8Bits.mp3");
        		Menu8Bits.Reproducir();
        		Menu8Bits.setVolumen(0.5f);
            	logger.info("Cancion secundaria cargada sin problemas");
    		} catch (Exception a) {
    			e.printStackTrace();
            	logger.info("Fallo al cargar la Cancion secundaria");
    		}
		}
    	
    	
    	
    	//dibujador de sprites
    	batch = new SpriteBatch();
    	viewport = new FitViewport(480, 280);
    	viewport.apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(viewport,batch);
    	batch = new SpriteBatch();

    	
    }
 
	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        menu.background(new TextureRegionDrawable(new Texture("Ocean.png")));
        
        //Set table to fill stage
        menu.setFillParent(true);
             
        
        //Set alignment of contents in the table.
        menu.center();

        //Create buttons
        TextButton boton1 = new TextButton("Jugar", skin);
        TextButton boton2 = new TextButton("Opciones", skin);
        TextButton boton3 = new TextButton("Salir", skin);
        
      //listeners
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	cMenu.detener();
            	game.setScreen(mar);
            }
        });
        
        boton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	//((Game)Gdx.app.getApplicationListener()).setScreen(new MenuOp(padre,game,s1));
            	
            		//s1.setCancion(AudioPlayer);
            		s1.setCambios(true);
            		s1.setDireccion(true);
                	s1.setvDestino(0.1f);
            	
            }
        });
        
        boton3.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		dispose();
        		       		
        	}
        });
        
       // menu.add(sprite);
        Actor barco = new Image(new Texture(Gdx.files.internal("Barco.png")));
        menu.add(barco).width(100).height(100);
        menu.row();
        menu.add(boton1).width(80).pad(5);
        menu.row();
        menu.add(boton2).width(80).pad(5);
        menu.row();
        menu.add(boton3).width(80).pad(5);
        
        
        barco.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(orden) {
        			cMenu.setVolumen(0);
            		Menu8Bits.setVolumen(0.5f);
            		orden=false;
        		}else {
        			cMenu.setVolumen(0.5f);
            		Menu8Bits.setVolumen(0);
            		orden=true;
        		}
        	}
        });
                        
        stage.addActor(menu);
      
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.5f, 1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
	
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
    	
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
		cMenu.detener();
		stage.dispose();
		skin.dispose();
		batch.dispose();
		s1.interrupt();
		Gdx.app.exit();
	
		
		
	}
}

