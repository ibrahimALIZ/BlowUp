package com.meda.blowup.monsters;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class BlueMinion extends Minion {
	private CCAction aliveAction, sleepyAction, slowAction, happyAction,
			unhappyAction, runningAction, fastAction, chargingAction,
			crazyAction, goneAction;

	public BlueMinion() {
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b05.png"));

		initAnimations();
		scheduleUpdate();
	}

	private void initAnimations() {
		ArrayList<CCSpriteFrame> animList = new ArrayList<CCSpriteFrame>();

		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b19.png"));
		aliveAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinAliveAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b32.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b37.png"));
		sleepyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinSleepingAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b32.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b19.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b37.png"));
		slowAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinSlowAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b22.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b30.png"));
		happyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinHappyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b32.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b37.png"));
		unhappyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinUnhappyAnim", animList), false);

		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b39.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b33.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b34.png"));
		runningAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinRunningAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b19.png"));
		fastAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinFastAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b34.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b39.png"));
		chargingAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinChargingAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b33.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b34.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b39.png"));
		crazyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinCrazyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b34.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("b37.png"));
		goneAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMinGoneAnim", animList), false);
	}

	public void update(float dt) {
		if (numberOfRunningActions() > 0)
			return;

		switch (state) {
		case ALIVE:
			runAction(aliveAction);
			break;
		case SLEEPY:
			runAction(sleepyAction);
			break;
		case SLOW:
			runAction(slowAction);
			break;
		case HAPPY:
			runAction(happyAction);
			break;
		case UNHAPPY:
			runAction(unhappyAction);
			break;
		case RUNNING:
			runAction(runningAction);
			break;
		case FAST:
			runAction(fastAction);
			break;
		case CHARGING:
			runAction(chargingAction);
			break;
		case CRAZY:
			runAction(crazyAction);
			break;
		case GONE:
			runAction(goneAction);
			break;
		default:
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("b05.png"));
			break;
		}
	}
}
