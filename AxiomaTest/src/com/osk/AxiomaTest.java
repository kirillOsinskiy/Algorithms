package com.osk;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kirill on 13.07.2020.
 */
public class AxiomaTest {

    private final ConcurrentHashMap<String, City> cities = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Road> roads = new ConcurrentHashMap<>();

    /**
     * Add new city
     *
     * @param name city name
     * @param x    city x coordinate
     * @param y    city y coordinate
     * @return true if added, false if already exists
     */
    public boolean addCity(String name, float x, float y) {
        return addCity(new City(name, x, y));
    }

    /**
     * Add new city
     * @param city object param
     * @return true if added, false if already exists
     */
    public boolean addCity(City city) {
        if (cities.containsKey(city.getName())) return false;
        cities.putIfAbsent(city.getName(), city);
        return true;
    }

    /**
     * Add new road between city1 and city2.
     *
     * @param city1 first city
     * @param city2 second city
     * @return true if added, false if road already exists.
     * Thrown IllegalArgumentException if city1 or city2 does not exists in cities collection
     */
    public boolean addRoad(City city1, City city2) {
        if (!cities.containsKey(city1.getName()))
            throw new IllegalArgumentException("City " + city1.getName() + " does not exists!");
        if (!cities.containsKey(city2.getName()))
            throw new IllegalArgumentException("City " + city2.getName() + " does not exists!");

        Road road = new Road(city1, city2);

        if (roads.containsKey(road.getName())) return false;
        roads.putIfAbsent(road.getName(), road);

        city1.addRoad(road);
        city2.addRoad(road);

        return true;
    }

    /**
     * Remove road from collection and also remove this road from cities.
     *
     * @param roadName name
     * @return Road object that was removed or null if road with given name not exists in collection
     */
    public Road removeRoad(String roadName) {
        Road road = roads.remove(roadName);
        if (road != null) {
            road.getCity1().removeRoad(roadName);
            road.getCity2().removeRoad(roadName);
        }
        return road;
    }

    /**
     * Romve road between city1 and city2
     * @param city1 first
     * @param city2 second
     * @return road object that was removed or null if not exists
     */
    public Road removeRoad(City city1, City city2) {
        return removeRoad(Road.getNameForRoad(city1, city2));
    }

    /**
     * Return city by given name.
     *
     * @param name city name
     * @return city object or null if city with given name does not exists
     */
    public City getCityByName(String name) {
        return cities.get(name);
    }

    /**
     * Return road list for specific city with given city name
     * @param cityName name of city
     * @return List of roads for city, null if city does not exists
     */
    public List<Road> getRoadListByCityName(String cityName) {
        if(cities.containsKey(cityName)) return cities.get(cityName).getRoads();
        return null;
    }

}
