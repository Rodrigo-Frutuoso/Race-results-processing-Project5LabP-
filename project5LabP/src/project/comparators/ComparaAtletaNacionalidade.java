package project.comparators;

import java.util.Comparator;
import project.Atleta;

/**
 * A classe ComparaAtletaNacionalidade implementa a interface Comparator para
 * comparar dois objetos Atleta com base em suas nacionalidades. Esta classe é
 * usada para ordenar uma coleção de objetos Atleta em ordem alfabética de
 * nacionalidade.
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class ComparaAtletaNacionalidade implements Comparator<Atleta> {

    @Override
    public int compare(Atleta a1, Atleta a2) {
        return a1.getNacionalidade().compareTo(a2.getNacionalidade());
    }
}
