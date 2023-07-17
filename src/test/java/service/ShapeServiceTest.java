package service;

import interfaces.Shape;
import model.Circle;
import model.Rectangle;
import model.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
 public class ShapeServiceTest {


    private Circle circle;

    private Circle secondCircle;

    private Rectangle rectangle;

    private Square square;

    private ShapeFactory shapeFactory;

    private ShapeService shapeService;

    private Map<String , Shape> cached;

    List<Shape> shapes;


    @Before
    public void init() {
        cached = new HashMap<>();
        shapeFactory = new ShapeFactory(cached);
        shapeService = new ShapeService();

        circle = shapeFactory.createCircle(4);
        secondCircle = shapeFactory.createCircle(5);
        rectangle = shapeFactory.createRectangle(4 , 5);
        square = shapeFactory.createSquare(10);

        shapes = new ArrayList<>(List.of(circle , secondCircle , rectangle ,square));
    }


    @Test
    public void shouldReturnMostShape() {
        Shape result = shapeService.getFigureMostArea(shapes);

        assertEquals(square , result);
    }

    @Test
    public void shouldReturnMostPerimeterWithGivenType() {
        Shape result = shapeService.getFigureMostPerimeterAndType(shapes , "circle");

        assertEquals(secondCircle , result);
    }

}