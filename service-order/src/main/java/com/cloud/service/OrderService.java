package com.cloud.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.dto.OrderDto;
import com.cloud.enums.StatusOrder;
import com.cloud.exceptions.ConvertDtoException;
import com.cloud.exceptions.CreateException;
import com.cloud.exceptions.GetException;
import com.cloud.exceptions.OrderInvalidatedException;
import com.cloud.exceptions.UpdateException;
import com.cloud.model.Order;
import com.cloud.model.Product;
import com.cloud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public Optional<Order> create(OrderDto orderDto) {
        try {
            boolean hasUser =  this.userService.hasUser(orderDto.getUserId());
            if(hasUser){
                
                List<Product> productOrderValidated = orderDto.getProducts().stream()
                .map(productOrder -> this.productHasQuantity(productOrder.getProductId(), productOrder.getQuantity()))
                .toList();

                if(productOrderValidated.size() == orderDto.getProducts().size()){
                    Order order = new Order();
                    order.setUserId(orderDto.getUserId());
                    order.setDateCreate(LocalDate.now());
                    order.setStatus(StatusOrder.PENDING);

                    List<Long> ids = productOrderValidated.stream()
                        .map(product -> product.getId())
                        .toList();

                    BigDecimal total = productOrderValidated.stream()
                        .map(Product :: getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                        
                    order.setTotal(total);
                    order.setProductsIds(ids);
                    Order orederAdded = this.orderRepository.save(order);

                    orderDto.getProducts().stream()
                        .forEach(p -> this.inventoryService.decreaseInventoryProductQuantity(p.getProductId(), p.getQuantity()));

                    this.notificationService.createNotification(order.getUserId());
                    return Optional.of(orederAdded);
                }else{
                    throw new OrderInvalidatedException("Bad order");
                }
            }else{
                throw new CreateException("User not found");
            }
        } catch (Exception error) {
            throw new CreateException("Error to create order -> " + error.getMessage());
        }
    }

    @Override
    public Optional<List<Order>> getOrdersByUserId(Long id) {
        try {
            Optional<List<Order>> ordersOp = this.orderRepository.findByUserId(id);
            return ordersOp;
        } catch (Exception error) {
            throw new GetException("Error to get order -> " + error.getMessage());
        }
    }

    @Override
    public Optional<List<Order>> all() {
        try {
            List<Order> orders = this.orderRepository.findAll();
            return Optional.of(orders);
        } catch (Exception error) {
            throw new GetException("Error to get order -> " + error.getMessage());
        }
    }

    @Override
    public Optional<Order> updateStatus(Long orderId, StatusOrder statusOrder) {
        try {
            Order order = this.orderRepository.findById(orderId)
                .orElseThrow(()-> new GetException("Order not found id " + orderId));

            //order.setStatus(statusOrder);
            Order orderUpdated = this.orderRepository.save(order);
            return Optional.of(orderUpdated);
        } catch (Exception error) {
            throw new UpdateException("Error to update order -> " + error.getMessage());
        }
    }

    @Override
    public Order convertDtoToOrder(OrderDto orderdto) {
        try {
            Order order = new Order();
            order.setUserId(orderdto.getUserId());
            return order;
        } catch (Exception error) {
            throw new ConvertDtoException("Error to convert dto to order -> " + error.getMessage());
        }
    }


    @Transactional
    public Product productHasQuantity(Long prodructId, int quantity){
        try {
            Product product = this.productService.getProduct(prodructId);
            if(Objects.nonNull(product) && this.inventoryService.hasProductQuantity(prodructId, quantity)){
                return product;
            }else{
                throw new GetException("Quantity not enough in inventory " + prodructId + " name " + product.getName() );
            }
        } catch (Exception error) {
            throw new GetException("Error to validate order -> " + error.getMessage());
        }
    }

}
