package com.meda.blowup.layers;

import org.cocos2d.opengl.CCTexture2D;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.meda.blowup.monsters.GreenMinion;
import com.meda.blowup.monsters.GreenMonster;
import com.meda.blowup.monsters.Monster;
import com.meda.blowup.util.Configuration;
import com.meda.blowup.util.Constants;

public class GreenMonsterLayer extends GameLayer {

	public GreenMonsterLayer(Configuration conf, CCTexture2D groundTexture) {
		super(groundTexture);

		setMonsterMaxSpeed(conf.getMonsterMaxSpeed());
		setMinionMaxSpeed(conf.getMinionMaxSpeed());
		setMonsterSpeedFactor(conf.getMonsterSpeedFactor());
		setMinionSpeedFactor(conf.getMinionSpeedFactor());
		setMonsterCount(conf.getMonsterCount());
		setMinionCount(conf.getMinionCount());

		createMonsters();
	}

	@Override
	public void createMonsters() {
		Monster monster;
		Body monsterBody;
		BodyDef bodyDef;
		CircleShape circle;
		FixtureDef fixtureDef;
		for (int i = 0; i < getMonsterCount(); i++) {
			monster = new GreenMonster();
			this.addChild(monster, 0, Constants.MONSTER_TAG + i);

			bodyDef = new BodyDef();
			bodyDef.type = BodyType.DYNAMIC;
			bodyDef.position.set(monster.getPosition().x / Constants.PTM_RATIO,
					monster.getPosition().y / Constants.PTM_RATIO);
			bodyDef.userData = monster;
			bodyDef.linearDamping = 0.f;
			bodyDef.angularDamping = 0.f;
			monsterBody = getWorld().createBody(bodyDef);

			circle = new CircleShape();
			circle.m_radius = 60f / Constants.PTM_RATIO * Constants.SCALE_FACTOR;
			fixtureDef = new FixtureDef();
			fixtureDef.shape = circle;
			fixtureDef.density = 1.0f;
			fixtureDef.friction = 0.f;
			fixtureDef.restitution = 1.f;
			monsterBody.createFixture(fixtureDef);
		}
		
		for (int i = 0; i < getMinionCount(); i++) {
			monster = new GreenMinion();
			this.addChild(monster, 0, Constants.MINION_TAG + i);

			bodyDef = new BodyDef();
			bodyDef.type = BodyType.DYNAMIC;
			bodyDef.position.set(monster.getPosition().x / Constants.PTM_RATIO,
					monster.getPosition().y / Constants.PTM_RATIO);
			bodyDef.userData = monster;
			bodyDef.linearDamping = 0.f;
			bodyDef.angularDamping = 0.f;
			monsterBody = getWorld().createBody(bodyDef);

			circle = new CircleShape();
			circle.m_radius = 25f / Constants.PTM_RATIO * Constants.SCALE_FACTOR;
			fixtureDef = new FixtureDef();
			fixtureDef.shape = circle;
			fixtureDef.density = 1.0f;
			fixtureDef.friction = 0.f;
			fixtureDef.restitution = 1.f;
			monsterBody.createFixture(fixtureDef);
		}
	}
}
