package com.contentstack.cms.core;

public final class ErrorMessages {

    private ErrorMessages() {
        throw new AssertionError("This class cannot be instantiated. Use the static constants to continue.");
    }

    public static final String NOT_LOGGED_IN = "You are not logged in. Login to continue.";
    public static final String ALREADY_LOGGED_IN = "Operation not allowed. You are already logged in.";
    public static final String OAUTH_LOGIN_REQUIRED = "Login or configure OAuth to continue.";
    public static final String OAUTH_NO_TOKENS = "No OAuth tokens available. Please authenticate first.";
    public static final String OAUTH_NO_REFRESH_TOKEN = "No refresh token available";
    public static final String OAUTH_EMPTY_CODE = "Authorization code cannot be null or empty";
    public static final String OAUTH_CONFIG_MISSING = "OAuth is not configured. Use Builder.setOAuth() with or without clientSecret for PKCE flow";
    public static final String OAUTH_REFRESH_FAILED = "Failed to refresh access token";
    public static final String OAUTH_REVOKE_FAILED = "Failed to revoke authorization";
    public static final String OAUTH_STATUS_FAILED = "Failed to get authorization status";
    public static final String OAUTH_ORG_EMPTY = "organizationUid can not be empty";

    public static final String PRIVATE_CONSTRUCTOR = "This class cannot be instantiated. Use the static methods to continue.";

    public static final String ALIAS_UID_REQUIRED = "Alias UID is required. Provide a valid Alias UID and try again.";
    public static final String ASSET_UID_REQUIRED = "Asset UID is required. Provide a valid Asset UID and try again.";
    public static final String LOG_ITEM_UID_REQUIRED = "Log Item UID is required. Provide a valid Log Item UID and try again.";
    public static final String BRANCH_UID_REQUIRED = "Branch UID is required. Provide a valid Branch UID and try again.";
    public static final String CUSTOM_FIELD_UID_REQUIRED = "Custom Field UID is required. Provide a valid Custom Field UID and try again.";
    public static final String DELIVERY_TOKEN_UID_REQUIRED = "Delivery Token UID is required. Provide a valid Delivery Token UID and try again.";
    public static final String ENVIRONMENT_REQUIRED = "Environment is required. Provide a valid Environment and try again.";
    public static final String FOLDER_UID_REQUIRED = "Folder UID is required. Provide a valid Folder UID and try again.";
    public static final String GLOBAL_FIELD_UID_REQUIRED = "Global Field UID is required. Provide a valid Global Field UID and try again.";
    public static final String LABEL_UID_REQUIRED = "Label UID is required. Provide a valid Label UID and try again.";
    public static final String LOCALE_CODE_REQUIRED = "Locale Code is required. Provide a valid Locale Code and try again.";
    public static final String MANAGEMENT_TOKEN_UID_REQUIRED = "Management Token UID is required. Provide a valid Management Token UID and try again.";
    public static final String ORGANIZATION_UID_REQUIRED = "Organization UID is required. Provide a valid Organization UID and try again.";
    public static final String PUBLISH_QUEUE_UID_REQUIRED = "Publish Queue UID is required. Provide a valid Publish Queue UID and try again.";
    public static final String RELEASE_UID_REQUIRED = "Release UID is required. Provide a valid Release UID and try again.";
    public static final String ROLE_UID_REQUIRED = "Role UID is required. Provide a valid Role UID and try again.";
    public static final String VARIANT_GROUP_UID_REQUIRED = "Variant Group UID is required. Provide a valid Variant Group UID and try again.";
    public static final String WEBHOOK_UID_REQUIRED = "Webhook UID is required. Provide a valid Webhook UID and try again.";
    public static final String WORKFLOW_UID_REQUIRED = "Workflow UID is required. Provide a valid Workflow UID and try again.";

    public static final String CONTENT_TYPE_REQUIRED = "Content Type is required. Provide a valid Content Type and try again.";
    public static final String REFERENCE_FIELDS_INVALID = "Reference Fields must be a string or an array of strings. Provide valid values and try again.";
    public static final String TERM_STRING_REQUIRED = "Term String is required. Provide a valid Term String and try again.";

    public static final String FILE_CONTENT_TYPE_UNKNOWN = "The file's content type could not be determined. Provide a valid file and try again.";

    public static final String MISSING_INSTALLATION_ID = "installation uid is required";
    public static final String MISSING_ORG_ID = "organization uid is required";
}
