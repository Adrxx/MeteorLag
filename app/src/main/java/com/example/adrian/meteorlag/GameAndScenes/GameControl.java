package com.example.adrian.meteorlag.GameAndScenes;

import android.view.KeyEvent;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

public class GameControl extends SimpleBaseGameActivity
{
    // Dimensiones de la cámara
    public static final int CAMERA_WIDTH = 540;
    public static final int CAMERA_HEIGHT = 960;
    // La cámara
    private Camera camera;
    // El administrador de escenas
    private ScenesController scenesController;

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
        /*return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT), camera);*/

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new FillResolutionPolicy(), camera);
    }

    @Override
    protected void onCreateResources() throws IOException {
        // Pasamos toda la información al administrador de recursos
        ResourcesController.inicializarAdministrador(mEngine, this,
                camera, getVertexBufferObjectManager());
        scenesController = ScenesController.getInstance();
    }

    @Override
    protected Scene onCreateScene() {
        // Crea la primer escena que se quiere mostrar

        scenesController.crearEscenaSplash();
        scenesController.setEscena(SceneType.SPLASH_SCENE);

        // Programa la carga de la segunda escena, después de cierto tiempo
        mEngine.registerUpdateHandler(new TimerHandler(3.5f,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        mEngine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer
                        // Cambia a la escena del MENU
                        //** 1. Crea la escena del menú
                        //** 2. Pone la escena del menú
                        //** 3. LIBERA la escena de Splash
                        scenesController.crearEscenaMenu();
                        scenesController.setEscena(SceneType.MENU_SCENE);
                        scenesController.liberarEscenaSplash();
                    }
                }));

        return scenesController.getEscenaActual();
    }

    // Atiende la tecla de BACK
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            if(scenesController.getActualSceneType()== SceneType.MENU_SCENE) {
                // Si está en el menú, termina
                finish();
            } else {
                // La escena que esté en pantalla maneja el evento
                scenesController.getEscenaActual().onBackKeyPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // La aplicación sale de memoria
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scenesController !=null) {
            System.exit(0);
        }
    }
}