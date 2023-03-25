package dg.main;

import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hilos.HiloVolumen;

public class FormatoMenus implements Screen{
	
	private  boolean ordenCanciones;
	private  static  AudioPlayer cPrincipal= new AudioPlayer();
	private  static  AudioPlayer cSecundaria= new AudioPlayer();
	protected static Logger logger= Logger.getLogger("Menu");
	
	//elementos menu
    protected Stage stage;
    private Viewport viewport;
	public static float[] volumenes;
    protected Skin skin;
	public  HiloVolumen s1 = HiloVolumen.getInstance();
   
    private static Screen instance;
    public static Screen getInstance() {
    	if(instance == null) instance = new FormatoMenus();
    	return instance;
    }
    public FormatoMenus() {
    	setViewport(new FitViewport(500, 280));
    	getViewport().apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(getViewport());
    }
    
    public Stage getStage() {
    	return stage;
    }
    
	public static AudioPlayer getcPrincipal() {
		return cPrincipal;
	}
	public void setcPrincipal(AudioPlayer cPrincipal) {
		this.cPrincipal = cPrincipal;
	}
	//getters y setters	
	public static AudioPlayer getcSecundaria() {
		return cSecundaria;
	}
	public void setcSecundaria(AudioPlayer cSecundaria) {
		this.cSecundaria = cSecundaria;
	}
        
	public boolean getOrdenCanciones() {
		return ordenCanciones;
	}
	public void setOrdenCanciones(boolean ordenCancniones) {
		this.ordenCanciones = ordenCancniones;
	}
	public Viewport getViewport() {
		return viewport;
	}
	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
