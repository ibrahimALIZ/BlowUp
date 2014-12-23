package com.meda.blowup.layers;

import java.text.DecimalFormat;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

import com.meda.blowup.GameManager;
import com.meda.blowup.GameState;
import com.meda.blowup.R.raw;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.util.Constants;

public class UILayer extends CCLayer {
	private CCBitmapFontAtlas experienceLabel, chapterLabel, percentageLabel;
	private CCSprite dpad, thumb, drawButton, pause;
	private CGSize winSize = CCDirector.sharedDirector().displaySize();

	private int direction;
	private boolean moving;
	private CGPoint defaultThumbPosition;
	private int __thumbDistInc = 4;
	private float __thumbLength = 80 * Constants.SCALE_FACTOR;

	private boolean pauseTouched;

	private CGPoint __dpadPosition, __thumbPosition, __buttonPosition;

	public UILayer() {
		if (GameManager.getInstance().isRightJoystick()) {
			__dpadPosition = CGPoint.ccp(winSize.width * 0.8f,
					winSize.height * 0.3f);
			__thumbPosition = CGPoint.ccp(winSize.width * 0.8f,
					winSize.height * 0.3f);
			__buttonPosition = CGPoint.ccp(winSize.width * 0.2f,
					winSize.height * 0.3f);
		} else {
			__dpadPosition = CGPoint.ccp(winSize.width * 0.2f,
					winSize.height * 0.3f);
			__thumbPosition = CGPoint.ccp(winSize.width * 0.2f,
					winSize.height * 0.3f);
			__buttonPosition = CGPoint.ccp(winSize.width * 0.8f,
					winSize.height * 0.3f);
		}

		chapterLabel = CCBitmapFontAtlas.bitmapFontAtlas("Stage  "
				+ (GameManager.getInstance().getLevelStage() + 1), "f1.fnt");
		chapterLabel.setColor(Constants.yellow);
		chapterLabel.setPosition(CGPoint.ccp(winSize.width * 0.5f,
				winSize.height - Constants.WORLD_GAP / 2f));
		chapterLabel.setScale(Constants.SCALE_FACTOR * 0.5f);
		this.addChild(chapterLabel);

		experienceLabel = CCBitmapFontAtlas.bitmapFontAtlas("Experience  "
				+ GameManager.getInstance().getExperience(), "f1.fnt");
		experienceLabel.setColor(Constants.yellow);
		experienceLabel.setPosition((winSize.width / 5.f) * 4, winSize.height
				- Constants.WORLD_GAP / 2.f);
		experienceLabel.setScale(Constants.SCALE_FACTOR * 0.5f);
		this.addChild(experienceLabel, 0);

		percentageLabel = CCBitmapFontAtlas.bitmapFontAtlas("% ", "f1.fnt");
		percentageLabel.setColor(Constants.yellow);
		percentageLabel.setPosition((winSize.width / 5.f) * 4,
				Constants.WORLD_GAP / 2.f);
		percentageLabel.setScale(Constants.SCALE_FACTOR * 0.5f);
		this.addChild(percentageLabel, 0);

		dpad = new CCSprite();
		dpad.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("dpad.png"));
		dpad.setPosition(__dpadPosition);
		dpad.setScale(Constants.SCALE_FACTOR);
		this.addChild(dpad);

