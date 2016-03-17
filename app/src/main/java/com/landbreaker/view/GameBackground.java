package com.landbreaker.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.landbreaker.base.BaseSurfaceView;
import com.landbreaker.config.Config;

/**
 * 移动重复背景View
 * 
 * @author kaiyu
 * 
 */
public class GameBackground extends BaseSurfaceView {

	private Bitmap bm = null;
	private Rect drawRect = null;
	private float offset_x = 0;
	private float offset_y = 0;
	private float bm_width = 0;
	private float bm_height = 0;
	private Paint paint = new Paint();
	private boolean isDraw = false;
	private float x = 0;
	private float y = 0;
	private Rect image_rect;
	private RectF dest_rect;

	private final static int BG_BLUE = 0xFF6DB0DB;

	public GameBackground(Context context) {
		super(context, Config.default_PFS);
		// TODO Auto-generated constructor stub
	}

	public GameBackground(Context context, AttributeSet attrs) {
		super(context, Config.default_PFS, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 播放设置
	 * 
	 * @param bg
	 *            map图片
	 * @param rect
	 *            循环绘制的范围
	 * @param speed_x
	 *            图标速度
	 * @param speed_y
	 *            图标速度
	 */
	public void setDrawOption(Bitmap bg, Rect rect, float speed_x, float speed_y) {
		bm = bg;
		drawRect = rect;
		offset_x = speed_x;
		offset_y = speed_y;
		bm_width = bm.getWidth();
		bm_height = bm.getHeight();
		image_rect = new Rect(0, 0, (int) bm_width, (int) bm_height);
		bm_width = bm_width * Config.Size_Scale;
		bm_height = bm_height * Config.Size_Scale;
		dest_rect = new RectF(0, 0, bm_width, bm_height);
		isDraw = true;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		if (!isDraw)
			return;
		canvas.drawColor(BG_BLUE);
		for (float dy = y; dy < drawRect.height(); dy += bm_height)
			for (float dx = x; dx < drawRect.width(); dx += bm_width) {
				dest_rect.offsetTo(dx, dy);
				canvas.drawBitmap(bm, image_rect, dest_rect, paint);
			}
	}

	@Override
	public void physics() {
		// TODO Auto-generated method stub
		super.physics();
		if (!isDraw)
			return;
		setOffset();
	}

	private void setOffset() {
		// TODO Auto-generated method stub
		float dx = x;
		float dy = y;
		dx += offset_x;
		dy += offset_y;
		dx = resetXY(dx, bm_width, drawRect.width());
		dy = resetXY(dx, bm_height, drawRect.height());
		x = dx;
		y = dy;
	}

	private float resetXY(float dxy, float wh, float max) {

		if (dxy > 0) {
			dxy = dxy % max;
			while (true) {
				dxy -= wh;
				if (dxy < 0)
					break;
			}

		} else if (dxy + wh < 0) {
			dxy = -((-dxy) % max);
			if (dxy + wh < 0)
				while (true) {
					dxy += wh;
					if (dxy + wh > 0)
						break;
				}
		}
		return dxy;

	}
}
