package ru.clevertec.check.dto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.model.DataOrder;

@Repository
public interface OrderRepository extends CrudRepository<DataOrder, Integer> {
}
