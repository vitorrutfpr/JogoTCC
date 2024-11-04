package io.github.master;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GerenciadorCameraClasse {

    private static GerenciadorCameraClasse instanciaUnica;
    private OrthographicCamera camera;
    private Viewport viewport;

    private float larguraOriginal;
    private float alturaOriginal;

    private GerenciadorCameraClasse() {
        this.larguraOriginal = 1400;
        this.alturaOriginal = 1200;

        camera = new OrthographicCamera();
        viewport = new FitViewport(larguraOriginal, alturaOriginal, camera);

        centralizarCamera();
    }

    public static GerenciadorCameraClasse getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new GerenciadorCameraClasse();
        }
        return instanciaUnica;
    }

    public void redimensionar(int largura, int altura) {
        viewport.update(largura, altura, true);
        centralizarCamera();
    }

    public void atualizar() {
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setZoom(float zoom) {
        camera.zoom = zoom;
    }

    public float getZoom() {
        return camera.zoom;
    }

    private void centralizarCamera() {
        camera.position.set(200, viewport.getWorldHeight() / 2 - 200, 0);
        camera.update();
    }

    public void resize(int largura, int altura, Resizable resizable) {
        this.redimensionar(largura, altura);
        if (resizable != null) {
            resizable.resize(largura, altura); // Chama o método resize da classe que precisa redimensionar
        }
    }

    public interface Resizable {
        void resize(int largura, int altura);
    }
}
