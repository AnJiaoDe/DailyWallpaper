package com.cy.dailywallpaper.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cy.permission.PermissionActivity;

/**
 * Created by cy on 2019/4/20.
 */

public abstract class BaseActivity extends PermissionActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
