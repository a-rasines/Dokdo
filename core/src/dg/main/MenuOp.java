package dg.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuOp extends formatoMenus{
	private static formatoMenus instaciaDeLlamada;
	//private static Logger logger= Logger.getLogger("Menu de opciones");
    private boolean visible = false;
    
	
    private static MenuOp instance;
    public static MenuOp getInstance(formatoMenus padre) {
    	if(instance == null) instance = new MenuOp(null);
    	setInstanciaDeLlamada(padre);
    	return instance;
    }
    
    public static void setInstanciaDeLlamada(formatoMenus padre) {
    	instaciaDeLlamada=padre;
    }

    public MenuOp(formatoMenus padre) {   
    	super();  
    	this.instaciaDeLlamada=padre;
    	
    }
 
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        TextButton boton1 = new TextButton("Volver", skin);
        TextButton botonV = new TextButton("Volumen", skin);
        TextButton botonC = new TextButton("Controles", skin);
        
        menu.add(boton1).width(100);
        menu.row();
        menu.add(botonV).width(100);
        menu.row();
        menu.add(botonC).width(100);
        menu.row();
        
        //TODO cambiar por el slider
        //Set table to fill stage
        //menu.setFillParent(true);
        Table volumenM = new Table();
        //botones del sonido
        TextButton cero = new TextButton("0%", skin);
        TextButton v25 = new TextButton("25%", skin);
        TextButton v50 = new TextButton("50%", skin);
        TextButton v75 = new TextButton("75%", skin);
        TextButton v100 = new TextButton("100%", skin);
      
        Actor sonido = new Image(new Texture(Gdx.files.internal("sonido2.png")));
        volumenM.add(sonido).width(40).height(20);
        volumenM.add(cero).pad(5).width(40);
        volumenM.add(v25).pad(5).width(40);
        volumenM.add(v50).pad(5).width(40);
        volumenM.add(v75).pad(5).width(40);
        volumenM.add(v100).pad(5).width(40);
        
        //menu controles movimiento
        Table teclas1= new Table();
        teclas1.debug();
        teclas1.pad(10);
        TextButton adelante = new TextButton("W", skin);
        TextButton atras = new TextButton("S", skin);
        TextButton derecha = new TextButton("D", skin);
        TextButton izquierda = new TextButton("A", skin);
        
        teclas1.add(adelante).width(50).height(50).colspan(3);
        teclas1.row();
        teclas1.add(izquierda).width(50).height(50);
        teclas1.add(atras).width(50).height(50);
        teclas1.add(derecha).width(50).height(50);
        //TODO meter un dibujo de volante en el centro de estos botones
       
        
        //menu controles disparos
        Table teclas2= new Table();
        teclas2.pad(10);
        TextButton dadelante = new TextButton("I", skin);
        TextButton datras = new TextButton("K", skin);
        TextButton dderecha = new TextButton("J", skin);
        TextButton dizquierda = new TextButton("L", skin);
        
        teclas2.add(dadelante).width(50).height(50).colspan(3);
        teclas2.row();
        teclas2.add(dizquierda).width(50).height(50);
        teclas2.add(datras).width(50).height(50);
        teclas2.add(dderecha).width(50).height(50);
        
        
        /**        
        Label anotherLabel = new Label("ANOTHER LABEL", skin); esto es para meter un texto sin fondo
        anotherLabel.setAlignment(Align.left);**/
        
        //TODO revisar para reemplazar el sonido
        Slider slider = new Slider(0, 100, 1, false, skin);
                
        /**
        Container<Table> contenedor = new Container<>();
        contenedor.setSize(300, 100); //tamaño respecto a la pantalla
        contenedor.setColor(Color.BLUE);**/
        
        
        //tabla global
        Table contenedor2 = new Table();
        contenedor2.add(slider);
        Table contenedor1 = new Table();
        contenedor1.debug();
        contenedor1.center();
        contenedor1.setFillParent(true);
        contenedor1.add(menu);
        contenedor1.add(teclas1);
        contenedor1.add(teclas2);
       
        
      
        
        stage.addActor(contenedor1);
        
      //listeners
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
           	Dokdo.getInstance().setScreen(MenuP.getInstance());
            }
        });
        cero.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0);
    			volumenes[0]=0;
        		s1.setCambios(true);
        		if(instaciaDeLlamada.getOrdenCancniones()) {
        			s1.setSelCancion(MenuP.getInstance().getcPrincipal());
        		}else {
        			s1.setSelCancion(MenuP.getInstance().getcSecundaria());
        		}
        	}
        });
        v25.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.25f);
        		s1.setCambios(true);
    			volumenes[0]=0.25f;
    			if(instaciaDeLlamada.getOrdenCancniones()) {
        			s1.setSelCancion(MenuP.getInstance().getcPrincipal());
        		}else {
        			s1.setSelCancion(MenuP.getInstance().getcSecundaria());
        		}
        	}
        });
        v50.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.50f);
        		s1.setCambios(true);
    			volumenes[0]=0.50f;
    			if(instaciaDeLlamada.getOrdenCancniones()) {
        			s1.setSelCancion(MenuP.getInstance().getcPrincipal());
        		}else {
        			s1.setSelCancion(MenuP.getInstance().getcSecundaria());
        		}
        	}
        });
        v75.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(0.75f);
        		s1.setCambios(true);
        		if(instaciaDeLlamada.getOrdenCancniones()) {
        			s1.setSelCancion(MenuP.getInstance().getcPrincipal());
        		}else {
        			s1.setSelCancion(MenuP.getInstance().getcSecundaria());
        		}
        	}
        });
        v100.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		s1.setvDestino(1);
        		s1.setCambios(true);
        		volumenes[0]=1;
        		if(instaciaDeLlamada.getOrdenCancniones()) {
        			s1.setSelCancion(MenuP.getInstance().getcPrincipal());
        		}else {
        			s1.setSelCancion(MenuP.getInstance().getcSecundaria());
        		}
        	}
        });
        botonV.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!visible) {
        			visible=true;  
        			contenedor1.add(contenedor2);	
        		}else{
        			visible=false;
        			contenedor1.removeActor(contenedor2);        			
        			}
        		}
        	});
        botonC.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!visible) {
        			visible=true;  
        			contenedor1.add(teclas1);	
        		}else{
        			visible=false;
        			contenedor1.removeActor(teclas1);        			
        			}
        		}
        	});
        
      
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
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

