/*
 * @(#)DownloadCountryInfoService.java
 *
 *
 */

package org.countries.helper.services;
import javax.servlet.http.HttpServletResponse;

public interface DownloadCountryInfoService {
    HttpServletResponse prepareResponseAttributes(HttpServletResponse response, String fileName);
}
