package com.meda.blowup.prkit;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.config.ccConfig;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CCTexParams;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccBlendFunc;
import org.cocos2d.utils.BufferProvider;

public class PRFilledPolygon extends CCNode {
	private int areaTrianglePointCount;

	private CCTexture2D texture;
	private ccBlendFunc blendFunc;
	private ITriangulator triangulator;
	private List<CGPoint> areaTrianglePoints;
	private List<CGPoint> textureCoordinates;

	public PRFilledPolygon() {
		areaTrianglePoints = new ArrayList<CGPoint>();
		textureCoordinates = new ArrayList<CGPoint>();
	}

	public void filledPolygonWithPoints(List<CGPoint> polygonPoints,
			CCTexture2D fillTexture) {
		initWithPoints(polygonPoints, fillTexture);
	}

	public void filledPolygonWithPoints(List<CGPoint> polygonPoints,
			CCTexture2D fillTexture, ITriangulator polygonTriangulator) {
		initWithPoints(polygonPoints, fillTexture, polygonTriangulator);
	}

	public void initWithPoints(List<CGPoint> polygonPoints,
			CCTexture2D fillTexture) {
		initWithPoints(polygonPoints, fillTexture, new PRRatcliffTriangulator());
	}

	public void initWithPoints(List<CGPoint> polygonPoints,
			CCTexture2D fillTexture, ITriangulator polygonTriangulator) {
		triangulator = polygonTriangulator;
		setTexture(fillTexture);
		setPoints(polygonPoints);
	}

	public void setPoints(List<CGPoint> points) {
		if (areaTrianglePoints != null)
			areaTrianglePoints.clear();
		if (textureCoordinates != null)
			textureCoordinates.clear();

		List<CGPoint> triangulatedPoints = triangulator
				.triangulateVertices(points);
		areaTrianglePointCount = triangulatedPoints.size();

		CGPoint p, pp;
		for (int i = 0; i < areaTrianglePointCount; i++) {
			p = triangulatedPoints.get(i);
			pp = new CGPoint();
			pp.set(p.x, p.y);
			areaTrianglePoints.add(pp);
		}
		calculateTextureCoordinates();
	}

	public void calculateTextureCoordinates() {
		CGPoint p;
		for (int j = 0; j < areaTrianglePointCount; j++) {
			p = CGPoint.ccpMult(areaTrianglePoints.get(j),
					1.0f / texture.pixelsWide());
			textureCoordinates.add(p);
		}
	}

	public CCTexture2D getTexture() {
		return texture;
	}

	public void setTexture(CCTexture2D texture) {
		CCTexParams texParams = new CCTexParams(GL10.GL_NEAREST,
				GL10.GL_NEAREST, GL10.GL_REPEAT, GL10.GL_REPEAT);
		texture.setTexParameters(texParams);
		this.texture = texture;

		updateBlendFunc();
		calculateTextureCoordinates();

	}

	public void updateBlendFunc() {
		// it's possible to have an untextured sprite
		if (texture != null || !texture.hasPremultipliedAlpha()) {
			blendFunc.src = GL10.GL_SRC_ALPHA;
			blendFunc.dst = GL10.GL_ONE_MINUS_SRC_ALPHA;
			// [self setOpacityModifyRGB:NO];
		} else {
			blendFunc.src = ccConfig.CC_BLEND_SRC;
			blendFunc.dst = ccConfig.CC_BLEND_DST;
			// [self setOpacityModifyRGB:YES];
		}
	}

	public ccBlendFunc getBlendFunc() {
		return blendFunc;
	}

	public void setBlendFunc(ccBlendFunc blendFunc) {
		this.blendFunc = blendFunc;
	}

	@Override
	public void draw(GL10 gl) {
		super.draw(gl);

		gl.glDisable(GL10.GL_BLEND);
		// we have a pointer to vertex points so enable client state
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.name());
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
				GL10.GL_DECAL);

		int i = 0;
		float[] vertices = new float[areaTrianglePoints.size() * 2];
		for (CGPoint p : areaTrianglePoints) {
			vertices[i++] = p.x;
			vertices[i++] = p.y;
		}
		Buffer vertexBuffer = BufferProvider.makeFloatBuffer(vertices);

		vertices = new float[textureCoordinates.size() * 2];
		i = 0;
		for (CGPoint p : textureCoordinates) {
			vertices[i++] = p.x;
			vertices[i++] = p.y;
		}
		Buffer texBuffer = BufferProvider.makeFloatBuffer(vertices);

		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, areaTrianglePointCount);

		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
				GL10.GL_MODULATE);

		// Restore texture matrix and switch back to modelview matrix
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glEnable(GL10.GL_BLEND);
	}
}
