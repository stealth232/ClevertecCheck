package ru.clevertec.check.utils.builders;

import ru.clevertec.check.entities.Product;

public interface Builder {

    void setId(int itemId);

    void setName(String name);

    void setCost(double cost);

    void setStock(boolean stock);

    Product getProduct();

}
