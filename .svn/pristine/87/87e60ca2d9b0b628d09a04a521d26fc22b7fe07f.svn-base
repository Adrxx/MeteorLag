package mx.itesm.meteorlag.Laggers;

import android.util.Log;

import mx.itesm.meteorlag.GameControl;
import mx.itesm.meteorlag.MainGameScene;
import mx.itesm.meteorlag.ResourcesController;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

/**
 * Created by Adrian on 4/16/15.
 */
public class SuperMissileLagger extends Lagger {

    private Rectangle overlay;

    public SuperMissileLagger(MainGameScene scene) {
        super(scene);
    }

    @Override
    public void actUponScene() {
        super.actUponScene();
        this.actingScene.meteorVelocity = Math.min(-0.75f * this.actingScene.meteorVelocity, -280.0f);
        this.actingScene.lagBar.reduceFillBy(75.0f);

        this.overlay = new Rectangle(GameControl.CAMERA_WIDTH/2,GameControl.CAMERA_HEIGHT/2,GameControl.CAMERA_WIDTH,GameControl.CAMERA_HEIGHT, ResourcesController.getInstance().vbom);
        this.overlay.setColor(Color.WHITE);

        AlphaModifier fadeOut = new AlphaModifier(0.2f,0.55f,0.0f );

        this.actingScene.effectsLayer.attachChild(this.overlay);
        this.overlay.registerEntityModifier(fadeOut);

    }

    @Override
    public void loadLaggerResources() {

        try {
            // Carga la imagen de fondo de la pantalla Splash
            buttonTexture = new AssetBitmapTexture(ResourcesController.getInstance().gameControl.getTextureManager(),
                    ResourcesController.getInstance().gameControl.getAssets(), INTERFACE_FOLDER+"weapon_lagger_2.png");
            buttonTextureRegion = TextureRegionFactory.extractFromTexture(buttonTexture);
            buttonTexture.load();
        } catch (IOException e) {
            Log.d("cargarRecursos", "No se puede cargar el LAGGER");
        }

    }

    @Override
    public float requestWearOffTime() {
        return 0.2f;
    }

    @Override
    public void wearOff() {
        super.wearOff();
        this.overlay.clearEntityModifiers();
        this.overlay.detachSelf();
    }
}
