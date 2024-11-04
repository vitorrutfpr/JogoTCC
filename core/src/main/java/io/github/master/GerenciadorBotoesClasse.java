package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import java.util.Random;

public class GerenciadorBotoesClasse {

    private static GerenciadorBotoesClasse instanciaUnica;
    private Stage stage;
    private Skin skin;
    private GerenciadorCameraClasse gerenciadorCamera;
    private GerenciadorBotoesClasse() {
        this.stage = null;
        this.gerenciadorCamera = GerenciadorCameraClasse.getInstancia();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(stage);
    }

    public static GerenciadorBotoesClasse getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new GerenciadorBotoesClasse();
        }
        return instanciaUnica;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void render(float delta) {
        gerenciadorCamera.atualizar();
        stage.act(delta);
        stage.draw();
    }

    public void resize(int largura, int altura) {
        gerenciadorCamera.getCamera().update();

        stage.getViewport().update(largura, altura, true);
        Gdx.input.setInputProcessor(stage);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
