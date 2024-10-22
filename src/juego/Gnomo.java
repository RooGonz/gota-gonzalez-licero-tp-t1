package juego;
import java.awt.Color;
import entorno.Entorno;
import java.util.Random;

public class Gnomo {
    private double x;
    private double y;
    private double alto;
    private double ancho;

    private int movimientoHorizontal ;
    private int velocidad;
    private double desplazamiento;
    Random random = new Random();

    public Gnomo(double x, double y, double ancho, double alto, double despl, int vel){
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = vel;
        this.desplazamiento = despl;
        this.movimientoHorizontal = 1;

    }

    public void dibujar(Entorno e){
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.MAGENTA);
    }

    public void mover(){
        
        int direccion = random.nextInt(10); //cero izquiera, 1 derecha
        if (direccion < 5){
            this.x -= 10;
        }
        else{
            this.x += 10;
        }
        //this.x += movimientoHorizontal * velocidad;
    }

    public boolean hayColisionDerecha(Entorno e){
        return this.x + this.ancho >= e.ancho();
    }

    public boolean hayColisionIzquierda(Entorno e){
        return this.x - this.ancho/2 <= 0;
    }

    public void cambiarMovimiento(){
        this.movimientoHorizontal *= -1;
    }

    public void caer(Entorno e){
        this.y += this.velocidad;
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
					return true;
				}
			}			
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



    



}
