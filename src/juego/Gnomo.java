package juego;
import entorno.Entorno;
import entorno.Herramientas;

import java.util.Random;
import java.awt.Image;

public class Gnomo {
    private double x;
    private double y;
    private double alto;
    private double ancho;
    private int movimientoHorizontal ;
    private int velocidad;
    private double desplazamiento;
    private Random random = new Random();
    private boolean enIsla;
    //variables de las imagenes de gnomo
    private Image izq;
    private Image der;
    

    
    public Gnomo(double x, double y, double ancho, double alto, double despl, int vel){
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = vel;
        this.desplazamiento = despl;
        this.movimientoHorizontal = 1;
        this.enIsla = false;
        this.izq = Herramientas.cargarImagen("imagenes/gnomoIzq.png");
        this.der = Herramientas.cargarImagen("imagenes/gnomoDer.png");
    }

    public void dibujar(Entorno e){
        if (this.movimientoHorizontal == -1){
            e.dibujarImagen(izq, this.x, this.y-9, 0, 0.05);
        }
        else{
            e.dibujarImagen(der, this.x, this.y-9, 0, 0.05);
        }
    }

    public void mover(){
        this.x += this.movimientoHorizontal*velocidad;
    }

    public void cambiarDireccionAleatoria(){
        int direccion = random.nextInt(2); //cero izquiera, 1 derecha
        if (direccion == 0){
            this.movimientoHorizontal = -1;
        }
        else{
            this.movimientoHorizontal = 1;
        }
    }

    public boolean hayColisionDerecha(Entorno e){
        return this.x + this.ancho/2 >= e.ancho();
    }

    public boolean hayColisionIzquierda(Entorno e){
        return this.x - this.ancho/2 <= 0;
    }

    public void cambiarMovimiento(){
        this.movimientoHorizontal *= -1;
    }

    public void caer(Entorno e){
        this.y ++;
    }

    public boolean colisionIsla (Islas[] is){
        for(Islas isla : is) {
			if(isla==null) {
				continue;
			}
			float bordeInferiorPersonaje = (float) (this.y + (this.alto / 2));
		    float bordeSuperiorIsla = (float) (isla.getY() - (isla.getAlto() / 2));	
			
			if(bordeInferiorPersonaje>=bordeSuperiorIsla && bordeInferiorPersonaje<=bordeSuperiorIsla +velocidad) {
				if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
					this.y=(int) bordeSuperiorIsla-(this.alto/2);

                    // Cambiar dirección aleatoria solo si no está ya en la isla
                    if (!enIsla) {
                        cambiarDireccionAleatoria();
                        enIsla = true; // Ahora está en la isla
                    }  
					return true;
				}
			}			
		}
        enIsla = false; // Si no hay colisión, no está en la isla
		return false;
    }

    public boolean bordeInferiorEntorno(Entorno e){
        return this.y + this.alto/2 >= e.alto();
    }

    public boolean colisionConTortuga(Tortugas t){
       
        if (t == null){
            return false;
        }
        
        float bordeDerGnomo = (float) (this.x + this.ancho/2);
        float bordeIzqGnomo = (float) (this.x - this.ancho/2);
        float bordeDerTortu = (float) (t.getX() + t.getAncho()/2);
        float bordeIzqTortu = (float) (t.getX() - t.getAncho()/2);

        float bordeSuperiorGnomo = (float) (this.y - (this.alto / 2));
        float bordeInferiorGnomo = (float) (this.y + (this.alto / 2));
        float bordeSuperiorTortu = (float) (t.getY() - (t.getAlto() / 2));
        float bordeInferiorTortu = (float) (t.getY() + (t.getAlto() / 2));

        if ((bordeDerGnomo >= bordeIzqTortu && bordeIzqGnomo <= bordeDerTortu) && (bordeInferiorGnomo >= bordeSuperiorTortu && bordeSuperiorGnomo <= bordeInferiorTortu)){
            //System.out.println("COLISIONTORTU ...");
            return true;
        }
        return false;
    }
    public boolean colisionConPersonaje(Personaje pep){
        
        if (pep == null){
            return false;
        }
        
        float bordeDerGnomo = (float) (this.x + this.ancho/2);
        float bordeIzqGnomo = (float) (this.x - this.ancho/2);
        float bordeDerPep = (float) (pep.getX() + pep.getAncho()/2);
        float bordeIzqPep = (float) (pep.getX() - pep.getAncho()/2);

        float bordeSuperiorGnomo = (float) (this.y - (this.alto / 2));
        float bordeInferiorGnomo = (float) (this.y + (this.alto / 2));
        float bordeSuperiorPep = (float) (pep.getY() - (pep.getAlto() / 2));
        float bordeInferiorPep = (float) (pep.getY() + (pep.getAlto() / 2));

        if ((bordeDerGnomo >= bordeIzqPep && bordeIzqGnomo <= bordeDerPep) && (bordeInferiorGnomo >= bordeSuperiorPep && bordeSuperiorGnomo <= bordeInferiorPep)){
            //System.out.println("COLISION PEP GNOMO ...");
            return true;
        }
        return false;
    }

    //getters and setters
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

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public boolean isEnIsla() {
        return enIsla;
    }

    public void setEnIsla(boolean enIsla) {
        this.enIsla = enIsla;
    }

}
