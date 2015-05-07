package com.example.adrian.meteorlag;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Adrian on 3/11/15.
 */
public class Meteor extends Sprite
{

    //public static final float METEOR_Y_SHAKE = 30.0f;

    public Meteor(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager)
    {

        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

        RotationByModifier rot = new RotationByModifier(1.0f,45);

        LoopEntityModifier l = new LoopEntityModifier(rot);

        this.registerEntityModifier(l);


    }

    @Override
    public boolean collidesWith(IEntity pOtherEntity) {

        final float radius = this.getWidth()/2 - 5.0f; //PARA HACERLO UN POCO MÁS PRECISO el -7
        final float radiusOther = pOtherEntity.getWidth()/2;

        final float distanceX = Math.abs(this.getX()-pOtherEntity.getX());
        final float distanceY = Math.abs(this.getY()-pOtherEntity.getY());

        final float distance = 1.0f/inverseSqrt( distanceX*distanceX + distanceY*distanceY );

        return distance <= (radius + radiusOther);
    }

    public static float inverseSqrt(float x) {
        float xhalf = 0.5f*x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i>>1);
        x = Float.intBitsToFloat(i);
        x = x*(1.5f - xhalf*x*x);
        return x;
    }

}
