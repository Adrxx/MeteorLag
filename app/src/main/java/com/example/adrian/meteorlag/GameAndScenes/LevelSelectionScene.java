package com.example.adrian.meteorlag.GameAndScenes;

import android.util.Log;

import com.google.gson.Gson;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Adrian on 3/29/15.
 */
public class LevelSelectionScene extends BaseScene {

    public int index = 0;
    public ArrayList<LevelJSON> levels;

    final static public String LEVEL_FOLDER = "Levels/";
    final static public String DATA_FILE = "/data.json";

    final static public String METEOR_FILE = "/meteor.png";

    private Text title;
    private Text subtitle;
    private Text record;
    private Text height;
    private Text neededTime;
    private Sprite meteor;
    private ITexture textureMet;
    private ITextureRegion regionMet;


    @Override
    public void createScene() {
        // Creamos el sprite de manera óptima


        ButtonSprite right = new ButtonSprite(GameControl.CAMERA_WIDTH - resourcesController.regionRightButton.getWidth()/2, GameControl.CAMERA_HEIGHT /2,
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



        ButtonSprite left = new ButtonSprite(resourcesController.regionLeftButton.getWidth()/2, GameControl.CAMERA_HEIGHT /2,
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



        final Gson gson = new Gson();

        this.levels = new ArrayList<>(10);
        this.levels.add( gson.fromJson(Level.loadJSONFromAsset(LEVEL_FOLDER+"Tierra"+DATA_FILE), LevelJSON.class) );
        this.levels.add( gson.fromJson(Level.loadJSONFromAsset(LEVEL_FOLDER+"Caramelo"+DATA_FILE), LevelJSON.class) );
        this.levels.add( gson.fromJson(Level.loadJSONFromAsset(LEVEL_FOLDER+"Orion"+DATA_FILE), LevelJSON.class) );



        setBackground(resourcesController.GAME_MENUS_BACKGROUND);
        setBackgroundEnabled(true);

        LevelJSON current = this.levels.get(index);

        this.title = resourcesController.generateText("                             ",GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2 + 350,92,0xFFFFFFFF);
        this.subtitle = resourcesController.generateText("                             ",GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2 + 280,58,0xFFFFFFFF);

        this.height = resourcesController.generateText("                             ",GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2 - 180,50,0xFFFFFFFF);
        this.neededTime = resourcesController.generateText("                             ",GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2 - 230,50,0xFFFFFFFF);
        this.record = resourcesController.generateText("                             ",GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2 - 290,50,0xFFFFFFFF);

        attachChild(title);
        attachChild(subtitle);
        attachChild(height);
        attachChild(neededTime);
        attachChild(record);

        this.meteor = new Sprite(GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT /2,
                resourcesController.logoR, resourcesController.vbom);
        attachChild(meteor);

        setupLevelAt(index);



    }


    public void previous()
    {
        index--;
        if (index < 0)
        {
            index = 0;
        }

        setupLevelAt(index);



    }

    public void next()
    {
        index++;
        if (index > (this.levels.size()-1))
        {
            index = (this.levels.size()-1);
        }
        setupLevelAt(index);

    }

    public void setupLevelAt(int index)
    {

        LevelJSON current = this.levels.get(index);

        this.title.setText(current.getTitle());
        this.subtitle.setText(current.getSubtitle());

        this.height.setText(current.getHeight()+ " KM");

        this.neededTime.setText(current.getTime() + " mins");

        this.record.setText("---");

        try {
            textureMet = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + current.getTitle()+ "/meteor.png");
            regionMet = TextureRegionFactory.extractFromTexture(textureMet);
            textureMet.load();
        } catch (IOException e) {
            Log.d("NIVEL LS METEORO ", "No se pueden cargar la METEORO");
        }

        this.meteor.detachSelf();

        this.meteor = new Sprite(GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT /2,
                regionMet, resourcesController.vbom);
        this.meteor.setSize(this.meteor.getWidth()*2,this.meteor.getHeight()*2);

        attachChild(meteor);


    }

    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal
        scenesController.crearEscenaMenu();
        scenesController.setEscena(SceneType.MENU_SCENE);
        scenesController.liberarEscenaLS();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.LEVEL_SELECTION_SCENE;
    }

    @Override
    public void unloadScene() {

        textureMet.unload();
        regionMet = null;

        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}
