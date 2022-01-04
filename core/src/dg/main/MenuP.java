package dg.main;

import java.util.logging.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hilos.HiloVolumen;

public class MenuP implements Screen{
	
	public static boolean orden;
	private final Dokdo game;
	private Screen padre;
	private Screen mar;
	//TODO declaración de la ventana de los distintos incicios de seción
	private Screen PantallaGuardados;
	private Screen opciones; 
	public static AudioPlayer cMenu= new AudioPlayer();
	public static AudioPlayer Menu8Bits= new AudioPlayer();
	private static Logger logger= Logger.getLogger("Menu");
    protected Stage stage;
    private Viewport viewport;
    protected Skin skin;
    private static boolean visible = false;
    public HiloVolumen s1 = HiloVolumen.getInstance();
    public static float[] volumenes = {0.5f,0.0f};

    
    public MenuP(Dokdo juego, HiloVolumen sonido,AudioPlayer cancion1,AudioPlayer cancion2, float[] volumen,boolean ordencanciones) {
    	this.padre = this;
    	this.mar= new MainScreen(padre);
    	this.game=Dokdo.getInstance();
    	this.opciones= new MenuOp(this, juego, sonido, volumen);
    	this.orden=true;
    	this.s1=sonido;
    	//TODO ordenar esto en la la clase Dorko
    	s1.start();
    	
    	
    	try {
    		Menu8Bits.setCancion("Sonidos//D8Bits.mp3");
    		Menu8Bits.Reproducir();
    		Menu8Bits.setVolumen(volumenes[1]);
        	logger.info("Cancion secundaria cargada sin problemas");
		} catch (Exception a) {
			a.printStackTrace();
        	logger.info("Fallo al cargar la Cancion secundaria");
		}
    	try {
    		cMenu.setCancion("Sonidos//DrunkenSailor.mp3");
    		cMenu.Reproducir();
        	logger.info("Cancion principal cargada sin problemas");
		} catch (Exception e) {
        	logger.info("Fallo al cargar la Cancion principal");
        	Menu8Bits.setVolumen(volumenes[0]);
		}
    	
    	
    	
    	
    	//dibujador de sprites
    	viewport = new FitViewport(480, 280);
    	viewport.apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(viewport);

    	
    }
 
	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        Table volumenM = new Table();
        volumenM.setVisible(visible);
        menu.background(new TextureRegionDrawable(new Texture("Ocean.png")));
        
        //Set table to fill stage
        menu.setFillParent(true);
             
        
        //Set alignment of contents in the table.
        menu.center();

        //Create buttons
        TextButton boton1 = new TextButton("Jugar", skin);
        TextButton boton2 = new TextButton("Opciones", skin);
        TextButton boton3 = new TextButton("Salir", skin);
        
        //botones del sonido
        TextButton cero = new TextButton("0%", skin);
        TextButton v25 = new TextButton("25%", skin);
        TextButton v50 = new TextButton("50%", skin);
        TextButton v75 = new TextButton("75%", skin);
        TextButton v100 = new TextButton("100%", skin);
        
        
        
      //listeners
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	cMenu.detener();
            	Menu8Bits.detener();
            	game.setScreen(mar);
            }
        });
        
        boton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(opciones);
            	/**
            	if(visible) {
            		visible=false;
            		volumenM.setVisible(visible);
            	}else {
            		visible=true;
            		volumenM.setVisible(visible);
            	}**/
            	
            	
            	
            }
        });
        
        boton3.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		dispose();
        		       		
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
        
        
        
        //parte visual del menu
        Actor barco = new Image(new Texture(Gdx.files.internal("Barco.png")));
        Actor sonido = new Image(new Texture(Gdx.files.internal("sonido2.png")));
        
        volumenM.add(sonido).width(40).height(20);
        volumenM.add(cero).pad(5).width(40);
        volumenM.add(v25).pad(5).width(40);
        volumenM.add(v50).pad(5).width(40);
        volumenM.add(v75).pad(5).width(40);
        volumenM.add(v100).pad(5).width(40);
        
        menu.add(barco).width(100).height(100);
        menu.row();
        menu.add(boton1).width(80).pad(5);
        menu.row();
        menu.add(boton2).width(80).pad(5);
        menu.row();
        menu.add(volumenM);
        menu.row();
        menu.add(boton3).width(80).pad(5);
        
        
        barco.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(orden) {
        			cMenu.setVolumen(volumenes[1]);
            		Menu8Bits.setVolumen(volumenes[0]);
            		orden=false;
        		}else {
        			cMenu.setVolumen(volumenes[0]);
            		Menu8Bits.setVolumen(volumenes[1]);
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
		s1.interrupt();
		Gdx.app.exit();
	
		
		
	}
}

