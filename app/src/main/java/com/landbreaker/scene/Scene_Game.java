package com.landbreaker.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.landbreaker.action.GameAction.GameCallBack;
import com.landbreaker.action.GameAnimate;
import com.landbreaker.action.GameMoveTo;
import com.landbreaker.action.GameRotateTo;
import com.landbreaker.application.AppData;
import com.landbreaker.base.BaseSprite;
import com.landbreaker.base.GameButton;
import com.landbreaker.base.GameScene;
import com.landbreaker.base.PhysicsThread;
import com.landbreaker.bean.Feed;
import com.landbreaker.bean.MapData;
import com.landbreaker.config.Config;
import com.landbreaker.config.GameStage;
import com.landbreaker.config.GameStage.Item_State.Drill;
import com.landbreaker.config.GameStage.Item_State.Piledriver;
import com.landbreaker.database.Item_BASICSYSTEMITEM;
import com.landbreaker.database.Item_MAPFORTUNE;
import com.landbreaker.database.Table_BASICSYSTEMITEM;
import com.landbreaker.database.Table_MAPFORTUNE;
import com.landbreaker.ui.Effect_Dig;
import com.landbreaker.item.GameItem_Dig;
import com.landbreaker.testdata.TestData;
import com.landbreaker.ui.UI_Luck;
import com.landbreaker.ui.UI_ToolsBar;
import com.landbreaker.ui.UI_Torii;
import com.landbreaker.ui.UI_depth;
import com.landbreaker.ui.UI_digItem;
import com.landbreaker.ui.UI_showItem;
import com.landbreaker.ui.UI_menu;

/**
 * 游戏场景
 * 
 * @author kaiyu
 * 
 */
public class Scene_Game extends GameScene implements GameCallBack {

	private Context mContext;
	private Matrix mMatrix_background;
	private Bitmap[] bms_map = null;
	private Paint mPaint = new Paint();

	private UI_Torii mUI_torii = null;// 鸟居
	private UI_Luck mUI_luck = null;// 签
	public  UI_depth mUI_depth = null;// 深度条

	private GameButton mUI_menuButton = null;// 菜单按钮
	private UI_ToolsBar mUI_toolsBar = null;// 工具栏

	private Effect_Dig mEffect_dig = null;// 挖掘特效
	private Effect_Dig mEffect_dig_front = null;

	private BaseSprite mSp_digTools = null;// 挖掘工具
	private Rect mRect_digTools_touch = null;

	private BaseSprite mSp_foundItem = null;// 发现道具
	private List<BaseSprite> mList_digEffect = null;

	private UI_digItem mList_digTools[] = null;
	private List<GameItem_Dig>[] mItem_list;

	private UI_menu mUI_menu = null;// 菜单

	private UI_showItem mUI_showItem = null;

	// 图片
	private Bitmap[] mbms_DigitImage = null;
	private Bitmap[] mAnimate_bubble = null;
	private Bitmap[] bms_digEffect_1 = null;
	private Bitmap[] bms_digEffect_2 = null;
	private Bitmap[] bms_digEffect_3 = null;
	private Bitmap[] bms_digEffect_sand = null;

	private Bitmap[] bms_digAnimate_plastic_shovel = null;
	private Bitmap[] bms_digAnimate_iron_shovel = null;
	private Bitmap[] bms_digAnimate_drill = null;
	private Bitmap[] bms_digAnimate_pileDriver = null;
	private Bitmap bm_digItem_show = null;

	// 绘制列表
	private List<BaseSprite> mList_sprite = null;// 精灵
	private List<BaseSprite> mList_ui = null;// ui

	//
	private final static int DIG_END = 1;
	private final static int BUBBLE_END = 2;
	private final static int DIGING_SHOVEL = 3;
	private final static int DIGING_DRILL = 4;
	private final static int DIGING_PILE = 5;

	private final static int PLASTIC_SHOVEL = 7;// 胶铲
	private final static int IRON_SHOVEL = 8;// 铁铲
	private final static int DRILL = 9;// 钻头
	private final static int PILEDRIVER = 10;// 打桩机
	private final static int FISHING_ROD = 11;// 钓鱼竿
	private final static int SPEAR_GUN = 12;// 鱼枪

	// 场景变换
	private MapData gameMap;
	private HashMap<String, Object> gameItemDropList;
	private HashMap<String, Object> systemitemList;
	private int alpha = 0;
	private boolean isTouchable = true;

