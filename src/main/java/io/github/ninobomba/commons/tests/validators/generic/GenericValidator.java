package io.github.ninobomba.commons.tests.validators.generic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class GenericValidator extends AbstractValidator<Object, GenericValidator> {

    private static final String MSG_NULL = "is null";
    private static final String MSG_EMPTY = "is empty";
    private static final String MSG_BLANK = "is blank";
    private static final String MSG_MIN = "must be greater than or equal to %d";
    private static final String MSG_MAX = "must be less than or equal to %d";
    private static final String MSG_DECIMAL_MIN = "must be greater than or equal to %s";
    private static final String MSG_DECIMAL_MAX = "must be less than or equal to %s";
    private static final String MSG_NEGATIVE = "must be less than 0";
    private static final String MSG_NEGATIVE_OR_ZERO = "must be less than or equal to 0";
    private static final String MSG_POSITIVE = "must be greater than 0";
    private static final String MSG_POSITIVE_OR_ZERO = "must be greater than or equal to 0";
    private static final String MSG_TRUE = "must be true";
    private static final String MSG_FALSE = "must be false";
    private static final String MSG_EMAIL = "must be a well-formed email address";
    private static final String MSG_PATTERN = "must match \"%s\"";
    private static final String MSG_FUTURE = "must be a future date";
    private static final String MSG_FUTURE_OR_PRESENT = "must be a date in the present or in the future";
    private static final String MSG_PAST = "must be a past date";
    private static final String MSG_PAST_OR_PRESENT = "must be a date in the past or in the present";
    private static final String MSG_SIZE = "size must be between %d and %d";
    private static final String MSG_DIGITS = "numeric value out of bounds (<%d digits>.<%d digits> expected)";
    private static final String MSG_STARTS_WITH = "must start with \"%s\"";
    private static final String MSG_ENDS_WITH = "must end with \"%s\"";
    private static final String MSG_CONTAINS = "must contain \"%s\"";
    private static final String MSG_IN = "must be one of %s";
    private static final String MSG_RANGE = "must be between %d and %d";

    // Simple Email Regex
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private GenericValidator(Object value) {
        super(value);
    }

    public static GenericValidator of(Object value) {
        return new GenericValidator(value);
    }

    public GenericValidator isNotNull() {
        return check(Objects::nonNull, MSG_NULL);
    }

    public GenericValidator isNull() {
        return check(Objects::isNull, "must be null");
    }

    public GenericValidator isNotEmpty() {
        return check(val -> {
            if (val == null)
                return true; // @NotEmpty usually implies @NotNull, but here we strictly check empty if not null? Standard implies not null.
            if (val instanceof CharSequence s) return !s.isEmpty();
            if (val instanceof Collection<?> c) return !c.isEmpty();
            if (val instanceof Map<?, ?> m) return !m.isEmpty();
            if (val instanceof Object[] a) return a.length > 0;
            return true;
        }, MSG_EMPTY);
    }

    public GenericValidator isNotBlank() {
        return check(val -> val == null || (val instanceof CharSequence s && !s.toString().isBlank()), MSG_BLANK);
    }

    public GenericValidator hasMin(long min) {
        return check(val -> {
            if (val == null) return true;
            if (val instanceof BigDecimal bd) return bd.compareTo(BigDecimal.valueOf(min)) >= 0;
            if (val instanceof BigInteger bi) return bi.compareTo(BigInteger.valueOf(min)) >= 0;
            if (val instanceof Number n) return n.longValue() >= min;
            if (val instanceof CharSequence s) return s.length() >= min;
            if (val instanceof Collection<?> c) return c.size() >= min;
            if (val instanceof Map<?, ?> m) return m.size() >= min;
            if (val instanceof Object[] a) return a.length >= min;
            return true;
        }, String.format(MSG_MIN, min));
    }

    public GenericValidator hasMax(long max) {
        return check(val -> {
            if (val == null) return true;
            if (val instanceof BigDecimal bd) return bd.compareTo(BigDecimal.valueOf(max)) <= 0;
            if (val instanceof BigInteger bi) return bi.compareTo(BigInteger.valueOf(max)) <= 0;
            if (val instanceof Number n) return n.longValue() <= max;
            if (val instanceof CharSequence s) return s.length() <= max;
            if (val instanceof Collection<?> c) return c.size() <= max;
            if (val instanceof Map<?, ?> m) return m.size() <= max;
            if (val instanceof Object[] a) return a.length <= max;
            return true;
        }, String.format(MSG_MAX, max));
    }

    public GenericValidator isTrue() {
        return check(val -> val == null || (val instanceof Boolean b && b), MSG_TRUE);
    }

    public GenericValidator isFalse() {
        return check(val -> val == null || (val instanceof Boolean b && !b), MSG_FALSE);
    }

    public GenericValidator hasDecimalMin(String value, boolean inclusive) {
        BigDecimal min = new BigDecimal(value);
        return check(val -> {
            if (val == null) return true;
            BigDecimal numVal = toBigDecimal(val);
            if (numVal == null) return false;
            int comparison = numVal.compareTo(min);
            return inclusive ? comparison >= 0 : comparison > 0;
        }, String.format(MSG_DECIMAL_MIN, value + (inclusive ? "" : " (exclusive)")));
    }

    public GenericValidator hasDecimalMax(String value, boolean inclusive) {
        BigDecimal max = new BigDecimal(value);
        return check(val -> {
            if (val == null) return true;
            BigDecimal numVal = toBigDecimal(val);
            if (numVal == null) return false;
            int comparison = numVal.compareTo(max);
            return inclusive ? comparison <= 0 : comparison < 0;
        }, String.format(MSG_DECIMAL_MAX, value + (inclusive ? "" : " (exclusive)")));
    }

    public GenericValidator isNegative() {
        return check(val -> {
            if (val == null) return true;
            BigDecimal numVal = toBigDecimal(val);
            return numVal != null && numVal.signum() < 0;
        }, MSG_NEGATIVE);
    }

    public GenericValidator isNegativeOrZero() {
        return check(val -> {
            if (val == null) return true;
            BigDecimal numVal = toBigDecimal(val);
            return numVal != null && numVal.signum() <= 0;
        }, MSG_NEGATIVE_OR_ZERO);
    }

    public GenericValidator isPositive() {
        return check(val -> {
            if (val == null) return true;
            BigDecimal numVal = toBigDecimal(val);
            return numVal != null && numVal.signum() > 0;
        }, MSG_POSITIVE);
    }

    public GenericValidator isPositiveOrZero() {
        return check(val -> {
            if (val == null) return true;
            BigDecimal numVal = toBigDecimal(val);
            return numVal != null && numVal.signum() >= 0;
        }, MSG_POSITIVE_OR_ZERO);
    }

    public GenericValidator hasDigits(int integer, int fraction) {
        return check(val -> {
            if (val == null) return true;
            BigDecimal numVal = toBigDecimal(val);
            if (numVal == null) return false;
            return numVal.precision() - numVal.scale() <= integer && numVal.scale() <= fraction;
        }, String.format(MSG_DIGITS, integer, fraction));
    }

    public GenericValidator isEmail() {
        return check(val -> val == null || (val instanceof CharSequence s && EMAIL_PATTERN.matcher(s).matches()), MSG_EMAIL);
    }

    public GenericValidator matches(String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        return check(val -> val == null || (val instanceof CharSequence s && pattern.matcher(s).matches()), String.format(MSG_PATTERN, regexp));
    }

    public GenericValidator hasSize(int min, int max) {
        return check(val -> {
            if (val == null) return true;
            if (val instanceof CharSequence s) return s.length() >= min && s.length() <= max;
            if (val instanceof Collection<?> c) return c.size() >= min && c.size() <= max;
            if (val instanceof Map<?, ?> m) return m.size() >= min && m.size() <= max;
            if (val instanceof Object[] a) return a.length >= min && a.length <= max;
            return true;
        }, String.format(MSG_SIZE, min, max));
    }

    public GenericValidator isInFuture() {
        return check(val -> isTimeComparisonValid(val, 1), MSG_FUTURE);
    }

    public GenericValidator isInFutureOrPresent() {
        return check(val -> isTimeComparisonValid(val, 0) || isTimeComparisonValid(val, 1), MSG_FUTURE_OR_PRESENT);
    }

    public GenericValidator isInPast() {
        return check(val -> isTimeComparisonValid(val, -1), MSG_PAST);
    }

    public GenericValidator isInPastOrPresent() {
        return check(val -> isTimeComparisonValid(val, 0) || isTimeComparisonValid(val, -1), MSG_PAST_OR_PRESENT);
    }

    public GenericValidator startsWith(String prefix) {
        return check(val -> val == null || (val instanceof CharSequence s && s.toString().startsWith(prefix)), String.format(MSG_STARTS_WITH, prefix));
    }

    public GenericValidator endsWith(String suffix) {
        return check(val -> val == null || (val instanceof CharSequence s && s.toString().endsWith(suffix)), String.format(MSG_ENDS_WITH, suffix));
    }

    public GenericValidator contains(String substring) {
        return check(val -> val == null || (val instanceof CharSequence s && s.toString().contains(substring)), String.format(MSG_CONTAINS, substring));
    }

    public GenericValidator isIn(Object... values) {
        List<Object> allowed = List.of(values);
        return check(val -> val == null || allowed.contains(val), String.format(MSG_IN, allowed));
    }

    public GenericValidator isInRange(long min, long max) {
        return check(val -> {
            if (val == null) return true;
            if (val instanceof BigDecimal bd)
                return bd.compareTo(BigDecimal.valueOf(min)) >= 0 && bd.compareTo(BigDecimal.valueOf(max)) <= 0;
            if (val instanceof BigInteger bi)
                return bi.compareTo(BigInteger.valueOf(min)) >= 0 && bi.compareTo(BigInteger.valueOf(max)) <= 0;
            if (val instanceof Number n) return n.longValue() >= min && n.longValue() <= max;
            return true;
        }, String.format(MSG_RANGE, min, max));
    }

    // --- Helpers ---

    private BigDecimal toBigDecimal(Object val) {
        if (val instanceof BigDecimal bd) return bd;
        if (val instanceof BigInteger bi) return new BigDecimal(bi);
        if (val instanceof Number n) return new BigDecimal(n.toString());
        if (val instanceof CharSequence s) {
            try {
                return new BigDecimal(s.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private boolean isTimeComparisonValid(Object val, int sign) {
        if (val == null) return true;

        if (val instanceof LocalDate d) {
            LocalDate now = LocalDate.now();
            return sign > 0 ? d.isAfter(now) : sign < 0 ? d.isBefore(now) : d.isEqual(now);
        }
        if (val instanceof LocalDateTime d) {
            LocalDateTime now = LocalDateTime.now();
            return sign > 0 ? d.isAfter(now) : sign < 0 ? d.isBefore(now) : d.isEqual(now);
        }
        if (val instanceof Instant i) {
            Instant now = Instant.now();
            return sign > 0 ? i.isAfter(now) : sign < 0 ? i.isBefore(now) : i.equals(now);
        }
        if (val instanceof ZonedDateTime z) {
            ZonedDateTime now = ZonedDateTime.now();
            return sign > 0 ? z.isAfter(now) : sign < 0 ? z.isBefore(now) : z.isEqual(now);
        }
        if (val instanceof java.util.Date d) {
            java.util.Date now = new java.util.Date();
            return sign > 0 ? d.after(now) : sign < 0 ? d.before(now) : d.equals(now);
        }

        return false;
    }
}
