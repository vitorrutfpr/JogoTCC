package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class JogadorClasse {
    private final int id;
    private final Cor cor;
    private int posicaoLinha;
    private int posicaoColuna;
    private boolean respostaCorreta;
    private RenderizadorClasse renderizador;

    public JogadorClasse(int id, Cor cor, int posicaoInicialLinha, int posicaoInicialColuna, RenderizadorClasse renderizador) {
        this.id = id;
        this.cor = cor;
        this.posicaoLinha = posicaoInicialLinha;
        this.posicaoColuna = posicaoInicialColuna;
        this.respostaCorreta = true;
        this.renderizador = renderizador;
    }

    public int getId() {
        return id;
    }

    public Cor getCor() {
        return cor;
    }

    public int getPosicaoLinha() {
        return this.posicaoLinha;
    }

    public int getPosicaoColuna() {
        return this.posicaoColuna;
    }

    public boolean getRespostaCorreta() { return this.respostaCorreta; }

    public void mover(int novaLinha, int novaColuna) {
        this.posicaoLinha = novaLinha;
        this.posicaoColuna = novaColuna;
    }

    public void responderPergunta(){
        this.respostaCorreta = true;

    }

    private void desenharJogador() {
        this.renderizador.desenharJogador(this);
    }


}
