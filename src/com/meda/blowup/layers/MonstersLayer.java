package com.meda.blowup.layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;

import com.meda.blowup.GameManager;
import com.meda.blowup.R;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.monsters.BossType;
import com.meda.blowup.scenes.SceneType;
import com.meda.blowup.util.Constants;

public class MonstersLayer extends CCLayer {
	private CCSprite monster, options, help, home, lock, facebook, gplus,
			twitter;
	private CCBitmapFontAtlas header, select, forward, backward;

	private CGSize winSize = CCDirector.sharedDirector().displaySize();

	private BossType bossType = BossType.CYCLOPS;

	public MonstersLayer() {
		monster = new CCSprite();
		monster.setPosition(winSize.width * 0.5f, winSize.height * 0.5f);
		monster.setScale(Constants.SCALE_FACTOR * 1.5f);
		this.addChild(monster, 0);

		options = new CCSprite();
		options.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("options.png"));
		options.setPosition(winSize.width * 0.9f, winSize.height * 0.2f);
		options.setScale(Constants.SCALE_FACTOR);
		this.addChild(options, 0);

		help = new CCSprite();
		help.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("help.png"));
		help.setPosition(winSize.width * 0.8f, winSize.height * 0.2f);
		help.setScale(Constants.SCALE_FACTOR);
		this.addChild(help, 0);

		home = new CCSprite();
		home.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("return.png"));
		home.setPosition(winSize.width * 0.1f, winSize.height * 0.1f);
		home.setScale(Constants.SCALE_FACTOR);
		this.addChild(home, 0);

		header = CCBitmapFontAtlas.bitmapFontAtlas("Cyclops", "f1.fnt");
		header.setColor(Constants.yellow);
		header.setPosition(winSize.width * 0.5f, winSize.height * 0.85f);
		header.setScale(Constants.SCALE_FACTOR);
		this.addChild(header, 0);

		select = CCBitmapFontAtlas.bitmapFontAtlas("Select", "f1.fnt");
		select.setColor(Constants.yellow);
		select.setPosition(winSize.width * 0.5f, winSize.height * 0.15f);
		select.setScale(Constants.SCALE_FACTOR);
		this.addChild(select, 0);

		forward = CCBitmapFontAtlas.bitmapFontAtlas(">>", "f1.fnt");
		forward.setPosition(winSize.width * 0.9f, winSize.height * 0.5f);
		forward.setColor(Constants.yellow);
		forward.setScale(Constants.SCALE_FACTOR);
		this.addChild(forward, 0);

		backward = CCBitmapFontAtlas.bitmapFontAtlas("<<", "f1.fnt");
		backward.setPosition(winSize.width * 0.1f, winSize.height * 0.5f);
		backward.setColor(Constants.yellow);
		backward.setScale(Constants.SCALE_FACTOR);
		this.addChild(backward, 0);
		backward.setVisible(false);

		lock = new CCSprite();
		lock.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("lock.png"));
		lock.setPosition(winSize.width * 0.5f, winSize.height * 0.15f);
		lock.setVisible(false);
		lock.setScale(Constants.SCALE_FACTOR);
		this.addChild(lock, 10);

		gplus = new CCSprite();
		gplus.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("gplus.png"));
		gplus.setPosition(winSize.width * 0.1f, winSize.height * 0.8f);
		gplus.setScale(Constants.SCALE_FACTOR);
		this.addChild(gplus, 10);

		facebook = new CCSprite();
		facebook.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("facebook.png"));
		facebook.setPosition(winSize.width * 0.8f, winSize.height * 0.8f);
		facebook.setScale(Constants.SCALE_FACTOR);
		this.addChild(facebook, 10);

		twitter = new CCSprite();
		twitter.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("twitter.png"));
		twitter.setPosition(winSize.width * 0.9f, winSize.height * 0.8f);
		twitter.setScale(Constants.SCALE_FACTOR);
		this.addChild(twitter, 10);

		switch (bossType) {
		case CYCLOPS:
			monster.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("PurpleMonster300.png"));
			backward.setVisible(false);
			break;
		case FROGGY:
			monster.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("GreenMonster300.png"));
			if (GameManager.getInstance().getStageNumber() >= Constants.STAGE_COUNT) {
				select.setVisible(true);
				lock.setVisible(false);
			} else {
				select.setVisible(false);
				lock.setVisible(true);
			}
			break;
		case BUBBLE_MONSTER:
			monster.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("BlueMonster300.png"));
			if (GameManager.getInstance().getStageNumber() >= 2 * Constants.STAGE_COUNT) {
				select.setVisible(true);
				lock.setVisible(false);
			} else {
				select.setVisible(false);
				lock.setVisible(true);
			}
			forward.setVisible(false);
			break;
		default:
			monster.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("PurpleMonster300.png"));
			backward.setVisible(false);
			break;
		}

		this.setIsTouchEnabled(true);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(select.getBoundingBox(), touchLocation)) {
			select.setScale(Constants.SCALE_FACTOR * 1.5f);
			switch (bossType) {
			case CYCLOPS:
			case FROGGY:
			case BUBBLE_MONSTER:

			default:
				select.setColor(Constants.darkYellow);
				break;
			}
		} else if (CGRect
				.containsPoint(monster.getBoundingBox(), touchLocation)) {
		} else if (CGRect.containsPoint(backward.getBoundingBox(),
				touchLocation)) {
			backward.setScale(Constants.SCALE_FACTOR * 1.5f);
			backward.setColor(Constants.darkYellow);
		} else if (CGRect
				.containsPoint(forward.getBoundingBox(), touchLocation)) {
			forward.setScale(Constants.SCALE_FACTOR * 1.5f);
			forward.setColor(Constants.darkYellow);
		} else if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR * 1.5f);
		} else if (CGRect
				.containsPoint(options.getBoundingBox(), touchLocation)) {
			options.setScale(Constants.SCALE_FACTOR * 1.5f);
		} else if (CGRect.containsPoint(help.getBoundingBox(), touchLocation)) {
			help.setScale(Constants.SCALE_FACTOR * 1.5f);
		} else if (CGRect.containsPoint(facebook.getBoundingBox(),
				touchLocation)) {
			facebook.setScale(Constants.SCALE_FACTOR * 1.5f);
		} else if (CGRect.containsPoint(gplus.getBoundingBox(), touchLocation)) {
			gplus.setScale(Constants.SCALE_FACTOR * 1.5f);
		} else if (CGRect
				.containsPoint(twitter.getBoundingBox(), touchLocation)) {
			twitter.setScale(Constants.SCALE_FACTOR * 1.5f);
		} else {
		}
		return false;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (select.getVisible()
				&& CGRect.containsPoint(select.getBoundingBox(), touchLocation)) {
			AudioManager.getInstance().playEffect(R.raw.select);
			select.setScale(Constants.SCALE_FACTOR);
			switch (bossType) {
			case CYCLOPS:
				GameManager.getInstance().runScene(SceneType.CYCLOPS_SCENE);
				break;
			case FROGGY:
				GameManager.getInstance().runScene(SceneType.FROGGY_SCENE);
				break;
			case BUBBLE_MONSTER:
				GameManager.getInstance().runScene(
						SceneType.BUBBLE_MONSTER_SCENE);
				break;
			default:
				break;
			}

		} else if (backward.getVisible()
				&& CGRect.containsPoint(backward.getBoundingBox(),
						touchLocation)) {
			AudioManager.getInstance().playEffect(R.raw.page);
			backward.setScale(Constants.SCALE_FACTOR);
			switch (bossType) {
			case CYCLOPS:
				// GameManager.getInstance().runScene(SceneType.MAIN_MENU);
				break;
			case FROGGY:
				bossType = BossType.CYCLOPS;
				header.setString("Cyclops");
				monster.removeFromParentAndCleanup(true);
				monster.setDisplayFrame(CCSpriteFrameCache
						.sharedSpriteFrameCache().getSpriteFrame(
								"PurpleMonster300.png"));			
				this.addChild(monster);
				select.setVisible(true);
				lock.setVisible(false);
				backward.setVisible(false);
				forward.setVisible(true);
				break;
			case BUBBLE_MONSTER:
				bossType = BossType.FROGGY;
				header.setString("Froggy");
				monster.removeFromParentAndCleanup(true);
				monster.setDisplayFrame(CCSpriteFrameCache
						.sharedSpriteFrameCache().getSpriteFrame(
								"GreenMonster300.png"));			
				this.addChild(monster);

				if (GameManager.getInstance().getStageNumber() >= Constants.STAGE_COUNT) {
					select.setVisible(true);
					lock.setVisible(false);
				} else {
					select.setVisible(false);
					lock.setVisible(true);
				}

				backward.setVisible(true);
				forward.setVisible(true);
				break;
			default:
				break;
			}
		} else if (forward.getVisible()
				&& CGRect
						.containsPoint(forward.getBoundingBox(), touchLocation)) {
			AudioManager.getInstance().playEffect(R.raw.page);
			forward.setScale(Constants.SCALE_FACTOR);
			switch (bossType) {
			case CYCLOPS:
				bossType = BossType.FROGGY;
				header.setString("Froggy");
				monster.removeFromParentAndCleanup(true);
				monster.setDisplayFrame(CCSpriteFrameCache
						.sharedSpriteFrameCache().getSpriteFrame(
								"GreenMonster300.png"));			
				this.addChild(monster);

				if (GameManager.getInstance().getStageNumber() >= Constants.STAGE_COUNT) {
					select.setVisible(true);
					lock.setVisible(false);
				} else {
					select.setVisible(false);
					lock.setVisible(true);
				}

				backward.setVisible(true);
				forward.setVisible(true);
				break;
			case FROGGY:
				bossType = BossType.BUBBLE_MONSTER;
				header.setString("Bubble Monster");
				monster.removeFromParentAndCleanup(true);
				monster.setDisplayFrame(CCSpriteFrameCache
						.sharedSpriteFrameCache().getSpriteFrame(
								"BlueMonster300.png"));			
				this.addChild(monster);

				if (GameManager.getInstance().getStageNumber() >= (2 * Constants.STAGE_COUNT)) {
					select.setVisible(true);
					lock.setVisible(false);
				} else {
					select.setVisible(false);
					lock.setVisible(true);
				}

				backward.setVisible(true);
				forward.setVisible(false);
				break;
			case BUBBLE_MONSTER:
				break;
			default:
				break;
			}
		} else if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			AudioManager.getInstance().playEffect(R.raw.select);
			home.setScale(Constants.SCALE_FACTOR);
			GameManager.getInstance().runScene(SceneType.MAIN_MENU);
		} else if (CGRect
				.containsPoint(options.getBoundingBox(), touchLocation)) {
			AudioManager.getInstance().playEffect(R.raw.select);
			options.setScale(Constants.SCALE_FACTOR);
			displayOptionsMenu();
		} else if (CGRect.containsPoint(help.getBoundingBox(), touchLocation)) {
			AudioManager.getInstance().playEffect(R.raw.select);
			help.setScale(Constants.SCALE_FACTOR);
			displayHelpMenu();
		} else if (CGRect.containsPoint(facebook.getBoundingBox(),
				touchLocation)) {
			facebook.setScale(Constants.SCALE_FACTOR);
			String url = "http://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Falizinhouse.com%2Fwp%2F%3Fp%3D242&t=Blow+Out+Monsters";
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			CCDirector.sharedDirector().getActivity().startActivity(intent);
		} else if (CGRect.containsPoint(gplus.getBoundingBox(), touchLocation)) {
			gplus.setScale(Constants.SCALE_FACTOR);
			String url = "https://plusone.google.com/_/+1/confirm?hl=en&url=http%3A%2F%2Falizinhouse.com%2Fwp%2F%3Fp%3D242&title=Blow+Out+Monsters";
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			CCDirector.sharedDirector().getActivity().startActivity(intent);
		} else if (CGRect
				.containsPoint(twitter.getBoundingBox(), touchLocation)) {
			twitter.setScale(Constants.SCALE_FACTOR);
			String url = "https://twitter.com/intent/tweet?original_referer=http%3A%2F%2Falizinhouse.com%2Fwp%2F&text=&url=http%3A%2F%2Falizinhouse.com%2Fwp%2F%3Fp%3D242";
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			CCDirector.sharedDirector().getActivity().startActivity(intent);
		} else {
		}
		return false;
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		return false;
	}

	private void displayOptionsMenu() {
		this.setIsTouchEnabled(false);

		if (getChild(Constants.OPTIONS_LAYER_TAG) == null) {
			addChild(new OptionsLayer(), Constants.UI_Z + 1,
					Constants.OPTIONS_LAYER_TAG);
		}
	}

	private void displayHelpMenu() {
		this.setIsTouchEnabled(false);

		if (getChild(Constants.HELP_LAYER_TAG) == null) {
			addChild(new HelpLayer(), Constants.UI_Z + 1,
					Constants.HELP_LAYER_TAG);
		}
	}
}
