package com.bookstore.ui.menu.menuitems;

import com.bookstore.ui.MenuItemAction;
import com.bookstore.ui.menu.UIState;
import com.bookstore.util.annotations.MenuItemAnnotation;
import com.bookstore.util.annotations.Singleton;

@Singleton
@MenuItemAnnotation(
        title = "Change order status",
        currentState = UIState.MAIN,
        nextState = UIState.CHANGE_ORDER_STATUS_CHOOSE
)
public class ChangeOrderStatusMenuItem implements MenuItem {
    private final int orderPriority = 12;
    private String title;
    private UIState currentState;
    private UIState nextState;
    private MenuItemAction action = () -> {
    };

    @Override
    public UIState doAction() {
        action.doAction();
        return nextState;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public UIState getCurrentState() {
        return currentState;
    }

    @Override
    public int getOrderPriority() {
        return orderPriority;
    }
}
