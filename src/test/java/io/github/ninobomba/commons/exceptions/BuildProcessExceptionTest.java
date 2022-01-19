package io.github.ninobomba.commons.exceptions;

import io.github.ninobomba.commons.exceptions.process.BuildProcessException;
import org.junit.jupiter.api.Test;

public class BuildProcessExceptionTest
{

    @Test
    public void createTest1() {
        BuildProcessException.create( "Error while running build test", "unexpected json value" );
    }

    @Test
    public void createTest2() {
        Throwable throwable = new RuntimeException( "error" );
        BuildProcessException.create( "username is null", "name is null", throwable );
    }

}
