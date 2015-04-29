package com.example.adrian.meteorlag.GameAndScenes.Interface;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;

/**
 * Created by Adrian on 4/3/15.
 */
public class EntityLayer extends Entity {

    public EntityLayer(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight);
    }

    @Override
    public void setAlpha(final float pAlpha) {
        super.setAlpha(pAlpha);
        for (IEntity mChild : mChildren) {
            mChild.setAlpha(mChild.getAlpha());
        }

    }

}
