package com.meda.blowup.monsters;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class GreenMinion extends Minion {
	private CCAction aliveAction, sleepyAction, slowAction, happyAction,
			unhappyAction, runningAction, fastAction, chargingAction,
			crazyAction, goneAction;

	public GreenMinion() {
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g14.png"));

		initAnimations();
		scheduleUpdate();
	}

	private void initAnimations() {
		ArrayList<CCSpriteFrame> animList = new ArrayList<CCSpriteFrame>();

		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g05.png"));
		aliveAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinAliveAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g04.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g17.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g04.png"));
		sleepyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinSleepingAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g16.png"));
		slowAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinSlowAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g16.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g07.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g16.png"));
		happyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinHappyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g17.png"));
		unhappyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinUnhappyAnim", animList), false);

		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g16.png"));
		runningAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinRunningAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g14.png"));
		fastAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinFastAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g05.png"));
		chargingAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinChargingAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g14.png"));
		crazyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinCrazyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g07.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("g17.png"));
		goneAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMinGoneAnim", animList), false);
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
