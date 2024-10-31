package juego;

import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	private CasaDeLosGnomos casa;
	private Gnomo[] gnomos;
	private Tortugas[] tortugas;
	private Islas[] islas;
	private Personaje personaje;
	private BolaDeFuegoPersonaje bolaDeFuego;

	private int posYinferior;
	private int posYsuperior;

	//variables de tiempo para los gnomos
	private long lastGnomoTime;
	private final int tiempoSpawneo = 3000; // 3 segundos en milisegundos
	//contadores en pantalla
	private int contadorBordeInferior;
	private int contadorColisionTortugas;
	private int gnomoSalvado;

	//tiempo de juego
	private long tiempoJuego; // variable para almacenar el tiempo de juego
	private long lastUpdateTime; // para calcular el tiempo transcurrido entre ticks

	private BufferedImage imagenFondo;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al rescate de los Gnomos", 1000, 700);

		// Inicializar lo que haga falta para el juego
		this.personaje = new Personaje (entorno.ancho()- (entorno.ancho()/25), entorno.alto()/2, 20, 60, 3, true);
		this.casa = new CasaDeLosGnomos(entorno.ancho()/2, entorno.alto()-(entorno.alto()-75), 60, 75);

		this.gnomos = new Gnomo[4];
		this.tortugas= new Tortugas[8];		
		islas = MetodosParaJuego.crearIslas(entorno);

		//posicion prohibida para tortuga
		this.posYinferior=entorno.ancho()/2-100;
		this.posYsuperior=entorno.ancho()/2+100;

		this.lastGnomoTime = System.currentTimeMillis(); // Inicializa el temporizador
		this.contadorBordeInferior = 0;
		this.contadorColisionTortugas = 0;
		this.gnomoSalvado = 0;
		this.tiempoJuego = 0; // iniciar el tiempo desde cero
		this.lastUpdateTime = System.currentTimeMillis(); // inicializar el tiempo de la última actualización

		// Cargar la imagen de fondo
		try {
			InputStream is = getClass().getResourceAsStream("/imagenes/imagenFondo.jpg.jpg");
			if (is != null) {
				imagenFondo = ImageIO.read(is);
			} else {
				System.err.println("No se encontró la imagen de fondo.");
			}
		} catch (IOException e) {
			System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
			e.printStackTrace();
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
		//procesamiento de un instante de tiempo
		long currentTime = System.currentTimeMillis();	    
		long deltaTime = currentTime - lastUpdateTime; // Calcular el tiempo transcurrido desde la última actualización
		tiempoJuego += deltaTime;// Actualizar el tiempo de juego	   	    
		lastUpdateTime = currentTime;// Actualizar lastUpdateTime para la próxima tick
		// ...

		if(imagenFondo!=null) {
			entorno.dibujarImagen(imagenFondo, entorno.ancho()-entorno.ancho()/2, entorno.alto()-(entorno.alto()/2), 0);
		}

		casa.dibujar(entorno);

		long tiempoDeJuego = System.currentTimeMillis();

		// Crear un nuevo gnomo cada 3 segundos si hay espacio
		if (tiempoDeJuego - lastGnomoTime >= tiempoSpawneo) {
			MetodosParaJuego.agregarGnomo(gnomos,entorno);
			lastGnomoTime = tiempoDeJuego;
		}

		for (int i = 0; i < gnomos.length; i++) {
			Gnomo gnomo = gnomos[i];
			if(gnomo != null) {
				gnomo.dibujar(entorno);
				gnomo.caer(entorno);

				//verifica colision cambia la direccion de manera aleatoria dentro del metodo
				if(gnomo.colisionIsla(islas)){
					gnomo.mover();
				}

				//Verifica los laterales del entorno y cambia de direccion
				if (gnomo.hayColisionDerecha(entorno) || gnomo.hayColisionIzquierda(entorno)) {
					gnomo.cambiarMovimiento();
				}
				//verifica que el gnomo cae al vacio y lo elimina
				if (gnomo.bordeInferiorEntorno(entorno)){
					gnomos[i] = null;
					contadorBordeInferior++; // Incrementar contador gnomos precipitados
					//System.out.print("gnomo fuera ");
				}
				// Verificar colisión con tortugas
				for (Tortugas tortuga : tortugas) {
					if (tortuga != null && gnomo.colisionConTortuga(tortuga)) {
						gnomos[i] = null; // Eliminar el gnomo
						contadorColisionTortugas++; // Incrementar contador
						break;
					}
				}
				if (gnomo.colisionConPersonaje(personaje)) {
					gnomos[i] = null; // Pep salva Gnomo
					//System.out.println("pepGnomo... ");
					gnomoSalvado++;
				}
			}
		}

		// dibujo las islas
		for (Islas isla : islas) {
			if (islas != null && islas.length > 0) {
				isla.dibujar(entorno);	
			}
		}
		// dibujo las tortugas
		for (int i = 0; i < tortugas.length; i++) {
			Tortugas tortuga = tortugas[i];
			if(tortuga!=null){
				tortuga.dibujar(entorno);
				tortuga.caer();			

				//colision tortugas - islas
				if(tortuga.estaColisionandoPorAbajo(islas)) {
					tortuga.moverIzquierda();

					//colision tortugas - entorno
					if (tortuga.colisionaPorDerecha(entorno) || tortuga.colisionaPorIzquierda(entorno)) {
						tortuga.cambiarMovimiento();
					}
					//movimiento tortugas sobre islas
					if(!tortuga.llegaAlBorde(islas)) {						
						tortuga.cambiarMovimiento();
					}
					//colision bolaDeFuego-tortuga
					if(tortuga.colisionBolaDeFuego(bolaDeFuego)) {
						tortugas[i]=null;
						bolaDeFuego=null;
					}
				}
			}
			else {
				//si una tortuga queda en null
				MetodosParaJuego.agregarTortuga(tortugas,entorno,this.posYinferior,this.posYsuperior); //agrega otra tortuga
			}
		}
		if (personaje != null){ 
			//dibujo del personaje
			personaje.dibujarse(entorno);

			//Colisiones personaje - entorno
			if(entorno.estaPresionada(entorno.TECLA_DERECHA) && !personaje.colisionaPorDerecha(entorno) && !personaje.estaColisionandoPorDerecha(islas)) {
				personaje.moverDerecha();
			}
			if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !personaje.colisionaPorIzquierda(entorno) && !personaje.estaColisionandoPorIzquierda(islas)) { 
				personaje.moverIzquierda();
			}
			if(entorno.estaPresionada(entorno.TECLA_ARRIBA) && !personaje.colisionaPorArriba(entorno) && !personaje.estaColisionandoPorArriba(islas)) {
				personaje.saltar();}

			if (!personaje.estaColisionandoPorAbajo(islas) || personaje.estaColisionandoPorArriba(islas)) 
				personaje.moverAbajo();
      
			//verifica que el Pep cae al vacio y lo elimina
			if (personaje!= null && personaje.colisionaPorAbajo(entorno)) {
				personaje = null;
				MetodosParaJuego.mostrarGameOver(gnomoSalvado, this);
				//System.out.print("Pep muerto ");
			}
			//verifica si el pep rescato el objetivo de gnomos para finalizar el juego
			if(personaje!= null && gnomoSalvado == 10) {
				personaje = null;
				MetodosParaJuego.mostrarYouWin(gnomoSalvado, this);
				//System.out.println("GANO ... ");
			}

			// Verificar colisión con tortugas
			for (Tortugas tortuga : tortugas) {
				if (tortuga != null && personaje!=null && personaje.colisionConTortuga(tortuga)) {					
					personaje = null; // Eliminar a Pep
					MetodosParaJuego.mostrarGameOver(gnomoSalvado, this);
					//System.out.println("peptortu ... ");
					break;
				}
			}				

			if(entorno.sePresiono(entorno.TECLA_ESPACIO) && bolaDeFuego==null) {
				this.bolaDeFuego = new BolaDeFuegoPersonaje(personaje.getX(), personaje.getY(), true,personaje.getdireccionDerecha());
			}
			if(this.bolaDeFuego!=null) {
				bolaDeFuego.dibujar(entorno);
				bolaDeFuego.mover(personaje);
				if(bolaDeFuego.colisionaPorDerecha(entorno) || bolaDeFuego.colisionaPorIzquierda(entorno) ||
						bolaDeFuego.estaColisionandoPorDerecha(islas) || bolaDeFuego.estaColisionandoPorIzquierda(islas)) {
					bolaDeFuego=null;
				}
			}
		}

		// Dibujar contadores en la parte superior
		entorno.cambiarFont("Arial", 18, Color.MAGENTA);
		entorno.escribirTexto("Gnomos perdidos: " + contadorBordeInferior, 20, 30);
		entorno.escribirTexto("Gnomos eliminados x tortugas: " + contadorColisionTortugas, 20, 50);
		entorno.escribirTexto("Gnomos salvados: " + gnomoSalvado, 20, 80);
		entorno.escribirTexto("Tiempo de juego: " + (tiempoJuego / 1000) + "s", 20, 100);

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

	public  void reiniciarJuego() {
		// Reinicia el personaje
		this.personaje = new Personaje(entorno.ancho() - (entorno.ancho() / 25), entorno.alto() / 2, 20, 60, 3, true);

		// Reinicia la casa
		this.casa = new CasaDeLosGnomos(entorno.ancho() / 2, entorno.alto() - (entorno.alto() - 75), 60, 75);

		// Reinicia los gnomos
		this.gnomos = new Gnomo[4];
		this.lastGnomoTime = System.currentTimeMillis(); // Reinicia el temporizador
		this.contadorBordeInferior = 0;
		this.contadorColisionTortugas = 0;
		this.gnomoSalvado = 0;

		// Reinicia las tortugas
		this.tortugas = new Tortugas[9];

		// Reinicia las islas
		this.islas = MetodosParaJuego.crearIslas(entorno);

		// Reinicia la bola de fuego
		this.bolaDeFuego = null;

		//variables que necesiten reiniciarse
		this.tiempoJuego=0;

		// Vuelve a dibujar el entorno inicial
		entorno.repaint(); 
	}
}