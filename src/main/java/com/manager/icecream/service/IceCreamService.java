package com.manager.icecream.service;

import lombok.AllArgsConstructor;
import com.manager.icecream.dto.IceCreamDTO;
import com.manager.icecream.entity.IceCream;
import com.manager.icecream.exception.IceCreamAlreadyRegisteredException;
import com.manager.icecream.exception.IceCreamNotFoundException;
import com.manager.icecream.exception.IceCreamExceededException;
import com.manager.icecream.mapper.IceCreamMapper;
import com.manager.icecream.repository.IceCreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class IceCreamService {

    private final IceCreamRepository iceCreamRepository;
    private final IceCreamMapper iceCreamMapper = IceCreamMapper.INSTANCE;

    public IceCreamDTO createIceCream(IceCreamDTO iceCreamDTO) throws IceCreamAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(iceCreamDTO.getName());
        IceCream iceCream = iceCreamMapper.toModel(iceCreamDTO);
        IceCream savedIceCream = iceCreamRepository.save(iceCream);
        return iceCreamMapper.toDTO(savedIceCream);
    }

    public IceCreamDTO findByName(String name) throws IceCreamNotFoundException {
        IceCream foundIceCream = iceCreamRepository.findByName(name)
                .orElseThrow(() -> new IceCreamNotFoundException(name));
        return iceCreamMapper.toDTO(foundIceCream);
    }

    public List<IceCreamDTO> listAll() {
        return iceCreamRepository.findAll()
                .stream()
                .map(iceCreamMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws IceCreamNotFoundException {
        verifyIfExists(id);
        iceCreamRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws IceCreamAlreadyRegisteredException {
        Optional<IceCream> optSavedIceCream = iceCreamRepository.findByName(name);
        if (optSavedIceCream.isPresent()) {
            throw new IceCreamAlreadyRegisteredException(name);
        }
    }

    private IceCream verifyIfExists(Long id) throws IceCreamNotFoundException {
        return iceCreamRepository.findById(id)
                .orElseThrow(() -> new IceCreamNotFoundException(id));
    }

    public IceCreamDTO increment(Long id, int quantityToIncrement) throws IceCreamNotFoundException, IceCreamExceededException {
        IceCream iceCreamToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + iceCreamToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= iceCreamToIncrementStock.getMax()) {
            iceCreamToIncrementStock.setQuantity(iceCreamToIncrementStock.getQuantity() + quantityToIncrement);
            IceCream incrementedIceCreamStock = iceCreamRepository.save(iceCreamToIncrementStock);
            return iceCreamMapper.toDTO(incrementedIceCreamStock);
        }
        throw new IceCreamExceededException(id, quantityToIncrement);
    }
}
