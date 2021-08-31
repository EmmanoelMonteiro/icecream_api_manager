package com.manager.icecream.builder;

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
    private String brand = "Ester Bom";

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
