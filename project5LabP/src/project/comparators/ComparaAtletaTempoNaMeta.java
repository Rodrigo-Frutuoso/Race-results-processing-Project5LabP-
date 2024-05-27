package project.comparators;

import java.util.Arrays;
import java.util.Comparator;
import project.Atleta;

/**
 * A classe ComparaAtletaTempoNaMeta implementa a interface Comparator para
 * comparar dois objetos Atleta com base em seus tempos na meta. Esta classe é
 * usada para ordenar uma coleção de objetos Atleta em ordem crescente de tempo
 * na meta.
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class ComparaAtletaTempoNaMeta implements Comparator<Atleta> {
    public int compare(Atleta a1, Atleta a2) {
        return Arrays.compare(a1.getTempoNaMeta(), a2.getTempoNaMeta());
    }
}
