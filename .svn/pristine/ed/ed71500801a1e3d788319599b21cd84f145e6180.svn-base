package mx.itesm.meteorlag.Interface;

import android.util.Log;

import mx.itesm.meteorlag.ResourcesController;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

/**
 * Created by Adrian on 4/6/15.
 */
public class HeightIndicator extends Entity {


    private ITexture textureHeightIndicator;
    private ITextureRegion regionHeightIndicator;
    private Sprite miniMeteor;
    private Sprite ruler;
    public boolean hasReachedBottom = false;


    public HeightIndicator(float pX, float pY,ITextureRegion meteor) {

        super(pX, pY);

        loadResources();
        super.setSize(regionHeightIndicator.getWidth(),regionHeightIndicator.getHeight());

        this.ruler = new Sprite(regionHeightIndicator.getWidth()/2,regionHeightIndicator.getHeight()/2,regionHeightIndicator,ResourcesController.getInstance().vbom);
        attachChild(ruler);

        this.miniMeteor = new Sprite(ruler.getWidth()/2,ruler.getHeight(),meteor,ResourcesController.getInstance().vbom);
        miniMeteor.setScale(0.35f);

        ruler.attachChild(miniMeteor);

    }

    private void loadResources()
    {
        //INTERFACE
        try {
            textureHeightIndicator = new AssetBitmapTexture(ResourcesController.getInstance().gameControl.getTextureManager(),
                    ResourcesController.getInstance().gameControl.getAssets(), "Interface/indicator.png");
            regionHeightIndicator = TextureRegionFactory.extractFromTexture(textureHeightIndicator);
            textureHeightIndicator.load();

        }
        catch (IOException e)
        {
            Log.d("cargarRecursosIND", "No se puede cargar indicador");
        }

    }

    private void unloadResources()
    {
        textureHeightIndicator.unload();
        regionHeightIndicator = null;
    }

    public void updateHeightIndicator(float rate)
    {
        float indY = rate * regionHeightIndicator.getHeight();
        indY = Math.min(Math.max(indY,0.0f),regionHeightIndicator.getHeight());
        this.miniMeteor.setY(indY);

        if (rate < 0.2)
        {
            this.ruler.setColor(Color.RED);
            this.miniMeteor.setColor(Color.RED);
        }
        else
        {
            this.miniMeteor.setColor(Color.WHITE);
            this.ruler.setColor(Color.WHITE);

        }

        if (indY <= 0) {
            hasReachedBottom = true;
        }
    }


}
