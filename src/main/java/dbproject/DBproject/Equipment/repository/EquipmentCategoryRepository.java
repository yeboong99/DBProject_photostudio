package dbproject.DBproject.Equipment.repository;

import dbproject.DBproject.Equipment.domain.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Integer> {
}
