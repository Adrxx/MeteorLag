package mx.itesm.meteorlag.Laggers;

import android.util.Log;

import mx.itesm.meteorlag.GameControl;
import mx.itesm.meteorlag.MainGameScene;
import mx.itesm.meteorlag.ResourcesController;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.ease.EaseCubicInOut;

import java.io.IOException;

/**
 * Created by Adrian on 4/5/15.
 */
public class AntigravityLagger extends Lagger{

    public AntigravityLagger(MainGameScene scene) {
        super(scene);
    }
    private Rectangle overlay;

    private float savedEnergy = 0.0f;

    @Override
    public void actUponScene() {

        if (!this.isActive()) {
            super.actUponScene();
            this.savedEnergy = this.actingScene.lagBar.getFill();
            this.actingScene.meteorAcceleration = this.actingScene.currentLevel.propierties.getAcceleration() / 20;
            this.actingScene.meteorVelocity = this.actingScene.meteorVelocity / 4;
            this.actingScene.lagBar.reduceFillBy(Math.max(40.0f, this.actingScene.lagBar.getFill() / 2));

            this.overlay = new Rectangle(GameControl.CAMERA_WIDTH / 2, GameControl.CAMERA_HEIGHT / 2, GameControl.CAMERA_WIDTH, GameControl.CAMERA_HEIGHT, ResourcesController.getInstance().vbom);
            this.overlay.setColor(Color.CYAN);

            AlphaModifier fadeIn = new AlphaModifier(0.8f, 0.15f, 0.45f, new EaseCubicInOut());
            AlphaModifier fadeOut = new AlphaModifier(0.8f, 0.45f, 0.15f, new EaseCubicInOut());

            SequenceEntityModifier seq = new SequenceEntityModifier(fadeOut, fadeIn);
            LoopEntityModifier endless = new LoopEntityModifier(seq);

            this.actingScene.effectsLayer.attachChild(this.overlay);
            this.overlay.registerEntityModifier(endless);
        }

    }


    @Override
    public void loadLaggerResources() {
        try {
            // Carga la imagen de fondo de la pantalla Splash
            buttonTexture = new AssetBitmapTexture(ResourcesController.getInstance().gameControl.getTextureManager(),
                    ResourcesController.getInstance().gameControl.getAssets(), INTERFACE_FOLDER+ "weapon_lagger_4.png");
            buttonTextureRegion = TextureRegionFactory.extractFromTexture(buttonTexture);
            buttonTexture.load();
        } catch (IOException e) {
            Log.d("cargarRecursos", "No se puede cargar el LAGGER");
        }

    }

    @Override
    public float requestWearOffTime() {
        return Math.max(this.savedEnergy/100* 10.0f,1.5f);
    }

    @Override
    public void wearOff()
    {
        super.wearOff();
        this.actingScene.meteorAcceleration = this.actingScene.currentLevel.propierties.getAcceleration();
        this.actingScene.meteorVelocity *= 1.2f;
        this.overlay.clearEntityModifiers();
        this.overlay.detachSelf();
    }
}
