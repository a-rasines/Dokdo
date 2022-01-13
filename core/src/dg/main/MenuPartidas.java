package dg.main;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import databasePack.DatabaseHandler;

public class MenuPartidas extends FormatoMenus{
	protected Stage stage;
	private static Logger logger= Logger.getLogger("MenuPartidas");
	private List<TextButton> botones = new ArrayList<TextButton>();
	private static MenuPartidas instance;
	public static MenuPartidas getInstance() {
		if(instance == null) instance = new MenuPartidas();
		return instance;
	}
	public MenuPartidas() {
		super();
		getViewport().apply();
		stage = new Stage(getViewport());
	}
	@Override
	public void show() {
		ResultSet pos0 = DatabaseHandler.SQL.get("Jugadores", "*");
		Gdx.input.setInputProcessor(stage);
		Table menu = new Table();
		menu.background(new TextureRegionDrawable(new Texture("Ocean.png")));
		menu.setFillParent(true);
		menu.center();
		TextButton partida1 = new TextButton("Nueva Partida", skin);
		TextButton partida2 = new TextButton("Nueva Partida", skin);
		TextButton partida3 = new TextButton("Nueva Partida", skin);
		botones.add(partida1);
		botones.add(partida2);
		botones.add(partida3);
		try {
			int boton = 0;
			while(pos0.next()) {
				/*ResultSet islasTotales = DatabaseHandler.SQL.get("Islas", "COUNT(*)", "ID_Jugador = " + pos0.getInt("ID"));
				int iT= islasTotales.getInt(0);
				System.out.println(iT);
				ResultSet islasConquistadas = DatabaseHandler.SQL.get("Islas", "COUNT(*)", "ID_Jugador = " + pos0.getInt("ID") + "AND Conquistada = 1");
				int iC = islasConquistadas.getInt(1);*/
				String jugador = pos0.getString("Nombre")+"   "+"vida: "+pos0.getInt("Vida")+"\n"+"Nivel: "+pos0.getInt("Nivel")+"   "+"Dinero: "+pos0.getInt("Dinero")/*+"   "+"Islas Conquistadas = " + iC + "/" + iT*/;
				botones.get(boton).setText(jugador);
				logger.info("Patidas Mostradas Correctamente");
				boton++;
			}
			
		} catch (Exception e) {
			logger.warning("NO HA SIDO POSIBLE MOSTRAR PARTIDAS");
		}
		menu.add(partida1).width(500);
		menu.row();
		menu.add(partida2).width(500);
		menu.row();
		menu.add(partida3).width(500);
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
		stage.dispose();
		skin.dispose();
		Gdx.app.exit();
		
	}

}
