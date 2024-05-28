package com.common.wheelproject.home.view;


import com.common.wheel.mvp.IBaseView;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public interface IMainView extends IBaseView {

    void initNavigation();

    String getResourcesHint();

}
