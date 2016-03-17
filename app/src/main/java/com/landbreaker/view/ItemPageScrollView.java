package com.landbreaker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ItemPageScrollView extends ScrollView {
	private ScrollViewListener scrollViewListener = null;

	public ItemPageScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ItemPageScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ItemPageScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

	public interface ScrollViewListener {

		void onScrollChanged(ItemPageScrollView scrollView, int x, int y, int oldx, int oldy);

	}
}
