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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuP implements Screen{
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
    private HiloVolumen s1= new HiloVolumen();

    
    public MenuP(Dokdo juego, MainScreen ventana2) {
    	this.padre = this;
    	this.mar=ventana2;
    	this.game=juego;
    	s1.start();
    	//musica de fondo;
    	try {
    		AudioPlayer.Reproducir("Sonidos//DrunkenSailor.mp3");
        	logger.info("Cancion cargada sin problemas");
		} catch (Exception e) {
			// TODO: handle exception
        	logger.info("Fallo al cargar la Cancion");
		}
    	
    	
    	//dibujador de sprites
    	batch = new SpriteBatch();
    	viewport = new FitViewport(400, 400);
    	viewport.apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(viewport,batch);
    	batch = new SpriteBatch();
    
    	
    	//dibujo barco 
        bar= new Texture(Gdx.files.internal("Barco.png"));
        sprite = new Sprite(bar);
        sprite.setPosition((Gdx.graphics.getWidth()/2)-(sprite.getWidth()/2), Gdx.graphics.getHeight()/1.5f);
    	
    }
 
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        
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
            	((Game)Gdx.app.getApplicationListener()).setScreen(new MenuOp(padre,game,s1));
            		/**s1.setCambios(true);
            		s1.setDireccion(true);
                	s1.setvDestino(0.1f);**/
            	
            }
        });
        
        boton3.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		dispose();
        		       		
        	}
        });
        
       // menu.add(sprite);
        menu.add(boton1).width(100);
        menu.row();
        menu.add(boton2).width(100);
        menu.row();
        menu.add(boton3).width(100);
        
        
        
        stage.addActor(menu);
      
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.5f, 1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        sprite.draw(batch);
        batch.end();
        
        stage.act();
        stage.draw();
	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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
		skin.dispose();
		batch.dispose();
		bar.dispose();
	
		
		
	}
}

