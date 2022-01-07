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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuOp extends formatoMenus{
	private static formatoMenus instaciaDeLlamada;
	//TODO agregar log
	//private static Logger logger= Logger.getLogger("Menu de opciones");
    private boolean visible = false;
    private boolean teclas=false;
    
	
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
		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        TextButton boton1 = new TextButton("Volver", skin);
        TextButton botonV = new TextButton("Volumen", skin);
        TextButton botonC = new TextButton("Controles", skin);
        
        menu.add(boton1).width(100).pad(5);
        menu.row();
        menu.add(botonV).width(100).pad(5);
        menu.row();
        menu.add(botonC).width(100).pad(5);
        
        //Contenedor de teclas
        Table contenedorTeclas= new Table();
        //menu controles movimiento
        Table teclas1= new Table();
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
        //TODO meter un dibujo de volante en el centro de estos botones?
       
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
        
        contenedorTeclas.add(teclas1);
        contenedorTeclas.add(teclas2);
              
        //Sonido
        Slider slider = new Slider(0, 100, 1, false, skin); 
        Table contenedorVolumen = new Table();
        Label instrucionesVolumen = new Label("Presiona Aceptar para guardar los cambios", skin);
        instrucionesVolumen.setAlignment(Align.center);
        Label volumen = new Label("volumen: "+slider.getValue(), skin);//esto es para meter un texto sin fondo
        instrucionesVolumen.setAlignment(Align.center);
        contenedorVolumen.add(instrucionesVolumen);
        contenedorVolumen.row();
        contenedorVolumen.add(slider).fillX();
        contenedorVolumen.row();
        contenedorVolumen.add(volumen);
       
      //tabla global
        Table contenedorG = new Table();
        contenedorG.background(new TextureRegionDrawable(new Texture("Ocean.png")));
        contenedorG.debug();
        contenedorG.center();
        contenedorG.setFillParent(true);
        contenedorG.add(menu);
        
       
        stage.addActor(contenedorG);
        boton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if(visible) {
            		visible=false;
            		contenedorG.removeActor(contenedorVolumen);
            	}else if(teclas) {
            		teclas=false;
        			contenedorG.removeActor(contenedorTeclas);    
            	}
            	Dokdo.getInstance().setScreen(MenuP.getInstance());
            }
        });

        botonV.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!visible) {
        			if(teclas) {
        				teclas=false;
            			contenedorG.removeActor(contenedorTeclas);
        			}
        			visible=true;  
        			botonV.setText("Aceptar");
        			slider.setValue(volumenes[0]*100);
        			volumen.setText("volumen: "+slider.getValue());
        			contenedorG.add(contenedorVolumen);	
        		}else{
        			//cambio de volumen
        			s1.setvDestino(slider.getValue()/100);
        			volumenes[0]=slider.getValue()/100;
            		s1.setCambios(true);
            		if(instaciaDeLlamada.getOrdenCancniones()) {
            			s1.setSelCancion(MenuP.getInstance().getcPrincipal());
            		}else {
            			s1.setSelCancion(MenuP.getInstance().getcSecundaria());
            			}
            		visible=false;
        			botonV.setText("Volumen");
        			contenedorG.removeActor(contenedorVolumen);   
        			}
        		}
        	});
        botonC.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!teclas) {
        			if(visible) {
        				visible=false;
            			contenedorG.removeActor(contenedorVolumen);     
        			}
        			teclas=true;  
        			contenedorG.add(contenedorTeclas);	
        		}else{
        			teclas=false;
        			contenedorG.removeActor(contenedorTeclas);        			
        			}
        		}
        	});
        
        slider.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		volumen.setText("volumen: "+slider.getValue());
        		}
        	
        	});
        /**
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
        });**/
        
      
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.5f, 1f,0);
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

