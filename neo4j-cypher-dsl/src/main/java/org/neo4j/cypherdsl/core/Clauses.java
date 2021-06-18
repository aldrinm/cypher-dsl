/*
 * Copyright (c) 2019-2021 "Neo4j,"
 * Neo4j Sweden AB [https://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.cypherdsl.core;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import java.util.List;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builder / factory for various {@link Clause clauses}. It's mostly useful for building a Cypher-DSL AST
 * outside of the fluent API.
 *
 * @author Michael J. Simons
 * @since TBA
 */
@API(status = EXPERIMENTAL, since = "TBA")
public final class Clauses {

	/**
	 * Builds a {@code MATCH} clause.
	 *
	 * @param optional        Should this be an optional match?
	 * @param patternElements The pattern elements to match
	 * @param optionalWhere   An optional where sub-clause
	 * @param optionalHints   Optional hints to be used
	 * @return an immutable match clause
	 */
	@NotNull
	public static Clause match(boolean optional, List<PatternElement> patternElements,
		@Nullable Expression optionalWhere,
		@Nullable List<Hint> optionalHints) {

		return new Match(optional, new Pattern(patternElements),
			optionalWhere == null ? null : new Where(optionalWhere.asCondition()), optionalHints);
	}

	/**
	 * Builds a {@code DELETE} clause.
	 *
	 * @param detach      Should this be an detach delete?
	 * @param expressions The expressions pointing to the things to be deleted
	 * @return an immutable delete clause
	 */
	@NotNull
	public static Clause delete(boolean detach, List<Expression> expressions) {

		return new Delete(new ExpressionList(expressions), detach);
	}

	/**
	 * Builds a {@code RETURN} clause.
	 *
	 * @param distinct          Should this be a distinct return
	 * @param expressions       the expressions to be returned
	 * @param optionalSortItems an optional list of sort items
	 * @param optionalSkip      an optional {@link NumberLiteral} of how many items to skip
	 * @param optionalLimit     an optional {@link NumberLiteral} of how many items to be returned
	 * @return an immutable return clause
	 */
	@NotNull
	public static Clause returning(boolean distinct, List<Expression> expressions,
		@Nullable List<SortItem> optionalSortItems,
		@Nullable Expression optionalSkip, @Nullable Expression optionalLimit) {

		DefaultStatementBuilder.OrderBuilder orderBuilder = new DefaultStatementBuilder.OrderBuilder();
		orderBuilder.orderBy(optionalSortItems);
		orderBuilder.skip(optionalSkip);
		orderBuilder.limit(optionalLimit);
		return Return.create(false, distinct, expressions, orderBuilder);
	}

	/**
	 * Builds a {@code CREATE} clause.
	 *
	 * @param patternElements The pattern elements to create
	 * @return an immutable create clause
	 */
	@NotNull
	public static Clause create(List<PatternElement> patternElements) {

		return new Create(new Pattern(patternElements));
	}

	/**
	 * Builds a {@code MERGE} clause.
	 *
	 * @param patternElements The pattern elements to merge
	 * @return an immutable merge clause
	 */
	@NotNull
	public static Clause merge(List<PatternElement> patternElements) {

		return new Merge(new Pattern(patternElements));
	}

	/**
	 * Not to be instantiated.
	 */
	private Clauses() {
	}
}
