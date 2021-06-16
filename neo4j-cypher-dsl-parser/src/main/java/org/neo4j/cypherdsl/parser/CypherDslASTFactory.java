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

import static org.apiguardian.api.API.Status.INTERNAL;

import scala.util.Either;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apiguardian.api.API;
import org.neo4j.cypher.internal.ast.factory.ASTFactory;
import org.neo4j.cypher.internal.ast.factory.ASTFactory.NULL;
import org.neo4j.cypher.internal.ast.factory.ParameterType;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.ExposesPatternLengthAccessors;
import org.neo4j.cypherdsl.core.ExposesProperties;
import org.neo4j.cypherdsl.core.ExposesRelationships;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.FunctionInvocation;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Literal;
import org.neo4j.cypherdsl.core.MapExpression;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Operation;
import org.neo4j.cypherdsl.core.Operations;
import org.neo4j.cypherdsl.core.Parameter;
import org.neo4j.cypherdsl.core.PatternElement;
import org.neo4j.cypherdsl.core.Predicates;
import org.neo4j.cypherdsl.core.Property;
import org.neo4j.cypherdsl.core.Relationship;
import org.neo4j.cypherdsl.core.RelationshipChain;
import org.neo4j.cypherdsl.core.RelationshipPattern;
import org.neo4j.cypherdsl.core.SymbolicName;
import org.neo4j.cypherdsl.core.utils.Assertions;

/**
 * An implementation of Neo4j's {@link ASTFactory} that creates Cypher-DSL AST elements that can be used for creating
 * conditions, patterns to match etc.
 *
 * @author Michael J. Simons
 * @since TBA
 */
