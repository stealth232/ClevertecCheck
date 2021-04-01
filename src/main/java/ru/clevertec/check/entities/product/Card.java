package ru.clevertec.check.entities.product;

import lombok.*;

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
