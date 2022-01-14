package dg.main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import databasePack.DatabaseHandler;

public class MenuOp extends FormatoMenus{
	private static FormatoMenus instaciaDeLlamada;
	//TODO agregar log
	//private static Logger logger= Logger.getLogger("Menu de opciones");
    private boolean visible = false;
    private boolean teclas=false;
    private boolean cteclas=false;
    private TextButton origen;
    private int pos;
    private static int[] vTeclas;
    private String[] keys = {"moveForward", "moveBackward", "moveLeft", "moveRight", "shootForward", "shootBackward", "shootLeft", "shootRight"};
    
    
	
    private static FormatoMenus instance;
    public static FormatoMenus getInstance() {
    	if(instance == null) instance = new MenuOp();
    	return instance;
    }
    
  
    public MenuOp() {   
    	super();
    	//{Input.Keys.W,Input.Keys.S,Input.Keys.A,Input.Keys.D,Input.Keys.I,Input.Keys.K,Input.Keys.J,Input.Keys.L};
    	JSONObject keys = DatabaseHandler.JSON.getObject("keys");
    	if (vTeclas == null)
    		vTeclas = new int[]{
    				(int)(long) keys.get("moveForward"), 
    				(int)(long) keys.get("moveBackward"), 
    				(int)(long) keys.get("moveLeft"), 
    				(int)(long) keys.get("moveRight"), 
    				
    				(int)(long) keys.get("shootForward"), 
    				(int)(long) keys.get("shootBackward"), 
    				(int)(long) keys.get("shootLeft"), 
    				(int)(long) keys.get("shootRight")
    				};
    	
    }
    
    public static int getvTeclas(int x) {
    	JSONObject keys = DatabaseHandler.JSON.getObject("keys");
    	if (vTeclas == null)
    		vTeclas = new int[]{
    				(int)(long) keys.get("moveForward"), 
    				(int)(long) keys.get("moveBackward"), 
    				(int)(long) keys.get("moveLeft"), 
    				(int)(long) keys.get("moveRight"), 
    				
    				(int)(long) keys.get("shootForward"), 
    				(int)(long) keys.get("shootBackward"), 
    				(int)(long) keys.get("shootLeft"), 
    				(int)(long) keys.get("shootRight")
    				};
		return vTeclas[x];
	}

	public static void setInstanciaDeLlamada(FormatoMenus padre) {
    	instaciaDeLlamada=padre;
    }
    
	public FormatoMenus getInstaciaDeLlamada() {
		return instaciaDeLlamada;
	}

