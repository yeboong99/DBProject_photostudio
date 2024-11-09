package dbproject.DBproject.Equipment.repository;

import dbproject.DBproject.Equipment.domain.Equipment;
import dbproject.DBproject.Equipment.domain.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    List<Equipment> findByCategory(EquipmentCategory category);

}
