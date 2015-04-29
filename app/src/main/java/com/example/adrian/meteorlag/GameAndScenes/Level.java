package com.example.adrian.meteorlag.GameAndScenes;

import android.opengl.GLES20;
import android.util.Log;

import com.example.adrian.meteorlag.GameAndScenes.Interface.VerticalGameBackground;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.AntigravityLagger;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.LagBar;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.Lagger;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.MissileLagger;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.SuperMissileLagger;
import com.example.adrian.meteorlag.Meteor;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Adrian on 3/11/15.
 */
public class Level
{
    final static public String LEVEL_FOLDER = "Levels/";
    final static public String INTERFACE_FOLDER = "Interface/";

    final static public String DATA_FILE = "data.json";
    final static public String BACKGROUND_FILE = "/bg.png";
    final static public String BACKGROUND_FILE_MIDDLE = "/bg_m.png";
    final static public String BACKGROUND_FILE_TOP = "/bg_t.png";

    final static public String BACKGROUND_ENDING_FILE = "/bg_end.png";
    final static public String METEOR_FILE = "/meteor.png";
    final static public String METEOR_TRAIL_FILE = "/trail.png";
    final static public String IN_LEVEL_LAGGER_FILE = "/lagger.png";
    final static public String IN_LEVEL_SPEEDER_FILE = "/speeder.png";

    protected ResourcesController resourcesController;
    protected MainGameScene scene;

    private ITexture textureBackground;
    public ITextureRegion regionBackground;

    public ITextureRegion regionBackgroundMiddle;

    public ITextureRegion regionBackgroundTop;

    public ITextureRegion regionBackgroundEnding;

    private ITexture textureMeteor;
    public ITextureRegion regionMeteor;

    private ITexture textureMeteorTrail;
    public ITextureRegion regionMeteorTrail;

    private ITexture textureInGameLagger;
    public ITextureRegion regionInGameLagger;

    private ITexture textureInGameSpeeder;
    public ITextureRegion regionInGameSpeeder;

    private ITexture textureWeaponLaggerMissile;
    public ITextureRegion regionWeaponLaggerMissile;

    private ITexture textureWeaponLaggerSuperMissile;
    public ITextureRegion regionWeaponLaggerSuperMissile;

    private ITexture textureWeaponLaggerPortal;
    public ITextureRegion regionWeaponLaggerPortal;

    private ITexture textureWeaponLaggerAntigravity;
    public ITextureRegion regionWeaponLaggerAntigravity;

    private ITexture textureWeaponLaggerSpecial;
    public ITextureRegion regionWeaponLaggerSpecial;

    private String id;
    public LevelJSON propierties;

    public Level(String id,ResourcesController adm){
        this.id = id;
        this.resourcesController = adm;
        final Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        listAssetFiles("");

        LevelJSON propierties =  gson.fromJson(loadJSONFromAsset("data.json"), LevelJSON.class);
        this.propierties = propierties;

        loadResources();
    }

