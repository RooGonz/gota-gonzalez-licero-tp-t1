package juego;
import java.awt.Color;
import entorno.Entorno;

public class CasaDeLosGnomos {
    private double x;
    private double y;
    private double alto;
    private double ancho;

    public CasaDeLosGnomos(double x, double y, double ancho, double alto){
        this.x = x;
        this.y = y;
        this.alto = alto;
        this.ancho = ancho;
    }
    

    public void dibujar(Entorno e){
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.ORANGE);
    }

    
}
