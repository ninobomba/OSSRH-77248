package io.github.ninobomba.commons;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;

public class LocalAbstractSetupTest {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
    }

    @SneakyThrows
    public static <T> T getBodyFromPath(String path, TypeReference<T> type)
    {
        try ( var inputStream = LocalAbstractSetupTest.class.getClassLoader().getResourceAsStream( path ) )
        {
            assert inputStream != null;
            return objectMapper
                    .readValue( IOUtils.toString( inputStream, StandardCharsets.UTF_8 ).replaceAll("[\\r\\n]+", ""), type );
        }
    }

}
