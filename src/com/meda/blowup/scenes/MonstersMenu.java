package com.meda.blowup.scenes;

import org.cocos2d.layers.CCScene;

import com.meda.blowup.layers.BackGroundLayer;
import com.meda.blowup.layers.MonstersLayer;
import com.meda.blowup.util.Constants;

public class MonstersMenu extends CCScene {
	public MonstersMenu() {

		BackGroundLayer bgLayer = new BackGroundLayer();
		MonstersLayer chapters = new MonstersLayer();

		addChild(bgLayer, Constants.BACKGROUND_Z);
		addChild(chapters);
	}
}
