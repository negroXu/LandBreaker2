package com.landbreaker.ui;

import java.util.List;

import com.landbreaker.action.GameMoveTo;
import com.landbreaker.action.GameAction.GameCallBack;
import com.landbreaker.base.BaseSprite;
import com.landbreaker.base.GameButton;
import com.landbreaker.item.GameItem_Dig;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

public class UI_ToolsBar implements GameCallBack {// 工具栏

	private GameButton mTools_main;
	private GameButton[] mTools_others;
	private PointF[] mPosition_others;
	private PointF mPosition_main;

	public UI_digItem[] mItems;// 道具精灵列表

	private boolean isExtend = false;
	private int mExtendTime = 150;

	public UI_ToolsBar(Bitmap m_up, Bitmap m_down, Bitmap o_up, Bitmap o_down) {
		mTools_main = new GameButton(m_up);
		mTools_main.setImageDown(m_down);
		mTools_others = new GameButton[5];
		for (int i = 0; i < 5; i++) {
			mTools_others[i] = new GameButton(o_up);
			mTools_others[i].setImageDown(o_down);
			mTools_others[i].setTouchable(false);
		}
	}

	public void setItemList(UI_digItem[] list) {
		mItems = list;
		for (int i = 0; i < mItems.length; i++) {
			if (mItems[0].id == 0)
				break;
			else if (mItems[i].id == 0) {
				swapItem(i, 0);
			}
		}
		sortOtherItem();
		mItems[0].setPosition(mPosition_main.x, mPosition_main.y);
		for (int i = 1; i < mItems.length; i++) {
			mItems[i].setPosition(mPosition_main.x, mPosition_main.y);
		}
	}

	private void sortOtherItem() {
		boolean exchange;
		do {
			exchange = false;
			for (int i = 2; i < mItems.length; i++) {
				if (mItems[i].id < mItems[i - 1].id) {
					swapItem(i, i - 1);
					exchange = true;
				}
			}
		} while (exchange);
	}

	private void swapItem(int i1, int i2) {

		UI_digItem temp = mItems[i1];
		PointF tempP = new PointF(mItems[i1].getPosition().x, mItems[i1].getPosition().y);
		PointF tempP2 = new PointF(mItems[i2].getPosition().x, mItems[i2].getPosition().y);
		mItems[i1] = mItems[i2];
		mItems[i1].setPosition(tempP);
		mItems[i2] = temp;
		mItems[i2].setPosition(tempP2);
	}

	private void selectItem(int i) {
		// TODO Auto-generated method stub
		if (mItems[i].canBeUse() == -1)
			return;
		swapItem(0, i);
		sortOtherItem();
	}

	public void setPosition(Point mp, Point[] op, float s) {
		mPosition_main = new PointF(mp.x * s, mp.y * s);
		mPosition_others = new PointF[op.length];
		mTools_main.setPosition(mPosition_main.x, mPosition_main.y);
		for (int i = 0; i < 5; i++) {
			mPosition_others[i] = new PointF(op[i].x * s, op[i].y * s);
			mTools_others[i].setPosition(mPosition_main.x, mPosition_main.y);
		}
	}

	public void draw(Canvas canvas) {
		for (int i = 0; i < mTools_others.length; i++)
			mTools_others[i].draw(canvas);
		for (int i = 1; i < mItems.length; i++) {
			mItems[i].draw(canvas);
		}
		mTools_main.draw(canvas);
		mItems[0].draw(canvas);
	}

	public void physics() {

		for (int i = 0; i < mTools_others.length; i++)
			mTools_others[i].physics();
		for (int i = 1; i < mItems.length; i++)
			mItems[i].physics();
	}

	public boolean onTouch(MotionEvent event) {
		MainClickEvent(event);
		return OtherClickEvent(event);
	}

	// ---TouchEvent----/////////////////////

	private void MainClickEvent(MotionEvent event) {
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			if (mTools_main.isContact(event.getX(), event.getY()))
				mTools_main.down();
			break;

		case MotionEvent.ACTION_UP:
			if (mTools_main.isDown()) {
				mTools_main.up();
				if (mTools_main.isContact(event.getX(), event.getY()))
					OtherToolsAction();
			}
			break;
		default:
			break;
		}
	}

	private boolean OtherClickEvent(MotionEvent event) {
		if (!isExtend)
			return false;
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			for (int i = 0; i < mTools_others.length; i++) {
				if (mTools_others[i].isContact(event.getX(), event.getY())) {
					mTools_others[i].down();
					break;
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			for (int i = 0; i < mTools_others.length; i++) {
				if (mTools_others[i].isDown()) {
					mTools_others[i].up();
					if (mTools_others[i].isContact(event.getX(), event.getY())) {
						// 交换道具
						selectItem(i + 1);
						OtherToolsAction();
						return true;
					}
				}
			}
			break;
		default:
			break;
		}
		return false;
	}

	// ---动作----////////////////////////////////////////////////

	private void OtherToolsAction() {
		// TODO Auto-generated method stub
		mTools_main.setTouchable(false);
		if (!isExtend) {
			for (int i = 0; i < mTools_others.length; i++) {
				GameMoveTo act = new GameMoveTo(mPosition_others[i].x, mPosition_others[i].y, mExtendTime);
				act.setCallBack(this);
				mTools_others[i].setTouchable(false);
				mTools_others[i].runAction(act);
			}
			for (int i = 1; i < mItems.length; i++) {
				GameMoveTo act = new GameMoveTo(mPosition_others[i - 1].x, mPosition_others[i - 1].y, mExtendTime);
				mItems[i].runAction(act);
			}
		} else {
			for (int i = 0; i < mTools_others.length; i++) {
				GameMoveTo act = new GameMoveTo(mPosition_main.x, mPosition_main.y, mExtendTime);
				act.setCallBack(this);
				mTools_others[i].setTouchable(false);
				mTools_others[i].runAction(act);
			}
			for (int i = 1; i < mItems.length; i++) {
				GameMoveTo act = new GameMoveTo(mPosition_main.x, mPosition_main.y, mExtendTime);
				mItems[i].runAction(act);
			}
		}
		isExtend = !isExtend;

	}

	@Override
	public void _finish(BaseSprite sp) {
		// TODO Auto-generated method stub
		sp.setTouchable(isExtend);
		mTools_main.setTouchable(true);
	}

	@Override
	public void _finishWithKey(int key) {
		// TODO Auto-generated method stub

	}

	// ==Method===========================
	public List<GameItem_Dig> getCurrentItemList() {
		return mItems[0].getItemList();
	}

	public void closeItem() {
		for (int i = 1; i < mItems.length; i++) {
			mItems[i].setPosition(mPosition_main.x, mPosition_main.y);
		}
		for (int i = 0; i < 5; i++) {
			mTools_others[i].setPosition(mPosition_main.x, mPosition_main.y);
		}
		isExtend = false;
	}

	public UI_digItem[] getItemList() {
		return mItems;
	}
	
	public void refreshAllItem()
	{
		for(UI_digItem item:mItems)
		{
			item.refreshItemStatus();
		}
	}

}
