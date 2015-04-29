package com.example.adrian.meteorlag.GameAndScenes;

import android.opengl.GLES20;

import com.example.adrian.meteorlag.GameAndScenes.Laggers.AntigravityLagger;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.LagBar;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.Lagger;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.MissileLagger;
import com.example.adrian.meteorlag.GameAndScenes.Laggers.SuperMissileLagger;

import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;

import java.util.ArrayList;

/**
 * Created by Adrian on 3/10/15.
 */
public class Level1Earth extends Level
{
    public Level1Earth(MainGameScene scene,ResourcesController adm) {
        super(scene,adm);
    }

    @Override
    public String requestLevelID() {
        return "1";
    }

    @Override
    public float requestInitialAcceleration() {
        return 200.0f;
    }

    @Override
    public float requestInitialVelocity() {
        return 80.0f;
    }

    @Override
    public float requestInitialHeight() {
        return (GameControl.CAMERA_HEIGHT*4) * 15;
    }

    @Override
    public float requestMeteorMoveResistance() {
        return 10.0f;
    }

    @Override
    public float requestMinLevelTime() {
        return 70.0f;
    }

    @Override
    public String requestLevelTitle() {
        return "Tierra";
    }

    @Override
    public String requestLevelYear() {
        return "2015";
    }

    @Override
    public ParticleSystem<Sprite> requestTrailParticleSystem() {

        IEntityFactory<Sprite> ief = new IEntityFactory<Sprite>() {
            @Override
            public Sprite create(float pX, float pY)
            {
                return new Sprite(pX,pY,Level1Earth.this.regionMeteorTrail,resourcesController.vbom);
            }
        };

        CircleParticleEmitter trailEmmiter = new CircleParticleEmitter(0.0f,0.0f,super.regionMeteor.getWidth()/2 + 5.0f);

        ParticleSystem<Sprite> trailParticleSystem = new ParticleSystem<Sprite>(ief,trailEmmiter,60,90,180);
        trailParticleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(
                GLES20.GL_SRC_ALPHA,GLES20.GL_ONE));
        float tiempoVida = 2.0f;   // Segundos de vida de cada partícula
        trailParticleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(tiempoVida));
        trailParticleSystem.addParticleInitializer(new ScaleParticleInitializer<Sprite>(2.0f,5.5f));
        trailParticleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(-100, 100));
        trailParticleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f,1.5f,0.45f,0.0f));

        trailParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f,0.05f,1.0f,5.0f));
        trailParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.05f,1.0f,5.0f,0.5f));

        return trailParticleSystem;
    }

    @Override
    public LagBar requestLagBar() {

        ArrayList<Lagger> laggers = new ArrayList<>(5);
        laggers.add(new MissileLagger(this.scene));
        laggers.add(new SuperMissileLagger(this.scene));
        laggers.add(new AntigravityLagger(this.scene));
        laggers.add(new AntigravityLagger(this.scene));
        laggers.add(new AntigravityLagger(this.scene));
        return new LagBar(0,0,laggers, resourcesController);

    }




}
