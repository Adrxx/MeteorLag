package mx.itesm.meteorlag;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLES20;
import android.util.Log;

import mx.itesm.meteorlag.Interface.VerticalGameBackground;
import mx.itesm.meteorlag.Laggers.AntigravityLagger;
import mx.itesm.meteorlag.Laggers.LagBar;
import mx.itesm.meteorlag.Laggers.Lagger;
import mx.itesm.meteorlag.Laggers.MissileLagger;
import mx.itesm.meteorlag.Laggers.PortalLagger;
import mx.itesm.meteorlag.Laggers.SuperMissileLagger;

import com.google.gson.Gson;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
    public Sound efectoExplosion;


    public Level(String id,MainGameScene scene,ResourcesController adm){
        this.id = id;
        this.scene = scene;
        this.resourcesController = adm;

        final Gson gson = new Gson();
        this.propierties = gson.fromJson(loadJSONFromAsset(LEVEL_FOLDER+id+DATA_FILE), LevelJSON.class);
        loadResources();
    }

    static public String loadJSONFromAsset(String asset) {
        String json = null;
        try {

            InputStream is = ResourcesController.getInstance().gameControl.getAssets().open(asset);

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


        // Efecto de sonido
        try {
            efectoExplosion = SoundFactory.createSoundFromAsset(resourcesController.engine.getSoundManager(),
                    resourcesController.gameControl, "sonidos/exp.mp3");
        } catch (final IOException e) {
            Log.i("cargarSonidos", "No se puede cargar efecto de sonido");
        }

        // *** Ver onManagedUpdate, onScene
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
            textureBackgroundMiddle = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + BACKGROUND_FILE_MIDDLE);
            regionBackgroundMiddle = TextureRegionFactory.extractFromTexture(textureBackgroundMiddle);
            textureBackgroundMiddle.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + BACKGROUND_FILE_MIDDLE);
        }

        //BACKGROUND TOP
        try {
            textureBackgroundTop = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
                    this.resourcesController.gameControl.getAssets(), LEVEL_FOLDER + this.id + BACKGROUND_FILE_TOP);
            regionBackgroundTop = TextureRegionFactory.extractFromTexture(textureBackgroundTop);
            textureBackgroundTop.load();
        } catch (IOException e) {
            Log.d("NIVEL ID: " + this.id , "No se pueden cargar la imagen" + BACKGROUND_FILE_TOP);
        }

        //BACKGROUND ENDING
        try {
            textureBackgroundEnding = new AssetBitmapTexture(this.resourcesController.gameControl.getTextureManager(),
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
        textureExplosionCrash = new BuildableBitmapTextureAtlas(this.resourcesController.gameControl.getTextureManager(),4080,411);
        regionExplosionCrash = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                textureExplosionCrash, this.resourcesController.gameControl,INTERFACE_FOLDER+EXPLOSION_CRASH_FILE, 20, 1);

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
        trailParticleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(
                GLES20.GL_SRC_ALPHA,GLES20.GL_ONE));
        float tiempoVida = 3.0f;   // Segundos de vida de cada partícula
        trailParticleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(tiempoVida));

        trailParticleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-2500.0f,2500.0f,1600.0f,1900.0f));
        trailParticleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(0.0f,-2400.0f));

        //trailParticleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(-800, 800));
       // trailParticleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f,1.5f,0.45f,0.0f));

        trailParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f,tiempoVida/2,2.0f,0.0f));
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

        ParticleSystem<Sprite> trailParticleSystem = new ParticleSystem<Sprite>(ief,trailEmmiter,60,90,280);
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
        laggers.add(new PortalLagger(this.scene));
        laggers.add(new AntigravityLagger(this.scene));
        laggers.add(new SuperMissileLagger(this.scene));

        //laggers.add(new AntigravityLagger(this.scene));
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

    public boolean saveRecord(float secs)
    {
        secs = Math.abs(secs) + this.propierties.getTime() ;

        SharedPreferences prefs = resourcesController.gameControl.getSharedPreferences("hs", Context.MODE_PRIVATE);
        float score = prefs.getFloat(this.propierties.getTitle(), this.propierties.getTime()); //0 is the default value

        if (secs > score)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat(this.propierties.getTitle(),secs);
            editor.apply();

            return true;

        }

        return false;


    }



    public void unloadResources()
    {
        textureBackground.unload();
        regionBackground = null;

        textureBackgroundMiddle.unload();
        regionBackgroundMiddle = null;

        textureBackgroundTop.unload();
       regionBackgroundTop = null;

        textureBackgroundEnding.unload();
       regionBackgroundEnding = null;

   textureMeteor.unload();
       regionMeteor = null;

        textureMeteorTrail.unload();
 regionMeteorTrail = null;

  textureInGameLagger.unload();
         regionInGameLagger = null;

 textureInGameSpeeder.unload();
regionInGameSpeeder = null;

        textureFinalLag.unload();
         regionFinalLag = null;

      textureFinalLagBG.unload();
     regionFinalLagBG = null;

         textureLaser.unload();
         regionLaser= null;

  textureExplosion.unload();
      regionExplosion = null;

         textureExplosionCrash.unload();
         regionExplosionCrash = null;


    }



}
