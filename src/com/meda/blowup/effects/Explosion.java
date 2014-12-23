package com.meda.blowup.effects;

import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.particlesystem.CCParticleExplosion;
import org.cocos2d.types.CGPoint;

public class Explosion extends CCParticleExplosion {
	public Explosion(int n) {
		setTexture(CCTextureCache.sharedTextureCache().addImage("fire.png"));
		totalParticles = n;
		setSpeed(20);
		//setAngleVar(30.0f);
		setSpeedVar(5.0f);
		setStartSizeVar(15.0f);
		setPosVar(CGPoint.ccp(5.0f, 5.f));
		setAutoRemoveOnFinish(true);
	}

}
