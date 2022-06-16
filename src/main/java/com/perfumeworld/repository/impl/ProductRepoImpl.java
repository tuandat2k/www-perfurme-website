package com.perfumeworld.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.perfumeworld.model.Product;
import com.perfumeworld.repository.ProductRepo;


@Repository
public class ProductRepoImpl extends AbstractRepoImpl<Integer, Product> implements ProductRepo {

    @Override
    public List<Product> getListSpKhuyenMai(int offset, int limit) {
        // TODO Auto-generated method stub
        Session session = sessionFactory.openSession();
        String hql = "FROM Product WHERE  discount > 0 ORDER BY updated_at DESC";
        Query query = session.createQuery(hql).setFirstResult(offset).setMaxResults(limit);
        return (List<Product>) query.list();
    }

    @Override
    public int getCountSpKhuyenMai() {
        // TODO Auto-generated method stub
        Session session = sessionFactory.openSession();
        String hql = "FROM Product WHERE  discount > 0";
        Query query = session.createQuery(hql);
        return ((List<Product>) query.list()).size();
    }
}
