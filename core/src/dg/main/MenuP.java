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
	
	private  boolean orden;
	//TODO declaraci�n de la ventana de los distintos incicios de seci�n 
	private   AudioPlayer cPrincipal= new AudioPlayer();
	private   AudioPlayer cSecundaria= new AudioPlayer();
	private  Logger logger= Logger.getLogger("Menu");
    protected Stage stage;
    private Viewport viewport;
	public static float[] volumenes = {0.5f,0.0f};
	public static boolean ordenCanciones = true;
    protected Skin skin;
	public  HiloVolumen s1 = HiloVolumen.getInstance();
    private boolean visible = false;
   
    private static MenuP instance;
    public static MenuP getInstance() {
    	if(instance == null) instance = new MenuP();
    	return instance;
    }
    public MenuP() {
    	//si quiero conservar la herencia, crear una nueva clase hija para el menu y mover las canciones
    	//s1.start();
    	
    	try {
    		cSecundaria.setCancion("Sonidos//D8Bits.mp3");
    		cSecundaria.Reproducir();
    		cSecundaria.setVolumen(volumenes[1]);
        	logger.info("Cancion secundaria cargada sin problemas");
		} catch (Exception a) {
			a.printStackTrace();
        	logger.info("Fallo al cargar la Cancion secundaria");
		}
    	try {
    		System.out.println("paso2");
    		cPrincipal.setCancion("Sonidos//DrunkenSailor.mp3");
    		cPrincipal.Reproducir();
        	logger.info("Cancion principal cargada sin problemas");
		} catch (Exception e) {
        	logger.info("Fallo al cargar la Cancion principal");
        	cSecundaria.setVolumen(volumenes[0]);
		}
    	
    	
    	
    	
    	//dibujador de sprites
    	viewport = new FitViewport(480, 280);
    	viewport.apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(viewport);

    	
    }
 
    //getters y setters
    
    
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
            	cPrincipal.detener();
            	cSecundaria.detener();
            	Dokdo.getInstance().setScreen(MainScreen.getInstance());
            }
        });
        
        boton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Dokdo.getInstance().setScreen(MenuOp.getInstance());
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
        			s1.setSelCancion(cPrincipal);
        		}else {
        			s1.setSelCancion(cSecundaria);
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
        			s1.setSelCancion(cPrincipal);
        		}else {
        			s1.setSelCancion(cSecundaria);
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
        			s1.setSelCancion(cPrincipal);
        		}else {
        			s1.setSelCancion(cSecundaria);
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
        			s1.setSelCancion(cPrincipal);
        		}else {
        			s1.setSelCancion(cSecundaria);
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
        			s1.setSelCancion(cPrincipal);
        		}else {
        			s1.setSelCancion(cSecundaria);
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
        			cPrincipal.setVolumen(volumenes[1]);
            		cSecundaria.setVolumen(volumenes[0]);
            		orden=false;
        		}else {
        			cPrincipal.setVolumen(volumenes[0]);
            		cSecundaria.setVolumen(volumenes[1]);
            		orden=true;
        		}
        	}
        });
                        
        stage.addActor(menu);
      
	}
	
	
	public boolean isOrden() {
		return orden;
	}

	public void setOrden(boolean orden) {
		this.orden = orden;
	}

	public AudioPlayer getcPrincipal() {
		return cPrincipal;
	}

	public void setcPrincipal(AudioPlayer cPrincipal) {
		this.cPrincipal = cPrincipal;
	}

	public AudioPlayer getcSecundaria() {
		return cSecundaria;
	}

	public void setcSecundaria(AudioPlayer cSecundaria) {
		this.cSecundaria = cSecundaria;
	}

	public HiloVolumen getS1() {
		return s1;
	}

	public void setS1(HiloVolumen s1) {
		this.s1 = s1;
	}

	public static float[] getVolumenes() {
		return volumenes;
	}

	public static void setVolumenes(float[] volumenes) {
		MenuP.volumenes = volumenes;
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
		cPrincipal.detener();
		stage.dispose();
		skin.dispose();
		s1.interrupt();
		Gdx.app.exit();
	
		
		
	}
}

