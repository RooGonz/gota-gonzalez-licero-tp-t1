package juego;

import java.awt.Color;

import entorno.Entorno;

public class Personaje {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	public boolean direccion;
	private double desplazamiento;

	public Personaje(double x, double y, double ancho, double alto, double desplazamiento) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.desplazamiento = desplazamiento;
		this.direccion = true;
	}
	
	public void dibujarse(Entorno e) {
		e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.yellow);
	}

	public void moverArriba() {
		this.y -= this.desplazamiento; 
	}
	public void moverAbajo() {
		this.y += this.desplazamiento; 
	}
	public void moverDerecha() {
		this.x += this.desplazamiento; 
		this.direccion = true;
	}
	public void moverIzquierda() {
		this.x -= this.desplazamiento; 
		this.direccion = false;
	}
	
	public void saltar() {
		int desplazo = 0;
		while (desplazo < 40) {
			desplazo ++;
			this.y -= this.desplazamiento;	
		}
	}
	
	public boolean colisionaPorDerecha(Entorno e) {
		if(this.x + this.ancho/2 >= e.ancho()) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean colisionaPorIzquierda(Entorno e) {
		if(this.x - this.ancho/2 <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean colisionaPorArriba(Entorno e) {
		if(this.y - this.alto/2 <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean colisionaPorAbajo(Entorno e) {
		if(this.y + this.alto/2 >= e.alto()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean estaColisionandoPorAbajo(Islas[] islas) {		
		for(Islas isla : islas) {
			if(isla==null) {
				continue;
			}
			double bordeInferiorPersonaje = this.y + (this.alto / 2);
			double bordeSuperiorIsla = isla.getY() - (isla.getAlto() / 2);	
			
			if(bordeInferiorPersonaje>=bordeSuperiorIsla && bordeInferiorPersonaje<=bordeSuperiorIsla +desplazamiento) {
				if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
					this.y=(int) bordeSuperiorIsla-(this.alto/2);
					return true;
				}
			}			
		}
		return false;
	}
	public boolean estaColisionandoPorArriba(Islas[] islas) {
		for(Islas isla : islas) {
			if(isla==null) {
				continue;
			}
			double bordeSuperiorPersonaje = this.y - (this.alto / 2);
			double bordeInferiorIsla = isla.getY() + (isla.getAlto() / 2);
		    
		    if(bordeSuperiorPersonaje <= bordeInferiorIsla && bordeSuperiorPersonaje>= bordeInferiorIsla-desplazamiento) {
				if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
					this.y=(int) bordeInferiorIsla+(this.alto/2);
					return true;
				}
			}			
		}	
		return false;
	}

	
	public boolean estaColisionandoPorDerecha(Islas[] islas) {
		for(Islas isla : islas) {
			if(isla==null) {
				continue;
			}					
			if(this.x+(this.ancho/2) == isla.getX()-(isla.getAncho()/2)) {
				if(this.y+(this.alto/2) > isla.getY()-(isla.getAlto()/2)  &&  this.y-(this.alto/2) < isla.getY()+(isla.getAlto()/2)) {
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
			if(this.x-(this.ancho/2) == isla.getX()+(isla.getAncho()/2)+1) {
				if(this.y+(this.alto/2) > isla.getY()-(isla.getAlto()/2)  &&  this.y-(this.alto/2) < isla.getY()+(isla.getAlto()/2)) {
					return true;
				}
			}			
		}		
		return false;
	}

    public boolean colisionConTortuga(Tortugas t){
       
        if (t == null){
            return false;
        }
        
        float bordeDerPep = (float) (this.x + this.ancho/2);
        float bordeIzqPep = (float) (this.x - this.ancho/2);
        float bordeDerTortu = (float) (t.getX() + t.getAncho()/2);
        float bordeIzqTortu = (float) (t.getX() - t.getAncho()/2);

        float bordeSuperiorPep = (float) (this.y - (this.alto / 2));
        float bordeInferiorPep = (float) (this.y + (this.alto / 2));
        float bordeSuperiorTortu = (float) (t.getY() - (t.getAlto() / 2));
        float bordeInferiorTortu = (float) (t.getY() + (t.getAlto() / 2));

        if ((bordeDerPep >= bordeIzqTortu && bordeIzqPep <= bordeDerTortu) && (bordeInferiorPep >= bordeSuperiorTortu && bordeSuperiorPep <= bordeInferiorTortu)){
            System.out.println("COLISION PEPTORTU ...");
            return true;
        }
        return false;
    }

	public boolean colisionConGnomo(Gnomo gnomo){
       
        if (gnomo == null){
            return false;
        }
        
        float bordeDerPep = (float) (this.x + this.ancho/2);
        float bordeIzqPep = (float) (this.x - this.ancho/2);
        float bordeDerGnomo = (float) (gnomo.getX() + gnomo.getAncho()/2);
        float bordeIzqGnomo = (float) (gnomo.getX() - gnomo.getAncho()/2);

        float bordeSuperiorPep = (float) (this.y - (this.alto / 2));
        float bordeInferiorPep = (float) (this.y + (this.alto / 2));
        float bordeSuperiorGnomo = (float) (gnomo.getY() - (gnomo.getAlto() / 2));
        float bordeInferiorGnomo = (float) (gnomo.getY() + (gnomo.getAlto() / 2));

        if ((bordeDerPep >= bordeIzqGnomo && bordeIzqPep <= bordeDerGnomo) && (bordeInferiorPep >= bordeSuperiorGnomo && bordeSuperiorPep <= bordeInferiorGnomo)){
            System.out.println("COLISION PEPGNOMO ...");
            return true;
        }
        return false;
    }

	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}
  
}


