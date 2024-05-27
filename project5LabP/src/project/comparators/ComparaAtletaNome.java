package project.comparators;

import java.util.Comparator;
import project.Atleta;

/**
 * A classe ComparaAtletaNome implementa a interface Comparator para comparar
 * dois objetos Atleta com base em seus nomes. Esta classe é usada para ordenar
 * uma coleção de objetos Atleta em ordem alfabética de nome.
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class ComparaAtletaNome implements Comparator<Atleta> {

    @Override
    public int compare(Atleta a1, Atleta a2) {
        return a1.getNome().compareTo(a2.getNome());
    }
}
