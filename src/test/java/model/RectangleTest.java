package model;

import interfaces.Shape;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ShapeFactory;
import service.FileShapeHandler;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RectangleTest {

    private Rectangle rectangle;

    private Map<String , Shape> cached;

    private ShapeFactory shapeFactory;


    private double length;

    private double width;


    @Before
    public void init() {
        cached = Mockito.mock(Map.class);
        shapeFactory = new ShapeFactory(cached);
        length = 5D;
        width = 3D;
        rectangle = shapeFactory.createRectangle(length , width);
    }

    @Test
    public void testCacheCalledInsteadOfCreatingObject() {
        String key = "Rectangle_" + length +"_" + width;

        when(cached.containsKey(key)).thenReturn(true);

        Rectangle rectangleVerify = shapeFactory.createRectangle(length , width);

        verify(cached, never()).put(key, rectangleVerify);
    }



    @Test
    public void verifyCacheContainsKeyCalledOnceForCircle() {
        Mockito.when(cached.containsKey("Rectangle_" + length + "_" + width)).thenReturn(true);

        verify(cached , Mockito.times(1)).containsKey("Rectangle_" + length + "_" + width);
    }

    @Test
    public void verifyCacheGetCalledTwiceForCircle() {
        cached.get("Rectangle_" + length + "_" + width);
        cached.get("Rectangle_" + length + "_" + width);

        verify(cached , Mockito.times(2)).get("Rectangle_" + length + "_" + width);
    }

    @Test
    public void testCachePutCalledForCreatingCircle() {
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