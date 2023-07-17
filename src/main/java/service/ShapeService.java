package service;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.Shape;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShapeService {

    private  final ObjectMapper objectMapper = new ObjectMapper();


    public  Shape getFigureMostArea(List<Shape> list) {
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


    public  void exportShapes(List<Shape> shapes, String filePath) {
        try {
            objectMapper.writerFor(new TypeReference<List<Shape>>() {
            }).writeValue(new File(filePath), shapes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Shape> importShapes(String filePath) {
        List<Shape> shapes = new ArrayList<>();
        try {
            shapes = objectMapper.readValue(new File(filePath), new TypeReference<List<Shape>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shapes;
    }

}
