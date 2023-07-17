package model;

import interfaces.Shape;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ShapeFactory;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CircleTest {

    private Circle circle;

    private Map<String, Shape> cachedShape;

    private ShapeFactory shapeFactory;


    double radius;


    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        cachedShape = Mockito.mock(Map.class);
        shapeFactory = new ShapeFactory(cachedShape);
        radius = 5D;
        circle = shapeFactory.createCircle(radius);
    }


    @Test
    public void testCacheCalledInsteadOfCreatingObject() {
        String key = "Circle_" + circle;

        when(cachedShape.containsKey(key)).thenReturn(true);

        Circle circleVerify = shapeFactory.createCircle(radius);

        verify(cachedShape, never()).put(key, circleVerify);
    }


    @Test
    public void verifyCacheContainsKeyCalledOnceForCircle() {
        when(cachedShape.containsKey("Circle_" + radius)).thenReturn(true);

        verify(cachedShape, times(1)).containsKey("Circle_" + radius);

    }

    @Test
    public void verifyCacheGetCalledTwiceForCircle() {
        cachedShape.get("Circle_" + radius);
        cachedShape.get("Circle_" + radius);

        verify(cachedShape, times(2)).get("Circle_" + radius);
    }

    @Test
    public void testCachePutCalledForCreatingCircle() {
        double rad = 8D;
        Circle circlePut = shapeFactory.createCircle(rad);

        verify(cachedShape, times(1)).put("Circle_" + rad, circlePut);
    }


    @Test
    public void shouldReturnCorrectArea() {
        double actualArea = circle.getArea();

        assertEquals(78.5, actualArea, 0.1);
    }

    @Test
    public void shouldReturnCorrectPerimeter() {
        double actualPerimeter = circle.getPerimeter();

        assertEquals(31.4, actualPerimeter, 0.1);
    }

}