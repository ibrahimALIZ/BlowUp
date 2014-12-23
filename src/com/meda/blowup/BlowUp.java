package com.meda.blowup;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.opengl.CCTextureAtlas;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.audio.AudioState;
import com.meda.blowup.scenes.LogoLayer;
import com.meda.blowup.scenes.SceneType;

public class BlowUp extends Activity {
	protected CCGLSurfaceView _glSurfaceView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		_glSurfaceView = new CCGLSurfaceView(this);
		setContentView(_glSurfaceView);
	}

	@Override
	public void onStart() {
		super.onStart();

		if (!CCDirector.sharedDirector().getIsPaused()) {
			CCDirector.sharedDirector().attachInView(_glSurfaceView);
			CCDirector.sharedDirector().setLandscape(true);
			CCDirector.sharedDirector().setDeviceOrientation(
					CCDirector.kCCDeviceOrientationLandscapeLeft);
			CCDirector.sharedDirector().setDisplayFPS(false);
			CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

			// Show logo
			CCDirector.sharedDirector().runWithScene(new LogoLayer());
			initializeGame();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		SceneType s = GameManager.getInstance().getCurrentSceneType();
		if (!(s == SceneType.MAIN_MENU || s == SceneType.MONSTERS_MENU || s == SceneType.CREDITS_SCENE)) {
			GameManager.getInstance().pauseGame();
		}
		// Log.v("blowup", "pause");
	}

	@Override
	public void onResume() {
		super.onResume();
		// Log.v("blowup", "resume");
		// if (CCDirector.sharedDirector().getIsPaused()) {
		// GameManager.getInstance().resumeGame();
		// }
	}

	@Override
	public void onStop() {
		super.onStop();
		if (!CCDirector.sharedDirector().getIsPaused()) {
			SharedPreferences myPrefs = getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor ed = myPrefs.edit();
			GameManager gameManager = GameManager.getInstance();
			ed.putInt("GameState", gameManager.getCurrentSceneType().ordinal());
			ed.putInt("AudioState", AudioManager.getInstance().getAudioState()
					.ordinal());
			ed.putInt("RightJoystick", gameManager.isRightJoystick() ? 1 : 0);
			ed.putInt("StageNumber", gameManager.getStageNumber());
			ed.putInt("Experience", gameManager.getScore());
			ed.commit();
			gameManager.stopGame();
			// Log.v("blowup", "stopped not paused");
		}
		// Log.v("blowup", "stopped");
	}

	@Override
	public void onBackPressed() {
		GameManager gameManager = GameManager.getInstance();		
		switch (gameManager.getCurrentSceneType()) {
		case MAIN_MENU:
			super.onBackPressed();
			break;
		case MONSTERS_MENU:
		case CREDITS_SCENE:
			gameManager.runScene(SceneType.MAIN_MENU);
			break;
		case CYCLOPS_SCENE:
		case FROGGY_SCENE:
		case BUBBLE_MONSTER_SCENE:
		case SMOKE_MONSTER_SCENE:
			gameManager.pauseGame();
			break;
		default:
			super.onBackPressed();
			break;
		}
	}

	private void initializeGame() {
		// Load textures
		CCTextureAtlas atlas = CCTextureAtlas.textureAtlas("ingame.png", 50);
		CCSpriteFrameCache.sharedSpriteFrameCache().addSpriteFrames(
				"ingame.plist", atlas.getTexture());

		// Audio option
		AudioManager.getInstance().initialize();
		AudioManager.getInstance().setAudioState(AudioState.SOUND_ON);

		GameManager gameManager = GameManager.getInstance();
		// Joystick option
		gameManager.setRightJoystick(true);

		// Load preferences
		SharedPreferences myPrefs = getPreferences(Context.MODE_PRIVATE);
		int pref = myPrefs.getInt("GameState", 0);
		gameManager.setGameState(GameState.values()[pref]);

		pref = myPrefs.getInt("AudioState", 0);
		AudioManager.getInstance().setAudioState(AudioState.values()[pref]);

		pref = myPrefs.getInt("RightJoystick", 1);
		gameManager.setRightJoystick(pref > 0 ? true : false);

		pref = myPrefs.getInt("StageNumber", 0);
		gameManager.setStageNumber(pref);

		pref = myPrefs.getInt("Experience", 0);
		gameManager.setExperience(pref);
	}
}