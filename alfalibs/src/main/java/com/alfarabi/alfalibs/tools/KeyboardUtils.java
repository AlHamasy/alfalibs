package com.alfarabi.alfalibs.tools;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.support.v7.app.AppCompatActivity;

public class KeyboardUtils {

    public static void hideKeyboard(Context context) {
        if(context instanceof AppCompatActivity){
            View view = ((AppCompatActivity) context).findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void showKeyboard(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void addKeyboardVisibilityListener(View rootLayout, OnKeyboardVisibiltyListener onKeyboardVisibiltyListener) {
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootLayout.getWindowVisibleDisplayFrame(r);
            int screenHeight = rootLayout.getRootView().getHeight();

            // r.bottom is the position above soft keypad or device button.
            // if keypad is shown, the r.bottom is smaller than that before.
            int keypadHeight = screenHeight - r.bottom;

            boolean isVisible = keypadHeight > screenHeight * 0.15; // 0.15 ratio is perhaps enough to determine keypad height.
            onKeyboardVisibiltyListener.onVisibilityChange(isVisible);
        });
    }

    public interface OnKeyboardVisibiltyListener {
        void onVisibilityChange(boolean isVisible);
    }
}