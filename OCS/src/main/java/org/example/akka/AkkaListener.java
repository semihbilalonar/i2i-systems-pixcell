package org.example.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.example.requestMessage.AkkaRequestMessage;
import org.example.calculator.BalanceCalculations;
import org.example.requestMessage.MessageConverter;
import org.example.requestConfigurator.XmlConfigParser;

import java.io.File;

public class AkkaListener extends AbstractActor {

    private final BalanceCalculations calculator = new BalanceCalculations();

    public static Props props() {
        return Props.create(AkkaListener.class, AkkaListener::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    System.out.println("Received message: " + message);

                    AkkaRequestMessage requestMessage;
                    try {
                        requestMessage = MessageConverter.createMessageFromJSON(message);
                    } catch (Exception e) {
                        System.err.println("Failed to parse JSON message: " + e.getMessage());
                        return;
                    }
                    System.out.println("Request RECEIVED");

                    // Set price data from config XML
                    File xmlFile = new File("unit_service_prices.xml");
                    new XmlConfigParser().parseAndAssign(requestMessage, xmlFile);
                    System.out.println("Request PARSED");
                    System.out.println("Request UNIT PRICE calculated: " + requestMessage.getUnitPrice());

                    requestMessage.calculateTotalPrice();
                    System.out.println("Request TOTALPRICE calculated: " + requestMessage.getTotalUsagePrice());

                    switch (requestMessage.getType()) {
                        case "voice":
                            calculator.calculateVoiceRequest(requestMessage);
                            break;
                        case "sms":
                            calculator.calculateSMSRequest(requestMessage);
                            break;
                        case "data":
                            calculator.calculateDataRequest(requestMessage);
                            break;
                        default:
                            System.out.println("Invalid request type: " + requestMessage.getType());
                    }
                })
                .build();
    }
}
