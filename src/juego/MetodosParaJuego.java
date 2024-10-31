package juego;
import javax.swing.JOptionPane;
import entorno.Entorno;

public class MetodosParaJuego {

	public static void mostrarYouWin(int gnomoSalvado, Juego juego) {
		String mensaje = "¡You Win!\n"
				+ "Gnomos salvados:"+gnomoSalvado+"\n"
				+ "¿Quieres reiniciar?";

		int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Fin del juego GANASTE", JOptionPane.YES_NO_OPTION);
		if (opcion == JOptionPane.YES_OPTION) {
			reiniciarJuego(juego); // Método para reiniciar el juego
		} else {
			System.exit(0); // Salir del juego
		}
	}

	public static void mostrarGameOver(int gnomoSalvado, Juego juego) {
		String mensaje = "¡Game Over!\n"
				+ "Gnomos salvados:"+gnomoSalvado+"\n"
				+ "¿Quieres reiniciar?";

		int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Fin del juego", JOptionPane.YES_NO_OPTION);
		if (opcion == JOptionPane.YES_OPTION) {
			reiniciarJuego(juego); // Método para reiniciar el juego
		} else {
			System.exit(0); // Salir del juego
		}
	}

	private static void reiniciarJuego(Juego juego) {
		juego.reiniciarJuego(); // Llamar al método de reinicio en la clase Juego
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
				if(indice==1) {
					x=((e.ancho()-expansion)/(i+1)*j+expansion/2);
					islas[indice]= new Islas(x+50,y,e.ancho()/8,30);
					indice++;
				}
				else if(indice==2) {
					x=((e.ancho()-expansion)/(i+1)*j+expansion/2);
					islas[indice]= new Islas(x-50,y,e.ancho()/8,30);
					indice++;
				}
				else if(indice==3) {
					x=((e.ancho()-expansion)/(i+1)*j+expansion/2);
					islas[indice]= new Islas(x+50,y,e.ancho()/8,30);
					indice++;
				}
				else if(indice==5) {
					x=((e.ancho()-expansion)/(i+1)*j+expansion/2);
					islas[indice]= new Islas(x-50,y,e.ancho()/8,30);
					indice++;
				}
				else {
					x=(e.ancho()-expansion)/(i+1)*j+expansion/2;
					islas[indice]= new Islas(x,y,e.ancho()/8,30);
					indice++;}
			}
		}
		return islas;
	}

	public static void agregarGnomo(Gnomo[] gnomos,Entorno entorno) {
		for (int i = 0; i < gnomos.length; i++) {
			if (gnomos[i] == null) {
				gnomos[i] = new Gnomo(entorno.ancho()/2, entorno.alto()/6-26, 15, 20, 1, 2);
				gnomos[i] = new Gnomo(entorno.ancho()/2, entorno.alto()-(entorno.alto()-75), 15, 20, 1, 1);
				break; // se sale para que solo agregue un gnomo por vez
			}
		}
	}

	public static void agregarTortuga(Tortugas[] tortugas, Entorno entorno, int posYinferior, int posYsuperior) {
		int maxTortugas = (tortugas.length / 2) ; // Solo dibujar la mitad de las tortugas
		int tortugasActivas = 0;

		// Contar cuántas tortugas están activas
		for (Tortugas tortuga : tortugas) {
			if (tortuga != null) {
				tortugasActivas++;
			}
		}

		// Solo agregar tortuga si no se ha alcanzado el máximo permitido
		if (tortugasActivas < maxTortugas) {
			for (int i = 0; i < tortugas.length; i++) {
				if (tortugas[i] == null) {
					int diby = entorno.alto() - entorno.alto(); // Suponiendo que dibujas en la parte superior
					int dibx;

					// Generar una posición x válida
					do {
						dibx = (int) (Math.random() * entorno.ancho());
					} while (posicionOcupada(dibx, tortugas) || (dibx>=posYinferior && dibx<=posYsuperior) || !isFarEnough(dibx, tortugas));


					// Crear y agregar la tortuga en la posición válida
					tortugas[i] = new Tortugas(dibx, diby, 20, 30, 1);
					break; // Solo agrega una tortuga por vez		                
				}
			}
		}
	}

	private static boolean posicionOcupada(int x, Tortugas[] tortugas) {
		for (Tortugas tortuga : tortugas) {
			if (tortuga != null && Math.abs(tortuga.getX() - x) < 30) { // Distancia mínima entre tortugas
				return true; // La posición está ocupada
			}
		}
		return false; // La posición está libre
	}

	private static boolean isFarEnough(int newX, Tortugas[] tortugas) {
		final int distanciaMinima = 100; // Distancia mínima deseada entre tortugas
		for (Tortugas tortuga : tortugas) {
			if (tortuga != null && Math.abs(tortuga.getX() - newX) < distanciaMinima) {
				return false; // No está suficientemente separada
			}
		}
		return true; // Está separada
	}

}
