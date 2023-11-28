package Vistas;
import Controladores.Controlador;
import Controladores.ControladorConsolaGrafica;
import Enumerados.Forma;
import Modelos.Bajada;
import Modelos.Carta;
import Modelos.Juego;
import Modelos.Jugador;
import Vistas.consolagrafica.ConsolaGrafica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class VistaConsola implements IVista{
    private Controlador controlador;
    Scanner scanner = new Scanner(System.in);

    public void setControlador(Controlador controlador){
        this.controlador= controlador;
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void comenzarJuego(){
        mostrarMensaje("BIENVENIDO A 'CARIOCA'");
        int cantidad= solicitarIngresoCantidadJugadores();
        for (int i=0; i<cantidad; i++){
            mostrarMensaje("Ingrese el nickname del jugador " + (i+1) + ": ");
            controlador.agregarJugadores(scanner.nextLine());
        }
        mostrarJuego();
    }

    public void mostrarJuego(){
        if (controlador.cantidadJugadores()>=2 && controlador.cantidadJugadores()<=4){
            controlador.repartir();
            boolean terminoRonda;
            boolean finJuego= false;
            Jugador actual= null;
            while(!finJuego){
                controlador.nuevaRonda();
                controlador.asignarTurno();
                terminoRonda= false;
                while (!terminoRonda){
                    mostrarMensaje("--------------------------------------");
                    mostrarRondaActual();
                    actual= controlador.jugadorActual();
                    mostrarMensaje("--------------------------------------");
                    mostrarFormasArmadasDeCadaJugador();
                    mostrarMensaje("--------------------------------------");
                    mostrarJugadorActual();
                    mostrarCartasJugadorActual();
                    mostrarMensaje("--------------------------------------");
                    mostrarTopePozo();
                    mostrarMazo();
                    mostrarMensaje("--------------------------------------");
                    mostrarMenuJuego(actual);
                    enterParaContinuar(1);
                    terminoRonda= controlador.finRonda(actual);
                    if (terminoRonda==false){
                        controlador.cambiarTurno();
                    }
                }
                Jugador j= controlador.ganadorRonda();
                mostrarMensaje("EL GANADOR DE LA RONDA ES: " + controlador.obtenerNickname(j));
                controlador.cargarPuntos();
                mostrarPuntosJugadores();
                enterParaContinuar(2);
                finJuego= controlador.finJuego();
            }
            mostrarMensaje("");
            mostrarMensaje("EL JUEGO HA TERMINADO");
            Jugador j= controlador.ganadorJuego();
            mostrarMensaje("GANADOR: " + controlador.obtenerNickname(j));
            mostrarPuntosJugadores();
        }
        else{
            mostrarMensaje("CANTIDAD DE JUGADORES INVALIDA");
        }
    }

    @Override
    public void mostrarMenuJuego(Jugador actual) {
        boolean salir= false;
        boolean recogioCarta= false;
        String opcion;
        int indiceCarta;
        boolean puedeBajarse;
        if (controlador.jugadorActualSeBajo()==false){
            mostrarMensaje("1- RECOGER CARTA DEL MAZO");
            mostrarMensaje("2- RECOGER CARTA DEL POZO");
            mostrarMensaje("3- BAJARSE (DEBE PODER FORMAR LAS FORMAS CORRESPONDIENTES)");
            mostrarMensaje("INGRESE UNA OPCION: ");
            opcion = scanner.nextLine();

            switch (opcion){
                case "1":
                    controlador.jugadorActualTomarCartaMazo();
                    mostrarMensaje("TOMO UNA CARTA DEL MAZO CON EXITO!");
                    mostrarCartasJugadorActual();
                    indiceCarta= solicitarIngresoCartaADejar();
                    controlador.jugadorActualDejarCarta(indiceCarta);
                    mostrarMensaje("HA DEJADO LA CARTA CON EXITO!");
                    break;
                case "2":
                    controlador.jugadorActualTomarCartaPozo();
                    mostrarMensaje("TOMO UNA CARTA DEL POZO CON EXITO!");
                    mostrarCartasJugadorActual();
                    indiceCarta= solicitarIngresoCartaADejar();
                    controlador.jugadorActualDejarCarta(indiceCarta);
                    mostrarMensaje("HA DEJADO LA CARTA CON EXITO!");
                    break;
                case "3":
                    puedeBajarse= controlador.jugadorActualPuedeBajarse();
                    if (puedeBajarse==true){
                        if (controlador.obtenerNumeroRondaActual()!=9 && controlador.obtenerNumeroRondaActual()!=10){
                            mostrarMensaje("SE HA BAJADO CON EXITO!");
                            mostrarCartasJugadorActual();
                            mostrarMensaje("DEBE DEJAR UNA CARTA EN EL POZO.");
                            indiceCarta= solicitarIngresoCartaADejar();
                            controlador.jugadorActualDejarCarta(indiceCarta);
                        }
                    }
                    else{
                        mostrarMensaje("NO PUEDE BAJARSE");
                        mostrarMenuJuego(actual);
                    }
                    break;
                default:
                    mostrarMensaje("OPCION INCORRECTA");
                    mostrarMenuJuego(actual);
                    break;
            }
        }
        else{
            mostrarMensaje("1- RECOGER CARTA DEL MAZO");
            mostrarMensaje("2- RECOGER CARTA DEL POZO");
            mostrarMensaje("3- DESHACER CARTA");
            mostrarMensaje("INGRESE UNA OPCION: ");
            opcion = scanner.nextLine();
            switch (opcion){
                case "1":
                    controlador.jugadorActualTomarCartaMazo();
                    mostrarMensaje("TOMO UNA CARTA DEL MAZO CON EXITO!");
                    mostrarCartasJugadorActual();
                    indiceCarta= solicitarIngresoCartaADejar();
                    controlador.jugadorActualDejarCarta(indiceCarta);
                    mostrarMensaje("HA DEJADO LA CARTA CON EXITO!");
                    break;
                case "2":
                    controlador.jugadorActualTomarCartaPozo();
                    mostrarMensaje("TOMO UNA CARTA DEL POZO CON EXITO!");
                    mostrarCartasJugadorActual();
                    indiceCarta= solicitarIngresoCartaADejar();
                    controlador.jugadorActualDejarCarta(indiceCarta);
                    mostrarMensaje("HA DEJADO LA CARTA CON EXITO!");
                    break;
                case "3":
                    indiceCarta= solicitarIngresoCartasABajar();
                    boolean pudoBajarse= controlador.jugadorActualPudoBajarCarta(indiceCarta);
                    if (pudoBajarse==true){
                        mostrarMensaje("HA BAJADO LA CARTA CON EXITO!");
                        mostrarCartasJugadorActual();
                        System.out.println("----------------------------");
                        if (controlador.jugadorActualSinCartas()){
                        }
                        else{
                            mostrarMenuJuego(actual);
                        }
                    }
                    else{
                        mostrarMensaje("NO ES POSIBLE BAJAR LA CARTA.");
                        mostrarSubmenuJuego();
                    }
                    break;
                default:
                    mostrarMensaje("OPCION INCORRECTA");
                    mostrarMenuJuego(actual);
                    break;
            }
        }

    }

    @Override
    public void mostrarSubmenuJuego() {
        Scanner scanner = new Scanner(System.in);
        mostrarMensaje("1- RECOGER CARTA DEL MAZO");
        mostrarMensaje("2- RECOGER CARTA DEL POZO");
        mostrarMensaje("INGRESE UNA OPCION: ");
        String opcion = scanner.nextLine();
        int indiceCarta;
        switch (opcion){
            case "1":
                controlador.jugadorActualTomarCartaMazo();
                mostrarMensaje("TOMO UNA CARTA DEL MAZO CON EXITO!");
                mostrarCartasJugadorActual();
                indiceCarta= solicitarIngresoCartaADejar();
                controlador.jugadorActualDejarCarta(indiceCarta);
                mostrarMensaje("HA DEJADO LA CARTA CON EXITO!");
                break;
            case "2":
                controlador.jugadorActualTomarCartaPozo();
                mostrarMensaje("TOMO UNA CARTA DEL POZO CON EXITO!");
                mostrarCartasJugadorActual();
                indiceCarta= solicitarIngresoCartaADejar();
                controlador.jugadorActualDejarCarta(indiceCarta);
                mostrarMensaje("HA DEJADO LA CARTA CON EXITO!");
                break;
            default:
                mostrarMensaje("OPCION INCORRECTA");
                mostrarSubmenuJuego();
                break;
        }
    }

    public void mostrarRondaActual(){
        mostrarMensaje("RONDA: " + controlador.obtenerNumeroRondaActual());
        mostrarMensaje("SE DEBE FORMAR: ");
        for (HashMap.Entry<Forma, Integer> entry : controlador.obtenerFormasRondaActual().entrySet()) {
            Forma forma = entry.getKey();
            int cantidad = entry.getValue();
            mostrarMensaje("Forma: " + forma + ", Cantidad: " + cantidad);
        }
    }

    public void mostrarFormasArmadasDeCadaJugador(){
        ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
        for (int i=0; i<controlador.cantidadJugadores(); i++){
            if (controlador.formasArmadasJugador(i).size()!=0){
                mostrarCartasBajadas(jugadores.get(i));
            }
        }
    }

    @Override
    public void mostrarJugadorActual() {
        Jugador j= controlador.jugadorActual();
        mostrarMensaje("ES EL TURNO DE: " + controlador.obtenerNickname(j));
    }

    public void mostrarCartasBajadas(Jugador jugador){
        mostrarMensaje("CARTAS BAJADAS DE: " + controlador.obtenerNickname(jugador));
        for (int i=0; i<controlador.formasArmadasJugador(jugador).size(); i++){
            mostrarForma(controlador.formasArmadasJugador(jugador).get(i));
        }
    }

    public void mostrarForma(Bajada bajada){
        if (controlador.obtenerNombreForma(bajada).equals(Forma.TRIO)){
            if (controlador.cartasQueFormanLaBajada(bajada).get(0).getValor().equals("$")){
                if (controlador.cartasQueFormanLaBajada(bajada).get(1).getValor().equals("$")){
                    mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(2).getValor());
                }
                else{
                    mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(1).getValor());
                }
            }
            else{
                mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor());
            }
        } else if (controlador.obtenerNombreForma(bajada).equals(Forma.ESCALA)) {
            mostrarMensaje("ESCALA " + controlador.cartasQueFormanLaBajada(bajada).get(0).getColor() + ", comienza con " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor() + " ,termina con " + controlador.cartasQueFormanLaBajada(bajada).get(controlador.cartasQueFormanLaBajada(bajada).size()-1).getValor());
        }
    }

    public void mostrarCartasJugadorActual(){
        ArrayList<Carta> cartas= controlador.cartasJugadorActual();
        for (int i=0; i<cartas.size(); i++){
            mostrarMensaje((i+1) + " - " + cartas.get(i).toString());
        }
    }

    public void mostrarPuntosJugadores(){
        ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
        for (int i=0; i<jugadores.size(); i++){
            mostrarMensaje(controlador.obtenerNickname(jugadores.get(i)) + ", PUNTOS: " + controlador.obtenerPuntosJugador(jugadores.get(i)));
        }
    }

    private void enterParaContinuar(int a){
        Scanner scanner = new Scanner(System.in);
        if (a == 1){
            mostrarMensaje("");
            mostrarMensaje("Su turno ha finalizado...");
            mostrarMensaje("Presione enter para continuar...");
            scanner.nextLine();
        } else if (a == 2 ) {
            mostrarMensaje("");
            mostrarMensaje("La ronda ha finalizado...");
            mostrarMensaje("Presione enter para continuar con una nueva ronda...");
            scanner.nextLine();
        }
    }

    public void mostrarTopePozo(){
        String a= controlador.obtenerTopePozo();
        mostrarMensaje("TOPE POZO: " + a);
    }

    public void mostrarMazo(){
        mostrarMensaje("MAZO...");
    }

    private int solicitarIngresoCantidadJugadores() {
        boolean salir = false;
        while (salir == false) {
            mostrarMensaje("Indique cuantos jugadores participaran del juego: ");
            String entrada = scanner.nextLine();
            try {
                int cantidad = Integer.parseInt(entrada);
                if (cantidad >= 2 && cantidad <= 4) {
                    return cantidad;
                } else {
                    mostrarMensaje("Debes ingresar un numero entre 2 y 4.");
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("Debes ingresar un numero entre 2 y 4.");
            }
        }
        return -1;
    }

    private int solicitarIngresoCartaADejar() {
        int indiceCarta;
        boolean salir = false;
        while (salir == false) {
            mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
            String entrada = scanner.nextLine();
            try {
                indiceCarta= Integer.parseInt(entrada);
                if (indiceCarta>=1 && indiceCarta<=controlador.cantidadCartasJugadorActual()){
                    return indiceCarta;
                }
                else{
                    mostrarMensaje("Error: Debe ingresar un numero valido");
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: Debe ingresar un numero valido");
            }
        }
        return -1;
    }

    private int solicitarIngresoCartasABajar(){
        int indiceCarta;
        boolean salir = false;
        while (salir == false) {
            mostrarMensaje("INGRESE EL NUMERO DE CARTA A BAJAR: ");
            String entrada = scanner.nextLine();
            try {
                indiceCarta= Integer.parseInt(entrada);
                if (indiceCarta>=1 && indiceCarta<=controlador.cantidadCartasJugadorActual()){
                    return indiceCarta;
                }
                else{
                    mostrarMensaje("Error: Debe ingresar un numero valido");
                }
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: Debe ingresar un numero valido");
            }
        }
        return -1;
    }

}
