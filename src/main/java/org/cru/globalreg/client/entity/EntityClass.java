package org.cru.globalreg.client.entity;

/**
 * Created by ryancarlson on 2/25/14.
 */
public class EntityClass<T>
{
    final Class<T> type;

    public EntityClass(Class<T> type)
    {
        this.type = type;
    }

    public Class<T> getType()
    {
        return type;
    }

}
