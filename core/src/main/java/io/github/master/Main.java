package io.github.master;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class Main extends ApplicationAdapter {

    private JogoClasse jogo;

    @Override
    public void create() {
        jogo = new JogoClasse();
    }

    @Override
    public void render() {
        jogo.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int largura, int altura) {
        GerenciadorCameraClasse.getInstancia().redimensionar(largura, altura);
    }

    @Override
    public void dispose() {
        jogo.dispose();
    }
}
