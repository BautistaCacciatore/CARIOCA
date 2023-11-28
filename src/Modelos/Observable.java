package Modelos;

import Controladores.ControladorConsolaGrafica;

public interface Observable {
    void addObserver(ControladorConsolaGrafica controladorConsolaGrafica);
    void deleteObserver(ControladorConsolaGrafica controladorConsolaGrafica);
    void notificar(Evento evento);
}
