package io.github.ninobomba.commons.jpa.id;

import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;
import lombok.SneakyThrows;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomHbIdGenerator implements IdentifierGenerator
{
    /**
     * Generates a unique identifier for the given object using the SnowFlake algorithm.
     *
     * @param session The session that is generating the identifier.
     * @param object The object for which the identifier is being generated.
     * @return A unique identifier for the object.
     * @throws Exception if an error occurs during the generation process.
     */
    @SneakyThrows
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object)  {
        return IdGeneratorSnowFlakeSupport.getInstance().getNextId();
    }

}
