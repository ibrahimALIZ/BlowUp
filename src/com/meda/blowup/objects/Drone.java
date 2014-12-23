package com.meda.blowup.objects;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;

import com.meda.blowup.GameManager;
import com.meda.blowup.GameState;
import com.meda.blowup.R;
import com.meda.blowup.audio.AudioManager;
import com.meda.blowup.layers.GameLayer;
import com.meda.blowup.powerups.PowerUp;
import com.meda.blowup.util.Constants;
import com.meda.blowup.util.Operations;

public class Drone extends CCSprite {
	private final List<CGPoint> coordinates;
	private int ixStart, ixEnd;
	private DRAWING drawingState;
	private CGPoint intersectionPoint;
	private boolean shielded;
	private boolean immune;
	private boolean burning = false;

	private PowerUp pup;
	private int droneXSpeed;

	private enum DRAWING {
		NOT_STARTED, CAN_START, STARTED, FINISHED
	}

	public boolean hasFinishedDrawing() {
		return drawingState == DRAWING.FINISHED;
	}

	public Drone() {
		coordinates = new ArrayList<CGPoint>(10);
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("drone64shield.png"));
		immune = true;
		setScale(Constants.SCALE_FACTOR * .8f);

		// schedule("removeShield", 3f);
		scheduleUpdate();

