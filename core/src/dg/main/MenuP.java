package dg.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuP implements Screen{
	
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camara;
    protected Skin skin;

    public MenuP() {
    	batch = new SpriteBatch();
    	viewport = new FitViewport(400, 400);
    	viewport.apply();
    	skin = new Skin(Gdx.files.internal("uiskin.json"));
    	stage = new Stage(viewport,batch);
    	
    	camara = new OrthographicCamera();
    	batch = new SpriteBatch();
    	
    	camara.position.set(camara.viewportWidth/2, camara.viewportHeight/2,0);
    	camara.update();
    	
    }
 
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        
        //Set table to fill stage
        menu.setFillParent(true);
        
        //Set alignment of contents in the table.
        menu.top();

        //Create buttons
        TextButton boton1 = new TextButton("Jugar", skin);
        TextButton boton2 = new TextButton("Opciones", skin);
        TextButton boton3 = new TextButton("Salir", skin);
        
        menu.add(boton1);
        menu.row();
        menu.add(boton2);
        menu.row();
        menu.add(boton3);
        
        stage.addActor(menu);
	}

	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        stage.act();
	        stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
		camara.position.set(camara.viewportWidth/2, camara.viewportHeight/2,0);
    	camara.update();
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
		skin.dispose();
		
	}
}