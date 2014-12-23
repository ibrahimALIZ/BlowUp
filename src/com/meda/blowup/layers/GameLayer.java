package com.meda.blowup.layers;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.meda.blowup.GameManager;
import com.meda.blowup.GameState;
import com.meda.blowup.R;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.effects.DroneSmoke;
import com.meda.blowup.effects.Explosion;
import com.meda.blowup.monsters.Monster;
import com.meda.blowup.monsters.MonsterState;
import com.meda.blowup.objects.Drone;
import com.meda.blowup.objects.Ground;
import com.meda.blowup.objects.Mine;
import com.meda.blowup.powerups.PowerUp;
import com.meda.blowup.util.Constants;
import com.meda.blowup.util.MyContactListener;
import com.meda.blowup.util.Operations;

public abstract class GameLayer extends CCLayer {
	private int viterations = 8;
	private int piterations = 1;

	private World world;
	private Ground ground;
	private Drone drone;
	private DroneSmoke droneSmoke;
	private Body groundBody;

	private int monsterMaxSpeed;
	private int minionMaxSpeed;
	private float monsterSpeedFactor;
	private float minionSpeedFactor;
	private int monsterCount;
	private int minionCount;

	private int bossPositionIndex;
	private CGPoint position[];
	private int oscillationCountdown = 20;

	private boolean removeChanceScheduled;

	public GameLayer(CCTexture2D groundTexture) {
		Vec2 gravity = new Vec2(0.f, 0.f);
		boolean doSleep = true;
		world = new World(gravity, doSleep);
		world.setContactListener(new MyContactListener());
		ground = new Ground(groundTexture);
		this.addChild(ground.getGround(), Constants.GROUND_Z);

		resetB2World();

		CGSize winSize = CCDirector.sharedDirector().displaySize();
		drone = new Drone();
		drone.setPosition(winSize.width / 2, winSize.height / 2);
		GameManager.getInstance().setDronePosition(drone.getPosition());
		this.addChild(drone, Constants.DRONE_Z, Constants.DRONE_TAG);

		droneSmoke = new DroneSmoke();
		droneSmoke.setPosition(drone.getPosition().x,
				drone.getPosition().y - 40);
		
		this.addChild(droneSmoke, Constants.DRONE_Z);

		showChapterInfo();

		position = new CGPoint[5];
		for (int i = 0; i < 5; i++) {
			position[i] = new CGPoint();
		}

		this.scheduleUpdate();
		this.schedule("tick");
		this.schedule("removeChapterInfo", 1.0f);
	}

	public abstract void createMonsters();

	private void resetB2World() {
		if (groundBody != null) {
			world.destroyBody(groundBody);
		}
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(0, 0);
		bodyDef.type = BodyType.STATIC;
		groundBody = world.createBody(bodyDef);

		FixtureDef fixtureDef;
		PolygonShape shape;
		List<CGPoint> coordinates = ground.getCoordinates();
		int n = coordinates.size();
		CGPoint p1, p2;
		for (int i = 0; i < n; i++) {
			fixtureDef = new FixtureDef();
			shape = new PolygonShape();
			p1 = coordinates.get(i);
			p2 = coordinates.get((i + 1) % n);
			shape.setAsEdge(new Vec2(p1.x / Constants.PTM_RATIO, p1.y
					/ Constants.PTM_RATIO), new Vec2(
					p2.x / Constants.PTM_RATIO, p2.y / Constants.PTM_RATIO));
			fixtureDef.shape = shape;
			groundBody.createFixture(fixtureDef);
		}
	}

