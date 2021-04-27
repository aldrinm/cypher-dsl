package org.neo4j.cypherdsl.parser;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import org.apiguardian.api.API;

/**
 * @author Michael J. Simons
 * @since TBA
 */
@API(status = EXPERIMENTAL, since = "TBA")
public final class CyperDslParseException extends RuntimeException {

	public CyperDslParseException(Throwable cause) {
		super(cause);
	}
}
