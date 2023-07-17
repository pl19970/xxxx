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
        fileRepository = new FileRepository();
        shapeService = new ShapeService();
        shapeFactory = new ShapeFactory(cached);
        circle = shapeFactory.createCircle(3);
        rectangle = shapeFactory.createRectangle(4, 5);
        square = shapeFactory.createSquare(4);
        shapeList = new ArrayList<>(List.of(circle, rectangle, square));
    }


    @Test
    public void test() {
        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());
        String fileName = "shapes.json";
        Path pathToStore = fileSystem.getPath("");
        fileRepository.create(pathToStore, fileName);

        shapeService.exportShapes(shapeList , fileName );
        assertTrue(Files.exists(pathToStore));
    }

}