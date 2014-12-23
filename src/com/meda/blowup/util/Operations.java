package com.meda.blowup.util;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

public final class Operations {

	public static int calculatePolygonArea(List<CGPoint> borders) {
		int n = borders.size();
		int s1 = 0, s2 = 0;
		CGPoint p1, p2;
		for (int i = 0; i < n; i++) {
			p1 = borders.get(i);
			p2 = borders.get((i + 1) % n);
			s1 += p1.x * p2.y;
			s2 += p1.y * p2.x;
		}
		return Math.abs((s1 - s2) / 2);
	}

	public static boolean testIfCCW(List<CGPoint> borders, int ix, int jx) {
		int n = borders.size();
		int sum = 0;
		CGPoint p1, p2;
		for (int i = 0; i < n; i++) {
			p1 = borders.get(i);
			p2 = borders.get((i + 1) % n);

			sum += (p2.x - p1.x) * (p2.y + p1.y);
		}

		boolean CCW;
		if (sum < 0) {
			CCW = true;
		} else if (sum > 0) {
			CCW = false;
		} else {
			if (ix > jx) {
				CCW = true;
			} else {
				CCW = false;
			}
		}
		return CCW;
	}

	public static boolean isInsidePolygon(List<CGPoint> vertexList, CGPoint p) {
		int n = vertexList.size();
		int i, j;
		boolean c = false;
		CGPoint pi, pj;
		for (i = 0, j = n - 1; i < n; j = i++) {
			pi = vertexList.get(i);
			pj = vertexList.get(j);

			if (((pi.y > p.y) != (pj.y > p.y))
					&& (p.x < (pj.x - pi.x) * (p.y - pi.y) / (pj.y - pi.y)
							+ pi.x))
				c = !c;
		}
		return c;
	}

	public static boolean linesIntersect(CGPoint p1, CGPoint p2, CGPoint pp1,
			CGPoint pp2) {
		return linesIntersect(p1.x, p1.y, p2.x, p2.y, pp1.x, pp1.y, pp2.x,
				pp2.y);
	}

