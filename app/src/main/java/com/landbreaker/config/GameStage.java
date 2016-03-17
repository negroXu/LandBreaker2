package com.landbreaker.config;

import android.graphics.Point;

public class GameStage {

	public final static int DEPTH_MAX = 150;

	public final static int DIG_TIME = 4000;

	/**
	 * 挖掘道具参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class Item_State {

		public static class PlasticShovel {
			/**
			 * 耐久
			 */
			public final static int DURABLE = 10;
			/**
			 * 挖掘深度
			 */
			public final static int POWER = 1;

			public final static Point CENTER_POS = new Point(372, 643);
			public final static int ANGLE = 0;
		}

		public static class IronShovel {
			public final static int DURABLE = 30;

			public final static int POWER = 2;

			public final static Point CENTER_POS = new Point(413, 580);
			public final static int ANGLE = 0;
		}

		public static class Drill {
			public final static int DURABLE = 60;

			public final static int POWER = 4;

			public final static Point CENTER_POS = new Point(347, 641);
			public final static int ANGLE = 15;
			public final static int UP = 20;

		}

		public static class Piledriver {
			public final static int DURABLE = 100;

			public final static int POWER = 7;

			public final static Point CENTER_POS = new Point(347, 641);
			public final static int ANGLE = 15;
			public final static int UP = 20;
		}

		public static class FishingRod {
			public final static int DURABLE = 0;

			public final static int POWER = 0;

			public final static Point CENTER_POS = new Point(368, 623);
			public final static int ANGLE = 0;
		}

		public static class SpearGun {
			public final static int DURABLE = 0;

			public final static int POWER = 0;

			public final static Point CENTER_POS = new Point(368, 623);
			public final static int ANGLE = 0;
		}

	}

	public static class Effect {
		public final static Point effect_Iron_Pos = new Point(217, 710);
		public final static Point effect_Sand_Pos = new Point(Config.GameUILayout.digEffect_center.x,
				Config.GameUILayout.digEffect_center.y - 10);
		public final static Point effect_Stone_Pos = new Point(Config.GameUILayout.digEffect_center.x,
				Config.GameUILayout.digEffect_center.y - 10);
	}

}
