package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "awssamplemessenger-mobilehub-45359657-Conversation")

public class ConversationDO {
    private String _userId;
    private String _conversationId;
    private String _chatRoomId;
    private String _createdAt;
    private Set<String> _imageUrlPath;
    private Set<String> _message;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "conversationId")
    @DynamoDBAttribute(attributeName = "conversationId")
    public String getConversationId() {
        return _conversationId;
    }

    public void setConversationId(final String _conversationId) {
        this._conversationId = _conversationId;
    }
    @DynamoDBIndexHashKey(attributeName = "chatRoomId", globalSecondaryIndexName = "ByCreationDate")
    public String getChatRoomId() {
        return _chatRoomId;
    }

    public void setChatRoomId(final String _chatRoomId) {
        this._chatRoomId = _chatRoomId;
    }
    @DynamoDBIndexRangeKey(attributeName = "createdAt", globalSecondaryIndexName = "ByCreationDate")
    public String getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(final String _createdAt) {
        this._createdAt = _createdAt;
    }
    @DynamoDBAttribute(attributeName = "imageUrlPath")
    public Set<String> getImageUrlPath() {
        return _imageUrlPath;
    }

    public void setImageUrlPath(final Set<String> _imageUrlPath) {
        this._imageUrlPath = _imageUrlPath;
    }
    @DynamoDBAttribute(attributeName = "message")
    public Set<String> getMessage() {
        return _message;
    }

    public void setMessage(final Set<String> _message) {
        this._message = _message;
    }

}
