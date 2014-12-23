package com.meda.blowup.util;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
	private int droneSpeed;
	private int monsterMaxSpeed;
	private int minionMaxSpeed;
	private float monsterSpeedFactor;
	private float minionSpeedFactor;
	private int monsterCount;
	private int minionCount;

	private static final int ISPF = Constants.SP_F;
	
	private static List<Configuration> configList;
	private static final int CAPACITY = Constants.STAGE_COUNT;

	public static void init() {
		configList = new ArrayList<Configuration>(CAPACITY);
		//Levels                		DroneSpeed  	MonMax  	MinMax  	MonSF       		MinSF       MonCount    MinCount
		configList.add(new Configuration(2*ISPF,       0*ISPF,      5*ISPF,      0.0f*ISPF,       0.005f*ISPF,     0,          1));
		configList.add(new Configuration(2*ISPF,       0*ISPF,      8*ISPF,      0.0f*ISPF,       0.010f*ISPF,     0,          2));
		configList.add(new Configuration(2*ISPF,       5*ISPF,      0*ISPF,      0.2f*ISPF,       0.015f*ISPF,     1,          0));
		configList.add(new Configuration(2*ISPF,       5*ISPF,      5*ISPF,      0.2f*ISPF,       0.005f*ISPF,     1,          2));
		configList.add(new Configuration(4*ISPF,       10*ISPF,     8*ISPF,      0.5f*ISPF,       0.015f*ISPF,     1,          2));
		configList.add(new Configuration(4*ISPF,       10*ISPF,     0*ISPF,      1.0f*ISPF,       0.005f*ISPF,     1,          0));
		configList.add(new Configuration(4*ISPF,       15*ISPF,     10*ISPF,     0.5f*ISPF,       0.005f*ISPF,     1,          3));
		configList.add(new Configuration(5*ISPF,       15*ISPF,     10*ISPF,     0.5f*ISPF,       0.050f*ISPF,     1,          5));    
		configList.add(new Configuration(5*ISPF,       20*ISPF,     0*ISPF,      1.5f*ISPF,       0.000f*ISPF,     1,          0));
		configList.add(new Configuration(6*ISPF,       20*ISPF,     15*ISPF,     1.5f*ISPF,       0.100f*ISPF,     1,          5));
	}

	public static Configuration getConfig(int n) {
		n %= CAPACITY;
		return configList.get(n);
	}

	private Configuration(int ds, int moms, int mims, float mosf, float misf,
			int moc, int mic) {
		this.droneSpeed = ds;
		this.monsterMaxSpeed = moms;
		this.minionMaxSpeed = mims;
		this.monsterSpeedFactor = mosf;
		this.minionSpeedFactor = misf;
		this.monsterCount = moc;
		this.minionCount = mic;
	}

	// getters
	public int getMonsterMaxSpeed() {
		return monsterMaxSpeed;
	}

	public int getMinionMaxSpeed() {
		return minionMaxSpeed;
	}

	public float getMonsterSpeedFactor() {
		return monsterSpeedFactor;
	}

	public float getMinionSpeedFactor() {
		return minionSpeedFactor;
	}

	public int getMonsterCount() {
		return monsterCount;
	}

	public int getMinionCount() {
		return minionCount;
	}

	public int getDroneSpeed() {
		return droneSpeed;
	}
}
