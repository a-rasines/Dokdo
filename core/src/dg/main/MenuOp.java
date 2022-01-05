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

import hilos.HiloVolumen;

public class MenuOp extends MenuP{
	//private static Logger logger= Logger.getLogger("Menu");
	protected Stage stage;
    private Viewport viewport;
    protected Skin skin;
    private Sprite sprite;
	private Screen padre;

    public MenuOp(Dokdo juego, HiloVolumen sonido,AudioPlayer cancion1,AudioPlayer cancion2, float[] volumen,boolean ordencanciones,Screen superior) {   
    	super(juego, sonido, cancion1, cancion2, volumen, ordencanciones);    
    	this.padre=superior;
    	//dibujador de sprites
    	viewport = new FitViewport(400, 400);
    	viewport.apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(viewport);
    }
 
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        Table contenedor = new Table();
        
        //Set table to fill stage
        menu.setFillParent(true);
             
        //Set alignment of contents in the table.
        contenedor.center();
        menu.center();

  
        TextButton boton1 = new TextButton("Volver", skin);
        TextButton boton2 = new TextButton("Volumen", skin);
        //botones del sonido
        TextButton cero = new TextButton("0%", skin);
        TextButton v25 = new TextButton("25%", skin);
        TextButton v50 = new TextButton("50%", skin);
        TextButton v75 = new TextButton("75%", skin);
        TextButton v100 = new TextButton("100%", skin);
        

      /**  
      //listeners
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            getGame().setScreen(padre); 
            }
        });
        
        cero.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0);
    			volumenes[0]=0;
        		s1.setCambios(true);
        		if(orden) {
        			s1.setSelCancion(cMenu);
        		}else {
        			s1.setSelCancion(Menu8Bits);
        		}
        	}
        });
        v25.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.25f);
        		s1.setCambios(true);
    			volumenes[0]=0.25f;
        		if(orden) {
        			s1.setSelCancion(cMenu);
        		}else {
        			s1.setSelCancion(Menu8Bits);
        		}
        	}
        });
        v50.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.50f);
        		s1.setCambios(true);
    			volumenes[0]=0.50f;
        		if(orden) {
        			s1.setSelCancion(cMenu);
        		}else {
        			s1.setSelCancion(Menu8Bits);
        		}
        	}
        });
        v75.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.75f);
        		s1.setCambios(true);
        		volumenes[0]=0.75f;
        		if(orden) {
        			s1.setSelCancion(cMenu);
        		}else {
        			s1.setSelCancion(Menu8Bits);
        		}
        	}
        });
        v100.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(1);
        		s1.setCambios(true);
        		volumenes[0]=1;
        		if(orden) {
        			s1.setSelCancion(cMenu);
        		}else {
        			s1.setSelCancion(Menu8Bits);
        		}
        	}
        });
        
        **/
        //distribucion menu de opciones
        menu.add(boton1).width(100);
        menu.row();
        contenedor.add(menu);
    
        
        
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
		
	}
}

