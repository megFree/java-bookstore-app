package com.bookstore.util.annotations;

import com.bookstore.ui.menu.UIState;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MenuItemAnnotation {
    String title();

    UIState currentState();

    UIState nextState();
}
