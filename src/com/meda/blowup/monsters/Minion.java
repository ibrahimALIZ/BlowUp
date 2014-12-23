package com.meda.blowup.monsters;

import java.util.Random;

import org.cocos2d.nodes.CCSpriteFrameCache;

import com.meda.blowup.GameManager;
import com.meda.blowup.powerups.PowerUp;
import com.meda.blowup.util.Constants;

public class Minion extends Monster {

	public Minion() {
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("mushroom.png"));
	}

	@Override
	public void die() {
		super.die();
		Random random = new Random();
		int randomInt = random.nextInt(10);
		if (randomInt > Constants.CHANCE_FACTOR) {
			PowerUp powerUp = new PowerUp();
			powerUp.setPosition(getPosition());
			this.getParent().addChild(
					powerUp,
					100,
					Constants.POWERUP_TAG
							+ GameManager.getInstance().addPowerUp());
		}
	}

}
