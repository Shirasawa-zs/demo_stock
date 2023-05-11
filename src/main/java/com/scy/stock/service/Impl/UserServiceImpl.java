package com.scy.stock.service.Impl;

import com.google.common.base.Strings;
import com.scy.stock.mapper.SysUserMapper;
import com.scy.stock.pojo.SysUser;
import com.scy.stock.service.UserService;
import com.scy.stock.vo.req.LoginReqVo;
import com.scy.stock.vo.resp.LoginRespVo;
import com.scy.stock.vo.resp.R;
import com.scy.stock.vo.resp.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author by itheima
 * @Date 2023/5/4
 * @Description 定义服务接口实现
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
       if(vo == null || Strings.isNullOrEmpty(vo.getUsername()) || Strings.isNullOrEmpty(vo.getPassword())){
           return R.error(ResponseCode.DATA_ERROR.getCode(), ResponseCode.DATA_ERROR.getMessage());
        }
       SysUser user = this.sysUserMapper.findByUserName(vo.getUsername());
       if(user == null || !passwordEncoder.matches(vo.getPassword(), user.getPassword())){
           return R.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
       }
       //新建返回数据体
       LoginRespVo respVo = new LoginRespVo();
       //属性名称与类型必须相同，否则copy不到
       BeanUtils.copyProperties(user,respVo);
       return  R.ok(respVo);
    }
}
