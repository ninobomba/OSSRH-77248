package io.github.ninobomba.commons.checkpoints;

import lombok.Data;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CheckPoint implements Cloneable
{
    private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" );

    private String id;
    private String module;
    private String name;
    private String description;
    private int order;

    private Boolean completed;

    private LocalDateTime localDateTime;
    private String formattedTimestamp;

    public void update() {
        localDateTime = LocalDateTime.now();
        formattedTimestamp = localDateTime.format( pattern );
        completed = true;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @SneakyThrows
    public String toJsonString()
    {
        return "{"
                .concat( "\"id\":" + "\"" + id + "\"" + "," )
                .concat( "\"module\":" + "\"" + module + "\"" + "," )
                .concat( "\"name\":" + "\"" + name + "\"" + ",")
                .concat( "\"order\":" + "\"" + order + "\"" + "," )
                .concat( "\"completed\":" + "\"" + completed + "\"" + "," )
                .concat( "\"localDateTime\":" + "\"" + localDateTime + "\"" + "," )
                .concat( "\"formattedTimestamp\":" + "\"" + formattedTimestamp + "\"" )
                .concat( "}" );
    }

}
