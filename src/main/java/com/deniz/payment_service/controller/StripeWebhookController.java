package com.deniz.payment_service.controller;

import com.deniz.payment_service.repository.OrderRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookController {

    @Value("${stripe.webhookSecret}")
    private String webhookSecret;

    private final OrderRepository orderRepository;

    public StripeWebhookController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
                                                    @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, webhookSecret
            );
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook signature verification failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payload");
        }

        // Handle the event type
        switch (event.getType()) {
            case "checkout.session.completed":
                handleCheckoutSessionCompleted(event);
                break;
            // You can add more event types if needed
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("Received");
    }

    private void handleCheckoutSessionCompleted(Event event)
    {
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

        if (dataObjectDeserializer.getObject().isPresent()) {
            Session session = (Session) dataObjectDeserializer.getObject().get();

            // Find the order by Stripe session ID
            orderRepository.findByStripeSessionId(session.getId())
                    .ifPresent(order -> {
                        order.setStatus("PAID"); // or "SUCCESS"
                        orderRepository.save(order);
                        System.out.println("Order " + order.getId() + " marked as PAID.");
                    });
        }
    }

}
