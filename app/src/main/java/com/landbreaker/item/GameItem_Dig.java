package com.landbreaker.item;

public class GameItem_Dig extends GameItem {

	public int durable;
	public int power;

	public GameItem_Dig(int durable, int power) {
		this.durable = durable;
		this.power = power;
	}

	/**
	 * 减少一次耐久，返回flase时道具损毁
	 * 
	 * @return
	 */
	public boolean reductionDurable(int damage) {
		durable -= damage;
		if (durable <= 0)
			return false;
		else
			return true;
	}
}
