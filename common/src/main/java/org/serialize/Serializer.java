package org.serialize;
/**
 * Abstract class to standardized serialization and deserialization of data
 * */
public abstract class Serializer<T> {
    /**
     * Serialization method to serialize each class
     * @param object object to serialize
     * @return Serialized string returned
     * @throws IllegalArgumentException if the object passed is not the one required by the class implementation
     */
    public abstract String serialize(T object) throws IllegalArgumentException;

    /**
     * Deserialization method to deserialize each message to specific class
     * @param serialized string to deserialize
     * @return deserialized object
     * @throws IllegalArgumentException if the message is empty or blank
     */
    public abstract T deserialize(String serialized) throws IllegalArgumentException;

}