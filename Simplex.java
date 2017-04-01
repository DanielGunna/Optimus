/**
 *
 * @author Daniel Gunna
 */
public class Simplex {

    private static final int COLUNA_ML = 0;
    private static final int LINHA_FO = 0;
    private double[][] celulaSuperior;
    private double[][] celulaInferior;
    private boolean[][] marcados;
    private int colunaPermitida;
    private int linhaPermitida;
    private int linhamembroLivre;

    /**
     * Funcao para encontra membro livre negativo
     * @return Retorna o indice do membro livre negativo na matriz  caso ele exista
     */
    private int getMembroLivre() {
        for (int linha = 1; linha < celulaSuperior.length; linha++) {
            if (celulaSuperior[linha][0] < 0) {
                return linha;
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Funcao que procura por um VNB Negativa na linha do ML negativo
     * @param linha Linha do membro livre negativo
     * @return Retorna o indice da VNB  negativa na linha do ML
     */
    private int getVnbNegativa(int linha) {
        for (int coluna = 1; coluna < celulaSuperior[0].length ;coluna++) {
            if (celulaSuperior[linha][coluna] < 0) {
                return coluna;
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Funcao que retorna a linha permitida com menor razao entro o ML e o
     * elemento da coluna permitida
     * @return Retorna o indice da linha permitida caso seja possivel
     */
    private int getLinhaElementoPermitido() {
        return getLinhaMenor();
    }

    /**
     * Metodo que multiplica a celula superior  da linha do elemento permitido
     * pelo inverso do elemento permitido e armazena na celula Infrior
     * Tambem marca os elementos da linha do elemento permitido
     * */
    private void multiplicaLinha(double inverso) {
        for (int coluna = 0; coluna < celulaSuperior[0].length; coluna++) {
            //Se celulaSuperior  = 0, apenas coloque 0 ma celula inferior,
            //pois e possivel que se multiplicarmos por um valor negativo
            //obtenhamos o valor -0
            celulaInferior[linhaPermitida][coluna] = (celulaSuperior[linhaPermitida][coluna] == 0) ? 0 : celulaSuperior[linhaPermitida][coluna] * (inverso);
            marcados[linhaPermitida][coluna] = true;
        }
    }

    /**
     * Metodo que multiplica a celula superior  da coluna do elemento permitido
     * pelo inverso multiplicado por  -1 do elemento permitido e armazena na celula Inferior
     * Tambem marca os elementos da coluna do elemento permitido
     */
    private void multiplicaColuna(double inverso) {
        for (int linha = 0; linha < celulaSuperior.length; linha++) {
            //Se celulaSuperior  = 0, apenas coloque 0 ma celula inferior,
            //pois e possivel que se multiplicarmos por um valor negativo
            //obtenhamos o valor -0
            celulaInferior[linha][colunaPermitida] = (celulaSuperior[linha][colunaPermitida] == 0) ? 0 : celulaSuperior[linha][colunaPermitida] * ((-1) * inverso);
            marcados[linha][colunaPermitida] = true;
        }
    }

    /**
     * Funcao que retorna o indice da linha do elemento com menor razao positiva
     * ML/Elemento da coluna permitida  caso exista
     * @return Indice da linha do elemento de menor razao
     */
    private int getLinhaMenor() {
        double menor = Double.MAX_VALUE;
        int linhaMenor = Integer.MAX_VALUE;
        double razao;
        for (int linha = 1; linha < celulaSuperior.length; linha++) {
            //Evita divisoes por 0
            if (celulaSuperior[linha][colunaPermitida] != 0) {
                //ML/Elemento da Coluna Permitida
                razao = (celulaSuperior[linha][COLUNA_ML] / celulaSuperior[linha][colunaPermitida]);
                if (razao > 0 && razao < menor) {
                    menor = razao;
                    linhaMenor = linha;
                }
            }
        }
        return linhaMenor;
    }

    /**
     * Metodo que mostra a matriz do Simplex
     */
    private void mostrar() {
        System.out.println("===============================");
        for (int x = 0; x < celulaSuperior.length ; x++) {
            System.out.print("|\n");
            for (int y = 0; y < celulaSuperior[0].length; y++) {
                System.out.print("(" + celulaSuperior[x][y] + "/" + celulaInferior[x][y] + ")");
            }
            System.out.print("|\n");
        }
        System.out.println("===============================");
    }

    /**
     * Metodo que mostra os elementos marcados
     */
    private void mostrarMarcados() {
        for (int x = 0; x < marcados.length ; x++) {
            for (int y = 0; y < marcados[0].length ; y++) {
                System.out.print("(" + marcados[x][y] + ") ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Metodo que executa primeira parte do Algoritmo Simplex
     */
    private void primeiroPasso() {
        linhamembroLivre = getMembroLivre();
        while(linhamembroLivre!=Integer.MAX_VALUE){
            colunaPermitida = getVnbNegativa(linhamembroLivre);
            if(colunaPermitida!=Integer.MAX_VALUE){
                linhaPermitida = getLinhaElementoPermitido();
                System.out.println("Elemento permitido :" + celulaSuperior[linhaPermitida][colunaPermitida]);
                double inverso = (1 / celulaSuperior[linhaPermitida][colunaPermitida]);
                System.out.println("Inverso :" + inverso);
                multiplicaLinha(inverso);
                multiplicaColuna(inverso);
                mostrarMarcados();
                celulaInferior[linhaPermitida][colunaPermitida] = inverso;
                algoritmoDaTroca();
                linhamembroLivre = getMembroLivre();
                System.out.println("-------------------------------------------");
            }else{
                System.out.println("Não existe solução para este problema!");
                break;
            }

        }
    }
     /**
     * Funcao que  procura indice da coluna de um elemento positivo na linha da funcao objetiva
     *@return Indice da coluna do elemento positivo
     */
     private int getColunaElementoPositivoFO(){
      for(int coluna = 1 ;  coluna < celulaSuperior[0].length ; coluna ++){
        if(celulaSuperior[LINHA_FO][coluna] > 0){
          return coluna;
        }
      }
      return Integer.MAX_VALUE;
    }
   /**
     * Funcao que  procura indice da coluna de um elemento positivo na linha da funcao objetiva
     *@return coluna Indice da coluna do elemento positivo
     **/
    private boolean  verificaSeIlimitado(){
      for(int linha   = 1 ; linha < celulaSuperior.length; linha ++ ){
        if(celulaSuperior[linha][colunaPermitida] > 0){
            return false;
        }
      }
      return true;
    }
     /**
     * Metodo que executa primeira parte do Algoritmo Simplex
     */
    private void segundoPasso(){
        colunaPermitida = getColunaElementoPositivoFO();
        while(colunaPermitida != Integer.MAX_VALUE){
          if(!verificaSeIlimitado()){
            linhaPermitida = getLinhaMenor();
            System.out.println("Elemento permitido :" + celulaSuperior[linhaPermitida][colunaPermitida]);
            double inverso = (1 / celulaSuperior[linhaPermitida][colunaPermitida]);
            System.out.println("Inverso :" + inverso);
            multiplicaLinha(inverso);
            multiplicaColuna(inverso);
            mostrarMarcados();
            celulaInferior[linhaPermitida][colunaPermitida] = inverso;
            algoritmoDaTroca();
            System.out.println("-------------------------------------------");
          }else{
            System.out.println("Este problema possui solução ilimitada!!!");
          }
          colunaPermitida = getColunaElementoPositivoFO();
        }
    }

    private void preencheCelulaInferior(int linhaPermitida, int colunaPermitida) {
        for (int linha = 0; linha < celulaInferior.length; linha++) {
            for (int coluna = 0; coluna < celulaInferior[0].length; coluna++) {
                if (!marcados[linha][coluna]) {
                    celulaInferior[linha][coluna] = getMarcadoLinha(linha) * getMarcadoColuna(coluna);
                }
            }
        }
    }

    private double getMarcadoLinha(int linha) {
        for (int coluna = 0; coluna < celulaInferior.length; coluna++) {
            if (marcados[linha][coluna]) {
                return celulaInferior[linha][coluna];
            }
        }
        return Double.MAX_VALUE;
    }

    private double getMarcadoColuna(int coluna) {
        for (int linha = 0; linha < celulaSuperior.length; linha++) {
            if (marcados[linha][coluna]) {
                return celulaSuperior[linha][coluna];
            }
        }
        return Double.MAX_VALUE;
    }
    private void somaValores() {
        for (int linha = 0; linha < celulaSuperior.length; linha++) {
            for (int coluna = 0; coluna < celulaSuperior[0].length; coluna++) {
                if(!marcados[linha][coluna]){
                    System.out.println(celulaSuperior[linha][coluna]+" "+ celulaInferior[linha][coluna]);
                    celulaSuperior[linha][coluna]+= celulaInferior[linha][coluna];
                    System.out.println("Igual :" + celulaSuperior[linha][coluna]);
                }
            }
        }
    }

    private void algoritmoDaTroca() {
        preencheCelulaInferior(linhaPermitida, colunaPermitida);
        mostrar();
        trocaValores();
        somaValores();
        celulaInferior = new double[celulaSuperior.length][celulaSuperior[0].length];
        marcados = new boolean[celulaSuperior.length][celulaSuperior[0].length];
        mostrar();
    }

    private void trocaValores() {
        trocaValoresLinha();
        trocaValoresColuna();
    }

    private void trocaValoresLinha() {
        int aux = 0;
        for (int coluna = 0; coluna < celulaSuperior[0].length; coluna++) {
            celulaSuperior[linhaPermitida][coluna] = celulaInferior[linhaPermitida][coluna];
        }
    }

    private void trocaValoresColuna() {
        int aux = 0;
        for (int linha = 0; linha < celulaSuperior.length; linha++) {
            if (linha != linhaPermitida) {
                celulaSuperior[linha][colunaPermitida] = celulaInferior[linha][colunaPermitida];
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

        celulaInferior = new double[matriz.length][matriz[0].length];
        marcados = new boolean[matriz.length][matriz[0].length];

        this.celulaSuperior = matriz.clone();
        primeiroPasso();
        segundoPasso();


    }

    public static void main(String[] args){
       new Simplex().teste();
    }


}
