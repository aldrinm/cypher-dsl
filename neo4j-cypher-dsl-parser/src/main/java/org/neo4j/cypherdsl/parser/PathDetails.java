package org.neo4j.cypherdsl.parser;

import org.neo4j.cypherdsl.core.Relationship;
import org.neo4j.cypherdsl.core.Relationship.Direction;

/**
 * A value object for the details of a path.
 *
 * @author Michael J. Simons
 * @soundtrack Pink Floyd - The Division Bell
 * @since TBA
 */
final class PathDetails {

	static PathDetails of(PathLength length, boolean left, boolean right) {

		if(left && right) {
			throw new IllegalArgumentException("Only left-to-right, right-to-left or unidirectional path elements are supported.");
		}

		Direction direction;
		if(left) {
			direction = Direction.RTL;
		} else if(right) {
			direction = Direction.LTR;
		} else {
			direction = Direction.UNI;
		}
		return new PathDetails(length, direction);
	};

	private final PathLength length;

	private final Direction direction;

	private PathDetails(PathLength length, Direction direction) {
		this.length = length;
		this.direction = direction;
	}

}
