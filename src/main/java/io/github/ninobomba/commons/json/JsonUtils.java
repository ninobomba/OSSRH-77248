package io.github.ninobomba.commons.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

public interface JsonUtils
{

    ObjectMapper objectMapper = new ObjectMapper().enable( SerializationFeature.INDENT_OUTPUT );

    static boolean isValidJson(String json)
    {
        try {
            objectMapper.readTree( json );
        } catch( JsonProcessingException e ) {
            return false;
        }
        return true;
    }

    @SneakyThrows
    static String pretty( String json )
    {
        return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString( objectMapper.readValue( json, Object.class ) );
    }

}
