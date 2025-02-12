package org.serialize;

import org.util.Pair;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class PairSerializer extends Serializer{
    private Pair p;
    private String serializedMessage;

    @Override
    public String serialize(Object object) throws IllegalArgumentException {
        if(!(object instanceof Pair)){
            throw new IllegalArgumentException();
        }
        this.p =(Pair) object;
        this.serializedMessage=p.toString();
        return this.serializedMessage;
    }

    @Override
    public Object deserialize(String serialized) throws IllegalArgumentException {
        if(serialized.isBlank()||serialized.isEmpty())
            throw new IllegalArgumentException();

        StringTokenizer tokenizer = new StringTokenizer(serialized,":");
        if(tokenizer.countTokens()!=2)
            throw new IllegalArgumentException();

        int x= Integer.parseInt(tokenizer.nextToken());
        int y= Integer.parseInt(tokenizer.nextToken());
        this.serializedMessage=serialized;
        this.p= new Pair(x,y);
        return p;
    }

    public String bitSerialize(Object object) throws IllegalArgumentException{
        if(!(object instanceof Pair)){
            throw new IllegalArgumentException();
        }
        this.p =(Pair) object;
        this.serializedMessage="";
        this.serializedMessage+=(char)p.getX();
        this.serializedMessage+=(char)p.getY();
        return this.serializedMessage;
    }

    public Pair bitDeserializer(String serialized) throws IllegalArgumentException{
        if(serialized.length()!=2)
            throw new IllegalArgumentException();

        int x= (int)serialized.charAt(0);
        int y=(int)serialized.charAt(1);
        this.p= new Pair(x,y);
        return p;
    }

}
