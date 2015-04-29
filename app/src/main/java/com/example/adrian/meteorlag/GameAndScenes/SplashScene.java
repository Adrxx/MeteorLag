package com.example.adrian.meteorlag.GameAndScenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 */
public class SplashScene extends BaseScene
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)

    @Override
    public void createScene() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, resourcesController.regionSplash, resourcesController.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        // Configuración de la imagen
        spriteFondo.setPosition(GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT /2);
        spriteFondo.setScale(1.0f);
        spriteFondo.setAlpha(0);
        attachChild(spriteFondo);

        AlphaModifier wait = new AlphaModifier(0.5f,0.0f,0.0f);
        AlphaModifier fadeIn = new AlphaModifier(0.5f,0.0f,1.0f);
        AlphaModifier still = new AlphaModifier(2.0f,1.0f,1.0f);
        AlphaModifier fadeOut = new AlphaModifier(0.5f,1.0f,0.0f);
        SequenceEntityModifier seq = new SequenceEntityModifier(wait,fadeIn,still,fadeOut);

        spriteFondo.registerEntityModifier(seq);

        setBackground(resourcesController.GAME_MENUS_BACKGROUND);
        setBackgroundEnabled(true);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SPLASH_SCENE;
    }

    @Override
    public void unloadScene() {
        // Liberar cada recurso usado en esta escena
        spriteFondo.detachSelf();   // Se desconecta de la escena
        spriteFondo.dispose();      // Libera la memoria

        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}