package com.manager.icecream.mapper;

import com.manager.icecream.dto.IceCreamDTO;
import com.manager.icecream.entity.IceCream;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-31T07:48:01-0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 16.0.1 (AdoptOpenJDK)"
)
public class IceCreamMapperImpl implements IceCreamMapper {

    @Override
    public IceCream toModel(IceCreamDTO iceCreamDTO) {
        if ( iceCreamDTO == null ) {
            return null;
        }

        IceCream iceCream = new IceCream();

        iceCream.setId( iceCreamDTO.getId() );
        iceCream.setName( iceCreamDTO.getName() );
        iceCream.setBrand( iceCreamDTO.getBrand() );
        if ( iceCreamDTO.getMax() != null ) {
            iceCream.setMax( iceCreamDTO.getMax() );
        }
        if ( iceCreamDTO.getQuantity() != null ) {
            iceCream.setQuantity( iceCreamDTO.getQuantity() );
        }
        iceCream.setType( iceCreamDTO.getType() );

        return iceCream;
    }

    @Override
    public IceCreamDTO toDTO(IceCream iceCream) {
        if ( iceCream == null ) {
            return null;
        }

        IceCreamDTO iceCreamDTO = new IceCreamDTO();

        iceCreamDTO.setId( iceCream.getId() );
        iceCreamDTO.setName( iceCream.getName() );
        iceCreamDTO.setBrand( iceCream.getBrand() );
        iceCreamDTO.setMax( iceCream.getMax() );
        iceCreamDTO.setQuantity( iceCream.getQuantity() );
        iceCreamDTO.setType( iceCream.getType() );

        return iceCreamDTO;
    }
}
