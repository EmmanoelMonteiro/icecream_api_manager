package com.manager.icecream.mapper;

import com.manager.icecream.dto.IceCreamDTO;
import com.manager.icecream.entity.IceCream;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IceCreamMapper {

    IceCreamMapper INSTANCE = Mappers.getMapper(IceCreamMapper.class);

    IceCream toModel(IceCreamDTO iceCreamDTO);

    IceCreamDTO toDTO(IceCream iceCream);
}
