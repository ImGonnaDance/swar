package com.com2us.wrapper.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;

public class CCustomEditText extends EditText {
    public CCustomEditText(Context context) {
        super(context);
    }

    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if (keyEvent != null && keyEvent.getAction() == 1 && keyEvent.getKeyCode() == 4) {
            CSoftKeyboard.getInstance().onKeyboardHide();
        }
        return super.dispatchKeyEventPreIme(keyEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null && (motionEvent.getAction() & 255) == 1) {
            CSoftKeyboard.getInstance().onKeyboardShow(this);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
