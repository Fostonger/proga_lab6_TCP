package consoleReader;

public enum ReceiverRules implements ReceiverRuleInterface {
    /**
     * no rules are set, any object is accepted
     */
    NONE {
        public boolean isAcceptable(Object o1) {
            return true;
        }
    },
    /**
     * object has to be not null
     */
    NOT_NULL {
        public boolean isAcceptable(Object o1) {
            return o1 != null && !o1.equals("");
        }
    },
    /**
     * the object has to be not empty string or null
     */
    STRING_NULL_OR_NOT_EMPTY {
        public boolean isAcceptable(Object o1) {
            String str = (String) o1;
            return (str == null || !str.equals(""));
        }
    },
    /**
     * the object has to be not empty string
     */
    STRING_NOT_NULL_AND_NOT_EMPTY {
        public boolean isAcceptable(Object o1) {
            String str = (String) o1;
            return (str != null && !str.equals(""));
        }
    },
    /**
     * the object has to be double that greater than 1
     */
    DOUBLE_MIN_1 {
        public boolean isAcceptable(Object o1) {
            if (o1 == null || o1.equals("")) return false;
            double num = Double.parseDouble((String) o1);
            return (num > 1);
        }
    },
    /**
     * the object has to be nonnull double
     */
    NOT_NULL_DOUBLE {
        public boolean isAcceptable(Object o1) {
            if (o1 == null || o1.equals("")) return false;
            double num = Double.parseDouble((String) o1);
            return true;
        }
    },
    /**
     * the object has to be nonnull long
     */
    NOT_NULL_LONG {
        public boolean isAcceptable(Object o1) {
            if (o1 == null || o1.equals("")) return false;
            long num = Long.parseLong((String) o1);
            return true;
        }
    },
    /**
     * the object has to be nullable long
     */
    NULLABLE_LONG {
        public boolean isAcceptable(Object o1) {
            if (o1 == null || o1.equals("")) return true;
            long num = Long.parseLong((String) o1);
            return true;
        }
    },
    /**
     * the object has to be nonnull int
     */
    NOT_NULL_INT {
        public boolean isAcceptable(Object o1) {
            if (o1 == null || o1.equals("")) return false;
            int num = Integer.parseInt((String) o1);
            return true;
        }
    },
    /**
     * the object has to be nullable int
     */
    NULLABLE_INT {
        public boolean isAcceptable(Object o1) {
            if (o1 == null || o1.equals("")) return true;
            int num = Integer.parseInt((String) o1);
            return true;
        }
    },
    /**
     * the object has to be nonnull int that is less than 106
     */
    INT_MIN_106 {
        public boolean isAcceptable(Object o1) {
            if (o1 == null || o1.equals("")) return false;
            int num = Integer.parseInt((String) o1);
            return (num < 106);
        }
    },
}
