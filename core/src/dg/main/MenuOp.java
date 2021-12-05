package dg.main;

import java.util.logging.Logger;

import javax.swing.JRadioButton;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuOp implements Screen {
	private final Dokdo game;
	private final Screen Origen;
	private static Logger logger= Logger.getLogger("Menu");
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    protected Skin skin;
    private Sprite sprite;
    private HiloVolumen sonido;

    
    public MenuOp(Screen origen ,Dokdo juego ,HiloVolumen sonido) {   
    	this.Origen=origen;
    	this.game=juego;
    	this.sonido=sonido;
    	    	
    	//dibujador de sprites
    	batch = new SpriteBatch();
    	viewport = new FitViewport(400, 400);
    	viewport.apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(viewport,batch);
    	batch = new SpriteBatch();
    
    	
    	//dibujo barco 

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
  
        TextButton boton1 = new TextButton("Volver", skin);
        //TODO Buscar como hacer el sistema de volumen 

        
      //listeners
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            game.setScreen(Origen); 
        	//((Game)Gdx.app.getApplicationListener()).setScreen(new MenuP());
            }
        });
        

        
       // menu.add(sprite);
        menu.add(boton1).width(100);
        menu.row();
    
        
        
        stage.addActor(menu);
      
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
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
		skin.dispose();
		batch.dispose();
		
	}
}

