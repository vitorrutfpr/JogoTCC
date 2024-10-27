package io.github.master;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class Main extends ApplicationAdapter {

    private TabuleiroClasse tabuleiro;

    @Override
    public void create() {
        RenderizadorClasse renderizadorClasse = new RenderizadorClasse();
        tabuleiro = new TabuleiroClasse(renderizadorClasse);
    }

    @Override
    public void render() {
        tabuleiro.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int largura, int altura) {
        tabuleiro.resize(largura, altura);
    }

    @Override
    public void dispose() {
        tabuleiro.dispose();
    }
}
