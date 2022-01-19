package dg.main;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import databasePack.DatabaseHandler;

public class VentanaCompras extends FormatoMenus{
	private static VentanaCompras instance;
	public static VentanaCompras getInstance() {
		if(instance == null)instance = new VentanaCompras();
		return instance;
	}
	
	List<String> mejoras = Arrays.asList(new String[]{"Vela", "Casco", "Cañones", "Barco"});
	int[] lvlmax = {9, 9, 9, 3};
	Table back = new Table();
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		ClickListener list = new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				System.out.println(event.getTarget().getClass().getName());
				String mejora = String.valueOf(((Label)event.getTarget()).getText());
				try {
					String[] niveles0 = DatabaseHandler.SQL.get("Jugadores", "Nivel", "ID="+MainScreen.ID_JUGADOR).getString(1).split("");
					String[] niveles = new String[4];
					for (int i = 0; i<niveles0.length;i++){
						niveles[i] = niveles0[i];
					}
					for (int i = niveles0.length;i<4;i++){
						niveles[i] = "0";
					}
					niveles[mejoras.indexOf(mejora.split(" ")[0])] = String.valueOf(niveles[mejoras.indexOf(mejora.split(" ")[0])]+1);
					DatabaseHandler.SQL.editValue("Jugadores(Nivel)", String.join("", niveles), "ID ="+MainScreen.ID_JUGADOR);
					String[] a = mejora.split(" ");
					if(lvlmax[mejoras.indexOf(a[0])] > Integer.parseInt(a[2])) {
						a[2] = String.valueOf(Integer.parseInt(a[2])+1);
					}else
						a[2] = "Max";
					((Label)event.getTarget()).setText(String.join(" ", a));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		try {
			String[] niveles0 = DatabaseHandler.SQL.get("Jugadores", "Nivel", "ID="+MainScreen.ID_JUGADOR).getString(1).split("");
			String[] niveles = new String[4];
			for (int i = 0; i<niveles0.length;i++){
				niveles[i] = niveles0[i];
			}
			for (int i = niveles0.length;i<4;i++){
				niveles[i] = "0";
			}
			TextButton vela = new TextButton("Vela \nNivel "+niveles[0]+" Coste: 0", skin);
			TextButton casco = new TextButton("Casco \nNivel "+niveles[1]+" Coste: 0", skin);
			TextButton cannons = new TextButton("Canones \nNivel "+niveles[2]+" Coste: 0", skin);
			TextButton barco = new TextButton("Barco \nNivel "+niveles[3]+" Coste: 0", skin);
			vela.addListener(list);
			casco.addListener(list);
			cannons.addListener(list);
			barco.addListener(list);
			back.add(vela);
			back.row();
			back.add(casco);
			back.row();
			back.add(cannons);
			back.row();
			back.add(barco);
			back.center();
			back.setFillParent(true);
			stage.addActor(back);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.5f, 1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
       
	}

}
