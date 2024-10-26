package juego;

import java.awt.Color;

import entorno.Entorno;

public class Tortugas {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double desplazamiento;
	private int velocidad;
	
	public Tortugas(double x, double y,double ancho, double alto, double despl) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.desplazamiento=despl;
		this.velocidad = 1;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.GREEN);
	}
	
	public void caer() {
		this.y++;
	}
	public void mover() {
			this.x+=this.desplazamiento*velocidad;
		
	}
	public void moverDerecha() {
		this.x += this.desplazamiento*velocidad; 
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
	public boolean estaColisionandoPorAbajo(Islas[] islas) {		
		for(Islas isla : islas) {
			if(isla==null) {
				continue;
			}
			float bordeInferiorPersonaje = (float) (this.y + (this.alto / 2));
		    float bordeSuperiorIsla = (float) (isla.getY() - (isla.getAlto() / 2));	
			
			if(bordeInferiorPersonaje>=bordeSuperiorIsla && bordeInferiorPersonaje<=bordeSuperiorIsla +velocidad) {
				if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
					this.y=(int) bordeSuperiorIsla-(this.alto/2);
					return true;
				}
			}			
		}
		return false;
	}

	public boolean llegaAlBorde(Islas[] islas) {		
		for(Islas isla : islas) {
			if(isla==null) {
				continue;
			}
			float bordeInferiorPersonaje = (float) (this.y + (this.alto / 2));
			float bordeSuperiorIsla = (float) (isla.getY() - (isla.getAlto() / 2));	

			if(bordeInferiorPersonaje >= bordeSuperiorIsla 
					&& bordeInferiorPersonaje <= bordeSuperiorIsla ) {
				if(this.x+(((this.ancho/2)-(this.ancho-this.ancho))-1) > isla.getX()-((isla.getAncho()/2))  &&
						this.x-((this.ancho/2)-(this.ancho-this.ancho)) < isla.getX()+(isla.getAncho()/2)-1) {

					return true;
				}
			}			
		}
		return false;
	}

	public boolean bordeInferiorEntorno(Entorno e){
		return this.y + this.alto/2 >= e.alto();
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
