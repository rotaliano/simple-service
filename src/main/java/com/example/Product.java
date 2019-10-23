package com.example;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement
public class Product {
    private int id;
    private String name;
    private int price;
}
