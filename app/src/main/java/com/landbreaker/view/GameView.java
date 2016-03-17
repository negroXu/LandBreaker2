package com.landbreaker.view;

import android.content.Context;

import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Canvas;

import android.util.Log;
import android.view.MotionEvent;
import com.landbreaker.base.BaseSurfaceView;
import com.landbreaker.bean.MapData;
import com.landbreaker.scene.Scene_Game;

import java.util.HashMap;

public class GameView extends BaseSurfaceView {

	public Scene_Game Scene = null;

	// private List<GameScene> mList_scene = null;

	public GameView(Context context, float fps) {
		super(context, fps);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (Scene != null)
			Scene.draw(canvas);
	}

	@Override
	public void physics() {
		// TODO Auto-generated method stub
		if (Scene != null)
			Scene.physics();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return Scene.touchEvent(event);
	}
	
	public void createGameScene(MapData map,HashMap<Integer,Integer> equiplist)
	{
		Scene = new Scene_Game(getContext(),map,equiplist);
	}

	/*
	 * public void setGameCallBack(_GameRequestData _interface) {
	 * mScene.setCallBack(_interface); }
	 */

	/*
	 * public void closeMenu() { ((Scene_Game) mScene).closeMenu(); }
	 * 
	 * public void setGameMap(GameMap map) { ((Scene_Game)
	 * mScene).setGameMap(map); }
	 */

}
