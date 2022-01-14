package dg.main;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
	Table back = new Table();
	Table menu = new Table();
	public MenuPartidas() {
		super();
		getViewport().apply();
		stage = new Stage(getViewport());
	}
	@Override
	public void show() {
		back.clear();
		@SuppressWarnings("unchecked")
		ResultSet pos0 = DatabaseHandler.SQL.get("Jugadores", "*", "ID IN ("+String.join(",", DatabaseHandler.JSON.getArray("users"))+")");
		Gdx.input.setInputProcessor(stage);
		back.background(new TextureRegionDrawable(new Texture("Ocean.png")));
		back.setFillParent(true);
		back.center();
		TextButton partida1 = new TextButton("Nueva Partida", skin);
		TextButton partida2 = new TextButton("Nueva Partida", skin);
		TextButton partida3 = new TextButton("Nueva Partida", skin);
		menu.clear();
		botones.clear();
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
				System.out.println("g");
				String jugador = pos0.getString("Nombre")+"   "+"vida: "+pos0.getInt("Vida")+"\n"+"Nivel: "+pos0.getInt("Nivel")+"   "+"Dinero: "+pos0.getInt("Dinero")/*+"   "+"Islas Conquistadas = " + iC + "/" + iT*/;
				botones.get(boton).setText(jugador);
				logger.info("Patidas Mostradas Correctamente");
				boton++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning("NO HA SIDO POSIBLE MOSTRAR PARTIDAS");
		}
		menu.add(partida1).width(400);
		menu.row();
		menu.add(partida2).width(400);
		menu.row();
		menu.add(partida3).width(400);
		back.add(menu);
		stage.addActor(back);
		partida1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				nuevaPartida(0);
			}
		});
		partida2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				nuevaPartida(1);
			}
		});
		partida3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				nuevaPartida(2);
			}
		});
	}
	public void nuevaPartida(int num) {
		List<?> ids = DatabaseHandler.JSON.getArray("users");
		getcPrincipal().detener();
    	getcSecundaria().detener();
    	if(ids.size()>=num+1) {
    		MainScreen.ID_JUGADOR=ids.get(num).toString();
    		logger.info("Partida cargada correctamente");
    		Dokdo.getInstance().setScreen(MainScreen.resetInstance());
    	}else {
    		logger.info("Nueva partida generada correctamente");
			TextButton superColegasDelInfierno = new TextButton("ACEPTAR", skin);
			TextField superColegasDelCielo = new TextField("INSERTA NOMBRE", skin);
			Table nuevo = new Table();
			nuevo.add(superColegasDelCielo);
			nuevo.add(superColegasDelInfierno);
			nuevo.setFillParent(true);
			nuevo.center();
			back.removeActor(menu);
			back.addActor(nuevo);
			superColegasDelInfierno.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					int value =new Random().nextInt(899999)+100000;
					while(DatabaseHandler.SQL.existsValue("Jugadores", "ID", String.valueOf(value))) {
						value =new Random().nextInt(899999)+100000;
					}
					MainScreen.ID_JUGADOR=String.valueOf(value);
					DatabaseHandler.JSON.write("users", String.valueOf(value), false);
					DatabaseHandler.SQL.addValue("Jugadores(ID, Nombre)", Integer.toString(value), "'"+superColegasDelCielo.getText()+"'");
					Dokdo.getInstance().setScreen(MainScreen.resetInstance());
				}
			});
    	}
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
