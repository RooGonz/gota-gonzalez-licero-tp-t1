package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	// ...
	private Gnomo gnomo;
	private Tortugas[] tortugas;
	private Islas[] islas;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al rescate de los Gnomos", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		gnomo = new Gnomo(entorno.ancho()/2, entorno.alto()/6, 10, 15, 1);

		this.tortugas= new Tortugas[5];
		for (int i = 0; i < tortugas.length; i++) {
		    tortugas[i] = new Tortugas(entorno.ancho() / 6*(i + 1), entorno.alto() - entorno.alto(), 25, 50, 1, 1);
		}

		this.islas= new Islas[15];
		for (int i = 0; i < islas.length; i++) {
			islas[i]= new Islas(entorno.ancho()/16*(i * 1), entorno.alto()/6*(i + 1), 100, 30);
			if (i < 1) {
				islas[i] = new Islas(entorno.ancho()/2,100,100,20);}
			else {
				if(i < 3) {	
					islas[i] = new Islas(entorno.ancho()/16*(i * 5),200,100,20);
				}
			else {
					if (i < 6) {	
						islas[i] = new Islas(entorno.ancho()/20*(i*3),300,100,30);
					}
				}
			}
	
			
		}

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y
	 * por lo tanto es el método más importante de esta clase. Aquí se debe
	 * actualizar el estado interno del juego para simular el paso del tiempo
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		// ...

		gnomo.dibujar(entorno);
		gnomo.mover();
		//gnomo.caer(entorno);
		//if (!gnomo.colisionIsla(islas)) {
		//	gnomo.caer(entorno);
		//}

		if (gnomo.hayColisionDerecha(entorno)) {
			gnomo.cambiarMovimiento();
		}
		if (gnomo.hayColisionIzquierda(entorno)) {
			gnomo.cambiarMovimiento();
		}
		// dibujo las islas

		for (Islas isla : islas) {
			isla.dibujar(entorno);
		}
		// dibujo las tortugas
		for (Tortugas tortuga : tortugas) {
			tortuga.dibujar(entorno);
			tortuga.caer();

			if (tortuga.colisionaPorDerecha(entorno) || tortuga.colisionaPorIzquierda(entorno)) {
				tortuga.cambiarMovimiento();
			}
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
