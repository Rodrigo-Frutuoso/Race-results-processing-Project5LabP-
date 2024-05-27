package project.comparators;

import java.util.Comparator;
import project.RegistoPassagem;

/**
 * A classe ComparaRegistoPassagemTempo implementa a interface Comparator para
 * comparar dois objetos RegistoPassagem com base em seus tempos de passagem.
 * Esta classe é usada para ordenar uma coleção de objetos RegistoPassagem em
 * ordem crescente de tempo de passagem.
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class ComparaRegistoPassagemTempo implements Comparator<RegistoPassagem> {

    @Override
    public int compare(RegistoPassagem r1, RegistoPassagem r2) {
        return Integer.compare(r1.getTempoPassagem(), r2.getTempoPassagem());
    }
}
