package com.example.adrian.meteorlag.GameAndScenes;

import org.andengine.engine.Engine;

/**
 * Administra la escena que se verá en la pantalla
 */
public class ScenesController
{
    // Instancia única
    private static final ScenesController INSTANCE =
            new ScenesController();

    // Declara las distintas escenas que forman el juego
    private BaseScene escenaSplash;
    private BaseScene escenaMenu;
    private BaseScene escenaAcercaDe;
    private BaseScene escenaJuego;
    private BaseScene escenaLS;


    // El tipo de escena que se está mostrando
    private SceneType actualSceneType = SceneType.SPLASH_SCENE;
    // La escena que se está mostrando
    private BaseScene escenaActual;
    // El engine para hacer el cambio de escenas
    private Engine engine = ResourcesController.getInstance().engine;
    // El administrados de recursos
    private ResourcesController admRecursos = ResourcesController.getInstance();

    // Regresa la instancia del administrador de escenas
    public static ScenesController getInstance() {
        return INSTANCE;
    }

    // Regresa el tipo de la escena actual
    public SceneType getActualSceneType() {
        return actualSceneType;
    }

    // Regresa la escena actual
    public BaseScene getEscenaActual() {
        return escenaActual;
    }

    /*
     * Pone en la pantalla la escena que llega como parámetro y guarda el nuevo estado
     */
    private void setEscenaBase(BaseScene nueva) {
        engine.setScene(nueva);
        escenaActual = nueva;
        actualSceneType = nueva.getSceneType();
    }

    /**
     * Cambia a la escena especificada en el parámetro
     * @param nuevoTipo la nueva escena que se quiere mostrar
     */
    public void setEscena(SceneType nuevoTipo) {
        switch (nuevoTipo) {
            case SPLASH_SCENE:
                setEscenaBase(escenaSplash);
                break;
            case MENU_SCENE:
                setEscenaBase(escenaMenu);
                break;
            case ABOUT_SCENE:
                setEscenaBase(escenaAcercaDe);
                break;
            case GAME_SCENE:
                setEscenaBase(escenaJuego);
                break;
            case LEVEL_SELECTION_SCENE:
                setEscenaBase(escenaLS);
                break;
        }
    }

    //*** Crea la escena de Splash
    public void crearEscenaSplash() {
        // Carga los recursos
        admRecursos.loadUniversalResources();
        admRecursos.cargarRecursosSplash();
        escenaSplash = new SplashScene();
    }

    //*** Libera la escena de Splash
    public void liberarEscenaSplash() {

        admRecursos.liberarRecursosSplash();
        escenaSplash.unloadScene();
        escenaSplash = null;
    }

    // ** MENU
    //*** Crea la escena de MENU
    public void crearEscenaMenu() {
        // Carga los recursos
        admRecursos.cargarRecursosMenu();
        escenaMenu = new MenuScene();
    }

    //*** Libera la escena de MENU
    public void liberarEscenaMenu() {
        admRecursos.liberarRecursosMenu();
        escenaMenu.unloadScene();
        escenaMenu = null;
    }

    //*** Crea la escena de Acerca De
    public void crearEscenaAcercaDe() {
        // Carga los recursos
        admRecursos.cargarRecursosAcercaDe();
        escenaAcercaDe = new AboutScene();
    }

    //*** Libera la escena de Splash
    public void liberarEscenaAcercaDe() {
        admRecursos.liberarRecursosAcercaDe();
        escenaAcercaDe.unloadScene();
        escenaAcercaDe = null;
    }

    //*** Crea la escena de Acerca De
    public void crearEscenaJuego() {
        // Carga los recursos
        escenaJuego = new MainGameScene();
    }

    //*** Libera la escena de Splash
    public void liberarEscenaJuego() {
        escenaJuego.unloadScene();
        escenaJuego = null;
    }

    //*** Crea la escena de Acerca De
    public void crearEscenaLS() {
        // Carga los recursos
        admRecursos.cargarRecursosLS();
        escenaLS = new LevelSelectionScene();
    }

    //*** Libera la escena de Splash
    public void liberarEscenaLS() {
        admRecursos.liberarRecursosLS();
        escenaLS.unloadScene();
        escenaLS = null;
    }



}