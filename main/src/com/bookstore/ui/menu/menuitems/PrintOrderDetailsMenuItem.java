package com.bookstore.ui.menu.menuitems;

import com.bookstore.ui.MenuItemAction;
import com.bookstore.ui.controllers.StoreController;
import com.bookstore.ui.menu.UIState;
import com.bookstore.util.annotations.InjectObject;
import com.bookstore.util.annotations.MenuItemAnnotation;
import com.bookstore.util.annotations.Singleton;

@Singleton
@MenuItemAnnotation(
        title = "Print order details",
        currentState = UIState.MAIN,
        nextState = UIState.MAIN
)
public class PrintOrderDetailsMenuItem implements MenuItem {
    private final int orderPriority = 7;
    @InjectObject
    private StoreController storeController;
    private String title;
    private UIState currentState;
    private UIState nextState;
    private MenuItemAction action = () -> {
        storeController.printOrderDetails();
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
