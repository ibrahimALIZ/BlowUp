package com.meda.blowup.monsters;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class PurpleMonster extends Monster {
	private CCAction aliveAction, sleepyAction, slowAction, happyAction,
			unhappyAction, runningAction, fastAction, chargingAction,
			crazyAction, goneAction;

	public PurpleMonster() {
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p09.png"));
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
					.getSpriteFrame("p03.png"));
			break;
		}
	}

	private void initAnimations() {
		ArrayList<CCSpriteFrame> animList = new ArrayList<CCSpriteFrame>();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p03.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p09.png"));
		aliveAction = CCAnimate.action(1.0f,
				CCAnimation.animation("PurpleMonAliveAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p01.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p08.png"));
		sleepyAction = CCAnimate
				.action(1.0f, CCAnimation.animation("PurpleMonSleepingAnim",
						animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p02.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p01.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p01.png"));
		slowAction = CCAnimate.action(1.0f,
				CCAnimation.animation("PurpleMonSlowAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p06.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p07.png"));
		happyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("PurpleMonHappyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p09.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p10.png"));
		unhappyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("PurpleMonUnhappyAnim", animList), false);

		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p03.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p04.png"));
		runningAction = CCAnimate.action(1.0f,
				CCAnimation.animation("PurpleMonRunningAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p04.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p04.png"));
		fastAction = CCAnimate.action(1.0f,
				CCAnimation.animation("PurpleMonFastAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p06.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p05.png"));
		chargingAction = CCAnimate
				.action(1.0f, CCAnimation.animation("PurpleMonChargingAnim",
						animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p07.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p10.png"));
		crazyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("PurpleMonCrazyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p04.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("p06.png"));
		goneAction = CCAnimate.action(1.0f,
				CCAnimation.animation("PurpleMonGoneAnim", animList), false);
	}

}
