package com.landbreaker.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.landbreaker.base.GameButton;

public class UI_Torii extends GameButton {// 鸟居

	private Bitmap[] mStaminaImage;
	private Bitmap mStaminaImage_0;
	private int mStamina = 10;

	public UI_Torii(Bitmap image, Bitmap[] plImage) {
		super(image);
		mStaminaImage_0 = image;
		mStaminaImage = plImage;
		// TODO Auto-generated constructor stub
	}

	public void setStamina(int stamina) {
		if (stamina > -1 && stamina < 11) {
			mStamina = stamina;
			if (stamina == 0) {
				this.setImage(mStaminaImage_0);
				this.setImageUp(mStaminaImage_0);
				this.setImageDown(mStaminaImage_0);
			} else {
				this.setImage(mStaminaImage[stamina - 1]);
				this.setImageUp(mStaminaImage[stamina - 1]);
				this.setImageDown(mStaminaImage[stamina - 1]);
			}
		}

	}

	public int getStamina() {
		return mStamina;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}

	@Override
	public void down() {
		// TODO Auto-generated method stub
		super.down();
		this.setScale((float) 0.92, (float) 0.92);
	}

	@Override
	public void up() {
		// TODO Auto-generated method stub
		super.up();
		this.resetScale();
	}

}
