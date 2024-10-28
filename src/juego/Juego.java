package juego;

import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

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
	private Personaje personaje;
	private BolaDeFuegoPersonaje bolaDeFuego;

	//variables de tiempo para los gnomos
	private long lastGnomoTime;
    private final int tiempoSpawneo = 3000; // 3 segundos en milisegundos
	//contadores en pantalla
	private int contadorBordeInferior;
    private int contadorColisionTortugas;

    private BufferedImage imagenFondo;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al rescate de los Gnomos", 1000, 700);
		
		// Inicializar lo que haga falta para el juego
		// ...
		

		this.personaje = new Personaje (entorno.ancho()- (entorno.ancho()/25), entorno.alto()/10, 20, 60, 3, true);
		this.casa = new CasaDeLosGnomos(entorno.ancho()/2, entorno.alto()-(entorno.alto()-75), 60, 75);

		this.gnomos = new Gnomo[4];
		this.tortugas= new Tortugas[9];		
		islas = crearIslas(entorno);

		this.lastGnomoTime = System.currentTimeMillis(); // Inicializa el temporizador
		this.contadorBordeInferior = 0;
        this.contadorColisionTortugas = 0;

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
		// Procesamiento de un instante de tiempo
		// ...
		if(imagenFondo!=null) {
			entorno.dibujarImagen(imagenFondo, entorno.ancho()-entorno.ancho()/2, entorno.alto()-(entorno.alto()/2), 0);
		}

		casa.dibujar(entorno);

		long tiempoDeJuego = System.currentTimeMillis();

        // Crear un nuevo gnomo cada 3 segundos si hay espacio
        if (tiempoDeJuego - lastGnomoTime >= tiempoSpawneo) {
            agregarGnomo();
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
                if(gnomo.colisionConPersonaje(personaje)) {
                	gnomos[i]=null;
                }
			}
		}
		
		// dibujo las islas
		for (Islas isla : islas) {
			if(isla!=null) {
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
							if(tortuga.colisionConTortuga(bolaDeFuego)) {
								tortugas[i]=null;
								bolaDeFuego=null;
							}
						}
						
					}

					else {
						//si una tortuga queda en null
						agregarTortuga(); //agrega otra tortuga
					}
		}
		//dibujo del personaje
		 personaje.dibujarse(entorno);
		 
		//Colisiones personaje - entorno
			if(entorno.estaPresionada(entorno.TECLA_DERECHA) && !personaje.colisionaPorDerecha(entorno) && !personaje.estaColisionandoPorDerecha(islas))
				personaje.moverDerecha();
				
			if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !personaje.colisionaPorIzquierda(entorno) && !personaje.estaColisionandoPorIzquierda(islas))
				personaje.moverIzquierda();
				
			if(entorno.estaPresionada(entorno.TECLA_ARRIBA) && !personaje.colisionaPorArriba(entorno) && !personaje.estaColisionandoPorArriba(islas))
				personaje.saltar();

			if (!personaje.estaColisionandoPorAbajo(islas)) {
				personaje.moverAbajo();
			}

			//dubijo de la Bola de fuego Personaje
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


			// Dibujar contadores en la parte superior
			entorno.cambiarFont("Arial", 18, Color.WHITE);
			entorno.escribirTexto("Gnomos perdidos: " + contadorBordeInferior, 20, 20);
			entorno.escribirTexto("Gnomos eliminados x tortugas: " + contadorColisionTortugas, 20, 40);
			//entorno.escribirTexto("TIEMPO: " + tiempoDeJuego, 300, 20);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	private void agregarTortuga() {
		int posx;
		int dibx;
	    for (int i = 0; i < tortugas.length; i++) {
	    	if (tortugas[i] == null) {
	    		posx=(entorno.ancho()/2);//posicion de la isla mas alta
	    		dibx=entorno.ancho() / 10 * (i + 1);//ancho del entorno en el que aparece la tortuga
	    		while (dibx > posx || dibx < posx) {
	    			tortugas[i] = new Tortugas(dibx , entorno.alto() - entorno.alto(), 20, 30, 1);
	    			
	    			break; // Solo agrega una tortuga por vez
	    		}
	    	}
	    }
	}

	private void agregarGnomo() {
        for (int i = 0; i < gnomos.length; i++) {
            if (gnomos[i] == null) {
                gnomos[i] = new Gnomo(entorno.ancho()/2, entorno.alto()-(entorno.alto()-75), 15, 20, 1, 1);
                break; // se sale para que solo agregue un gnomo por vez
            }
        }
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
				if(indice<3) {
					x=((e.ancho()-expansion)/(i+1)*j+expansion/2);
					islas[indice]= new Islas(x,y,e.ancho()/7,30);
					indice++;
				}else {
				x=(e.ancho()-expansion)/(i+1)*j+expansion/2;
				islas[indice]= new Islas(x,y,e.ancho()/8,30);
				indice++;}
			}
		}
		return islas;}
}
