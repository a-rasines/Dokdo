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

public class MenuP implements Screen{
	private Image fondo;
	private final Dokdo game;
	private Screen padre;
	private Screen mar;
	private static Logger logger= Logger.getLogger("Menu");
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    protected Skin skin;
    private Texture bar;
    private Sprite sprite;
    private HiloVolumen s1;

    
    public MenuP(Dokdo juego, MainScreen ventana2,HiloVolumen hiloVol) {
    	this.s1=hiloVol;
    	this.padre = this;
    	this.mar=ventana2;
    	this.game=juego;
    	s1.start();
    	try {
    		AudioPlayer.Reproducir("Sonidos//DrunkenSailor.mp3");
        	logger.info("Cancion cargada sin problemas");
		} catch (Exception e) {
        	logger.info("Fallo al cargar la Cancion");
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
            	AudioPlayer.detener();
            	game.setScreen(mar);
            }
        });
        
        boton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	//((Game)Gdx.app.getApplicationListener()).setScreen(new MenuOp(padre,game,s1));
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
        
        
        
        stage.addActor(menu);
      
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.5f, 1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        //sprite.draw(batch);
        batch.end();
        
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
		AudioPlayer.detener();
		stage.dispose();
		skin.dispose();
		batch.dispose();
		s1.interrupt();
		Gdx.app.exit();
	
		
		
	}
}

