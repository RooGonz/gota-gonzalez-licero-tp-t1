package juego;

import java.awt.Color;

import entorno.Entorno;

public class BolaDeFuegoPersonaje {
	private double x;

	private double y;
	private double radio;
	private int movimientoVertical;
	private int movimientoHorizontal;
	private int velocidad;
	
	public BolaDeFuegoPersonaje(double x, double y, double radio, int movV, int movH, int vel) {
		this.x = x;
		this.y = y;
		this.radio = radio;
		this.movimientoHorizontal = movH;
		this.movimientoVertical = movV;
		this.velocidad = vel;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, this.radio*2, Color.red);
	}
	
	public void mover() {
		this.x+=movimientoHorizontal*velocidad;
		this.y+=movimientoVertical*velocidad;
		
	}
	
	public void moverDerecha() {
		this.x += this.velocidad; 
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
