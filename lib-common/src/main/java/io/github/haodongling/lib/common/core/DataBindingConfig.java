package io.github.haodongling.lib.common.core;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * Author: tangyuan
 * Time : 2021/7/9
 * Description:
 * 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 * 通过这样的方式，来彻底解决 视图调用的一致性问题，
 * 如此，视图实例的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
 * 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。
 */
public class DataBindingConfig {
    private final int layout;
    private  int vmVariableId;
    private  ViewModel stateViewModel;
    private SparseArray bindingParams = new SparseArray();
    public DataBindingConfig(@NonNull Integer layout){
        this(layout,0,null);
    }
    public DataBindingConfig(@NonNull Integer layout, Integer vmVariableId, @Nullable ViewModel stateViewModel) {
        this.layout = layout;
        if (vmVariableId!=0&&stateViewModel!=null){
            this.vmVariableId = vmVariableId;
            this.stateViewModel = stateViewModel;
        }
    }

    public int getLayout() {
        return this.layout;
    }

    public int getVmVariableId() {
        return this.vmVariableId;
    }

    public ViewModel getStateViewModel() {
        return this.stateViewModel;
    }

    public SparseArray getBindingParams() {
        return this.bindingParams;
    }

    public DataBindingConfig addBindingParam(@NonNull Integer variableId, @NonNull Object object) {
        if (this.bindingParams.get(variableId) == null) {
            this.bindingParams.put(variableId, object);
        }

        return this;
    }
}
