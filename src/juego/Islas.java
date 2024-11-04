package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Islas {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image isla;
	
	public Islas(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.isla = Herramientas.cargarImagen("imagenes/isla.png");

	}
	public void dibujar(Entorno e) {
		//e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.white);
		e.dibujarImagen(isla, this.x, this.y, 0, 0.017);
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
