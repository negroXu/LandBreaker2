package com.landbreaker.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

// 资料功能类别
public class Table_BASICITEM {
	// 表格名称
	public static final String TABLE_NAME = "BASICITEM";
	private Context contex;
	// 编号表格字段名称，固定不变
	public static final String KEY_ID = "id";

	// 其它表格字段名称
	public static final String STATUS_COLUMN = "status";
	public static final String NAME_COLUMN = "NAME";
	public static final String PIC_URL_COLUMN = "pic_url";
	public static final String DESCRIPTION_COLUMN = "description";
	public static final String LEVEL_COLUMN = "LEVEL";
	public static final String RATE_COLUMN = "rate";
	public static final String SELL_PRICE_COLUMN = "sell_price";
	public static final String BUY_PRICE_COLUMN = "buy_price";
	public static final String EXP_COLUMN = "exp";

	// 使用上面宣告的变量建立表格的SQL指令 ('10001', '1', '便当盒', null, '便当盒', '0', '1', '5', '999999', '0')
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID
			+ " INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " + STATUS_COLUMN + " INTEGER DEFAULT '1', "
			+ NAME_COLUMN + " CHAR(100) DEFAULT NULL UNIQUE, " + PIC_URL_COLUMN + " CHAR(255) DEFAULT NULL, "
			+ DESCRIPTION_COLUMN + " TEXT, " + LEVEL_COLUMN + " INTEGER NOT NULL, " + RATE_COLUMN
			+ " DOUBLE NOT NULL, " + SELL_PRICE_COLUMN + " INTEGER DEFAULT '0', " + BUY_PRICE_COLUMN
			+ " INTEGER DEFAULT '999999', " +EXP_COLUMN + " INTEGER DEFAULT '0'" +")"; // " PRIMARY KEY ('id')," +"UNIQUE KEY 'id' ('id')," +"UNIQUE KEY 'NAME' ('NAME')"+

	// 数据库物件
	private SQLiteDatabase db;

	// 建构子，一般的应用都不需要修改
	public Table_BASICITEM(Context context) {
		db = MyDBHelper.getDatabase(context);
		this.contex = context;
	}

	// 关闭数据库，一般的应用都不需要修改
	public void close() {
		db.close();
	}

	// 新增参数指定的物件
	public Item_BASICITEM insert(Item_BASICITEM item) {
		// 建立准备新增资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的新增资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		cv.put(KEY_ID, item.id);
		cv.put(STATUS_COLUMN, item.status);
		cv.put(NAME_COLUMN, item.NAME);
		cv.put(PIC_URL_COLUMN, item.pic_url);
		cv.put(DESCRIPTION_COLUMN, item.description);
		cv.put(LEVEL_COLUMN, item.LEVEL);
		cv.put(RATE_COLUMN, item.rate);
		cv.put(SELL_PRICE_COLUMN, item.sell_price);
		cv.put(BUY_PRICE_COLUMN, item.buy_price);
		cv.put(EXP_COLUMN, item.exp);

		// 新增一笔资料并取得编号
		// 第一个参数是表格名称
		// 第二个参数是没有指定字段值的默认值
		// 第三个参数是包装新增资料的ContentValues物件
		long id = db.insert(TABLE_NAME, null, cv);
		//Toast.makeText(contex, "" + id, Toast.LENGTH_SHORT).show();
		// 回传结果
		return item;
	}

	// 修改参数指定的物件
	public boolean update(Item_BASICITEM item) {
		// 建立准备修改资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的修改资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		// cv.put(KEY_ID, item.id);
		cv.put(STATUS_COLUMN, item.status);
		cv.put(NAME_COLUMN, item.NAME);
		cv.put(PIC_URL_COLUMN, item.pic_url);
		cv.put(DESCRIPTION_COLUMN, item.description);
		cv.put(LEVEL_COLUMN, item.LEVEL);
		cv.put(RATE_COLUMN, item.rate);
		cv.put(SELL_PRICE_COLUMN, item.sell_price);
		cv.put(BUY_PRICE_COLUMN, item.buy_price);
		cv.put(EXP_COLUMN, item.exp);

		// 设定修改资料的条件为编号
		// 格式为“字段名称＝资料”
		String where = KEY_ID + "=" + item.id;

		// 执行修改资料并回传修改的资料数量是否成功
		return db.update(TABLE_NAME, cv, where, null) > 0;
	}

	// 删除参数指定编号的资料
	public boolean delete(long id) {
		// 设定条件为编号，格式为“字段名称=资料”
		String where = KEY_ID + "=" + id;
		// 删除指定编号资料并回传删除是否成功
		return db.delete(TABLE_NAME, where, null) > 0;
	}

	// 删除表内所有数据
	public void deleteTable(){
		db.execSQL("delete from " + TABLE_NAME);
	}

	// 读取所有记事资料
	public List<Item_BASICITEM> getAll() {
		List<Item_BASICITEM> result = new ArrayList<Item_BASICITEM>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}

