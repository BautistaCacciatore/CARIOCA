package Controladores;
import Enumerados.Forma;
import Modelos.*;
import Vistas.IVista;
import Vistas.VistaConsola;

import java.util.ArrayList;
import java.util.HashMap;

public class Controlador {
    private VistaConsola vista;
    private Juego modelo;

    public Controlador(VistaConsola vista) {
        this.vista = vista;
        vista.setControlador(this);
    }

    public void setModelo(Juego modelo) {
        this.modelo = modelo;
    }

    public void agregarJugadores(String nickname){
        modelo.agregarJugador(new Jugador(nickname));
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

    public void nuevaRonda(){
        modelo.nuevaRonda();
    }

    public void asignarTurno(){
        modelo.asignarTurno();
    }

    public Ronda obtenerRondaActual(){
        return modelo.getRondaActual();
    }

    public Jugador jugadorActual(){
        return modelo.jugadorActual();
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

    public int cantidadCartasJugadorActual(){
        return modelo.cantidadCartasJugadorActual();
    }
}
