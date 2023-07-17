package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import interfaces.Shape;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonTypeName("rectangle")
public class Rectangle implements Shape {

    private double length;
    private double width;


    public static Rectangle getInstance(double length, double width) {
        return new Rectangle(length, width);
    }


    @Override
    public double getArea() {
        return length * width;
    }

    @Override
    public double getPerimeter() {
        return (length * 2) + (width * 2);
    }

}
