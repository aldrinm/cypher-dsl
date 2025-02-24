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
package org.neo4j.cypherdsl.core.internal;

import static org.apiguardian.api.API.Status.INTERNAL;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apiguardian.api.API;
import org.neo4j.cypherdsl.core.ast.Visitable;
import org.neo4j.cypherdsl.core.ast.Visitor;

/**
 * @author Michael J. Simons
 * @soundtrack Apocalyptica - Cell-0
 * @since 2020.0.1
 */
@API(status = INTERNAL, since = "2020.0.1")
public final class ProcedureName implements Visitable {

	private final Namespace optionalNamespace;

	private final String value;

	public static ProcedureName from(String... namespaceAndProcedure) {
		if (namespaceAndProcedure.length == 1) {
			return new ProcedureName(namespaceAndProcedure[0]);
		} else {
			Namespace namespace = new Namespace(Arrays.copyOf(namespaceAndProcedure, namespaceAndProcedure.length - 1));
			return new ProcedureName(namespace, namespaceAndProcedure[namespaceAndProcedure.length - 1]);
		}
	}

	private ProcedureName(String value) {
		this(null, value);
	}

	private ProcedureName(Namespace namespace, String value) {

		this.optionalNamespace = namespace;
		this.value = value;
	}

	public String getQualifiedName() {

		String namespace = "";
		if (this.optionalNamespace != null) {
			namespace = Arrays.stream(this.optionalNamespace.getContent()).collect(Collectors.joining(".", "", "."));
		}
		return namespace + this.value;
	}

	@Override
	public void accept(Visitor visitor) {

		visitor.enter(this);
		Visitable.visitIfNotNull(this.optionalNamespace, visitor);
		visitor.leave(this);
	}

	/**
	 * Use {@link #getQualifiedName()} to retrieve the full name, including the namespace.
	 *
	 * @return The actual name of the procedure, without any namespace.
	 */
	public String getValue() {
		return value;
	}
}
