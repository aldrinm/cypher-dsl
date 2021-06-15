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

import java.util.List;

import org.neo4j.cypher.internal.ast.factory.ASTFactory;
import org.neo4j.cypherdsl.core.Relationship.Direction;

/**
 * A value object for the details of a path.
 *
 * @author Michael J. Simons
 * @soundtrack Pink Floyd - The Division Bell
 * @since TBA
 */
final class PathDetails {

	static PathDetails of(PathLength length, boolean left, boolean right,
		List<ASTFactory.StringPos<InputPosition>> relTypes) {

		if (left && right) {
			throw new IllegalArgumentException("Only left-to-right, right-to-left or unidirectional path elements are supported.");
		}

		Direction direction;
		if (left) {
			direction = Direction.RTL;
		} else if (right) {
			direction = Direction.LTR;
		} else {
			direction = Direction.UNI;
		}

		return new PathDetails(length, direction, relTypes.stream().map(sp -> sp.string).toArray(String[]::new));
	}

	private final PathLength length;

	private final Direction direction;

	private final String[] types;

	private PathDetails(PathLength length, Direction direction, String[] types) {
		this.length = length;
		this.direction = direction;
		this.types = types;
	}

	public PathLength getLength() {
		return length;
	}

	public Direction getDirection() {
		return direction;
	}

	public String[] getTypes() {
		return types;
	}
}
