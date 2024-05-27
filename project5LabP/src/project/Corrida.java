package project;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import org.jfree.data.xy.XYDataset;
import project.comparators.*;

/**
 * Guarda todos os dados de uma corrida e fornece métodos para analisar esses
 * dados
 * 
 * @author Rodrigo Frutuoso 61865
 */
public class Corrida {

    /**
     * Tempo de passagem de um atleta que não passou num posto, medido em minutos
     * desde a partida
     * 
     * É um valor arbitrário que corresponde a 100 horas, muito superior ao tempo
     * limite. Aplica-se também a um atleta que não tenha chegado à meta, através da
     * constante TEMPO_DE_QUEM_NAO_CHEGOU_A_META.
     */
    public static final int MINUTOS_DE_QUEM_NAO_PASSOU = 6000;

    /**
     * Tempo de chegada de um atleta que não chegou à meta, expresso em {horas,
     * minutos, segundos} desde a partida.
     * 
     * É um valor arbitrário consistente com MINUTOS_DE_QUEM_NAO_PASSOU.
     */
    public static final int[] TEMPO_DE_QUEM_NAO_CHEGOU_A_META = { MINUTOS_DE_QUEM_NAO_PASSOU / 60,
            MINUTOS_DE_QUEM_NAO_PASSOU % 60, 0 };

    /**
     * Posição final de um atleta que não chegou à meta.
     * 
     * É um valor arbitrário, muito superior ao número de atletas em prova. Serve
     * para a posição absoluta e para a posição por escalão.
     */
    public static final int POSICAO_DE_QUEM_NAO_CHEGOU_A_META = 9999;

    // Todos os atletas que se apresentaram à partida, por ordem crescente de número
    // de dorsal

    private final Atleta[] atletas;

    // Cada linha de registosPassagem corresponde a um posto de controlo.
    // Após leitura dos dados de ficheiroRegistoPassagens e processamento desses
    // dados, cada linha deve ficar ordenada
    // por ordem crescente de tempo de passagem.
    private final RegistoPassagem[][] registosPassagem;

    /**
     * Construtor da classe Corrida. Inicializa os atletas e os registos de passagem
     * lendo os dados dos arquivos fornecidos.
     * 
     * @param ficheiroListaAtletas     O caminho para o ficheiro csv que contém a
     *                                 lista de atletas.
     * @param ficheiroClassificacoes   O caminho para o ficheiro csv que contém as
     *                                 classificações.
     * @param ficheiroRegistoPassagens O caminho para o ficheiro csv que contém o
     *                                 registo de passagens.
     * @throws FileNotFoundException Se algum dos arquivos não for encontrado.
     */
    public Corrida(String ficheiroListaAtletas, String ficheiroClassificacoes, String ficheiroRegistoPassagens)
            throws FileNotFoundException {
        this.atletas = inicializarAtletas(ficheiroListaAtletas);
        Atleta.ordena(atletas);
        this.registosPassagem = inicializarRegistosPassagem(ficheiroRegistoPassagens);
        classificacoes(ficheiroClassificacoes);
    }

