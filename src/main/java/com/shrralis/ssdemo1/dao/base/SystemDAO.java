package com.shrralis.ssdemo1.dao.base;

import com.shrralis.ssdemo1.configuration.AppConfig;
import com.shrralis.tools.model.SystemDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;

public class SystemDAO {
    private static final Logger logger = LoggerFactory.getLogger(SystemDAO.class);

    protected HibernateTemplate hibernateTemplate;

    //    @Autowired
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    protected void throwableDelete(Object entity) throws SystemDAOException {
        try {
            hibernateTemplate.delete(entity);
        } catch (Exception e) {
            if (AppConfig.DEBUG) {
                logger.error("Exception when `throwableDelete()`!", e);
            }
            throw new SystemDAOException(SystemDAOException.Exception.MYSQL);
        }
    }

    protected void throwableInsert(Object entity) throws SystemDAOException {
        try {
            hibernateTemplate.save(entity);
        } catch (Exception e) {
            if (AppConfig.DEBUG) {
                logger.error("Exception when `throwableInsert()`!", e);
            }
            throw new SystemDAOException(SystemDAOException.Exception.MYSQL);
        }
    }

    protected void throwableUpdate(Object entity) throws SystemDAOException {
        try {
            hibernateTemplate.update(entity);
        } catch (Exception e) {
            if (AppConfig.DEBUG) {
                logger.error("Exception when `throwableUpdate()`!", e);
            }
            throw new SystemDAOException(SystemDAOException.Exception.MYSQL);
        }
    }
}
