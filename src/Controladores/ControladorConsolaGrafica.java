package Controladores;

import Enumerados.Forma;
import Modelos.*;
import Vistas.consolagrafica.ConsolaGrafica;

import java.util.ArrayList;
import java.util.HashMap;

public class ControladorConsolaGrafica implements Observador {
    private ConsolaGrafica vista;
    private Juego modelo;

    public ControladorConsolaGrafica(ConsolaGrafica vista) {
        this.vista = vista;
    }

    public void setModelo(Juego modelo) {
        this.modelo = modelo;
    }

    public void agregarJugadores(String nickname){
        modelo.agregarJugador(new Jugador(nickname));
    }

    public void nuevaRonda(){
        modelo.nuevaRonda();
    }

    public void asignarTurno(){
        modelo.asignarTurno();
    }

    public Jugador jugadorActual(){
        return modelo.jugadorActual();
    }

    public void cambiarTurno(){
        modelo.cambiarTurno();
    }

    public boolean jugadorActualSeBajo(){
        return modelo.jugadorActualSeBajo();
    }

    public void jugadorActualTomarCartaMazo(){
        modelo.jugadorActualTomarCartaMazo();
    }

    public void jugadorActualTomarCartaPozo(){
        modelo.jugadorActualTomarCartaPozo();
    }

    public void jugadorActualDejarCarta(int indice){
        modelo.jugadorActualDejarCarta(indice);
    }

    public boolean jugadorActualPuedeBajarse(){
        return modelo.jugadorActualPuedeBajarse();
    }

    public boolean jugadorActualPudoBajarCarta(int indice){
        return modelo.jugadorActualPudoBajarCarta(indice);
    }

    public boolean jugadorActualSinCartas(){
        return modelo.jugadorActualSinCartas();
    }

    public int cantidadJugadores(){
        return modelo.cantidadJugadores();
    }

    public void repartir(){
        modelo.repartir();
    }


    public int cantidadCartasJugadorActual(){
        return modelo.cantidadCartasJugadorActual();
    }

    public Ronda obtenerRondaActual(){
        return modelo.getRondaActual();
    }

    public boolean finRonda(Jugador jugador){
        return modelo.finRonda(jugador);
    }

    public Jugador ganadorRonda(){
        return modelo.ganadorRonda();
    }

    public Jugador ganadorJuego(){
        return modelo.ganadorJuego();
    }

    public void cargarPuntos(){
        modelo.cargarPuntos();
    }

    public boolean finJuego(){
        return modelo.finJuego();
    }

    public ArrayList<Jugador> obtenerJugadores(){
        return modelo.getJugadores();
    }

    public ArrayList<Carta> cartasJugadorActual(){
        return modelo.cartasJugadorActual();
    }

    public String obtenerTopePozo(){
        return modelo.obtenerTopePozo();
    }

    public int obtenerNumeroRondaActual(){
        return modelo.obtenerNumeroRondaActual();
    }

    public HashMap<Forma, Integer> obtenerFormasRondaActual(){
        return modelo.obtenerFormasRondaActual();
    }

    public ArrayList<Bajada> formasArmadasJugador(int indice){
        return modelo.formasArmadas(indice);
    }

    public ArrayList<Bajada> formasArmadasJugador(Jugador jugador){
        return modelo.formasArmadas(jugador);
    }

    public String obtenerNickname(Jugador jugador){
        return modelo.obtenerNickname(jugador);
    }

    public int obtenerPuntosJugador(Jugador jugador){
        return modelo.obtenerPuntosJugador(jugador);
    }

    public Forma obtenerNombreForma(Bajada bajada){
        return modelo.obtenerNombreForma(bajada);
    }

    public ArrayList<Carta> cartasQueFormanLaBajada(Bajada bajada){
        return modelo.cartasQueFormanLaBajada(bajada);
    }

    public boolean jugadorActualSeBajoRecien(){
        return modelo.jugadorActual().getSeBajoRecien();
    }

    public void setJugadorActualSeBajoRecien(boolean a){
        modelo.jugadorActual().setSeBajoRecien(a);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Evento) {
            switch ((Evento) arg) {
                case NUEVA_RONDA:
                    this.vista.mostrarRondaActual();
                    break;
                case JUGADOR_TOMO_CARTA_POZO:
                    this.vista.mostrarJugadorTomoCartaPozo();
                    break;
                case JUGADOR_TOMO_CARTA_MAZO:
                    this.vista.mostrarJugadorTomoCartaMazo();
                    break;
                case JUGADOR_SE_BAJO:
                    this.vista.mostrarJugadorSeBajo();
                    break;
                case JUGADOR_NO_PUDO_BAJARSE:
                    this.vista.mostrarJugadorNoPudoBajarse();
                    break;
                case JUGADOR_DEJO_CARTA:
                    this.vista.mostrarJugadorDejoCarta();
                    break;
                case JUGADOR_BAJO_CARTA:
                    this.vista.mostrarJugadorBajoCarta();
                    break;
                case JUGADOR_NO_PUDO_BAJAR_CARTA:
                    this.vista.mostrarJugadorNoPudoBajarCarta();
                    break;
                case TERMINO_RONDA:
                    this.vista.mostrarTerminoRonda();
                    break;
                case TERMINO_JUEGO:
                    this.vista.mostrarFinJuego();
                    break;
                default:
                    break;

            }
        }
    }
}
