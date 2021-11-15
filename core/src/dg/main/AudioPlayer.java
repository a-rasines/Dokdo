package dg.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioPlayer {

	private static Music musica;
	//colocar el param
	public static void Reproducir(String cancion) {
		musica = Gdx.audio.newMusic(Gdx.files.internal(cancion));
		musica.play();
		musica.setLooping(true);
		musica.setVolume(0.5f);
	}
	
	public static void detener() {
		musica.dispose();
	}
}
