package com.tx.schoolmanagement.module.common.service;

import java.util.Date;

public interface Model<K> {

    K getId();

    void setId(K id);

    Date getCreatedDate();

    void setCreatedDate(Date createdDate);

    Date getModifiedDate();

    void setModifiedDate(Date modifiedDate);
}
