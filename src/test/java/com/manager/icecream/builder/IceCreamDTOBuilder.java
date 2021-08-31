package com.manager.icecream.builder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;
import com.manager.icecream.dto.IceCreamDTO;
import com.manager.icecream.enums.IceCreamType;

@Builder
public class IceCreamDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Chocolate";

    @Builder.Default
    private String brand = "Ster Bom";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private IceCreamType type = IceCreamType.PREMIUM;

    public IceCreamDTO toIceCreamDTO() {
        return new IceCreamDTO(id,
                name,
                brand,
                max,
                quantity,
                type);
    }
}
