package com.example.acetumor.components;

import android.view.View;
import android.widget.ImageButton;

import com.example.acetumor.R;

public class TestImageButton {
    private ImageButton btn;
    private Boolean click;

    public TestImageButton(ImageButton btn, Boolean click) {
        this.btn = btn;
        this.click = click;
    }

    public ImageButton getBtn() {
        return btn;
    }

    public void setBtn(ImageButton btn) {
        this.btn = btn;
    }

    public Boolean getClick() {
        return click;
    }

    public void setClick(Boolean click) {
        this.click = click;
    }

    public void updateClick() {
        if (!getClick()) setClick(true);
        else setClick(false);
    }
}
