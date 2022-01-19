package tech.tools4monkeys.commons.jpa.audit;

import tech.tools4monkeys.commons.json.JsonUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AuditableEntity implements Serializable
{
    private static final long serialVersionUID = -1381710822390094232L;

    //@CreatedBy
    private String createdBy;

    //@CreatedDate
    private LocalDateTime createdTimestamp;

    //@LastModifiedBy
    private String lastModifiedBy;

    //@LastModifiedDate
    private LocalDateTime lastModifiedTimestamp;

    private String status;

    public AuditableEntity() {
        createdTimestamp = LocalDateTime.now();
        lastModifiedTimestamp = LocalDateTime.now();
    }

    public String toJsonString(boolean pretty) {

        var response = "{"
                .concat( "\"CreatedBy\":\"" + createdBy + "\",")
                .concat( "\"CreatedTimestamp\":\"" + createdTimestamp.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" )) + "\"," )
                .concat( "\"LastModifiedBy\":\"" + lastModifiedBy + "\"," )
                .concat( "\"LastModifiedTimestamp\":\"" + lastModifiedTimestamp.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" )) + "\"," )
                .concat( "\"Status\":\"" + status + "\"" )
                .concat( "}" );

        return pretty ? JsonUtils.pretty( response ) : response;
    }

}
