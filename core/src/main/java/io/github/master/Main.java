package io.github.master;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class Main extends ApplicationAdapter {

    private Tabuleiro tabuleiro;

    @Override
    public void create() {
        Renderizador renderizador = new RenderizarForma();
        tabuleiro = new Tabuleiro(renderizador);
        tabuleiro.inicializar();
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
