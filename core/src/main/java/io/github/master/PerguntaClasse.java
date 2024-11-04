package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private boolean perguntaRespondida;
    private Pergunta perguntaAtual;
    private TabuleiroClasse tabuleiro;

    public PerguntaClasse(Stage stage, TabuleiroClasse tabuleiro) {
        this.tabuleiro = tabuleiro;
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.perguntas = new ArrayList<>();
        this.mostrarPergunta = false;
        this.perguntaRespondida = false;
        this.perguntaAtual = null;

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
        this.stage.clear();
        if(!this.perguntaRespondida){
            Random perguntaId = new Random();
            this.perguntaAtual = perguntas.get(perguntaId.nextInt(perguntas.size()));
            this.perguntaRespondida = true;
        }
        mostrarPergunta(this.perguntaAtual);
    }

    private void mostrarPergunta(Pergunta pergunta) {
        BitmapFont font = getFont();
        Label perguntaLabel = criarLabelPergunta(pergunta, font);
        this.stage.addActor(perguntaLabel);

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
        // Define um estilo básico para os botões sem a necessidade de imagens
        TextButton.TextButtonStyle estiloBotao = new TextButton.TextButtonStyle();
        estiloBotao.font = font;
        estiloBotao.fontColor = Color.WHITE;

        // Cria uma textura de cor sólida para o estado normal do botão
        Pixmap pixmapUp = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapUp.setColor(Color.DARK_GRAY);
        pixmapUp.fill();
        estiloBotao.up = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapUp)));

        // Cria uma textura de cor sólida para o estado clicado do botão
        Pixmap pixmapDown = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapDown.setColor(Color.GRAY);
        pixmapDown.fill();
        estiloBotao.down = new TextureRegionDrawable(new TextureRegion(new Texture(pixmapDown)));

        float perguntaLabelHeight = 100;
        float botaoYBase = Gdx.graphics.getHeight() - (200 + perguntaLabelHeight);

        for (int i = 0; i < pergunta.alternativas.size(); i++) {
            Alternativa alternativa = pergunta.alternativas.get(i);

            // Cria o botão com a letra da alternativa (A, B, C, etc.)
            String letraAlternativa = alternativa.alternativa;
            TextButton botaoLetra = new TextButton(letraAlternativa, estiloBotao);
            botaoLetra.setPosition(20, botaoYBase - (i * 80));

            // Cria o label com o texto completo da alternativa
            Label.LabelStyle estiloAlternativaTexto = new Label.LabelStyle(font, Color.WHITE);
            Label textoAlternativa = new Label(alternativa.solucao, estiloAlternativaTexto);
            textoAlternativa.setWrap(true);
            textoAlternativa.setWidth(Gdx.graphics.getWidth() * 0.7f);
            textoAlternativa.setPosition(80, botaoYBase - (i * 80));

            // Listener para o botão da letra
            botaoLetra.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Alternativa selecionada: " + letraAlternativa);
                    setMostrarPergunta(false);
                    setPerguntaRespondida(false);
                    voltarParaTabuleiro();
                }
            });

            // Adiciona o botão e o texto ao this.stage
            this.stage.addActor(botaoLetra);
            this.stage.addActor(textoAlternativa);
        }

        // Libera os recursos de Pixmap
        pixmapUp.dispose();
        pixmapDown.dispose();
    }




    private void voltarParaTabuleiro() {
        this.stage.clear();               // Limpa a tela da pergunta
        tabuleiro.render(Gdx.graphics.getDeltaTime());  // Renderiza o tabuleiro novamente
    }

    @Override
    public void resize(int largura, int altura) {
        this.stage.getViewport().update(largura, altura, true);
    }

    public void render() {
        this.stage.act();
        this.stage.draw();
    }

    public void dispose() {
        this.stage.dispose();
        skin.dispose();
    }
    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntaRespondida(boolean respondida){
        this.perguntaRespondida = respondida;
    }
    public boolean getMostrarPergunta(){
        return this.mostrarPergunta;
    }

    public void setMostrarPergunta(boolean mostrar){
        this.mostrarPergunta = mostrar;
    }

}
