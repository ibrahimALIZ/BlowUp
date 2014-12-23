package com.meda.blowup.objects;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.config.ccConfig;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccBlendFunc;

import com.meda.blowup.GameManager;
import com.meda.blowup.GameState;
import com.meda.blowup.prkit.PRFilledPolygon;
import com.meda.blowup.util.Constants;
import com.meda.blowup.util.Operations;

public class Ground {
	private final List<CGPoint> coordinates;
	private final PRFilledPolygon ground;
	private boolean updated;

	public Ground(CCTexture2D texture) {
		coordinates = new ArrayList<CGPoint>(100);
		initCoordinates();
		ground = new PRFilledPolygon();

		ccBlendFunc blend = new ccBlendFunc();
		blend.src = ccConfig.CC_BLEND_SRC;
		blend.dst = ccConfig.CC_BLEND_DST;
		ground.setBlendFunc(blend);
		ground.initWithPoints(coordinates, texture);
	}

	private void initCoordinates() {
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		CGPoint p = new CGPoint();
		p.x = 0 + Constants.WORLD_GAP;
		p.y = 0 + Constants.WORLD_GAP;
		coordinates.add(p);

		p = new CGPoint();
		p.x = winSize.width - Constants.WORLD_GAP;
		p.y = 0 + Constants.WORLD_GAP;
		coordinates.add(p);

		p = new CGPoint();
		p.x = winSize.width - Constants.WORLD_GAP;
		p.y = winSize.height - Constants.WORLD_GAP;
		coordinates.add(p);

		p = new CGPoint();
		p.x = 0 + Constants.WORLD_GAP;
		p.y = winSize.height - Constants.WORLD_GAP;
		coordinates.add(p);
	}

	public void mergeWith(List<CGPoint> borders, int ixStart, int ixEnd) {
		boolean CCW = Operations.testIfCCW(borders, ixStart, ixEnd);
		int n = coordinates.size();
		int m = borders.size();

		if (ixStart == ixEnd) {
			if (m < 3) {
				return;
			}

			if (CCW) {
				for (int i = 0, j = m - 1; i < m; i++, j--) {
					coordinates.add(ixStart + i + 1, borders.get(j));
				}
			} else {
				for (int i = 0; i < m; i++) {
					coordinates.add(ixStart + i + 1, borders.get(i));
				}
			}
			GameManager.getInstance().updateExperience(
					Operations.calculatePolygonArea(borders));
		} else {
			int iP, jP;
			if (ixStart < ixEnd) {
				iP = ixStart;
				jP = ixEnd;
			} else {
				jP = ixStart;
				iP = ixEnd;
			}
			List<CGPoint> mergedGroundVertices = new ArrayList<CGPoint>(n + m);
			List<CGPoint> mergedGroundVertices2 = new ArrayList<CGPoint>(n + m);
			// Divide ground into 2
			for (int i = 0; i < n; i++) {
				if (i <= iP)
					mergedGroundVertices.add(coordinates.get(i));
				else if (i > jP)
					mergedGroundVertices.add(coordinates.get(i));
				else
					mergedGroundVertices2.add(coordinates.get(i));
			}

			for (int i = 0, j = m - 1; i < m; i++, j--) {
				if (ixStart < ixEnd) {
					mergedGroundVertices.add(iP + 1, borders.get(j));
					mergedGroundVertices2.add(borders.get(j));
				} else {
					mergedGroundVertices.add(iP + 1, borders.get(i));
					mergedGroundVertices2.add(borders.get(i));
				}
			}

			int area1 = Operations.calculatePolygonArea(mergedGroundVertices);
			int area2 = Operations.calculatePolygonArea(mergedGroundVertices2);
			coordinates.clear();
			if (area1 > area2) {
				coordinates.addAll(mergedGroundVertices);
				GameManager.getInstance().updateExperience(area2);
			} else {
				coordinates.addAll(mergedGroundVertices2);
				GameManager.getInstance().updateExperience(area1);
			}
		}

		// Just to check no point repeats
		n = coordinates.size();
		CGPoint p1, p2;
		for (int i = 0; i < n; i++) {
			p1 = coordinates.get(i);
			p2 = coordinates.get((i + 1) % n);
			if (p1.x == p2.x && p1.y == p2.y) {
				coordinates.remove(i);
				break;
			}
		}
		// Log.v("Ground", "World:" + coordinates);

		ground.setPoints(coordinates);
		updated = true;
		GameManager.getInstance().setGameState(GameState.PLAYING);
	}

	public List<CGPoint> getCoordinates() {
		return coordinates;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void resetGround() {
		updated = false;
	}

	public PRFilledPolygon getGround() {
		return ground;
	}
}
