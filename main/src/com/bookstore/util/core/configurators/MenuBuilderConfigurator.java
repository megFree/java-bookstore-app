package com.bookstore.util.core.configurators;

import com.bookstore.ui.menu.MenuBuilder;
import com.bookstore.ui.menu.UIState;
import com.bookstore.ui.menu.menuitems.MenuItem;
import com.bookstore.util.core.ApplicationContext;
import com.bookstore.util.helpers.MenuItemsComparator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuBuilderConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> objClass = obj.getClass();
        boolean isAssign = MenuBuilder.class.isAssignableFrom(objClass);
        if (MenuBuilder.class.isAssignableFrom(objClass)) {
            Map<UIState, Map<Integer, MenuItem>> menuItemsMap = new HashMap<>();
            try {
                List<Class<? extends MenuItem>> menuItemsClasses = context.getConfig().getScanner().getSubTypesOf(MenuItem.class);
                List<MenuItem> menuItemsObjects = new ArrayList<>();
                for (Class<? extends MenuItem> cls : menuItemsClasses) {
                    MenuItem menuItem = context.getObject(cls);
                    if (menuItem == null) {
                        continue;
                    }
                    menuItemsObjects.add(context.getObject(cls));
                }

                for (MenuItem item : menuItemsObjects) {
                    if (!menuItemsMap.containsKey(item.getCurrentState())) {
                        menuItemsMap.put(item.getCurrentState(), new HashMap<>());
                    }
                }

                menuItemsObjects.sort(new MenuItemsComparator());

                for (MenuItem item : menuItemsObjects) {
                    int actionIndex = menuItemsMap.get(item.getCurrentState()).size() + 1;
                    menuItemsMap.get(item.getCurrentState()).put(actionIndex, item);
                }

                Field menuItemsField = objClass.getDeclaredField("menuItems");
                menuItemsField.setAccessible(true);
                menuItemsField.set(obj, menuItemsMap);

            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }
}
