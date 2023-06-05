package il.cshaifasweng.OCSFMediatorExample.server.ocsf;

public class LoggedInClient {
    private ConnectionToClient client;

    //if _class == 0 --> Principal client, 1 --> Teacher client, 2 --> Student client
    private int _class;

    private String _id;

    public LoggedInClient(ConnectionToClient client, int _class, String _id) {
        this.client = client;
        this._class = _class;
        this._id = _id;
    }

    public ConnectionToClient getClient() {
        return client;
    }

    public void setClient(ConnectionToClient client) {
        this.client = client;
    }

    public void set_class(int _class) {
        this._class = _class;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get_class() {
        return _class;
    }

    public String get_id() {
        return _id;
    }
}
