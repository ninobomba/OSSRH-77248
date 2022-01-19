package tech.tools4monkeys.commons.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonUtils
{

    private static final ObjectMapper objectMapper = new ObjectMapper().enable( SerializationFeature.INDENT_OUTPUT );

    public static boolean isValidJson(String json)
    {
        try {
            objectMapper.readTree( json );
        } catch( JsonProcessingException e ) {
            return false;
        }
        return true;
    }

    @SneakyThrows
    public static String pretty( String json )
    {
        return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString( objectMapper.readValue( json, Object.class ) );
    }

}