		drawingState = DRAWING.NOT_STARTED;
		ixStart = ixEnd = -1;
		intersectionPoint = new CGPoint();
	}

	public List<CGPoint> getCoordinates() {
		return coordinates;
	}

	public void removeShield(float dt) {
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("drone64.png"));
		shielded = false;
		unschedule("removeShield");
	}

	public void move(CGPoint p, List<CGPoint> groundCoordinates) {
		CGPoint pp = getPosition();
		float r = getRotation();
		if (p.x == pp.x && p.y == pp.y)
			return;
		if (p.x == pp.x) {
			if (p.y < pp.y) {
				setRotation(180.0f);
			} else {
				setRotation(0.0f);
			}
		} else {
			if (p.x < pp.x) {
				setRotation(-90.0f);
			} else {
				setRotation(90.0f);
			}
		}
		setPosition(p);

		if (GameManager.getInstance().isDrawing()) {
			int m = groundCoordinates.size();
			int ix = -1;
			CGPoint pp1, pp2;
			for (int i = 0; i < m; i++) {
				pp1 = groundCoordinates.get(i);
				pp2 = groundCoordinates.get((i + 1) % m);

				if (Operations.linesIntersect(p, pp, pp1, pp2)) {
					float x = Math.abs(getRotation());
					CGPoint pprime = new CGPoint();
					if (x > 0 && x < 180) {
						pprime.x = pp1.x;
						pprime.y = p.y;
					} else {
						pprime.x = p.x;
						pprime.y = pp1.y;
					}

					if (intersectionPoint.x == pprime.x
							&& intersectionPoint.y == pprime.y) {
						// just ignore this case
						return;
					} else {
						intersectionPoint.set(pprime.x, pprime.y);
					}
					ix = i;
					break;
				}
			}

			switch (drawingState) {
			case NOT_STARTED:
				if (ix >= 0) {
					ixStart = ix;
					drawingState = DRAWING.CAN_START;
				}
				break;
			case CAN_START:
				if (Operations.isInsidePolygon(groundCoordinates, p)) {
					CGPoint q = new CGPoint();
					q.set(intersectionPoint.x, intersectionPoint.y);
					coordinates.add(q);
					coordinates.add(p);
					drawingState = DRAWING.STARTED;
					AudioManager.getInstance().playSound(R.raw.engine, true);
				} else {
					ixStart = -1;
					intersectionPoint.set(-1, -1);
					drawingState = DRAWING.NOT_STARTED;
				}
				break;
			case STARTED:
				if (ix >= 0) {
					ixEnd = ix;
					coordinates.remove(coordinates.size() - 1);
					CGPoint q = new CGPoint();
					q.set(intersectionPoint.x, intersectionPoint.y);
					coordinates.add(q);
					drawingState = DRAWING.FINISHED;
				} else {
					if (Operations.isInsidePolygon(groundCoordinates, p)) {
						if (coordinates.size() > 0)
							coordinates.remove(coordinates.size() - 1);
						if (r != getRotation()) {
							float angleDiff = Math.abs(r - getRotation());
							if ((angleDiff > 0 && angleDiff < 180)
									|| angleDiff > 269) {
								coordinates.add(pp);
							}
						}
						coordinates.add(p);
					} else {
						resetDrawing();
					}
				}

				break;
			case FINISHED:
				// done exclusively
				// resetDrawing();
				break;

			default:
				break;
			}

			// update for intersection
			int n = coordinates.size();
			if (n > 4) {
				int breakPoint = -1;
				CGPoint p1, p2, ixPoint = new CGPoint();
				p1 = coordinates.get(n - 1);
				p2 = coordinates.get(n - 2);

				for (int i = 0; i < n; i++) {
					pp1 = coordinates.get(i);
					pp2 = coordinates.get(i + 1);
					if (Operations.linesIntersect(p1, p2, pp1, pp2)) {
						breakPoint = i;
						if (p1.x == p2.x) {
							ixPoint.set(p1.x, pp1.y);
						} else {
							ixPoint.set(pp1.x, p1.y);
						}

						break;
					}
				}
				if (breakPoint >= 0) {
					for (int i = n - 1; i > breakPoint; i--) {
						coordinates.remove(i);
					}
					coordinates.add(ixPoint);
					coordinates.add(p);
				}
			}
		}
	}

	public void burn() {
		burning = true;
		AudioManager.getInstance().playEffect(R.raw.burn);
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("drone64red.png"));
		schedule("undoBurn", 0.4f);
	}

	public void undoBurn(float dt) {
		burning = false;
		setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
				.getSpriteFrame("drone64.png"));
		unschedule("undoBurn");
	}

	public void powerUp(PowerUp p) {
		float mosf;
		if (pup != null)
			return;
		pup = p;
		GameLayer parent = (GameLayer) getParent();
		switch (pup.getType()) {
		case FREEZE_MONSTERS:
			GameManager.getInstance().setGameState(GameState.PAUSED);
			break;
		case SHIELD:
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("drone64shield.png"));
			shielded = true;
			break;
		case SLOWDOWN_DRONE:
			droneXSpeed = GameManager.getInstance().getDroneSpeed();
			GameManager.getInstance().setDroneSpeed(2 * Constants.SP_F);
			break;
		case SLOWDOWN_MONSTERS:
			mosf = parent.getMonsterSpeedFactor();
			parent.setMonsterSpeedFactor(mosf / 5.f);
			mosf = parent.getMinionSpeedFactor();
			parent.setMinionSpeedFactor(mosf / 5.f);
			break;
		case SPEEDUP_DRONE:
			droneXSpeed = GameManager.getInstance().getDroneSpeed();
			GameManager.getInstance().setDroneSpeed(6 * Constants.SP_F);
			break;
		case SPEEDUP_MONSTERS:
			mosf = parent.getMonsterSpeedFactor();
			parent.setMonsterSpeedFactor(mosf * 2.f);
			mosf = parent.getMonsterSpeedFactor();
			parent.setMinionSpeedFactor(mosf * 2.f);
			parent.setMonsterMaxSpeed(parent.getMonsterMaxSpeed() * 2);
			parent.setMinionMaxSpeed(parent.getMinionMaxSpeed() * 2);
			break;
		case NEW_CHANCE:
			GameManager.getInstance().addChance();
			break;
		default:
			break;
		}
		schedule("undoPowerUp", 5f);
	}

	public void undoPowerUp(float dt) {
		float mosf;
		if (pup == null)
			return;
		GameLayer parent = (GameLayer) getParent();
		switch (pup.getType()) {
		case FREEZE_MONSTERS:
			GameManager.getInstance().setGameState(GameState.PLAYING);
			break;
		case SHIELD:
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("drone64.png"));
			shielded = false;
			break;
		case SLOWDOWN_DRONE:
			GameManager.getInstance().setDroneSpeed(droneXSpeed);
			break;
		case SLOWDOWN_MONSTERS:
			mosf = parent.getMonsterSpeedFactor();
			parent.setMonsterSpeedFactor(mosf * 5.f);
			mosf = parent.getMinionSpeedFactor();
			parent.setMonsterSpeedFactor(mosf * 5.f);
			break;
		case SPEEDUP_DRONE:
			GameManager.getInstance().setDroneSpeed(droneXSpeed);
			break;
		case SPEEDUP_MONSTERS:
			mosf = parent.getMonsterSpeedFactor();
			parent.setMonsterSpeedFactor(mosf / 2.f);
			mosf = parent.getMinionSpeedFactor();
			parent.setMonsterSpeedFactor(mosf / 2.f);
			parent.setMonsterMaxSpeed(parent.getMonsterMaxSpeed() / 2);
			parent.setMinionMaxSpeed(parent.getMinionMaxSpeed() / 2);
			break;
		case NEW_CHANCE:
			break;
		default:
			break;
		}
		unschedule("undoPowerUp");
	}

	public void resetDrawing() {
		ixStart = -1;
		ixEnd = -1;
		intersectionPoint.set(-1, -1);
		drawingState = DRAWING.NOT_STARTED;
		coordinates.clear();
		AudioManager.getInstance().stopSound();
	}

	public void update(float dt) {
		if (burning || shielded)
			return;
		boolean isDrawing = GameManager.getInstance().isDrawing();
		if (isDrawing) {
			immune = false;
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("drone64.png"));
		} else {
			immune = true;
			setDisplayFrame(CCSpriteFrameCache.sharedSpriteFrameCache()
					.getSpriteFrame("drone64shield.png"));
		}
	}

	public int getIXStart() {
		return ixStart;
	}

	public int getIXEnd() {
		return ixEnd;
	}

	public boolean isShielded() {
		return shielded;
	}

	public void setShielded(boolean shielded) {
		this.shielded = shielded;
	}

	public boolean isImmune() {
		return immune;
	}

	public void setImmune(boolean immune) {
		this.immune = immune;
	}
}
