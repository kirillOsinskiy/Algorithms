package com.osk;

/**
 * Created by Kirill on 13.07.2020.
 */
public class Road {

    private String name;
    private double length;

    private City city1, city2;

    public Road(City city1, City city2) {
        this.city1 = city1;
        this.city2 = city2;

        name = getNameForRoad(city1, city2);

        length = Math.sqrt(Math.pow((city2.getX() - city1.getX()), 2)
                + Math.pow((city2.getY() - city1.getY()), 2));
    }

    public static String getNameForRoad(City city1, City city2) {
        String firstCityName = city1.getName();
        String secondCityName = city2.getName();
        if (firstCityName.compareTo(secondCityName) > 0) {
            return firstCityName + " - " + secondCityName;
        } else if (firstCityName.compareTo(secondCityName) < 0) {
            return secondCityName + " - " + firstCityName;
        } else {
            throw new RuntimeException("Cities names are not unique!");
        }
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public City getCity1() {
        return city1;
    }

    public City getCity2() {
        return city2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Road road = (Road) o;

        if (Double.compare(road.length, length) != 0) return false;
        if (!name.equals(road.name)) return false;
        if (!city1.equals(road.city1)) return false;
        return city2.equals(road.city2);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + city1.hashCode();
        result = 31 * result + city2.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Road{" +
                "name='" + name + '\'' +
                '}';
    }
}
