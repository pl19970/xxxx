package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import interfaces.Shape;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonTypeName("circle")
public class Circle implements Shape {

    private double radius;


    public static Circle getInstance(double radius) {
        return new Circle(radius);
    }


    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }



}
