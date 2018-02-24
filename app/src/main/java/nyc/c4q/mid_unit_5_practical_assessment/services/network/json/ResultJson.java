package nyc.c4q.mid_unit_5_practical_assessment.services.network.json;

import java.util.List;

/**
 * Models the top level result list provided from https://randomuser.me
 * <p>
 * Created by charlie on 1/18/18.
 */

public class ResultJson {
    private final List<UserJson> results;

    public ResultJson(List<UserJson> results) {
        this.results = results;
    }

    public List<UserJson> getResults() {
        return results;
    }
}
