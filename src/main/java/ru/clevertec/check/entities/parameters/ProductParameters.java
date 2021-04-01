package ru.clevertec.check.entities.parameters;

public interface ProductParameters {

    int getItemId();

    String getName();

    double getCost();

    boolean isStock();

    void setStock(boolean x);

}
