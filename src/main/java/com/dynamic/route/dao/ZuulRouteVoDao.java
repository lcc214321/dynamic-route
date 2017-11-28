package com.dynamic.route.dao;

import com.dynamic.route.vo.ZuulRouteVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ZuulRouteVoDao {
    int insert(@Param("pojo") ZuulRouteVo pojo);

    int insertSelective(@Param("pojo") ZuulRouteVo pojo);

    int insertList(@Param("pojos") List<ZuulRouteVo> pojo);

    int updateStatusById(@Param("id") String id);

    List<ZuulRouteVo> queryAll();
}
