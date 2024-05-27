package project.comparators;

import java.util.Comparator;
import project.Atleta;

/**
 * A classe ComparaAtletaDorsal implementa a interface Comparator para comparar
 * dois objetos Atleta com base em seus números de dorsal. Esta classe é usada
 * para ordenar uma coleção de objetos Atleta em ordem crescente de número de
 * dorsal.
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class ComparaAtletaDorsal implements Comparator<Atleta> {

    @Override
    public int compare(Atleta a1, Atleta a2) {
        return Integer.compare(a1.getDorsal(), a2.getDorsal());
    }
}
