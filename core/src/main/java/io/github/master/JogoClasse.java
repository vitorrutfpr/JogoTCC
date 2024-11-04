package io.github.master;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class JogoClasse {
    private Stage stage;
    private GerenciadorCameraClasse camera;
    private TabuleiroClasse tabuleiro;
    private PerguntaClasse pergunta;
    private RenderizadorClasse renderizador;
    private Skin skin;
    private TextButton botaoPergunta;


    public JogoClasse() {
        this.stage = new Stage(new ScreenViewport());
        this.camera = GerenciadorCameraClasse.getInstancia();
        this.renderizador = new RenderizadorClasse();
        this.tabuleiro = new TabuleiroClasse(renderizador);
        this.pergunta = new PerguntaClasse(stage, tabuleiro);
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        criarBotaoPergunta();

        Gdx.input.setInputProcessor(stage);
    }

    private void criarBotaoPergunta() {
        float larguraBotao = Gdx.graphics.getWidth() * 0.15f;
        float alturaBotao = Gdx.graphics.getHeight() * 0.07f;
        botaoPergunta = new TextButton("Pergunta", skin);

        botaoPergunta.setSize(larguraBotao, alturaBotao);
        botaoPergunta.setPosition(Gdx.graphics.getWidth() - larguraBotao, Gdx.graphics.getHeight() - alturaBotao);

        botaoPergunta.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botão clicado! Posição do clique: (" + x + ", " + y + ")");
                pergunta.setMostrarPergunta(true);
            }
        });
    }

    public void gerenciarTurno() {
        if(pergunta.getMostrarPergunta()){
            mostrarPergunta();
        }
        else {
            mostrarTabuleiro();
        }

    }

    public void mostrarTabuleiro() {
        stage.addActor(botaoPergunta);
        tabuleiro.render(Gdx.graphics.getDeltaTime());
    }

    public void mostrarPergunta() {
        pergunta.mostrarPerguntaAleatoria();
        pergunta.render();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.atualizar();
        gerenciarTurno();
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        pergunta.dispose();
        tabuleiro.dispose();
    }
}
