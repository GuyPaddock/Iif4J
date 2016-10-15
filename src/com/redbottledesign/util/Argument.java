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
import java.util.Objects;
import java.util.function.Function;
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
     * Ensures that all of the provided argument values are either null or all
     * have a non-null value.
     *
     * <p>This is useful for a group of arguments that are optional, but can
     * only take effect if all are specified.</p>
     *
     * <p>If at least one value is set, but not all of the values are set,
     * an {@link IllegalArgumentException} is raised with the provided error
     * message.</p>
     *
     * @param   errorMessage
     *          The error message to include in an exception, if one of the
     *          values is {@code null} but the rest are not.
     *
     * @param   values
     *          The values to check.
     *
     * @throws  IllegalArgumentException
     *          If one of the values is {@code null} but the rest are not.
     */
    public static void ensureAllOrNoneNull(String errorMessage, Object... values) {
        Integer positionOfFirstNull = null,
                positionOfFirstSet  = null;

        for (int index = 0; index < values.length; ++index) {
            Object value = values[index];

            if ((value == null) && (positionOfFirstNull == null)) {
                positionOfFirstNull = index;
            }

            if ((value != null) && (positionOfFirstSet == null)) {
                positionOfFirstSet = index;
            }
        }

        if ((positionOfFirstSet != null) && (positionOfFirstNull != null)) {
            throw new IllegalArgumentException(
                String.format(
                    "%s (argument %d is `null`).",
                    errorMessage,
                    positionOfFirstNull + 1));
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
                String.format(
                    "The %s can only be set once (already set to `%s`; was trying to set to `%s`).",
                    name,
                    currentValue,
                    newValue));
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
    public static void ensureUnset(double currentValue, double newValue, String name)
    throws IllegalStateException {
        if ((currentValue != 0) && (currentValue != newValue)) {
            throw new IllegalStateException(
                String.format(
                    "The %s can only be set once (already set to `%s`; was trying to set to `%s`).",
                    name,
                    currentValue,
                    newValue));
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
    public static void ensureUnset(float currentValue, float newValue, String name)
    throws IllegalStateException {
        if ((currentValue != 0) && (currentValue != newValue)) {
            throw new IllegalStateException(
                String.format(
                    "The %s can only be set once (already set to `%s`; was trying to set to `%s`).",
                    name,
                    currentValue,
                    newValue));
        }
    }

    /**
     * Ensures that either the current value is not yet set, an empty
     * collection, or the same collection as the provided new value.
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
                String.format(
                    "The %s can only be set when empty (it currently contains `%d` elements).",
                    name,
                    currentValue.size()));
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
        ensureUnset(currentValue, newValue, Objects::isNull, name);
    }

    /**
     * Ensures that either the current value is empty, as defined by the
     * provided function, or that it matches the provided new value.
     *
     * @param   currentValue
     *          The current value of the field.
     *
     * @param   newValue
     *          The proposed new value for the field.
     *
     * @param   emptyFunc
     *          The function used to test whether the current value is empty.
     *
     * @param   name
     *          The human-friendly name for the field (for error messages).
     *
     * @throws  IllegalStateException
     *          If the field is already set to a different value.
     */
    public static <T> void ensureUnset(T currentValue, T newValue, Function<T, Boolean> emptyFunc,
                                       String name)
    throws IllegalStateException {
        if (!emptyFunc.apply(currentValue) && !currentValue.equals(newValue)) {
            throw new IllegalStateException(
                String.format(
                    "The %s can only be set once (already set to `%s`; was trying to set to `%s`).",
                    name,
                    currentValue,
                    newValue));
        }
    }
}