    public String loadJSONFromAsset(String asset) {
        String json = null;
        try {

            InputStream is = this.resourcesController.gameControl.getAssets().open(asset);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private boolean listAssetFiles(String path) {

        String [] list;
        try {
            list = this.resourcesController.gameControl.getAssets().list(path);

                for (String file : list) {
                    Log.d("JSOOOOON",(path + "/" + file));
                }

        } catch (IOException e) {
                return false;
         }

            return true;
    }

    public void loadResources()
    {

        //BACKGROUND
        try {
            textureBackground = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + BACKGROUND_FILE);
            regionBackground = TextureRegionFactory.extractFromTexture(textureBackground);
            textureBackground.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + BACKGROUND_FILE);
        }

        //BACKGROUND MIDDLE
        try {
            ITexture textureBackgroundMiddle = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + BACKGROUND_FILE_MIDDLE);
            regionBackgroundMiddle = TextureRegionFactory.extractFromTexture(textureBackgroundMiddle);
            textureBackgroundMiddle.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + BACKGROUND_FILE_MIDDLE);
        }

        //BACKGROUND TOP
        try {
            ITexture textureBackgroundTop = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + BACKGROUND_FILE_TOP);
            regionBackgroundTop = TextureRegionFactory.extractFromTexture(textureBackgroundTop);
            textureBackgroundTop.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + BACKGROUND_FILE_TOP);
        }

        //BACKGROUND ENDING
        try {
            ITexture textureBackgroundEnding = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + BACKGROUND_ENDING_FILE);
            regionBackgroundEnding = TextureRegionFactory.extractFromTexture(textureBackgroundEnding);
            textureBackgroundEnding.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + BACKGROUND_ENDING_FILE);
        }

        //METEOR
        try {
            textureMeteor = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + METEOR_FILE);
            regionMeteor = TextureRegionFactory.extractFromTexture(textureMeteor);
            textureMeteor.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + METEOR_FILE);
        }

        //METEOR TRAIL
        try {
            textureMeteorTrail = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + METEOR_TRAIL_FILE);
            regionMeteorTrail = TextureRegionFactory.extractFromTexture(textureMeteorTrail);
            textureMeteorTrail.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + METEOR_TRAIL_FILE);
        }


        //IN GAME LAGGER
        try {
            textureInGameLagger = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + IN_LEVEL_LAGGER_FILE);
            regionInGameLagger = TextureRegionFactory.extractFromTexture(textureInGameLagger);
            textureInGameLagger.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + IN_LEVEL_LAGGER_FILE);
        }

        //IN GAME SPEEDER
        try {
            textureInGameSpeeder = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + IN_LEVEL_SPEEDER_FILE);
            regionInGameSpeeder = TextureRegionFactory.extractFromTexture(textureInGameSpeeder);
            textureInGameSpeeder.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + IN_LEVEL_SPEEDER_FILE);
        }

    }

    public ITextureRegion getInGameLaggerRegion()
    {
        return regionInGameLagger;
    }

    public ITextureRegion getInGameSpeederRegion()
    {
        return regionInGameSpeeder;
    }

    public VerticalGameBackground getParallaxBackground()
    {
        VerticalGameBackground b = new VerticalGameBackground(1.0f,regionBackground,regionBackgroundEnding,this.resourcesController);

        final float x = GameControl.CAMERA_WIDTH/2;

        Sprite rS= new Sprite(x,0, regionBackgroundMiddle,resourcesController.vbom);
        Sprite eS = new Sprite(x,0, regionBackgroundTop,resourcesController.vbom);

        b.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.26f,rS));
        b.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.4f,eS));

        return b;
    }

    public Meteor getMeteor()
    {
        return new Meteor(0,0, regionMeteor, resourcesController.vbom);
    }

    public ParticleSystem<Sprite> getTrailParticleSystem() {

        IEntityFactory<Sprite> ief = new IEntityFactory<Sprite>() {
            @Override
            public Sprite create(float pX, float pY)
            {
                return new Sprite(pX,pY,Level.this.regionMeteorTrail,resourcesController.vbom);
            }
        };

        CircleParticleEmitter trailEmmiter = new CircleParticleEmitter(0.0f,0.0f,this.regionMeteor.getWidth()/2 + 5.0f);

        ParticleSystem<Sprite> trailParticleSystem = new ParticleSystem<Sprite>(ief,trailEmmiter,60,90,180);
        trailParticleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(
                GLES20.GL_SRC_ALPHA,GLES20.GL_ONE));
        float tiempoVida = 2.0f;   // Segundos de vida de cada partícula
        trailParticleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(tiempoVida));
        trailParticleSystem.addParticleInitializer(new ScaleParticleInitializer<Sprite>(2.0f,5.5f));
        trailParticleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(-100, 100));
        trailParticleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f,1.5f,0.45f,0.0f));

        trailParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f,0.05f,1.0f,5.0f));
        trailParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.05f,1.0f,5.0f,0.5f));

        return trailParticleSystem;
    }


    public LagBar getLagBar() {

        ArrayList<Lagger> laggers = new ArrayList<>(5);
        laggers.add(new MissileLagger(this.scene));
        laggers.add(new SuperMissileLagger(this.scene));
        laggers.add(new AntigravityLagger(this.scene));
        laggers.add(new AntigravityLagger(this.scene));
        laggers.add(new AntigravityLagger(this.scene));
        return new LagBar(0,0,laggers, resourcesController);

    }



    public void unloadResources()
    {
        textureBackground.unload();
        regionBackground = null;

        textureMeteor.unload();
        regionMeteor = null;

        textureMeteorTrail.unload();
        regionMeteorTrail = null;

        textureInGameSpeeder.unload();
        regionInGameSpeeder = null;

        textureInGameLagger.unload();
        regionInGameLagger = null;

        textureWeaponLaggerMissile.unload();
        regionWeaponLaggerMissile = null;

        textureWeaponLaggerSuperMissile.unload();
        regionWeaponLaggerSuperMissile = null;

        textureWeaponLaggerPortal.unload();
        regionWeaponLaggerPortal = null;

        textureWeaponLaggerAntigravity.unload();
        regionWeaponLaggerAntigravity = null;

    }



}
