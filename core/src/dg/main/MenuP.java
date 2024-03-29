package dg.main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuP extends FormatoMenus{
	
	
	private static boolean salir=true;
	private int count = 0;
	private static boolean regreso=false;
    private static MenuP instance;
    public static MenuP getInstance() {
    	if(instance == null) instance = new MenuP();
    	return instance;
    }
    public static void setreinicio() {
    	salir=false;
    	instance.dispose();
    	instance=null;
    	MainScreen.resetInstance();
    }
   
    public MenuP() {
    	super();
    	setOrdenCanciones(true);
    	try {
    		getcSecundaria().setCancion("Sonidos//D8Bits.mp3");
    		getcSecundaria().Reproducir();
    		getcSecundaria().setVolumen(volumenes[1]);
        	logger.info("Cancion secundaria cargada sin problemas");
		} catch (Exception a) {
			a.printStackTrace();
        	logger.info("Fallo al cargar la Cancion secundaria");
		}
    	try {
    		getcPrincipal().setCancion("Sonidos//DrunkenSailor.mp3");
    		getcPrincipal().Reproducir();
        	logger.info("Cancion principal cargada sin problemas");
		} catch (Exception e) {
        	logger.info("Fallo al cargar la Cancion principal");
        	//getcSecundaria().setVolumen(volumenes[0]);
		}
    	
    	    	
    	//dibujador de sprites
    	getViewport().apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(getViewport());
    }
 
    //getters y setters
    public static void setregreso() {
    	regreso=true;
    }
    
    
	@Override
	public void show() {
		
		if(regreso) {
			getcSecundaria().setCancion("Sonidos//D8Bits.mp3");
    		regreso=false;
			
		}

		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
    
       // volumenM.setVisible(visible);
       
        menu.background(new TextureRegionDrawable(new Texture("Ocean.png")));
        
        
        
        //Set table to fill stage
        menu.setFillParent(true);
             
        
        //Set alignment of contents in the table.
        menu.center();

        //Create buttons
        TextButton boton1 = new TextButton("Jugar", skin);
        TextButton boton2 = new TextButton("Opciones", skin);
        TextButton boton3 = new TextButton("Salir", skin);

        //parte visual del menu
        Actor barco = new Image(new Texture(Gdx.files.internal("Barco.png")));        
        menu.add(barco).width(100).height(100);
        menu.row();
        menu.add(boton1).width(80).pad(5);
        menu.row();
        menu.add(boton2).width(80).pad(5);
        menu.row();
        menu.add(boton3).width(80).pad(5);
                                
        stage.addActor(menu);
        
      //listeners
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
            	Dokdo.getInstance().setScreen(MenuPartidas.getInstance());
            }
        });
        
        boton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	MenuOp.setInstanciaDeLlamada(instance);
            	Dokdo.getInstance().setScreen(MenuOp.getInstance());           	
            }
        });
        
        boton3.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		dispose();
        		       		
        	}
        });
        
        barco.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		count++;
        		if(count >= 10)
					try {
						Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=QtBDL8EiNZo"));
					} catch (IOException | URISyntaxException e) {
						e.printStackTrace();
					}
        		if(getOrdenCanciones()) {
        			getcPrincipal().setVolumen(volumenes[1]);
            		getcSecundaria().setVolumen(volumenes[0]);
            		setOrdenCanciones(false);
        		}else {
        			getcPrincipal().setVolumen(volumenes[0]);
            		getcSecundaria().setVolumen(volumenes[1]);
            		setOrdenCanciones(true);
        		}
        	}
        });
      
	}
	
	
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.5f, 1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
	
	}

	@Override
	public void resize(int width, int height) {
		getViewport().update(width, height);
    	
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
		getcPrincipal().detener();
		getcSecundaria().detener();
		stage.dispose();
		skin.dispose();
		s1.interrupt();
		if(salir)Gdx.app.exit();
		salir=true;
	
		
		
	}
	
}

