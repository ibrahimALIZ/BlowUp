package com.meda.blowup.effects;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.particlesystem.CCParticleSmoke;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class DroneSmoke extends CCParticleSmoke {
	public DroneSmoke() {
		super();
		// adding properties to the object....
		setTexture(CCTextureCache.sharedTextureCache().addImage("smoke.png"));
		setSpeed(20);
		
		setSpeedVar(5.0f);
		setPosVar(CGPoint.ccp(5.0f, 5.f));
		setAutoRemoveOnFinish(true);

		// angle
		setAngle(90);
		setAngleVar(10);

		// emitter position
		CGSize winSize = CCDirector.sharedDirector().winSize();
		setPosition(CGPoint.ccp(winSize.width / 2, winSize.height / 2));
		posVar = CGPoint.ccp(2, 2);

		// life of particles
		life = 1;
		lifeVar = 1.5f;
		// size, in pixels
		setStartSize(5.0f);
		setStartSizeVar(10.0f);
		
		setAngle(-90);
	}
}
