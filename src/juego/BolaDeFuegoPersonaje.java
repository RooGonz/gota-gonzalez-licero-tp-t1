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

	public BolaDeFuegoPersonaje(double x, double y, boolean activo, boolean direccion) {
		this.x = x;
		this.y = y;
		this.velocidad = 2; 
		this.radio = 10;
		this.alto = 20;
		this.escala = 0;
        this.activo = activo;
        this.direccion=direccion;
	}
	

	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(this.x, this.y, this.radio*2, Color.red);
	}
	
//	public void mover() {
//		this.x+=movimientoHorizontal*velocidad;
//		this.y+=movimientoVertical*velocidad;
//		
//	}
	
	public void mover(Personaje p) {
		if(direccion==true) {
            		this.x += this.velocidad; // Si el personaje mira hacia la derecha, el disparo se mueve hacia la derecha
        	} else {
            		this.x -= this.velocidad; // Si el personaje mira hacia la izquierda, el disparo se mueve hacia la izquierda
        	}
    	}
	public boolean estaColisionandoPorDerecha(Islas[] islas) {
		for(Islas isla : islas) {
			if(isla==null) {
				continue;
			}					
		double bordeDerechoBola = this.x + (this.radio);
            	double bordeIzquierdoIsla = isla.getX() - (isla.getAncho() / 2);

            		if (bordeDerechoBola >= bordeIzquierdoIsla && bordeDerechoBola <= bordeIzquierdoIsla + velocidad) {
                		if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    			this.x = bordeIzquierdoIsla - (this.radio);
                    			return true;
                		}
            		}				
		}		
		return false;
	}
	
	public boolean estaColisionandoPorIzquierda(Islas[] islas) {
		for(Islas isla : islas) {
			if(isla==null) {
				continue;
			}
		
		double bordeIzquierdoBola = this.x - (this.radio);
            	double bordeDerechoIsla = isla.getX() + (isla.getAncho() / 2);

            		if (bordeIzquierdoBola <= bordeDerechoIsla && bordeIzquierdoBola >= bordeDerechoIsla - 10) {
                		if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    			this.x = bordeDerechoIsla + (this.radio);
                    			return true;
                		}
            		}					
		}		
		return false;
	}
	public boolean colisionaPorDerecha(Entorno e) {
		return this.x + this.radio/2 >= e.ancho();
	}
	
	public boolean colisionaPorIzquierda(Entorno e) {
		return this.x - this.radio/2 <= 0;
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
