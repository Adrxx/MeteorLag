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

import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

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
    final static public String EXPLOSION_FILE = "ex.png";
    final static public String EXPLOSION_CRASH_FILE = "exc.png";
    final static public String LASER_FILE = "laser.png";

    final static public String DATA_FILE = "/data.json";
    final static public String BACKGROUND_FILE = "/bg.png";
    final static public String BACKGROUND_FILE_MIDDLE = "/bg_m.png";
    final static public String BACKGROUND_FILE_TOP = "/bg_t.png";

    final static public String BACKGROUND_ENDING_FILE = "/bg_end.png";
    final static public String METEOR_FILE = "/meteor.png";
    final static public String METEOR_TRAIL_FILE = "/trail.png";
    final static public String IN_LEVEL_LAGGER_FILE = "/lagger.png";
    final static public String IN_LEVEL_SPEEDER_FILE = "/speeder.png";
    final static public String FINAL_LAG_FILE = "/finallag.png";
    final static public String FINAL_LAG_BG_FILE = "/bg_end.png";



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

    private ITexture textureFinalLag;
    public ITextureRegion regionFinalLag;

    private ITexture textureFinalLagBG;
    public ITextureRegion regionFinalLagBG;

    private ITexture textureLaser;
    public ITextureRegion regionLaser;

    private BuildableBitmapTextureAtlas textureExplosion;
    private TiledTextureRegion regionExplosion;

    private BuildableBitmapTextureAtlas textureExplosionCrash;
    private TiledTextureRegion regionExplosionCrash;


    private String id;
    public LevelJSON propierties;



    public Level(String id,MainGameScene scene,ResourcesController adm){
        this.id = id;
        this.scene = scene;
        this.resourcesController = adm;

        final Gson gson = new Gson();
        this.propierties = gson.fromJson(loadJSONFromAsset(LEVEL_FOLDER+id+DATA_FILE), LevelJSON.class);
        //this.propierties.setHeight(  );
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
            Log.d("JSON","ERROR LOADING JSON");
            return null;
        }
        return json;

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

        //FINAL LAG
        try {
            textureFinalLag = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + FINAL_LAG_FILE);
            regionFinalLag = TextureRegionFactory.extractFromTexture(textureFinalLag);
            textureFinalLag.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + FINAL_LAG_FILE);
        }

        //FINAL LAG BG
        try {
            textureFinalLagBG = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + FINAL_LAG_BG_FILE);
            regionFinalLagBG = TextureRegionFactory.extractFromTexture(textureFinalLagBG);
            textureFinalLagBG.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + FINAL_LAG_BG_FILE);
        }

        //Final Lag Laser
        try {
            textureLaser = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), INTERFACE_FOLDER + LASER_FILE);
            regionLaser = TextureRegionFactory.extractFromTexture(textureLaser);
            textureLaser.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + METEOR_TRAIL_FILE);
        }


        //EXPLOSION ANIMADA
        // Carga las imágenes para el perro Animado
        textureExplosion = new BuildableBitmapTextureAtlas(this.resourcesController.gameControl.getTextureManager(),2048,1536);
        regionExplosion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                textureExplosion, this.resourcesController.gameControl,INTERFACE_FOLDER+EXPLOSION_FILE, 8, 6);

        try {
            textureExplosion.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del perro Animado");
        }
        textureExplosion.load();

        //EXPLOSION ANIMADA CHOQUE
        // Carga las imágenes para el perro Animado
        textureExplosionCrash = new BuildableBitmapTextureAtlas(this.resourcesController.gameControl.getTextureManager(),2000,1200);
        regionExplosionCrash = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                textureExplosionCrash, this.resourcesController.gameControl,INTERFACE_FOLDER+EXPLOSION_CRASH_FILE, 5, 3);

        try {
            textureExplosionCrash.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del perro Animado2");
        }
        textureExplosionCrash.load();

    }

    public ITextureRegion getInGameLaggerRegion()
    {
        return regionInGameLagger;
    }

    public ITextureRegion getInGameSpeederRegion()
    {
        return regionFinalLag;
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

    public AnimatedSprite getExplosion() {
        return new AnimatedSprite(GameControl.CAMERA_WIDTH/2,regionExplosion.getHeight(),
                regionExplosion,resourcesController.vbom);
    }

    public AnimatedSprite getExplosionCrash() {
        return new AnimatedSprite(GameControl.CAMERA_WIDTH/2,regionExplosionCrash.getHeight(),
                regionExplosionCrash,resourcesController.vbom);
    }

    public ParticleSystem<Sprite> getExplosionParticleSystem() {

        IEntityFactory<Sprite> ief = new IEntityFactory<Sprite>() {
            @Override
            public Sprite create(float pX, float pY)
            {
                return new Sprite(pX,pY,Level.this.regionMeteorTrail,resourcesController.vbom);
            }
        };

        PointParticleEmitter trailEmmiter = new PointParticleEmitter(0.0f,0.0f);

        ParticleSystem<Sprite> trailParticleSystem = new ParticleSystem<Sprite>(ief,trailEmmiter,190,200,800);
        float tiempoVida = 4.0f;   // Segundos de vida de cada partícula
        trailParticleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(tiempoVida));
        trailParticleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-1500.0f,1500.0f,1800.0f,1900.0f));
        trailParticleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(0.0f,-2000.0f));

        //trailParticleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(-800, 800));
       // trailParticleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f,1.5f,0.45f,0.0f));

        trailParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f,tiempoVida,2.0f,0.0f));
        //trailParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.05f,1.0f,5.0f,0.5f));

        return trailParticleSystem;
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

    public ParticleSystem<Sprite> getFinalLagLaserParticleSystem() {

        IEntityFactory<Sprite> ief = new IEntityFactory<Sprite>() {
            @Override
            public Sprite create(float pX, float pY)
            {
                return new Sprite(pX,pY,Level.this.regionLaser,resourcesController.vbom);
            }
        };

        PointParticleEmitter trailEmmiter = new PointParticleEmitter(0.0f,0.0f);


        ParticleSystem<Sprite> trailParticleSystem = new ParticleSystem<Sprite>(ief,trailEmmiter,100,100,100);
        trailParticleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(
                GLES20.GL_SRC_ALPHA,GLES20.GL_ONE));


        float tiempoVida = 0.35f;   // Segundos de vida de cada partícula
        trailParticleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0,0,0,255,255,255));

        trailParticleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(tiempoVida));
        trailParticleSystem.addParticleInitializer(new ScaleParticleInitializer<Sprite>(-10.0f,10.0f));

        trailParticleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-2.5f,2.5f,1500.0f,1500.0f));
        trailParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f,0.06f,1.5f,0.4f));



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

    public Sprite getFinalLag()
    {
        return new Sprite(0,0,regionFinalLag,resourcesController.vbom);
    }

    public Sprite getFinalLagBG()
    {
        return new Sprite(0,0,regionFinalLagBG,resourcesController.vbom);
    }



    public void unloadResources()
    {
        textureBackground.unload();
        regionBackground = null;

        textureMeteor.unload();
        regionMeteor = null;

        textureMeteorTrail.unload();
        regionMeteorTrail = null;

        textureFinalLag.unload();
        regionFinalLag = null;

        textureInGameLagger.unload();
        regionInGameLagger = null;

    }



}
