import com.osk.AxiomaTest;
import com.osk.City;
import com.osk.Road;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Kirill on 13.07.2020.
 */
public class RoadTest {

    @Test
    public void apiTest() {
        AxiomaTest app = new AxiomaTest();

        //add mew city test
        assertTrue(app.addCity("City1", 1, 1));
        assertTrue(app.addCity("City2", 2, 2));

        assertFalse(app.addCity("City1", 1, 1));
        assertFalse(app.addCity("City2", 1, 1));

        // get city by name test
        City city1 = app.getCityByName("City1");
        assertNotNull(city1);
        City city2 = app.getCityByName("City2");
        assertNotNull(city2);
        assertNull(app.getCityByName("City3"));

        // add road test
        assertTrue(app.addRoad(city1, city2));
        assertFalse(app.addRoad(city2, city1));

        // road name test
        String roadName = Road.getNameForRoad(city1, city2);
        assertEquals(roadName, Road.getNameForRoad(city2, city1));

        // get road list by city name test
        List<Road> roads = app.getRoadListByCityName("City1");
        assertNotNull(roads.get(0));

        assertEquals(roads.get(0).getCity1(), city1);
        assertEquals(roads.get(0).getCity2(), city2);

        //remove road test
        assertNotNull(app.removeRoad(city2, city1));
        assertNull(app.removeRoad(city1, city2));

    }

    @Test
    public void testConcurrent() {
        AxiomaTest app = new AxiomaTest();
        List<Thread> test = new ArrayList<>();

        for (int i = 0; i < 100; i = i + 10) {
            test.add(new Thread(new ConcurrentTest(app, i)));
        }

        for (Thread t : test) {
            t.start();
        }

        try {
            for (Thread t : test) {
                t.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            assertNotNull(app.getCityByName("City" + i));
        }
    }
}

class ConcurrentTest implements Runnable {
    private int i;
    private AxiomaTest app;

    List<City> cities = new ArrayList<>();

    public ConcurrentTest(AxiomaTest app, int i) {
        this.app = app;
        this.i = i;
    }

    @Override
    public void run() {
        Random r = new Random();
        for (int j = i; j < i + 10; j++) {
            City city = new City("City" + j, r.nextFloat(), r.nextFloat());
            app.addCity(city);
            cities.add(city);
        }
        for (int j = 0; j < 5; j++) {
            app.addRoad(cities.get(j), cities.get(j + 4));
        }
    }
}
