package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerguntaClasse implements GerenciadorCameraClasse.Resizable {
    private Stage stage;
    private Skin skin;
    private List<Pergunta> perguntas;
    private boolean mostrarPergunta;

    private TabuleiroClasse tabuleiro;

    public PerguntaClasse(Stage stage, TabuleiroClasse tabuleiro) {
        this.tabuleiro = tabuleiro;
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.perguntas = new ArrayList<>();
        this.mostrarPergunta = false;

        Gdx.input.setInputProcessor(this.stage);
        carregarPerguntas();
    }

    private void carregarPerguntas() {
        Json json = new Json();
        FileHandle pastaPerguntas = Gdx.files.internal("perguntas");

        if (pastaPerguntas.isDirectory()) {
            for (FileHandle arquivo : pastaPerguntas.list()) {
                if (arquivo.extension().equals("json")) {
                    Pergunta pergunta = json.fromJson(Pergunta.class, arquivo.readString());
                    perguntas.add(pergunta);
                }
            }
        }
    }

    public void mostrarPerguntaAleatoria() {
        if (perguntas.isEmpty()) {
            System.out.println("Nenhuma pergunta disponível.");
            return;
        }
        stage.clear();  // Limpa os atores do stage antes de adicionar a nova pergunta
        Random random = new Random();
        Pergunta perguntaAleatoria = perguntas.get(random.nextInt(perguntas.size()));
        mostrarPergunta(perguntaAleatoria);
    }

    private void mostrarPergunta(Pergunta pergunta) {
        BitmapFont font = getFont();
        Label perguntaLabel = criarLabelPergunta(pergunta, font);
        stage.addActor(perguntaLabel);

        criarAlternativasLabels(pergunta, font);
    }

    private BitmapFont getFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontes/Roboto-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    private Label criarLabelPergunta(Pergunta pergunta, BitmapFont font) {
        Label.LabelStyle estiloPergunta = new Label.LabelStyle();
        estiloPergunta.font = font;

        Label perguntaLabel = new Label(pergunta.pergunta, estiloPergunta);
        perguntaLabel.setAlignment(Align.center);
        perguntaLabel.setWrap(true);

        float larguraMaximaPergunta = Gdx.graphics.getWidth() * 0.8f;
        perguntaLabel.setWidth(larguraMaximaPergunta);
        perguntaLabel.setPosition(Gdx.graphics.getWidth() / 2 - larguraMaximaPergunta / 2, Gdx.graphics.getHeight() - 150);

        return perguntaLabel;
    }

    private void criarAlternativasLabels(Pergunta pergunta, BitmapFont font) {
        Label.LabelStyle estiloAlternativa = new Label.LabelStyle();
        estiloAlternativa.font = font;

        float perguntaLabelHeight = 100;
        float botaoYBase = Gdx.graphics.getHeight() - (200 + perguntaLabelHeight);

        for (int i = 0; i < pergunta.alternativas.size(); i++) {
            Alternativa alternativa = pergunta.alternativas.get(i);
            String alternativaTexto = alternativa.alternativa + ": " + alternativa.solucao;
            Label botaoLabel = new Label(alternativaTexto, estiloAlternativa);

            float larguraMaximaAlternativa = Gdx.graphics.getWidth() * 0.8f;
            botaoLabel.setWidth(larguraMaximaAlternativa);
            botaoLabel.setWrap(true);

            float botaoY = botaoYBase - (i * 80);
            botaoLabel.setPosition(20, botaoY);
            botaoLabel.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setMostrarPergunta(false);
                }
            });

            stage.addActor(botaoLabel);
        }
    }

    @Override
    public void resize(int largura, int altura) {
        stage.getViewport().update(largura, altura, true);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public boolean getMostrarPergunta(){
        return this.mostrarPergunta;
    }

    public void setMostrarPergunta(boolean mostrar){
        this.mostrarPergunta = mostrar;
    }

}
