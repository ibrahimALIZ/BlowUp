package com.meda.blowup.monsters;

import java.util.Random;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import com.meda.blowup.util.Constants;

public class Monster extends CCSprite {
	protected MonsterState state;
	private float runningSpeed;
	private float sleepingSpeed;

	public Monster() {
		state = MonsterState.ALIVE;
		runningSpeed = 5.0f;
		sleepingSpeed = 1.0f;
		setScale(Constants.SCALE_FACTOR);
		
		CGSize winSize = CCDirector.sharedDirector().displaySize();		
		Random random = new Random();
		int randX = random.nextInt((int) winSize.width);
		if (randX >= winSize.width - Constants.WORLD_GAP)
			randX = (int) winSize.width - Constants.WORLD_GAP * 2;
		if (randX <= Constants.WORLD_GAP)
			randX += Constants.WORLD_GAP * 2;

		int randY = random.nextInt((int) winSize.height);
		if (randY >= winSize.height - Constants.WORLD_GAP)
			randY = (int) winSize.height - Constants.WORLD_GAP * 2;
		if (randY <= Constants.WORLD_GAP)
			randY += Constants.WORLD_GAP * 2;

		CGPoint p = CGPoint.ccp(randX, randY);
		setPosition(p);
	}

	public void die() {
		state = MonsterState.DEAD;
	}

	public boolean isDead() {
		return state == MonsterState.DEAD;
	}

	public boolean isBoss() {
		return false;
	}

	public MonsterState getState() {
		return state;
	}

	public void setState(MonsterState state) {
		this.state = state;
	}

	public float getRunningSpeed() {
		return runningSpeed;
	}

	public float getSleepingSpeed() {
		return sleepingSpeed;
	}
	
}
