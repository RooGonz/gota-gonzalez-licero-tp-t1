package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	// ...
	private CasaDeLosGnomos casa;
	private Gnomo[] gnomos;
	private Tortugas[] tortugas;
	private Islas[] islas;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al rescate de los Gnomos", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.casa = new CasaDeLosGnomos(entorno.ancho()/2, entorno.alto()/6-26, 60, 75);

		gnomos = new Gnomo[4];
		for (int i = 0; i < gnomos.length; i++) {
			if (gnomos[i] == null){
				gnomos[i] = new Gnomo(entorno.ancho()/2+i, entorno.alto()/6-26, 15, 20, 1, 1);
			}
			
		}

		this.tortugas= new Tortugas[5];
		for (int i = 0; i < tortugas.length; i++) {
		    tortugas[i] = new Tortugas(entorno.ancho() / 6*(i + 1), entorno.alto() - entorno.alto(), 25, 50, 1, 1);
		}

		islas = crearIslas(entorno);
		

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
		casa.dibujar(entorno);

		for (Gnomo gnomo : gnomos) {
			if(gnomo!=null) {
				gnomo.dibujar(entorno);
				gnomo.caer(entorno);
				
			}

			// Verificar colisión con la isla y cambiar de dirección una vez
			if (gnomo.colisionIsla(islas)) {
				gnomo.cambiarDireccionAleatoria();
				gnomo.mover(); 
			}
	
			// Mover el gnomo en la dirección actual
			//gnomo.mover();

			//Verifica los laterales del entorno y cambia de direccion
			if (gnomo.hayColisionDerecha(entorno) || gnomo.hayColisionIzquierda(entorno)) {
				gnomo.cambiarMovimiento();
			}
			
		}
		
		// dibujo las islas

		for (Islas isla : islas) {
			if(isla!=null) {
				isla.dibujar(entorno);				
			}
		}
		// dibujo las tortugas
		for (Tortugas tortuga : tortugas) {
			if(tortuga!=null){
				tortuga.dibujar(entorno);
				tortuga.caer();
				
			}

			if (tortuga.colisionaPorDerecha(entorno) || tortuga.colisionaPorIzquierda(entorno)) {
				tortuga.cambiarMovimiento();
			}

			//colision tortugas - islas
			if(tortuga.estaColisionandoPorAbajo(islas)){
				tortuga.moverDerecha();
			}
		}
			

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

	public static Islas[] crearIslas(Entorno e) {
		int pisos=(e.alto()/100)-1;
		Islas[] islas=new Islas[pisos*(pisos+1)/2];
		int y=0;
		int x=0;
		int indice=0;
		for(int i=1 ;i<=pisos; i++) {
			y=y+100;
			int expansion=-50*i;
			for(int j=1 ; j<=i; j++) {
				x=(e.ancho()-expansion)/(i+1)*j+expansion/2;
				islas[indice]= new Islas(x,y,145,30);
				indice++;
			}
		}
		return islas;}
}
