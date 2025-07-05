package com.example.CRUDSpace.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDSpace.Model.DTO.Api.ApiResponse;
import com.example.CRUDSpace.Model.DTO.Space.MaterialsInSpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithChildrenDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceWithTypeDTO;
import com.example.CRUDSpace.Model.Entity.Space;
import com.example.CRUDSpace.Service.Space.SpaceServiceInterface;

import lombok.*;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/space")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpaceController {
    SpaceServiceInterface spaceService;

    @GetMapping
    ApiResponse<List<SpaceDTO>> getAllSpace() {
        var result = spaceService.getAllSpaces();

        return ApiResponse.<List<SpaceDTO>>builder()
                .result(result)
                .build();
    }

    @GetMapping("{spaceId}")
    ApiResponse<SpaceDTO> getSpaceById(@PathVariable UUID spaceId) {
        var result = spaceService.getSpaceById(spaceId);

        return ApiResponse.<SpaceDTO>builder().result(result).build();
    }

    @GetMapping("/spaceWithChildren")
    ApiResponse<List<SpaceWithChildrenDTO>> getAllSpaceWithChilren() {
        var result = spaceService.getAllSpacesWithChildren();

        return ApiResponse.<List<SpaceWithChildrenDTO>>builder().result(result).build();
    }

    @GetMapping("/spaceWithType")
    ApiResponse<List<SpaceWithTypeDTO>> getAllSpaceWithType() {
        var result = spaceService.getAllSpacesWithType();

        return ApiResponse.<List<SpaceWithTypeDTO>>builder().result(result).build();
    }

    @GetMapping("/spaceWithType/{spaceId}")
    ApiResponse<SpaceWithTypeDTO> getSpaceWithTypeById(@PathVariable UUID spaceId) {
        var result = spaceService.getSpaceWithTypeById(spaceId);

        return ApiResponse.<SpaceWithTypeDTO>builder().result(result).build();
    }

    @GetMapping("/{spaceId}/getAllMaterials")
    ApiResponse<List<MaterialsInSpaceDTO>> getAllMaterialsInInventory(@PathVariable UUID spaceId) {
        var result = spaceService.getAllMaterialsInInventory(spaceId);

        return ApiResponse.<List<MaterialsInSpaceDTO>>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{spaceId}/addingInventory")
    ApiResponse<String> addInventoryBySpaceId(@PathVariable UUID spaceId) {
        var result = spaceService.addInventoryBySpaceId(spaceId);

        return ApiResponse.<String>builder().result(result).build();
    }

}
