package com.landbreaker.database;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Item_GLOBALARCHIVEMENT_IN_PROGRESS {


	public int player_id;
	public int arch_id;
	public int STATUS;
	public int type;
	public String progress;
    public String start_time;
    public String end_time;


    // Format: BasicSysItem_id:Qty
    public void writeProgress(Map<Integer, Integer> progress){
        if (progress == null || progress.size() <= 0) {
            return;
        }
        String str = null;
        for (Map.Entry<Integer, Integer> entry : progress.entrySet()) {
            if (str == null) {
                str = entry.getKey() + ":" + entry.getValue();
            } else {
                str += ";" + entry.getKey() + ":" + entry.getValue();
            }
        }
        this.progress = str;
    }

    // Format: BasicSysItem_id:Qty
    public Map<Integer, Integer> readProgress() {
        if (this.progress == null) {
            return new HashMap<Integer, Integer>();
        }

        Map<Integer, Integer> progress = new HashMap<Integer, Integer>();
        String[] tempList = this.progress.split(";");
        for (String string : tempList) {
            String[] temp = string.split(":");
            if (temp.length != 2) {
                return null;
            }
            progress.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
        }
        return progress;
    }



}
