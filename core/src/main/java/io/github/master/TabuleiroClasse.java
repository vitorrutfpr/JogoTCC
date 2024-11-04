package io.github.master;

import java.util.ArrayList;
import java.util.List;

public class TabuleiroClasse {

    private final int tamanhoQuadradoMatriz = 80;
    private final int numLinhas = 10;
    private final int numColunas = 10;
    private QuadradoMatriz[][] tabuleiro;
    private RenderizadorClasse renderizador;

    private GerenciadorCameraClasse camera;
    private JogadorClasse[] jogadores;
    private final int numeroDeJogadores;

    private int turnoAtual;

    class QuadradoMatriz {
        int linha;
        int coluna;
        String imagem;
        List<JogadorClasse> jogadoresNoQuadrado;

        public QuadradoMatriz(int linha, int coluna, String imagem) {
            this.linha = linha;
            this.coluna = coluna;
            this.jogadoresNoQuadrado = new ArrayList<>();
            this.imagem = imagem;
        }

        public void adicionarJogador(JogadorClasse jogador) {
            jogadoresNoQuadrado.add(jogador);
            jogador.mover(linha, coluna);
        }

        public void removerJogador(JogadorClasse jogador) {
            jogadoresNoQuadrado.remove(jogador);
        }

        public void desenharQuadrado(RenderizadorClasse renderizador) {
            renderizador.desenharQuadradoComImagem(coluna * tamanhoQuadradoMatriz, linha * tamanhoQuadradoMatriz, 80, 80, imagem);
        }
    }

    public TabuleiroClasse(RenderizadorClasse renderizador) {
        this.renderizador = renderizador;
        this.camera = GerenciadorCameraClasse.getInstancia();
        this.turnoAtual = 1;
        this.numeroDeJogadores = 4;

        this.tabuleiro = new QuadradoMatriz[numLinhas][numColunas];
        this.jogadores = new JogadorClasse[this.numeroDeJogadores];

        inicializarJogadores();

    }

    private void inicializarJogadores() {
        this.jogadores[0] = new JogadorClasse(0, Cor.VERMELHO, 0, 0, this.renderizador);
        this.jogadores[1] = new JogadorClasse(1, Cor.AMARELO, 0, 0, this.renderizador);
        this.jogadores[2] = new JogadorClasse(2, Cor.VERDE, 0, 0, this.renderizador);
        this.jogadores[3] = new JogadorClasse(3, Cor.AZUL, 0, 0, this.renderizador);

        tabuleiro[0][0] = new QuadradoMatriz(0, 0, "interrogacao.jpg");

        for (JogadorClasse jogador : jogadores) {
            tabuleiro[0][0].adicionarJogador(jogador);
        }
    }
    //da para melhorar essa logica pegando a posição atual de cada jogador
    private void desenharJogadores() {
        int quadranteTamanho = tamanhoQuadradoMatriz / 2;

        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                QuadradoMatriz quadrado = tabuleiro[linha][coluna];
                int x = coluna * tamanhoQuadradoMatriz;
                int y = linha * tamanhoQuadradoMatriz;

                for (int i = 0; i < quadrado.jogadoresNoQuadrado.size(); i++) {
                    JogadorClasse jogador = quadrado.jogadoresNoQuadrado.get(i);

                    switch (i) {
                        case 0:
                            renderizador.desenharRetangulo(x, y + quadranteTamanho, quadranteTamanho, quadranteTamanho, jogador.getCor());
                            break;
                        case 1:
                            renderizador.desenharRetangulo(x + quadranteTamanho, y + quadranteTamanho, quadranteTamanho, quadranteTamanho, jogador.getCor());
                            break;
                        case 2:
                            renderizador.desenharRetangulo(x, y, quadranteTamanho, quadranteTamanho, jogador.getCor());
                            break;
                        case 3:
                            renderizador.desenharRetangulo(x + quadranteTamanho, y, quadranteTamanho, quadranteTamanho, jogador.getCor());
                            break;
                    }
                }
            }
        }
    }


    private void desenharTabuleiro() {
        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                if (tabuleiro[linha][coluna] == null) {
                    tabuleiro[linha][coluna] = new QuadradoMatriz(linha, coluna, "interrogacao.jpg");
                }
                QuadradoMatriz quadrado = tabuleiro[linha][coluna];

                float x = coluna * tamanhoQuadradoMatriz;
                float y = linha * tamanhoQuadradoMatriz;

                if ((linha == 0 || linha == numLinhas - 1 || coluna == 0 || coluna == numColunas - 1) &&
                    !(linha == 0 && coluna == 4) &&
                    !(linha == 0 && coluna == 5)) {
                    quadrado.desenharQuadrado(renderizador);
                    renderizador.desenharBordaRetangulo(x, y, tamanhoQuadradoMatriz, tamanhoQuadradoMatriz, Cor.BRANCO);
                }
            }
        }
    }

    public void render(float delta) {
        renderizador.setProjectionMatrix(camera.getCamera().combined);
        renderizador.render(delta);
        desenharTabuleiro();
        desenharJogadores();
    }

    public void dispose(){
    }
}
