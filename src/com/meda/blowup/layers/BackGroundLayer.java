package com.meda.blowup.layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class BackGroundLayer extends CCLayer {
	public BackGroundLayer() {
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		CCTexture2D texture = CCTextureCache.sharedTextureCache().addImage(
				"background.png");
		CCSprite background = CCSprite.sprite(texture);
		background.setScaleX(winSize.width
				/ background.getTextureRect().size.width);
		background.setScaleY(winSize.height
				/ background.getTextureRect().size.height);
		background.setPosition(CGPoint.ccp(winSize.width / 2,
				winSize.height / 2));
		this.addChild(background, -1);
	}
}
