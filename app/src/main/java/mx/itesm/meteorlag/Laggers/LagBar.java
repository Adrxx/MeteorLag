package mx.itesm.meteorlag.Laggers;

import mx.itesm.meteorlag.GameControl;
import mx.itesm.meteorlag.ResourcesController;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.adt.color.Color;

import java.util.ArrayList;

/**
 * Created by Adrian on 3/10/15.
 */
public class LagBar extends Entity {

    private static final float LAG_BAR_WIDTH = mx.itesm.meteorlag.GameControl.CAMERA_WIDTH;
    private static final float LAG_BAR_HEIGHT = 120.0f;
    private static final float INDICATOR_BAR_HEIGHT = 20.0f;

    private float fill = 0.0f;
    private Rectangle bar;

    public ArrayList<Lagger> laggers = new ArrayList<>();
   // public ArrayList<Lagger.ClickableSprite> touchableButtons;

    public float getFill() {
        return fill;
    }

    public void setFill(float fill) {

        fill = Math.max(Math.min(fill, 100.0f), 0.0f); // VALIDATION
        this.fill = fill;

        this.bar.setWidth(LAG_BAR_WIDTH / 100 * fill);
        this.bar.setX(this.bar.getWidth() / 2);

        int affectedLaggers = Math.round( fill / (100/this.laggers.size()) );

        for (int i = this.laggers.size()-1; i >= 0; i--)
        {
            Lagger.ClickableSprite sp = this.laggers.get(i).getButton();

            if (i < affectedLaggers) //LAGGERS ARE IN REVERSE
            {
                sp.setAlpha(1.0f);
                //sp.setColor(Color.TRANSPARENT);
                sp.setEnabled(true);
            }
            else
            {
                sp.setAlpha(0.2f);
                //sp.setColor(Color.WHITE);
                sp.setEnabled(false);
            }
        }
    }

    public void reduceFillBy(float by)
    {
        this.setFill(this.getFill() - by);
    }

    public void incrementFillBy(float by)
    {
        this.setFill(this.getFill() + by);
    }

    public LagBar(final float pX, final float pY,ArrayList<Lagger> laggers, final ResourcesController adm)
    {
        super(pX,pY,LAG_BAR_WIDTH,LAG_BAR_HEIGHT);


        Rectangle bg = new Rectangle(LAG_BAR_WIDTH/2,LAG_BAR_HEIGHT/2,LAG_BAR_WIDTH,LAG_BAR_HEIGHT,adm.vbom);
        bg.setColor(Color.BLACK);
        bg.setAlpha(0.2f);
        attachChild(bg);


        this.bar = new Rectangle(LAG_BAR_WIDTH/2,+INDICATOR_BAR_HEIGHT/2,LAG_BAR_WIDTH,INDICATOR_BAR_HEIGHT,adm.vbom);
        this.bar.setColor(Color.GREEN);
        attachChild(this.bar);

        this.laggers = laggers;

        float divisionWitdh = 3.0f;
        float buttonWitdh = (LAG_BAR_WIDTH - (this.laggers.size()-1) * divisionWitdh)/this.laggers.size();

        for (int i = 0; i < this.laggers.size(); i++) {

            Lagger l = this.laggers.get(i);
            Lagger.ClickableSprite s = l.getButton();
            s.setSize(buttonWitdh,LAG_BAR_HEIGHT-INDICATOR_BAR_HEIGHT);
            s.setPosition(i * (buttonWitdh + divisionWitdh) + (buttonWitdh / 2), LAG_BAR_HEIGHT - (LAG_BAR_HEIGHT - INDICATOR_BAR_HEIGHT) / 2);
            attachChild(s);

        }

        setFill(0.0f);

    }


    public void updateTimesToLaggers(float pSecondsElapsed)
    {
        for (Lagger lagger : laggers) {
            lagger.updateTime(pSecondsElapsed);
        }
    }

    public void wearAllOff()
    {
        for (Lagger lagger : laggers) {
            if (lagger.isActive())
            {
                lagger.wearOff();
            }
        }
    }
}
