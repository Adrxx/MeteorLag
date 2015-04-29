package com.example.adrian.meteorlag.GameAndScenes.Laggers;

import android.util.Log;

import com.example.adrian.meteorlag.GameAndScenes.MainGameScene;
import com.example.adrian.meteorlag.GameAndScenes.ResourcesController;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.ease.EaseCubicInOut;

/**
 * Created by Adrian on 4/5/15.
 */
public abstract class Lagger
{
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
                this.setActive(false);
                this.effectTimer = 0.0f;
                this.getButton().clearEntityModifiers();
            }
        }
    }

}
