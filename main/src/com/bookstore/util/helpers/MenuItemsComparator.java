package com.bookstore.util.helpers;

import com.bookstore.ui.menu.menuitems.MenuItem;

import java.util.Comparator;

public class MenuItemsComparator implements Comparator<MenuItem> {
    @Override
    public int compare(MenuItem o1, MenuItem o2) {
        if (o1.getOrderPriority() < o2.getOrderPriority()) {
            return -1;
        }
        return 1;
    }
}
