package com.meda.blowup.layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

import com.meda.blowup.GameManager;
import com.meda.blowup.util.Constants;

public class GamePausedLayer extends CCLayer {
	private CCBitmapFontAtlas header, home, retry, next;
	private CGSize winSize = CCDirector.sharedDirector().displaySize();

	public GamePausedLayer() {
		this.setIsTouchEnabled(true);

		home = CCBitmapFontAtlas.bitmapFontAtlas("HOME", "f1.fnt");
		home.setColor(Constants.yellow);
		home.setPosition(CGPoint.ccp(winSize.width * 0.25f,
				winSize.height * 0.5f));
		home.setScale(Constants.SCALE_FACTOR);
		this.addChild(home);

		retry = CCBitmapFontAtlas.bitmapFontAtlas("RESTART", "f1.fnt");
		retry.setColor(Constants.yellow);
		retry.setPosition(CGPoint.ccp(winSize.width * 0.5f,
				winSize.height * 0.5f));
		retry.setScale(Constants.SCALE_FACTOR);
		this.addChild(retry);

		header = CCBitmapFontAtlas.bitmapFontAtlas("GAME PAUSED", "f2.fnt");
		header.setScale(Constants.SCALE_FACTOR * 1.5f);
		header.setColor(ccColor3B.ccMAGENTA);
		header.setPosition(CGPoint.ccp(winSize.width / 2,
				winSize.height * 0.65f));
		this.addChild(header);

		next = CCBitmapFontAtlas.bitmapFontAtlas("PLAY", "f1.fnt");
		next.setColor(Constants.yellow);
		next.setPosition(CGPoint.ccp(winSize.width * 0.75f,
				winSize.height * 0.5f));
		next.setScale(Constants.SCALE_FACTOR);
		this.addChild(next);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// Log.v("UILayer", "Touch at: " + event.getX() + ", " + event.getY());
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR*1.5f);
			home.setColor(Constants.darkYellow);
		} else if (CGRect.containsPoint(retry.getBoundingBox(), touchLocation)) {
			retry.setScale(Constants.SCALE_FACTOR*1.5f);
			retry.setColor(Constants.darkYellow);
		} else if (CGRect.containsPoint(next.getBoundingBox(), touchLocation)) {
			next.setScale(Constants.SCALE_FACTOR*1.5f);
			next.setColor(Constants.darkYellow);
		}
		return false;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR);
			home.setColor(Constants.yellow);
			GameManager.getInstance().resumeGame();
			this.removeFromParentAndCleanup(true);
			GameManager.getInstance().returnHome();
		} else if (CGRect.containsPoint(retry.getBoundingBox(), touchLocation)) {
			retry.setScale(Constants.SCALE_FACTOR);
			retry.setColor(Constants.yellow);
			GameManager.getInstance().resumeGame();
			this.removeFromParentAndCleanup(true);
			GameManager.getInstance().restartStage();
		} else if (CGRect.containsPoint(next.getBoundingBox(), touchLocation)) {
			next.setScale(Constants.SCALE_FACTOR);
			next.setColor(Constants.yellow);
			GameManager.getInstance().resumeGame();
			this.removeFromParentAndCleanup(true);
		}
		return false;
	}

}