@API(status = INTERNAL, since = "TBA")
enum CypherDslASTFactory
	implements ASTFactory<NULL, NULL, Object, NULL, NULL, NULL, PatternElement, Node, PathDetails, PathLength, NULL, Operation, NULL, NULL, NULL, Expression, Parameter<?>, SymbolicName, Property, NULL, NULL, NULL, NULL, NULL, NULL, InputPosition> {

	INSTANCE;

	@Override
	public NULL newSingleQuery(List<Object> objects) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL newUnion(InputPosition p, NULL lhs, NULL rhs, boolean all) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL periodicCommitQuery(InputPosition p, String batchSize, Object loadCsv, List<Object> queryBody) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL useClause(InputPosition p, Expression e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL newReturnClause(InputPosition p, boolean distinct, boolean returnAll, List<NULL> nulls,
		List<NULL> order,
		Expression skip, Expression limit) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL newReturnItem(InputPosition p, Expression e, SymbolicName v) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL newReturnItem(InputPosition p, Expression e, int eStartOffset, int eEndOffset) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL orderDesc(Expression e) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL orderAsc(Expression e) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL withClause(InputPosition p, NULL aNull, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object matchClause(InputPosition p, boolean optional, List<PatternElement> patternElements, List<NULL> nulls,
		Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL usingIndexHint(InputPosition p, SymbolicName v, String label, List<String> properties,
		boolean seekOnly) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL usingJoin(InputPosition p, List<SymbolicName> joinVariables) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL usingScan(InputPosition p, SymbolicName v, String label) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL createClause(InputPosition p, List<PatternElement> patternElements) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL setClause(InputPosition p, List<Operation> nulls) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Operation setProperty(Property property, Expression value) {
		throw new UnsupportedOperationException();
	}

	@Override public Operation setVariable(SymbolicName symbolicName, Expression value) {
		throw new UnsupportedOperationException();
	}

	@Override public Operation addAndSetVariable(SymbolicName symbolicName, Expression value) {
		throw new UnsupportedOperationException();
	}

	@Override public Operation setLabels(SymbolicName symbolicName, List<StringPos<InputPosition>> value) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL removeClause(InputPosition p, List<NULL> nulls) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL removeProperty(Property property) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL removeLabels(SymbolicName symbolicName, List<StringPos<InputPosition>> labels) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL deleteClause(InputPosition p, boolean detach, List<Expression> expressions) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL unwindClause(InputPosition p, Expression e, SymbolicName v) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL mergeClause(InputPosition p, PatternElement patternElement, List<NULL> nulls,
		List<MergeActionType> actionTypes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object callClause(InputPosition p, List<String> namespace, String name, List<Expression> arguments,
		boolean yieldAll, List<NULL> nulls, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL callResultItem(InputPosition p, String name, SymbolicName v) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PatternElement namedPattern(SymbolicName v, PatternElement patternElement) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PatternElement shortestPathPattern(InputPosition p, PatternElement patternElement) {

		Assertions.isInstanceOf(RelationshipPattern.class, patternElement,
			"Only relationship patterns are supported for the shortestPath function.");

		return new ExpressionAsPatternElementWrapper(
			FunctionInvocation.create(PatternElementFunctions.SHORTEST_PATH, patternElement));
	}

	@Override public PatternElement allShortestPathsPattern(InputPosition p, PatternElement patternElement) {

		Assertions.isInstanceOf(RelationshipPattern.class, patternElement,
			"Only relationship patterns are supported for the allShortestPaths function.");

		return new ExpressionAsPatternElementWrapper(
			FunctionInvocation.create(PatternElementFunctions.ALL_SHORTEST_PATHS, patternElement));
	}

	@Override
	public PatternElement everyPathPattern(List<Node> nodes, List<PathDetails> relationships) {

		if (nodes.isEmpty() || relationships.isEmpty()) {
			throw new IllegalStateException(
				"Cannot create a PatternElement from an empty list of nodes or path details.");
		}

		if (nodes.size() != relationships.size() + 1) {
			throw new IllegalStateException(
				"Something weird has happened. Got " + nodes.size() + " nodes and " + relationships.size()
				+ " path details.");
		}

		ExposesRelationships<?> relationshipPattern = nodes.get(0);
		for (int i = 1; i < nodes.size(); i++) {
			PathDetails pathDetails = relationships.get(i - 1);
			PathLength length = pathDetails.getLength();

			switch (pathDetails.getDirection()) {
				case LTR:
					relationshipPattern = relationshipPattern.relationshipTo(nodes.get(i), pathDetails.getTypes());
					break;
				case RTL:
					relationshipPattern = relationshipPattern.relationshipFrom(nodes.get(i), pathDetails.getTypes());
					break;
				case UNI:
					relationshipPattern = relationshipPattern.relationshipBetween(nodes.get(i), pathDetails.getTypes());
					break;
				default:
					throw new IllegalStateException("Unknown direction type: " + pathDetails.getDirection());
			}

			if (pathDetails.getName() != null) {
				if (relationshipPattern instanceof Relationship) {
					relationshipPattern = ((Relationship) relationshipPattern).named(pathDetails.getName());
				} else {
					relationshipPattern = ((RelationshipChain) relationshipPattern).named(pathDetails.getName());
				}
			}

			if (pathDetails.getProperties() != null) {
				if (relationshipPattern instanceof ExposesProperties) {
					relationshipPattern = (ExposesRelationships<?>) ((ExposesProperties<?>) relationshipPattern)
						.withProperties(pathDetails.getProperties());
				} else {
					relationshipPattern = ((RelationshipChain) relationshipPattern)
						.properties(pathDetails.getProperties());
				}
			}

			if (length != null) {
				if (length.isUnbounded()) {
					relationshipPattern = ((ExposesPatternLengthAccessors<?>) relationshipPattern)
						.unbounded();
				} else {
					relationshipPattern = ((ExposesPatternLengthAccessors<?>) relationshipPattern)
						.length(length.getMinimum(), length.getMaximum());
				}
			}
		}

		return (PatternElement) relationshipPattern;
	}

	@Override
	public Node nodePattern(InputPosition p, SymbolicName v, List<StringPos<InputPosition>> labels,
		Expression properties) {
		Node node;
		if (labels.isEmpty()) {
			node = Cypher.anyNode();
		} else {
			var primaryLabel = labels.get(0).string;
			var additionalLabels = labels.stream().skip(1)
				.map(sp -> sp.string).collect(Collectors.toList());
			node = Cypher.node(primaryLabel, additionalLabels);
		}

		if (v != null) {
			node = node.named(v);
		}
		if (properties != null) {
			node = node.withProperties((MapExpression) properties);
		}
		return node;
	}

	@Override
	public PathDetails relationshipPattern(InputPosition p, boolean left, boolean right, SymbolicName v,
		List<StringPos<InputPosition>> relTypes,
		PathLength pathLength, Expression properties, boolean legacyTypeSeparator) {

		return PathDetails.of(v, pathLength, left, right, relTypes, (MapExpression) properties);
	}

	@Override
	public PathLength pathLength(InputPosition p, InputPosition pMin, InputPosition pMax, String minLength, String maxLength) {
		return PathLength.of(minLength, maxLength);
	}

	@Override
	public NULL loadCsvClause(InputPosition p, boolean headers, Expression source, SymbolicName v,
		String fieldTerminator) {
		throw new UnsupportedOperationException();
	}

	@Override public Object foreachClause(InputPosition p, SymbolicName v, Expression list, List<Object> objects) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL subqueryClause(InputPosition p, NULL subquery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL yieldClause(InputPosition p, boolean returnAll, List<NULL> nulls, List<NULL> orderBy, Expression skip,
		Expression limit, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object showIndexClause(InputPosition p, String indexType, boolean brief, boolean verbose, Expression where,
		boolean hasYield) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object showConstraintClause(InputPosition p, String constraintType, boolean brief, boolean verbose,
		Expression where, boolean hasYield) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object showProcedureClause(InputPosition p, boolean currentUser, String user, Expression where,
		boolean hasYield) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object showFunctionClause(InputPosition p, String functionType, boolean currentUser, String user,
		Expression where, boolean hasYield) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL useGraph(NULL aNull, NULL aNull2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL hasCatalog(NULL aNull) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL createRole(InputPosition p, boolean replace, Either<String, Parameter<?>> roleName, Either<String, Parameter<?>> fromRole, boolean ifNotExists) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL dropRole(InputPosition p, Either<String, Parameter<?>> roleName, boolean ifExists) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL renameRole(InputPosition p, Either<String, Parameter<?>> fromRoleName,
		Either<String, Parameter<?>> toRoleName, boolean ifExists) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL showRoles(InputPosition p, boolean withUsers, boolean showAll, NULL yieldExpr, NULL returnWithoutGraph, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL grantRoles(InputPosition p, List<Either<String, Parameter<?>>> roles,
		List<Either<String, Parameter<?>>> users) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL revokeRoles(InputPosition p, List<Either<String, Parameter<?>>> roles,
		List<Either<String, Parameter<?>>> users) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL createUser(InputPosition p, boolean replace, boolean ifNotExists, Either<String, Parameter<?>> username,
		Expression password, boolean encrypted, boolean changeRequired, Boolean suspended,
		Either<String, Parameter<?>> homeDatabase) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL dropUser(InputPosition p, boolean ifExists, Either<String, Parameter<?>> username) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL renameUser(InputPosition p, Either<String, Parameter<?>> fromUserName,
		Either<String, Parameter<?>> toUserName, boolean ifExists) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL setOwnPassword(InputPosition p, Expression currentPassword, Expression newPassword) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL alterUser(InputPosition p, boolean ifExists, Either<String, Parameter<?>> username, Expression password,
		boolean encrypted, Boolean changeRequired, Boolean suspended, Either<String, Parameter<?>> homeDatabase,
		boolean removeHome) {
		throw new UnsupportedOperationException();
	}

	@Override public Expression passwordExpression(Parameter<?> password) {
		throw new UnsupportedOperationException();
	}

	@Override public Expression passwordExpression(InputPosition p, String password) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL showUsers(InputPosition p, NULL yieldExpr, NULL returnWithoutGraph, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL showCurrentUser(InputPosition p, NULL yieldExpr, NULL returnWithoutGraph, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL createDatabase(InputPosition p, boolean replace, Either<String, Parameter<?>> databaseName,
		boolean ifNotExists, NULL aNull, Either<Map<String, Expression>, Parameter<?>> options) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL dropDatabase(InputPosition p, Either<String, Parameter<?>> databaseName, boolean ifExists,
		boolean dumpData, NULL wait) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL showDatabase(InputPosition p, NULL aNull, NULL yieldExpr, NULL returnWithoutGraph, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL startDatabase(InputPosition p, Either<String, Parameter<?>> databaseName, NULL wait) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL stopDatabase(InputPosition p, Either<String, Parameter<?>> databaseName, NULL wait) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL databaseScope(InputPosition p, Either<String, Parameter<?>> databaseName, boolean isDefault,
		boolean isHome) {
		throw new UnsupportedOperationException();
	}

	@Override public NULL wait(boolean wait, long seconds) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SymbolicName newVariable(InputPosition p, String name) {
		return Cypher.name(name);
	}

	@Override
	public Parameter<?> newParameter(InputPosition p, SymbolicName v, ParameterType type) {
		return Cypher.parameter(v.getValue());
	}

	@Override
	public Parameter<?> newParameter(InputPosition p, String v, ParameterType type) {
		return Cypher.parameter(v);
	}

	@Override
	public Parameter<?> newSensitiveStringParameter(InputPosition p, SymbolicName v) {
		throw new UnsupportedOperationException("The Cypher-DSL does not support sensitive parameters.");
	}

	@Override
	public Parameter<?> newSensitiveStringParameter(InputPosition p, String v) {
		throw new UnsupportedOperationException("The Cypher-DSL does not support sensitive parameters.");
	}

	@Override
	public Expression oldParameter(InputPosition p, SymbolicName v) {
		return Cypher.parameter(v.getValue());
	}

	@Override
	public Expression newDouble(InputPosition p, String image) {
		return Cypher.literalOf(Double.parseDouble(image));
	}

	@Override
	public Expression newDecimalInteger(InputPosition p, String image, boolean negated) {
		return Cypher.literalOf(Long.parseUnsignedLong(image) * (negated ? -1 : 1));
	}

	@Override public Expression newHexInteger(InputPosition p, String image, boolean negated) {
		return Cypher.literalOf(Long.parseUnsignedLong(image.replaceFirst("(?i)0x", ""), 16) * (negated ? -1 : 1));
	}

	@Override public Expression newOctalInteger(InputPosition p, String image, boolean negated) {
		return Cypher.literalOf(Long.parseUnsignedLong(image, 8) * (negated ? -1 : 1));
	}

	@Override
	public Literal<String> newString(InputPosition p, String image) {
		return Cypher.literalOf(image);
	}

	@Override
	public Expression newTrueLiteral(InputPosition p) {
		return Cypher.literalTrue();
	}

	@Override
	public Expression newFalseLiteral(InputPosition p) {
		return Cypher.literalFalse();
	}

	@Override
	public Expression newNullLiteral(InputPosition p) {
		return Cypher.literalOf(null);
	}

	@Override
	public Expression listLiteral(InputPosition p, List<Expression> values) {
		return Cypher.listOf(values.toArray(new Expression[0]));
	}

	@Override
	public MapExpression mapLiteral(InputPosition p, List<StringPos<InputPosition>> keys, List<Expression> values) {
		Object[] keysAndValues = new Object[keys.size() * 2];
		int i = 0;
		Iterator<Expression> valueIterator = values.iterator();
		for (StringPos<InputPosition> key : keys) {
			keysAndValues[i++] = key.string;
			keysAndValues[i++] = valueIterator.next();
		}
		return Cypher.mapOf(keysAndValues);
	}

	@Override
	public Expression hasLabelsOrTypes(Expression subject, List<StringPos<InputPosition>> labels) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Property property(Expression subject, StringPos<InputPosition> propertyKeyName) {
		return subject.property(propertyKeyName.string);
	}

	@Override
	public Expression or(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.asCondition().or(rhs.asCondition());
	}

	@Override
	public Expression xor(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.asCondition().xor(rhs.asCondition());
	}

	@Override
	public Expression and(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.asCondition().and(rhs.asCondition());
	}

	@Override
	public Expression ands(List<Expression> exprs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Expression not(Expression e) {
		return e.asCondition().not();
	}

	@Override
	public Expression plus(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.add(rhs);
	}

	@Override
	public Expression minus(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.subtract(rhs);
	}

	@Override
	public Expression multiply(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.multiply(rhs);
	}

	@Override
	public Expression divide(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.divide(rhs);
	}

	@Override
	public Expression modulo(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.remainder(rhs);
	}

	@Override
	public Expression pow(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.pow(rhs);
	}

	@Override public Expression unaryPlus(Expression e) {
		return Operations.plus(e);
	}

	@Override
	public Expression unaryMinus(Expression e) {
		return Operations.minus(e);
	}

	@Override
	public Expression eq(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.eq(rhs);
	}

	@Override
	public Expression neq(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.ne(rhs);
	}

	@Override
	public Expression neq2(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.ne(rhs);
	}

	@Override
	public Expression lte(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.lte(rhs);
	}

	@Override
	public Expression gte(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.gte(rhs);
	}

	@Override
	public Expression lt(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.lt(rhs);
	}

	@Override
	public Expression gt(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.gt(rhs);
	}

	@Override
	public Expression regeq(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.matches(rhs);
	}

	@Override
	public Expression startsWith(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.startsWith(rhs);
	}

	@Override
	public Expression endsWith(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.endsWith(rhs);
	}

	@Override
	public Expression contains(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.contains(rhs);
	}

	@Override
	public Expression in(InputPosition p, Expression lhs, Expression rhs) {
		return lhs.in(rhs);
	}

	@Override
	public Expression isNull(Expression e) {
		return e.isNull();
	}

	@Override
	public Expression listLookup(Expression list, Expression index) {
		return Cypher.valueAt(list, index);
	}

	@Override
	public Expression listSlice(InputPosition p, Expression list, Expression start, Expression end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Expression newCountStar(InputPosition p) {
		return Functions.count(Cypher.asterisk());
	}

	@Override
	public Expression functionInvocation(InputPosition p, List<String> namespace, String name, boolean distinct,
		List<Expression> arguments) {

		String[] parts = new String[namespace.size() + 1];
		for (int i = 0; i < namespace.size(); i++) {
			parts[i] = namespace.get(i);
		}
		parts[parts.length - 1] = name;
		return Cypher.call(parts).withArgs(arguments.toArray(Expression[]::new)).asFunction(distinct);
	}

	@Override
	public Expression listComprehension(InputPosition p, SymbolicName v, Expression list, Expression where,
		Expression projection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Expression patternComprehension(InputPosition p, SymbolicName v, PatternElement patternElement,
		Expression where,
		Expression projection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Expression filterExpression(InputPosition p, SymbolicName v, Expression list, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Expression extractExpression(InputPosition p, SymbolicName v, Expression list, Expression where,
		Expression projection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Expression reduceExpression(InputPosition p, SymbolicName acc, Expression accExpr, SymbolicName v,
		Expression list,
		Expression innerExpr) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Expression allExpression(InputPosition p, SymbolicName v, Expression list, Expression where) {

		Assertions.notNull(where, "all(...) requires a WHERE predicate");
		return Predicates.all(v).in(list).where(where.asCondition());
	}

	@Override
	public Expression anyExpression(InputPosition p, SymbolicName v, Expression list, Expression where) {

		Assertions.notNull(where, "any(...) requires a WHERE predicate");
		return Predicates.any(v).in(list).where(where.asCondition());
	}

	@Override
	public Expression noneExpression(InputPosition p, SymbolicName v, Expression list, Expression where) {

		Assertions.notNull(where, "none(...) requires a WHERE predicate");
		return Predicates.none(v).in(list).where(where.asCondition());
	}

	@Override
	public Expression singleExpression(InputPosition p, SymbolicName v, Expression list, Expression where) {

		Assertions.notNull(where, "single(...) requires a WHERE predicate");
		return Predicates.single(v).in(list).where(where.asCondition());
	}

	@Override
	public Expression patternExpression(InputPosition p, PatternElement patternElement) {

		if (patternElement instanceof ExpressionAsPatternElementWrapper) {
			return ((ExpressionAsPatternElementWrapper) patternElement).getExpression();
		}

		throw new UnsupportedOperationException();
	}

	@Override
	public Expression existsSubQuery(InputPosition p, List<PatternElement> patternElements, Expression where) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Expression mapProjection(InputPosition p, SymbolicName v, List<NULL> nulls) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL mapProjectionLiteralEntry(StringPos<InputPosition> property, Expression value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL mapProjectionProperty(StringPos<InputPosition> property) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL mapProjectionVariable(SymbolicName v) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NULL mapProjectionAll(InputPosition p) {
		throw new UnsupportedOperationException();
	}

	@Override
	public
	Expression caseExpression(InputPosition p, Expression e, List<Expression> whens, List<Expression> thens,
		Expression elze) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputPosition inputPosition(int offset, int line, int column) {
		return new InputPosition(offset, line, column);
	}
}
