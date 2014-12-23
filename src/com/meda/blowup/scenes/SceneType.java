package com.meda.blowup.scenes;

public enum SceneType {
	MAIN_MENU, MONSTERS_MENU, CREDITS_SCENE, CYCLOPS_SCENE, FROGGY_SCENE, BUBBLE_MONSTER_SCENE, SMOKE_MONSTER_SCENE;

	public static SceneType getSceneType(int ord) {
		return values()[ord];
	}
}
