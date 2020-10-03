package com.bookstore.ui.menu;

import com.bookstore.util.annotations.InjectObject;
import com.bookstore.util.annotations.PostConstruct;
import com.bookstore.util.annotations.Singleton;

@Singleton
public class UIManager {
    private UIState currentState = UIState.MAIN;

    @InjectObject
    private MenuBuilder menuBuilderImpl;

    @InjectObject
    private CommandListener cl;

    @InjectObject
    private Menu menu;

    @PostConstruct
    public void buildMenu() {
        while (true) {
            menu.setCurrentMenu(menuBuilderImpl.buildMenu(currentState));
            menu.printMenu();
            currentState = menu.doAction(cl.startListening());
            if (currentState == null) {
                currentState = UIState.MAIN;
            }
        }
    }
}
