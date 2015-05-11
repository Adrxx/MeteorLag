package mx.itesm.meteorlag;

import org.andengine.entity.scene.Scene;

/**
 * Define el comportamiento de las ESCENAS.
 * Cada escena del juego DEBE heredar de esta clase
 */
public abstract class BaseScene extends Scene
{
    // Variable protegida para que se pueda acceder desde las subclases
    protected ResourcesController resourcesController;
    protected ScenesController scenesController;

    public BaseScene() {
        resourcesController = ResourcesController.getInstance();
        scenesController = ScenesController.getInstance();
        // Llama al método que crea la escena
        createScene();  // Este método debe implementarse en la subclase
    }

    // Métodos abstractos
    public abstract void createScene(); // Arma la escena
    public abstract void onBackKeyPressed(); // Atiende el botón de back
    public abstract SceneType getSceneType(); // Regresa el tipo de escena
    public abstract void unloadScene();   // Libera los recursos asignados


}
