package mx.itesm.meteorlag.Interface;

import mx.itesm.meteorlag.GameControl;
import mx.itesm.meteorlag.ResourcesController;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by Adrian on 3/15/15.
 */
public class VerticalGameBackground extends AutoParallaxBackground
{
    private ResourcesController resourcesController;
    private ParallaxEntity endingPE;
    private ParallaxEntity repeatingPE;


    public VerticalGameBackground(float changePerSecond)
    {
        super(0.0f,0.0f,0.0f,changePerSecond);
    }

    public VerticalGameBackground(float pParallaxChangePerSecond,ITextureRegion repeatingRegion,ITextureRegion endingRegion,ResourcesController rController)
    {
        super(0.0f, 0.0f, 0.0f, pParallaxChangePerSecond);
        this.resourcesController = rController;

        final float x = GameControl.CAMERA_WIDTH/2;

        Sprite rS= new Sprite(x,0, repeatingRegion,resourcesController.vbom);
        Sprite eS = new Sprite(x,0, endingRegion,resourcesController.vbom);

        this.repeatingPE = new ParallaxBackground.ParallaxEntity(0.15f,rS);
        this.endingPE = new ParallaxBackground.ParallaxEntity(0.15f,eS);

        this.attachParallaxEntity(repeatingPE);
    }

    public void startEndingSequence()
    {
        //this.detachParallaxEntity(repeatingPE);
        this.attachParallaxEntity(endingPE);
    }

}
