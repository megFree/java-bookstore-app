package com.bookstore.ui.menu;

import com.bookstore.ui.menu.menuitems.MenuItem;
import com.bookstore.util.annotations.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class MenuBuilderImpl implements MenuBuilder {
    private Map<UIState, Map<Integer, MenuItem>> menuItems = new HashMap<>();

    @Override
    public Map<Integer, MenuItem> buildMenu(UIState currentState) {
        return menuItems.get(currentState);
    }

}
