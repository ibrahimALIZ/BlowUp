package com.meda.blowup.layers;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

import com.meda.blowup.GameManager;
import com.meda.blowup.R;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.audio.AudioState;
import com.meda.blowup.util.Constants;

public class OptionsLayer extends CCColorLayer {
	CGSize winSize = CCDirector.sharedDirector().displaySize();
	private CCSprite home;
	private CCBitmapFontAtlas soundLabel, joystickLabel, soundOption,
			joystickOption;

	public OptionsLayer() {
		super(ccColor4B.ccc4(125, 125, 125, 240));

		CCBitmapFontAtlas x = CCBitmapFontAtlas.bitmapFontAtlas("Options Menu",
				"f1.fnt");
		x.setPosition(CGPoint.ccp(winSize.width / 2, winSize.height * 0.85f));
		x.setColor(Constants.yellow);
		x.setScale(Constants.SCALE_FACTOR);
		this.addChild(x);

		home = new CCSprite();
		home.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("return.png"));
		home.setPosition(winSize.width * 0.1f, winSize.height * 0.1f);
		home.setScale(Constants.SCALE_FACTOR);
		this.addChild(home, 0);

		soundLabel = CCBitmapFontAtlas.bitmapFontAtlas("Sound", "f1.fnt");
		soundLabel.setPosition(CGPoint.ccp(winSize.width * 0.4f,
				winSize.height * 0.6f));
		soundLabel.setColor(Constants.darkYellow);
		soundLabel.setScale(Constants.SCALE_FACTOR);
		this.addChild(soundLabel);

		if (AudioManager.getInstance().getAudioState()
				.equals(AudioState.SOUND_ON)) {
			soundOption = CCBitmapFontAtlas.bitmapFontAtlas("On", "f2.fnt");
		} else {
			soundOption = CCBitmapFontAtlas.bitmapFontAtlas("Off", "f2.fnt");
		}

		soundOption.setPosition(CGPoint.ccp(winSize.width * 0.6f,
				winSize.height * 0.6f));
		soundOption.setColor(ccColor3B.ccMAGENTA);
		soundOption.setScale(Constants.SCALE_FACTOR);
		this.addChild(soundOption);

		joystickLabel = CCBitmapFontAtlas.bitmapFontAtlas("Joystick", "f1.fnt");
		joystickLabel.setPosition(CGPoint.ccp(winSize.width * 0.4f,
				winSize.height * 0.4f));
		joystickLabel.setColor(Constants.darkYellow);
		joystickLabel.setScale(Constants.SCALE_FACTOR);
		this.addChild(joystickLabel);

		if (GameManager.getInstance().isRightJoystick()) {
			joystickOption = CCBitmapFontAtlas.bitmapFontAtlas("Right",
					"f2.fnt");
		} else {
			joystickOption = CCBitmapFontAtlas
					.bitmapFontAtlas("Left", "f2.fnt");
		}
		joystickOption.setPosition(CGPoint.ccp(winSize.width * 0.6f,
				winSize.height * 0.4f));
		joystickOption.setColor(ccColor3B.ccMAGENTA);
		joystickOption.setScale(Constants.SCALE_FACTOR);
		this.addChild(joystickOption);

		setIsTouchEnabled(true);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR * 1.5f);
		}
		if (CGRect.containsPoint(soundOption.getBoundingBox(), touchLocation)) {
			soundOption.setScale(Constants.SCALE_FACTOR * 1.5f);
		}
		if (CGRect
				.containsPoint(joystickOption.getBoundingBox(), touchLocation)) {
			joystickOption.setScale(Constants.SCALE_FACTOR * 1.5f);
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
		if (CGRect.containsPoint(soundOption.getBoundingBox(), touchLocation)) {
			soundOption.setScale(Constants.SCALE_FACTOR);
			schedule("updateSound");
		}
		if (CGRect
				.containsPoint(joystickOption.getBoundingBox(), touchLocation)) {
			joystickOption.setScale(Constants.SCALE_FACTOR);
			schedule("updateJoystick");
		}
		return false;
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		// ccTouchesEnded(event);
		return false;
	}

	public void returnHome(float dt) {
		unschedule("returnHome");
		AudioManager.getInstance().playEffect(R.raw.page);
		((CCLayer) this.getParent()).setIsTouchEnabled(true);
		removeFromParentAndCleanup(true);
	}

	public void updateSound(float dt) {
		unschedule("updateSound");
		AudioManager.getInstance().playEffect(R.raw.page);
		if (AudioManager.getInstance().getAudioState()
				.equals(AudioState.SOUND_ON)) {
			AudioManager.getInstance().setAudioState(AudioState.SOUND_OFF);
			AudioManager.getInstance().stopSound();
		} else {
			AudioManager.getInstance().setAudioState(AudioState.SOUND_ON);
		}
		if (AudioManager.getInstance().getAudioState()
				.equals(AudioState.SOUND_ON)) {
			soundOption.setString("On");
		} else {
			soundOption.setString("Off");
		}
	}

	public void updateJoystick(float dt) {
		unschedule("updateJoystick");
		AudioManager.getInstance().playEffect(R.raw.page);
		GameManager.getInstance().setRightJoystick(
				!GameManager.getInstance().isRightJoystick());
		if (GameManager.getInstance().isRightJoystick()) {
			joystickOption.setString("Right");
		} else {
			joystickOption.setString("Left");
		}
	}
}
