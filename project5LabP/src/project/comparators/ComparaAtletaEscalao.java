package project.comparators;

import java.util.Comparator;
import project.Atleta;

/**
 * A classe ComparaAtletaEscalao implementa a interface Comparator para comparar
 * dois objetos Atleta com base em seus escalões. Esta classe é usada para
 * ordenar uma coleção de objetos Atleta em ordem alfabética de escalão.
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class ComparaAtletaEscalao implements Comparator<Atleta> {

    @Override
    public int compare(Atleta a1, Atleta a2) {
        return a1.getEscalao().compareTo(a2.getEscalao());
    }
}
