package com.ducknorris.baseproject.utils.server;

/**
 * Created by ndelanou on 17/05/2017.
 */

public class ServerException extends Throwable {
    public ServerException(String cause) {
        super(cause);
    }

    public ServerException(Exception e) {
        super(e);
    }
}