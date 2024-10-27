package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class RenderizadorClasse implements RenderizadorInterface {

    private ShapeRenderer renderizadorForma;
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin;
    private TextButton botaoMover;

    public RenderizadorClasse() {
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

    @Override
    public void iniciar() {
        renderizadorForma = new ShapeRenderer();
        camera = new OrthographicCamera();
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        botaoMover = new TextButton("Mover Jogador", skin);

        Gdx.input.setInputProcessor(stage);
        botaoMover.setSize(200, 50);
        botaoMover.setPosition(50, 400);

        this.addListenerBotao();
    }


    private void addListenerBotao(){
        botaoMover.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        stage.addActor(botaoMover);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderizadorForma.setProjectionMatrix(camera.combined);

        //renderizar botao
        stage.act(delta);
        stage.draw();
    }

    public void desenharJogador(JogadorClasse jogador) {
        int x = jogador.getPosicaoColuna() * 80;
        int y = jogador.getPosicaoLinha() * 80;
        desenharRetangulo(x, y, 80, 80, jogador.getCor());
    }

    @Override
    public void resize(int largura, int altura) {
        camera.setToOrtho(false, largura, altura);
        camera.update();
        stage.getViewport().update(largura, altura, true);
    }

    @Override
    public void dispose() {
        renderizadorForma.dispose();
        stage.dispose();
        skin.dispose();
    }
}
