package com.scy.stock.service;

import com.scy.stock.vo.req.LoginReqVo;
import com.scy.stock.vo.resp.LoginRespVo;
import com.scy.stock.vo.resp.R;

public interface UserService {
    R<LoginRespVo> login(LoginReqVo vo);
}
