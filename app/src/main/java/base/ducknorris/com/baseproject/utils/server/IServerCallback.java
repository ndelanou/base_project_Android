package base.ducknorris.com.baseproject.utils.server;


import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by ndelanou on 17/05/2017.
 */

public interface IServerCallback {
    void onFailure(ServerException e);

    void onResponse(Collection<JSONObject> jsonObjects);
}
