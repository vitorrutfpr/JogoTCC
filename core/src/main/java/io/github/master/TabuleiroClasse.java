package io.github.master;

public class Tabuleiro implements TabuleiroInterface {

    private final int tamanhoQuadradoMatriz = 80;
    private final int numLinhas = 10;
    private final int numColunas = 10;
    private QuadradoMatriz[][] tabuleiro;
    private RenderizadorClasse renderizador;


    class QuadradoMatriz {
        int id;
        int linha;
        int coluna;
        Cor cor;

        public QuadradoMatriz(int id, int linha, int coluna, Cor cor) {
            this.id = id;
            this.linha = linha;
            this.coluna = coluna;
            this.cor = cor;
        }
    }

    public Tabuleiro(RenderizadorClasse renderizador) {
        this.renderizador = renderizador;
        this.tabuleiro = new QuadradoMatriz[numLinhas][numColunas];
        renderizador.iniciar();
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                int id = linha * numColunas + coluna;
                tabuleiro[linha][coluna] = new QuadradoMatriz(id, linha, coluna, Cor.BRANCO);
            }
        }
    }

    private void desenharTabuleiro() {
        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                QuadradoMatriz quadrado = tabuleiro[linha][coluna];
                if (quadrado != null) {
                    if (coluna == 0 || linha == numLinhas - 1 || coluna == numColunas - 1) {
                        if (linha == 0 && coluna == 0) {
                            quadrado.cor = Cor.BRANCO;
                        } else if (linha == 0 && coluna == 9) {
                            quadrado.cor = Cor.PRETO;
                        } else if (coluna == 0) {
                            quadrado.cor = Cor.VERMELHO;
                        } else if (linha == numLinhas - 1) {
                            quadrado.cor = Cor.AMARELO;
                        } else if (coluna == numColunas - 1) {
                            quadrado.cor = Cor.VERDE;
                        }

                        renderizador.desenharRetangulo(quadrado.coluna * tamanhoQuadradoMatriz,
                            quadrado.linha * tamanhoQuadradoMatriz,
                            tamanhoQuadradoMatriz,
                            tamanhoQuadradoMatriz,
                            quadrado.cor);

                        renderizador.desenharBordaRetangulo(quadrado.coluna * tamanhoQuadradoMatriz,
                            quadrado.linha * tamanhoQuadradoMatriz,
                            tamanhoQuadradoMatriz,
                            tamanhoQuadradoMatriz,
                            Cor.BRANCO);
                    }
                }
            }
        }
    }

    public void render(float delta) {
        renderizador.render(delta);
        desenharTabuleiro();
    }

    public void resize(int largura, int altura) {
        renderizador.resize(largura, altura);
    }

    public void dispose() {
        renderizador.dispose();
    }
}
