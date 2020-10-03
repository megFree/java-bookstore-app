package com.bookstore.ui.menu;

import com.bookstore.ui.menu.menuitems.MenuItem;

import java.util.Map;

public interface Menu {
    void printMenu();

    void setCurrentMenu(Map<Integer, MenuItem> currentMenu);

    UIState doAction(Integer actionIndex);
}
