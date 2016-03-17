package com.landbreaker.file;

import java.io.IOException;
import java.io.InputStream;

import com.landbreaker.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileInput {
	public static Bitmap loadBitmap(Context context, String fileName) {
		Bitmap bm = null;
		InputStream input = null;

		try {
			input = context.getAssets().open(fileName);
			bm = cropImage(BitmapFactory.decodeStream(input), 1);
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		}

		return bm;
	}

	public static Bitmap loadBitmapSmaller(Context context, String fileName, float scale) {
		Bitmap bm = null;
		InputStream input = null;

		try {
			input = context.getAssets().open(fileName);
			bm = cropImage(BitmapFactory.decodeStream(input), scale);
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		}

		return bm;
	}

	private static Bitmap cropImage(Bitmap bm, float s) {
		// TODO Auto-generated method stub
		float w;
		float h;
		if (s < 1) {
			w = bm.getWidth() * s;
			h = bm.getHeight() * s;
		} else {
			w = bm.getWidth();
			h = bm.getHeight();
		}

		bm = Bitmap.createScaledBitmap(bm, (int) (w + 0.5), (int) (h + 0.5), true);
		return bm;
	}

}
