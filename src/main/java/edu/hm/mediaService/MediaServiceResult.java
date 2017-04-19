package edu.hm.mediaService;

/**
 * Representation of a MediaServiceResult
 * Containing information: Status and Code
 */
public enum MediaServiceResult {

    /*
    *  TODO - Create some values
    */
    ;

    private final int code;
    private final Status status;

    private class Status {
        private final String status;
        Status(String status){
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    MediaServiceResult(int code, Status status){
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public Status getStatus() {
        return status;
    }
}
