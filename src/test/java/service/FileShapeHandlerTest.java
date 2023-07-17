package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import interfaces.Shape;
import model.Circle;
import model.Rectangle;
import model.Square;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileShapeHandlerTest {


    private FileRepository fileRepository;

    private FileSystem fileSystem;

    private String fileName;
    private Path pathToStore;

    private ObjectMapper objectMapper;

    private FileShapeHandler fileShapeHandler;

    private ShapeFactory shapeFactory;

    private Map<String, Shape> cached;

    private List<Shape> shapeList;

    private Circle circle;

    private Rectangle rectangle;

    private Square square;


    @Before
    public void init() {
        cached = new HashMap<>();

        fileShapeHandler = new FileShapeHandler();
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
        objectMapper = new ObjectMapper();
    }


    @Test
    public void testExportShapesAndVerifyFileAndJson() throws JsonProcessingException {
        fileShapeHandler.exportShapes(shapeList, fileName);
        assertTrue(Files.exists(pathToStore));

        String jsonString = objectMapper.writerFor(new TypeReference<List<Shape>>() {
        }).writeValueAsString(shapeList);

        String jsonExpected = "[{\"type\":\"circle\",\"radius\":3.0},{\"type\":\"rectangle\",\"length\":4.0,\"width\":5.0},{\"type\":\"square\",\"side\":4.0}]";

        assertEquals(jsonExpected, jsonString);

    }

    @Test
    public void testImportShapesAndVerifySize() {
        long actual = fileShapeHandler.importShapes(fileName).size();

        assertEquals(3 , actual);
    }

}