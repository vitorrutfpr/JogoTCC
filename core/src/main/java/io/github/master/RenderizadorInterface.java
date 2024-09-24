package io.github.master;

public interface Renderizador {
    void iniciar();
    void render(float delta);
    void resize(int largura, int altura);
    void dispose();
}
