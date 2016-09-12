package com.redbottledesign.accounting.quickbooks.iif;

/**
 * Common interface for objects that can be exported to IIF.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public interface IifExportable {
    /**
     * Exports this object into an IIF-friendly representation.
     *
     * @return The IIF output to represent this object.
     */
    String toIifString();
}
