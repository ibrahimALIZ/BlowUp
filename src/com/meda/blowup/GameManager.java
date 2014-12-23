package com.meda.blowup;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import com.meda.blowup.layers.GameOverLayer;
import com.meda.blowup.layers.GamePausedLayer;
import com.meda.blowup.monsters.BossType;
import com.meda.blowup.scenes.CreditsScene;
import com.meda.blowup.scenes.GameScene;
import com.meda.blowup.scenes.MainMenu;
import com.meda.blowup.scenes.MonstersMenu;
import com.meda.blowup.scenes.SceneType;
import com.meda.blowup.util.Configuration;
import com.meda.blowup.util.Constants;

public class GameManager {
	private static GameManager instance;
	private boolean rightJoystick = true;
	private CGPoint dronePosition;
	private boolean isDrawing;
	private int experience;
	private int score;
	private int droneSpeed;
	private int droppedPowerupCount;
	private int consumedPowerupCount;
	private GameState gameState;
	private CCScene currentScene;
	private SceneType currentSceneType;
	private BossType bossType;

	// follow drone progress
	private int stageNumber;
	private int levelStage;
	private int numberOfPowerUps = 0;
	private int droneChance;
	private float percentageCleared;

	private GameManager() {
		Configuration.init();
	}

	public static GameManager getInstance() {
		synchronized (GameManager.class) {
			if (instance == null) {
				instance = new GameManager();
			}
		}
		return instance;
	}

	private void initGamePlay() {
		droneChance = Constants.DRONE_CHANCE_COUNT;
		percentageCleared = 0;
		droppedPowerupCount = 0;
		consumedPowerupCount = 0;
		gameState = GameState.PLAYING;
	}

	public void runScene(SceneType sceneType) {
		initGamePlay();
		currentSceneType = sceneType;

		switch (sceneType) {
		case MAIN_MENU:
			currentScene = new MainMenu();
			break;
		case MONSTERS_MENU:
			currentScene = new MonstersMenu();
			break;
		case CREDITS_SCENE:
			currentScene = new CreditsScene();
			break;
		case CYCLOPS_SCENE:
			bossType = BossType.CYCLOPS;
			levelStage = stageNumber >= Constants.STAGE_COUNT ? Constants.STAGE_COUNT - 1
					: stageNumber;
			currentScene = new GameScene(Configuration.getConfig(levelStage),
					BossType.CYCLOPS);
			break;
		case FROGGY_SCENE:
			bossType = BossType.FROGGY;
			levelStage = stageNumber >= 2 * Constants.STAGE_COUNT ? Constants.STAGE_COUNT - 1
					: stageNumber % Constants.STAGE_COUNT;
			currentScene = new GameScene(Configuration.getConfig(levelStage),
					BossType.FROGGY);
			break;
		case BUBBLE_MONSTER_SCENE:
			bossType = BossType.BUBBLE_MONSTER;
			levelStage = stageNumber >= 3 * Constants.STAGE_COUNT ? Constants.STAGE_COUNT - 1
					: stageNumber % Constants.STAGE_COUNT;
			currentScene = new GameScene(Configuration.getConfig(levelStage),
					BossType.BUBBLE_MONSTER);
			break;
		case SMOKE_MONSTER_SCENE:

			break;
		default:
			break;
		}

		if (currentScene != null) {
			if (CCDirector.sharedDirector().getRunningScene() != null) {
				CCDirector.sharedDirector().replaceScene(currentScene);
			} else {
				CCDirector.sharedDirector().runWithScene(currentScene);
			}
		}

	}

	public void returnHome() {
		runScene(SceneType.MONSTERS_MENU);
	}

	public void runNextStage() {
		if ((bossType == BossType.CYCLOPS && stageNumber >= Constants.STAGE_COUNT)
				|| (bossType == BossType.FROGGY && stageNumber >= (Constants.STAGE_COUNT * 2))) {
			runScene(SceneType.MONSTERS_MENU);
			return;
		}

		stageNumber++;
		initGamePlay();

		switch (bossType) {
		case CYCLOPS:
			if (stageNumber >= Constants.STAGE_COUNT) {
				bossType = BossType.FROGGY;
			}
			break;
		case FROGGY:
			if (stageNumber >= (Constants.STAGE_COUNT * 2)) {
				bossType = BossType.BUBBLE_MONSTER;
			}
			break;
		case BUBBLE_MONSTER:
			if (stageNumber >= (Constants.STAGE_COUNT * 3)) {
				currentScene = new CreditsScene();
				CCDirector.sharedDirector().replaceScene(currentScene);
				return;
			}
			break;
		default:
			runScene(SceneType.MONSTERS_MENU);
			return;
		}
		levelStage = stageNumber % Constants.STAGE_COUNT;
		Configuration config = Configuration.getConfig(levelStage);
		currentScene = new GameScene(config, bossType);
		CCDirector.sharedDirector().replaceScene(currentScene);
	}

