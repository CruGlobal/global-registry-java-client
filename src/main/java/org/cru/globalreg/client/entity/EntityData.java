package org.cru.globalreg.client.entity;

/**
 * Created by ryancarlson on 2/25/14.
 */

public class EntityData<T> extends EntityClass<T>
{
    final T data;

    public EntityData(Class<T> type, T data)
    {
        super(type);
        this.data = data;
    }

    public T getData()
    {
        return data;
    }
}
