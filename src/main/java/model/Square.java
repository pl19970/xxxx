package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import interfaces.Shape;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@JsonTypeName("square")
public class Square implements Shape {

    private double side;

    public static Square getInstance(double side) {
        return new Square(side);
    }


    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }


}
