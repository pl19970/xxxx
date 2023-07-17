package service;


import interfaces.Shape;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.Circle;
import model.Rectangle;
import model.Square;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class ShapeFactory {

    private  Map<String, Shape> cache = new HashMap<>();

    public ShapeFactory(Map<String, Shape> cache) {
        this.cache = cache;
    }

    public  Circle createCircle(double radius) {
        String key = "Circle_" + radius;
        if (cache.containsKey(key)) {
            return (Circle) cache.get(key);
        }
        Circle circle = Circle.getInstance(radius);
        cache.put(key, circle);
        return circle;
    }

    public  Rectangle createRectangle(double length, double width) {
        String key = "Rectangle_" + length + "_" + width;
        if (cache.containsKey(key)){
            return (Rectangle) cache.get(key);
        }
        Rectangle rectangle = Rectangle.getInstance(length, width);
        cache.put(key, rectangle);
        return rectangle;
    }

    public  Square createSquare(double side) {
        String key = "Square_" + side;
        if (cache.containsKey(key)){
            return (Square) cache.get(key);
        }
        Square square = Square.getInstance(side);
        cache.put(key, square);
        return square;
    }
}

