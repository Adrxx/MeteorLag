package com.example.adrian.meteorlag.GameAndScenes.Laggers;

import android.util.Log;

import com.example.adrian.meteorlag.GameAndScenes.MainGameScene;
import com.example.adrian.meteorlag.GameAndScenes.ResourcesController;

import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.io.IOException;

/**
 * Created by Adrian on 4/16/15.
 */
public class SuperMissileLagger extends Lagger {

    public SuperMissileLagger(MainGameScene scene) {
        super(scene);
    }

    @Override
    public void actUponScene() {
        super.actUponScene();
        this.actingScene.meteorVelocity *= -0.75f * this.actingScene.lagBar.getFill()/100;
        this.actingScene.lagBar.reduceFillBy(35.0f);
    }

    @Override
    public void loadLaggerResources() {

        try {
            // Carga la imagen de fondo de la pantalla Splash
            buttonTexture = new AssetBitmapTexture(ResourcesController.getInstance().gameControl.getTextureManager(),
                    ResourcesController.getInstance().gameControl.getAssets(), INTERFACE_FOLDER+"weapon_lagger_4.png");
            buttonTextureRegion = TextureRegionFactory.extractFromTexture(buttonTexture);
            buttonTexture.load();
        } catch (IOException e) {
            Log.d("cargarRecursos", "No se puede cargar el LAGGER");
        }

    }

    @Override
    public float requestWearOffTime() {
        return 0;
    }

    @Override
    public void wearOff() {
        super.wearOff();
    }
}
