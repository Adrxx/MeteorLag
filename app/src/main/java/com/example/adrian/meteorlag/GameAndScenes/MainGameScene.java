package com.example.adrian.meteorlag.GameAndScenes;

import com.example.adrian.meteorlag.GameAndScenes.Interface.EntityLayer;
import com.example.adrian.meteorlag.GameAndScenes.Interface.HeightIndicator;
import com.example.adrian.meteorlag.GameAndScenes.Interface.TextTimer;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.LagBar;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.Lagger;
import com.example.adrian.meteorlag.Meteor;
import com.example.adrian.meteorlag.GameAndScenes.Interface.VerticalGameBackground;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.ArrayList;

/**
 * Created by Adrian on 2/12/15.
 */
public class MainGameScene extends BaseScene implements GameMechanics, IAccelerationListener
{
    private GameStatus gameStatus;
    public Level currentLevel;

    //ALL METEOR PROPERTIES USED IN THE REAL-TIME SIMULATION
    public float meteorVelocity;
    public float meteorAcceleration;
    public float meteorHeight;
    public float meteorResistance;
    public float timeEleapsed;


    //LEVEL LAYERS
    public EntityLayer interfaceLayer;
    public EntityLayer infoLayer;
    public EntityLayer gameLayer;
    public EntityLayer finalLagLayer;
    public EntityLayer effectsLayer;

    // LEVEL RESOURCES
    private Text levelTitle;
    private Text levelSubtitle;
    private Text tapToStartText;

    private ITextureRegion inGameLaggerRegion;
    private ITextureRegion inGameSpeederRegion;

    private ButtonSprite pauseButton;
    private AnimatedSprite explosion;
    private AnimatedSprite explosionCrash;
    private Meteor meteor;
    private HeightIndicator heightIndicator;
    private TextTimer textTimer;


    private Sprite finalLagBackground;
    private Sprite finalLag;

    private ParticleSystem<Sprite> trailParticleSystem;
    private CircleParticleEmitter trailEmmiter;

    private ParticleSystem<Sprite> finalLagLaserParticleSystem;
    private PointParticleEmitter finalLagLaserEmitter;


    private VelocityParticleInitializer trailVelocityParticleInitializer;

    public LagBar lagBar;
    private VerticalGameBackground verticalGameBackground;

    // GAME MECHANICS INTERNAL PROPERTIES
    private boolean canWin = false;

    private float inGameLaggerTimer = 0.0f;
    private float inGameSpeederTimer = 0.0f;

