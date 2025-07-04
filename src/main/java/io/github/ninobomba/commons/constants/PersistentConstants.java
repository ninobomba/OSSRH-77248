package io.github.ninobomba.commons.constants;

public interface PersistentConstants {

    enum RowAuditoryValues {
        ;
        public static final String ENABLED = "ENABLED";
        public static final String DISABLED = "DISABLED";

        public static final String CREATED_BY = "SYSTEM";
        public static final String LAST_MODIFIED_BY = "SYSTEM";
    }

}
