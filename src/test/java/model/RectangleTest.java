package model;

import interfaces.Shape;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ShapeFactory;
import service.ShapeService;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RectangleTest {

    private Rectangle rectangle;

    private Map<String , Shape> cached;

    private ShapeFactory shapeFactory;

    private ShapeService shapeService;

    private double length;

    private double width;


    @Before
    public void init() {
        shapeService = new ShapeService();
        cached = Mockito.mock(Map.class);
        shapeFactory = new ShapeFactory(cached);
        length = 5D;
        width = 3D;
        rectangle = shapeFactory.createRectangle(length , width);
    }

    @Test
    public void testingInteractionCacheContainsKey() {
        Mockito.when(cached.containsKey("Rectangle_" + length + "_" + width)).thenReturn(true);

        verify(cached , Mockito.times(1)).containsKey("Rectangle_" + length + "_" + width);
    }

    @Test
    public void testing1() {
        cached.get("Rectangle_" + length + "_" + width);
        cached.get("Rectangle_" + length + "_" + width);

        verify(cached , Mockito.times(2)).get("Rectangle_" + length + "_" + width);
    }

    @Test
    public void testing2() {
        double lengthRectangle = 8D;
        Rectangle rectanglePut = shapeFactory.createRectangle(lengthRectangle , width);

        verify(cached , Mockito.times(1)).put("Rectangle_" + lengthRectangle + "_" + width , rectanglePut);
    }

    @Test
    public void shouldReturnCorrectArea() {
        double actualArea = rectangle.getArea();

        assertEquals(15 , actualArea , 0.1);
    }
    @Test
    public void shouldReturnCorrectPerimeter() {
    double actualPerimeter = rectangle.getPerimeter();

    assertEquals(16 , actualPerimeter , 0.1);

    }
}