package dg.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import databasePack.DatabaseHandler;

public class AudioPlayer {
	
	private  Music musica;
	private String cancion;
	
	public void setCancion(String cancion) {
		System.out.println("cambiando cancion");
		this.cancion = cancion;
	}
	public String getCacion() {
		return this.cancion;
	}
	public Music getMusica() {
		return this.musica;
	}

	public void setMusica(Music musica) {
		this.musica = musica;
	}

	/**
	 * Agrega un sonido a la ventana
	 * @param cancion
	 */
	public void Reproducir() {
		musica = Gdx.audio.newMusic(Gdx.files.internal(cancion));
		musica.play();
		musica.setLooping(true);
		musica.setVolume(Float.parseFloat(DatabaseHandler.JSON.getString("volumen")));
		rep = true;
	}
	
	boolean rep = false;
	
	public boolean reproduciendo() {
		return rep;
	}
	/**
	 * Detiene el sonido actual
	 */
	public void detener() {
		musica.dispose();
		rep=false;
	}
	
	/**
	 * Establece el volumen del sonido
	 * @param i es un float entre 0.0 y 1 (recomendado menor que 0.5)
	 */
	public void setVolumen(float i) {
		musica.setVolume(i);
	}
	
	public float getVolumen() {
		return musica.getVolume();
	}
}
