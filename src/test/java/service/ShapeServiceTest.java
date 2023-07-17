package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import interfaces.Shape;
import model.Circle;
import model.Rectangle;
import model.Square;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShapeServiceTest {


    private FileRepository fileRepository;

    private FileSystem fileSystem;

    private String fileName;
    private Path pathToStore;

    private ShapeService shapeService;

    private ShapeFactory shapeFactory;

    private Map<String, Shape> cached;

    private List<Shape> shapeList;

    private Circle circle;

    private Rectangle rectangle;

    private Square square;


    @Before
    public void init() {
        cached = new HashMap<>();

        shapeService = new ShapeService();
        shapeFactory = new ShapeFactory(cached);
        circle = shapeFactory.createCircle(3);
        rectangle = shapeFactory.createRectangle(4, 5);
        square = shapeFactory.createSquare(4);
        shapeList = new ArrayList<>(List.of(circle, rectangle, square));


        fileRepository = new FileRepository();
        fileSystem = Jimfs.newFileSystem(Configuration.windows());
        fileName = "shapes.json";
        pathToStore = fileSystem.getPath("");
        fileRepository.create(pathToStore, fileName);
    }


    @Test
    public void test() throws JsonProcessingException {
        shapeService.exportShapes(shapeList, fileName);
        assertTrue(Files.exists(pathToStore));

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writerFor(new TypeReference<List<Shape>>() {
        }).writeValueAsString(shapeList);

        String jsonExpected = "[{\"type\":\"circle\",\"radius\":3.0},{\"type\":\"rectangle\",\"length\":4.0,\"width\":5.0},{\"type\":\"square\",\"side\":4.0}]";

        assertEquals(jsonExpected, jsonString);

    }

    @Test
    public void testy() {
        long actual = shapeService.importShapes(fileName).size();

        assertEquals(3 , actual);
    }

}