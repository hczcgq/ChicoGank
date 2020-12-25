package com.chico.gank.base;

import android.os.Bundle;


/**
 * @author: Chico Chen
 * @date: 2019/5/21
 * @description:
 */
public interface FragmentHelperView {

    void start(BaseFragment baseFragment);

    void start(Class<?> cls);

    void start(Class<?> cls, Bundle bundle);

    void start(BaseFragment baseFragment, int requestCode);

    void start(Class<?> cls, Bundle bundle, int requestCode);

    void start(BaseFragment baseFragment, Class<?> cls, Bundle bundle, int requestCode);
}
