package com.bookstore.ui.menu;

import com.bookstore.ui.menu.menuitems.MenuItem;

import java.util.Map;

public class MenuImpl implements Menu {
    private Map<Integer, MenuItem> currentMenu;

    @Override
    public void printMenu() {
        currentMenu.forEach(((actionIndex, menuItem) -> {
            System.out.println(actionIndex + ": " + menuItem.getTitle());
        }));
    }

    @Override
    public void setCurrentMenu(Map<Integer, MenuItem> currentMenu) {
        this.currentMenu = currentMenu;
    }

    @Override
    public UIState doAction(Integer actionIndex) {
        if (!currentMenu.containsKey(actionIndex)) {
            return null;
        }
        return currentMenu.get(actionIndex).doAction();
    }
}
