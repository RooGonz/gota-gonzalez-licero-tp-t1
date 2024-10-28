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
	
	public BolaDeFuegoPersonaje(double x, double y, double radio, int movV, int movH, int vel) {
		this.x = x;
		this.y = y;
		this.velocidad = 2; 
        	this.radio = 10;
        	this.alto = 20;
        	this.escala = 0;
        	this.activo = true; 
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, this.radio*2, Color.red);
	}
	
	public void mover() {
		this.x+=movimientoHorizontal*velocidad;
		this.y+=movimientoVertical*velocidad;
		
	}
	
	public void mover(Personaje p) {
		if(p.getdireccionDerecha()) {
            		this.x += this.velocidad; // Si el personaje mira hacia la derecha, el disparo se mueve hacia la derecha
        	} else {
            		this.x -= this.velocidad; // Si el personaje mira hacia la izquierda, el disparo se mueve hacia la izquierda
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
