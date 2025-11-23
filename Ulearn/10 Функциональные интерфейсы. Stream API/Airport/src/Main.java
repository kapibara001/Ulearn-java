import java.util.List;
import java.util.stream.Collectors;

import com.airport.Airport;
import com.airport.Flight;

public class Main {
    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        //TODO используя библиотеку Airport и Stream API, найдите самолеты вылетающие в ближайшие два часа
        long nowTime = System.currentTimeMillis();
        long neededTime = java.util.concurrent.TimeUnit.HOURS.toMillis(2);

        return airport.getTerminals().stream()
                    .flatMap(terminal -> terminal.getFlights().stream())
                    .filter(flight -> flight.getType() == Flight.Type.DEPARTURE)
                    .filter(flight -> {
                        long flightTime = flight.getDate().getTime();

                        return flightTime >= nowTime && flightTime <= nowTime + neededTime;
                    })
                    .collect(Collectors.toList());
    }
}