/*
 * Copyright (C) 2016 Red Bottle Design, LLC
 *
 * This program is free software: you can redistribute it and/or modify
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
package com.redbottledesign.util;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Common methods for checking and validating method arguments.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public abstract class Argument {
    /**
     * Checks if the provided objects are all non-null.
     *
     * @param   objects
     *          The objects to check for nullity.
     *
     * @return  {@code true} if all objects are not null; Or, {@code false},
     *          otherwise.
     */
    public static boolean allNonNull(Object... objects) {
        return (Stream.of(objects).allMatch(object -> object != null));
    }

    /**
     * Ensures that the provided value is within the specified range of values, inclusively.
     *
     * @param   value
     *          The value of the argument being checked.
     *
     * @param   min
     *          The minimum inclusive value for the argument.
     *
     * @param   max
     *          The maximum inclusive value for the argument.
     *
     * @param   name
     *          The human-friendly name for the argument (for error messages).
     */
    public static void ensureInRange(double value, double min, double max, String name) {
        if ((value < min) || (value > max)) {
            throw new IllegalArgumentException(
                String.format(
                    "%s must be between %f and %f, inclusive (was given `%f`).",
                    name, min, max, value));
        }
    }

    /**
     * Ensures that the provided argument is not null.
     *
     * @param   value
     *          The value of the argument being checked.
     *
     * @param   name
     *          The human-friendly name for the argument (for error messages).
     *
     * @throws  IllegalArgumentException
     *          If {@code value} is {@code null}.
     */
    public static void ensureNotNull(Object value, String name)
    throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }

    /**
     * Ensures that either the current value is not yet set, or that it matches
     * the provided new value.
     *
     * @param   currentValue
     *          The current value of the field.
     *
     * @param   newValue
     *          The proposed new value for the field.
     *
     * @param   name
     *          The human-friendly name for the field (for error messages).
     *
     * @throws  IllegalStateException
     *          If the field is already set to a different value.
     */
    public static void ensureUnset(int currentValue, int newValue, String name)
    throws IllegalStateException {
        if ((currentValue != 0) && (currentValue != newValue)) {
            throw new IllegalStateException(
                "The " + name + " can only be set once (already set to `" + currentValue + "`.");
        }
    }

    /**
     * Ensures that either the current value is not yet set, or that it matches
     * the provided new value.
     *
     * @param   currentValue
     *          The current value of the field.
     *
     * @param   newValue
     *          The proposed new value for the field.
     *
     * @param   name
     *          The human-friendly name for the field (for error messages).
     *
     * @throws  IllegalStateException
     *          If the field is already set to a different value.
     */
    public static void ensureUnset(Collection<?> currentValue, Collection<?> newValue, String name)
    throws IllegalStateException {
        if ((currentValue != null) && !currentValue.isEmpty() && !currentValue.equals(newValue)) {
            throw new IllegalStateException(
                "The " + name + " can only be set when empty (it currently contains `" +
                currentValue.size() + "` elements.");
        }
    }

    /**
     * Ensures that either the current value is not yet set, or that it matches
     * the provided new value.
     *
     * @param   currentValue
     *          The current value of the field.
     *
     * @param   newValue
     *          The proposed new value for the field.
     *
     * @param   name
     *          The human-friendly name for the field (for error messages).
     *
     * @throws  IllegalStateException
     *          If the field is already set to a different value.
     */
    public static void ensureUnset(Object currentValue, Object newValue, String name)
    throws IllegalStateException {
        if ((currentValue != null) && !currentValue.equals(newValue)) {
            throw new IllegalStateException(
                "The " + name + " can only be set once per schedule (already set to `" +
                currentValue + "`.");
        }
    }
}
