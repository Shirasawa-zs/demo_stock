package com.scy.stock.mapper;

import com.scy.stock.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Jack
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-05-04 20:14:15
* @Entity com.scy.stock.pojo.SysUser
*/
@Mapper
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);


    SysUser findByUserName(String username);
}
