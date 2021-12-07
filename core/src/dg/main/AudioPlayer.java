package dg.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioPlayer {
	
	private  Music musica;
	
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
	public void Reproducir(String cancion) {
		musica = Gdx.audio.newMusic(Gdx.files.internal(cancion));
		musica.play();
		musica.setLooping(true);
		musica.setVolume(0.5f);
	}
	
	/**
	 * Detiene el sonido actual
	 */
	public void detener() {
		musica.dispose();
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
