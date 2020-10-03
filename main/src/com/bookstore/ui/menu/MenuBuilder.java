package com.bookstore.ui.menu;

import com.bookstore.ui.menu.menuitems.MenuItem;

import java.util.Map;

public interface MenuBuilder {
    Map<Integer, MenuItem> buildMenu(UIState currentState);
}
