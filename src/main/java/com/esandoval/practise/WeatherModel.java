package com.esandoval.practise;

import com.esandoval.practise.astrobody.Planet;
import com.esandoval.practise.planetarysystem.ThreePlanetSystem;
import com.esandoval.practise.simulator.Simulator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static java.lang.String.format;

public class WeatherModel {

    private static final String TABLE = "weather";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_WEATHER = "weather";

    //TODO: use a better way to connect and close to DB (Spring boot should have one)
    private Connection conn;
    private Statement stmt;

    public WeatherModel() {
        try {
            Map<Integer, String> history = new Simulator(10, new ThreePlanetSystem(
                    new Planet("Ferengi", 1, 500, true),
                    new Planet("Betasoide", 3, 2000, true),
                    new Planet("Vulcano", 5, 1000, false))
            ).getHistory();

            conn = DriverManager.getConnection("jdbc:derby:memory:demo;create=true");
            stmt = conn.createStatement();
            stmt.executeUpdate(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s VARCHAR(30))", TABLE, COLUMN_DAY, COLUMN_WEATHER));
            for (Integer key : history.keySet()) {
                stmt.executeUpdate(format("INSERT INTO %s VALUES (%s, '%s')", TABLE, key, history.get(key)));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not initialize DB Connection", e.getCause());
        }
    }

    public Weather getWeatherFromADay(Integer day) throws SQLException {
        ResultSet rs = stmt.executeQuery(format("SELECT * FROM %s WHERE day=%s", TABLE, day));
        rs.next();
        return new Weather(rs.getInt(COLUMN_DAY), rs.getString(COLUMN_WEATHER));
    }
}
