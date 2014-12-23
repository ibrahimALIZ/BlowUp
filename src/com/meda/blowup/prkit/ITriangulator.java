package com.meda.blowup.prkit;

import java.util.List;

import org.cocos2d.types.CGPoint;

public interface ITriangulator {
	List<CGPoint> triangulateVertices(List<CGPoint> vertices);
}
