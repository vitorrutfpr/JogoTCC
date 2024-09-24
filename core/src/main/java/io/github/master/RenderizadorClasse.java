package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RenderizarForma implements Renderizador {

    private ShapeRenderer renderizadorForma;
    private OrthographicCamera camera;

    @Override
    public void iniciar() {
        renderizadorForma = new ShapeRenderer();
        camera = new OrthographicCamera();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderizadorForma.setProjectionMatrix(camera.combined);
        renderizadorForma.begin(ShapeRenderer.ShapeType.Filled);
    }

    @Override
    public void resize(int largura, int altura) {
        camera.setToOrtho(false, largura, altura);
        camera.update();
    }

    @Override
    public void dispose() {
        renderizadorForma.dispose();
    }

    public ShapeRenderer getRenderizadorForma() {
        return renderizadorForma;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
