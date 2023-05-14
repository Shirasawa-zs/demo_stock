package com.scy.stock.service.Impl;

import com.google.common.base.Strings;
import com.scy.stock.mapper.SysUserMapper;
import com.scy.stock.pojo.SysUser;
import com.scy.stock.service.UserService;
import com.scy.stock.utils.IdWorker;
import com.scy.stock.utils.RedisConstants;
import com.scy.stock.vo.req.LoginReqVo;
import com.scy.stock.vo.resp.LoginRespVo;
import com.scy.stock.vo.resp.R;
import com.scy.stock.vo.resp.ResponseCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author by scy
 * @Date 2023/5/4
 * @Description 定义服务接口实现
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
       if(vo == null || Strings.isNullOrEmpty(vo.getUsername()) || Strings.isNullOrEmpty(vo.getPassword()) || Strings.isNullOrEmpty(vo.getRkey())){
           return R.error(ResponseCode.DATA_ERROR.getCode(), ResponseCode.DATA_ERROR.getMessage());
        }
        String code = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CODE_KEY + vo.getRkey());
       if(Strings.isNullOrEmpty(vo.getCode())){
           return R.error(ResponseCode.DATA_ERROR.getCode(), ResponseCode.DATA_ERROR.getMessage());
       }
       if(!vo.getCode().equals(code)){
           return R.error(ResponseCode.SYSTEM_VERIFY_CODE_ERROR.getCode(), ResponseCode.SYSTEM_USERNAME_EXPIRED.getMessage());
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

    @Override
    public R<Map> generateCaptcha() {
        String code = RandomStringUtils.randomNumeric(4);
        String rkey=  String.valueOf(idWorker.nextId());
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_CODE_KEY +rkey, code,RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);
        HashMap<String, String> tokenHashMap = new HashMap<>();
        tokenHashMap.put("code", code);
        tokenHashMap.put("rkey", rkey);
        return R.ok(tokenHashMap);
    }


}
