package io.github.ninobomba.commons.exceptions;

import io.github.ninobomba.commons.exceptions.process.AbstractFactoryBusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AbstractBusinessExceptionTest
{

    @Test
    public void createTest()
    {
        var exception = AbstractFactoryBusinessException.create(
                AbstractFactoryBusinessException.ActionType.BUILD,
                "Invalid data",
                "Not enough money"
        );
        assertThat( exception ).isNotNull();
        System.out.println( exception );

    }
}
