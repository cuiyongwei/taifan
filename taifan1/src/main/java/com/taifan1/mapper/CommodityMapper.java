package com.taifan1.mapper;

import com.taifan1.domain.Commodity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 一
 */
public interface CommodityMapper {

    @Select("SELECT * FROM Commodity WHERE ID = #{id} ")
    CommodityMapper selectById(Integer id);


    @Select("SELECT * FROM Commodity WHERE id IN "
            +"(SELECT commodity_id FROM item WHERE order_id = #{id} ) ")
    List<Commodity> selectByOrderId(Integer order_id);
}