    /**
     * Método privado para inicializar os atletas. Lê os dados do arquivo fornecido
     * e cria um array de objetos Atleta.
     * 
     * @param nome O caminho para o ficheiro csv que contém a lista de atletas.
     * @return Um array de objetos Atleta.
     * @throws FileNotFoundException Se o arquivo não for encontrado.
     */
    private static Atleta[] inicializarAtletas(String nome) throws FileNotFoundException {
        ArrayList<Atleta> atletas = new ArrayList<>();
        Scanner scanner = new Scanner(new File(nome));
        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String[] partes = scanner.nextLine().split(";");
            Atleta at = new Atleta(Integer.parseInt(partes[0]), partes[1], partes[4], partes[3]);
            at.setTempoNaMeta(TEMPO_DE_QUEM_NAO_CHEGOU_A_META);
            at.setPosicaoFinalAbsoluta(POSICAO_DE_QUEM_NAO_CHEGOU_A_META);
            at.setPosicaoFinalEscalao(POSICAO_DE_QUEM_NAO_CHEGOU_A_META);
            atletas.add(at);
        }
        scanner.close();
        return atletas.toArray(new Atleta[0]);
    }

    /**
     * Método privado para inicializar os registos de passagem. Lê os dados do
     * arquivo fornecido e cria uma matriz de objetos RegistoPassagem.
     * 
     * @param nome O caminho para o ficheiro csv que contém o registo de passagens.
     * @return Uma matriz de objetos RegistoPassagem.
     * @throws FileNotFoundException Se o arquivo não for encontrado.
     */
    private RegistoPassagem[][] inicializarRegistosPassagem(String nome) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(nome));
        int numerosDePontos = sc.nextLine().split(";").length - 3;
        RegistoPassagem[][] reg = new RegistoPassagem[numerosDePontos][atletas.length];
        int vez = 0;
        while (sc.hasNextLine()) {
            String[] partes = sc.nextLine().split(";");
            int dorsal = Integer.parseInt(partes[0]);
            int[] temposPassagem = new int[numerosDePontos];
            for (int i = 3; i < partes.length; i++) {
                int tempo = getTime(partes[i]);
                temposPassagem[i - 3] = tempo;
                reg[i - 3][vez] = new RegistoPassagem(dorsal, tempo);
            }
            atletas[Atleta.indiceAtletaPorNome(atletas, partes[1])].setTemposPassagem(temposPassagem);
            vez++;
        }
        for (RegistoPassagem[] row : reg) {
            Arrays.sort(row, new ComparaRegistoPassagemTempo());
        }
        sc.close();
        return reg;
    }

    /**
     * Método privado para obter o tempo de passagem a partir de uma string.
     * 
     * @param dia A string que representa o dia e a hora da passagem.
     * @return O tempo de passagem em minutos.
     */
    private static int getTime(String tempo) {
        String[] dados = tempo.split(" ");
        if (dados.length == 1) {
            return MINUTOS_DE_QUEM_NAO_PASSOU;
        }
        String[] horaPartes = dados[1].split(":");
        int horas = Integer.parseInt(horaPartes[0]);
        int minutos = Integer.parseInt(horaPartes[1]);

        int dia;
        if (dados[0].contains("Sun.")) {
            dia = 3;
        } 
        else if (dados[0].contains("Sat.")) {
            dia = 2;
        } 
        else {
            dia = 1;
        }
        Tempo time = new Tempo(2023, 9, dia, horas, minutos, 0);
        return time.getMinutosEmProva();
    }

    /**
     * Método privado para processar as classificações. Lê os dados do arquivo
     * fornecido e atualiza os objetos Atleta correspondentes.
     * 
     * @param nome O caminho para o ficheiro csv que contém as classificações.
     * @throws FileNotFoundException Se o arquivo não for encontrado.
     */
    private void classificacoes(String nome) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(nome));
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        while (sc.hasNextLine()) {
            String[] partes = sc.nextLine().split(";");
            int indice = Atleta.indiceAtletaPorNome(atletas, partes[2]);
            atletas[indice].setPosicaoFinalAbsoluta(Integer.parseInt(partes[0]));
            atletas[indice].setPosicaoFinalEscalao(Integer.parseInt(partes[5]));
            String[] timeParts = partes[6].split(":");
            int[] tempos = { Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]),
                    Integer.parseInt(timeParts[2]) };
            atletas[indice].setTempoNaMeta(tempos);
        }
        sc.close();
    }

    /**
     * Retorna uma cópia do array de atletas ordenados.
     * 
     * @return Uma cópia do array de atletas ordenados.
     */
    public Atleta[] getAtletas() {
        return Arrays.copyOf(atletas, atletas.length);
    }

    /**
     * Retorna o atleta no índice especificado.
     * 
     * @param indice O índice do atleta a ser retornado.
     * @return O atleta no índice especificado.
     */
    public Atleta getAtletaPorIndice(int indice) {
        return atletas[indice];
    }

    /**
     * Retorna uma cópia da matriz de registos de passagem.
     * 
     * @return Uma cópia da matriz de registos de passagem.
     */
    public RegistoPassagem[][] getRegistosPassagem() {
        return Arrays.copyOf(registosPassagem, registosPassagem.length);
    }

    /**
     * Retorna o número de atletas na corrida.
     * 
     * @return o número de atletas na corrida.
     */
    public int getNumeroDeAtletas() {
        return atletas.length;
    }

    /**
     * Retorna o número de postos de controle na corrida.
     * 
     * @return O número de postos de controle na corrida.
     */
    public int getNumeroPostosControlo() {
        return registosPassagem.length;
    }

    /**
     * Calcula e retorna um array contendo as posições em que o atleta com o dorsal
     * dado passou em cada um dos postos. O array retornado tem portanto tamanho
     * igual ao número de postos. Caso um atleta não tenha passado num certo posto,
     * a posição calculada nesse posto é indeterminada: a única exigência é de que
     * seja superior à posição do último atleta que efetivamente passou no posto.
     * 
     * @param dorsal O número do dorsal do atleta.
     * @return Um array de inteiros onde cada elemento representa a posição do
     *         atleta no respectivo posto de controle.
     */

    public int[] calculaPosicoesPostos(int dorsal) {
        int[] posicoes = new int[registosPassagem.length];
        for (int i = 0; i < registosPassagem.length; i++) {
            int posicao = 0;
            for (int j = 0; j < registosPassagem[i].length; j++) {
                if (registosPassagem[i][j].getDorsal() == dorsal) {
                    posicao = j + 1;
                    break;
                }
            }
            posicoes[i] = posicao;
        }
        return posicoes;
    }

    /**
     * Sendo vec um array de tamanho arbitrário, o método mostra uma janela com um
     * gráfico em que estão sobrepostas as séries de posições de cada atleta ao
     * longo dos postos de controlo. Caso um atleta não tenha passado num certo
     * posto, a posição exibida nesse posto não tem validade, como explicado no
     * método calculaPosicoesPostos.
     * 
     * @param vec Um array de atletas.
     */
    public void plotPosicoesPostos(Atleta[] vec) {
        XYDataset[] datasets = new XYDataset[vec.length];
        for (int i = 0; i < vec.length; i++) {
            int[] posicoes = calculaPosicoesPostos(vec[i].getDorsal());
            double[] yData = new double[posicoes.length];
            for (int j = 0; j < posicoes.length; j++) {
                yData[j] = (double) posicoes[j];
            }
            datasets[i] = XYPlotter.createDataset("Atleta " + vec[i].getDorsal(), yData);
        }
        XYPlotter.showXYPlot("Compação de atletas", "Postos de Controle", "Posições", datasets);
    }

    /**
     * Comporta-se como o método anterior, exceto que são apenas passados os dorsais
     * de cada atleta, e não o objeto Atleta completo.
     * 
     * @param dorsais Um array de dorsais de atletas.
     */
    public void plotPosicoesPostos(int[] dorsais) {
        Atleta[] vec = new Atleta[dorsais.length];
        for (int i = 0; i < dorsais.length; i++) {
            for (Atleta atleta : atletas) {
                if (atleta.getDorsal() == dorsais[i]) {
                    vec[i] = atleta;
                    break;
                }
            }
        }
        plotPosicoesPostos(vec);
    }
}
