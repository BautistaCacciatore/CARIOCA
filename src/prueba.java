import Enumerados.Color;
import Enumerados.Forma;
import Enumerados.Palo;
import Modelos.Carta;
import Modelos.Jugador;
import Modelos.Ronda;

import java.util.ArrayList;
import java.util.Random;

public class prueba {
    public static void main(String[] args) {
        Carta c1= new Carta("5", Palo.PICA, Color.NEGRO, 5);
        Carta c2= new Carta("6", Palo.DIAMANTE, Color.ROJO, 6);
        Carta c3= new Carta("7", Palo.TREBOL, Color.NEGRO, 7);
        Carta c4= new Carta("8", Palo.TREBOL, Color.NEGRO, 8);
        Carta c5= new Carta("8", Palo.CORAZON, Color.ROJO, 8);
        Carta c6= new Carta("8", Palo.TREBOL, Color.NEGRO, 8);
        Carta c7= new Carta("9", Palo.PICA, Color.NEGRO, 9);
        Carta c8= new Carta("J", Palo.PICA, Color.NEGRO, 10);
        Carta c9= new Carta("J", Palo.DIAMANTE, Color.ROJO, 11);
        Carta c10= new Carta("J", Palo.DIAMANTE, Color.ROJO, 11);
        Carta c11= new Carta("K", Palo.PICA, Color.NEGRO, 13);
        Carta c12= new Carta("K", Palo.TREBOL, Color.NEGRO, 13);
        ArrayList<Carta> cartas= new ArrayList<>();
        cartas.add(c1);
        cartas.add(c2);
        cartas.add(c3);
        cartas.add(c4);
        cartas.add(c5);
        cartas.add(c6);
        cartas.add(c7);
        cartas.add(c8);
        cartas.add(c9);
        cartas.add(c10);
        cartas.add(c11);
        cartas.add(c12);
        Jugador j= new Jugador("bachi");
        j.setCartas(cartas);
        Ronda r= new Ronda(2);
        r.añadirForma(Forma.ESCALA, 1);
        r.añadirForma(Forma.TRIO, 1);
        j.bajarse(r);



    }
}

