package dg.main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaMuerte extends FormatoMenus{
	private static PantallaMuerte instanceFather;
	
    public static FormatoMenus getInstance() {
    	if(instanceFather == null) instanceFather = new PantallaMuerte();
    	return instanceFather;
    }
    
	public FormatoMenus getInstaciaDeLlamada() {
		return instanceFather;
	}
	protected Stage stage;
	//protected Skin skin; TODO
	public PantallaMuerte() {
		super();
		getViewport().apply();
		stage = new Stage(getViewport());
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Table menu = new Table();
		menu.background(new TextureRegionDrawable(new Texture("FondoMuerte.png")));
		menu.setFillParent(true);
		menu.center();
		TextButton cargarPartida = new TextButton("Cargar guardado", skin);
		TextButton menuPrincipal = new TextButton("Volver al menu", skin);
		TextButton salir=new TextButton("Salir", skin);
		menu.add(cargarPartida).width(100).pad(5).padTop(100);
		menu.row();
		menu.add(menuPrincipal).width(100).pad(5);;
		menu.row();
		menu.add(salir).width(100).pad(5);;
		stage.addActor(menu);
		cargarPartida.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Dokdo.getInstance().setScreen(MenuPartidas.getInstance());
			}
		});
		menuPrincipal.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MenuP.setreinicio();
				getcPrincipal().detener();
            	getcSecundaria().detener();
				Dokdo.getInstance().setScreen(MenuP.getInstance());
				
			}
		});
		salir.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
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
		//getcPrincipal().detener();
		stage.dispose();
		skin.dispose();
		Gdx.app.exit();
	}
	
}