	public void update(float dt) {
		GameManager gameManager = GameManager.getInstance();
		GameState gameState = gameManager.getGameState();
		if (gameState == GameState.ALL_MONSTERS_DEAD
				|| gameState == GameState.BOSS_MONSTER_DEAD
				|| gameState == GameState.PLAYER_DEAD) {
			unscheduleAllSelectors();
			stopAllActions();
			gameManager.endGame();
			return;
		} else if (gameManager.getPercentageCleared() > Constants.PERCENTAGE_WIN_RATIO) {
			for (Monster m : getMonsterList()) {
				m.die();
			}
		}

		CGPoint pos1 = drone.getPosition();
		CGPoint pos2 = gameManager.getDronePosition();
		int k = 35;
		if (!CGPoint.equalToPoint(pos1, pos2)) {
			drone.move(pos2, ground.getCoordinates());

			CGPoint p = drone.getPosition();
			float r = drone.getRotation();
			if (r == 0) {
				droneSmoke.setAngle(-90);
				droneSmoke.setPosition(CGPoint.ccp(p.x, p.y - k));
			} else if (r == 90) {
				droneSmoke.setPosition(CGPoint.ccp(p.x - k, p.y));
				droneSmoke.setAngle(180);
			} else if (r == -90) {
				droneSmoke.setPosition(CGPoint.ccp(p.x + k, p.y));
				droneSmoke.setAngle(0);
			} else if (r == 180) {
				droneSmoke.setPosition(CGPoint.ccp(p.x, p.y + k));
				droneSmoke.setAngle(90);
			} else {
			}
		}

		if (drone.hasFinishedDrawing()) {
			// Log.v("GameLayer", "Coordinates: " + drone.getCoordinates());
			AudioManager.getInstance().playEffect(R.raw.merge);
			ground.mergeWith(drone.getCoordinates(), drone.getIXStart(),
					drone.getIXEnd());
			// Log.v("GameLayer", "Ground Coordinates: " +
			// ground.getCoordinates());
			drone.resetDrawing();
			cleanMines();
		}

		if (!gameManager.isDrawing()) {
			drone.resetDrawing();
			cleanMines();
		}

		List<Monster> monsterList = getMonsterList();
		if (monsterList.isEmpty()) {
			gameManager.setGameState(GameState.ALL_MONSTERS_DEAD);
			AudioManager.getInstance().playEffect(R.raw.select);
		} else {
			if (!drone.isImmune() && !drone.isShielded()
					&& !removeChanceScheduled) {
				for (Monster m : monsterList) {
					List<CGPoint> bb = Operations.getBoundingBoxOf(m);
					if (Operations.isInsidePolygon(bb, drone.getPosition())) {
						drone.burn();
						this.schedule("removeChance", 0.4f);
						removeChanceScheduled = true;
						break;
					}
				}
				CCNode mineNode = this.getChild(Constants.MINE_TAG);
				if (mineNode != null
						&& CGRect.containsPoint(mineNode.getBoundingBox(),
								drone.getPosition())) {
					drone.burn();
					this.schedule("removeChance", 0.2f);
					removeChanceScheduled = true;
				}
			}
		}

		if (ground.isUpdated()) {
			List<CGPoint> vList = ground.getCoordinates();
			for (Monster m : monsterList) {
				if (!Operations.isInsidePolygon(vList, m.getPosition())) {
					m.die();
				}
			}
			resetB2World();
			ground.resetGround();
		}

		List<PowerUp> pupList = getPowerUpList();
		for (PowerUp p : pupList) {
			if (CGRect.containsPoint(p.getBoundingBox(), drone.getPosition())) {
				AudioManager.getInstance().playEffect(R.raw.pan);
				drone.powerUp(p);
				this.removeChild(p, true);
				gameManager.consumePowerup();
			}
		}

		CCNode boss = this.getChild(Constants.MONSTER_TAG);
		if (gameManager.isDrawing() && boss != null) {
			Mine mine = (Mine) this.getChild(Constants.MINE_TAG);
			List<CGPoint> pointList = drone.getCoordinates();
			int n = pointList.size();
			if (mine == null && n > 2) {
				CGPoint p1, p2;
				for (int i = 0; i < n - 3; i++) {
					p1 = pointList.get(i);
					p2 = pointList.get(i + 1);
					if (Operations.rectangleIntersectsLine(
							boss.getBoundingBox(), p1.x, p1.y, p2.x, p2.y)) {
						mine = new Mine();
						mine.setPosition(p2);
						mine.follow(drone, p2);
						this.addChild(mine, 100, Constants.MINE_TAG);
						break;
					}
				}
			}
		}
	}

