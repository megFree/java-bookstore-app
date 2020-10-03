package com.bookstore.ui.menu.menuitems;

import com.bookstore.ui.MenuItemAction;
import com.bookstore.ui.controllers.StoreController;
import com.bookstore.ui.menu.UIState;
import com.bookstore.util.annotations.InjectObject;
import com.bookstore.util.annotations.MenuItemAnnotation;
import com.bookstore.util.annotations.Singleton;

@Singleton
@MenuItemAnnotation(
        title = "Sort alphabetically",
        currentState = UIState.PRINT_REQUESTS_CHOOSE_SORT,
        nextState = UIState.MAIN
)
public class PrintRequestsAlphabeticallySortMenuItem implements MenuItem {
    private final int orderPriority = 6;
    @InjectObject
    private StoreController storeController;
    private String title;
    private UIState nextState;
    private UIState currentState;
    private MenuItemAction action = () -> {
        storeController.printRequestsAlphabetically();
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
