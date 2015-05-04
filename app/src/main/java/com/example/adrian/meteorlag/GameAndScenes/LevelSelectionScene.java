package com.example.adrian.meteorlag.GameAndScenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * Created by Adrian on 3/29/15.
 */
public class LevelSelectionScene extends BaseScene {


    @Override
    public void createScene() {
        // Creamos el sprite de manera óptima

        setBackground(resourcesController.GAME_MENUS_BACKGROUND);
        setBackgroundEnabled(true);
    }

    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal
        scenesController.crearEscenaMenu();
        scenesController.setEscena(SceneType.MENU_SCENE);
        scenesController.liberarEscenaAcercaDe();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.ABOUT_SCENE;
    }

    @Override
    public void unloadScene() {

        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}
