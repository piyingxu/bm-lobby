package com.bm.lobby.service.impl;

import com.github.pagehelper.StringUtil;
import com.bm.lobby.dao.UssdMenuMapper;
import com.bm.lobby.dto.base.MenuDto;
import com.bm.lobby.dto.req.UssdReq;
import com.bm.lobby.dto.res.UssdRes;
import com.bm.lobby.dto.res.UssdSonRes;
import com.bm.lobby.model.UssdMenu;
import com.bm.lobby.service.TestService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/6/27 10:09
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private UssdMenuMapper ussdMenuMapper;

    private static Map<String, Long> timeOutMap = new ConcurrentHashMap<String, Long>();

    private static Map<String, UssdReq> userLastOpMap = new ConcurrentHashMap<String, UssdReq>(); //记录上一次的操作记录

    private static Map<String, Map<String, String>> userTransOpMap = new ConcurrentHashMap<String, Map<String, String>>(); //收集交易操作数据

    @Override
    public UssdRes execute(UssdReq req) {
        //管理会话
        manageSession(req);
        String protocolId = req.getProtocolId();
        UssdRes ussdRes = null;
        UssdMenu menu = ussdMenuMapper.selectByMenuId(req.getProtocolId());
        if (menu == null) {
            return ussdRes;
        }
        List<UssdMenu> menus = ussdMenuMapper.selectByParentId(req.getProtocolId());
        if (menus != null && menus.size() != 0) {
            //有子菜单分两种情况（1、子菜单也是选项; 2、子菜单进入输入收集;）
            if ("showMenu".equals(menu.getAction())) {
                ussdRes = buildUssdRes(ussdRes, menu.getAction(), menu.getTitle(), null);
                for (UssdMenu obj : menus) {
                    ussdRes = buildUssdRes(ussdRes, menu.getAction(), menu.getTitle(), obj);
                }
            } else if ("input".equals(menu.getAction())) {
                //进入第一步
                UssdMenu sonmMenu = menus.get(0);
                ussdRes = buildUssdRes(ussdRes, sonmMenu.getAction(), sonmMenu.getTitle(), sonmMenu);
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put(sonmMenu.getMenuId(), "");
                userTransOpMap.put(req.getMsisdn(), tempMap);
            }
        } else {
            //没有子菜单，找到下一个节点
            if (userTransOpMap.containsKey(req.getMsisdn())) {
                //记录输入的值
                Map<String, String> tempMap = userTransOpMap.get(req.getMsisdn());
                tempMap.put(menu.getMenuId(), req.getParam());
            }
            //推送下一步
            UssdMenu nextmenu = ussdMenuMapper.selectByMenuId(String.valueOf(Integer.parseInt(req.getProtocolId()) + 1));
            if (nextmenu != null) {
                ussdRes = buildUssdRes(ussdRes, nextmenu.getAction(), nextmenu.getTitle(), nextmenu);
                if ("prompt".equals(menu.getAction())) {
                    // 交易完成--关闭会话、清除数据
                    timeOutMap.remove(req.getMsisdn());
                    userLastOpMap.remove(req.getMsisdn());
                    userTransOpMap.remove(req.getMsisdn());
                }
            }
        }
        return ussdRes;
    }

    private void manageSession (UssdReq req) {
        //记录会话信息
        if (!timeOutMap.containsKey(req.getMsisdn()) || (timeOutMap.containsKey(req.getMsisdn()) && timeOutMap.get(req.getMsisdn()) < System.currentTimeMillis())) {
            //创建会话
            long expired = System.currentTimeMillis() + 120 *1000;
            timeOutMap.put(req.getMsisdn(), expired);
            req.setProtocolId("1"); //返回主页(会话不存在或会话失效)
        } else {
           //找出操作记录继续操作（前提条件是本次请求的是主菜单才去判断历史操作，断线后会从主菜单开始操作）
           if ("1".equals(req.getProtocolId()) && userLastOpMap.containsKey(req.getMsisdn())) {
               req = userLastOpMap.get(req.getMsisdn());
           }
        }
        //更新历史操作记录
        userLastOpMap.put(req.getMsisdn(), req);
    }

    private UssdRes buildUssdRes(UssdRes ussdRes, String action, String title, UssdMenu menu) {
        List<MenuDto> menuDtoList = null;
        if (ussdRes == null) {
            ussdRes = new UssdRes();
            UssdSonRes sonRes = new UssdSonRes();
            menuDtoList = new ArrayList<MenuDto>();
            ussdRes.setUSSDResp(sonRes);
            sonRes.setAction(action);
            sonRes.setMenus(menuDtoList);
            sonRes.setTitle(title);
        } else {
            menuDtoList = ussdRes.getUSSDResp().getMenus();
        }
        if (menu != null) {
            MenuDto dto = new MenuDto();
            dto.setMenuName(menu.getMenuName());
            dto.setMenuId(menu.getMenuId());
            menuDtoList.add(dto);
        }
        return ussdRes;
    }
}
