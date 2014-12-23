package com.meda.blowup.prkit;

import java.util.ArrayList;
import java.util.List;

public class Triangulate {
	static final float EPSILON = 0.0000000001f;

	public static List<Vector2D> Process(List<Vector2D> contour) {
		/* allocate and initialize list of Vertices in polygon */
		List<Vector2D> result = new ArrayList<Vector2D>();

		int n = contour.size();
		if (n < 3)
			return result;

		int[] V = new int[n];

		/* we want a counter-clockwise polygon in V */
		if (0.0f < Area(contour))
			for (int v = 0; v < n; v++)
				V[v] = v;
		else
			for (int v = 0; v < n; v++)
				V[v] = (n - 1) - v;

		int nv = n;

		/* remove nv-2 Vertices, creating 1 triangle every time */
		int count = 2 * nv; /* error detection */

		for (int m = 0, v = nv - 1; nv > 2;) {
			/* if we loop, it is probably a non-simple polygon */
			if (0 >= (count--)) {
				// ** Triangulate: ERROR - probable bad polygon!
				return result;
			}

			/* three consecutive vertices in current polygon, <u,v,w> */
			int u = v;
			if (nv <= u)
				u = 0; /* previous */
			v = u + 1;
			if (nv <= v)
				v = 0; /* new v */
			int w = v + 1;
			if (nv <= w)
				w = 0; /* next */

			if (Snip(contour, u, v, w, nv, V)) {
				int a, b, c, s, t;

				/* true names of the vertices */
				a = V[u];
				b = V[v];
				c = V[w];

				/* output Triangle */
				result.add(contour.get(a));
				result.add(contour.get(b));
				result.add(contour.get(c));

				m++;

				/* remove v from remaining polygon */
				for (s = v, t = v + 1; t < nv; s++, t++)
					V[s] = V[t];
				nv--;

				/* resest error detection counter */
				count = 2 * nv;
			}
		}

		return result;
	}

	public static float Area(List<Vector2D> contour) {
		int n = contour.size();
		float A = 0.0f;
		for (int p = n - 1, q = 0; q < n; p = q++) {
			A += contour.get(p).getX() * contour.get(q).getY()
					- contour.get(q).getX() * contour.get(q).getY();
		}
		return A * 0.5f;
	}

	// decide if point Px/Py is inside triangle defined by
	// (Ax,Ay) (Bx,By) (Cx,Cy)
	public static boolean InsideTriangle(float Ax, float Ay, float Bx,
			float By, float Cx, float Cy, float Px, float Py) {

		float ax, ay, bx, by, cx, cy, apx, apy, bpx, bpy, cpx, cpy;
		float cCROSSap, bCROSScp, aCROSSbp;

		ax = Cx - Bx;
		ay = Cy - By;
		bx = Ax - Cx;
		by = Ay - Cy;
		cx = Bx - Ax;
		cy = By - Ay;
		apx = Px - Ax;
		apy = Py - Ay;
		bpx = Px - Bx;
		bpy = Py - By;
		cpx = Px - Cx;
		cpy = Py - Cy;

		aCROSSbp = ax * bpy - ay * bpx;
		cCROSSap = cx * apy - cy * apx;
		bCROSScp = bx * cpy - by * cpx;

		return ((aCROSSbp >= 0.0f) && (bCROSScp >= 0.0f) && (cCROSSap >= 0.0f));
	}

	public static boolean Snip(List<Vector2D> contour, int u, int v, int w,
			int n, int[] V) {
		int p;
		float Ax, Ay, Bx, By, Cx, Cy, Px, Py;

		Ax = contour.get(V[u]).getX();
		Ay = contour.get(V[u]).getY();

		Bx = contour.get(V[v]).getX();
		By = contour.get(V[v]).getY();

		Cx = contour.get(V[w]).getX();
		Cy = contour.get(V[w]).getY();

		if (EPSILON > (((Bx - Ax) * (Cy - Ay)) - ((By - Ay) * (Cx - Ax))))
			return false;

		for (p = 0; p < n; p++) {
			if ((p == u) || (p == v) || (p == w))
				continue;
			Px = contour.get(V[p]).getX();
			Py = contour.get(V[p]).getY();
			if (InsideTriangle(Ax, Ay, Bx, By, Cx, Cy, Px, Py))
				return false;
		}
		return true;
	}
}
