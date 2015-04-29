package com.example.adrian.meteorlag.GameAndScenes;

import android.util.Log;

import com.example.adrian.meteorlag.GameAndScenes.Interface.VerticalGameBackground;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.LagBar;
import com.example.adrian.meteorlag.Meteor;

import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.io.IOException;

/**
 * Created by Adrian on 3/11/15.
 */
public abstract class Level
{
    final static public String LEVEL_FOLDER = "Levels/Level";
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

    private ITexture textureBackgroundMiddle;
    public ITextureRegion regionBackgroundMiddle;

    private ITexture textureBackgroundTop;
    public ITextureRegion regionBackgroundTop;

    private ITexture textureBackgroundEnding;
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


    public abstract String requestLevelID();
    public abstract float requestInitialAcceleration();
    public abstract float requestInitialVelocity();
    public abstract float requestInitialHeight();
    public abstract float requestMeteorMoveResistance();
    public abstract float requestMinLevelTime();
    public abstract String requestLevelTitle();
    public abstract String requestLevelYear();
    public abstract ParticleSystem<Sprite> requestTrailParticleSystem();
    public abstract LagBar requestLagBar();

    public Level(MainGameScene scene,ResourcesController adm)
    {
        this.scene = scene;
        this.resourcesController = adm;
        loadResources();
    }

    public void loadResources()
    {

        //BACKGROUND
        try {
            textureBackground = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + requestLevelID() + BACKGROUND_FILE);
            regionBackground = TextureRegionFactory.extractFromTexture(textureBackground);
            textureBackground.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + requestLevelID() , "No se pueden cargar la imagen" + BACKGROUND_FILE);
        }

        //BACKGROUND MIDDLE
        try {
            textureBackgroundMiddle = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + requestLevelID() + BACKGROUND_FILE_MIDDLE);
            regionBackgroundMiddle = TextureRegionFactory.extractFromTexture(textureBackgroundMiddle);
            textureBackgroundMiddle.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + requestLevelID() , "No se pueden cargar la imagen" + BACKGROUND_FILE_MIDDLE);
        }

        //BACKGROUND TOP
        try {
            textureBackgroundTop = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + requestLevelID() + BACKGROUND_FILE_TOP);
            regionBackgroundTop = TextureRegionFactory.extractFromTexture(textureBackgroundTop);
            textureBackgroundTop.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + requestLevelID() , "No se pueden cargar la imagen" + BACKGROUND_FILE_TOP);
        }

        //BACKGROUND ENDING
        try {
            textureBackgroundEnding = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + requestLevelID() + BACKGROUND_ENDING_FILE);
            regionBackgroundEnding = TextureRegionFactory.extractFromTexture(textureBackgroundEnding);
            textureBackgroundEnding.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + requestLevelID() , "No se pueden cargar la imagen" + BACKGROUND_ENDING_FILE);
        }

        //METEOR
        try {
            textureMeteor = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + requestLevelID() + METEOR_FILE);
            regionMeteor = TextureRegionFactory.extractFromTexture(textureMeteor);
            textureMeteor.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + requestLevelID() , "No se pueden cargar la imagen" + METEOR_FILE);
        }

        //METEOR TRAIL
        try {
            textureMeteorTrail = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + requestLevelID() + METEOR_TRAIL_FILE);
            regionMeteorTrail = TextureRegionFactory.extractFromTexture(textureMeteorTrail);
            textureMeteorTrail.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + requestLevelID() , "No se pueden cargar la imagen" + METEOR_TRAIL_FILE);
        }


        //IN GAME LAGGER
        try {
            textureInGameLagger = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + requestLevelID() + IN_LEVEL_LAGGER_FILE);
            regionInGameLagger = TextureRegionFactory.extractFromTexture(textureInGameLagger);
            textureInGameLagger.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + requestLevelID() , "No se pueden cargar la imagen" + IN_LEVEL_LAGGER_FILE);
        }

        //IN GAME SPEEDER
        try {
            textureInGameSpeeder = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + requestLevelID() + IN_LEVEL_SPEEDER_FILE);
            regionInGameSpeeder = TextureRegionFactory.extractFromTexture(textureInGameSpeeder);
            textureInGameSpeeder.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + requestLevelID() , "No se pueden cargar la imagen" + IN_LEVEL_SPEEDER_FILE);
        }
         //LAGGERS
        try {
            textureWeaponLaggerMissile = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), "Levels/weapon_lagger_1.png");
            regionWeaponLaggerMissile = TextureRegionFactory.extractFromTexture(textureWeaponLaggerMissile);
            textureWeaponLaggerMissile.load();

            textureWeaponLaggerSuperMissile = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), "Levels/weapon_lagger_2.png");
            regionWeaponLaggerSuperMissile = TextureRegionFactory.extractFromTexture(textureWeaponLaggerSuperMissile);
            textureWeaponLaggerSuperMissile.load();

            textureWeaponLaggerPortal = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), "Levels/weapon_lagger_3.png");
            regionWeaponLaggerPortal = TextureRegionFactory.extractFromTexture(textureWeaponLaggerPortal);
            textureWeaponLaggerPortal.load();

            textureWeaponLaggerAntigravity = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), "Levels/weapon_lagger_4.png");
            regionWeaponLaggerAntigravity = TextureRegionFactory.extractFromTexture(textureWeaponLaggerAntigravity);
            textureWeaponLaggerAntigravity.load();

            //TODO: CAMBIARLO A CADA NIVEL
            textureWeaponLaggerSpecial = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), "Levels/weapon_lagger_5.png");
            regionWeaponLaggerSpecial = TextureRegionFactory.extractFromTexture(textureWeaponLaggerSpecial);
            textureWeaponLaggerSpecial.load();
        }
        catch (IOException e)
        {
            Log.d("cargarRecursosJuego","No se pueden cargar los laggers");
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

    public VerticalGameBackground requestBackground()
    {
        VerticalGameBackground b = new VerticalGameBackground(1.0f,regionBackground,regionBackgroundEnding,this.resourcesController);

        final float x = GameControl.CAMERA_WIDTH/2;

        Sprite rS= new Sprite(x,0, regionBackgroundMiddle,resourcesController.vbom);
        Sprite eS = new Sprite(x,0, regionBackgroundTop,resourcesController.vbom);

        b.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.26f,rS));
        b.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.4f,eS));

        return b;
    }

    public Meteor requestMeteor()
    {
        return new Meteor(0,0, regionMeteor, resourcesController.vbom);
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
