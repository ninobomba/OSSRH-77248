package io.github.ninobomba.commons.unit.exceptions.commons;

import io.github.ninobomba.commons.catalogs.financial.CatalogFinancialMessages;
import org.junit.jupiter.api.Test;

class CatalogNumericMessagesTest {

	@Test
	void values ( ) {
		CatalogFinancialMessages[] values = CatalogFinancialMessages.values ( );
		assert values.length > 0;
		for ( CatalogFinancialMessages value : values )
			System.out.println ( value );

		System.out.println ( );

		CatalogFinancialMessages.class.getEnumConstants ( );
		for ( CatalogFinancialMessages value : CatalogFinancialMessages.class.getEnumConstants ( ) ) {
			assert value != null && ! value.name ( ).isBlank ( ) && ! value.description.isBlank ( );
			System.out.println ( value );
		}
	}

}