	public void restartStage() {
		initGamePlay();
		levelStage = stageNumber % Constants.STAGE_COUNT;

		switch (bossType) {
		case CYCLOPS:
			if (stageNumber >= Constants.STAGE_COUNT) {
				levelStage = Constants.STAGE_COUNT - 1;
			}
			break;
		case FROGGY:
			if (stageNumber >= (Constants.STAGE_COUNT * 2)) {
				levelStage = Constants.STAGE_COUNT - 1;
			}
			break;
		case BUBBLE_MONSTER:
			if (stageNumber >= (Constants.STAGE_COUNT * 3)) {
				runScene(SceneType.MONSTERS_MENU);
				return;
			}
			break;
		default:
			levelStage = stageNumber % Constants.STAGE_COUNT;
			break;
		}

		Configuration config = Configuration.getConfig(levelStage);
		currentScene = new GameScene(config, bossType);
		CCDirector.sharedDirector().replaceScene(currentScene);
	}

	public int addPowerUp() {
		droppedPowerupCount++;
		return numberOfPowerUps++;
	}

	public int removePowerUp() {
		return numberOfPowerUps--;
	}

	public int getNumberOfPowerUps() {
		return numberOfPowerUps;
	}

	public void startGame() {
		// SoundEngine.sharedEngine().playSound(
		// CCDirector.sharedDirector().getActivity(),
		// R.raw.background_music_aac, true);

		// runScene(SceneType.CREDITS_SCENE);
		runScene(SceneType.getSceneType(gameState.ordinal()));
	}

	public void pauseGame() {
		if (currentScene==null) return;
		if (currentScene.getChild(Constants.GAME_PAUSED_LAYER_TAG) == null
				&& currentScene.getChild(Constants.GAME_OVER_LAYER_TAG) == null) {
			currentScene.addChild(new GamePausedLayer(), Constants.UI_Z + 1,
					Constants.GAME_PAUSED_LAYER_TAG);

			SoundEngine.sharedEngine().pauseSound();
			CCDirector.sharedDirector().pause();
		}
	}

	public void resumeGame() {
		SoundEngine.sharedEngine().resumeSound();
		CCDirector.sharedDirector().resume();
	}

	public void stopGame() {
		CCDirector.sharedDirector().end();
		SoundEngine.sharedEngine().stopSound();
	}

	public void back() {
	}

	public void endGame() {
		if (currentScene.getChild(Constants.GAME_OVER_LAYER_TAG) == null) {
			setScore();
			currentScene.addChild(new GameOverLayer(), Constants.UI_Z + 1,
					Constants.GAME_OVER_LAYER_TAG);
		}
	}

	public boolean isDroneDead() {
		return !(droneChance > 0);
	}

	public void removeChance() {
		if (droneChance > 0)
			droneChance--;
	}

	public void addChance() {
		if (droneChance < Constants.MAX_DRONE_CHANCE)
			droneChance++;
	}

	public int getDroneChance() {
		return droneChance;
	}

	public boolean isDrawing() {
		return isDrawing;
	}

	public void setDrawing(boolean isDrawing) {
		this.isDrawing = isDrawing;
	}

	public CGPoint getDronePosition() {
		return dronePosition;
	}

	public void setDronePosition(CGPoint dronePosition) {
		this.dronePosition = dronePosition;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public void updateExperience(int area) {
		int exp = area / 1000;
		experience += exp;
		score = experience;
		percentageCleared += (area * 100.f) / Constants.TOTAL_AREA;
	}

	private void setScore() {
		score = experience + 10 * droppedPowerupCount + 100
				* consumedPowerupCount;
	}

	public int getScore() {
		return score;
	}

	public void consumePowerup() {
		consumedPowerupCount++;
	}

	public int getDroneSpeed() {
		return droneSpeed;
	}

	public void setDroneSpeed(int droneSpeed) {
		this.droneSpeed = droneSpeed;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public boolean isRightJoystick() {
		return rightJoystick;
	}

	public void setRightJoystick(boolean rightJoystick) {
		this.rightJoystick = rightJoystick;
	}

	public BossType getBossType() {
		return bossType;
	}

	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public int getLevelStage() {
		return levelStage;
	}

	public void setLevelStage(int levelStage) {
		this.levelStage = levelStage;
	}

	public float getPercentageCleared() {
		return percentageCleared;
	}

	public void setPercentageCleared(int percentageCleared) {
		this.percentageCleared = percentageCleared;
	}

}
