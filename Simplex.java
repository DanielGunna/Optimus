

/**
 * Created by Daniel on 31/03/17.
 */

/**
 *
 * @author Daniel Gunna
 */
public class Simplex {

    private static final int COLUNA_ML = 0;
    private double[][] matriz;
    private double[][] matrizInferior;
    private double[] variaveis;
    private boolean[][] marcados;
    private double[][] restricoes;
    private int colunaPermitida;
    private int linhaPermitida;
    private int linhamembroLivre;

    /**
     * Funcao para encontra membro livre negativo
     *
     * @return Retorna o indice do membro livre negativo na matriz
     */
    private int getMembroLivreNegativo() {
        for (int linha = 1; linha < matriz.length; linha++) {
            if (matriz[linha][0] < 0) {
                return linha;
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Funcao que procura por um VNB Negativa na linha do ML negativo
     *
     * @param linha Linha do membro livre negativo
     * @return Retorna o indice do membro livre negativo na matriz
     */
    private int getVariavelNaoBasicaNegativa(int linha) {
        for (int coluna = 1; coluna < matriz[0].length ;coluna++) {
            if (matriz[linha][coluna] < 0) {
                System.out.println("Membro negativo " + matriz[linha][coluna]);
                return coluna;
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Funcao que retorna a linha permitida com menor razao entro o ML e o
     * elemento da coluna permitida
     *
     * @return Retorna o indice da linha permitida
     */
    private int getLinhaMembroPermitido() {
        return getLinhaMenor();
    }

    /**
     * Metodo que multiplica a posicao inferior da linha do elemento permitido
     * pelo inverso do elemento permitido
     *
     *
     *
     */
    private void multiplicaLinha(double inverso) {
        for (int coluna = 0; coluna < matriz[0].length; coluna++) {
            matrizInferior[linhaPermitida][coluna] = (matriz[linhaPermitida][coluna] == 0) ? 0 : matriz[linhaPermitida][coluna] * (inverso);
            marcados[linhaPermitida][coluna] = true;
        }
    }

    /**
     * Metodo que multiplica a posicao inferior da coluna do elemento permitido
     * pelo inverso do elemento permitido
     *
     *
     *
     */
    private void multiplicaColuna(double inverso) {
        for (int linha = 0; linha < matriz.length; linha++) {
            matrizInferior[linha][colunaPermitida] = (matriz[linha][colunaPermitida] == 0) ? 0 : matriz[linha][colunaPermitida] * ((-1) * inverso);
            marcados[linha][colunaPermitida] = true;
        }
    }

    /**
     * Funcao que retorna o indice da linha do elemento com menor razao
     * ML/Elemento da coluna permitida pelo inverso do elemento permitido
     *
     *
     * @return Indice da linha do elemento de menor razao
     */
    private int getLinhaMenor() {
        double menor = Double.MAX_VALUE;
        int linhaMenor = Integer.MAX_VALUE;
        for (int linha = 1; linha < matriz.length; linha++) {
            if (matriz[linha][colunaPermitida] != 0) {
                double razao = (matriz[linha][COLUNA_ML] / matriz[linha][colunaPermitida]);
                if (razao > 0 && razao < menor) {
                    System.out.println("Menor "+ matriz[linha][colunaPermitida] + "Razao " + razao);
                    menor = razao;
                    linhaMenor = linha;
                }
            }
        }
        return linhaMenor;
    }

    private void processarFuncao(String funcao) {

    }

    /**
     * Metodo que mostra a matriz do Simplex
     *
     */
    private void mostrar() {
        for (int x = 0; x < matriz.length ; x++) {
            for (int y = 0; y < matriz[0].length; y++) {
                System.out.print("(" + matriz[x][y] + "/" + matrizInferior[x][y] + ") ");
            }
            System.out.print("\n");
        }
        System.out.println("===============================");
    }

    /**
     * Metodo que mostra a matriz do Simplex
     *
     */
    private void mostrar(boolean[][] matriz) {
        for (int x = 0; x < matriz.length ; x++) {
            for (int y = 0; y < matriz[0].length ; y++) {
                System.out.print("(" + matriz[x][y] + ") ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Metodo que executa primeira parte do Simplex
     *
     */
    private void primeiroPasso() {
        linhamembroLivre = getMembroLivreNegativo();
        while(linhamembroLivre!=Integer.MAX_VALUE){
            colunaPermitida = getVariavelNaoBasicaNegativa(linhamembroLivre);
            if(colunaPermitida!=Integer.MAX_VALUE){
                linhaPermitida = getLinhaMembroPermitido();
                System.out.println("Elemento permitido :" + matriz[linhaPermitida][colunaPermitida]);
                double inverso = (1 / matriz[linhaPermitida][colunaPermitida]);
                System.out.println("Inverso :" + inverso);
                multiplicaLinha(inverso);
                multiplicaColuna(inverso);
                mostrar(marcados);
                matrizInferior[linhaPermitida][colunaPermitida] = inverso;
                algoritmoDaTroca();
                linhamembroLivre = getMembroLivreNegativo();
                System.out.println("-------------------------------------------");
            }else{
                System.out.println("Não existe solução para este problema!");
                break;
            }

        }
    }

    private void simplex() {
        primeiroPasso();
    }

    private void preencheCelulaInferior(int linhaPermitida, int colunaPermitida) {
        for (int linha = 0; linha < matrizInferior.length; linha++) {
            for (int coluna = 0; coluna < matrizInferior[0].length; coluna++) {
                if (!marcados[linha][coluna]) {
                    double marcadoLinha =  getMarcadoLinha(linha);
                    double marcadoColuna  = getMarcadoColuna(coluna);
                    matrizInferior[linha][coluna] = marcadoLinha * marcadoColuna;
                    System.out.println("Marcado da linha"+ linha + " "+ marcadoLinha);
                    System.out.println("Marcado da coluna"+ coluna + " "+ marcadoColuna);
                    System.out.println("Matriz inferior "+ matrizInferior[linha][coluna]);
                }
            }
        }
    }

    private double getMarcadoLinha(int linha) {
        for (int coluna = 0; coluna < matrizInferior.length; coluna++) {
            if (marcados[linha][coluna]) {
                return matrizInferior[linha][coluna];
            }
        }
        return Double.MAX_VALUE;
    }

    private double getMarcadoColuna(int coluna) {
        for (int linha = 0; linha < matriz.length; linha++) {
            if (marcados[linha][coluna]) {
                return matriz[linha][coluna];
            }
        }
        return Double.MAX_VALUE;
    }
    private void somaValores() {
        for (int linha = 0; linha < matriz.length; linha++) {
            for (int coluna = 0; coluna < matriz[0].length; coluna++) {
                if(!marcados[linha][coluna]){
                    System.out.println(matriz[linha][coluna]+" "+ matrizInferior[linha][coluna]);
                    matriz[linha][coluna]+= matrizInferior[linha][coluna];
                    System.out.println("Igual :" + matriz[linha][coluna]);
                }
            }
        }
    }

    private void algoritmoDaTroca() {
        preencheCelulaInferior(linhaPermitida, colunaPermitida);
        mostrar();
        trocaValores();
        somaValores();
        matrizInferior = new double[matriz.length][matriz[0].length];
        marcados = new boolean[matriz.length][matriz[0].length];
        mostrar();
    }

    private void trocaValores() {
        trocaValoresLinha();
        trocaValoresColuna();
    }

    private void trocaValoresLinha() {
        int aux = 0;
        for (int coluna = 0; coluna < matriz[0].length; coluna++) {
            System.out.println("Trocando valores " +  matriz[linhaPermitida][coluna]+" "+ matrizInferior[linhaPermitida][coluna]);
            matriz[linhaPermitida][coluna] = matrizInferior[linhaPermitida][coluna];
        }
    }

    private void trocaValoresColuna() {
        int aux = 0;
        for (int linha = 0; linha < matriz.length; linha++) {
            if (linha != linhaPermitida) {
                System.out.println("Trocando valores " +  matriz[linha][colunaPermitida]+" "+ matrizInferior[linha][colunaPermitida]);
                matriz[linha][colunaPermitida] = matrizInferior[linha][colunaPermitida];
            }
        }
    }

    /**
     * Metodo que executa teste do algoritmo
     *
     */
    public  void teste() {
        double[] variaveis = {5.0, 3.5};
        double[][] restricoes
                = {{0, 5.0, 3.5},
                {400, 1.5, 1.0},
                {150, 1.0, 0.0},
                {300, 0.0, 1.0}};

        double[][] matriz
                =
                {{0, 14.0, 22},
                {250, 2.0, 4.0},
                {-460, -5.0, -8.0},
                {40, 1.0, 0.0}};

        matrizInferior = new double[matriz.length][matriz[0].length];
        marcados = new boolean[matriz.length][matriz[0].length];

        this.matriz = matriz.clone();
        primeiroPasso();


    }

    public static void main(String[] args){
       new Simplex().teste();
    }


}
