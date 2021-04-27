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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Node;

/**
 * @author Michael J. Simons
 */
class CypherParserTest {

	@Nested
	class Nodes {

		@Test
		void shouldParseAnyNode() {

			var node = CypherParser.parseNode("()");
			assertNode(node, "()");
		}

		@ParameterizedTest
		@ValueSource(strings = { ":`A`", ":A", ":A:B", ":A:`B`:C" })
		void shouldParseNodeWithLabels(String labels) {

			var node = CypherParser.parseNode(String.format("(%s)", labels));
			var expected = Arrays.stream(labels.split(":")).filter(l -> !l.isEmpty())
				.map(l -> l.startsWith("`") ? l : "`" + l + "`").collect(
					Collectors.joining(":", "(:", ")"));
			assertNode(node, expected);
		}

		@Test
		void shouldParseNodeWithName() {
			var node = CypherParser.parseNode("(m)");
			assertNode(node, "(m)");
		}

		@Test
		void shouldParseNodeWithNameAndLabel() {
			var node = CypherParser.parseNode("(m:Movie)");
			assertNode(node, "(m:`Movie`)");
		}

		@Test
		void shouldParseProperties() {
			var node = CypherParser.parseNode("(m {a:'b'})");
			assertNode(node, "(m {a: 'b'})");
		}
	}

	@Nested
	class Numbers {

		@ParameterizedTest
		@CsvSource({ "1, 1", "-1, -1", "0XF, 15", "0xF, 15", "-0xE, -14", "010, 8", "-010, -8" })
		void shouldParseIntegers(String input, int expected) {
			Expression e = CypherParser.parseExpression(input);
			assertThat(Cypher.returning(e).build().getCypher())
				.isEqualTo(String.format("RETURN %d", expected));
		}
	}

	@Nested
	class Booleans {

		@ParameterizedTest
		@CsvSource({ "TRUE, true", "true, true", "True, true", "fAlse, false", "FALSE, false" })
		void shouldParseBooleans(String input, String expected) {
			Expression e = CypherParser.parseExpression(input);
			assertThat(Cypher.returning(e).build().getCypher())
				.isEqualTo(String.format("RETURN %s", expected));
		}
	}

	void assertNode(Node node, String cypherDslRepresentation) {

		assertThat(Cypher.match(node).returning(Cypher.asterisk()).build().getCypher())
			.isEqualTo(String.format("MATCH %s RETURN *", cypherDslRepresentation));
	}
}