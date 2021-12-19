/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.usa.ciclo.Service;

import co.usa.ciclo.Modelo.Order;
import co.usa.ciclo.Repository.crud.OrderRepository;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.REUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;

/**
 *
 * @author roll-
 */
@Service
public class OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private OrderRepository orderRepo;

    public List<Order> getAll() {

        return orderRepo.getAll();

    }

    public Order save(Order order) {
        Optional<Order> orderExist = orderRepo.getOrderById(order.getId());

        if (!orderExist.isPresent()) {

            return orderRepo.save(order);
        } else {
            return order;
        }
    }

    public Order update(Order order) {

        if (order.getId() == null) {

            return order;

        } else {
            Optional<Order> orderExist = orderRepo.getOrderById(order.getId());
            if (orderExist.isPresent()) {
                if (order.getStatus() != null) {

                    orderExist.get().setStatus(order.getStatus());

                }
                
                return orderRepo.save(orderExist.get());
            } else {

                return order;

            }

        }

    }

    public Integer deleteOrder(Integer id) {
        Optional<Order> orderExist = orderRepo.getOrderById(id);

        if (orderExist.isPresent()) {

            orderRepo.deleteOrder(id);
            return null;
        } else {
            return id;
        }
    }

    public Order getById(Integer id) {

        Optional<Order> orderExist = orderRepo.getOrderById(id);
        if (orderExist.isPresent()) {

            return orderExist.get();
        } else {

            return new Order();
        }
    }

    public List<Order> getZone(String country) {

        return orderRepo.getZone(country);

    }

    public List<Order> getStatus(String zone) {

        return orderRepo.getStatus(zone);

    }

    public List<Order> findBySalesManId(Integer id) {

        return orderRepo.findBySalesManId(id);

    }

    public List<Order> getStatusById(String status, Integer id) {

        return orderRepo.getStatusById(status, id);

    }

    public List<Order> getRegisterDay(String dateStr, Integer id) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Query query = new Query();

        Criteria dateCriteria = Criteria.where("registerDay")
                .gte(LocalDate.parse(dateStr, dtf).minusDays(1).atStartOfDay())
                .lt(LocalDate.parse(dateStr, dtf).plusDays(1).atStartOfDay())
                .and("salesMan.id").is(id);
        query.addCriteria(dateCriteria);

        List<Order> orders = mongoTemplate.find(query, Order.class);
        System.out.println(orders);
        return orders;

    }
    
    
}


