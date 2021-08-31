package com.manager.icecream.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.manager.icecream.dto.IceCreamDTO;
import com.manager.icecream.dto.QuantityDTO;
import com.manager.icecream.exception.IceCreamAlreadyRegisteredException;
import com.manager.icecream.exception.IceCreamNotFoundException;
import com.manager.icecream.exception.IceCreamExceededException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("Manages ice cream manager")
public interface IceCreamControllerDocs {

    @ApiOperation(value = "Ice Cream creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success ice cream creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    IceCreamDTO createIceCream(IceCreamDTO iceCreamDTO) throws IceCreamAlreadyRegisteredException;

    @ApiOperation(value = "Returns ice cream found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success ice cream found in the system"),
            @ApiResponse(code = 404, message = "ice cream with given name not found.")
    })
    IceCreamDTO findByName(@PathVariable String name) throws IceCreamNotFoundException;

    @ApiOperation(value = "Returns a list of all ice creams registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all ice creams registered in the system"),
    })
    List<IceCreamDTO> listIceCream();

    @ApiOperation(value = "Delete a ice cream found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success ice cream deleted in the system"),
            @ApiResponse(code = 404, message = "ice cream with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws IceCreamNotFoundException;
}
