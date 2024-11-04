package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class RenderizadorClasse {

    private ShapeRenderer renderizadorForma;
    private GerenciadorCameraClasse gerenciadorCamera;
    private SpriteBatch batch;
    public RenderizadorClasse() {
        this.gerenciadorCamera = GerenciadorCameraClasse.getInstancia();
        renderizadorForma = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    public void desenharRetangulo(float x, float y, float largura, float altura, Cor cor) {
        renderizadorForma.begin(ShapeRenderer.ShapeType.Filled);
        renderizadorForma.setColor(getCorRenderizador(cor));
        renderizadorForma.rect(x, y, largura, altura);
        renderizadorForma.end();
    }

    public void desenharBordaRetangulo(float x, float y, float largura, float altura, Cor cor) {
        renderizadorForma.begin(ShapeRenderer.ShapeType.Filled);
        renderizadorForma.setColor(getCorRenderizador(cor));
        renderizadorForma.rectLine(x, y, x + largura, y, 3);
        renderizadorForma.rectLine(x, y, x, y + altura, 3);
        renderizadorForma.rectLine(x + largura, y, x + largura, y + altura, 3);
        renderizadorForma.rectLine(x, y + altura, x + largura, y + altura, 3);
        renderizadorForma.end();
    }

    public Color getCorRenderizador(Cor corInicial) {
        switch (corInicial) {
            case BRANCO:
                return Color.WHITE;
            case PRETO:
                return Color.BLACK;
            case VERMELHO:
                return Color.RED;
            case VERDE:
                return Color.GREEN;
            case AZUL:
                return Color.BLUE;
            case AMARELO:
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gerenciadorCamera.atualizar();
        renderizadorForma.setProjectionMatrix(gerenciadorCamera.getCamera().combined);
    }

    public void desenharJogador(JogadorClasse jogador) {
        int x = jogador.getPosicaoColuna() * 80;
        int y = jogador.getPosicaoLinha() * 80;
        desenharRetangulo(x, y, 80, 80, jogador.getCor());
    }

    public void desenharQuadradoComImagem(float x, float y, float largura, float altura, String imagemPath) {
        desenharRetangulo(x, y, largura, altura, Cor.BRANCO);

        Texture imagem = new Texture(imagemPath);
        batch.begin();
        batch.draw(imagem, x, y, largura, altura);
        batch.end();
        imagem.dispose();
    }

    public void setProjectionMatrix(Matrix4 matrix) {
        batch.setProjectionMatrix(matrix);
    }

}
