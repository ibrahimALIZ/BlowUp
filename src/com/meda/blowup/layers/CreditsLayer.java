package com.meda.blowup.layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.particlesystem.CCParticleSnow;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

import com.meda.blowup.GameManager;
import com.meda.blowup.R;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.util.Constants;

public class CreditsLayer extends CCLayer {
	private CCSprite home;
	private CGSize winSize = CCDirector.sharedDirector().displaySize();

	public CreditsLayer() {
		this.setIsTouchEnabled(true);

		home = new CCSprite();
		home.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("return.png"));
		home.setPosition(winSize.width * 0.1f, winSize.height * 0.1f);
		home.setScale(Constants.SCALE_FACTOR);
		this.addChild(home, 0);

		CCBitmapFontAtlas header1 = CCBitmapFontAtlas.bitmapFontAtlas(
				"Congratulations", "f2.fnt");
		header1.setColor(ccColor3B.ccMAGENTA);
		header1.setPosition(CGPoint.ccp(winSize.width * 0.5f,
				winSize.height * 0.85f));
		header1.setScale(Constants.SCALE_FACTOR*2);
		this.addChild(header1);

		CCBitmapFontAtlas header2 = CCBitmapFontAtlas.bitmapFontAtlas(
				"You finished the game!", "f1.fnt");
		header2.setColor(Constants.darkYellow);
		header2.setPosition(CGPoint.ccp(winSize.width / 2,
				winSize.height * 0.65f));
		header2.setScale(Constants.SCALE_FACTOR);
		this.addChild(header2);
		
		CCBitmapFontAtlas score = CCBitmapFontAtlas.bitmapFontAtlas(
				"Total Score " + GameManager.getInstance().getScore(), "f2.fnt");
		score.setColor(Constants.yellow);
		score.setPosition(CGPoint.ccp(winSize.width / 2,
				winSize.height * 0.5f));
		score.setScale(Constants.SCALE_FACTOR);
		this.addChild(score);

		CCBitmapFontAtlas header3 = CCBitmapFontAtlas.bitmapFontAtlas(
				"Designed & Developed By", "f2.fnt");
		header3.setColor(ccColor3B.ccMAGENTA);
		header3.setPosition(CGPoint.ccp(winSize.width * 0.5f,
				winSize.height * 0.25f));
		header3.setScale(Constants.SCALE_FACTOR);
		this.addChild(header3);

		CCBitmapFontAtlas header4 = CCBitmapFontAtlas.bitmapFontAtlas(
				"ibrahim ALIZ", "f1.fnt");
		header4.setColor(Constants.darkYellow);
		header4.setPosition(CGPoint.ccp(winSize.width * 0.5f,
				winSize.height * 0.1f));
		header4.setScale(Constants.SCALE_FACTOR);
		this.addChild(header4);
		
		CCNode snow = CCParticleSnow.node();
		this.addChild(snow);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR * 1.5f);
		}
		return false;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR);
			schedule("returnHome");
		}
		return false;
	}

	public void returnHome(float dt) {
		unschedule("returnHome");
		AudioManager.getInstance().playEffect(R.raw.page);
		GameManager.getInstance().returnHome();
	}

}
