package com.perfumeworld.repository.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.perfumeworld.model.ProductOrder;
import com.perfumeworld.repository.ProductOrderRepo;



@Repository
public class ProductOrderRepoImpl extends AbstractRepoImpl<Integer, ProductOrder> implements ProductOrderRepo {

    @Override
    public boolean deleteProductOrder(ProductOrder productOrder) {
        // TODO Auto-generated method stub
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();

            session.delete(productOrder);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            // TODO: handle exception
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            session.flush();
            session.close();
        }
        return false;
    }

}
