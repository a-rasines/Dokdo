package dg.main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PantallaMuerte implements Screen{
	protected Stage stage;
	protected Skin skin;
	public PantallaMuerte() {
		
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table menu = new Table();
		menu.setFillParent(true);
		menu.center();
		TextButton incio = new TextButton("Jugar de nuevo", skin);
		TextButton menuPrincipal = new TextButton("Volver al menu", skin);
		TextButton salir=new TextButton("Salir", skin);
		menu.add(incio).width(100);
		menu.row();
		menu.add(menuPrincipal).width(100);
		menu.row();
		menu.add(salir).width(100);
		stage.addActor(menu);
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
