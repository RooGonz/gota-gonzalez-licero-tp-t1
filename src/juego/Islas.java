package juego;

import java.awt.Color;

import entorno.Entorno;

public class Islas {
	private double desplazamiento = 1;
	private double velocidad = 1;
	private double x;
	private double y;
	private double ancho;
	private double alto;
	
	public Islas(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
	}
	public void dibujar(Entorno e) {
		e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.white);
  }
	public void moverDerecha() {
		this.x += desplazamiento*velocidad; 
	}
	public void moverIzquierda() {
		this.x -= this.desplazamiento*velocidad; 
	}
	
	public void cambiarMovimiento() {
		this.desplazamiento*=-1;
	}
	public boolean colisionaPorDerecha(Entorno e) {
		return this.x + this.ancho/2 >= e.ancho();
	}
	
	public boolean colisionaPorIzquierda(Entorno e) {
		return this.x - this.ancho/2 <= 0;
	}
	public boolean colisionaCon(Islas otra) {
	    // Comprobamos si hay superposición en el eje X
	    boolean colisionX = this.x + this.ancho / 2 >= otra.x - otra.ancho / 2 &&
	                        otra.x + otra.ancho / 2 >= this.x - this.ancho / 2;
	    
	    // Comprobamos si hay superposición en el eje Y
	    boolean colisionY = this.y + this.alto / 2 >= otra.y - otra.alto / 2 &&
	                        otra.y + otra.alto / 2 >= this.y - this.alto / 2;
	    
	    // Retornamos verdadero si hay colisión en ambos ejes
	    return colisionX && colisionY;
	}
	public boolean verificarColisiones(Islas[] islas) {
        for (int i = 0; i < islas.length; i++) {
            for (int j = i + 1; j < islas.length; j++) {
                if (islas[i].colisionaCon(islas[j])) {
                	return true;
                    //System.out.println("Colisión entre isla " + i + " e isla " + j);
                }
            }
        }return false;
    }



	
	public double getX() {
		return x;
  }
	public void setX(double x) {
		this.x = x;
  }
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAncho() {
		return ancho;
	}
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}
	public double getAlto() {
		return alto;
	}
	public void setAlto(double alto) {
		this.alto = alto;
	}
	
}
