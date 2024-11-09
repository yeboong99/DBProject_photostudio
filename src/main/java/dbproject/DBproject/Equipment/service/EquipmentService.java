package dbproject.DBproject.Equipment.service;

import dbproject.DBproject.Equipment.domain.Equipment;
import dbproject.DBproject.Equipment.domain.EquipmentCategory;
import dbproject.DBproject.Equipment.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public void addEquipment(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    public void deleteEquipment(Integer id) {
        equipmentRepository.deleteById(id);
    }

    public Equipment getEquipmentById(Integer id) {
        return equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    public void updateEquipment(Integer id, Equipment updatedEquipment) {
        Equipment equipment = getEquipmentById(id);
        equipment.setName(updatedEquipment.getName());
        equipment.setStatus(updatedEquipment.getStatus());
        equipment.setPrice(updatedEquipment.getPrice());
        equipment.setImage_path(updatedEquipment.getImage_path());
        equipment.setCategory(updatedEquipment.getCategory());
        equipmentRepository.save(equipment);
    }

    public List<Equipment> getEquipmentsByIds(List<Integer> equipmentIds) {
        return equipmentRepository.findAllById(equipmentIds);
    }

}
