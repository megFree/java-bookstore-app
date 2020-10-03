package com.bookstore.ui.menu.menuitems;

import com.bookstore.ui.menu.UIState;

public interface MenuItem {
    UIState doAction();

    String getTitle();

    UIState getCurrentState();

    int getOrderPriority();
}
