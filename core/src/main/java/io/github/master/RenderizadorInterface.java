package io.github.master;

public interface RenderizadorInterface {
    void iniciar();
    void render(float delta);
    void resize(int largura, int altura);
    void dispose();
    void desenharRetangulo(float x, float y, float largura, float altura, Cor cor);
    void desenharBordaRetangulo(float x, float y, float largura, float altura, Cor cor);
}
