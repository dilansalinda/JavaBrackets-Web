package msv.management.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import msv.management.system.dto.EntityDTO;
import msv.management.system.service.IEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "${server.context}/entities", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Entity Exclusions"})
public class EntityController {

    @Autowired
    private IEntityService entityService;

    @ApiOperation(value = "Insert an entity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<List<EntityDTO>> insertEntity(@RequestBody List<EntityDTO> entities) {
        List<EntityDTO> result = entityService.insertEntities(entities);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
