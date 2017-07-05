package org.ccci.gto.globalreg;

public class NotificationMessage {
    private static String MESSAGE_UPDATED = "updated";
    private static String MESSAGE_CREATED = "created";
    private static String MESSAGE_DELETED = "deleted";

    private String action;
    private String id;
    private String clientIntegrationId;
    private String triggeredBy;
    private String entityType;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientIntegrationId() {
        return clientIntegrationId;
    }

    public void setClientIntegrationId(String clientIntegrationId) {
        this.clientIntegrationId = clientIntegrationId;
    }

    public String getTriggeredBy() {
        return triggeredBy;
    }

    public void setTriggeredBy(String triggeredBy) {
        this.triggeredBy = triggeredBy;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public static boolean isUpsertMessage(final NotificationMessage notificationMessage) {
        String action = notificationMessage.getAction();
        return MESSAGE_CREATED.equals(action) || MESSAGE_UPDATED.equals(action);
    }

    public static boolean isDeleteMessage(final NotificationMessage notificationMessage) {
        return MESSAGE_DELETED.equals(notificationMessage.getAction());
    }
}
