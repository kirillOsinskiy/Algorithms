package com.osk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kirill on 13.07.2020.
 */
public class City {
    private String name;
    private float x;
    private float y;

    private ConcurrentHashMap<String, Road> roads;

    public City(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;

        roads = new ConcurrentHashMap<>();
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public List<Road> getRoads() {
        return new ArrayList<>(roads.values());
    }

    void addRoad(Road road) {
        roads.put(road.getName(), road);
    }

    void removeRoad(String roadName) {
        roads.remove(roadName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (Float.compare(city.x, x) != 0) return false;
        if (Float.compare(city.y, y) != 0) return false;
        return name.equals(city.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + '[' + x + ", " + y + ']';
    }
}
