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

public class ShapeServiceTest {


    private FileRepository fileRepository;

    private ShapeService shapeService;

    private ShapeFactory shapeFactory;

    private Map<String, Shape> cached;

    private List<Shape> shapeList;

    private Circle circle;

    private Rectangle rectangle;

    private Square square;

    private String PATH = "src/main/resources/obj.json";
    FileSystem fileSystem;
    String fileName;

    @Before
    public void init() {
        cached = new HashMap<>();
        fileRepository = new FileRepository();
        shapeService = new ShapeService();
        shapeFactory = new ShapeFactory(cached);
        circle = shapeFactory.createCircle(3);
        rectangle = shapeFactory.createRectangle(4, 5);
        square = shapeFactory.createSquare(4);
        shapeList = new ArrayList<>(List.of(circle, rectangle, square));
        fileSystem = Jimfs.newFileSystem(Configuration.windows());
        fileName = "test.json";
    }



    @Test
    public void test() throws URISyntaxException, IOException {
        Path resourceFilePath = fileSystem.getPath("test.json");
        Files.copy(getResourceFilePath(), resourceFilePath);

        shapeService.exportShapes(shapeList, getResourceFilePath().toString());
        String content = fileRepository.read(resourceFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writerFor(new TypeReference<List<Shape>>() {}).writeValueAsString(shapeList);


        assertEquals(jsonString, content);
    }



    private Path getResourceFilePath() throws URISyntaxException {
        return Paths.get(getClass().getClassLoader().getResource("test.json").toURI());
    }
}