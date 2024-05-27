package project.comparators;

import java.util.Comparator;
import project.Atleta;

/**
 * A classe ComparaAtletaPosFinalEscalao implementa a interface Comparator para
 * comparar dois objetos Atleta com base em suas posições finais no escalão.
 * Esta classe é usada para ordenar uma coleção de objetos Atleta em ordem
 * crescente de posição final no escalão.
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class ComparaAtletaPosFinalEscalao implements Comparator<Atleta> {

    @Override
    public int compare(Atleta a1, Atleta a2) {
        return Integer.compare(a1.getPosicaoFinalEscalao(),a2.getPosicaoFinalEscalao());
    }
}
