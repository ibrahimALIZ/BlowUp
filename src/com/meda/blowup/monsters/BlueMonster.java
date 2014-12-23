package com.meda.blowup.monsters;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class BlueMonster extends Monster {
	private CCAction aliveAction, sleepyAction, slowAction, happyAction,
			unhappyAction, runningAction, fastAction, chargingAction,
			crazyAction, goneAction;

	public BlueMonster() {
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue01.png"));
		initAnimations();
		scheduleUpdate();
	}

	@Override
	public boolean isBoss() {
		return true;
	}

	public void update(float dt) {
		if (numberOfRunningActions() > 0)
			return;

		switch (getState()) {
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
					.getSpriteFrame("blue01.png"));
			break;
		}
	}

	private void initAnimations() {
		ArrayList<CCSpriteFrame> animList = new ArrayList<CCSpriteFrame>();

		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue02.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue01.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue01.png"));
		aliveAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonAliveAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue02.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue03.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue02.png"));
		sleepyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonSleepingAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue03.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue04.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue03.png"));
		slowAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonSlowAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue02.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue08.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue01.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue01.png"));
		happyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonHappyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue09.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue07.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue09.png"));
		unhappyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonUnhappyAnim", animList), false);

		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue06.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue05.png"));
		runningAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonRunningAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue01.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue05.png"));
		fastAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonFastAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue06.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue07.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue06.png"));
		chargingAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonChargingAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue04.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue06.png"));
		crazyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonCrazyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue01.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("blue05.png"));
		goneAction = CCAnimate.action(1.0f,
				CCAnimation.animation("BlueMonGoneAnim", animList), false);
	}

}
