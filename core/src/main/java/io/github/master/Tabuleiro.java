package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tabuleiro {

    private final int tamanhoQuadrado = 80;
    private final int numLinhas = 10;
    private final int numColunas = 10;
    private Color[][] coresTabuleiro;
    private Renderizador renderizador;

    public Tabuleiro(Renderizador renderizador) {
        this.renderizador = renderizador;
    }

    public void inicializar() {
        renderizador.iniciar();
        coresTabuleiro = new Color[numLinhas][numColunas];
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        Color[] cores = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        int indiceCor = 0;

        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                coresTabuleiro[linha][coluna] = Color.WHITE;
            }
        }

        boolean conectarDireita = true;

        for (int linha = 0; linha < numLinhas; linha += 3) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                coresTabuleiro[linha][coluna] = cores[indiceCor % cores.length];
            }

            if (conectarDireita) {
                for (int i = 0; i < 3 && (linha + i) < numLinhas; i++) {
                    coresTabuleiro[linha + i][numColunas - 1] = cores[indiceCor % cores.length];
                }
            } else {
                for (int i = 0; i < 3 && (linha + i) < numLinhas; i++) {
                    coresTabuleiro[linha + i][0] = cores[indiceCor % cores.length];
                }
            }

            conectarDireita = !conectarDireita;
            indiceCor++;
        }
        pintarTabuleiroAleatoriamente();
    }

    private void pintarTabuleiroAleatoriamente() {
        Color[] cores = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

        Color corAnterior = null;

        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                if (coresTabuleiro[linha][coluna].equals(Color.WHITE)) {
                    continue;
                }

                Color novaCor;

                do {
                    novaCor = cores[(int) (Math.random() * cores.length)];
                } while (novaCor.equals(corAnterior));

                coresTabuleiro[linha][coluna] = novaCor;
                corAnterior = novaCor;
            }
            corAnterior = null;
        }
    }

    private void desenharTabuleiro() {
        float deslocamentoX = Gdx.graphics.getWidth() * (1.0f / 3.0f);
        float deslocamentoY = (Gdx.graphics.getHeight() - (numLinhas * tamanhoQuadrado)) / 2.0f;

        ShapeRenderer renderizadorForma = ((RenderizarForma) renderizador).getRenderizadorForma();

        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                float x = deslocamentoX + coluna * tamanhoQuadrado;
                float y = deslocamentoY + linha * tamanhoQuadrado;

                renderizadorForma.setColor(coresTabuleiro[linha][coluna]);
                renderizadorForma.rect(x, y, tamanhoQuadrado, tamanhoQuadrado);
            }
        }

        renderizadorForma.end();

        //esse é o shape que faz as bordas
        renderizadorForma.begin(ShapeRenderer.ShapeType.Line);
        renderizadorForma.setColor(Color.BLACK);

        for (int linha = 0; linha < numLinhas; linha++) {
            for (int coluna = 0; coluna < numColunas; coluna++) {
                //evita as de por borda nas partes que nao tao pintadas
                if (!coresTabuleiro[linha][coluna].equals(Color.WHITE)) {
                    float x = deslocamentoX + coluna * tamanhoQuadrado;
                    float y = deslocamentoY + linha * tamanhoQuadrado;

                    //faz as bordas
                    renderizadorForma.rectLine(x, y, x + tamanhoQuadrado, y, 1);
                    renderizadorForma.rectLine(x, y, x, y + tamanhoQuadrado, 1);
                    renderizadorForma.rectLine(x + tamanhoQuadrado, y, x + tamanhoQuadrado, y + tamanhoQuadrado, 1); // Borda direita
                    renderizadorForma.rectLine(x, y + tamanhoQuadrado, x + tamanhoQuadrado, y + tamanhoQuadrado, 1); // Borda inferior
                }
            }
        }
        renderizadorForma.end();
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
