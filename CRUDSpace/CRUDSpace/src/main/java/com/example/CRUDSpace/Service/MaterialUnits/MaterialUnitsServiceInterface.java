package com.example.CRUDSpace.Service.MaterialUnits;

import java.util.List;

import com.example.CRUDSpace.Model.DTO.MaterialUnits.MaterialUnitsCreationFromInventoryDTO;
import com.example.CRUDSpace.Model.DTO.MaterialUnits.MaterialUnitsCreationFromSupplierDTO;
import com.example.CRUDSpace.Model.DTO.Space.SpaceDTO;
import com.example.CRUDSpace.Model.Entity.MaterialUnits;
import com.example.CRUDSpace.Model.Entity.Space;

public interface MaterialUnitsServiceInterface {

    String addMaterialUnitsFromSupplier(MaterialUnitsCreationFromSupplierDTO dto);

    String addMaterialUnitsFromInventory(MaterialUnitsCreationFromInventoryDTO dto);
}
