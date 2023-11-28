package Vistas.consolagrafica;
import Controladores.ControladorConsolaGrafica;
import Enumerados.Forma;
import Modelos.Bajada;
import Modelos.Carta;
import Modelos.Jugador;
import Vistas.IVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ConsolaGrafica implements IVista{
    ControladorConsolaGrafica controlador;
    private final JFrame frame;
    private JPanel contentPane;
    private JTextField txtEntrada;
    private JButton btnEnter;
    private JTextArea txtSalidaConsola;

    private int estadoCantidadJugadores;
    private int jugadoresRestantes;
    private int cantidadJugadoresAgregados;
    private int estadoMenuJuego;
    private int estadoIndiceCarta;
    private int estadoSubmenuJuego;

    public ConsolaGrafica() {
        frame = new JFrame("<CARIOCA>");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        txtSalidaConsola.setBackground(Color.BLACK);
        txtSalidaConsola.setForeground(Color.GREEN);
        txtSalidaConsola.setEditable(false);
        estadoCantidadJugadores=1;
        jugadoresRestantes=0;
        cantidadJugadoresAgregados=0;

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSalidaConsola.append(txtEntrada.getText() + "\n");
                if (estadoCantidadJugadores==1){
                    procesarIngresoCantidadJugadores(txtEntrada.getText());
                } else if (jugadoresRestantes > 0) {
                    procesarNicknameJugador(txtEntrada.getText());
                } else if (estadoMenuJuego==1) {
                    procesarMenu1(txtEntrada.getText());
                } else if (estadoIndiceCarta==1) {
                    procesarJugadorActualDejarCarta(txtEntrada.getText());
                } else if (estadoMenuJuego==2) {
                    procesarMenu2(txtEntrada.getText());
                } else if (estadoSubmenuJuego==1) {
                    procesarSubmenuJuego(txtEntrada.getText());
                } else if (estadoIndiceCarta==2) {
                    procesarJugadorActualDejarCarta2(txtEntrada.getText());
                }
                txtEntrada.setText("");
            }
        });
    }

    public void setControlador(ControladorConsolaGrafica controlador) {
        this.controlador = controlador;
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public void mostrarMensaje(String texto) {
        txtSalidaConsola.append(texto + "\n");
    }

    public void comenzarJuego() {
        mostrar();
        this.mostrarMensaje("------BIENVENIDO A CARIOCA------");
        this.mostrarMensaje("Ingrese la cantidad de jugadores: ");
    }

    private void procesarIngresoCantidadJugadores(String input){
        try {
            int cantidad = Integer.parseInt(input);
            if (cantidad >= 2 && cantidad <= 4) {
                jugadoresRestantes= cantidad;
                estadoCantidadJugadores=0;
                procesarNicknamesJugadores();
            } else {
                this.mostrarMensaje("Error: Debes ingresar un número entre 2 y 4.");
                this.mostrarMensaje("Ingrese la cantidad de jugadores: ");
            }
        } catch (NumberFormatException e) {
            this.mostrarMensaje("Error: Debes ingresar un número entre 2 y 4.");
            this.mostrarMensaje("Ingrese la cantidad de jugadores: ");
        }
    }

    private void procesarNicknamesJugadores() {
        if (jugadoresRestantes > 0) {
            this.mostrarMensaje("Ingrese el nickname del jugador " + ((cantidadJugadoresAgregados + 1)) + ": ");
        } else {
            // Puedes llamar a un método para procesar la lógica después de ingresar los nicknames.
            this.mostrarMensaje("Todos los jugadores fueron anadidos con exito.");
            iniciarRonda();
        }
    }

    private void procesarNicknameJugador(String nickname) {
        // Aquí puedes procesar el nickname, por ejemplo, guardarlo en la lista de nicknames.
        controlador.agregarJugadores(nickname);
        jugadoresRestantes--;
        cantidadJugadoresAgregados+=1;

        // Luego, procesa el siguiente nickname o realiza acciones adicionales según tu lógica.
        procesarNicknamesJugadores();
    }

    private void iniciarRonda(){
        controlador.finJuego();
        controlador.nuevaRonda();
        controlador.asignarTurno();
        mostrarFormasArmadasDeCadaJugador();
        mostrarJugadorActual();
        mostrarCartasJugadorActual();
        mostrarTopePozo();
        mostrarMazo();
        mostrarMenuJuego(controlador.jugadorActual());
    }

    private void continuarRonda(){
        Jugador actual = controlador.jugadorActual();
        boolean terminoRonda = controlador.finRonda(actual);
        if (terminoRonda == true) {
            iniciarRonda();
            return;
        }
        controlador.cambiarTurno();
        mostrarFormasArmadasDeCadaJugador();
        mostrarJugadorActual();
        mostrarCartasJugadorActual();
        mostrarTopePozo();
        mostrarMazo();
        mostrarMenuJuego(controlador.jugadorActual());
    }

    public void mostrarMenuJuego(Jugador actual) {
        if (controlador.jugadorActualSeBajo()==false){
            this.mostrarMensaje("1- RECOGER CARTA DEL MAZO");
            this.mostrarMensaje("2- RECOGER CARTA DEL POZO");
            this.mostrarMensaje("3- BAJARSE (DEBE PODER FORMAR LAS FORMAS CORRESPONDIENTES)");
            this.mostrarMensaje("INGRESE UNA OPCION: ");
            estadoMenuJuego=1;
        }
        else {
            this.mostrarMensaje("1- RECOGER CARTA DEL MAZO");
            this.mostrarMensaje("2- RECOGER CARTA DEL POZO");
            this.mostrarMensaje("3- DESHACER CARTA");
            this.mostrarMensaje("INGRESE UNA OPCION: ");
            estadoMenuJuego = 2;
        }
    }

    public void procesarJugadorActualDejarCarta(String input){
        try {
            int indiceCarta = Integer.parseInt(input);
            if ((controlador.jugadorActualSeBajo()==false) || (controlador.jugadorActualSeBajoRecien())){
                if (indiceCarta>=1 && indiceCarta<= controlador.cantidadCartasJugadorActual()){
                    controlador.jugadorActualDejarCarta(indiceCarta);
                    estadoIndiceCarta=0;
                    if (controlador.jugadorActualSeBajoRecien()){
                        controlador.setJugadorActualSeBajoRecien(false);
                    }
                    continuarRonda();
                }
                else{
                    this.mostrarMensaje("Error: Debes ingresar un número valido.");
                    this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                }
            }
            else{
                if (indiceCarta >= 1 && indiceCarta <= controlador.cantidadCartasJugadorActual()) {
                    boolean pudoBajarCarta = controlador.jugadorActualPudoBajarCarta(indiceCarta);
                    if (pudoBajarCarta == true) {
                        if (controlador.jugadorActualSinCartas()) {
                            estadoIndiceCarta = 0;
                            continuarRonda();
                        } else {
                            mostrarCartasJugadorActual();
                            estadoIndiceCarta=0;
                            mostrarMenuJuego(controlador.jugadorActual());
                        }
                    } else {
                        estadoIndiceCarta = 0;
                    }
                } else {
                    this.mostrarMensaje("Error: Debes ingresar un número valido.");
                    this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                }
            }
        } catch (NumberFormatException e) {
            this.mostrarMensaje("Error: Debes ingresar un número valido.");
            this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
        }
    }

    public void procesarJugadorActualDejarCarta2(String input){
        try {
            int indiceCarta = Integer.parseInt(input);
            if (indiceCarta>=1 && indiceCarta<= controlador.cantidadCartasJugadorActual()){
                controlador.jugadorActualDejarCarta(indiceCarta);
                continuarRonda();
            }
            else{
                this.mostrarMensaje("Error: Debes ingresar un número valido.");
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
            }
        } catch (NumberFormatException e) {
            this.mostrarMensaje("Error: Debes ingresar un número valido.");
            this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
        }
    }

    public void procesarMenu1(String input){
        boolean puedeBajarse;
        switch (input) {
            case "1":
                controlador.jugadorActualTomarCartaMazo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=1;
                break;
            case "2":
                controlador.jugadorActualTomarCartaPozo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=1;
                break;
            case "3":
                puedeBajarse = controlador.jugadorActualPuedeBajarse();
                if (puedeBajarse == true) {
                    if (controlador.obtenerNumeroRondaActual()!= 9 && controlador.obtenerNumeroRondaActual()!=10){
                        this.mostrarMensaje("DEBE DEJAR UNA CARTA EN EL POZO, INGRESE EL NUMERO DE CARTA A DEJAR: ");
                        controlador.setJugadorActualSeBajoRecien(true);
                        estadoMenuJuego=0;
                        estadoIndiceCarta=1;
                    }
                    else{
                        continuarRonda();
                    }
                }
                break;
            default:
                mostrarMensaje("Error: Opcion invalida");
                break;
        }
    }

    public void procesarMenu2(String input){
        switch (input){
            case "1":
                controlador.jugadorActualTomarCartaMazo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=2;
                break;
            case "2":
                controlador.jugadorActualTomarCartaPozo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=2;
                break;
            case "3":
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A BAJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=1;
                break;
            default:
                mostrarMensaje("Error: Opcion invalida");
                break;
        }
    }

    public void mostrarSubmenuJuego() {
        estadoMenuJuego=0;
        estadoSubmenuJuego=1;
        this.mostrarMensaje("1- RECOGER CARTA DEL MAZO");
        this.mostrarMensaje("2- RECOGER CARTA DEL POZO");
        this.mostrarMensaje("INGRESE UNA OPCION: ");
    }

    public void procesarSubmenuJuego(String input){
        switch (input){
            case "1":
                controlador.jugadorActualTomarCartaMazo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoSubmenuJuego=0;
                estadoIndiceCarta=2;
                break;
            case "2":
                controlador.jugadorActualTomarCartaPozo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoSubmenuJuego=0;
                estadoIndiceCarta=2;
                break;
            default:
                mostrarMensaje("Error: Opcion invalida");
                break;
        }
    }

    public void mostrarRondaActual(){
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("RONDA: " + controlador.obtenerNumeroRondaActual());
        this.mostrarMensaje("SE DEBE FORMAR: ");
        for (HashMap.Entry<Forma, Integer> entry : controlador.obtenerFormasRondaActual().entrySet()) {
            Forma forma = entry.getKey();
            int cantidad = entry.getValue();
            this.mostrarMensaje("Forma: " + forma + ", Cantidad: " + cantidad);
        }
        this.mostrarMensaje("--------------------------------------");
    }

    public void mostrarFormasArmadasDeCadaJugador(){
        ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
        for (int i=0; i<controlador.cantidadJugadores(); i++){
            if (controlador.formasArmadasJugador(i).size()!=0){
                mostrarCartasBajadas(jugadores.get(i));
            }
        }
        this.mostrarMensaje("--------------------------------------");
    }

    public void mostrarCartasBajadas(Jugador jugador){
        this.mostrarMensaje("CARTAS BAJADAS DE: " + controlador.obtenerNickname(jugador));
        for (int i=0; i<controlador.formasArmadasJugador(jugador).size(); i++){
            mostrarForma(controlador.formasArmadasJugador(jugador).get(i));
        }
    }

    public void mostrarForma(Bajada bajada){
        if (controlador.obtenerNombreForma(bajada).equals(Forma.TRIO)){
            if (controlador.cartasQueFormanLaBajada(bajada).get(0).getValor().equals("$")){
                if (controlador.cartasQueFormanLaBajada(bajada).get(1).getValor().equals("$")){
                    this.mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(2).getValor());
                }
                else{
                    this.mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(1).getValor());
                }
            }
            else{
                this.mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor());
            }
        } else if (controlador.obtenerNombreForma(bajada).equals(Forma.ESCALA)) {
            this.mostrarMensaje("ESCALA " + controlador.cartasQueFormanLaBajada(bajada).get(0).getColor() + ", comienza con " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor() + " ,termina con " + controlador.cartasQueFormanLaBajada(bajada).get(controlador.cartasQueFormanLaBajada(bajada).size()-1).getValor());
        }
    }

    public void mostrarJugadorActual() {
        Jugador j= controlador.jugadorActual();
        this.mostrarMensaje("ES EL TURNO DE: " + controlador.obtenerNickname(j));
        this.mostrarMensaje("--------------------------------------");
    }

    public void mostrarCartasJugadorActual(){
        ArrayList<Carta> cartas= controlador.cartasJugadorActual();
        for (int i=0; i<cartas.size(); i++){
            this.mostrarMensaje((i+1) + " - " + cartas.get(i).toString());
        }
        this.mostrarMensaje("--------------------------------------");
    }

    public void mostrarTopePozo(){
        String a= controlador.obtenerTopePozo();
        this.mostrarMensaje("TOPE POZO: " + a);
    }

    public void mostrarMazo(){
        this.mostrarMensaje("MAZO...");
    }

    public void mostrarJugadorTomoCartaMazo(){
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("TOMO UNA CARTA DEL MAZO CON EXITO!");
        this.mostrarMensaje("--------------------------------------");
        mostrarCartasJugadorActual();
    }

    public void mostrarJugadorDejoCarta(){
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("HA DEJADO LA CARTA CON EXITO!");
        this.mostrarMensaje("--------------------------------------");
    }

    public void mostrarJugadorTomoCartaPozo(){
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("TOMO UNA CARTA DEL POZO CON EXITO!");
        this.mostrarMensaje("--------------------------------------");
        mostrarCartasJugadorActual();
    }

    public void mostrarJugadorSeBajo(){
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("SE HA BAJADO CON EXITO!");
        this.mostrarMensaje("CARTAS RESTANTES: ");
        mostrarCartasJugadorActual();
    }

    public void mostrarJugadorNoPudoBajarse(){
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("NO PUEDE BAJARSE");
        this.mostrarMensaje("--------------------------------------");
        mostrarMenuJuego(controlador.jugadorActual());
    }

    public void mostrarJugadorBajoCarta(){
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("HA BAJADO LA CARTA CON EXITO!");
        this.mostrarMensaje("--------------------------------------");
    }

    public void mostrarJugadorNoPudoBajarCarta(){
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("NO ES POSIBLE BAJAR LA CARTA.");
        this.mostrarMensaje("--------------------------------------");
        mostrarSubmenuJuego();
    }

    public void mostrarTerminoRonda(){
        this.mostrarMensaje("--------------------------------------");
        Jugador j= controlador.ganadorRonda();
        this.mostrarMensaje("EL GANADOR DE LA RONDA ES: " + controlador.obtenerNickname(j));
        controlador.cargarPuntos();
        this.mostrarMensaje("--------------------------------------");
        mostrarPuntosJugadores();
    }

    public void mostrarPuntosJugadores(){
        this.mostrarMensaje("--------------------------------------");
        ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
        for (int i=0; i<jugadores.size(); i++){
            this.mostrarMensaje(controlador.obtenerNickname(jugadores.get(i)) + ", PUNTOS: " + controlador.obtenerPuntosJugador(jugadores.get(i)));
        }
    }

    public void mostrarFinJuego(){
        this.mostrarMensaje("");
        this.mostrarMensaje("--------------------------------------");
        this.mostrarMensaje("EL JUEGO HA TERMINADO");
        Jugador j= controlador.ganadorJuego();
        this.mostrarMensaje("GANADOR: " + controlador.obtenerNickname(j));
        this.mostrarMensaje("--------------------------------------");
    }

}