		cursor.close();
		return result;
	}

	// 取得指定编号的资料物件
	public Item_BASICITEM get(long id) {
		// 准备回传结果用的物件
		Item_BASICITEM item = null;
		// 使用编号为查询条件
		String where = KEY_ID + "=" + id;
		// 执行查询
		Cursor result = db.query(TABLE_NAME, null, where, null, null, null, null, null);

		// 如果有查询结果
		if (result.moveToFirst()) {
			// 读取包装一笔资料的物件
			item = getRecord(result);
		}

		// 关闭Cursor物件
		result.close();
		// 回传结果
		return item;
	}

	// 把Cursor目前的资料包装为物件
	public Item_BASICITEM getRecord(Cursor cursor) {
		// 准备回传结果用的物件
		Item_BASICITEM result = new Item_BASICITEM();

		result.id = cursor.getLong(0);
		result.status = cursor.getInt(1);
		result.NAME = cursor.getString(2);
		result.pic_url = cursor.getString(3);
		result.description = cursor.getString(4);
		result.LEVEL = cursor.getInt(5);
		result.rate = cursor.getDouble(6);
		result.sell_price = cursor.getInt(7);
		result.buy_price = cursor.getInt(8);
		result.exp = cursor.getInt(9);

		// 回传结果
		return result;
	}

	// 取得资料数量
	public int getCount() {
		int result = 0;
		Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

		if (cursor.moveToNext()) {
			result = cursor.getInt(0);
		}

		return result;
	}

	// 建立范例资料
	public void sample() {

		String a = "INSERT INTO `BASICITEM` VALUES ('10001', '1', '吃了一半的便当盒', '还剩下很多炸鸡！Luck~可是没有筷子【如果是野外求生的话，一定会把它吃完】', null, '0', '1', '5', '999999', '5'), ('10002', '1', '木质纽扣', '一个普通的木质纽扣【会不会是从某个性感大姐姐的胸前蹦下来的】', null, '0', '1', '5', '999999', '5'), ('10003', '1', '破烂的梳子', '发霉了！上面还长了菇【这烂木头梳子上的蘑菇能吃么】', null, '0', '1', '5', '999999', '5'), ('10004', '1', '焗炉手套', '前提是要有焗炉【还没有劳保手套那么方便】', null, '0', '1', '5', '999999', '5'), ('10005', '1', '烟蒂', '已经无法抽了【这年头收集烟蒂也换不了钱】', null, '0', '1', '5', '999999', '5'), ('10006', '1', '早操卡', '哇！盖得满满的', null, '0', '1', '5', '999999', '5'), ('10007', '1', '吉娃娃的屎', '可恶！脏死了！！【Hooooly  Shiiiit！】', null, '0', '1', '5', '999999', '5'), ('10008', '1', '妈妈的马丁靴', '看上去像MS道具（我为什么会知道）【S.M！女王！哦~性感的大长腿！】', null, '0', '1', '5', '999999', '5'), ('10009', '1', 'Madao', '额···还是装作没看见吧【为什么会挖出大活人？！】', null, '0', '1', '5', '999999', '5'), ('10010', '1', '海带', '我要吃肉啊！！！【晒干它，来煲老火靓汤吧。】', null, '0', '1', '5', '999999', '5'), ('10011', '1', '鱼骨', '吃上去没什么味道，喂猫好了。【把它炸酥脆了也还是能吃的。】', null, '0', '1', '5', '999999', '5'), ('10012', '1', '开花牙刷', '呜哇，这人是用多大力刷牙啊【蓬成这个样子，不光刷牙也刷了鞋吧？】', null, '0', '1', '5', '999999', '5'), ('10013', '1', '纸巾团', '不知道包裹着什么，但是有点暖【这手感……里面好像有些暖暖的粘稠液体】', null, '0', '1', '5', '999999', '5'), ('10014', '1', '牛郎店小卡片', '卡片上的小子根本没我帅【要是找小姐的卡片或许有用】', null, '0', '1', '5', '999999', '5'), ('10015', '1', '徽章', '居然是我最喜欢的魔法少女小蜘蛛的限量版徽章！', null, '0', '1', '5', '999999', '5'), ('10016', '1', '手表带', '真皮的手表带【真皮的手表带，但没有另外一段】', null, '0', '1', '5', '999999', '5'), ('10017', '1', '车胎', '呵呵，备胎（自嘲）', null, '0', '1', '5', '999999', '5'), ('10018', '1', '小云酱的新曲BD', '可是演唱会门票抽选券不见了', null, '0', '1', '5', '999999', '5'), ('10019', '1', '厕纸芯', '一点用处都没有啊【我小时候曾经拿它来当作单筒望远镜】', null, '0', '1', '5', '999999', '5'), ('10020', '1', '按摩券', '看一下限期已经过了60年了【来来来，大保健！】', null, '0', '1', '5', '999999', '5'), ('10021', '1', '蝉蜕', '嘎嘣脆鸡肉味【可以入药，但我搞不懂中药配方】', null, '1', '0.8', '5', '999999', '10'), ('10022', '1', '小木牛', '头一直在晃动，相当魔性', null, '1', '0.8', '5', '999999', '10'), ('10023', '1', '筷子', '筷子？然而并没有什么卵用！说起来有点饿【筷子有了，还差一碗拉面。】', null, '1', '0.8', '5', '999999', '10'), ('10024', '1', '某人房间的钥匙', '奇怪的形状到底是打开什么锁的呢？【隐藏着黑暗力量的钥匙啊！在我面前……等等，我并不是魔法少女！】', null, '1', '0.8', '5', '999999', '10'), ('10025', '1', '假牙', '好像是万圣节道具【戴上它根本合不上嘴啊！】', null, '1', '0.8', '5', '999999', '10'), ('10026', '1', '五日元', '如果每天都能挖到，相信我很快可以变成富豪', null, '1', '0.8', '5', '999999', '10'), ('10027', '1', '搞笑的眼镜框', '可以当做生日礼物送人【很少人会买的搞笑道具】', null, '1', '0.8', '5', '999999', '10'), ('10028', '1', '当年的0分试卷', '噗！哈哈哈··等等！这个名字··', null, '1', '0.8', '5', '999999', '10'), ('10029', '1', '一捆旧报纸', '报纸上面登载的照片好像是年轻的校长…一定是我的错觉', null, '1', '0.8', '5', '999999', '10'), ('10030', '1', '收音机', '流浪汉三大法宝其中之一', null, '1', '0.8', '5', '999999', '10'), ('10031', '1', '翻花绳', '一个人就能玩，我才不是没有朋友呢！', null, '1', '0.8', '5', '999999', '10'), ('10032', '1', '波子汽水空瓶', '怎么会是空的啊！', null, '1', '0.8', '5', '999999', '10'), ('10033', '1', '游乐中心代币', '现在哪里都不会用这种代币了…', null, '1', '0.8', '5', '999999', '10'), ('10034', '1', '残破漫画书', '都已经破破烂烂的了，但是却十分好看！', null, '1', '0.8', '5', '999999', '10'), ('10035', '1', '保温瓶', '倒上一杯热茶，身体好暖~', null, '1', '0.8', '5', '999999', '10'), ('10036', '1', '月代头头套', '传说中只有真正的帅哥才能驾驭的发型！', null, '1', '0.8', '5', '999999', '10'), ('10037', '1', '泥丸子', '泥丸子的光滑程度和制作人的用心程度是成正比的', null, '1', '0.8', '5', '999999', '10'), ('10038', '1', '仙女棒', '啊！快要熄灭了！赶快把另一支仙女棒拿过来！', null, '1', '0.8', '5', '999999', '10'), ('10039', '1', '彩色贝壳', '虽然随处可见，但是确实很漂亮啊', null, '1', '0.8', '5', '999999', '10'), ('10040', '1', '石币', '拿着这东西付钱肯定会累死', null, '1', '0.8', '5', '999999', '10'), ('10041', '1', '不知名的野兽牙', '代表与野兽搏斗的勇气……虽然只不过是捡来的', null, '1', '0.8', '5', '999999', '10'), ('10042', '1', '带有牙印的肉干', '这肉干，比石头还硬！', null, '1', '0.8', '5', '999999', '10'), ('10043', '1', '手制弓', '只有弓没有箭啊……', null, '1', '0.8', '5', '999999', '10'), ('10044', '1', '石头哑铃', '这种东西我单手就……阿勒！？', null, '1', '0.8', '5', '999999', '10'), ('10045', '1', '锋利的石刃', '太锋利了，这东西真的是石头做的吗', null, '1', '0.8', '5', '999999', '10'), ('10046', '1', '叶子人字拖', '叶梗的地方断掉的话真是灾难……', null, '1', '0.8', '5', '999999', '10'), ('10047', '1', '陶罐', '画着奇怪的图案', null, '1', '0.8', '5', '999999', '10'), ('10048', '1', '一撮麦子', '是直接吃掉呢还是拿来播种，这是个问题', null, '1', '0.8', '5', '999999', '10'), ('10049', '1', '野性豹纹', '这种太野性了不适合我带', null, '2', '0.5', '5', '999999', '20'), ('10050', '1', '粉红小蕾丝', '摸上去有点小激动【少女情怀总是春】', null, '2', '0.5', '5', '999999', '20'), ('10051', '1', '纯情小雏菊', '纯洁！嗯！纯洁！【应该会是班花喜欢的款式吧】', null, '2', '0.5', '5', '999999', '20'), ('10052', '1', '劲爆香蕉', '这香蕉图案有点微妙', null, '2', '0.5', '5', '999999', '20'), ('10053', '1', '小贝壳', '美人鱼套装？！【贝壳太小，遮不住大胸】', null, '2', '0.5', '5', '999999', '20'), ('10054', '1', '蓝白条', '两个碗【老板，再来两碗白米饭！】', null, '2', '0.5', '5', '999999', '20'), ('10055', '1', '热情桃心', '好情趣！【款式很庸俗，但摸上去让人幻想翩翩】', null, '2', '0.5', '5', '999999', '20'), ('10056', '1', 'kuma', '嗯嗯，叔叔我喜欢【小萝莉的最爱，呵呵呵呵（口水）……】', null, '2', '0.5', '5', '999999', '20'), ('10057', '1', 'cherryboy', '跟我现在穿的是同款呢', null, '2', '0.5', '5', '999999', '20'), ('10058', '1', '草莓百分百', '闻上去果真有草莓的味道', null, '2', '0.5', '5', '999999', '20'), ('10059', '1', '基佬紫', 'so sex【看着看着就硬了】', null, '2', '0.5', '5', '999999', '20'), ('10060', '1', '人气同款', '貌似很多明星都穿这款【B站的超人气舞姬穿的就是这款】', null, '2', '0.5', '5', '999999', '20'), ('10061', '1', '蓝白碗', '经典！【经典！简直是圣物！】', null, '2', '0.5', '5', '999999', '20'), ('10062', '1', '纯情少女', '太普通了【太普通了。如果是妹子穿过的原味才值得留下来】', null, '2', '0.5', '5', '999999', '20'), ('10063', '1', '圣诞快乐', '里面有张爸爸冒充圣诞老人打的欠条【这么小的圣诞袜。礼物只能塞进几根辣条吧？！】', null, '2', '0.5', '5', '999999', '20'), ('10064', '1', '迷情渔网', '马上穿上试试【马上发个微博去撕逼！快说你是黑丝党还是渔网党？】', null, '2', '0.5', '5', '999999', '20'), ('10065', '1', '足球王子', '可惜我是棒球迷【只有小孩子喜欢这种鲜艳的大红色】', null, '2', '0.5', '5', '999999', '20'), ('10066', '1', '破旧白袜', '有股难以言喻的味道【呜哇……余味绕梁，三日不绝。】', null, '2', '0.5', '5', '999999', '20'), ('10067', '1', '小丑袜', '穿了才发现两只都是右脚【跟某些偶像打歌服也很配的长袜】', null, '2', '0.5', '5', '999999', '20'), ('10068', '1', '足袋', '鱼人穿不了的袜子（冷笑话）【我穿了大半个月的袜子终于能换了】', null, '2', '0.5', '5', '999999', '20'), ('10069', '1', '小学鸡', '刚好可以做手机套', null, '2', '0.5', '5', '999999', '20'), ('10070', '1', '红豆汤', '里面只剩下3颗红豆', null, '2', '0.5', '5', '999999', '20'), ('10071', '1', '可乐', '完全没有气了【来，干了这瓶83年的可乐！】', null, '2', '0.5', '5', '999999', '20'), ('10072', '1', '咖啡', '为什么里面的液体是绿色的【喝了还是觉得很困，而且肚子也不太舒服】', null, '2', '0.5', '5', '999999', '20'), ('10073', '1', '精力剂', '一天不喝浑身难受【出奇迹！一天不喝我就浑身难受！】', null, '2', '0.5', '5', '999999', '20'), ('10074', '1', 'bb柠檬', '一滴都不剩了【医学证明——剩下的量不足每天所需的维生素C】', null, '2', '0.5', '5', '999999', '20'), ('10075', '1', '麒麟啤酒', '不胜酒力【竟然是上个世纪的陈年佳酿】', null, '2', '0.5', '5', '999999', '20'), ('10076', '1', '关东煮', '完整的！【汤多料少不厚道】', null, '2', '0.5', '5', '999999', '20'), ('10077', '1', '乌龙茶', '虽然只剩下一口了【没有冰红茶那么好喝】', null, '2', '0.5', '5', '999999', '20'), ('10078', '1', '益力多', '地底的温度应该达到存放标准【撕开盖子，闻到的像是细菌尸体的酸味】', null, '2', '0.5', '5', '999999', '20'), ('10079', '1', '宝矿力', '想起CM的女星【一口闷。然后忍不住像怪物猎人那样猛地一抬手挺腰，仿佛充满了力量。】', null, '2', '0.5', '5', '999999', '20'), ('10080', '1', '地藏', '随处可见的地藏菩萨雕像', null, '2', '0.5', '5', '999999', '20'), ('10081', '1', '天狗', '传说面具后面是个软妹子？！【应该不会是东方project的周边吧？】', null, '2', '0.5', '5', '999999', '20'), ('10082', '1', '招财猫', '我感觉今天会中大奖【按照这运势继续挖，会挖到金矿吧？】', null, '2', '0.5', '5', '999999', '20'), ('10083', '1', '天线机器人', '一个奇怪的机器人，有根天线难道可以控制他？【技术宅快来研发个遥控器】', null, '2', '0.5', '5', '999999', '20'), ('10084', '1', '狐狸', '一个表情生动的狐狸雕像【供着它，会不会跑出个狐仙妹子？】', null, '2', '0.5', '5', '999999', '20'), ('10085', '1', '狸猫', '圆滚滚的狸猫雕像【它拿着这些道具是要去公共澡堂么？】', null, '2', '0.5', '5', '999999', '20'), ('10086', '1', '佛陀', '看着就想点额头上的痣', null, '2', '0.5', '5', '999999', '20'), ('10087', '1', '风神', '后面的风袋居然是布料材质的，太精细了', null, '2', '0.5', '5', '999999', '20'), ('10088', '1', '雷神', '后面的雷鼓居然能敲响的，太精细了', null, '2', '0.5', '5', '999999', '20'), ('10089', '1', '祖玛神像', '为什么会挖到这么奇怪的神像【放回土里再埋几年，然后拿去古董市场卖吧】', null, '2', '0.5', '5', '999999', '20'), ('10090', '1', '护目镜', '没有镜片，只是装装样子而已', null, '2', '0.5', '5', '999999', '20'), ('10091', '1', '遮羞叶', '讨厌啦~不要揭开', null, '2', '0.5', '5', '999999', '20'), ('10092', '1', '水晶戒指', '好漂亮！（但是戴在手上好容易被划伤）', null, '2', '0.5', '5', '999999', '20'), ('10093', '1', '兽牙项梁', '将兽牙戴在身上这是古时代的时尚吗', null, '2', '0.5', '5', '999999', '20'), ('10094', '1', '骨头回旋镖', '嘎啦嘎啦~', null, '2', '0.5', '5', '999999', '20'), ('10095', '1', '菊石化石', '螺旋的形状，不是和钻头很像吗？', null, '2', '0.5', '5', '999999', '20'), ('10096', '1', '带骨熟肉', '一本满足！', null, '2', '0.5', '5', '999999', '20'), ('10097', '1', '有点开裂的恐龙蛋', '这是什么东西的蛋？嗯？刚才它是不是动了一下！', null, '2', '0.5', '5', '999999', '20'), ('10098', '1', '石头滑板', '乘着滑板，我就是风之子！', null, '2', '0.5', '5', '999999', '20'), ('10099', '1', '石制足球', '啊啊啊啊啊我的脚啊啊啊啊啊！！', null, '2', '0.5', '5', '999999', '20'), ('10100', '1', '蝙蝠', '洞穴顶上密密麻麻的全是这玩意……', null, '2', '0.5', '5', '999999', '20'), ('10101', '1', '洞穴蟹', '意外地美味！不要问我为什么知道……', null, '2', '0.5', '5', '999999', '20'), ('10102', '1', '岩尾蛇', '被尾巴抽到了，痛得我都要哭出来了！', null, '2', '0.5', '5', '999999', '20'), ('10103', '1', '喧嚣飞虫', '放着不管的话要吵死人了！', null, '2', '0.5', '5', '999999', '20'), ('10104', '1', '拳击蜈蚣', '明明是蜈蚣却会用右摆拳', null, '2', '0.5', '5', '999999', '20'), ('10105', '1', '钟乳泥', '漂亮的钟乳石就是由这泥浆构成的吗……无法接受啊', null, '2', '0.5', '5', '999999', '20'), ('10106', '1', '蝙蝠便便', '一定要带伞，否则蝙蝠的便便就会落到头上', null, '2', '0.5', '5', '999999', '20'), ('10107', '1', '钟乳石', '好漂亮的形状！', null, '2', '0.5', '5', '999999', '20'), ('10108', '1', '矿泉水', '要是有冰镇就好了', null, '2', '0.5', '5', '999999', '20'), ('10109', '1', '手电筒', '直到电池用光为止都是洞穴里最可靠的道具', null, '2', '0.5', '5', '999999', '20'), ('10110', '1', '朱雀石', '四圣石其中之一，上面有红色的羽毛纹路', null, '2', '0.5', '5', '999999', '20'), ('10111', '1', '玄武石', '四圣石其中之一，上面有黑色的龟甲纹路', null, '2', '0.5', '5', '999999', '20'), ('10112', '1', '青龙石', '四圣石其中之一，上面有青色的鳞片纹路', null, '2', '0.5', '5', '999999', '20'), ('10113', '1', '白虎石', '四圣石其中之一，上面有白色的虎斑纹路', null, '2', '0.5', '5', '999999', '20'), ('10114', '1', '燕雀石', '比羽毛还轻，但却比钢铁还硬', null, '2', '0.5', '5', '999999', '20'), ('10115', '1', '硫磺石', '有种臭鸡蛋的味道', null, '2', '0.5', '5', '999999', '20'), ('10116', '1', '爆焰石', '千万要小心不要踩到……', null, '2', '0.5', '5', '999999', '20'), ('10117', '1', '妖媚石', '盯着看的话仿佛灵魂都要被吸进去', null, '2', '0.5', '5', '999999', '20'), ('10118', '1', '七彩精', '世上所有的颜色都在里面了', null, '2', '0.5', '5', '999999', '20'), ('10119', '1', '风怒石', '贴着耳朵的话能够呼啸的风声', null, '2', '0.5', '5', '999999', '20'), ('10120', '1', '巨大机器人的右手', '组成巨大机器人的右手，集合起来的话……', null, '2', '0.5', '5', '999999', '20'), ('10121', '1', '巨大机器人的左手', '组成巨大机器人的左手，集合起来的话……', null, '2', '0.5', '5', '999999', '20'), ('10122', '1', '巨大机器人的左脚', '组成巨大机器人的左脚，集合起来的话……', null, '2', '0.5', '5', '999999', '20'), ('10123', '1', '巨大机器人的右脚', '组成巨大机器人的右脚，集合起来的话……', null, '2', '0.5', '5', '999999', '20'), ('10124', '1', '巨大机器人的躯干', '组成巨大机器人的躯干，集合起来的话……', null, '2', '0.5', '5', '999999', '20'), ('10125', '1', '土偶', '不可思议地浮在空中', null, '2', '0.5', '5', '999999', '20'), ('10126', '1', '怀表', '除了能确认时间之外也能用作催眠道具', null, '2', '0.5', '5', '999999', '20'), ('10127', '1', '燧发枪', '每开一次枪都要再装填子弹弹药，真是有够麻烦的', null, '2', '0.5', '5', '999999', '20'), ('10128', '1', '古铜钟', '不吊起来的话完全敲不响啊……', null, '2', '0.5', '5', '999999', '20'), ('10129', '1', '巨大齿轮组', '完美契合的齿轮组，是哪里的机器的零件吗？', null, '2', '0.5', '5', '999999', '20'), ('10130', '1', '断掉的触角', '不知从哪里扯下来的触角，看着就很痛', null, '2', '0.5', '5', '999999', '20'), ('10131', '1', '光波枪', '哔哔哔哔哔~', null, '2', '0.5', '5', '999999', '20'), ('10132', '1', '培养槽', '里面的东西要是跑出来就麻烦了', null, '2', '0.5', '5', '999999', '20'), ('10133', '1', 'UFO铜锣烧', '据说是宇宙人的热门点心，但其实是地球人的产品', null, '2', '0.5', '5', '999999', '20'), ('10134', '1', '吉吉多尔目击照片', '目击者冒着生命危险拍摄下来', null, '2', '0.5', '5', '999999', '20'), ('10135', '1', '宇航服头盔', '好重……不是到了宇宙的话最好还是不要戴', null, '2', '0.5', '5', '999999', '20'), ('10136', '1', '一瓶绿色粘液', '好恶心！刚才粘液里好像还张开了眼睛！？！？', null, '2', '0.5', '5', '999999', '20'), ('10137', '1', '干枯的怪手模型', '是宇宙人的手……等等……宇宙人有手的吗？', null, '2', '0.5', '5', '999999', '20'), ('10138', '1', '变异改造牛', '被宇宙人抓走的家畜，改造之后又丢回地面', null, '2', '0.5', '5', '999999', '20'), ('10139', '1', '飞船救生舱', '被送到地球的宇宙人救生舱，来的是救世主还是侵略者呢……', null, '2', '0.5', '5', '999999', '20'), ('10140', '1', '草莓蛋糕', '上面的草莓呢！！', null, '3', '0.1', '5', '999999', '50'), ('10141', '1', '栗子蛋糕', '被踩了一脚，不过还是很好吃', null, '3', '0.1', '5', '999999', '50'), ('10142', '1', '芒果千层', '一层~两层~三层~四层~五层【只有一块是远远满足不了我的胃袋的】', null, '3', '0.1', '5', '999999', '50'), ('10143', '1', '黑森林', '巧克力有点变味了', null, '3', '0.1', '5', '999999', '50'), ('10144', '1', '芝士蛋糕', '已经融得差不多了【不小心摔在了地上，正犹豫还要不要捡起来吃】', null, '3', '0.1', '5', '999999', '50'), ('10145', '1', '甜甜圈', '已经被咬了一口【TMD又是谁偷吃了一口？像狗啃的一样！】', null, '3', '0.1', '5', '999999', '50'), ('10146', '1', '校长的假发', '校长你没事吧【校长果然是大光头！】', null, '3', '0.1', '5', '999999', '50'), ('10147', '1', '校长家的钥匙', '要不要通知他老人家呢【看起来十分严肃的校长竟然喜欢如此少女风的挂饰】', null, '3', '0.1', '5', '999999', '50'), ('10148', '1', '校长的金牙', '虽然恶心，不过很值钱', null, '3', '0.1', '5', '999999', '50'), ('10149', '1', '校长的眼镜', '戴了会儿，头晕目眩【比瓶底还厚的镜片说的就是这个吧】', null, '3', '0.1', '5', '999999', '50'), ('10150', '1', '校长的钱包', '没有钱呢··等等！上面有偷拍田中训导主任的照片！我知道了不得了的事情', null, '3', '0.1', '5', '999999', '50'), ('10151', '1', '校长的手帕', '校长掉落的手帕，上面有训导主任的姓？！', null, '3', '0.1', '5', '999999', '50'), ('10152', '1', '校长的水手服', '我又知道了不应该知道的秘密，好害怕【您老人家跟水手服老爷爷是秘密的偶像组合吗？】', null, '3', '0.1', '5', '999999', '50'), ('10153', '1', '一万', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10154', '1', '九万', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10155', '1', '一筒', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10156', '1', '九筒', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10157', '1', '一索', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10158', '1', '九索', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10159', '1', '东', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10160', '1', '南', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10161', '1', '西', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10162', '1', '北', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10163', '1', '中', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10164', '1', '发', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10165', '1', '白', '一只很普通的麻将，集齐一幅可以召唤什么吗？', null, '3', '0.1', '5', '999999', '50'), ('10166', '1', '兽皮紧身衣', '穿上这样的衣服不会被认为是变态嘛', null, '3', '0.1', '5', '999999', '50'), ('10167', '1', '兽骨勺子', '出乎意料地好用，但是太容易折断了吧', null, '3', '0.1', '5', '999999', '50'), ('10168', '1', '兽皮披风', '套上之后……好闷热！', null, '3', '0.1', '5', '999999', '50'), ('10169', '1', '兽皮斧头', '这么巨大的斧头，到底是什么动物的骨头做成的', null, '3', '0.1', '5', '999999', '50'), ('10170', '1', '兽骨不求人', '挠在恰到好处的地方，好舒服~', null, '3', '0.1', '5', '999999', '50'), ('10171', '1', '女巫', '“谢谢你……这瓶药剂就作为谢礼吧哦呵呵~”——我是不会喝的哦！', null, '3', '0.1', '5', '999999', '50'), ('10172', '1', '洞穴探险家', '“谢谢你救了我”——到底为什么会挖出人类啊……', null, '3', '0.1', '5', '999999', '50'), ('10173', '1', '恐惧的洞穴探险家', '“妈妈啊啊啊啊！！”——只会发出刺耳的尖叫，这家伙到底遇见了什么', null, '3', '0.1', '5', '999999', '50'), ('10174', '1', '穴居人', '“#￥%&！*&%$%”——完全听不懂', null, '3', '0.1', '5', '999999', '50'), ('10175', '1', '微笑的小女孩', '“呵呵呵嘻嘻嘻嘻嘻……”——这笑声好可怕啊', null, '3', '0.1', '5', '999999', '50'), ('10176', '1', '回音石', '能够完美重现声音', null, '3', '0.1', '5', '999999', '50'), ('10177', '1', '魔眼石', '透过这块石头看女生的话……会被逮捕的！', null, '3', '0.1', '5', '999999', '50'), ('10178', '1', '麻痹石', '动！弹！不！得！', null, '3', '0.1', '5', '999999', '50'), ('10179', '1', '捆绑石', '不要光顾着看啊快来救我！', null, '3', '0.1', '5', '999999', '50'), ('10180', '1', '烟雾石', '咳咳……什么都……咳咳……看不清……', null, '3', '0.1', '5', '999999', '50'), ('10181', '1', '金刚杵', '会发出雷光的武器，好强！', null, '3', '0.1', '5', '999999', '50'), ('10182', '1', '扭曲时钟', '靠近的话连时间都会被扭曲的道具', null, '3', '0.1', '5', '999999', '50'), ('10183', '1', '武士光剑', 'I am your father！', null, '3', '0.1', '5', '999999', '50'), ('10184', '1', '蒸汽喷射背包', '带上它就能飞了，但是蒸汽好烫啊，完全不能用', null, '3', '0.1', '5', '999999', '50'), ('10185', '1', '巨大机器人的头部', '组成巨大机器人的头部，集合起来的话……', null, '3', '0.1', '5', '999999', '50'), ('10186', '1', '校长伪装皮套', '校长他……原来是外星人！？那么现在里面的“它”到底去哪了呢', null, '3', '0.1', '5', '999999', '50'), ('10187', '1', '全息投影手表', '怎么都是马赛克的啊……信号不好吗？喂？喂！', null, '3', '0.1', '5', '999999', '50'), ('10188', '1', '战斗力观察眼镜', '已经碎掉了，让我看看，最后的观察数据……这不是校长的名字吗！？', null, '3', '0.1', '5', '999999', '50'), ('10189', '1', '保姆机器人', '怎么不是女仆外形的啊！！！', null, '3', '0.1', '5', '999999', '50'), ('10190', '1', '训导主任的MIB身份卡', '带着墨镜的训导主任……额……一点也不适合啊', null, '3', '0.1', '5', '999999', '50'), ('10191', '1', '住有考试之神的护身符', '我是学霸，谁与争锋', '/LandBreaker/item/4/yushou.png', '4', '0.05', '5', '999999', '100'), ('10192', '1', '刻着当红明星名字的竖笛', '偷偷收起来等没人的时候再用（奸笑）', '/LandBreaker/item/4/dizi.png', '4', '0.05', '5', '999999', '100'), ('10193', '1', '时光锦囊', '里面有几本写着如何统治世界的计划书，真是羞耻play', '/LandBreaker/item/4/timebox.png', '4', '0.05', '5', '999999', '100'), ('10194', '1', '一个奇怪操作的手柄', '奇怪的手柄感觉像控制某些机械装置的【只要按下这个按钮，世界就会毁灭。不要这样做啊，我的右手！】', '/LandBreaker/item/4/yaokongqi.png', '4', '0.05', '5', '999999', '100'), ('10195', '1', '限量版纯金套套', '得到了一个不得了的东西！【摩擦的时候会掉24K金粉吗？】', '/LandBreaker/item/4/goldentt.png', '4', '0.05', '5', '999999', '100'), ('10196', '1', '玻璃鞋', '似曾相识的玻璃鞋，而且很合我的脚。【悄悄试穿的时候不小心摔出了裂纹，档次降低了】', '/LandBreaker/item/4/shuijingxie.png', '4', '0.05', '5', '999999', '100'), ('10197', '1', '阿拉丁的神灯', '打开神灯一看，里面居然装着咖喱。【到底是哪位大妈把它当调料壶来用了？】', '/LandBreaker/item/4/shendeng.png', '4', '0.05', '5', '999999', '100'), ('10198', '1', '妖刀村正', '啊，我的身体不受控制了【街头巷尾的小混混再也不敢欺负我了】', '/LandBreaker/item/4/yaodao.png', '4', '0.05', '5', '999999', '100'), ('10199', '1', '忍者秘籍', '上面模糊写着“射yoooo术”？【翻开后的第一件事就是找有没有色诱术】', '/LandBreaker/item/4/juanzhou.png', '4', '0.05', '5', '999999', '100'), ('10200', '1', '贴着封条的电饭锅', '怎么看怎么眼熟，自觉告诉我不要碰【散发出不详的气息，里面是什么罪恶深重的东西？】', '/LandBreaker/item/4/dianfanbao.png', '4', '0.05', '5', '999999', '100'), ('10201', '1', '长牙狮摔跤头套', '戴上之后仿佛获得了无尽的力量！但是理智……', null, '4', '0.05', '5', '999999', '100'), ('10202', '1', '冰封猛犸象', '要是冰融了的话……', null, '4', '0.05', '5', '999999', '100'), ('10203', '1', '刻有发光文字的石板', '能够从文字里读到未来的真理', null, '4', '0.05', '5', '999999', '100'), ('10204', '1', '洞穴宝箱（空）', '千辛万苦找到的宝箱……居然是空的', null, '4', '0.05', '5', '999999', '100'), ('10205', '1', '探险者的遗书', '字迹已经模糊不清了', null, '4', '0.05', '5', '999999', '100'), ('10206', '1', '剩下一发的左轮枪', '最后一发子弹……是留给自己的', null, '4', '0.05', '5', '999999', '100'), ('10207', '1', '弹力球', '真正的弹力球高手连反弹的轨迹都能计算到！“达到自己了，好痛！”', null, '4', '0.05', '5', '999999', '100'), ('10208', '1', '冰晶', '永远都不会融化的冰之矿石，光靠近都要被冻结了', null, '4', '0.05', '5', '999999', '100'), ('10209', '1', '肥皂', '后面突然出现一个黑人不知道在等什么【在公共澡堂里掉了就千万别去捡】', null, '4', '0.05', '5', '999999', '100'), ('10210', '1', '变身腰带', '戴上之后就能够成为英雄，但是再也变不回人类了……还是三思而后行吧', null, '4', '0.05', '5', '999999', '100'), ('10211', '1', '石中剑', '看来是没有成为王的资质啊……', null, '4', '0.05', '5', '999999', '100'), ('10212', '1', '电磁方尖塔', '360°无差别攻击，但是只要站在塔底就完全没问题了', null, '4', '0.05', '5', '999999', '100'), ('10213', '1', '瓶中黑洞', '不要打开盖子……千万不要！', null, '4', '0.05', '5', '999999', '100'), ('10214', '1', '超宇宙兽·吉吉多尔', '汪~！', null, '4', '0.05', '5', '999999', '100'), ('10215', '1', '时间机器', '“真希望我们能够回到过去的时光”', null, '4', '0.05', '5', '999999', '100');";

		db.execSQL(a);
	}

}