package model;

import interfaces.Shape;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ShapeFactory;
import service.ShapeService;

import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CircleTest {

    private Circle circle;

    private Map<String, Shape> cachedShape;

    private ShapeFactory shapeFactory;

    private ShapeService shapeService;

    double radius;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        shapeService = new ShapeService();
        cachedShape = Mockito.mock(Map.class);
        shapeFactory = new ShapeFactory(cachedShape);
        radius = 5D;
        circle = shapeFactory.createCircle(radius);
    }

    @Test
    public void testingInteractionCacheContainsKey() {
        when(cachedShape.containsKey("Circle_" + radius)).thenReturn(true);

        verify(cachedShape , times(1)).containsKey("Circle_" + radius);

    }

    @Test
    public void testingInteractionWithCacheWithGet() {
        cachedShape.get("Circle_" + radius);
        cachedShape.get("Circle_" + radius);

        verify(cachedShape, times(2)).get("Circle_" + radius);
    }

    @Test
    public void testingInteractionCacheWithPut() {
        double rad = 8D;
        Circle circlePut = shapeFactory.createCircle(rad);

        verify(cachedShape, times(1)).put("Circle_" + rad, circlePut);
    }


    @Test
    public void shouldReturnCorrectArea() {
        double actualArea = circle.getArea();

        Assert.assertEquals(78.5, actualArea , 0.1);
    }

    @Test
    public void shouldReturnCorrectPerimeter() {
        double actualPerimeter = circle.getPerimeter();

        Assert.assertEquals(31.4 , actualPerimeter , 0.1);
    }

}