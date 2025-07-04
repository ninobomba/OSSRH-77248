package io.github.ninobomba.commons.j2p;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;

/**
 * This class is responsible for annotating Java classes using Lombok annotations based on the provided JSON schema.
 */
public class Json2PojoLombokAnnotator extends AbstractAnnotator {
	
	
	/**
	 * Annotates a Java class using Lombok annotations based on the provided JSON schema.
	 *
	 * @param clazz  the Java class to annotate
	 * @param schema the JSON schema
	 */
	@Override
	public void propertyInclusion ( JDefinedClass clazz, JsonNode schema ) {
		JsonNode additionalProperties = schema.get ( "additionalProperties" );
		if ( additionalProperties == null ) return;
		
		additionalProperties.fieldNames ( ).forEachRemaining ( e -> {
			
			switch ( e.toLowerCase ( ) ) {
				case "lombok-builder":
					clazz.annotate ( lombok.Builder.class );
					break;
				case "lombok-data":
					clazz.annotate ( lombok.Data.class );
					break;
				case "lombok-no-arg":
					clazz.annotate ( lombok.NoArgsConstructor.class );
					break;
				case "lombok-all-args-constructor":
					clazz.annotate ( lombok.AllArgsConstructor.class );
					break;
				case "lombok-to-string":
					clazz.annotate ( lombok.ToString.class );
					break;
				case "lombok-value":
					clazz.annotate ( lombok.Value.class );
					break;
				case "lombok-getter":
					clazz.annotate ( lombok.Getter.class );
					break;
				case "lombok-setter":
					clazz.annotate ( lombok.Setter.class );
					break;
				case "lombok-equals-and-hashcode":
					clazz.annotate ( lombok.EqualsAndHashCode.class );
					break;
				default:
					break; // do nothing.
			}
			
		} );
		
	}
	
}
