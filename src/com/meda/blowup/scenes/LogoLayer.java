package com.meda.blowup.scenes;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import com.meda.blowup.GameManager;

public class LogoLayer extends CCScene {

	public LogoLayer() {
		super();
		CCLayer logo = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));
		CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage(
				"alizinhouselogo.png");
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		CCSprite logoSprite = CCSprite.sprite(texture);
		logoSprite.setPosition(CGPoint.ccp(winSize.width / 2,
				winSize.height / 2));
		logo.addChild(logoSprite);

		CCLabel orgs = CCLabel.makeLabel("© 2012 ALIZ in House", "Arial", 10);
		orgs.setPosition(CGPoint.ccp(winSize.width / 2, winSize.height * 0.1f));
		orgs.setColor(ccColor3B.ccBLACK);
		logo.addChild(orgs);
		this.addChild(logo);

		schedule("startGame", 1f);
	}

	public void startGame(float dt) {
		// Start game
		GameManager.getInstance().startGame();
		//Log.v("BlowUp", "Start game!");
		unschedule("startGame");
	}
}
