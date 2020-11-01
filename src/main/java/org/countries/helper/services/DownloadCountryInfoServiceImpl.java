package org.countries.helper.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class DownloadCountryInfoServiceImpl implements DownloadCountryInfoService{
    private final String RESPONSE_HEADER_NAME = "Content-Disposition";
    private final String RESPONSE_HEADER_VALUE = "attachment; file=";
    private final String RESPONSE_CONTENT_TYPE = "text/csv";


    @Override
    public HttpServletResponse prepareResponseAttributes( HttpServletResponse response, String fileName) {
        response.setContentType(getResponseContentType());
        response.setHeader(getResponseHeaderName(), getResponseHeaderValue(fileName));
        return response;
    }

    private String getResponseHeaderName(){
        return RESPONSE_HEADER_NAME;
    }

    private String getResponseHeaderValue( String csvFileName){
        return RESPONSE_HEADER_VALUE.concat(csvFileName);
    }

    private String getResponseContentType(){
        return RESPONSE_CONTENT_TYPE;
    }

}
