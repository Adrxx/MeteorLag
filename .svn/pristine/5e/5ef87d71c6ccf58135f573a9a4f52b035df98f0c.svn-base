package mx.itesm.meteorlag;

import mx.itesm.meteorlag.Interface.EntityLayer;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import java.util.ArrayList;

/**
 * Created by Adrian on 3/29/15.
 */
public class AboutScene extends BaseScene {

    public int index = 0;

    private ButtonSprite right;
    private ButtonSprite left;

    private ArrayList<Sprite> images;
    private EntityLayer imageLayer;


    @Override
    public void createScene() {
        // Creamos el sprite de manera óptima

        setBackground(resourcesController.GAME_MENUS_BACKGROUND);
        setBackgroundEnabled(true);


        this.right = new ButtonSprite(GameControl.CAMERA_WIDTH - resourcesController.regionRightButton.getWidth()/2, GameControl.CAMERA_HEIGHT /2,
                resourcesController.regionRightButton, resourcesController.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {

                   next();

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(right);
        this.attachChild(right);



        this.left = new ButtonSprite(resourcesController.regionLeftButton.getWidth()/2, GameControl.CAMERA_HEIGHT /2,
                resourcesController.regionLeftButton, resourcesController.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {

                    previous();

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(left);
        this.attachChild(left);


        this.images = new ArrayList<>(10);


        this.images.add( new Sprite(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,resourcesController.ra1,resourcesController.vbom) );

        this.images.add( new Sprite(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,resourcesController.ra2,resourcesController.vbom) );

        this.images.add( new Sprite(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,resourcesController.ra3,resourcesController.vbom) );

        this.images.add( new Sprite(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,resourcesController.ra4,resourcesController.vbom) );


        this.imageLayer = new EntityLayer(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,GameControl.CAMERA_WIDTH,GameControl.CAMERA_HEIGHT);

        this.attachChild(this.imageLayer);

        setupImageAt(index);

    }


    public void previous()
    {
        index--;
        if (index < 0)
        {
            index = 0;
        }

        setupImageAt(index);



    }

    public void next()
    {
        index++;
        if (index > (this.images.size()-1))
        {
            index = (this.images.size()-1);
        }
        setupImageAt(index);

    }

    public void setupImageAt(int index)
    {

        if (index >= (this.images.size()-1))
        {
            this.right.setEnabled(false);
            this.right.setAlpha(0.2f);
        }
        else
        {
            this.right.setEnabled(true);
            this.right.setAlpha(1.0f);
        }

        if (index <= 0)
        {
            this.left.setEnabled(false);
            this.left.setAlpha(0.2f);
        }
        else
        {
            this.left.setEnabled(true);
            this.left.setAlpha(1.0f);
        }


        this.imageLayer.detachChildren();
        this.imageLayer.attachChild(this.images.get(index));
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
