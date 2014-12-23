package com.meda.blowup.scenes;

import org.cocos2d.layers.CCScene;

import com.meda.blowup.layers.MainMenuLayer;

public class MainMenu extends CCScene {

	public MainMenu() {
		super();
		MainMenuLayer mainMenu = new MainMenuLayer();
		this.addChild(mainMenu);
	}
}