	public Scene_Game(Context context, MapData map,HashMap<Integer,Integer> equiplist) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		init(equiplist);
		initGameStage(map);
		isInit = true;
	}

	// ======================================初始化游戏场景UI===================================================
	public void init(HashMap<Integer,Integer> equiplist) {
		// TODO Auto-generated method stub
		mList_sprite = new ArrayList<BaseSprite>();
		mList_ui = new ArrayList<BaseSprite>();
		mList_digEffect = new ArrayList<BaseSprite>();

		mMatrix_background = new Matrix();
		mMatrix_background.setScale(Config.Size_Scale, Config.Size_Scale);
		mPaint.setAntiAlias(true);
		// 初始化数字

		mbms_DigitImage = mImgReader.loadDigit();

		// 挖地特效

		bms_digEffect_1 = mImgReader.loadAnimate("fx_1_act", 16);
		bms_digEffect_2 = mImgReader.loadAnimate("fx_2_act", 12);
		bms_digEffect_3 = mImgReader.loadAnimate("fx_3_act", 12);
		bms_digEffect_sand = mImgReader.loadAnimate("fx_4_act", 12);
		bms_digAnimate_plastic_shovel = mImgReader.loadAnimate("Item_1_act", 17);
		bms_digAnimate_iron_shovel = mImgReader.loadAnimate("Item_2_act", 17);
		bms_digAnimate_drill = mImgReader.loadAnimate("Item_3_act", 12);
		bms_digAnimate_pileDriver = mImgReader.loadAnimate("Item_4_act", 2);

		mEffect_dig = new Effect_Dig(mImgReader.loadDigEffect());
		setSpirtePosition(mEffect_dig, Config.GameUILayout.digEffect_jiedao);
		mEffect_dig.setVisibility(false);
		// mList_sprite.add(mEffect_dig);

		mEffect_dig_front = new Effect_Dig(mImgReader.loadDigEffect_front());
		setSpirtePosition(mEffect_dig_front, Config.GameUILayout.digEffect_jiedao);
		mEffect_dig_front.setVisibility(false);
		// mList_sprite.add(mEffect_dig);

		// 挖掘工具
		mSp_digTools = new BaseSprite(null);
		mSp_digTools.setTouchable(true);
		mRect_digTools_touch = new Rect((int) (Config.GameUILayout.dig_touch_left * scaleSize),
				(int) (Config.GameUILayout.dig_touch_top * scaleSize),
				(int) (Config.GameUILayout.dig_touch_right * scaleSize),
				(int) (Config.GameUILayout.dig_touch_bottom * scaleSize));
		// mList_sprite.add(mSp_digTools);

		// 发现道具
		mAnimate_bubble = mImgReader.loadAnimate("Ani_tanhao", 10);
		// mSp_foundItem = new BaseSprite(mImgReader.load("gameui/qipao.png"));
		mSp_foundItem = new BaseSprite(mAnimate_bubble[0]);
		setSpirtePosition(mSp_foundItem, Config.GameUILayout.item_found_effect);
		mSp_foundItem.setVisibility(false);
		mSp_foundItem.setTouchable(true);

		// --ui--///////////////////////////////////////////
		// 鸟居
		mUI_torii = new UI_Torii(mImgReader.load("gameui/zhanbuicon.png"), mImgReader.loadStamina());
		mUI_torii.setStamina(10);
		mUI_torii.setTouchable(true);
		setSpirtePosition(mUI_torii, Config.GameUILayout.torii);
		mList_ui.add(mUI_torii);
		// 签
		mUI_luck = new UI_Luck(mImgReader.load("gameui/zhanbu_daji.png"), 0);
		setSpirtePosition(mUI_luck, Config.GameUILayout.luck);
		mList_ui.add(mUI_luck);

		// 深度条
		mUI_depth = new UI_depth(mImgReader.load("gameui/shendutiao.png"), mImgReader.load("gameui/shenduzhishi.png"),
				new Bitmap[] { mImgReader.load("gameui/dimian.png") }, scaleSize);
		mUI_depth.setPosition(Config.GameUILayout.depth, Config.GameUILayout.depth_Cursor,
				Config.GameUILayout.depth_Name);
		mList_ui.add(mUI_depth);

		// 菜单按钮
		mUI_menuButton = new GameButton(mImgReader.load("gameui/menu_up.png"));
		mUI_menuButton.setImageDown(mImgReader.load("gameui/menu_down.png"));
		mUI_menuButton.setTouchable(true);
		setSpirtePosition(mUI_menuButton, Config.GameUILayout.menu);
		mList_ui.add(mUI_menuButton);

		// 工具栏
		initItemTools(equiplist);

		// 菜单
		initMenu();

		mUI_showItem = new UI_showItem(mImgReader.load("item/itembig_jiqiren.png"));
		setSpirtePosition(mUI_showItem, Config.GameUILayout.item_show);

	}

	/**
	 * 初始化工具栏
	 * @param equiplist 铲工具个数
	 */
	private void initItemTools(HashMap<Integer,Integer> equiplist) {
		mUI_toolsBar = new UI_ToolsBar(mImgReader.load("gameui/itemicon_up.png"),
				mImgReader.load("gameui/itemicon_down.png"), mImgReader.load("gameui/item_up.png"),
				mImgReader.load("gameui/item_down.png"));

		mUI_toolsBar.setPosition(Config.GameUILayout.tool_main, Config.GameUILayout.tool_others, scaleSize);

		initItemDataList(equiplist);
		mList_digTools = new UI_digItem[6];
		mList_digTools[0] = new UI_digItem(mContext, mImgReader.load("tools/84jiaochan.png"), mbms_DigitImage, PLASTIC_SHOVEL,
				mItem_list[0]);
		mList_digTools[1] = new UI_digItem(mContext, mImgReader.load("tools/84jinshuchan.png"), mbms_DigitImage, IRON_SHOVEL,
				mItem_list[1]);
		mList_digTools[2] = new UI_digItem(mContext, mImgReader.load("tools/84shouzuan.png"), mbms_DigitImage, DRILL,
				mItem_list[2]);
		mList_digTools[3] = new UI_digItem(mContext, mImgReader.load("tools/84dianzuan.png"), mbms_DigitImage, PILEDRIVER,
				mItem_list[3]);
		mList_digTools[4] = new UI_digItem(mContext, mImgReader.load("tools/84dianyugan.png"), mbms_DigitImage, FISHING_ROD,
				mItem_list[4]);
		mList_digTools[5] = new UI_digItem(mContext, mImgReader.load("tools/84yucha.png"), mbms_DigitImage, SPEAR_GUN,
				mItem_list[5]);

		mUI_toolsBar.setItemList(mList_digTools);

		changeMainDigItem(mUI_toolsBar.mItems[0].id);
	}

	/**
	 * 初始化工具列表(工具数量)
	 */
	@SuppressWarnings("unchecked")
	private void initItemDataList(HashMap<Integer,Integer> equiplist) {
		// TODO Auto-generated method stub
		mItem_list = new ArrayList[6];
		for (int i = 0; i < mItem_list.length; i++) {
			if(equiplist.get(i + 7) == null){
				mItem_list[i] = TestData.getDigItemDataList(i + 7,0);
			}else{
				mItem_list[i] = TestData.getDigItemDataList(i + 7,equiplist.get(i + 7).intValue());
			}
		}
	}

	/**
	 * 初始化菜单
	 */
	private void initMenu() {
		// TODO Auto-generated method stub
		mUI_menu = new UI_menu(mImgReader.loadMenuItemWithBack_up(), mImgReader.loadMenuItemWithBack_down(),
				mImgReader.load("menu/beijingkuang.png"), mContext);
		mUI_menu.setPosition(Config.MenuUILayout.menu_bg, Config.MenuUILayout.menu_item, Config.MenuUILayout.back,
				scaleSize);
	}

	// --//
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		canvas.drawBitmap(bms_map[0], mMatrix_background, mPaint);
		// 挖掘效果
		mEffect_dig.draw(canvas);
		mSp_digTools.draw(canvas);

		mSp_foundItem.draw(canvas);
		mEffect_dig_front.draw(canvas);
		for (int i = 0; i < mList_digEffect.size(); i++)
			mList_digEffect.get(i).draw(canvas);

		// canvas.drawBitmap(mbm_Background[1], 0, 0, mPaint);

		for (int i = 0; i < mList_sprite.size(); i++) {
			mList_sprite.get(i).draw(canvas);
		}

		for (int i = 0; i < mList_ui.size(); i++) {
			mList_ui.get(i).draw(canvas);
		}

		mUI_toolsBar.draw(canvas);
		mUI_menu.draw(canvas);

		mUI_showItem.draw(canvas);

		if (!isTouchable)
			canvas.drawARGB(alpha, 255, 255, 255);

	}

	@Override
	public void physics() {
		// TODO Auto-generated method stub
		super.physics();
		for (int i = 0; i < mList_sprite.size(); i++) {
			mList_sprite.get(i).physics();
		}
		for (int i = 0; i < mList_ui.size(); i++) {
			mList_ui.get(i).physics();
		}
		for (int i = 0; i < mList_digEffect.size(); i++) {
			mList_digEffect.get(i).physics();
		}
		mUI_toolsBar.physics();
		mSp_digTools.physics();
		mUI_showItem.physics();
		mSp_foundItem.physics();
	}

	@Override
	public boolean touchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (!isTouchable)
			return true;
		mUI_menu.touchEvent(event);
		if (mUI_menu.isShowing()) {
			return true;
		}

		if (mUI_showItem.touchEvent(event)) {
			if (mSp_digTools.getTag() != null)
				if (mUI_toolsBar.mItems[0].id != (Integer) mSp_digTools.getTag())
					changeMainDigItem(mUI_toolsBar.mItems[0].id);
			if (mUI_toolsBar.mItems[0].canBeUse() != UI_digItem.USEABLE)
				mSp_digTools.setVisibility(false);
			return true;
		}

		_touchEvent_torii(event);// 鸟居

		_touchEvent_menuButton(event);// 菜单

		_touchEvent_tools(event);// 工具栏

		_touchEvent_Dig(event);// 挖掘道具

		return true;
	}

	// =======================================touch_event===================================================

	/**
	 * 鸟居的点击事件
	 * @param event
	 */
	private void _touchEvent_torii(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mUI_torii.isContact(event.getX(), event.getY())) {
				mUI_torii.down();
				mUI_luck.setScale((float) 0.92, (float) 0.92, mUI_torii.getPosition().x, mUI_torii.getPosition().y);
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mUI_torii.isDown()) {
				mUI_torii.up();
				mUI_luck.resetScale();
				if (mUI_torii.isContact(event.getX(), event.getY())) {
					changeMapStart();
//					changeMap();
				}
			}
		default:
			break;
		}
	}

	/**
	 *菜单按钮的点击事件
	 * @param event
	 */
	private void _touchEvent_menuButton(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mUI_menuButton.isContact(event.getX(), event.getY()))
				mUI_menuButton.down();
			break;
		case MotionEvent.ACTION_UP:
			if (mUI_menuButton.isDown()) {
				mUI_menuButton.up();
				if (mUI_menuButton.isContact(event.getX(), event.getY()))
					mUI_menu.show();// openMenu
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 工具栏的点击事件
	 * @param event
	 */
	private void _touchEvent_tools(MotionEvent event) {
		// TODO Auto-generated method stub
		if (mUI_toolsBar.onTouch(event))
			if (mSp_digTools.getTouchable() == true)
				changeMainDigItem(mUI_toolsBar.mItems[0].id);
	}

	/**
	 * 挖地的点击事件
	 * @param event
	 */
	private void _touchEvent_Dig(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mSp_digTools.getTouchable() && mSp_digTools.getVisibility())
				if (mRect_digTools_touch.contains((int) event.getX(), (int) event.getY())) {
					dig();
					break;
				}
			if (mSp_foundItem.isContact(event.getX(), event.getY())) {
				getItem();
				break;
			}
			break;

		default:
			break;
		}
	}

	// =========================================Method=======================================================

	/**
	 * 初始化场景
	 * 
	 * @param map
	 */
	public void initGameStage(MapData map) {
		gameMap = map;
		gameItemDropList = gameMap.getItemDropList();
		systemitemList =gameMap.getsystemitemList();

		bms_map = mImgReader.loadGameBg(gameMap.map_id);

		Table_MAPFORTUNE table_mapfortune = new Table_MAPFORTUNE(mContext);
		Item_MAPFORTUNE item_mapfortune = table_mapfortune.get(gameMap.mapfortune_id);

		// 签
		mList_ui.remove(mUI_luck);
		mUI_luck = new UI_Luck(mImgReader.load("gameui/"+ item_mapfortune.pic + ".png"), 0);
		setSpirtePosition(mUI_luck, Config.GameUILayout.luck);
		mList_ui.add(mUI_luck);
	}

	/**
	 * 设置场景
	 * 
	 * @param map
	 */
	public void setGameStage(MapData map) {
		gameMap = map;
		gameItemDropList = gameMap.getItemDropList();
		bms_map = mImgReader.loadGameBg(gameMap.map_id);
		resetGameStage();
		changeMapFinish();
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		mUI_menu.close();
	}

	// =======================================GameLogic=======================================================

	/**
	 * 挖地逻辑
	 */
	private void dig() {
		if (mUI_toolsBar.mItems[0].canBeUse() == UI_digItem.USEABLE && mUI_depth.getDepth() < GameStage.DEPTH_MAX) {
			try{
				List<GameItem_Dig> list = mUI_toolsBar.getCurrentItemList();
				GameItem_Dig item = list.get(0);
				setDigItemAnimate(mUI_toolsBar.mItems[0].id);
				mUI_depth.down(item.power);
				//

//			if (!item.reductionDurable(2)) {
//				mUI_toolsBar.mItems[0].removeItem(item);
//			}
				setDigEffect(mUI_depth.getDepth());
			}catch (Exception e){

			}
		}
	}

	/**
	 * 挖完之后的动作
	 */
	private void digEndAction() {
		mSp_digTools.setImage(bm_digItem_show);
		mSp_digTools.setTouchable(false);
		mUI_toolsBar.refreshAllItem();
	}

	/**
	 * 获得道具
	 */
	private void getItem() {
		Feed[] feed = new Feed[1];
		feed[0] = new Feed();
		feed[0].system_id = gameItemDropList.get(mUI_depth.getDepth() + "") != null ? (Integer) gameItemDropList
				.get(mUI_depth.getDepth() + "") : -1;

		if(feed[0].system_id == -1){
			// 分配物品 systemitemList
//			systemitemList.get("4");
//			feed[0].system_id = 4;
		}

		mSp_digTools.setTouchable(true);
		mSp_foundItem.setVisibility(false);
		mUI_showItem.showItem(mImgReader.load("prop/" + feed[0].system_id + ".png"));


		feed[0].map_level = mUI_depth.getDepth();
		feed[0].qty = 1;
		upLoadData(feed,mUI_toolsBar);

	}

	/**
	 * 设置场景的挖掘效果（设置坑
	 * @param depth
	 */
	private void setDigEffect(int depth) {
		if (depth > 0 && depth <= 15) {
			setDigEffectImage(0);
		} else if (depth > 15 && depth <= 35) {
			setDigEffectImage(1);
		} else if (depth > 35) {
			setDigEffectImage(2);
		} else {
			setDigEffectImage(-1);
		}
	}

	private void setDigEffectImage(int index) {
		if (index < 0 || index > 2) {
			mEffect_dig.setVisibility(false);
			mEffect_dig_front.setVisibility(false);
		} else {
			mEffect_dig.setVisibility(true);
			mEffect_dig.setEffect(index);
			mEffect_dig_front.setVisibility(true);
			mEffect_dig_front.setEffect(index);
		}
	}

	/**
	 * 设置鸟居体力值
	 * @param ph
     */
	public void setToriiStamina(int ph){
		mUI_torii.setStamina(ph);
	}
	/**
	 * 开始切换地图的动作
	 */
	public void changeMapStart() {
		// test
		if (mUI_torii.getStamina() == 0)
			mUI_torii.setStamina(0);
		else
			mUI_torii.setStamina(mUI_torii.getStamina() - 1);
		isTouchable = false;

		new PhysicsThread() {

			@Override
			public void runPhysics() {
				// TODO Auto-generated method stub
				super.runPhysics();
				if (alpha + 8 < 255)
					alpha += 8;
				else {
					alpha = 255;
					isRun = false;
					changeMap();
				}
			}
		}.start();

	}

	/**
	 * 完成切换地图的动作
	 */
	public void changeMapFinish() {
		new PhysicsThread() {

			@Override
			public void runPhysics() {
				// TODO Auto-generated method stub
				super.runPhysics();
				if (alpha - 8 > 0)
					alpha -= 8;
				else {
					alpha = 0;
					isRun = false;
					isTouchable = true;
				}
			}
		}.start();
	}

	/**
	 * 重设游戏的参数
	 */
	private void resetGameStage() {
		// TODO Auto-generated method stub
		setDigEffectImage(-1);
		mUI_depth.reset();
		mSp_digTools.clearAction();
		changeMainDigItem(mUI_toolsBar.mItems[0].id);
		mSp_foundItem.setVisibility(false);
		mUI_toolsBar.refreshAllItem();
		mUI_toolsBar.closeItem();
		mList_digEffect.clear();
	}

	/**
	 * 切换挖地的主工具
	 * @param key
	 */
	private void changeMainDigItem(int key) {

		switch (key) {
		case PLASTIC_SHOVEL:
			setMainDigItem(bms_digAnimate_plastic_shovel[0], PLASTIC_SHOVEL,
					GameStage.Item_State.PlasticShovel.CENTER_POS, Config.GameUILayout.digEffect_center, 0);
			break;
		case IRON_SHOVEL:
			setMainDigItem(bms_digAnimate_iron_shovel[0], IRON_SHOVEL, GameStage.Item_State.IronShovel.CENTER_POS,
					Config.GameUILayout.digEffect_center, 0);
			mSp_digTools.setRotate(0, Config.GameUILayout.digEffect_center.x * scaleSize,
					Config.GameUILayout.digEffect_center.y * scaleSize);
			break;
		case DRILL:
			setMainDigItem(bms_digAnimate_drill[0], DRILL, GameStage.Item_State.Drill.CENTER_POS,
					Config.GameUILayout.digEffect_center, GameStage.Item_State.Drill.ANGLE);
			mSp_digTools.setPosition(mSp_digTools.getX(), mSp_digTools.getY()
					- (scaleSize * GameStage.Item_State.Drill.UP));
			break;
		case PILEDRIVER:
			setMainDigItem(bms_digAnimate_pileDriver[0], PILEDRIVER, GameStage.Item_State.Piledriver.CENTER_POS,
					Config.GameUILayout.digEffect_center, GameStage.Item_State.Piledriver.ANGLE);
			mSp_digTools.setPosition(mSp_digTools.getX(), mSp_digTools.getY()
					- (scaleSize * GameStage.Item_State.Drill.UP));
			break;
		case FISHING_ROD:
			setMainDigItem(bms_digAnimate_plastic_shovel[0], FISHING_ROD,
					GameStage.Item_State.PlasticShovel.CENTER_POS, Config.GameUILayout.digEffect_center, 0);
			break;
		case SPEAR_GUN:
			setMainDigItem(bms_digAnimate_plastic_shovel[0], SPEAR_GUN, GameStage.Item_State.PlasticShovel.CENTER_POS,
					Config.GameUILayout.digEffect_center, 0);

			break;

		default:
			break;
		}
		mSp_digTools.setVisibility(true);
		mSp_digTools.setTouchable(true);
		if (mUI_toolsBar.mItems[0].canBeUse() != UI_digItem.USEABLE)
			mSp_digTools.setVisibility(false);
	}

	private void setMainDigItem(Bitmap bm, int tag, Point position, Point cp, float degree) {
		mSp_digTools.setImage(bm);
		bm_digItem_show = bm;
		mSp_digTools.setTag(tag);
		setSpirtePosition(mSp_digTools, position);
		mSp_digTools.setRotate(degree, cp.x * scaleSize, cp.y * scaleSize);
	}

	// =====================================Animation_Interface=========================================================

	@Override
	public void _finish(BaseSprite sp) {
		// TODO Auto-generated method stub
		if (sp.getName() != null)
			if ("effect".contentEquals(sp.getName())) {
				mList_digEffect.remove(sp);
			}
	}

	@Override
	public void _finishWithKey(int key) {
		// TODO Auto-generated method stub
		switch (key) {
		case DIG_END:
			digEndAction();
			popBubble();
			break;
		case BUBBLE_END:
			mSp_foundItem.setTouchable(true);
			break;
		case DIGING_SHOVEL:
			effect_iron_shovel();
			break;
		case DIGING_DRILL:
			effect_drill();
			break;
		case DIGING_PILE:
			effect_pileDriver();
			break;
		default:
			break;
		}
	}

	// ==========================================Animation===============================================================

	/**
	 * 挖地完成后弹出气泡
	 */
	private void popBubble() {
		// TODO Auto-generated method stub

		mSp_foundItem.setTouchable(false);
		GameAnimate act1 = new GameAnimate(mAnimate_bubble, 50, false, false);
		mSp_foundItem.setVisibility(true);
		act1.setCallBack(this, BUBBLE_END);
		mSp_foundItem.runAction(act1);
	}

	/**
	 * 设置挖地工具的动画
	 * @param key
	 */
	private void setDigItemAnimate(int key) {
		mSp_digTools.setTouchable(false);
		switch (key) {
		case PLASTIC_SHOVEL:
			animate_plastic_shovel();
			break;
		case IRON_SHOVEL:
			animate_iron_shovel();
			break;
		case DRILL:
			animate_drill();
			break;
		case PILEDRIVER:
			animate_pileDriver();
			break;
		case FISHING_ROD:
			// animate_plastic_shovel();
			break;
		case SPEAR_GUN:
			// animate_plastic_shovel();
			break;

		default:
			break;
		}
	}

	/**
	 * 塑料铲的挖地动画
	 */
	private void animate_plastic_shovel() {
		Bitmap[] a = new Bitmap[4];
		Bitmap[] b = new Bitmap[bms_digAnimate_plastic_shovel.length - a.length];
		for (int i = 0; i < bms_digAnimate_plastic_shovel.length; i++) {
			if (i < 4)
				a[i] = bms_digAnimate_plastic_shovel[i];
			else
				b[i - 4] = bms_digAnimate_plastic_shovel[i];
		}

//		GameAnimate act1 = new GameAnimate(bms_digAnimate_plastic_shovel, Config.Value.animate_interval_default, 3,
//				true);
//		act1.setCallBack(this, DIG_END);

		GameAnimate act1 = new GameAnimate(a, Config.Value.animate_interval_default, false, false);
		act1.setCallBack(this, DIGING_SHOVEL);
		mSp_digTools.runAction(act1);
		GameAnimate act2 = new GameAnimate(b, Config.Value.animate_interval_default, false, false);
		mSp_digTools.runAction(act2);
		GameAnimate act3 = new GameAnimate(a, Config.Value.animate_interval_default, false, false);
		act3.setCallBack(this, DIGING_SHOVEL);
		mSp_digTools.runAction(act3);
		GameAnimate act4 = new GameAnimate(b, Config.Value.animate_interval_default, false, false);
		mSp_digTools.runAction(act4);
		GameAnimate act5 = new GameAnimate(a, Config.Value.animate_interval_default, false, false);
		act5.setCallBack(this, DIGING_SHOVEL);
		mSp_digTools.runAction(act5);
		GameAnimate act6 = new GameAnimate(b, Config.Value.animate_interval_default, false, false);
		act6.setCallBack(this, DIG_END);

		mSp_digTools.runAction(act6);

	}

	/**
	 * 铁铲的动画
	 */
	private void animate_iron_shovel() {
		Bitmap[] a = new Bitmap[4];
		Bitmap[] b = new Bitmap[bms_digAnimate_iron_shovel.length - a.length];
		for (int i = 0; i < bms_digAnimate_iron_shovel.length; i++) {
			if (i < 4)
				a[i] = bms_digAnimate_iron_shovel[i];
			else
				b[i - 4] = bms_digAnimate_iron_shovel[i];
		}
		GameAnimate act1 = new GameAnimate(a, Config.Value.animate_interval_default, false, false);
		act1.setCallBack(this, DIGING_SHOVEL);
		mSp_digTools.runAction(act1);
		GameAnimate act2 = new GameAnimate(b, Config.Value.animate_interval_default, false, false);
		mSp_digTools.runAction(act2);
		GameAnimate act3 = new GameAnimate(a, Config.Value.animate_interval_default, false, false);
		act3.setCallBack(this, DIGING_SHOVEL);
		mSp_digTools.runAction(act3);
		GameAnimate act4 = new GameAnimate(b, Config.Value.animate_interval_default, false, false);
		mSp_digTools.runAction(act4);
		GameAnimate act5 = new GameAnimate(a, Config.Value.animate_interval_default, false, false);
		act5.setCallBack(this, DIGING_SHOVEL);
		mSp_digTools.runAction(act5);
		GameAnimate act6 = new GameAnimate(b, Config.Value.animate_interval_default, false, false);
		act6.setCallBack(this, DIG_END);
		mSp_digTools.runAction(act6);
	}

	/**
	 * 钻头的动画
	 */
	private void animate_drill() {
		float x1 = GameStage.Item_State.Drill.CENTER_POS.x * scaleSize;
		float y1 = GameStage.Item_State.Drill.CENTER_POS.y * scaleSize;
		float x2 = x1;
		float y2 = (GameStage.Item_State.Drill.CENTER_POS.y - GameStage.Item_State.Drill.UP) * scaleSize;
		float cx = Config.GameUILayout.digEffect_center.x * scaleSize;
		float cy = Config.GameUILayout.digEffect_center.y * scaleSize;

		GameMoveTo act1 = new GameMoveTo(x1, y1, 150);
		act1.setCallBack(this, DIGING_DRILL);
		mSp_digTools.runAction(act1);

		GameAnimate act2 = new GameAnimate(bms_digAnimate_drill, Config.Value.animate_interval_default, 2, true);
		mSp_digTools.runAction(act2);

		GameMoveTo act3 = new GameMoveTo(x2, y2, 150);
		mSp_digTools.runAction(act3);

		GameRotateTo act4 = new GameRotateTo(cx, cy, Drill.ANGLE, -Drill.ANGLE, 300);
		mSp_digTools.runAction(act4);

		GameMoveTo act5 = new GameMoveTo(x1, y1, 150);
		act5.setCallBack(this, DIGING_DRILL);
		mSp_digTools.runAction(act5);

		GameAnimate act6 = new GameAnimate(bms_digAnimate_drill, Config.Value.animate_interval_default, 2, true);
		mSp_digTools.runAction(act6);

		GameMoveTo act7 = new GameMoveTo(x2, y2, 150);
		mSp_digTools.runAction(act7);

		GameRotateTo act8 = new GameRotateTo(cx, cy, -Drill.ANGLE, Drill.ANGLE, 300);
		act8.setCallBack(this, DIG_END);
		mSp_digTools.runAction(act8);

	}

	/**
	 * 电钻的动画
	 */
	private void animate_pileDriver() {
		float x1 = GameStage.Item_State.Piledriver.CENTER_POS.x * scaleSize;
		float y1 = GameStage.Item_State.Piledriver.CENTER_POS.y * scaleSize;
		float x2 = x1;
		float y2 = (GameStage.Item_State.Piledriver.CENTER_POS.y - GameStage.Item_State.Piledriver.UP) * scaleSize;
		float cx = Config.GameUILayout.digEffect_center.x * scaleSize;
		float cy = Config.GameUILayout.digEffect_center.y * scaleSize;

		GameMoveTo act1 = new GameMoveTo(x1, y1, 150);
		act1.setCallBack(this, DIGING_PILE);
		mSp_digTools.runAction(act1);

		GameAnimate act2 = new GameAnimate(bms_digAnimate_pileDriver, Config.Value.animate_interval_default, 12, true);
		mSp_digTools.runAction(act2);

		GameMoveTo act3 = new GameMoveTo(x2, y2, 150);
		mSp_digTools.runAction(act3);

		GameRotateTo act4 = new GameRotateTo(cx, cy, Piledriver.ANGLE, -Piledriver.ANGLE, 300);
		mSp_digTools.runAction(act4);

		GameMoveTo act5 = new GameMoveTo(x1, y1, 150);
		act5.setCallBack(this, DIGING_PILE);
		mSp_digTools.runAction(act5);

		GameAnimate act6 = new GameAnimate(bms_digAnimate_pileDriver, Config.Value.animate_interval_default, 12, true);
		mSp_digTools.runAction(act6);

		GameMoveTo act7 = new GameMoveTo(x2, y2, 150);
		mSp_digTools.runAction(act7);

		GameRotateTo act8 = new GameRotateTo(cx, cy, -Piledriver.ANGLE, Piledriver.ANGLE, 300);
		act8.setCallBack(this, DIG_END);
		mSp_digTools.runAction(act8);
	}

	// =============================================DIG_EFFECTION=========================================================

	/**
	 * 铁铲挖地时的特效
	 */
	private void effect_iron_shovel() {
		BaseSprite eff = new BaseSprite(bms_digEffect_1[0]);
		eff.setName("effect");
		setSpirtePosition(eff, GameStage.Effect.effect_Iron_Pos);
		GameAnimate act1 = new GameAnimate(bms_digEffect_1, Config.Value.animate_interval_default, false, false);
		act1.setCallBack(this);
		eff.runAction(act1);
		mList_digEffect.add(eff);
	}

	/**
	 * 钻头的特效
	 */
	private void effect_drill() {
		BaseSprite sandEff = new BaseSprite(bms_digEffect_sand[0]);
		sandEff.setName("effect");
		setSpirtePosition(sandEff, GameStage.Effect.effect_Sand_Pos);
		mList_digEffect.add(sandEff);
		BaseSprite stoneEff = new BaseSprite(bms_digEffect_3[0]);
		stoneEff.setName("effect");
		setSpirtePosition(stoneEff, GameStage.Effect.effect_Stone_Pos);
		mList_digEffect.add(stoneEff);

		GameAnimate act1 = new GameAnimate(bms_digEffect_sand, Config.Value.animate_interval_default, 2, false);
		act1.setCallBack(this);
		GameAnimate act2 = new GameAnimate(bms_digEffect_3, Config.Value.animate_interval_default, 2, false);
		act2.setCallBack(this);

		sandEff.runAction(act1);
		stoneEff.runAction(act2);
	}

	/**
	 * 电钻的特效
	 */
	private void effect_pileDriver() {
		BaseSprite sandEff = new BaseSprite(bms_digEffect_sand[0]);
		sandEff.setName("effect");
		setSpirtePosition(sandEff, GameStage.Effect.effect_Sand_Pos);
		mList_digEffect.add(sandEff);
		BaseSprite stoneEff = new BaseSprite(bms_digEffect_2[0]);
		stoneEff.setName("effect");
		setSpirtePosition(stoneEff, GameStage.Effect.effect_Stone_Pos);
		mList_digEffect.add(stoneEff);

		GameAnimate act1 = new GameAnimate(bms_digEffect_sand, Config.Value.animate_interval_default, 2, false);
		act1.setCallBack(this);
		GameAnimate act2 = new GameAnimate(bms_digEffect_2, Config.Value.animate_interval_default, 2, false);
		act2.setCallBack(this);

		sandEff.runAction(act1);
		stoneEff.runAction(act2);

	}
}
