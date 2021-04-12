package ru.clevertec.check.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Card {
    private int number;

    public int getNumber() {
        return number;
    }
}
