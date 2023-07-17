package service;

import interfaces.Shape;
import model.Circle;
import model.Rectangle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShapeFactoryTest {

    private ShapeFactory shapeFactory;

    @Mock
    private Circle circle;

    private Rectangle rectangle;

    private Map<String, Shape> cachedShape;


    @Before
    public void initFactory() {
        MockitoAnnotations.openMocks(this);
        cachedShape = Mockito.spy(Map.class);
        shapeFactory = new ShapeFactory(cachedShape);

        rectangle = new Rectangle(2 ,5);
    }


    @Test
    public void testingInteractionFactoryWithCache() {
        double radius = 5.0;

        when(circle.getRadius()).thenReturn(radius);

        when(cachedShape.containsKey("Circle_" + radius)).thenReturn(true);
        when(cachedShape.get("Circle_" + radius)).thenReturn(circle);

        Circle result = shapeFactory.createCircle(radius);

        verify(cachedShape).containsKey("Circle_" + radius);
        verify(cachedShape).get("Circle_" + radius);



        assertEquals(circle , result);
    }

    @Test
    public void shouldReturnCorrectAreaEqual10() {
        double result = rectangle.getArea();

        assertEquals(10 , result , 0.1);
    }

    @Test
    public void shouldReturnCorrectPerimeterEqual() {
        double result = rectangle.getPerimeter();

        assertEquals(14 , result , 0.1);
    }
}