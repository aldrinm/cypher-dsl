/*
 * Copyright (c) "Neo4j"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypherdsl.parser;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import org.apiguardian.api.API;
import org.neo4j.cypher.internal.parser.javacc.CharStream;
import org.neo4j.cypher.internal.parser.javacc.Cypher;
import org.neo4j.cypher.internal.parser.javacc.CypherCharStream;
import org.neo4j.cypher.internal.parser.javacc.ParseException;
import org.neo4j.cypherdsl.core.Clause;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.RelationshipPattern;

/**
 * @author Michael J. Simons
 * @since TBA
 */
@API(status = EXPERIMENTAL, since = "TBA")
public final class CypherParser {

	/**
	 * Parses a Cypher fragment describing a Node-pattern into a {@link Node} instance.
	 *
	 * @param input A Cypher fragment
	 * @return A node
	 */
	public static Node parseNode(String input) {

		return handle(() -> (Node) new Cypher(CypherDslASTFactory.INSTANCE,
			CypherDslASTExceptionFactory.INSTANCE,
			getCharStream(input)).NodePattern());
	}

	/**
	 * Parses a Cypher fragment describing a relationship into a {@link RelationshipPattern} instance.
	 *
	 * @param input A Cypher fragment
	 * @return A relationship pattern or chain of relationship pattern
	 */
	public static RelationshipPattern parseRelationship(String input) {

		return handle(() -> (RelationshipPattern) new Cypher(CypherDslASTFactory.INSTANCE,
			CypherDslASTExceptionFactory.INSTANCE,
			getCharStream(input)).Pattern());
	}

	/**
	 * Parses a Cypher expression into an {@link Expression}.
	 *
	 * @param input A Cypher fragment of an expression
	 * @return A valid Cypher-DSL expression instance
	 */
	public static Expression parseExpression(String input) {

		return handle(() -> (Expression) new Cypher(CypherDslASTFactory.INSTANCE,
			CypherDslASTExceptionFactory.INSTANCE,
			getCharStream(input)).Expression());
	}

	public static Clause parseClause(String input) {

		return handle(() -> (Clause) new Cypher(CypherDslASTFactory.INSTANCE,
			CypherDslASTExceptionFactory.INSTANCE,
			getCharStream(input)).Clause());
	}

	private static <T> T handle(ThrowingParser<T> parser) {
		try {
			return parser.parse();
		} catch (ParseException e) {
			throw new CyperDslParseException(e);
		}
	}

	@FunctionalInterface
	private interface ThrowingParser<T> {

		T parse() throws ParseException;
	}

	private static CharStream getCharStream(String input) {
		return new CypherCharStream(input);
	}

	/**
	 * Not to be instantiated.
	 */
	private CypherParser() {
	}
}
