package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.MenuDto;
import java.util.List;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/6/27 10:11
 */
public class UssdSonRes {

    private String action;

    private List<MenuDto> menus;

    private String title;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<MenuDto> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDto> menus) {
        this.menus = menus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
