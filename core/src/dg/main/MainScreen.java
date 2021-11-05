package dg.main;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import objetos.Barco;

//Pantalla en la que se va desarrollar el juego
public class MainScreen implements Screen{

	Barco barco = new Barco(10,0,0,0,null);
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			barco.left();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			barco.right();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			barco.forward();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			barco.backwards();
		}
		if(!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S)){
			barco.decelerate();
		}
		barco.printPos();
		//TODO el dibujado sigue dando problemas
		//barco.dibujar(new Texture("tileSetBarco.png"), 0, 0, barco.getX(), barco.getY(), barco.getAngle(), 32);
		
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
