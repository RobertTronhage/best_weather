package se.norrland.best_weather.clients.domain;

public record Temperature(Double value, String unit) implements Comparable<Temperature>{

    @Override
    public int compareTo(Temperature temp) {
        return value.compareTo(temp.value());
    }

}