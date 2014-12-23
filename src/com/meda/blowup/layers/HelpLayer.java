package com.meda.blowup.layers;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;

import com.meda.blowup.R;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.util.Constants;

public class HelpLayer extends CCColorLayer {
	private final CGSize winSize = CCDirector.sharedDirector().displaySize();
	private CCSprite home;
	private CCLabel siteLink;

	public HelpLayer() {
		super(ccColor4B.ccc4(125, 125, 125, 240));

		CCBitmapFontAtlas x = CCBitmapFontAtlas.bitmapFontAtlas("Help Menu",
				"f1.fnt");
		x.setPosition(CGPoint.ccp(winSize.width / 2, winSize.height * 0.85f));
		x.setColor(Constants.yellow);
		x.setScale(Constants.SCALE_FACTOR);
		this.addChild(x);

		CCSprite help = new CCSprite("help.png");
		help.setPosition(CGPoint.ccp(winSize.width / 2, winSize.height / 2));
		help.setScale(Constants.SCALE_FACTOR * 1.5f);
		this.addChild(help);

		home = new CCSprite();
		home.setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("return.png"));
		home.setPosition(winSize.width * 0.1f, winSize.height * 0.1f);
		home.setScale(Constants.SCALE_FACTOR);
		this.addChild(home, 0);

		siteLink = CCLabel.makeLabel("www.alizinhouse.com", "Arial", 30);
		siteLink.setPosition(CGPoint.ccp(winSize.width / 2,
				winSize.height * 0.1f));
		siteLink.setScale(Constants.SCALE_FACTOR);
		this.addChild(siteLink);

		setIsTouchEnabled(true);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR * 1.5f);
		} else if (CGRect.containsPoint(siteLink.getBoundingBox(),
				touchLocation)) {
			siteLink.setScale(Constants.SCALE_FACTOR * 1.5f);
			siteLink.setColor(ccColor3B.ccBLUE);
		}
		return false;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(home.getBoundingBox(), touchLocation)) {
			home.setScale(Constants.SCALE_FACTOR);
			schedule("returnHome", .5f);
		} else if (CGRect.containsPoint(siteLink.getBoundingBox(),
				touchLocation)) {
			siteLink.setScale(Constants.SCALE_FACTOR);
			String url = "http://www.alizinhouse.com";
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			CCDirector.sharedDirector().getActivity().startActivity(intent);
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

}
