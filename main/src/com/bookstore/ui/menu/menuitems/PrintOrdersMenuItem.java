package com.bookstore.ui.menu.menuitems;

import com.bookstore.ui.MenuItemAction;
import com.bookstore.ui.menu.UIState;
import com.bookstore.util.annotations.MenuItemAnnotation;
import com.bookstore.util.annotations.Singleton;

@Singleton
@MenuItemAnnotation(
        title = "Print orders",
        currentState = UIState.MAIN,
        nextState = UIState.PRINT_ORDERS_CHOOSE_SORT
)
public class PrintOrdersMenuItem implements MenuItem {
    private final int orderPriority = 3;
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
