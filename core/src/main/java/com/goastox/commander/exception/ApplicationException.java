package com.goastox.commander.exception;

public class ApplicationException extends RuntimeException{

    public enum Code{
        INVALID_INPUT(400),
        WARNING_INPUT(401),
        INTERNAL_ERROR(500),
        NOT_FOUND(404),
        CONFLICT(409),
        UNAUTHORIZED(403),
        BACKEND_ERROR(500);

        private int status;

        Code(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }
    private final Code code;

    public ApplicationException(Code code, String message) {
        super(message);
        this.code = code;
    }
}
