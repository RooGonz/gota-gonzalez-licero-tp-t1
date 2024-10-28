package juego;

import java.awt.Color;

import entorno.Entorno;

public class BolaDeFuegoPersonaje {
	private double x;
	private double y;
	private double radio;
	private double escala;
	private double velocidad;
   	private double alto;
	private boolean activo;
	private boolean direccion;
	private int desplazamiento;
	
	public BolaDeFuegoPersonaje(double x, double y, double radio, int desplazamiento, int vel) {
		this.x = x;
		this.y = y;
		this.velocidad = vel; 
		this.radio = radio;
		this.alto = 20;
		this.desplazamiento = desplazamiento;
		this.activo = activo; 
		
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, this.radio*2, Color.red);
	}
	
	//public void mover2() {
	//	this.x+=movimientoHorizontal*velocidad;
	//	this.y+=movimientoVertical*velocidad;
		
	//}
	
	public void mover(Personaje p) {
        if (p.direccion ) {
            this.x -= this.velocidad; // Si el personaje mira hacia la derecha, el disparo se mueve hacia la derecha
        } else {
            this.x += this.velocidad; // Si el personaje mira hacia la izquierda, el disparo se mueve hacia la izquierda
        }
    }
	
	public boolean colisionaPorDerecha(Entorno e) {
		if(this.x + this.radio >= e.ancho()) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean colisionaPorIzquierda(Entorno e) {
		if(this.x - this.radio <= e.ancho() - e.ancho()) {
			return true;
		}
		else {
			return false;
		}
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

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

}
