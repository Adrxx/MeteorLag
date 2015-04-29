package com.example.adrian.meteorlag.GameAndScenes;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 */

public class MenuScene extends BaseScene
{
    // *** Fondo

    // *** Botones del menú
    private ButtonSprite btnJugar;
    private ButtonSprite btnAcercaDe;
    private Sprite logo;

    @Override
    public void createScene() {

        setBackground(resourcesController.GAME_MENUS_BACKGROUND);
        setBackgroundEnabled(true);

        // Habilita los eventos de touch en ciertas áreas
        setTouchAreaBindingOnActionDownEnabled(true);


        logo = new Sprite(GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT /2 + 220,
                resourcesController.logoR, resourcesController.vbom);

        attachChild(logo);

        btnAcercaDe = new ButtonSprite(GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT /2 - 150,
                resourcesController.regionBtnAcercaDe, resourcesController.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    // Cambia a la escena de ACERCA DE
                    scenesController.crearEscenaAcercaDe();
                    scenesController.setEscena(SceneType.ABOUT_SCENE);
                    scenesController.liberarEscenaMenu();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        // *** Agrega los botones al Menú
        btnJugar = new ButtonSprite(GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT /2,
                resourcesController.regionBtnJugar, resourcesController.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {

                    // Cambia a la escena de ACERCA DE
                    this.setEnabled(false);
                    scenesController.crearEscenaJuego();
                    scenesController.setEscena(SceneType.GAME_SCENE);
                    scenesController.liberarEscenaMenu();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnAcercaDe);
        attachChild(btnAcercaDe);

        registerTouchArea(btnJugar);
        attachChild(btnJugar);
    }

    @Override
    public void onBackKeyPressed() {
        // Salir del juego
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.MENU_SCENE;
    }

    @Override
    public void unloadScene() {
        // Liberar cada recurso usado en esta escena
      // Libera la memoria
        // Btn Jugar
        btnJugar.detachSelf();
        btnJugar.dispose();

        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}