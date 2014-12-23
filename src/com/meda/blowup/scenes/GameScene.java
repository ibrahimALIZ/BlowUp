package com.meda.blowup.scenes;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;

import com.meda.blowup.GameManager;
import com.meda.blowup.layers.BackGroundLayer;
import com.meda.blowup.layers.BlueMonsterLayer;
import com.meda.blowup.layers.GameLayer;
import com.meda.blowup.layers.GreenMonsterLayer;
import com.meda.blowup.layers.PurpleMonsterLayer;
import com.meda.blowup.layers.UILayer;
import com.meda.blowup.monsters.BossType;
import com.meda.blowup.util.Configuration;
import com.meda.blowup.util.Constants;

public class GameScene extends CCScene {

	public GameScene(Configuration conf, BossType boss) {
		GameManager.getInstance().setDroneSpeed(conf.getDroneSpeed());
		
		CCTexture2D texture;
		GameLayer game;
		switch (boss) {
		case BUBBLE_MONSTER:
			texture = CCTextureCache.sharedTextureCache().addImage("tile5.png");
			game = new BlueMonsterLayer(conf, texture);
			break;
		case FROGGY:
			texture = CCTextureCache.sharedTextureCache().addImage("tile2.png");
			game = new GreenMonsterLayer(conf, texture);
			break;
		case CYCLOPS:
			texture = CCTextureCache.sharedTextureCache().addImage("tile3.png");
			game = new PurpleMonsterLayer(conf, texture);
			break;
		default:
			texture = CCTextureCache.sharedTextureCache().addImage("tile3.png");
			game = new PurpleMonsterLayer(conf, texture);
			break;
		}		
		
		BackGroundLayer bgLayer = new BackGroundLayer();
		addChild(bgLayer, Constants.BACKGROUND_Z);
		
		addChild(game, Constants.GAME_Z, Constants.GAME_LAYER_TAG);
		
		UILayer ui = new UILayer();
		addChild(ui, Constants.UI_Z, Constants.UI_LAYER_TAG);
	}
}
