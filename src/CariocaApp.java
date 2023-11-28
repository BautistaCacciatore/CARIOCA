import Controladores.Controlador;
import Controladores.ControladorConsolaGrafica;
import Modelos.Juego;
import Vistas.VistaConsola;
import Vistas.consolagrafica.ConsolaGrafica;

public class CariocaApp {
    public static void main(String[] args) {
        //CONSOLA NORMAL
        /*VistaConsola vista1 = new VistaConsola();
        Juego modelo1= new Juego();
        Controlador controlador1= new Controlador(vista1);
        controlador1.setModelo(modelo1);
        vista1.setControlador(controlador1);
        vista1.comenzarJuego();*/

        //CONSOLA GRAFICA
        ConsolaGrafica vista2 = new ConsolaGrafica();
        Juego modelo2= new Juego();
        ControladorConsolaGrafica controlador2= new ControladorConsolaGrafica(vista2);
        modelo2.addObserver(controlador2);
        controlador2.setModelo(modelo2);
        vista2.setControlador(controlador2);
        vista2.comenzarJuego();
    }
}
