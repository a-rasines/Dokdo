package dg.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import hilos.HiloVolumen;


public class Dokdo extends Game{
	private static Dokdo instance;
	public HiloVolumen s1 = HiloVolumen.getInstance();
	public AudioPlayer cMenu= new AudioPlayer();
	public AudioPlayer Menu8Bits= new AudioPlayer();
	public float[] volumenes = {0.5f,0.0f};
	private Screen MenuP;
	private Screen menuOp;
	
	
	public static Dokdo getInstance() {
		if(instance == null)instance = new Dokdo();
		return instance;
	}
	@Override
	public void create() {
		s1.start();
		MenuP = new MenuP(Dokdo.getInstance(), s1, cMenu, Menu8Bits, volumenes, true);
		//menuOp= new MenuOp(Dokdo.getInstance(), s1, cMenu, Menu8Bits, volumenes, false, MenuP);
		//TODO Crear todas las ventnas que se van a usar para poder cambiar entre ellas
		//setScreen(new MenuP(Dokdo.getInstance(), s1, cMenu, Menu8Bits, volumenes, true)); 
		setScreen(MenuP);
		//setScreen(new MainScreen(new MenuP()));
	}
	
	

}
