package com.meda.blowup.util;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.ccColor3B;

public final class Constants {
	public static final int PTM_RATIO = 80;
	public static final int UI_Z = 100;
	public static final int GAME_Z = 10;
	public static final int BACKGROUND_Z = 0;
	public static final int DRONE_Z = 100;
	public static final int GROUND_Z = 0;
	public static final int MONSTER_Z = 1;
	public static final int OSCILLATION_COUNTDOWN = 40;
	public static final int CHANCE_FACTOR = 0;

	public static final int WORLD_GAP = 50;
	public static final int MAX_DRONE_CHANCE = 5;
	public static final int DRONE_CHANCE_COUNT = 3;

	public static final int DRONE_TAG = 111;
	public static final int MONSTER_TAG = 300;
	public static final int MINION_TAG = 500;
	public static final int POWERUP_TAG = 800;
	public static final int MINE_TAG = 900;
	public static final int GAME_OVER_TAG = 333;
	public static final int CHANCE_TAG = 950;

	public static final float MINE_VELOCITY = 150.0f;
	public static final int UI_LAYER_TAG = 34;
	public static final int GAME_LAYER_TAG = 35;
	public static final int GAME_PAUSED_LAYER_TAG = 42;
	public static final int GAME_OVER_LAYER_TAG = 45;
	public static final int HELP_LAYER_TAG = 55;
	public static final int OPTIONS_LAYER_TAG = 56;

	public static final int UI_FONT_SZ = 30;

	public static final ccColor3B purple = ccColor3B.ccc3(148, 70, 167);
	public static final ccColor3B darkPurple = ccColor3B.ccc3(92, 22, 113);
	public static final ccColor3B green = ccColor3B.ccc3(30, 144, 55);
	public static final ccColor3B darkGreen = ccColor3B.ccc3(53, 159, 36);
	public static final ccColor3B blue = ccColor3B.ccc3(119, 217, 246);
	public static final ccColor3B darkBlue = ccColor3B.ccc3(13, 143, 207);
	public static final ccColor3B yellow = ccColor3B.ccc3(252, 186, 0);
	public static final ccColor3B darkYellow = ccColor3B.ccc3(226, 143, 0);

	public static final int STAGE_COUNT = 10;

	public static final float SCALE_FACTOR = (CCDirector.sharedDirector()
			.displaySize().width / 800.f);
	
	public static final int SP_F = SCALE_FACTOR > 1f ? 2:1;

	public static final float PERCENTAGE_WIN_RATIO = 80.f;
	public static final float TOTAL_AREA = (CCDirector.sharedDirector()
			.displaySize().width - 2 * WORLD_GAP)
			* (CCDirector.sharedDirector().displaySize().height - 2 * WORLD_GAP);
}
