package com.gvpartner.com;

import com.gvpartner.com.base.BaseActivity;

import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
        topLevelClass = BaseActivity.class
)
@GeneratedEntryPoint
@InstallIn(ActivityComponent.class)
public interface BaseActivity_GeneratedInjector {
    void injectBaseActivity(BaseActivity baseActivity);
}