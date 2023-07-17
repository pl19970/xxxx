package service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public void jimfs() throws Exception {

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        String fileName = "test.json";
        Path pathToStore = fileSystem.getPath("");

        fileRepository.create(pathToStore, fileName);

        assertTrue(Files.exists(pathToStore.resolve(fileName)));

        Path createdFilePath = pathToStore.resolve(fileName);

        assertTrue(Files.exists(createdFilePath));
        assertTrue(Files.isRegularFile(createdFilePath));

        shapeService.exportShapes(shapeList, createdFilePath.toString());


        String fileContent = fileRepository.read(createdFilePath);
        System.out.println(fileContent);
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedContent = objectMapper.writeValueAsString(shapeList);
        assertEquals(expectedContent, fileContent);


    }

}