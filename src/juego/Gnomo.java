package juego;
import java.awt.Color;
import entorno.Entorno;

public class Gnomo {
    private double x;
    private double y;
    private double alto;
    private double ancho;

    private int movimientoHorizontal ;
    private int velocidad;

    public Gnomo(double x, double y, double ancho, double alto, int vel){
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = vel;
        this.movimientoHorizontal = 1;

    }

    public void dibujar(Entorno e){
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.MAGENTA);
    }

    public void mover(){
        this.x += movimientoHorizontal * velocidad;
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
        for (Islas i: is){
            if (this.y + this.alto <= i.getY() - i.getAlto()){
                        return true;
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
