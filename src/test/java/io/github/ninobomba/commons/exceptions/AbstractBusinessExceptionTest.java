package io.github.ninobomba.commons.exceptions;

import io.github.ninobomba.commons.exceptions.process.BusinessExceptionAbstractFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AbstractBusinessExceptionTest
{

    @Test
    public void createTest()
    {
        var exception = BusinessExceptionAbstractFactory.create(
                BusinessExceptionAbstractFactory.ActionType.BUILD,
                "Invalid data",
                "Not enough money"
        );
        assertThat( exception ).isNotNull();
        System.out.println( exception );

    }
}
