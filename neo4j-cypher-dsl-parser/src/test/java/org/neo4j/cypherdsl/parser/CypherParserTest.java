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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

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
import org.neo4j.cypherdsl.core.RelationshipPattern;

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

		@Test
		void shouldParseManyProperties() {
			var node = CypherParser.parseNode("(m {a:'b', c: 'd'})");
			assertNode(node, "(m {a: 'b', c: 'd'})");
		}
	}

	@Nested
	class RelationshipPatterns {

		@ParameterizedTest
		@CsvSource(nullValues = "N/A", value = { "N/A, N/A", "N/A, 5", "5, N/A", "5, 10", "-,-" })
		void simplePatternWithVariousLengths(String minimum, String maximum) {
			StringBuilder simplePattern = new StringBuilder("(n)-");
			if (minimum != null || maximum != null) {
				simplePattern.append("[*");
				if (minimum != null && !"-".equals(minimum)) {
					simplePattern.append(minimum);
				}
				if (!"-".equals(maximum)) {
					simplePattern.append("..");
					if (maximum != null) {
						simplePattern.append(maximum);
					}
				}
				simplePattern.append("]");
			}
			simplePattern.append("->(m)");
			var rel = CypherParser.parseRelationship(simplePattern.toString());
			assertThat(Cypher.match(rel).returning(Cypher.asterisk()).build().getCypher())
				.isEqualTo(String.format("MATCH %s RETURN *", simplePattern));
		}

		@ParameterizedTest
		@ValueSource(strings = { "T", "T1|T2", "T1|T2|T3" })
		void types(String types) {
			var rel = CypherParser.parseRelationship(String.format("(n)-[:%s]->(m)", types));
			assertThat(Cypher.match(rel).returning(Cypher.asterisk()).build().getCypher())
				.isEqualTo(String.format("MATCH (n)-[:%s]->(m) RETURN *",
					Arrays.stream(types.split("\\|")).map(v -> String.format("`%s`", v))
						.collect(Collectors.joining("|"))));
		}

		@ParameterizedTest
		@CsvSource({ "-,-", "<-,-", "-,->" })
		void direction(String left, String right) {
			StringBuilder simplePattern = new StringBuilder("(n)")
				.append(left)
				.append(right)
				.append("(m)");
			var rel = CypherParser.parseRelationship(simplePattern.toString());
			assertThat(Cypher.match(rel).returning(Cypher.asterisk()).build().getCypher())
				.isEqualTo(String.format("MATCH %s RETURN *", simplePattern));
		}

		@Test
		void pointyThingAtBothSidesIsNotSupported() {
			assertThatIllegalArgumentException().isThrownBy(() -> CypherParser.parseRelationship("(n)<-->(m)"))
				.withMessage("Only left-to-right, right-to-left or unidirectional path elements are supported.");
		}

		@Test
		void chain() {
			RelationshipPattern rel = CypherParser.parseRelationship("(n)-->()-->(o)");
			assertThat(Cypher.match(rel).returning(Cypher.asterisk()).build().getCypher())
				.isEqualTo("MATCH (n)-->()-->(o) RETURN *");
		}

		@Test
		void shortestPath() {
			Expression ex = CypherParser.parseExpression("shortestPath((n:A)-->(o:B))");
			assertThat(Cypher.returning(ex).build().getCypher())
				.isEqualTo("RETURN shortestPath((n:`A`)-->(o:`B`))");
		}

		@Test
		void allShortestPaths() {
			Expression ex = CypherParser.parseExpression("allShortestPaths((n:A)-->(o:B))");
			assertThat(Cypher.returning(ex).build().getCypher())
				.isEqualTo("RETURN allShortestPaths((n:`A`)-->(o:`B`))");
		}

		@Test
		void names() {
			RelationshipPattern rel = CypherParser.parseRelationship("(n)-[r1]->()-[r2]->(o)");
			assertThat(Cypher.match(rel).returning(Cypher.asterisk()).build().getCypher())
				.isEqualTo("MATCH (n)-[r1]->()-[r2]->(o) RETURN *");
		}

		@Test
		void properties() {
			RelationshipPattern rel = CypherParser.parseRelationship("(n)-[{a: 'b', c: 'd'}]->(o)");
			assertThat(Cypher.match(rel).returning(Cypher.asterisk()).build().getCypher())
				.isEqualTo("MATCH (n)-[ {a: 'b', c: 'd'}]->(o) RETURN *");
		}

		@Test
		void propertiesOnAChain() {
			RelationshipPattern rel = CypherParser
				.parseRelationship("(n)-[r:TYPE{x:'x'}]->(m)-[{a: 'b', c: 'd'}]->(o)");
			assertThat(Cypher.match(rel).returning(Cypher.asterisk()).build().getCypher())
				.isEqualTo("MATCH (n)-[r:`TYPE` {x: 'x'}]->(m)-[ {a: 'b', c: 'd'}]->(o) RETURN *");
		}
	}

	@Nested
	class Numbers {

		@ParameterizedTest
		@CsvSource({ "1, 1", "-1, -1", "0XF, 15", "0xF, 15", "-0xE, -14", "010, 8", "-010, -8" })
		void shouldParseIntegers(String input, int expected) {
			assertExpression(input, String.format("%d", expected));
		}

		@ParameterizedTest
		@CsvSource({ "1.1, 1.1", "3.14, 3.14", "6.022E23, 6.022E23", "6.022e+24.0, 6.022E24" })
		void shouldParseDoubles(String input, String expected) {
			assertExpression(input, expected);
		}
	}

	@Nested
	class Booleans {

		@ParameterizedTest
		@CsvSource({ "TRUE, true", "true, true", "True, true", "fAlse, false", "FALSE, false" })
		void shouldParseBooleans(String input, String expected) {
			assertExpression(input, expected);
		}
	}

	@Test
	void shouldParseCount() {
		assertExpression("Count(*)", "count(*)");
	}

	@ParameterizedTest
	@CsvSource({
		"+1, +1",
		"+-1, +-1",
		"-1, -1",
		"--1, --1",
		"NOT true, NOT (true)",
		"2+2, (2 + 2)",
		"2-2, (2 - 2)",
		"2*2, (2 * 2)",
		"2/2, (2 / 2)",
		"2%2, (2 % 2)",
		"2^2, 2^2",
		"n.f <> 1, n.f <> 1",
		"n.f != 1, n.f <> 1",
		"n.f = 1, n.f = 1",
		"n.f <= 1, n.f <= 1",
		"n.f >= 1, n.f >= 1",
		"n.f < 1, n.f < 1",
		"n.f > 1, n.f > 1",
		"n.f =~ '.*', n.f =~ '.*'",
		"n.f ends with \"blah\", n.f ENDS WITH 'blah'",
		"n.f starts with 'blah', n.f STARTS WITH 'blah'",
		"n.f contains 'blah', n.f CONTAINS 'blah'",
		"n.f is NULL, n.f IS NULL"
	})
	void shouldParseOperatorsAndConditions(String input, String expected) {
		assertExpression(input, expected);
	}

	@Test
	void shouldParseIn() {
		assertExpression("n in [1,2,3]", "n IN [1, 2, 3]");
	}

	@ParameterizedTest
	@CsvSource(value = {
		"f()| f()",
		"foo.bar()| foo.bar()",
		"foo.bar(e)| foo.bar(e)",
		"foo.bar(e,f)| foo.bar(e, f)",
		"count(distinct e,f)| count(DISTINCT e, f)"
	}, delimiterString = "|")
	void shouldParseFunctionInvocation(String input, String expected) {
		assertExpression(input, expected);
	}

	@Nested
	class Literals {

		@Test
		void shouldParseListLiteral() {
			assertExpression("[1,2,a, 'b']", "[1, 2, a, 'b']");
		}

		@Test
		void shouldParseLookups() {
			assertExpression("n[23]", "n[23]");
		}
	}

	@Nested
	class Parameters {

		@Test
		void newParameterShouldWork() {
			assertExpression("$foo", "$foo");
		}

		@Test
		void newNumberedParameterShouldWork() {
			assertExpression("$1", "$1");
		}

		@Test
		void oldParametersShouldWork() {
			assertExpression("{foo}", "$foo");
		}
	}

	static void assertExpression(String expression) {
		assertExpression(expression, expression);
	}

	static void assertExpression(String expression, String expected) {

		Expression e = CypherParser.parseExpression(expression);
		assertThat(Cypher.returning(e).build().getCypher())
			.isEqualTo(String.format("RETURN %s", expected));
	}

	static void assertNode(Node node, String cypherDslRepresentation) {

		assertThat(Cypher.match(node).returning(Cypher.asterisk()).build().getCypher())
			.isEqualTo(String.format("MATCH %s RETURN *", cypherDslRepresentation));
	}

	@Nested
	class Predicates {

		@Test
		void any() {

			assertThatIllegalArgumentException()
				.isThrownBy(() -> CypherParser.parseExpression("any(color IN n.liked_colors)"))
				.withMessage("any(...) requires a WHERE predicate");
			assertExpression("any(color IN n.liked_colors WHERE color = 'yellow')");
		}

		@Test
		void none() {

			assertThatIllegalArgumentException()
				.isThrownBy(() -> CypherParser.parseExpression("none(color IN n.liked_colors)"))
				.withMessage("none(...) requires a WHERE predicate");
			assertExpression("none(color IN n.liked_colors WHERE color = 'yellow')");
		}

		@Test
		void single() {

			assertThatIllegalArgumentException()
				.isThrownBy(() -> CypherParser.parseExpression("single(color IN n.liked_colors)"))
				.withMessage("single(...) requires a WHERE predicate");
			assertExpression("single(color IN n.liked_colors WHERE color = 'yellow')");
		}

		@Test
		void all() {

			assertThatIllegalArgumentException()
				.isThrownBy(() -> CypherParser.parseExpression("all(color IN n.liked_colors)"))
				.withMessage("all(...) requires a WHERE predicate");
			assertExpression("all(color IN n.liked_colors WHERE color = 'yellow')");
		}
	}
}