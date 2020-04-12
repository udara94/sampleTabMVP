package com.example.sample.model.entities.response;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Parcel
public class BaseServerResponse {
    private int status_code;
    private boolean success;
    private boolean isAPIError;
    private boolean status;
    public String message;
    private boolean isTokenExpired;
    private String error_description;
    private String error;
}
