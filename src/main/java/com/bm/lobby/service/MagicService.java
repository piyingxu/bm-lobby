package com.bm.lobby.service;

import com.bm.lobby.enums.MagicEnum;

public interface MagicService {

    long getOrUpMagic (MagicEnum magic, String pid, long addCount);


}