	/**
	 * Tests if the line segment from {@code (x1,y1)} to {@code (x2,y2)}
	 * intersects the line segment from {@code (x3,y3)} to {@code (x4,y4)}.
	 * 
	 * @param x1
	 *            the X coordinate of the start point of the first specified
	 *            line segment
	 * @param y1
	 *            the Y coordinate of the start point of the first specified
	 *            line segment
	 * @param x2
	 *            the X coordinate of the end point of the first specified line
	 *            segment
	 * @param y2
	 *            the Y coordinate of the end point of the first specified line
	 *            segment
	 * @param x3
	 *            the X coordinate of the start point of the second specified
	 *            line segment
	 * @param y3
	 *            the Y coordinate of the start point of the second specified
	 *            line segment
	 * @param x4
	 *            the X coordinate of the end point of the second specified line
	 *            segment
	 * @param y4
	 *            the Y coordinate of the end point of the second specified line
	 *            segment
	 * @return <code>true</code> if the first specified line segment and the
	 *         second specified line segment intersect each other;
	 *         <code>false</code> otherwise.
	 * @since 1.2
	 */
	public static boolean linesIntersect(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4) {
		return ((relativeCCW(x1, y1, x2, y2, x3, y3)
				* relativeCCW(x1, y1, x2, y2, x4, y4) <= 0) && (relativeCCW(x3,
				y3, x4, y4, x1, y1) * relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
	}

	/**
	 * Returns an indicator of where the specified point {@code (px,py)} lies
	 * with respect to the line segment from {@code (x1,y1)} to {@code (x2,y2)}.
	 * The return value can be either 1, -1, or 0 and indicates in which
	 * direction the specified line must pivot around its first end point,
	 * {@code (x1,y1)}, in order to point at the specified point {@code (px,py)}
	 * .
	 * <p>
	 * A return value of 1 indicates that the line segment must turn in the
	 * direction that takes the positive X axis towards the negative Y axis. In
	 * the default coordinate system used by Java 2D, this direction is
	 * counterclockwise.
	 * <p>
	 * A return value of -1 indicates that the line segment must turn in the
	 * direction that takes the positive X axis towards the positive Y axis. In
	 * the default coordinate system, this direction is clockwise.
	 * <p>
	 * A return value of 0 indicates that the point lies exactly on the line
	 * segment. Note that an indicator value of 0 is rare and not useful for
	 * determining colinearity because of floating point rounding issues.
	 * <p>
	 * If the point is colinear with the line segment, but not between the end
	 * points, then the value will be -1 if the point lies "beyond
	 * {@code (x1,y1)}" or 1 if the point lies "beyond {@code (x2,y2)}".
	 * 
	 * @param x1
	 *            the X coordinate of the start point of the specified line
	 *            segment
	 * @param y1
	 *            the Y coordinate of the start point of the specified line
	 *            segment
	 * @param x2
	 *            the X coordinate of the end point of the specified line
	 *            segment
	 * @param y2
	 *            the Y coordinate of the end point of the specified line
	 *            segment
	 * @param px
	 *            the X coordinate of the specified point to be compared with
	 *            the specified line segment
	 * @param py
	 *            the Y coordinate of the specified point to be compared with
	 *            the specified line segment
	 * @return an integer that indicates the position of the third specified
	 *         coordinates with respect to the line segment formed by the first
	 *         two specified coordinates.
	 * @since 1.2
	 */
	public static int relativeCCW(double x1, double y1, double x2, double y2,
			double px, double py) {
		x2 -= x1;
		y2 -= y1;
		px -= x1;
		py -= y1;
		double ccw = px * y2 - py * x2;
		if (ccw == 0.0) {
			// The point is colinear, classify based on which side of
			// the segment the point falls on. We can calculate a
			// relative value using the projection of px,py onto the
			// segment - a negative value indicates the point projects
			// outside of the segment in the direction of the particular
			// endpoint used as the origin for the projection.
			ccw = px * x2 + py * y2;
			if (ccw > 0.0) {
				// Reverse the projection to be relative to the original x2,y2
				// x2 and y2 are simply negated.
				// px and py need to have (x2 - x1) or (y2 - y1) subtracted
				// from them (based on the original values)
				// Since we really want to get a positive answer when the
				// point is "beyond (x2,y2)", then we want to calculate
				// the inverse anyway - thus we leave x2 & y2 negated.
				px -= x2;
				py -= y2;
				ccw = px * x2 + py * y2;
				if (ccw < 0.0) {
					ccw = 0.0;
				}
			}
		}
		return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
	}

	public static List<CGPoint> getBoundingBoxOf(CCSprite sprite) {
		CGPoint p = sprite.getPosition();
		CGSize s = sprite.getBoundingBox().size;
		float ccContentScaleFactor = CCDirector.sharedDirector()
				.getContentScaleFactor();
		int segs = 20;
		List<CGPoint> box = new ArrayList<CGPoint>(segs);
		float a = 30.f;
		int r = (int) (s.width / 2.f);
		float coef = 2.0f * (float) Math.PI / segs;

		for (int i = 0; i <= segs; i++) {
			float rads = i * coef;
			float j = (float) (r * Math.cos(rads + a)) + p.x;
			float k = (float) (r * Math.sin(rads + a)) + p.y;
			box.add(CGPoint.ccp(j * ccContentScaleFactor, k
					* ccContentScaleFactor));
		}
		return box;
	}

	/**
	 * The bitmask that indicates that a point lies to the left of this
	 * <code>Rectangle2D</code>.
	 * 
	 * @since 1.2
	 */
	private static final int OUT_LEFT = 1;

	/**
	 * The bitmask that indicates that a point lies above this
	 * <code>Rectangle2D</code>.
	 * 
	 * @since 1.2
	 */
	private static final int OUT_TOP = 2;

	/**
	 * The bitmask that indicates that a point lies to the right of this
	 * <code>Rectangle2D</code>.
	 * 
	 * @since 1.2
	 */
	private static final int OUT_RIGHT = 4;

	/**
	 * The bitmask that indicates that a point lies below this
	 * <code>Rectangle2D</code>.
	 * 
	 * @since 1.2
	 */
	private static final int OUT_BOTTOM = 8;

	private static int outcode(CGRect r, double x, double y) {
		/*
		 * Note on casts to double below. If the arithmetic of x+w or y+h is
		 * done in float, then some bits may be lost if the binary exponents of
		 * x/y and w/h are not similar. By converting to double before the
		 * addition we force the addition to be carried out in double to avoid
		 * rounding error in the comparison.
		 * 
		 * See bug 4320890 for problems that this inaccuracy causes.
		 */
		int out = 0;
		if (r.size.width <= 0) {
			out |= OUT_LEFT | OUT_RIGHT;
		} else if (x < r.origin.x) {
			out |= OUT_LEFT;
		} else if (x > r.origin.x + (double) r.size.width) {
			out |= OUT_RIGHT;
		}
		if (r.size.height <= 0) {
			out |= OUT_TOP | OUT_BOTTOM;
		} else if (y < r.origin.y) {
			out |= OUT_TOP;
		} else if (y > r.origin.y + (double) r.size.height) {
			out |= OUT_BOTTOM;
		}
		return out;
	}

	/**
	 * Tests if the specified line segment intersects the interior of this
	 * <code>Rectangle2D</code>.
	 * 
	 * @param x1
	 *            the X coordinate of the start point of the specified line
	 *            segment
	 * @param y1
	 *            the Y coordinate of the start point of the specified line
	 *            segment
	 * @param x2
	 *            the X coordinate of the end point of the specified line
	 *            segment
	 * @param y2
	 *            the Y coordinate of the end point of the specified line
	 *            segment
	 * @return <code>true</code> if the specified line segment intersects the
	 *         interior of this <code>Rectangle2D</code>; <code>false</code>
	 *         otherwise.
	 * @since 1.2
	 */
	public static boolean rectangleIntersectsLine(CGRect r, double x1,
			double y1, double x2, double y2) {
		int out1, out2;
		if ((out2 = outcode(r, x2, y2)) == 0) {
			return true;
		}
		while ((out1 = outcode(r, x1, y1)) != 0) {
			if ((out1 & out2) != 0) {
				return false;
			}
			if ((out1 & (OUT_LEFT | OUT_RIGHT)) != 0) {
				double x = r.origin.x;
				if ((out1 & OUT_RIGHT) != 0) {
					x += r.size.width;
				}
				y1 = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
				x1 = x;
			} else {
				double y = r.origin.y;
				if ((out1 & OUT_BOTTOM) != 0) {
					y += r.size.height;
				}
				x1 = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
				y1 = y;
			}
		}
		return true;
	}
}
