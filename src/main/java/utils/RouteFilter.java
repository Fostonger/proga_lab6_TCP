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
    },
    BY_NAME {
        @Override
        public int compare(Route o1, Route o2) {
            if (o1 == null) return 1;
            else if(o2 == null) return -1;
            char[] o1Array = o1.getName().toCharArray();
            char[] o2Array = o2.getName().toCharArray();
            for (int i = 0; i < Math.min(o1Array.length, o2Array.length); i++) {
                if (Character.getNumericValue(o1Array[i]) > Character.getNumericValue(o2Array[i])) return 1;
                if (Character.getNumericValue(o1Array[i]) < Character.getNumericValue(o2Array[i])) return -1;
            }
            return 0;
        }
    },

}
