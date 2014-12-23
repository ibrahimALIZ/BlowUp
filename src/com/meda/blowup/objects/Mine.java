package com.meda.blowup.objects;

import java.util.List;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;

import com.meda.blowup.util.Constants;

public class Mine extends CCSprite {
	private Drone drone;
	private int positionIndex;

	public Mine() {
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("mine2.png"));
		setScale(Constants.SCALE_FACTOR);
	}

	public void follow(Drone drone, CGPoint p) {
		this.drone = drone;

		List<CGPoint> pList = drone.getCoordinates();
		positionIndex = pList.lastIndexOf(p) + 1;

		move(null);
	}

	public void move(Object sender) {
		List<CGPoint> pList = drone.getCoordinates();
		if (positionIndex >= pList.size()) {
			this.stopAllActions();
			this.removeFromParentAndCleanup(true);
		} else {
			CGPoint p1 = pList.get(positionIndex - 1);
			CGPoint p2 = pList.get(positionIndex++);

			CCMoveTo move = CCMoveTo.action(CGPoint.ccpDistance(p1, p2)
					/ Constants.MINE_VELOCITY, p2);

			CCCallFuncN moveAgain = CCCallFuncN.action(this, "move");
			moveAgain.setTag(345);
			CCSequence actions = CCSequence.actions(move, moveAgain);
			runAction(actions);
		}
	}
}
