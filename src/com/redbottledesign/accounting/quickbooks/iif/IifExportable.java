package com.redbottledesign.accounting.quickbooks.iif;

/**
 * Common interface for objects that can be exported to IIF.
 *
 * @author Guy Paddock (guy@redbottledesign.com)
 */
public interface IifExportable {
    String toIifString();
}
