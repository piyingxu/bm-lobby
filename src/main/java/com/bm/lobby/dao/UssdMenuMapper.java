package com.bm.lobby.dao;

import com.bm.lobby.model.UssdMenu;
import java.util.List;

public interface UssdMenuMapper {

    UssdMenu selectByMenuId(String menuId);

    List<UssdMenu> selectByParentId(String menuId);

}