	@Override
	public void show() {
		int xy=30;
		int xyI=60;
		Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        TextButton boton1 = new TextButton("Volver", skin);
        TextButton botonV = new TextButton("Volumen", skin);
        TextButton botonC = new TextButton("Controles", skin);
        TextButton botonSSS = new TextButton("Super Secret Settings", skin);
        
		menu.add(boton1).width(100).pad(5);
		menu.row();
		menu.add(botonV).width(100).pad(5);
		menu.row();
		menu.add(botonC).width(100).pad(5);
        menu.row();
        menu.add(botonSSS).width(160).pad(5);
        
        //Contenedor de teclas
        Table contenedorTeclas= new Table();
        //menu controles movimiento
        Table teclas1= new Table();
        teclas1.pad(10);
        TextButton adelante = new TextButton(Input.Keys.toString(vTeclas[0])+" ", skin);
        TextButton atras = new TextButton(Input.Keys.toString(vTeclas[1])+" ", skin);
        TextButton derecha = new TextButton(Input.Keys.toString(vTeclas[3])+" ", skin);
        TextButton izquierda = new TextButton(Input.Keys.toString(vTeclas[2])+" ", skin);
        
        teclas1.add(adelante).width(xy).height(xy).colspan(3);
        teclas1.row();
        teclas1.add(izquierda).width(xy).height(xy);
        Actor timon = new Image(new Texture(Gdx.files.internal("timon.png")));
        teclas1.add(timon).width(xyI).height(xyI);
        teclas1.add(derecha).width(xy).height(xy);
        teclas1.row();
        teclas1.add(atras).width(xy).height(xy).colspan(xy);
   
       
        //menu controles disparos
        Table teclas2= new Table();
        teclas2.pad(10);
        TextButton dadelante = new TextButton(Input.Keys.toString(vTeclas[4])+" ", skin);
        TextButton datras = new TextButton(Input.Keys.toString(vTeclas[5])+" ", skin);
        TextButton dderecha = new TextButton(Input.Keys.toString(vTeclas[7])+" ", skin);
        TextButton dizquierda = new TextButton(Input.Keys.toString(vTeclas[6])+" ", skin);
        
        teclas2.add(dadelante).width(xy).height(xy).colspan(xy);
        teclas2.row();
        teclas2.add(dizquierda).width(xy).height(xy);
        Actor cannon = new Image(new Texture(Gdx.files.internal("sonido2.png")));
        teclas2.add(cannon).width(xyI).height(xyI);
        teclas2.add(dderecha).width(xy).height(xy);
        teclas2.row();
        teclas2.add(datras).width(xy).height(xy).colspan(3);
        
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
            	Dokdo.getInstance().setScreen(getInstaciaDeLlamada());
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
        			DatabaseHandler.JSON.write("volumen", slider.getValue()/100, true);
            		s1.setCambios(true);
            		if(((FormatoMenus) instaciaDeLlamada).getOrdenCanciones()) {
            			s1.setSelCancion(FormatoMenus.getcPrincipal());
            		}else {
            			s1.setSelCancion(FormatoMenus.getcSecundaria());
            			}
            		visible=false;
        			botonV.setText("Volumen");
        			contenedorG.removeActor(contenedorVolumen);   
        			}
        		}
        	});
        botonC.addListener(new ClickListener() {
        	//TODO crear una lista de la que cargar y descargar los valores de los controles
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
        botonSSS.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		try {
					Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=QtBDL8EiNZo"));
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
        slider.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		volumen.setText("volumen: "+slider.getValue());
        		}
        	
        	});
        
        adelante.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!cteclas) {
        			adelante.setText("?");
        			pos=0;
            		origen=adelante;
            		cteclas=true;
        		}
        	}
        });
        atras.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!cteclas) {
        			atras.setText("?");
        			pos=1;
            		origen=atras;
            		cteclas=true;
        		}
        	}
        });
        izquierda.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!cteclas) {
        			izquierda.setText("?");
            		origen=izquierda;
            		cteclas=true;
            		pos=2;
        		}
        	}
        });
        derecha.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!cteclas) {
        			derecha.setText("?");
            		origen=derecha;
            		cteclas=true;
            		pos=3;
        		}
        	}
        });
        dadelante.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!cteclas) {
        			dadelante.setText("?");
            		origen=dadelante;
            		cteclas=true;
            		pos=4;
        		}
        	}
        });
        datras.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!cteclas) {
        			datras.setText("?");
            		origen=datras;
            		cteclas=true;
            		pos=5;
        		}
        	}
        });
        dizquierda.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!cteclas) {
        			dizquierda.setText("?");
            		origen=dizquierda;
            		cteclas=true;
            		pos=6;
        		}
        	}
        });
        dderecha.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x , float y) {
        		if(!cteclas) {
        			dderecha.setText("?");
            		origen=dderecha;
            		cteclas=true;
            		pos=7;
        		}
        	}
        });
        
        getStage().addListener(new InputListener() 
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode){
            	if(cteclas) {
            		origen.setText(Keys.toString(keycode)+" ");
            		vTeclas[pos]=keycode;
            		DatabaseHandler.JSON.writeInObject("keys", keys[pos], keycode);
            	}            	
            	cteclas=false;
                return true;
            }
        });
        
        
        stage.setKeyboardFocus(contenedorG);
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

