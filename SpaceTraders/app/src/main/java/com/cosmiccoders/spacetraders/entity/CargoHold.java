package com.cosmiccoders.spacetraders.entity;

import java.util.HashMap;
import java.util.Map;

public class CargoHold {
    private int max;
    private Map<TradeGood, Integer> inventory;

    public CargoHold(int max) {
        this.max = max;
        inventory = new HashMap<>();
    }

    public void putItem(TradeGood good) {
        if (inventory.size() == max) {
            //place some error here
            return;
        }
        if (inventory.containsKey(good)) {
            int temp = inventory.get(good);
            inventory.put(good, temp);
        } else {
            inventory.put(good, 1);
        }
    }

    public void takeItem(TradeGood good, int amount) {
        if (inventory.containsKey(good)) {
            int temp = inventory.get(good);
            if(amount < temp) {
                inventory.put(good, temp - amount);
            } else if (amount == temp) {
                inventory.remove(good);
            } else {
                // error message for when you don't have enough items
                return;
            }
        } else {
            //give an error message for the item does not exist
            return;
        }
    }

    public int getMax() { return max; }

    public Map<TradeGood, Integer> getInventory() { return inventory; }
}
