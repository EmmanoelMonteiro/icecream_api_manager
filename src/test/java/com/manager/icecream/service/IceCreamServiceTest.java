package com.manager.icecream.service;

import com.manager.icecream.builder.IceCreamDTOBuilder;
import com.manager.icecream.dto.IceCreamDTO;
import com.manager.icecream.entity.IceCream;
import com.manager.icecream.exception.IceCreamAlreadyRegisteredException;
import com.manager.icecream.exception.IceCreamNotFoundException;
import com.manager.icecream.exception.IceCreamExceededException;
import com.manager.icecream.mapper.IceCreamMapper;
import com.manager.icecream.repository.IceCreamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IceCreamServiceTest {

    private static final long INVALID_ICECREAM_ID  = 1L;

    @Mock
    private IceCreamRepository iceCreamRepository;

    private IceCreamMapper iceCreamMapper = IceCreamMapper.INSTANCE;

    @InjectMocks
    private IceCreamService iceCreamService;

    @Test
    void whenIceCreamInformedThenItShouldBeCreated() throws IceCreamAlreadyRegisteredException {
        // given
        IceCreamDTO expectedIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        IceCream expectedSavedIceCream = iceCreamMapper.toModel(expectedIceCreamDTO);

        // when
        when(iceCreamRepository.findByName(expectedIceCreamDTO.getName())).thenReturn(Optional.empty());
        when(iceCreamRepository.save(expectedSavedIceCream)).thenReturn(expectedSavedIceCream);

        //then
        IceCreamDTO createdIceCreamDTO = iceCreamService.createIceCream(expectedIceCreamDTO);

        assertThat(createdIceCreamDTO.getId(), is(equalTo(expectedIceCreamDTO.getId())));
        assertThat(createdIceCreamDTO.getName(), is(equalTo(expectedIceCreamDTO.getName())));
        assertThat(createdIceCreamDTO.getQuantity(), is(equalTo(expectedIceCreamDTO.getQuantity())));
    }

    @Test
    void whenAlreadyRegisteredIceCreamInformedThenAnExceptionShouldBeThrown() {
        // given
        IceCreamDTO expectedIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        IceCream duplicatedIceCream = iceCreamMapper.toModel(expectedIceCreamDTO);

        // when
        when(iceCreamRepository.findByName(expectedIceCreamDTO.getName())).thenReturn(Optional.of(duplicatedIceCream));

        // then
        assertThrows(IceCreamAlreadyRegisteredException.class, () -> iceCreamService.createIceCream(expectedIceCreamDTO));
    }

    @Test
    void whenValidIceCreamNameIsGivenThenReturnAIceCream() throws IceCreamNotFoundException {
        // given
        IceCreamDTO expectedFoundIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        IceCream expectedFoundCreamMapper = iceCreamMapper.toModel(expectedFoundIceCreamDTO);

        // when
        when(iceCreamRepository.findByName(expectedFoundCreamMapper.getName())).thenReturn(Optional.of(expectedFoundCreamMapper));

        // then
        IceCreamDTO foundIceCreamDTO = iceCreamService.findByName(expectedFoundIceCreamDTO.getName());

        assertThat(foundIceCreamDTO, is(equalTo(expectedFoundIceCreamDTO)));
    }

    @Test
    void whenNotRegisteredIceCreamNameIsGivenThenThrowAnException() {
        // given
        IceCreamDTO expectedFoundIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();

        // when
        when(iceCreamRepository.findByName(expectedFoundIceCreamDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(IceCreamNotFoundException.class, () -> iceCreamService.findByName(expectedFoundIceCreamDTO.getName()));
    }

    @Test
    void whenListIceCreamIsCalledThenReturnAListOfIceCreams() {
        // given
        IceCreamDTO expectedFoundIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        IceCream expectedFoundCreamMapper = iceCreamMapper.toModel(expectedFoundIceCreamDTO);

        //when
        when(iceCreamRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundCreamMapper));

        //then
        List<IceCreamDTO> foundListIceCreamDTO = iceCreamService.listAll();

        assertThat(foundListIceCreamDTO, is(not(empty())));
        assertThat(foundListIceCreamDTO.get(0), is(equalTo(expectedFoundIceCreamDTO)));
    }

    @Test
    void whenListIceCreamIsCalledThenReturnAnEmptyListOfIceCreams() {
        //when
        when(iceCreamRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<IceCreamDTO> foundListIceCreamDTO = iceCreamService.listAll();

        assertThat(foundListIceCreamDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAIceCreamShouldBeDeleted() throws IceCreamNotFoundException{
        // given
        IceCreamDTO expectedDeletedIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        IceCream expectedDeletedIceCream = iceCreamMapper.toModel(expectedDeletedIceCreamDTO);

        // when
        when(iceCreamRepository.findById(expectedDeletedIceCreamDTO.getId())).thenReturn(Optional.of(expectedDeletedIceCream));
        doNothing().when(iceCreamRepository).deleteById(expectedDeletedIceCreamDTO.getId());

        // then
        iceCreamService.deleteById(expectedDeletedIceCreamDTO.getId());

        verify(iceCreamRepository, times(1)).findById(expectedDeletedIceCreamDTO.getId());
        verify(iceCreamRepository, times(1)).deleteById(expectedDeletedIceCreamDTO.getId());
    }

    @Test
    void whenIncrementIsCalledThenIncrementIceCreamStock() throws IceCreamNotFoundException, IceCreamExceededException {
        //given
        IceCreamDTO expectedIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        IceCream expectedIceCream = iceCreamMapper.toModel(expectedIceCreamDTO);

        //when
        when(iceCreamRepository.findById(expectedIceCreamDTO.getId())).thenReturn(Optional.of(expectedIceCream));
        when(iceCreamRepository.save(expectedIceCream)).thenReturn(expectedIceCream);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedIceCreamDTO.getQuantity() + quantityToIncrement;

        // then
        IceCreamDTO incrementedIceCreamDTO = iceCreamService.increment(expectedIceCreamDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedIceCreamDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedIceCreamDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {
        IceCreamDTO expectedIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        IceCream expectedIceCream = iceCreamMapper.toModel(expectedIceCreamDTO);

        when(iceCreamRepository.findById(expectedIceCreamDTO.getId())).thenReturn(Optional.of(expectedIceCream));

        int quantityToIncrement = 80;
        assertThrows(IceCreamExceededException.class, () -> iceCreamService.increment(expectedIceCreamDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
        IceCreamDTO expectedIceCreamDTO = IceCreamDTOBuilder.builder().build().toIceCreamDTO();
        IceCream expectedIceCream = iceCreamMapper.toModel(expectedIceCreamDTO);

        when(iceCreamRepository.findById(expectedIceCreamDTO.getId())).thenReturn(Optional.of(expectedIceCream));

        int quantityToIncrement = 45;
        assertThrows(IceCreamExceededException.class, () -> iceCreamService.increment(expectedIceCreamDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        when(iceCreamRepository.findById(INVALID_ICECREAM_ID )).thenReturn(Optional.empty());

        assertThrows(IceCreamNotFoundException.class, () -> iceCreamService.increment(INVALID_ICECREAM_ID , quantityToIncrement));
    }
}
