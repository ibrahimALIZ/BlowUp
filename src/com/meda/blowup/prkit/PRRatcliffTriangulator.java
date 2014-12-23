package com.meda.blowup.prkit;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.types.CGPoint;

public class PRRatcliffTriangulator implements ITriangulator {
	@Override
	public List<CGPoint> triangulateVertices(List<CGPoint> vertices) {
		List<Vector2D> inputPointsForTriangulation = new ArrayList<Vector2D>(
				vertices.size());
		for (CGPoint cgPoint : vertices) {
			inputPointsForTriangulation.add(new Vector2D(cgPoint.x, cgPoint.y));
		}

		List<Vector2D> triangulatedPoints = Triangulate
				.Process(inputPointsForTriangulation);

		int triangulatedPointCount = triangulatedPoints.size();
		List<CGPoint> triangulatedPointsArray = new ArrayList<CGPoint>(
				triangulatedPointCount);
		CGPoint p;
		Vector2D v;
		for (int i = 0; i < triangulatedPointCount; i++) {
			p = new CGPoint();
			v = triangulatedPoints.get(i);
			p.set(v.getX(), v.getY());
			triangulatedPointsArray.add(p);
		}
		return triangulatedPointsArray;
	}
}