	public void tick(float dt) {
		if (GameManager.getInstance().getGameState() != GameState.PLAYING)
			return;
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		bossPositionIndex %= 5;
		world.step(dt, viterations, piterations);

		Body mBody = world.getBodyList();
		while (mBody != null) {
			Monster actor = (Monster) mBody.getUserData();
			if (actor != null) {
				Vec2 vec = mBody.getPosition();
				float rot = mBody.getAngle() * 57f; // convert radians to
													// degrees
				actor.setPosition(vec.x * Constants.PTM_RATIO, vec.y
						* Constants.PTM_RATIO);
				actor.setRotation(rot);

				if (actor.isDead()) {
					world.destroyBody(mBody);
					// Explosion effect
					CCParticleSystem explosion;
					if (actor.isBoss()) {
						explosion = new Explosion(500);
					} else {
						explosion = new Explosion(100);
						explosion.setAngleVar(45.0f);
					}
					explosion.setPosition(actor.getPosition());
					AudioManager.getInstance().playEffect(R.raw.boom02);
					this.addChild(explosion);
					if (actor.isBoss()) {
						GameManager.getInstance().setGameState(
								GameState.BOSS_MONSTER_DEAD);
					}
					this.removeChild(actor, true);
				}

				// Oscillation
				if (actor.isBoss()) {
					CGPoint pos = actor.getPosition();
					CGPoint temp;
					boolean isOscillating = false;
					for (int i = 0; i < 5; i++) {
						temp = position[i];
						if (pos.x == temp.x && pos.y == temp.y) {
							isOscillating = true;
							break;
						}
					}
					position[bossPositionIndex++] = pos;

					if (isOscillating) {
						oscillationCountdown--;
						actor.setState(MonsterState.UNHAPPY);
					} else {
						oscillationCountdown = Constants.OSCILLATION_COUNTDOWN;
					}

					if (oscillationCountdown == 0) {
						Fixture f = mBody.getFixtureList();
						mBody.destroyFixture(f);

						CircleShape circle = new CircleShape();
						circle.m_radius = 30f / Constants.PTM_RATIO;

						FixtureDef fixtureDef = new FixtureDef();
						fixtureDef.shape = circle;
						fixtureDef.density = 1.0f;
						fixtureDef.friction = 0.f;
						fixtureDef.restitution = 1.f;
						mBody.createFixture(fixtureDef);

						actor.setScale(0.5f);
						oscillationCountdown = Constants.OSCILLATION_COUNTDOWN;
						actor.setState(MonsterState.CRAZY);
					}
				}

				// Speed control for monsters
				Vec2 velocity = mBody.getLinearVelocity();
				float speed = velocity.length();
				if (speed < 1.f) {
					actor.setState(MonsterState.ALIVE);
				} else if (speed < 2.f) {
					actor.setState(MonsterState.SLEEPY);
				} else if (speed < 3.f) {
					actor.setState(MonsterState.SLOW);
				} else if (speed < 5.f) {
					actor.setState(MonsterState.HAPPY);
				} else if (speed < 7.f) {
					actor.setState(MonsterState.UNHAPPY);
				} else if (speed < 8.5f) {
					actor.setState(MonsterState.RUNNING);
				} else if (speed < 10.f) {
					actor.setState(MonsterState.FAST);
				} else if (speed < 12.f) {
					actor.setState(MonsterState.CHARGING);
				} else if (speed < 15.f) {
					actor.setState(MonsterState.CRAZY);
				} else {
					actor.setState(MonsterState.GONE);
				}

				float speedLimit = monsterMaxSpeed;
				if (!actor.isBoss())
					speedLimit = minionMaxSpeed;

				if (speed > speedLimit) {
					mBody.setLinearDamping(0.5f);
				} else if (speed < speedLimit) {
					mBody.setLinearDamping(0.0f);
				}

				// monster following drone behaviour
				CGPoint p = drone.getPosition();
				float speedFactor = monsterSpeedFactor;
				if (!actor.isBoss())
					speedFactor = minionSpeedFactor;

				float xfactor = p.x < actor.getPosition().x ? -speedFactor
						: speedFactor;
				float yfactor = p.y < actor.getPosition().y ? -speedFactor
						: speedFactor;

				if (drone.isImmune()) {
					xfactor = -yfactor;
					yfactor = -xfactor;
				}

				mBody.applyLinearImpulse(new Vec2(
						xfactor * p.x / winSize.width, yfactor * p.y
								/ winSize.height), mBody.getWorldCenter());

			}
			mBody = mBody.getNext();
		}
	}

	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
		List<CGPoint> coordinates = ground.getCoordinates();
		int n = coordinates.size();
		CGPoint p1, p2;
		// for (int i = 0; i < n; i++) {
		// p1 = coordinates.get(i);
		// p2 = coordinates.get((i + 1) % n);
		// CCDrawingPrimitives.ccDrawLine(gl, p1, p2);
		// }
		// gl.glPointSize(6);
		// for (int i = 0; i < n; i++) {
		// p1 = coordinates.get(i);
		// CCDrawingPrimitives.ccDrawPoint(gl, p1);
		// }

		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		gl.glLineWidth(3.0f);
		int m = drone.getCoordinates().size();
		if (m > 1) {
			p2 = new CGPoint();
			for (int i = 0; i < m - 1; i++) {
				p1 = drone.getCoordinates().get(i);
				p2 = drone.getCoordinates().get(i + 1);
				CCDrawingPrimitives.ccDrawLine(gl, p1, p2);
			}
		}
		// for (int i = 0; i < m; i++) {
		// p1 = drone.getCoordinates().get(i);
		// CCDrawingPrimitives.ccDrawPoint(gl, p1);
		// }

