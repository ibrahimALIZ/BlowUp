package com.meda.blowup.layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

import com.meda.blowup.GameManager;
import com.meda.blowup.R;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.effects.DroneSmoke;
import com.meda.blowup.scenes.SceneType;
import com.meda.blowup.util.Constants;

public class MainMenuLayer extends CCLayer {
	private DroneSmoke droneSmoke;

	public MainMenuLayer() {
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		CCSprite background = CCSprite.sprite("Monsters.png");
		background.setScaleX(winSize.width
				/ background.getTextureRect().size.width);
		background.setScaleY(winSize.height
				/ background.getTextureRect().size.height);
		background.setPosition(winSize.width / 2, winSize.height / 2);
		this.addChild(background);

		CCBitmapFontAtlas bheader = CCBitmapFontAtlas.bitmapFontAtlas(
				"BLOW OUT", "f2.fnt");
		bheader.setColor(ccColor3B.ccMAGENTA);
		bheader.setPosition(winSize.width * 0.4f, winSize.height * 0.75f);		
//		Log.v("Blow Out", "W:" + winSize.width + " H:" + winSize.height + " S:"
//				+ Constants.SCALE_FACTOR);		
		bheader.setScale(Constants.SCALE_FACTOR * 2.f);
		this.addChild(bheader);

		CCBitmapFontAtlas mheader = CCBitmapFontAtlas.bitmapFontAtlas(
				"MONSTERS", "f1.fnt");
		mheader.setColor(Constants.darkYellow);
		mheader.setPosition(winSize.width * 0.4f, winSize.height * 0.5f);
		mheader.setScale(Constants.SCALE_FACTOR * 2.0f);
		this.addChild(mheader);

		CCSprite drone = new CCSprite(Constants.SCALE_FACTOR >= 1 ? "drone256.png": "drone128.png");
		drone.setPosition(CGPoint.ccp(winSize.width * 0.75f,
				winSize.height * 0.6f));
		drone.setRotation(30.0f);
		this.addChild(drone, Constants.DRONE_Z);

		droneSmoke = new DroneSmoke();
		droneSmoke.setPosition(CGPoint.ccp(winSize.width * 0.82f,
				winSize.height * 0.8f));
		droneSmoke.setRotation(210.f);
		droneSmoke.setStartSize(15.0f);
		droneSmoke.setStartSizeVar(10.0f);
		this.addChild(droneSmoke, Constants.DRONE_Z);

		CCBitmapFontAtlas select = CCBitmapFontAtlas.bitmapFontAtlas("Touch to play", "f2.fnt");
		select.setColor(ccColor3B.ccMAGENTA);
		select.setPosition(winSize.width * 0.5f, winSize.height * 0.05f);
		select.setScale(Constants.SCALE_FACTOR / 2.f);
		this.addChild(select, 0);
		
		setIsTouchEnabled(true);
		AudioManager.getInstance().playSound(R.raw.engine, true);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// CGPoint touchLocation = convertTouchToNodeSpace(event);
		// if (CGRect.containsPoint(next.getBoundingBox(), touchLocation)) {
		// next.setScale(2.0f);
		// }
		return false;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		AudioManager.getInstance().playEffect(R.raw.spaceship);
		droneSmoke.setLife(3.f);
		droneSmoke.setAngleVar(25);
		schedule("startGame", 3f);
		this.setIsTouchEnabled(false);
		return false;
	}

	public void startGame(float dt) {
		unschedule("startGame");
		AudioManager.getInstance().stopSound();
		GameManager.getInstance().runScene(SceneType.MONSTERS_MENU);
	}
}
