package mx.itesm.meteorlag;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * Created by Adrian on 2/11/15.
 */
public class CreditScene extends BaseScene
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)

    @Override
    public void createScene() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, resourcesController.regionFondoCreditos, resourcesController.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        // Configuración de la imagen
        spriteFondo.setPosition(GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT /2);


        attachChild(spriteFondo);

        setBackground(resourcesController.GAME_MENUS_BACKGROUND);
        setBackgroundEnabled(true);



    }

    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal
        scenesController.crearEscenaMenu();
        scenesController.setEscena(SceneType.MENU_SCENE);
        scenesController.liberarEscenaCreditos();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.CREDITS_SCENE;
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