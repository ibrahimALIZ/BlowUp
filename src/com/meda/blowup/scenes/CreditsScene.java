package com.meda.blowup.scenes;

import org.cocos2d.layers.CCScene;

import com.meda.blowup.layers.BackGroundLayer;
import com.meda.blowup.layers.CreditsLayer;
import com.meda.blowup.util.Constants;

public class CreditsScene extends CCScene {
	public CreditsScene() {

		BackGroundLayer bgLayer = new BackGroundLayer();
		CreditsLayer chapters = new CreditsLayer();

		addChild(bgLayer, Constants.BACKGROUND_Z);
		addChild(chapters);
	}
}