    private ArrayList<Sprite> inGameLaggers = new ArrayList<>();
    private ArrayList<Sprite> inGameSpeeders = new ArrayList<>();


    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        switch (gameStatus)
        {
            case GAME_STARTING:
                this.verticalGameBackground.setParallaxChangePerSecond(this.meteorVelocity);
                break;

            case GAME_ENDING:
                this.timeEleapsed -= pSecondsElapsed;
                this.canWin = (timeEleapsed > 0);

                this.textTimer.updateTimeInSecs(this.timeEleapsed);
                this.meteorHeight = this.meteorHeight - this.meteorVelocity * pSecondsElapsed;
                break;
            case GAME_PLAYING:
                this.timeEleapsed -= pSecondsElapsed;
                this.textTimer.updateTimeInSecs(this.timeEleapsed);

                this.meteorVelocity = Math.min(this.meteorVelocity + this.meteorAcceleration * pSecondsElapsed,2000.0f);
                this.meteorHeight = this.meteorHeight - this.meteorVelocity * pSecondsElapsed;

                if (this.meteorHeight <= 0)
                {
                    this.endGameplay();
                }

                this.verticalGameBackground.setParallaxChangePerSecond(this.meteorVelocity);
                this.trailVelocityParticleInitializer.setVelocityY(this.meteorVelocity/3);

                this.heightIndicator.updateHeightIndicator(this.meteorHeight/this.currentLevel.propierties.getHeight());

                this.lagBar.incrementFillBy(0.05f);
                this.lagBar.updateTimesToLaggers(pSecondsElapsed); //TO WEAR OFF LAGGERS AS THEY END.

                /*
                for (Stop s: this.currentLevel.propierties.getStops())
                {
                    if (this.meteorHeight <= s.getAt())
                    {
                        Log.d("TEST", s.getText());
                    }
                }
                */

/*
                //INGAME LAGGER
                inGameLaggerTimer += pSecondsElapsed;
                if ( inGameLaggerTimer > 0.3 +(10.0f * Math.random()) && this.meteorVelocity > this.currentLevel.propierties.getVelocity())
                {
                    spawnInGameLagger();
                    inGameLaggerTimer = 0.0f;
                }

                for(int i=inGameLaggers.size()-1; i>=0; i--) {
                    Sprite sp = inGameLaggers.get(i);

                    sp.setY(sp.getY() + this.meteorVelocity *pSecondsElapsed);

                    if (meteor.collidesWith(sp) ) {
                        this.meteorVelocity *= 0.75f;
                        this.lagBar.incrementFillBy(1.0f);

                        sp.detachSelf();
                        inGameLaggers.remove(sp);
                    }
                }

                //INGAME SPEEDER
                inGameSpeederTimer += pSecondsElapsed;
                if ( inGameSpeederTimer > 0.2 +(7.0f * Math.random()) && this.meteorVelocity > this.currentLevel.propierties.getHeight())
                {
                    spawnInGameSpeeder();
                    inGameSpeederTimer = 0.0f;
                }
                for(int i=inGameSpeeders.size()-1; i>=0; i--)
                {
                    Sprite sp = inGameSpeeders.get(i);

                    sp.setY(sp.getY() + this.meteorVelocity *pSecondsElapsed);

                    if (meteor.collidesWith(sp) ) {   //
                        this.meteorVelocity += 100.0f;
                        this.lagBar.reduceFillBy(0.01f);

                        sp.detachSelf();
                        inGameSpeeders.remove(sp);
                    }
                }

*/

                break;
            case GAME_PAUSED:
                break;
            case GAME_OVER:
                break;
            case GAME_WON:
                break;
            default:
                break;
        }


    }

    @Override
    public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {

    }

    @Override
    public void onAccelerationChanged(AccelerationData pAccelerationData) {

        float newX = meteor.getX() + ((pAccelerationData.getX()/10* GameControl.CAMERA_WIDTH /2)/this.meteorResistance);

        if (newX < (meteor.getWidth()/2) ) { newX = (meteor.getWidth()/2); }
        if (newX > GameControl.CAMERA_WIDTH - (meteor.getWidth()/2) ) { newX = GameControl.CAMERA_WIDTH - (meteor.getWidth()/2); }

        this.trailEmmiter.setCenterX(newX);

        this.meteor.setX(newX);
    }


    private void spawnInGameLagger()
    {
        Sprite l = new Sprite(GameControl.CAMERA_WIDTH * (float)Math.random() , 0 ,this.inGameLaggerRegion, resourcesController.vbom);
        this.inGameLaggers.add(l);
        this.gameLayer.attachChild(l);
    }

    private void spawnInGameSpeeder()
    {
        Sprite l = new Sprite(GameControl.CAMERA_WIDTH * (float)Math.random() , 0 ,this.inGameSpeederRegion, resourcesController.vbom);
        this.inGameSpeeders.add(l);
        this.gameLayer.attachChild(l);
    }


    //GAME MECHANICS INTERFACE


    @Override
    public void startGameplay()
    {
        this.interfaceLayer = setupInterfaceLayer();
        attachChild(this.interfaceLayer);

        MainGameScene.this.resourcesController.engine.enableAccelerationSensor(MainGameScene.this.resourcesController.gameControl,MainGameScene.this); //HABILITA EL ACELEROMETRO
        gameStatus = GameStatus.GAME_PLAYING;
    }

    @Override
    public void pauseGameplay() {

        resourcesController.engine.enableAccelerationSensor(resourcesController.gameControl,this); //HABILITA EL ACELEROMETRO

    }

    @Override
    public void endGameplay()
    {
        this.gameStatus = GameStatus.GAME_ENDING;

        resourcesController.engine.disableAccelerationSensor(resourcesController.gameControl);

        MoveModifier centerMeteor = new MoveModifier(0.5f,this.meteor.getX(),this.meteor.getY(),GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT - this.meteor.getHeight() - 30);
        this.meteor.registerEntityModifier(centerMeteor);

        this.interfaceLayer.detachSelf();


        MoveYModifier my = new MoveYModifier(0.5f,this.finalLag.getY(),this.finalLag.getHeight()/2);
        DelayModifier d = new DelayModifier(2.5f);

        SequenceEntityModifier seq = new SequenceEntityModifier(d,my);

        this.finalLag.registerEntityModifier(seq);


        MoveYModifier myFinal = new MoveYModifier(3.0f,this.finalLagBackground.getY(),this.finalLagBackground.getHeight()/2)  {
            @Override
            protected void onModifierFinished(IEntity pItem) {
                super.onModifierFinished(pItem);

                gameOver();

                if (MainGameScene.this.canWin)
                {
                    //winGame();
                }
                else
                {
                    //winGame();
                }


            }
        };

        this.finalLagBackground.registerEntityModifier(myFinal);


    }

    @Override
    public void winGame()
    {
        this.gameStatus = GameStatus.GAME_WON;
        this.meteorVelocity = 0.0f;
        this.meteorAcceleration = 0.0f;
        this.verticalGameBackground.setParallaxChangePerSecond(this.meteorVelocity);
        this.trailParticleSystem.setParticlesSpawnEnabled(false);
        this.finalLagLaserParticleSystem.setParticlesSpawnEnabled(true);

        this.explosion.setPosition(this.meteor.getX()-10, this.meteor.getY());
        this.gameLayer.attachChild(this.explosion);

        this.resourcesController.engine.registerUpdateHandler(new TimerHandler(2.0f,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        MainGameScene.this.resourcesController.engine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer
                        MainGameScene.this.explosion.animate(24, false);
                        MainGameScene.this.meteor.detachSelf();
                        MainGameScene.this.finalLagLaserParticleSystem.setParticlesSpawnEnabled(false);


                    }
                }));

    }


    @Override
    public void gameOver()
    {
        this.gameStatus = GameStatus.GAME_OVER;
        this.meteorVelocity = 0.0f;
        this.meteorAcceleration = 0.0f;
        this.verticalGameBackground.setParallaxChangePerSecond(this.meteorVelocity);

        this.explosionCrash.setPosition(GameControl.CAMERA_WIDTH/2, this.explosionCrash.getHeight()/2);
        this.gameLayer.attachChild(this.explosionCrash);



        this.explosionCrash.animate(60,true);


    }

    //ESCENA BASE ABSTRACT METHODS _______
    @Override
    public void createScene()
    {

        Level l = new Level("Tierra",this,this.resourcesController);

        loadLevel(l);

        //SETTING UP LAYERS
        this.infoLayer = setupInfoLayer();
        attachChild(this.infoLayer);

        this.finalLagLayer = setupFinalLagLayer();
        attachChild(this.finalLagLayer);

        this.gameLayer = setupGameLayer();
        attachChild(this.gameLayer);

        this.effectsLayer = new EntityLayer(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,GameControl.CAMERA_WIDTH,GameControl.CAMERA_HEIGHT);
        attachChild(this.effectsLayer);

        this.gameStatus = GameStatus.GAME_STARTING;
    }

    private EntityLayer setupFinalLagLayer() {

        EntityLayer layer = new EntityLayer(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,GameControl.CAMERA_WIDTH,GameControl.CAMERA_HEIGHT);

        this.finalLagBackground.setPosition(GameControl.CAMERA_WIDTH / 2, -this.finalLagBackground.getHeight() / 2);
        layer.attachChild(this.finalLagBackground);

        this.finalLagLaserEmitter.setCenter(GameControl.CAMERA_WIDTH / 2, this.finalLag.getHeight()-15);
        layer.attachChild(this.finalLagLaserParticleSystem);

        this.finalLag.setPosition(GameControl.CAMERA_WIDTH / 2, -this.finalLag.getHeight() / 2);
        layer.attachChild(this.finalLag);

        return layer;
    }

    private EntityLayer setupInterfaceLayer() {

        EntityLayer layer = new EntityLayer(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,GameControl.CAMERA_WIDTH,GameControl.CAMERA_HEIGHT);

        this.lagBar.setPosition(GameControl.CAMERA_WIDTH/2, this.lagBar.getHeight()/2);

        for (Lagger l : this.lagBar.laggers) {
            registerTouchArea(l.getButton());
        }

        layer.attachChild(this.lagBar);

        this.textTimer = new TextTimer(60,GameControl.CAMERA_HEIGHT - 60,200,200);
        this.textTimer.updateTimeInSecs(this.currentLevel.propierties.getTime());
        layer.attachChild(this.textTimer);

        this.heightIndicator =new HeightIndicator(0,0,this.meteor.getTextureRegion());
        this.heightIndicator.setPosition(GameControl.CAMERA_WIDTH-this.heightIndicator.getWidth()/2 -10.0f,GameControl.CAMERA_HEIGHT/2);

        layer.attachChild(this.heightIndicator);
        return layer;
    }

    private EntityLayer setupGameLayer() {

        EntityLayer layer = new EntityLayer(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,GameControl.CAMERA_WIDTH,GameControl.CAMERA_HEIGHT);
        //METEOR
        this.meteor.setPosition(GameControl.CAMERA_WIDTH /2, GameControl.CAMERA_HEIGHT - this.meteor.getHeight() - 30);
        layer.attachChild(this.meteor);

        this.trailVelocityParticleInitializer = new VelocityParticleInitializer(-20,20, this.meteorVelocity, this.meteorVelocity);
        this.trailParticleSystem.addParticleInitializer(this.trailVelocityParticleInitializer);


        // Se agrega a la escena, como cualquier Sprite
        layer.attachChild(trailParticleSystem);
        this.trailEmmiter.setCenterY(this.meteor.getY());
        this.trailEmmiter.setCenterX(meteor.getX()); //PARA QUE INICIE EN EL METEORO

        return layer;

    }

    private EntityLayer setupInfoLayer() {

        final EntityLayer layer = new EntityLayer(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,GameControl.CAMERA_WIDTH,GameControl.CAMERA_HEIGHT);

        AlphaModifier fadeIn = new AlphaModifier(0.4f,0.0f,1.0f);
        AlphaModifier fadeOut = new AlphaModifier(0.4f,1.0f,0.0f);

        SequenceEntityModifier seq = new SequenceEntityModifier(fadeIn,fadeOut);
        LoopEntityModifier endless = new LoopEntityModifier(seq);

        layer.attachChild(levelTitle);
        layer.attachChild(levelSubtitle);
        layer.attachChild(tapToStartText);

        tapToStartText.registerEntityModifier(endless);

        return layer;
    }

    @Override
    public void loadLevel(Level level) {
        this.currentLevel = level;
        //Carga las propiedades del nivel actual SOLO LAS CARGA NO DEBE PONERLAS EN ESCENA AÚN

        this.meteorAcceleration = level.propierties.getAcceleration();
        this.meteorVelocity = level.propierties.getVelocity();
        this.meteorResistance = level.propierties.getResistance();
        this.meteorHeight = level.propierties.getHeight();
        this.timeEleapsed = level.propierties.getTime();

        this.levelTitle = resourcesController.generateText(level.propierties.getTitle(),GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2 + 50,90,0xFFFFFFFF);
        this.levelSubtitle = resourcesController.generateText(level.propierties.getSubtitle(),GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2 - 10,50,0xFFFFFFFF);
        this.tapToStartText = resourcesController.generateText("Toque para iniciar",GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2 - 60,32,0xFFFFFFFF);

        this.verticalGameBackground = level.getParallaxBackground();
        setBackground(this.verticalGameBackground);
        setBackgroundEnabled(true);

        this.meteor = level.getMeteor();
        this.lagBar = level.getLagBar();
        this.finalLagBackground = level.getFinalLagBG();
        this.finalLag = level.getFinalLag();
        this.explosion = level.getExplosion();
        this.explosionCrash = level.getExplosionCrash();


        this.trailParticleSystem = level.getTrailParticleSystem();
        this.trailEmmiter = (CircleParticleEmitter) this.trailParticleSystem.getParticleEmitter();

        this.finalLagLaserParticleSystem = level.getFinalLagLaserParticleSystem();
        this.finalLagLaserParticleSystem.setParticlesSpawnEnabled(false);
        this.finalLagLaserEmitter = (PointParticleEmitter) this.finalLagLaserParticleSystem.getParticleEmitter();

        this.inGameLaggerRegion = level.getInGameLaggerRegion();
        this.inGameSpeederRegion = level.getInGameSpeederRegion();
    }


    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {

        if(pSceneTouchEvent.isActionDown()) {
            switch (gameStatus) {
                case GAME_STARTING:
                    this.gameStatus = GameStatus.TRANSITION_STATUS;
                    //Log.d("marti","FUCKAAA");
                    AlphaModifier fadeOut = new AlphaModifier(0.5f, 1.0f, 0.0f) {
                        @Override
                        protected void onModifierFinished(IEntity pItem) {
                            super.onModifierFinished(pItem);
                            infoLayer.detachSelf();
                            startGameplay();
                        }
                    };
                    this.infoLayer.registerEntityModifier(fadeOut);
                    break;
                case GAME_ENDING:
                    break;
                default:

                    break;
            }
        }

        return super.onSceneTouchEvent(pSceneTouchEvent);
    }

    @Override
    public void onBackKeyPressed() {
        // Regresar al MENU principal
        resourcesController.engine.disableAccelerationSensor(resourcesController.gameControl);
        scenesController.crearEscenaMenu();
        scenesController.setEscena(SceneType.MENU_SCENE);
        scenesController.liberarEscenaJuego();
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.GAME_SCENE;
    }

    @Override
    public void unloadScene() {
        // Liberar cada recurso usado en esta escena
        //this.currentLevel.unloadResources(); //TODO CHECA QUE TODO SE LIBERE!
        this.detachSelf();     // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }

}