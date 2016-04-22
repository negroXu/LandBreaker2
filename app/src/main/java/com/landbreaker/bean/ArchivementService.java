package com.landbreaker.bean;

import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.util.ArrayMap;
import android.util.Log;

import com.landbreaker.activity.NewActivity;
import com.landbreaker.database.Item_BASICARCHIVEMENT;
import com.landbreaker.database.Item_BASICITEM;
import com.landbreaker.database.Item_BASICITEM_BASICARCHIVEMENT;
import com.landbreaker.database.Item_BASICSYSTEMITEM;
import com.landbreaker.database.Item_BASICSYSTEMITEM_BASICARCHIVEMENT;
import com.landbreaker.database.Item_GLOBALARCHIVEMENT_IN_PROGRESS;
import com.landbreaker.database.Table_BASICARCHIVEMENT;
import com.landbreaker.database.Table_BASICITEM_BASICARCHIVEMENT;
import com.landbreaker.database.Table_BASICSYSTEMITEM_BASICARCHIVEMENT;
import com.landbreaker.database.Table_GLOBALARCHIVEMENT_IN_PROGRESS;
import com.landbreaker.internet.InternetApi;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArchivementService {

	public static final int new_GAList_completed = 1;
	public static final int new_GAList_inProgress = 2;
	public static final int update_GAList_inProgress = 3;
	public static final int delete_GAList_inProgress = 4;

    public static final int STATUS_INPROGRESS = 1;
    public static final int STATUS_COMPLETED = 2;

    private Context context;

    // 前置参数
    private List<Item_BASICARCHIVEMENT> basicArchivementList = new ArrayList<Item_BASICARCHIVEMENT>();
    private Map<Integer, Item_BASICARCHIVEMENT> basicArchivementMap = new HashMap<Integer, Item_BASICARCHIVEMENT>();
    private List<Item_BASICITEM_BASICARCHIVEMENT> basicItemToBasicArchivementList = new ArrayList<Item_BASICITEM_BASICARCHIVEMENT>();
    private List<Item_BASICSYSTEMITEM_BASICARCHIVEMENT> basicSystemItemToBasicArchivementList = new ArrayList<Item_BASICSYSTEMITEM_BASICARCHIVEMENT>();

    // 数据库
    private Table_GLOBALARCHIVEMENT_IN_PROGRESS table_globalarchivement_in_progress= null;
    private Table_BASICARCHIVEMENT table_basicarchivement = null;
    private Table_BASICITEM_BASICARCHIVEMENT table_basicitem_basicarchivement = null;
    private Table_BASICSYSTEMITEM_BASICARCHIVEMENT table_basicsystemitem_basicarchivement = null;

    public final static int type_collect_item = 1;// 收集掉落物品成就(eg:累计挖10个，累计挖到88宝箱)
    public final static int type_collect_systemItem = 2;// 收集系统物品成就(eg:累计挖到888钻石，累计挖到88宝箱)
    public final static int type_use_systemItem = 3;// 使用系统物品成就(eg:累计使用88888金币，累计开启123宝箱)
    public final static int type_buy_systemItem = 4;// 购买、充值系统物品成就(eg:累计购买了1888钻石，累计购买666钥匙)
    public final static int type_sell_item = 5;// 卖出掉落物品成就(eg: 卖出了50个朱雀石)
    public final static int type_sell_systemItem = 6;// 卖出系统物品的成就(eg:卖出了5个钻石宝箱)
    public final static int type_level = 7;// 等级成就(eg:达到10级)

    public ArchivementService(Context context) throws Exception {
        this.context = context;
        init();
    }
	public void init() throws Exception{
        // 获取基础成就信息
        table_basicarchivement = new Table_BASICARCHIVEMENT(context);
        basicArchivementList = table_basicarchivement.getAll();
        table_basicarchivement.close();
        table_basicarchivement = null;
        for (Item_BASICARCHIVEMENT basicArchivement : basicArchivementList) {
            basicArchivementMap.put(basicArchivement.getId(), basicArchivement);
        }

        // 获取掉落物品与成就的关联信息
        table_basicitem_basicarchivement = new Table_BASICITEM_BASICARCHIVEMENT(context);
        basicItemToBasicArchivementList = table_basicitem_basicarchivement.getAll();
        table_basicitem_basicarchivement.close();
        table_basicitem_basicarchivement = null;

        // 获取系统物品与成就的关联信息
        table_basicsystemitem_basicarchivement = new Table_BASICSYSTEMITEM_BASICARCHIVEMENT(context);
        basicSystemItemToBasicArchivementList = table_basicsystemitem_basicarchivement.getAll();
        table_basicsystemitem_basicarchivement.close();
        table_basicsystemitem_basicarchivement = null;

        table_globalarchivement_in_progress = new Table_GLOBALARCHIVEMENT_IN_PROGRESS(context);

	}

    public void destory(){

//        for(Item_GLOBALARCHIVEMENT_IN_PROGRESS item_globalarchivement_in_progress : table_globalarchivement_in_progress.getSTATUS(2)){
//            Log.i("table_globalarchivement_in_progress getAll", item_globalarchivement_in_progress.arch_id + "&" + item_globalarchivement_in_progress.progress);
//        }

        // 关闭数据库
        table_globalarchivement_in_progress.close();
        table_globalarchivement_in_progress = null;
    }

    /**
     * NOTE Get Player Archivement List
     * @param player
     * @return
     * @throws Exception
     */
    public List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> getPlayerArchivement(PlayerList player, Integer status)
            throws Exception {

        if (player == null || player.id == 0) {
            return null;
        }
        if (status == null) {
            status = 0;
        }
        List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> globalArchivementList = new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>();
        switch (status) {
            case STATUS_COMPLETED:
                globalArchivementList.addAll(table_globalarchivement_in_progress.getSTATUS(2));
                break;
            case STATUS_INPROGRESS:
                globalArchivementList.addAll(table_globalarchivement_in_progress.getSTATUS(1));
                break;
            default:
                globalArchivementList.addAll(table_globalarchivement_in_progress.getSTATUS(2));
                globalArchivementList.addAll(table_globalarchivement_in_progress.getSTATUS(1));
                break;
        }
        return globalArchivementList;
    }

    /**
     * NOTE UpdateArchivement with player info
     * @param player
     * @param feedList
     * @throws Exception
     */
    public Map<String, String> updateArchivement(PlayerList player, List<Feed> feedList) throws Exception{
        if (player == null || feedList == null || feedList.size() <= 0) {
            return null;
        }
        // 打开数据库
        table_globalarchivement_in_progress = new Table_GLOBALARCHIVEMENT_IN_PROGRESS(context);

        // 返回的信息
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> tempMap = new HashMap<String, String>();

        // (1)获取必要对象列表
        // 获取玩家现有的成就记录
        List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> globalArchivementList = getPlayerArchivement(player, null);

        // (2)准备待更新的对象
        Map<Integer, List<Item_GLOBALARCHIVEMENT_IN_PROGRESS>> updateMap = new HashMap<Integer, List<Item_GLOBALARCHIVEMENT_IN_PROGRESS>>();
        // 待新增的已完成的成就
        updateMap.put(new_GAList_completed, new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>());
        // 待新增的进行中的成就
        updateMap.put(new_GAList_inProgress, new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>());
        // 待更新的进行中的成就
        updateMap.put(update_GAList_inProgress, new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>());
        // 待删除的进行中的成就
        updateMap.put(delete_GAList_inProgress, new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>());

        for (int i = 0; i < feedList.size(); i++) {
            switch (feedList.get(i).type) {
                case Feed.FEED_TYPE_SYNC:// 挖掘同步
                //case Feed.type_system_get:// 系统提供的信息
                    // 检查是否消耗了挖掘的装备
//                    if (feedList.get(i).map_equip != 0) {
//                        tempMap = checkArchivement(updateMap,
//                                globalArchivementList, player,
//                                BasicArchivement.type_use_systemItem,
//                                feedList.get(i).getMap_equip(), feedList.get(i)
//                                        .getTime(), 1);
//                    }
                    if (feedList.get(i).system_id >= 10000) {// 掉落的物品id 是5位数以上的
                        tempMap = checkArchivement(updateMap,
                                globalArchivementList, player,
                                type_collect_item,
                                feedList.get(i).system_id,
                                feedList.get(i).time, feedList.get(i).qty);
                    } else {// 掉落系统物品时
                        tempMap = checkArchivement(updateMap,
                                globalArchivementList, player,
                                type_collect_systemItem,
                                feedList.get(i).system_id,
                                feedList.get(i).time, feedList.get(i).qty);
                    }
                    break;
                case Feed.FEED_TYPE_BUY:
                    tempMap = checkArchivement(updateMap,
                            globalArchivementList, player,
                            type_buy_systemItem,
                            feedList.get(i).system_id,
                            feedList.get(i).time, feedList.get(i).qty);
                    break;
                case Feed.FEED_TYPE_SELL:
                    if (feedList.get(i).system_id >= 10000) {// 卖出掉落的物品id 是5位数以上的
                        tempMap = checkArchivement(updateMap,
                                globalArchivementList, player,
                                type_sell_item,
                                feedList.get(i).system_id,
                                feedList.get(i).time, feedList.get(i).qty);
                    } else {// 掉落系统物品时
                        tempMap = checkArchivement(updateMap,
                                globalArchivementList, player,
                                type_sell_systemItem,
                                feedList.get(i).system_id,
                                feedList.get(i).time, feedList.get(i).qty);
                    }
                    break;
                case Feed.FEED_TYPE_USE:
                    tempMap = checkArchivement(updateMap,
                            globalArchivementList, player,
                            type_use_systemItem,
                            feedList.get(i).system_id,
                            feedList.get(i).time, feedList.get(i).qty);
                    break;

                default:
                    //throw new BusinessException(ResultCode.FEEDTYPE_ERROR);
            }
            // 处理返回的信息
            if (tempMap != null && tempMap.size() > 0) {
                resultMap.putAll(tempMap);
                tempMap.clear();
            }
        }
        // 更新数据库
        if(updateMap.get(new_GAList_completed) != null){
            for(int i = 0; i < updateMap.get(new_GAList_completed).size(); i++){
                table_globalarchivement_in_progress.insert(updateMap.get(new_GAList_completed).get(i));
            }
        }
        if(updateMap.get(new_GAList_inProgress) != null){
            for(int i = 0; i < updateMap.get(new_GAList_completed).size(); i++){
                table_globalarchivement_in_progress.insert(updateMap.get(new_GAList_inProgress).get(i));
            }
        }
        if(updateMap.get(update_GAList_inProgress) != null){
            for(int i = 0; i < updateMap.get(update_GAList_inProgress).size(); i++){
                table_globalarchivement_in_progress.update(updateMap.get(update_GAList_inProgress).get(i));
            }
        }
        if(updateMap.get(delete_GAList_inProgress) != null){
            for(int i = 0; i < updateMap.get(delete_GAList_inProgress).size(); i++){
                table_globalarchivement_in_progress.delete(updateMap.get(delete_GAList_inProgress).get(i).arch_id,STATUS_INPROGRESS);
            }
        }


        return resultMap;
    }

    /**
     * NOTE CheckArchivement function
     * @param updateMap
     * @param playerGlobalArchivementList
     * @param player
     * @param archType
     * @param itemId
     * @param getTime
     * @return
     * @throws Exception
     */
    private Map<String, String> checkArchivement(
            Map<Integer, List<Item_GLOBALARCHIVEMENT_IN_PROGRESS>> updateMap,
            List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> playerGlobalArchivementList, PlayerList player,
            Integer archType, Integer itemId, Timestamp getTime, Integer qty)
            throws Exception {

        // 判断updateMap 的key值是否有误
        Set<Integer> tempSet = updateMap.keySet();
        for (Integer string : tempSet) {
            switch (string) {
                case new_GAList_completed:
                case new_GAList_inProgress:
                case update_GAList_inProgress:
                case delete_GAList_inProgress:
                    break;
                default:
                    Log.e("checkArchivement","updateMap key error");
                    //throw new BusinessException(ResultCode.UNKNOWN_ERROR);
            }
        }

        // 返回的信息
        Map<String, String> resultMap = new HashMap<String, String>();

        // 放置符合条件的系统物品成就信息
        List<Item_BASICSYSTEMITEM_BASICARCHIVEMENT> tempSIAList = new ArrayList<Item_BASICSYSTEMITEM_BASICARCHIVEMENT>();
        // 放置符合条件的掉落物品成就信息
        List<Item_BASICITEM_BASICARCHIVEMENT> tempIAList = new ArrayList<Item_BASICITEM_BASICARCHIVEMENT>();

        // 查找此物品是否有对应的成就信息
        if (itemId > 10000) { // 掉落的物品id 是5位数以上的
            for (Item_BASICITEM_BASICARCHIVEMENT basicItemToBasicArchivement : basicItemToBasicArchivementList) {
                if (basicItemToBasicArchivement.basicitem_id == itemId
                        && basicArchivementMap.get(basicItemToBasicArchivement.basicarch_id).STATUS == Item_BASICARCHIVEMENT.STATUS_ACTIVE
                        && basicArchivementMap.get(basicItemToBasicArchivement.basicarch_id).type == archType) {
                    tempIAList.add(basicItemToBasicArchivement);
                }
            }
        } else { // 系统物品的id
            for (Item_BASICSYSTEMITEM_BASICARCHIVEMENT basicSystemItemToBasicArchivement : basicSystemItemToBasicArchivementList) {
                if (basicSystemItemToBasicArchivement.basicsystemitem_id == itemId // 在基础成就表找到此系统物品相应的成就
                        && basicArchivementMap.get(basicSystemItemToBasicArchivement.basicarch_id).STATUS == Item_BASICARCHIVEMENT.STATUS_ACTIVE// 判断此成就是否active
                        && basicArchivementMap.get(basicSystemItemToBasicArchivement.basicarch_id).type == archType) {// 判断此成就是否为相应的成就类型
                    tempSIAList.add(basicSystemItemToBasicArchivement);
                }
            }
        }

        // 找到符合条件的系统物品成就时
        if (tempSIAList.size() > 0) {
            for (int j = 0; j < tempSIAList.size(); j++) {
                boolean haveArch = false;
                Item_GLOBALARCHIVEMENT_IN_PROGRESS tempGA = new Item_GLOBALARCHIVEMENT_IN_PROGRESS();// 存放成就信息
                // 寻找玩家的成就列表中是否有此成就
                if (playerGlobalArchivementList != null && playerGlobalArchivementList.size() > 0) {
                    for (int j2 = 0; j2 < playerGlobalArchivementList.size(); j2++) {
                        if (tempSIAList.get(j).basicarch_id == playerGlobalArchivementList.get(j2).arch_id) {
                            haveArch = true;
                            tempGA = playerGlobalArchivementList.get(j2);//把用户已有的成就信息放到tempGA等待处理
                            break; // 跳出循环
                        }
                    }
                }
                if (!haveArch) {// 如果玩家信息中没有此条成就时，添加进行中成就
                    tempGA.arch_id = tempSIAList.get(j).basicarch_id;
                    tempGA.player_id = (int) player.id;
                    tempGA.start_time = getTime.toString();
                    tempGA.STATUS = STATUS_INPROGRESS;
                    tempGA.type = basicArchivementMap.get(tempSIAList.get(j).basicarch_id).type;
                }
                // 处理成就信息
                if (tempGA.STATUS == STATUS_INPROGRESS) { // 如果此成就正在进行中，更新成就信息
                    Map<Integer, Integer> tempProgress = tempGA.readProgress();//ReadProgress(tempGA.progress);
                    Integer tempQty = tempProgress.get(itemId);
                    tempProgress.put(itemId, tempQty == null ? qty : tempQty + qty); // 更新成就信息

                    tempGA.writeProgress(tempProgress);

                    boolean archCompleted = true;
                    if (tempProgress.get(itemId) >= tempSIAList.get(j).num) { // 判断消耗的数量是否达到成就所要求得数量
                        // 完成此成就的条件时，判断该成就的其他条件是否也已经完成
                        List<Item_BASICSYSTEMITEM_BASICARCHIVEMENT> tempBSI_BA = new ArrayList<Item_BASICSYSTEMITEM_BASICARCHIVEMENT>();
                        for(int i = 0; i < basicSystemItemToBasicArchivementList.size(); i ++){
                            if(basicSystemItemToBasicArchivementList.get(i).basicarch_id == tempSIAList.get(j).basicarch_id){
                                tempBSI_BA.add(basicSystemItemToBasicArchivementList.get(i));
                            }
                        }
                        //List<Item_BASICSYSTEMITEM_BASICARCHIVEMENT> tempBSI_BA = basicSystemItemToBasicArchivementList.get(tempSIAList.get(j).basicarch_id)   basicArchivementMap.get(tempSIAList.get(j).basicarch_id). .getBasicSystemItemToBasicArchivementList();
                        for (Item_BASICSYSTEMITEM_BASICARCHIVEMENT basicSystemItemToBasicArchivement : tempBSI_BA) {
                            if (tempProgress.get(basicSystemItemToBasicArchivement.basicsystemitem_id) == null
                                    || tempProgress.get(basicSystemItemToBasicArchivement.basicsystemitem_id) < basicSystemItemToBasicArchivement.num) {
                                archCompleted = false;
                                break;
                            }
                        }
                    } else {
                        // 未完成时
                        archCompleted = false;
                    }
                    resultMap = processArchivement(archCompleted, tempGA, updateMap, playerGlobalArchivementList, player, getTime);
                } else if (tempGA.STATUS != STATUS_COMPLETED) {
                    Log.e("checkArchivement","UNKNOWN_ERROR1");
                    //throw new BusinessException(ResultCode.UNKNOWN_ERROR);
                }
            }
        }

        // 找到掉落物品的成就时
        if (tempIAList.size() > 0) {
            for (int i = 0; i < tempIAList.size(); i++) {
                boolean haveArch = false;
                Item_GLOBALARCHIVEMENT_IN_PROGRESS tempGA = new Item_GLOBALARCHIVEMENT_IN_PROGRESS();// 存放成就信息
                // 寻找玩家的成就列表中是否有此成就
                if (playerGlobalArchivementList != null && playerGlobalArchivementList.size() > 0) {
                    for (int j = 0; j < playerGlobalArchivementList.size(); j++) {
                        if (tempIAList.get(i).basicarch_id == playerGlobalArchivementList.get(j).arch_id) {
                            haveArch = true;
                            tempGA = playerGlobalArchivementList.get(j);//把用户已有的成就信息放到tempGA等待处理
                            break; // 跳出循环
                        }
                    }
                }
                if (!haveArch) {// 如果玩家信息中没有此条成就时，添加进行中成就
                    tempGA.arch_id = tempIAList.get(i).basicarch_id;
                    tempGA.player_id = (int)player.id;
                    tempGA.start_time = getTime.toString();
                    tempGA.STATUS = STATUS_INPROGRESS;
                    tempGA.type = basicArchivementMap.get(tempIAList.get(i).basicarch_id).type;
                }
                // 处理成就信息
                if (tempGA.STATUS == STATUS_INPROGRESS ) { // 如果此成就正在进行中，更新成就信息
                    Map<Integer, Integer> tempProgress =  tempGA.readProgress(); //ReadProgress(tempGA.progress);
                    Integer tempQty = tempProgress.get(itemId);
                    tempProgress.put(itemId, tempQty == null ? qty : tempQty + qty); // 更新成就信息
                    tempGA.writeProgress(tempProgress);
                    boolean archCompleted = true;
                    if(tempProgress.get(itemId) >= tempIAList.get(i).num) { // 判断消耗的数量是否达到成就所要求得数量
                        // 完成此成就的条件时，判断该成就的其他条件是否也已经完成
                        List<Item_BASICITEM_BASICARCHIVEMENT> tempBI_BA = new ArrayList<Item_BASICITEM_BASICARCHIVEMENT>();
                        for(int i2 = 0; i2 < basicItemToBasicArchivementList.size(); i2 ++){
                            if(basicSystemItemToBasicArchivementList.get(i2).basicarch_id == tempSIAList.get(i).basicarch_id){
                                tempBI_BA.add(basicItemToBasicArchivementList.get(i2));
                            }
                        }
                        //List<BasicItemToBasicArchivement> tempBI_BA = basicArchivementMap.get(tempIAList.get(i).getBasicarch_id()).getBasicItemToBasicArchivementList();
                        for (Item_BASICITEM_BASICARCHIVEMENT basicItemToBasicArchivement : tempBI_BA) {
                            if (tempProgress.get(basicItemToBasicArchivement.basicitem_id) == null
                                    || tempProgress.get(basicItemToBasicArchivement.basicitem_id) < basicItemToBasicArchivement.num) {
                                archCompleted = false;
                                break;
                            }
                        }
                    } else {
                        // 未完成时
                        archCompleted = false;
                    }
                    resultMap = processArchivement(archCompleted, tempGA, updateMap, playerGlobalArchivementList, player, getTime);
                } else if(tempGA.STATUS != STATUS_COMPLETED) {
                    Log.e("checkArchivement","UNKNOWN_ERROR2");
                    //throw new BusinessException(ResultCode.UNKNOWN_ERROR);
                }
            }
        }

        return resultMap;
    }

    /**
     * NOTE ProcessArchivement
     * @param archCompleted
     * @param globalArchivement
     * @param updateMap
     * @param playerGlobalArchivementList
     * @param player
     * @param getTime
     * @return
     * @throws Exception
     */
    private Map<String, String> processArchivement(
            boolean archCompleted, Item_GLOBALARCHIVEMENT_IN_PROGRESS globalArchivement,
            Map<Integer, List<Item_GLOBALARCHIVEMENT_IN_PROGRESS>> updateMap,
            List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> playerGlobalArchivementList, PlayerList player,
            Timestamp getTime) throws Exception {
        // 返回的信息
        Map<String, String> resultMap = new HashMap<String, String>();

        if (archCompleted) {// 如果该成就的全部条件满足时
            globalArchivement.STATUS = STATUS_COMPLETED;
            globalArchivement.progress = null;
            globalArchivement.end_time = getTime.toString();
            updateMap.get(new_GAList_completed).add(globalArchivement);
            if (playerGlobalArchivementList != null && playerGlobalArchivementList.contains(globalArchivement)) {
                updateMap.get(delete_GAList_inProgress).add(globalArchivement);
            } else {
                playerGlobalArchivementList.add(globalArchivement);
            }
            // 更新player 的成就点
            int archPoint = 0;
            for (Item_GLOBALARCHIVEMENT_IN_PROGRESS globalArchivement2 : playerGlobalArchivementList) {
                if (globalArchivement2.STATUS == STATUS_COMPLETED) {
                    archPoint += basicArchivementMap.get(globalArchivement2.arch_id).point;
                }
            }
            player.archievement_point = archPoint;
            // 返回完成的信息
            // {archivement_id : 2}
            resultMap.put(globalArchivement.arch_id+"", globalArchivement.STATUS + "");
        } else {// 未完成时
            if (playerGlobalArchivementList != null && playerGlobalArchivementList.contains(globalArchivement)) {
                if (updateMap.get(new_GAList_inProgress) == null
                        || !updateMap.get(new_GAList_inProgress).contains(globalArchivement)) {
                    if (updateMap.get(update_GAList_inProgress) == null
                            || updateMap.get(update_GAList_inProgress).size() <= 0
                            || !updateMap.get(update_GAList_inProgress).contains(globalArchivement)) {
                        updateMap.get(update_GAList_inProgress).add(globalArchivement);
                    }
                }
            } else {
                playerGlobalArchivementList.add(globalArchivement);
                updateMap.get(new_GAList_inProgress).add(globalArchivement);
            }
        }
        return resultMap;
    }


}
