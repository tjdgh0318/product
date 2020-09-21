package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.persistence.PostUpdate;
import java.util.Optional;


@Service
public class PolicyHandler {


    @Autowired
    ProductRepository productRepository;

    @StreamListener(Processor.INPUT)
    public void onEventByString(@Payload OrderPlaced orderPlaced){
        //orderPlaced 데이터를 json --> 객체로 파싱 --> 해결
        
        
        // if(주문시 생성되었을 때만)
        if("OrderPlaced".equals(orderPlaced.getEventType())){
            System.out.println("onEventByObject getEventType = " + orderPlaced.getEventType());
            System.out.println("onEventByObject getProductName = " + orderPlaced.getProductName());

            // 상품저장


            //Product p = new Product();
            //p.setId(orderPlaced.getProductId());
            //p.setStock(orderPlaced.getQty());

            //productRepository.save(p);

            // 상품 ID 값의 재고 변경
            // if()



            Optional<Product> productById  = productRepository.findById((orderPlaced.getProductId()));
            if(productById.isEmpty()){

            }
            Product p = productById.get();
            System.out.println("p.getStock() = " +  p.getStock());
            System.out.println("orderPlaced.getQty() = " + orderPlaced.getQty());
            p.setStock(p.getStock()-orderPlaced.getQty());
            productRepository.save(p);


        }

    }
}
