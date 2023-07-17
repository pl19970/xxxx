package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.Shape;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileShapeHandler {

    private  final ObjectMapper objectMapper = new ObjectMapper();

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
