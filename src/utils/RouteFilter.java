package utils;

import data.Route;

import java.util.Comparator;

/**
 * enum for easier routes filtering
 */
public enum RouteFilter implements Comparator<Route> {
    /**
     * use when decrease order is needed
     */
    DISTANCE_LESS {
        @Override
        public int compare(Route o1, Route o2) {
            if (o1.getDistance() < o2.getDistance()) return -1;
            return (o1.getDistance() > o2.getDistance()) ? 0 : 1;
        }
    },
    /**
     * use when increase order is needed
     */
    DISTANCE_GREATER {
        @Override
        public int compare(Route o1, Route o2) {
            if (o1.getDistance() > o2.getDistance()) return -1;
            return (o1.getDistance() < o2.getDistance()) ? 0 : 1;
        }
    }

}
