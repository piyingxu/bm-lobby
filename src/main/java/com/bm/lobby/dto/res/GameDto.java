package com.bm.lobby.dto.res;

import com.bm.lobby.dto.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/1 16:27
 */
public class GameDto extends BaseEntity {

    @ApiModelProperty(value = "游戏ID", example = "1")
    private String gameId;

    @ApiModelProperty(value = "游戏名称", example = "王者荣耀")
    private String gameName;

    @ApiModelProperty(value = "游戏图标", example = "http://xxx.icon")
    private String gameIcon;

    @ApiModelProperty(value = "游戏访问地址", example = "http://xxx")
    private String gameUrl;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }
}
