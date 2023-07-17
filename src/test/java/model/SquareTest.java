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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SquareTest {

    private Square square;

    private Map<String , Shape> cached;

    private ShapeFactory shapeFactory;

    private ShapeService shapeService;

    double side;

    @Before
    public void init() {
        shapeService = new ShapeService();
        cached = Mockito.mock(Map.class);
        shapeFactory = new ShapeFactory(cached);
        side = 5D;
        square = shapeFactory.createSquare(side);
    }

    @Test
    public void test() {
        when(cached.containsKey("Square_" + side)).thenReturn(true);

        verify(cached , times(1)).containsKey("Square_" + side);
    }

    @Test
    public void test2() {
        cached.get("Square_" + side);

        verify(cached , times(1)).get("Square_" + side);
    }

    @Test
    public void test3() {
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