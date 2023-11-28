package Vistas;
import Controladores.Controlador;
import Modelos.Bajada;
import Modelos.Jugador;

public interface IVista {

    void mostrarMensaje(String mensaje);
    void comenzarJuego();
    void mostrarMenuJuego(Jugador actual);
    void mostrarSubmenuJuego();

    void mostrarRondaActual();
    void mostrarFormasArmadasDeCadaJugador();
    void mostrarJugadorActual();
    void mostrarCartasBajadas(Jugador jugador);

    void mostrarForma(Bajada bajada);
    void mostrarCartasJugadorActual();
    void mostrarPuntosJugadores();

    void mostrarTopePozo();
    void mostrarMazo();


}
