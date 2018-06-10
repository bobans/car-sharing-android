package rs.elfak.bobans.carsharing.models;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class FirebaseMessageData {

    public enum MessageType {
        DRIVE_REQUESTED,
        DRIVE_REQUEST_CANCELED,
        NEW_DRIVE
    }

    private MessageType type;
    private Object payload;

    public FirebaseMessageData() {
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}
