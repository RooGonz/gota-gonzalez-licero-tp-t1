package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double desplazamiento;	
	private boolean direccionDer;
    private boolean saltando;
	private int alturaSalto;
	private boolean estaApoyado;
	private Image Izq;
	private Image Der;


	public Personaje(double x, double y, double ancho, double alto, double desplazamiento, boolean direccionDer) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.desplazamiento = desplazamiento;
		this.direccionDer = direccionDer;
		this.saltando=false;
		this.alturaSalto=0;
		this.estaApoyado=false;
		this.Izq = Herramientas.cargarImagen("imagenes/PepIzq.png");
		this.Der = Herramientas.cargarImagen("imagenes/PepDer.png");

	}
	public void dibujarse(Entorno e) {
		if (this.direccionDer == false){
			e.dibujarImagen(Izq, this.x-5, this.y, 0, 0.15);
		}
		else{
			e.dibujarImagen(Der, this.x+5, this.y, 0, 0.15);
		}
	}
	public void moverArriba() {
		this.y -= this.desplazamiento; 
	}
	public void moverAbajo() {
		if(!estaApoyado){
		    this.y += this.desplazamiento; }
	}
	public void moverDerecha() {
		if (estaApoyado || saltando){
	        this.direccionDer = true;
	        this.x += this.desplazamiento; 
		}
	}
	public void moverIzquierda() {
		if(estaApoyado || saltando){
		    this.direccionDer = false;
			this.x -= this.desplazamiento;
		}
	}
	public void saltar() {
		if(estaApoyado ){
			saltando = true;
		}
		if(saltando){
			this.y-= this.desplazamiento*3;
			this.alturaSalto++;
		}
		if(this.alturaSalto>20){
			saltando=false;
			this.alturaSalto=0;
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
		if(this.y - this.alto/2 <= 80) {
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
					this.estaApoyado=true;
					return true;
				}
			}			
		}
		this.estaApoyado=false;
		return false;
	}
	public boolean estaColisionandoPorArriba(Islas[] islas) {
		for(Islas isla : islas) {
			if(isla==null) {
				continue;
			}
			double bordeSuperiorPersonaje = this.y - (this.alto / 2);
			double bordeInferiorIsla = isla.getY() + (isla.getAlto() / 2);

			if(bordeSuperiorPersonaje <= bordeInferiorIsla && bordeSuperiorPersonaje >= bordeInferiorIsla-10) {
				if(this.x+(this.ancho/2)-this.ancho < isla.getX()+(isla.getAncho()/2)  &&  this.x-(this.ancho/2)+this.ancho > isla.getX()-(isla.getAncho()/2)) {
					this.y=bordeInferiorIsla+(this.alto/2);
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
		double bordeDerechoPersonaje = this.x + (this.ancho / 2);
            	double bordeIzquierdoIsla = isla.getX() - (isla.getAncho() / 2);
            
            		if (bordeDerechoPersonaje >= bordeIzquierdoIsla && bordeDerechoPersonaje <= bordeIzquierdoIsla + desplazamiento) {
                		if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    			this.x = bordeIzquierdoIsla - (this.ancho / 2);
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
		double bordeIzquierdoPersonaje = this.x - (this.ancho / 2);
            	double bordeDerechoIsla = isla.getX() + (isla.getAncho() / 2);

            		if (bordeIzquierdoPersonaje <= bordeDerechoIsla && bordeIzquierdoPersonaje >= bordeDerechoIsla - desplazamiento) {
                		if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    			this.x = bordeDerechoIsla + (this.ancho / 2);
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
            //System.out.println("COLISION PEPTORTU ...");
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
            //System.out.println("COLISION PEPGNOMO ...");
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
	public boolean getdireccionDerecha() {
		return direccionDer;
	}
  
}