		// gl.glEnable(GL10.GL_BLEND);
		reorderChild(ground.getGround(), -1);
	}

	private List<Monster> getMonsterList() {
		List<Monster> list = new ArrayList<Monster>(monsterCount + minionCount);
		List<CCNode> nodeList = this.getChildren();
		int tag;
		for (CCNode node : nodeList) {
			tag = node.getTag();
			if ((tag >= Constants.MONSTER_TAG && tag < Constants.MONSTER_TAG
					+ monsterCount)
					|| (tag >= Constants.MINION_TAG && tag < Constants.MINION_TAG
							+ minionCount)) {
				list.add((Monster) node);
			}
		}
		// Log.v("GameLayer", "Number of monsters: " + list.size());
		return list;
	}

	private List<PowerUp> getPowerUpList() {
		List<PowerUp> list = new ArrayList<PowerUp>(minionCount);
		List<CCNode> nodeList = this.getChildren();
		int tag;
		for (CCNode node : nodeList) {
			tag = node.getTag();
			if (tag >= Constants.POWERUP_TAG
					&& tag < Constants.POWERUP_TAG
							+ GameManager.getInstance().getNumberOfPowerUps()) {
				list.add((PowerUp) node);
			}
		}
		// Log.v("GameLayer", "Number of powerups: " + list.size());
		return list;
	}

	private void showChapterInfo() {
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		String message = "Stage "
				+ (GameManager.getInstance().getLevelStage() + 1);
		CCBitmapFontAtlas x = CCBitmapFontAtlas.bitmapFontAtlas(message,
				"f1.fnt");
		x.setPosition(CGPoint.ccp(winSize.width / 2, winSize.height * 0.6f));
		x.setColor(Constants.yellow);
		this.addChild(x, 1000, 15);

		CCScaleTo scale = CCScaleTo.action(1.0f, 2.0f);
		x.runAction(scale);
	}

	public void removeChapterInfo(float dt) {
		this.removeChild(15, true);
		unschedule("removeChapterInfo");
	}

	public void removeChance(float dt) {
		GameManager.getInstance().removeChance();
		if (GameManager.getInstance().isDroneDead()) {
			CCParticleSystem explosion = new Explosion(200);
			explosion.setPosition(drone.getPosition());
			this.addChild(explosion);
			AudioManager.getInstance().playEffect(R.raw.gameover);
			drone.resetDrawing();
			drone.removeFromParentAndCleanup(true);
			droneSmoke.removeFromParentAndCleanup(true);
			GameManager.getInstance().setGameState(GameState.PLAYER_DEAD);
		}

		switch (GameManager.getInstance().getDroneChance()) {
		case 2:
			droneSmoke.setStartSize(12.0f);
			droneSmoke.setStartSizeVar(8.0f);
			break;
		case 1:
			droneSmoke.setStartSize(15.0f);
			droneSmoke.setStartSizeVar(10.0f);
			break;
		default:
			droneSmoke.setStartSize(8.0f);
			droneSmoke.setStartSizeVar(8.0f);
			break;
		}

		removeChanceScheduled = false;
		unschedule("removeChance");
	}

	public void cleanMines() {
		Mine mine = (Mine) this.getChild(Constants.MINE_TAG);
		if (mine != null) {
			this.removeChild(Constants.MINE_TAG, true);
		}
	}

	public int getMonsterMaxSpeed() {
		return monsterMaxSpeed;
	}

	public void setMonsterMaxSpeed(int monsterMaxSpeed) {
		this.monsterMaxSpeed = monsterMaxSpeed;
	}

	public int getMinionMaxSpeed() {
		return minionMaxSpeed;
	}

	public void setMinionMaxSpeed(int minionMaxSpeed) {
		this.minionMaxSpeed = minionMaxSpeed;
	}

	public float getMonsterSpeedFactor() {
		return monsterSpeedFactor;
	}

	public void setMonsterSpeedFactor(float monsterSpeedFactor) {
		this.monsterSpeedFactor = monsterSpeedFactor;
	}

	public float getMinionSpeedFactor() {
		return minionSpeedFactor;
	}

	public void setMinionSpeedFactor(float minionSpeedFactor) {
		this.minionSpeedFactor = minionSpeedFactor;
	}

	public int getMonsterCount() {
		return monsterCount;
	}

	public void setMonsterCount(int monsterCount) {
		this.monsterCount = monsterCount;
	}

	public int getMinionCount() {
		return minionCount;
	}

	protected World getWorld() {
		return world;
	}

	public void setMinionCount(int minionCount) {
		this.minionCount = minionCount;
	}

	public boolean isWaiting() {
		return false;
	}
}
