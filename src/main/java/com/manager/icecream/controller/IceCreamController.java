package com.manager.icecream.controller;

import lombok.AllArgsConstructor;
import com.manager.icecream.dto.IceCreamDTO;
import com.manager.icecream.dto.QuantityDTO;
import com.manager.icecream.exception.IceCreamAlreadyRegisteredException;
import com.manager.icecream.exception.IceCreamNotFoundException;
import com.manager.icecream.exception.IceCreamExceededException;
import com.manager.icecream.service.IceCreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/icecream")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class IceCreamController implements IceCreamControllerDocs {

    private final IceCreamService iceCreamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IceCreamDTO createIceCream(@RequestBody @Valid IceCreamDTO iceCreamDTO) throws IceCreamAlreadyRegisteredException {
        return iceCreamService.createIceCream(iceCreamDTO);
    }

    @GetMapping("/{name}")
    public IceCreamDTO findByName(@PathVariable String name) throws IceCreamNotFoundException {
        return iceCreamService.findByName(name);
    }

    @GetMapping
    public List<IceCreamDTO> listIceCream() {
        return iceCreamService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws IceCreamNotFoundException {
        iceCreamService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public IceCreamDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws IceCreamNotFoundException, IceCreamExceededException {
        return iceCreamService.increment(id, quantityDTO.getQuantity());
    }
}
