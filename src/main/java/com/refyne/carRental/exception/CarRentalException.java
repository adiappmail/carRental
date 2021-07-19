package com.refyne.carRental.exception;

public class CarRentalException extends RuntimeException {


    public CarRentalException() {
        super();
    }

    public CarRentalException(String message) {
        super(message);
    }

    public CarRentalException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarRentalException(Throwable cause) {
        super(cause);
    }
}