		thumb = new CCSprite();
		thumb.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("thumb.png"));
		thumb.setPosition(__thumbPosition);
		thumb.setScale(Constants.SCALE_FACTOR);
		this.addChild(thumb);
		defaultThumbPosition = thumb.getPosition();

		drawButton = new CCSprite();
		drawButton.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("pawn.png"));
		drawButton.setPosition(__buttonPosition);
		drawButton.setScale(Constants.SCALE_FACTOR);
		this.addChild(drawButton);

		pause = new CCSprite();
		pause.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("freeze.png"));
		pause.setPosition(Constants.WORLD_GAP, winSize.height
				- Constants.WORLD_GAP);
		pause.setScale(Constants.SCALE_FACTOR * 1.5f);
		this.addChild(pause);

		this.setIsTouchEnabled(true);
		this.scheduleUpdate();
		this.schedule("updateVisuals");
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(thumb.getBoundingBox(), touchLocation)) {
			moving = true;
		}
		if (CGRect.containsPoint(pause.getBoundingBox(), touchLocation)) {
			pause.setScale(Constants.SCALE_FACTOR * 2.f);
			pauseTouched = true;
		}

		direction = 0;
		return false;
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if (moving) {
			CGPoint touchLocation = convertTouchToNodeSpace(event);

			if (Math.abs(touchLocation.x - defaultThumbPosition.x) > Math
					.abs(touchLocation.y - defaultThumbPosition.y)) {
				if (touchLocation.x > defaultThumbPosition.x) {
					direction = 1;
				} else {
					direction = 2;
				}
			} else {
				if (touchLocation.y > defaultThumbPosition.y) {
					direction = 3;
				} else {
					direction = 4;
				}
			}
			CGPoint p;
			switch (direction) {
			case 1:
				p = CGPoint.ccp(thumb.getPosition().x + __thumbDistInc,
						thumb.getPosition().y);
				break;
			case 2:
				p = CGPoint.ccp(thumb.getPosition().x - __thumbDistInc,
						thumb.getPosition().y);
				break;
			case 3:
				p = CGPoint.ccp(thumb.getPosition().x, thumb.getPosition().y
						+ __thumbDistInc);
				break;
			case 4:
				p = CGPoint.ccp(thumb.getPosition().x, thumb.getPosition().y
						- __thumbDistInc);
				break;
			default:
				p = defaultThumbPosition;
				break;
			}
			if (Math.abs(p.x - defaultThumbPosition.x) < __thumbLength
					&& Math.abs(p.y - defaultThumbPosition.y) < __thumbLength) {
				thumb.setPosition(p);
			}
		}

		return false;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// Log.v("UILayer", "Touch at: " + event.getX() + ", " + event.getY());
		for (int i = 0; i < event.getPointerCount(); i++) {
			// Log.v("UILayer", "Point x:" + event.getX(i) + "Point y:" +
			// event.getY(i));
			CGPoint touchLocation = convertTouchToNodeSpace(event, i);
			if (CGRect
					.containsPoint(drawButton.getBoundingBox(), touchLocation)) {
				GameManager gameManager = GameManager.getInstance();
				if (gameManager.getGameState().equals(GameState.PLAYING)) {
					if (gameManager.isDrawing()) {
						drawButton.setDisplayFrame(CCSpriteFrameCache
								.sharedSpriteFrameCache().getSpriteFrame(
										"pawn.png"));
						gameManager.setDrawing(false);
					} else {
						drawButton.setDisplayFrame(CCSpriteFrameCache
								.sharedSpriteFrameCache().getSpriteFrame(
										"draw.png"));
						gameManager.setDrawing(true);
					}
				}

				return false;
			}
		}

		if (pauseTouched) {
			pause.setScale(Constants.SCALE_FACTOR * 1.5f);
			AudioManager.getInstance().playEffect(raw.select);
			GameManager.getInstance().pauseGame();
			pauseTouched = false;
		}

		thumb.setPosition(defaultThumbPosition);
		moving = false;
		direction = 0;
		return false;
	}

	public void update(float dt) {
		if (direction == 0)
			return;

		CGPoint p = GameManager.getInstance().getDronePosition();
		CGPoint newP = new CGPoint();

		int droneDistInc = GameManager.getInstance().getDroneSpeed();

		switch (direction) {
		case 1:
			newP.set(p.x + droneDistInc, p.y);
			break;
		case 2:
			newP.set(p.x - droneDistInc, p.y);
			break;
		case 3:
			newP.set(p.x, p.y + droneDistInc);
			break;
		case 4:
			newP.set(p.x, p.y - droneDistInc);
			break;
		default:
			break;
		}
		winSize = CCDirector.sharedDirector().displaySize();
		if (newP.x > 0 && newP.x < winSize.width && newP.y > 0
				&& newP.y < winSize.height) {
			GameManager.getInstance().setDronePosition(newP);
		}
	}

	public void updateVisuals(float dt) {
		experienceLabel.setString("Experience  "
				+ GameManager.getInstance().getExperience());
		percentageLabel.setString("% "
				+ new DecimalFormat("#.#").format(GameManager.getInstance()
						.getPercentageCleared()));

		int chanceNumber = GameManager.getInstance().getDroneChance();
		for (int i = 0; i < Constants.MAX_DRONE_CHANCE; i++) {
			if (i < chanceNumber) { // There shall be a drone icon
				if (this.getChild(Constants.CHANCE_TAG + i) == null) {
					CCSprite chance = new CCSprite();
					chance.setDisplayFrame(CCSpriteFrameCache
							.sharedSpriteFrameCache().getSpriteFrame(
									"drone32.png"));
					chance.setPosition(CGPoint.ccp(
							Constants.WORLD_GAP
									* 3
									+ (i * chance.getContentSize().width * Constants.SCALE_FACTOR),
							(winSize.height - Constants.WORLD_GAP / 2.f)));
					chance.setScale(Constants.SCALE_FACTOR);
					this.addChild(chance, 0, Constants.CHANCE_TAG + i);
				}
			} else {// There wont be a drone icon
				if (this.getChild(Constants.CHANCE_TAG + i) != null) {
					this.removeChild(Constants.CHANCE_TAG + i, true);
				}
			}
		}
	}
}
