package io.github.ninobomba.commons.jpa.id;

import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;
import lombok.SneakyThrows;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomHbIdGenerator implements IdentifierGenerator
{
    @SneakyThrows
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object)  {
        return IdGeneratorSnowFlakeSupport.getInstance().getNextId();
    }

}
