package com.meda.blowup.monsters;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteFrameCache;

public class GreenMonster extends Monster {
	private CCAction aliveAction, sleepyAction, slowAction, happyAction,
			unhappyAction, runningAction, fastAction, chargingAction,
			crazyAction, goneAction;

	public GreenMonster() {
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr08.png"));
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
					.getSpriteFrame("gr08.png"));
			break;
		}
	}

	private void initAnimations() {
		ArrayList<CCSpriteFrame> animList = new ArrayList<CCSpriteFrame>();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr01.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr10.png"));
		aliveAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonAliveAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr02.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr03.png"));
		sleepyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonSleepingAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr01.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr02.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr01.png"));
		slowAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonSlowAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr10.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr08.png"));
		happyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonHappyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr03.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr06.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr03.png"));
		unhappyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonUnhappyAnim", animList), false);

		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr04.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr08.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr09.png"));
		runningAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonRunningAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr08.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr06.png"));
		fastAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonFastAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr07.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr08.png"));
		chargingAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonChargingAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr05.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr09.png"));
		crazyAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonCrazyAnim", animList), false);

		animList.clear();
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr10.png"));
		animList.add(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gr04.png"));
		goneAction = CCAnimate.action(1.0f,
				CCAnimation.animation("GreenMonGoneAnim", animList), false);
	}

}
