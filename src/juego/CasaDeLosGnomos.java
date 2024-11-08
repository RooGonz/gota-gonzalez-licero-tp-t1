package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class CasaDeLosGnomos {
    private double x;
    private double y;
    private double alto;
    private double ancho;
    private Image imagen;

    public CasaDeLosGnomos(double x, double y, double ancho, double alto){
        this.x = x;
        this.y = y;
        this.alto = alto;
        this.ancho = ancho;
        this.imagen = Herramientas.cargarImagen("imagenes/CasaGnomos.png");
    }
    

    public void dibujar(Entorno e){
        //e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.ORANGE);
        
        e.dibujarImagen(imagen, this.x+5, this.y-30, 0, 0.20);
    }

    
}
