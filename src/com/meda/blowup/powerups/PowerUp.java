package com.meda.blowup.powerups;

import java.util.Random;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;

import com.meda.blowup.GameManager;
import com.meda.blowup.util.Constants;

public class PowerUp extends CCSprite {
	private PowerUpType type;
	private int factor;

	public PowerUp() {
		Random random = new Random();
		int randomInt = random.nextInt(7);
		switch (randomInt) {
		case 0:
			factor = 2; // doubles the related feature above specified at type
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("shield.png"));
			type = PowerUpType.SHIELD;
			break;
		case 1:
			factor = 2; // doubles the related feature above specified at type
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("freeze.png"));
			type = PowerUpType.FREEZE_MONSTERS;
			break;
		case 2:
			factor = 2; // doubles the related feature above specified at type
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("slowdown1.png"));
			type = PowerUpType.SLOWDOWN_MONSTERS;
			break;
		case 3:
			factor = 2; // doubles the related feature above specified at type
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("slowdown2.png"));
			type = PowerUpType.SLOWDOWN_DRONE;
			break;
		case 4:
			factor = 2; // doubles the related feature above specified at type
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("speedup1.png"));
			type = PowerUpType.SPEEDUP_DRONE;
			break;
		case 5:
			factor = 2; // doubles the related feature above specified at type
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("speedup2.png"));
			type = PowerUpType.SPEEDUP_MONSTERS;
			break;
		case 6:
			factor = 2; // doubles the related feature above specified at type
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("drone64.png"));
			type = PowerUpType.NEW_CHANCE;
			break;

		default:
			factor = 2; // doubles the related feature above specified at type
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("shield.png"));
			type = PowerUpType.SHIELD;
			break;
		}
		setScale(Constants.SCALE_FACTOR);
		schedule("cleanUp", 5f);
	}

	public void cleanUp(float dt) {
		unschedule("cleanUp");
		this.removeFromParentAndCleanup(true);
		GameManager.getInstance().removePowerUp();
	}

	public PowerUpType getType() {
		return type;
	}

	public int getFactor() {
		return factor;
	}
}
