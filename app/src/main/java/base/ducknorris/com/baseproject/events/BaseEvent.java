package base.ducknorris.com.baseproject.events;

/**
 * Created by DuckN on 20/05/2017.
 */

public class BaseEvent {
    private String message;

    public BaseEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
