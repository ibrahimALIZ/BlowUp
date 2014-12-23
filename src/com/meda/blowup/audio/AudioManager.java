package com.meda.blowup.audio;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;

import android.content.Context;

import com.meda.blowup.R;

public class AudioManager {
	private static AudioManager instance;
	private AudioState audioState;

	public static AudioManager getInstance() {
		synchronized (AudioManager.class) {
			if (instance == null) {
				instance = new AudioManager();
			}
		}
		return instance;
	}

	private AudioManager() {
	}

	public void initialize() {
		Context context = CCDirector.sharedDirector().getActivity();
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.binglow);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.boing);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.pan);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.boom02);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.spaceship);
		SoundEngine.sharedEngine().preloadSound(
				CCDirector.sharedDirector().getActivity(), R.raw.engine);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.merge);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.squeak);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.gameover);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.burn);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.select);
		SoundEngine.sharedEngine().preloadEffect(context, R.raw.page);
	}

	public AudioState getAudioState() {
		return audioState;
	}

	public void setAudioState(AudioState audioState) {
		this.audioState = audioState;
	}

	public void playEffect(int id) {
		if (audioState.equals(AudioState.SOUND_ON)) {
			SoundEngine.sharedEngine().playEffect(
					CCDirector.sharedDirector().getActivity(), id);
		}
	}

	public void playSound(int id, boolean loop) {
		if (audioState.equals(AudioState.SOUND_ON)) {
			SoundEngine.sharedEngine().playSound(
					CCDirector.sharedDirector().getActivity(), id, loop);
		}
	}

	public void resumeSound() {
		if (audioState.equals(AudioState.SOUND_ON)) {
			SoundEngine.sharedEngine().resumeSound();
		}
	}

	public void stopSound() {
		SoundEngine.sharedEngine().stopSound();
	}

}
