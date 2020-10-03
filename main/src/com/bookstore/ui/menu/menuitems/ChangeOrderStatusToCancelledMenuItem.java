package com.bookstore.ui.menu.menuitems;

import com.bookstore.ui.MenuItemAction;
import com.bookstore.ui.controllers.StoreController;
import com.bookstore.ui.menu.UIState;
import com.bookstore.util.annotations.InjectObject;
import com.bookstore.util.annotations.MenuItemAnnotation;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.enums.OrderStatus;

@Singleton
@MenuItemAnnotation(
        title = "Change order status to CANCELLED",
        currentState = UIState.CHANGE_ORDER_STATUS_CHOOSE,
        nextState = UIState.MAIN
)
public class ChangeOrderStatusToCancelledMenuItem implements MenuItem {
    private final int orderPriority = 0;
    @InjectObject
    private StoreController storeController;
    private String title;
    private UIState nextState;
    private UIState currentState;
    private MenuItemAction action = () -> {
        storeController.changeOrderStatus(OrderStatus.CANCELLED);
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
