package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import project.comparators.ComparaAtletaNome;
import project.comparators.ComparaAtletaDorsal;

/**
 * Classe Atleta que representa um atleta em uma corrida.
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class Atleta implements Comparable<Atleta> {

    private final int dorsal; // usado na comparação de Atletas por omissão através do compareTo()
    private final String nome;
    private final String nacionalidade;
    private final String escalao;
    private int posicaoFinalAbsoluta;
    private int posicaoFinalEscalao;
    private int[] tempoNaMeta; // {horas, minutos, segundos}
    private int[] temposPassagem; // tempos de passagem nos postos de controlo, em minutos desde a partida

    /**
     * Construtor da classe Atleta.
     * 
     * @param dorsal        o número do dorsal do atleta.
     * @param nome          o nome do atleta.
     * @param nacionalidade a nacionalidade do atleta.
     * @param escalao       o escalão do atleta.
     */
    public Atleta(int dorsal, String nome, String nacionalidade, String escalao) {
        this.dorsal = dorsal;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.escalao = escalao;
    }

    /**
     * Retorna o número do dorsal do atleta.
     * 
     * @return o número do dorsal do atleta.
     */
    public int getDorsal() {
        return dorsal;
    }

    /**
     * Retorna o nome do atleta.
     * 
     * @return o nome do atleta.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a nacionalidade do atleta.
     * 
     * @return a nacionalidade do atleta.
     */
    public String getNacionalidade() {
        return nacionalidade;
    }

    /**
     * Retorna o escalao do atleta.
     * 
     * @return o escalao do atleta.
     */
    public String getEscalao() {
        return escalao;
    }

    /**
     * Retorna a posição final absoluta do atleta.
     * 
     * @return a posição final absoluta do atleta.
     */
    public int getPosicaoFinalAbsoluta() {
        return posicaoFinalAbsoluta;
    }

    /**
     * Retorna a posição final do atleta no seu escalão.
     * 
     * @return a posição final do atleta no seu escalão.
     */
    public int getPosicaoFinalEscalao() {
        return posicaoFinalEscalao;
    }

    /**
     * Retorna uma cópia do tempo que o atleta chegou na meta.
     * 
     * @return uma cópia do tempo que o atleta chegou na meta.
     */
    public int[] getTempoNaMeta() {
        return Arrays.copyOf(tempoNaMeta, tempoNaMeta.length);
    }

    /**
     * Retorna uma cópia de todos os tempos de passagem do atleta.
     * 
     * @return uma cópia de todos os tempos de passagem do atleta.
     */
    public int[] getTemposPassagem() {
        return Arrays.copyOf(temposPassagem, temposPassagem.length);
    }

    /**
     * Define a posição final absoluta do atleta.
     * 
     * @param posicaoFinalAbsoluta a posição final absoluta do atleta.
     */
    public void setPosicaoFinalAbsoluta(int posicaoFinalAbsoluta) {
        this.posicaoFinalAbsoluta = posicaoFinalAbsoluta;
    }

    /**
     * Define a posição final do atleta no seu escalão.
     * 
     * @param posicaoFinalEscalao a posição final do escalao do atleta.
     */
    public void setPosicaoFinalEscalao(int posicaoFinalEscalao) {
        this.posicaoFinalEscalao = posicaoFinalEscalao;
    }

    /**
     * Define o tempo que o atleta chegou na meta.
     * 
     * @param tempoNaMeta tempo que o atleta chegou na meta.
     */
    public void setTempoNaMeta(int[] tempoNaMeta) {
        this.tempoNaMeta = Arrays.copyOf(tempoNaMeta, tempoNaMeta.length);
    }

    /**
     * Define os tempos de passagem do atleta.
     * 
     * @param temposPassagem os tempos de passagem do atleta.
     */
    public void setTemposPassagem(int[] temposPassagem) {
        this.temposPassagem = Arrays.copyOf(temposPassagem, temposPassagem.length);
    }

    @Override
    public int compareTo(Atleta outro) {
        return new ComparaAtletaDorsal().compare(this, outro);
    }

    /**
     * Retorna um código de hash para este atleta.
     * 
     * @return um código de hash baseado nos atributos dorsal, nome, nacionalidade e
     *         escalão deste atleta.
     */
    public int hashCode() {
        return Objects.hash(dorsal, nome, nacionalidade, escalao);
    }

    /**
     * Indica se algum objeto é "igual" a este atleta.
     * 
     * @param obj o objeto a ser comparado com este atleta.
     * @return true se o objeto passado for a mesma instância que este atleta, caso
     *         contrário false.
     */
    public boolean equals(Object obj) {
        return (this == obj);
    }

    /**
     * Ordena um vetor de atletas por ordem crescente de número do dorsal.
     * 
     * @param vec o vetor de atletas a ser ordenado.
     */
    public static void ordena(Atleta[] vec) {
        Arrays.sort(vec);
    }

    /**
     * Ordena um array de objetos do tipo Atleta de acordo com o comparador
     * especificado.
     *
     * @param vec        o array de objetos Atleta a ser ordenado
     * @param comparador o comparador a ser usado para determinar a ordem dos
     *                   objetos Atleta
     */
    public static void ordena(Atleta[] vec, Comparator<Atleta> comparador) {
        Arrays.sort(vec, comparador);
    }

    /**
     * Retorna o índice do atleta no vetor de atletas com base no nome.
     * 
     * @param vec  o vetor de atletas.
     * @param nome o nome do atleta.
     * @return o índice do atleta no vetor de atletas, ou -1 se o atleta não for
     *         encontrado.
     */
    public static int indiceAtletaPorNome(Atleta[] vec, String nome) {
        for (int i = 0; i < vec.length; i++) {
            if (vec[i].getNome().equals(nome)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retorna o índice do atleta no vetor de atletas ordenado por nome.
     * 
     * Este método utiliza busca binária para encontrar o atleta no vetor ordenado
     * por nome.
     * 
     * @param vec  o vetor de atletas ordenado por nome.
     * @param nome o nome do atleta.
     * @return o índice do atleta no vetor de atletas, ou -1 se o atleta não for
     *         encontrado.
     */
    public static int indiceAtletaPorNomeArrayOrdenado(Atleta[] vec, String nome) {
        return Arrays.binarySearch(vec, new Atleta(0, nome, "", ""), new ComparaAtletaNome());
    }

    /**
     * Seleciona os atletas de acordo com o escalão e/ou nacionalidade
     * 
     * @param vec           o vetor de atletas.
     * @param escalao       o escalão desejado, ou "todos" se não houver restrição.
     * @param nacionalidade a nacionalidade desejada, ou "todas" se não houver
     *                      restrição.
     * @return um vetor de atletas que atendem às restrições especificadas.
     */
    public static Atleta[] seleccionaEscalaoEouNacionalidade(Atleta[] vec, String escalao, String nacionalidade) {

        boolean restricaoEscalao = !escalao.equals("todos");
        boolean restricaoNacionalidade = !nacionalidade.equals("todas");
        if (!restricaoEscalao && !restricaoNacionalidade) {
            return vec;
        }
        ArrayList<Atleta> atletas = new ArrayList<>();

        for (Atleta atleta : vec) {
            // ver se há restrições e se o atleta as atende essas restrições
            if ((!restricaoEscalao || atleta.getEscalao().equals(escalao))
                    && (!restricaoNacionalidade || atleta.getNacionalidade().equals(nacionalidade))) {
                atletas.add(atleta);
            }
        }
        return atletas.toArray(new Atleta[0]);
    }

    /**
     * Retorna uma representação em formato de string deste atleta.
     * 
     * A representação inclui o número do dorsal do atleta seguido por um hífen e
     * seu nome.
     * 
     * @return uma string contendo o número do dorsal e o nome do atleta.
     */
    public String toString() {
        return dorsal + " - " + nome;
    }
}