package dg.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuOp extends formatoMenus{
	//private static Logger logger= Logger.getLogger("Menu");
	
    private boolean visible = false;
    
	
    private static MenuOp instance;
    public static MenuOp getInstance() {
    	if(instance == null) instance = new MenuOp();
    	return instance;
    }

    public MenuOp() {   
    	super();  
    	
    }
 
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
		Table contenedor = new Table();
        Table menu = new Table();
        Table volumenM = new Table();
        
        //Set table to fill stage
        menu.setFillParent(true);
             
        //Set alignment of contents in the table.
        contenedor.left();
        menu.center();

        TextButton boton1 = new TextButton("Volver", skin);
        TextButton botonV = new TextButton("Volumen", skin);
        
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
           	Dokdo.getInstance().setScreen(MenuP.getInstance());
            }
        });
        
        Actor sonido = new Image(new Texture(Gdx.files.internal("sonido2.png")));
        volumenM.add(sonido).width(40).height(20);
        volumenM.add(cero).pad(5).width(40);
        volumenM.add(v25).pad(5).width(40);
        volumenM.add(v50).pad(5).width(40);
        volumenM.add(v75).pad(5).width(40);
        volumenM.add(v100).pad(5).width(40);
        
        //distribucion menu de opciones
        menu.add(boton1).width(100);
        menu.row();
        contenedor.add(botonV);
        contenedor.add(volumenM);
        menu.add(contenedor);
 
        
        stage.addActor(menu);
           
        
        cero.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0);
    			volumenes[0]=0;
        		s1.setCambios(true);
        		if(getOrdenCancniones()) {
        			s1.setSelCancion(getcPrincipal());
        		}else {
        			s1.setSelCancion(getcSecundaria());
        		}
        	}
        });
        v25.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.25f);
        		s1.setCambios(true);
    			volumenes[0]=0.25f;
    			if(getOrdenCancniones()) {
        			s1.setSelCancion(getcPrincipal());
        		}else {
        			s1.setSelCancion(getcSecundaria());
        		}
        	}
        });
        v50.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.50f);
        		s1.setCambios(true);
    			volumenes[0]=0.50f;
    			if(getOrdenCancniones()) {
        			s1.setSelCancion(getcPrincipal());
        		}else {
        			s1.setSelCancion(getcSecundaria());
        		}
        	}
        });
        v75.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.75f);
        		s1.setCambios(true);
        		if(getOrdenCancniones()) {
        			s1.setSelCancion(getcPrincipal());
        		}else {
        			s1.setSelCancion(getcSecundaria());
        		}
        	}
        });
        v100.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(1);
        		s1.setCambios(true);
        		volumenes[0]=1;
        		if(getOrdenCancniones()) {
        			s1.setSelCancion(getcPrincipal());
        		}else {
        			s1.setSelCancion(getcSecundaria());
        		}
        	}
        });
        
        
        
      
      
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        stage.act();
        stage.draw();
	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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
		skin.dispose();
		
	}
}

