package io.github.ninobomba.commons.exceptions.core.messages;

import io.github.ninobomba.commons.exceptions.types.process.TransactionProcessException;
import io.github.ninobomba.commons.exceptions.types.system.SystemIllegalAccessException;
import io.github.ninobomba.commons.project.IPackageUtils;
import org.junit.jupiter.api.Test;

import java.util.Optional;


class LocalExceptionMessageTest {

    @Test
    void testInLineInstanceCreator() {

        var message = LocalExceptionMessage.builder()
                .code("200")
                .message("OK")
                .description(format("processed: {}, {}, {}", 5, 6, "C", 1.7))
                .tClass(SystemIllegalAccessException.class)
                .throwable(null)
                .build();
        var exception = message.create();

        System.out.println(exception);

    }

    @Test
    void test() {
        var exception = LocalExceptionMessage.builder()
                .code("200")
                .message("OK")
                .description(format("processed: {}, {}, {}", 1, 2, "A"))
                .tClass( SystemIllegalAccessException.class )
                .separator( LocalExceptionMessage.SEPARATORS.SYSTEM_SEPARATOR.value )
                .throwable( null )
                .level( LocalExceptionMessage.ExceptionLevel.CRITICAL )
                .build()
                .create();
        System.out.println(exception);
    }

    @Test
    void testWithThrowable() {
        var exception = LocalExceptionMessage.builder()
                .id(1_000 )
                .code("500")
                .message(String.format("Server Error: %d", 501))
                .description("error when running transaction")
                .tClass(TransactionProcessException.class)
                .throwable(new Throwable("Invalid transaction exception"))
                .build()
                .create();

        System.out.println(exception);
    }

    @Test
    void testAll() {

        var classes = IPackageUtils.getCustomExceptionSet("io.github.ninobomba.commons.exceptions.types");

        classes.forEach(e -> {
            var exception = LocalExceptionMessage.builder()
                    .code("200")
                    .message("OK")
                    .description("processed")
                    .tClass((Class<? extends Exception>) e)
                    .build()
                    .create();

            System.out.println(exception);
        });
    }

    static String format(String processingMessage, Object... parameters) {
        Object[] OBJECT_EMPTY_ARRAY = new Object[]{};
        for (Object parameter : Optional.ofNullable(parameters).orElse(OBJECT_EMPTY_ARRAY))
            processingMessage = processingMessage.replaceFirst("\\{}", String.valueOf(parameter).replaceAll("[^\\dA-Za-z ]", "\\\\$0"));
        return processingMessage;
    }

}
