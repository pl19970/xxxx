package service;

import com.fasterxml.jackson.annotation.JsonTypeName;
import interfaces.Shape;

import java.util.*;

public class ShapeService {


    public Shape getFigureMostArea(List<Shape> list) {
        return Optional.ofNullable(list)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Shape::getArea))
                .orElse(null);
    }


    public  Shape getFigureMostPerimeterAndType(List<Shape> list, String type) {
        return Optional.ofNullable(list)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(shape -> type.equals(shape.getClass().getAnnotation(JsonTypeName.class).value()))
                .max(Comparator.comparingDouble(Shape::getPerimeter))
                .orElse(null);
    }
}
