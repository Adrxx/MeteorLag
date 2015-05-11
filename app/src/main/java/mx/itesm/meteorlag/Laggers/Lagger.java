package mx.itesm.meteorlag.Laggers;

import android.util.Log;

import mx.itesm.meteorlag.MainGameScene;
import mx.itesm.meteorlag.ResourcesController;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.io.IOException;

/**
 * Created by Adrian on 4/5/15.
 */
public abstract class Lagger
{
    private Sound efectoSonido;

    public class ClickableSprite extends Sprite
    {
        private boolean enabled = false;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
            super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        }

    }

    final static public String INTERFACE_FOLDER = "Interface/";

    public ITexture buttonTexture;
    public ITextureRegion buttonTextureRegion;

    protected MainGameScene actingScene;

    private ClickableSprite button;

    public abstract void loadLaggerResources();
    public abstract float requestWearOffTime();

    private float effectTimer = 0.0f;

    public void actUponScene()
    {
        this.effectTimer = 0.0f;
        this.setActive(true);

        efectoSonido.play();


        /*
        this.getButton().setColor(Color.RED);

        ScaleModifier fadeIn = new ScaleModifier(0.7f,1.0f,0.6f, new EaseCubicInOut() );
        ScaleModifier fadeOut = new ScaleModifier(0.7f,0.6f,1.0f, new EaseCubicInOut() );

        SequenceEntityModifier seq = new SequenceEntityModifier(fadeOut,fadeIn);
        LoopEntityModifier endless = new LoopEntityModifier(seq);

        this.getButton().registerEntityModifier(endless);
        */

    }
    public void wearOff()
    {
        this.setActive(false);
        this.effectTimer = 0.0f;

        /*this.getButton().setColor(Color.WHITE);
        this.getButton().clearEntityModifiers();
        */
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean active = false;

    public Lagger(MainGameScene scene)
    {
        this.actingScene = scene;
        this.loadLaggerResources();

        // Efecto de sonido
        try {
            efectoSonido = SoundFactory.createSoundFromAsset(ResourcesController.getInstance().engine.getSoundManager(),
                    ResourcesController.getInstance().gameControl, "sonidos/lag.mp3");
        } catch (final IOException e) {
            Log.i("cargarSonidos", "No se puede cargar efecto de sonido");
        }


        this.button = new ClickableSprite(0,0,buttonTextureRegion, ResourcesController.getInstance().vbom)
        {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp() && isEnabled()) {
                    Lagger.this.actUponScene();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
    }

    public ClickableSprite getButton() {
        return this.button;
    }

    public void updateTime(float pSecondsElapsed)
    {
        if (this.isActive())
        {
            this.effectTimer += pSecondsElapsed;

            if (this.effectTimer >= this.requestWearOffTime())
            {
                this.wearOff();
                this.getButton().clearEntityModifiers();
            }
        }
    }

}
