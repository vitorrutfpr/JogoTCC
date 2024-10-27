package io.github.master;
import java.util.ArrayList;
import java.util.List;

public class TabuleiroClasse implements TabuleiroInterface {

    private final int tamanhoQuadradoMatriz = 80;
    private final int numLinhas = 10;
    private final int numColunas = 10;
    private QuadradoMatriz[][] tabuleiro;
    private RenderizadorClasse renderizador;
    private JogadorClasse[] jogadores;


    class QuadradoMatriz {
        int id;
        int linha;
        int coluna;
        Cor cor;
        List<JogadorClasse> jogadoresNoQuadrado;

        public QuadradoMatriz(int id, int linha, int coluna, Cor cor) {
            this.id = id;
            this.linha = linha;
            this.coluna = coluna;
            this.cor = cor;
            this.jogadoresNoQuadrado = new ArrayList<>();
        }

        public boolean estaPintado() {
            return this.cor != Cor.BRANCO; // Cor BRANCO define quadrados inválidos
        }

        public void adicionarJogador(JogadorClasse jogador) {
            jogadoresNoQuadrado.add(jogador);
            jogador.mover(linha, coluna);
        }

        public void removerJogador(JogadorClasse jogador) {
            jogadoresNoQuadrado.remove(jogador);
        }
    }

    public TabuleiroClasse(RenderizadorClasse renderizador) {
        this.renderizador = renderizador;
        this.tabuleiro = new QuadradoMatriz[numLinhas][numColunas];
        this.jogadores = new JogadorClasse[4];
        renderizador.iniciar();
        inicializarTabuleiro();
        inicializarJogadores();
    }

    private void inicializarJogadores() {
        this.jogadores[0] = new JogadorClasse(0, Cor.VERMELHO, 0, 0, this.renderizador);
        this.jogadores[1] = new JogadorClasse(1, Cor.AMARELO, 0, 0, this.renderizador);
        this.jogadores[2] = new JogadorClasse(2, Cor.VERDE, 0, 0, this.renderizador);
        this.jogadores[3] = new JogadorClasse(3, Cor.AZUL, 0, 0, this.renderizador);

        for (JogadorClasse jogador : jogadores) {
            tabuleiro[0][0].adicionarJogador(jogador);
        }
    }

    public boolean moverJogador(JogadorClasse jogador, int novaLinha, int novaColuna) {
        // Verifica se a nova posição está dentro dos limites do tabuleiro
        if (novaLinha < 0 || novaLinha >= numLinhas || novaColuna < 0 || novaColuna >= numColunas) {
            return false;
        }

        QuadradoMatriz quadradoAtual = tabuleiro[jogador.getPosicaoLinha()][jogador.getPosicaoColuna()];
        QuadradoMatriz novoQuadrado = tabuleiro[novaLinha][novaColuna];

        if (novoQuadrado.estaPintado()) {
            quadradoAtual.removerJogador(jogador);
            novoQuadrado.adicionarJogador(jogador);
            return true;
        }
        return false;
    }

    public void moverJogadorAutomatico(JogadorClasse jogador) {
        int novaColuna = jogador.getPosicaoColuna() + 1;
        if (!moverJogador(jogador, jogador.getPosicaoLinha(), novaColuna)) {
            int novaLinha = jogador.getPosicaoLinha() + 1;
            moverJogador(jogador, novaLinha, jogador.getPosicaoColuna());
        }
    }

    private void desenharJogadores() {
        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                QuadradoMatriz quadrado = tabuleiro[linha][coluna];
                if (tabuleiro[linha][coluna].jogadoresNoQuadrado.size() > 0) {
                    desenharQuadrantes(quadrado);
                }
            }
        }
    }

    private void desenharQuadrantes(QuadradoMatriz quadrado) {
        int quadranteTamanho = tamanhoQuadradoMatriz / 2;
        int x = quadrado.coluna * tamanhoQuadradoMatriz;
        int y = quadrado.linha * tamanhoQuadradoMatriz;

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

        desenharJogadores();
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
