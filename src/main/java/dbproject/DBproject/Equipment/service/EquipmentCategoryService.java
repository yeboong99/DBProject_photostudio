package dbproject.DBproject.Equipment.service;

import dbproject.DBproject.Equipment.domain.EquipmentCategory;
import dbproject.DBproject.Equipment.repository.EquipmentCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentCategoryService{
    private final EquipmentCategoryRepository equipmentCategoryRepository;

    public List<EquipmentCategory> getAllEquipmentCategories() {
        return equipmentCategoryRepository.findAll();
    }
}
