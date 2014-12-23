package com.meda.blowup.layers;

import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

import com.meda.blowup.GameManager;
import com.meda.blowup.GameState;
import com.meda.blowup.util.Constants;

public class GameOverLayer extends CCLayer {
	private CCBitmapFontAtlas header, home, retry, next;

	public GameOverLayer() {
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		header = CCBitmapFontAtlas.bitmapFontAtlas("", "f2.fnt");
		header.setScale(0.1f);

		home = CCBitmapFontAtlas.bitmapFontAtlas("HOME", "f1.fnt");
		home.setColor(Constants.yellow);
		home.setPosition(CGPoint.ccp(winSize.width * 0.3f,
				winSize.height * 0.5f));
		home.setScale(Constants.SCALE_FACTOR);
		this.addChild(home);

		retry = CCBitmapFontAtlas.bitmapFontAtlas("RETRY", "f1.fnt");
		retry.setColor(Constants.yellow);
		retry.setPosition(CGPoint.ccp(winSize.width * 0.5f,
				winSize.height * 0.5f));
		retry.setScale(Constants.SCALE_FACTOR);
		this.addChild(retry);

		GameManager gameManager = GameManager.getInstance();
		GameState gameState = gameManager.getGameState();
		switch (gameState) {
		case ALL_MONSTERS_DEAD:
			header.setString("ALL MONSTERS DECEASED");
			break;
		case BOSS_MONSTER_DEAD:
			header.setString("BOSS MONSTER DECEASED");
			break;
		case PLAYER_DEAD:
			header.setString("MONSTERS GOT YOU");
			break;
		default:
			break;
		}
		header.setPosition(CGPoint.ccp(winSize.width / 2,
				winSize.height * 0.65f));
		header.setColor(ccColor3B.ccMAGENTA);
		this.addChild(header, Constants.UI_Z, Constants.GAME_OVER_TAG);

		CCBitmapFontAtlas score = CCBitmapFontAtlas.bitmapFontAtlas(
				"Total Score " + gameManager.getScore(), "f2.fnt");
		score.setColor(Constants.yellow);
		score.setPosition(CGPoint
				.ccp(winSize.width / 2, winSize.height * 0.25f));
		score.setScale(Constants.SCALE_FACTOR);
		this.addChild(score);

		if (gameState.equals(GameState.ALL_MONSTERS_DEAD)
				|| gameState.equals(GameState.BOSS_MONSTER_DEAD)) {
			next = CCBitmapFontAtlas.bitmapFontAtlas("NEXT", "f1.fnt");
			next.setColor(Constants.yellow);
			next.setPosition(CGPoint.ccp(winSize.width * 0.7f,
					winSize.height * 0.5f));
			next.setScale(Constants.SCALE_FACTOR);
			this.addChild(next);
		}

		CCScaleTo scale = CCScaleTo.action(1.f, Constants.SCALE_FACTOR * 1.5f);
		header.runAction(scale);

		gameManager.setDrawing(false);
		this.setIsTouchEnabled(true);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// Log.v("UILayer", "Touch at: " + event.getX() + ", " + event.getY());
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR * 1.5f);
			home.setColor(Constants.darkYellow);
		} else if (CGRect.containsPoint(retry.getBoundingBox(), touchLocation)) {
			retry.setScale(Constants.SCALE_FACTOR * 1.5f);
			retry.setColor(Constants.darkYellow);
		} else if (next != null
				&& CGRect.containsPoint(next.getBoundingBox(), touchLocation)) {
			next.setScale(Constants.SCALE_FACTOR * 1.5f);
			next.setColor(Constants.darkYellow);
		}
		return false;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// Log.v("UILayer", "Touch at: " + event.getX() + ", " + event.getY());
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR);
			home.setColor(Constants.yellow);
			schedule("homeTapped", 0.5f);
		} else if (CGRect.containsPoint(retry.getBoundingBox(), touchLocation)) {
			retry.setScale(Constants.SCALE_FACTOR);
			retry.setColor(Constants.yellow);
			schedule("retryTapped", 0.5f);
		} else if (next != null
				&& CGRect.containsPoint(next.getBoundingBox(), touchLocation)) {
			next.setScale(Constants.SCALE_FACTOR);
			next.setColor(Constants.yellow);
			schedule("nextTapped", 0.5f);
		}
		return false;
	}

	public void retryTapped(float dt) {
		this.removeFromParentAndCleanup(true);
		unschedule("retryTapped");
		GameManager.getInstance().restartStage();
	}

	public void homeTapped(float dt) {
		this.removeFromParentAndCleanup(true);
		unschedule("homeTapped");
		GameManager.getInstance().returnHome();
	}

	public void nextTapped(float dt) {
		this.removeFromParentAndCleanup(true);
		unschedule("nextTapped");
		GameManager.getInstance().runNextStage();
	}

}
