package com.landbreaker.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.landbreaker.action.GameMoveBy;
import com.landbreaker.base.BaseSprite;
import com.landbreaker.config.Config;
import com.landbreaker.config.GameStage;

public class UI_depth extends BaseSprite {

	private BaseSprite cursor;
	private BaseSprite cursor_name;
	private int depth_MAX = GameStage.DEPTH_MAX;
	private int depth = 0;
	private float offset = 0;
	private float scaleSize = 1;
	private int level = 1;
	private Point cursor_p;
	private Point cursor_name_p;
	private Bitmap[] name_ic;

	public UI_depth(Bitmap image, Bitmap cursor_ic, Bitmap[] names, float scaleSize) {
		super(image);
		// TODO Auto-generated constructor stub
		cursor = new BaseSprite(cursor_ic);
		cursor_name = new BaseSprite(names[0]);
		name_ic = names;
		this.scaleSize = scaleSize;
		offset = (float) (Config.GameUILayout.depth_height * scaleSize) / (float) depth_MAX;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		cursor.draw(canvas);
		cursor_name.draw(canvas);
	}

	@Override
	public void physics() {
		// TODO Auto-generated method stub
		super.physics();
		cursor.physics();
		cursor_name.physics();
	}

	public void setPosition(Point mp, Point cp, Point np) {
		cursor_p = cp;
		cursor_name_p = np;
		this.setPosition(mp.x * scaleSize, mp.y * scaleSize);
		cursor.setPosition(cp.x * scaleSize, cp.y * scaleSize);
		cursor_name.setPosition(np.x * scaleSize, np.y * scaleSize);
	}

	private void cursorMoveBy(float s) {

		GameMoveBy act1 = new GameMoveBy(0, s, GameStage.DIG_TIME);
		GameMoveBy act2 = new GameMoveBy(0, s, GameStage.DIG_TIME);
		cursor.runAction(act1);
		cursor_name.runAction(act2);
	}

	public boolean down(int dep) {
		if (depth + dep >= depth_MAX) {
			cursorMoveBy(offset * (depth_MAX - depth));
			depth = depth_MAX;
			level = 6;
			return false;
		} else {
			cursorMoveBy(offset * dep);
			depth += dep;
			level = 1 + depth / 30;
			return true;
		}
		// cursor下降
	}

	public int getLevel() {
		return level;
	}

	public int getDepth() {
		return depth;
	}

	public void reset() {
		cursor.clearAction();
		cursor_name.clearAction();
		depth = 0;
		level = 1;
		cursor.setPosition(cursor_p.x * scaleSize, cursor_p.y * scaleSize);
		cursor_name.setPosition(cursor_name_p.x * scaleSize, cursor_name_p.y * scaleSize);
		cursor_name.setImage(name_ic[0]);
	}
}
