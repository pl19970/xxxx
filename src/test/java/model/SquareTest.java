package model;

import interfaces.Shape;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.ShapeFactory;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SquareTest {

    private Square square;

    private Map<String , Shape> cached;

    private ShapeFactory shapeFactory;

    double side;

    @Before
    public void init() {
        cached = Mockito.mock(Map.class);
        shapeFactory = new ShapeFactory(cached);
        side = 5D;
        square = shapeFactory.createSquare(side);
    }

    @Test
    public void testCacheCalledInsteadOfCreatingObject() {

        String key = "Square_" + side;

        when(cached.containsKey(key)).thenReturn(true);

        Square squareVerify = shapeFactory.createSquare(side);

        verify(cached, never()).put(key, squareVerify);
    }


    @Test
    public void verifyCacheContainsKeyCalledOnceForCircle() {
        when(cached.containsKey("Square_" + side)).thenReturn(true);

        verify(cached , times(1)).containsKey("Square_" + side);
    }

    @Test
    public void verifyCacheGetCalledOnceForCircle() {
        cached.get("Square_" + side);

        verify(cached , times(1)).get("Square_" + side);
    }

    @Test
    public void testCachePutCalledForCreatingCircle() {
        double sideSquare = 6D;

        Square squarePut = shapeFactory.createSquare(sideSquare);
        verify(cached , times(1)).put("Square_" + sideSquare , squarePut);
    }

    @Test
    public void shouldReturnCorrectArea() {
        double actualArea = square.getArea();

        assertEquals(25 , actualArea , 0.1);
    }
    @Test
    public void shouldReturnCorrectPerimeter() {
        double actualPerimeter = square.getPerimeter();

        assertEquals(20 , actualPerimeter , 0.1);

    }

}