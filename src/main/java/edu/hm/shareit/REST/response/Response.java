package edu.hm.shareit.REST.response;

import edu.hm.shareit.mediaService.MediaServiceResult;

/**
 * Created by aykut on 19.04.2017.
 */
public class Response {
    private final MediaServiceResult msr;
    public Response(MediaServiceResult msr){
        this.msr = msr;
    }

    public String getStatus() {
        //TODO
        return "To be implemented";
    }
}