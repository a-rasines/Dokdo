package dg.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import databasePack.DatabaseHandler;

public class Tienda extends FormatoMenus {
	protected Stage stage;
	private static boolean salir=true;
	private static Logger logger= Logger.getLogger("Tienda");
	private List<TextButton> botones = new ArrayList<TextButton>();
	private static Tienda instance;
	public static Tienda getInstance() {
		if(instance == null) instance = new Tienda();
		return instance;
	}
	public Tienda(){
		super();
		getViewport().apply();
		stage = new Stage(getViewport());
	}
	@Override
	public void show() {
		TextField vida = new TextField("Mejorar el casco", skin);
		TextField velocidad = new TextField("Mejorar las velas", skin);
		TextField cantCanones = new TextField("MAS CAÑONES", skin);
		TextField danyo = new TextField("Mejor municion", skin);
		TextButton compVida = new TextButton("COMPRAR", skin);
		TextButton compVelocidad = new TextButton("COMPRAR", skin);
		TextButton compCanon = new TextButton("COMPRAR", skin);
		TextButton compDanyo = new TextButton("COMPRAR", skin);
		Table nuevo = new Table();
		nuevo.setFillParent(true);
		nuevo.center();
		nuevo.debug();
		nuevo.add(vida);
		nuevo.add(compVida);
		nuevo.add(velocidad);
		nuevo.add(compVelocidad);
		nuevo.add(cantCanones);
		nuevo.add(compCanon);
		nuevo.add(danyo);
		nuevo.add(compDanyo);
		ClickListener comprar = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					int botinJug = DatabaseHandler.SQL.get("Jugadores", "Dinero", "ID = " + MainScreen.ID_JUGADOR).getInt(1);
					if(event.getTarget().equals(compVida)) {
						System.out.println("HAS COMPRADO VIDA");
					}else if(event.getTarget().equals(compVelocidad)){
						
					}else if(event.getTarget().equals(compCanon)) {
						
					}else {
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		};
		
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
	
}
