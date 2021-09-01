package com.manager.icecream.controller;

import com.manager.icecream.builder.IceCreamDTOBuilder;
import com.manager.icecream.dto.IceCreamDTO;
import com.manager.icecream.dto.QuantityDTO;
import com.manager.icecream.exception.IceCreamNotFoundException;
import com.manager.icecream.service.IceCreamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.manager.icecream.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class IceCreamControllerTest {

    private static final String ICECREAM_API_URL_PATH = "/api/v1/icecream";
    private static final long VALID_ICECREAM_ID = 1L;
    private static final long INVALID_ICECREAM_ID = 2l;
    private static final String ICECREAM_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String ICECREAM_API_SUBPATH_DECREMENT_URL = "/decrement";

    private MockMvc mockMvc;

    @Mock
    private IceCreamService iceCreamService;

    @InjectMocks
    private IceCreamController iceCreamController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(iceCreamController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAIceCreamIsCreated() throws Exception {
        // given
        IceCreamDTO iceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();

        // when
        when(iceCreamService.createIceCream(iceCreamDTO)).thenReturn(iceCreamDTO);

        // then
        mockMvc.perform(post(ICECREAM_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(iceCreamDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(iceCreamDTO.getName())))
                .andExpect(jsonPath("$.brand", is(iceCreamDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(iceCreamDTO.getType().toString())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        IceCreamDTO iceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        iceCreamDTO.setBrand(null);

        // then
        mockMvc.perform(post(ICECREAM_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(iceCreamDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        IceCreamDTO iceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();

        //when
        when(iceCreamService.findByName(iceCreamDTO.getName())).thenReturn(iceCreamDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ICECREAM_API_URL_PATH + "/" + iceCreamDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(iceCreamDTO.getName())))
                .andExpect(jsonPath("$.brand", is(iceCreamDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(iceCreamDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // given
        IceCreamDTO iceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();

        //when
        when(iceCreamService.findByName(iceCreamDTO.getName())).thenThrow(IceCreamNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ICECREAM_API_URL_PATH + "/" + iceCreamDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithIceCreamIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        IceCreamDTO iceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();

        //when
        when(iceCreamService.listAll()).thenReturn(Collections.singletonList(iceCreamDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ICECREAM_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(iceCreamDTO.getName())))
                .andExpect(jsonPath("$[0].brand", is(iceCreamDTO.getBrand())))
                .andExpect(jsonPath("$[0].type", is(iceCreamDTO.getType().toString())));
    }

    @Test
    void whenGETListWithoutIceCreamIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        IceCreamDTO iceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();

        //when
        when(iceCreamService.listAll()).thenReturn(Collections.singletonList(iceCreamDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ICECREAM_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        IceCreamDTO iceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();

        //when
        doNothing().when(iceCreamService).deleteById(iceCreamDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(ICECREAM_API_URL_PATH + "/" + iceCreamDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(IceCreamNotFoundException.class).when(iceCreamService).deleteById(INVALID_ICECREAM_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(ICECREAM_API_URL_PATH + "/" + INVALID_ICECREAM_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() throws Exception {
        QuantityDTO quantityDTO = QuantityDTO.builder()
                .quantity(10)
                .build();

        IceCreamDTO iceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        iceCreamDTO.setQuantity(iceCreamDTO.getQuantity() + quantityDTO.getQuantity());

        when(iceCreamService.increment(VALID_ICECREAM_ID, quantityDTO.getQuantity())).thenReturn(iceCreamDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch(ICECREAM_API_URL_PATH + "/" + VALID_ICECREAM_ID + ICECREAM_API_SUBPATH_INCREMENT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(iceCreamDTO.getName())))
                .andExpect(jsonPath("$.brand", is(iceCreamDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(iceCreamDTO.getType().toString())))
                .andExpect(jsonPath("$.quantity", is(iceCreamDTO.getQuantity())));
    }
}
