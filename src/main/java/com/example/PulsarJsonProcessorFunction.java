package com.example;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PulsarJsonProcessorFunction implements Function<String, String> {
    private static final Logger logger = LoggerFactory.getLogger(PulsarJsonProcessorFunction.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String badDataTopic;

    @Override
    public void initialize(Context context) throws Exception {
        // Retrieve bad data topic from function configuration
        badDataTopic = context.getUserConfigValue("badDataTopic").toString();
        logger.info("Initialized PulsarJsonProcessorFunction with badDataTopic: {}", badDataTopic);
    }

    @Override
    public String process(String input, Context context) {
        try {
            // Parse input JSON
            JsonNode jsonNode = objectMapper.readTree(input);

            // Log the received JSON message
            logger.info("Received message: {}", jsonNode.toString());

            // Optionally, process the JSON payload as needed
            // For demonstration, we'll just add "Message State": "Received"

            if (jsonNode instanceof ObjectNode) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.put("Message State", "Received");
                String output = objectMapper.writeValueAsString(objectNode);

                // Log the processed message
                logger.info("Processed message: {}", output);

                return output;
            } else {
                throw new IllegalArgumentException("JSON message is not an object");
            }
        } catch (Exception e) {
            // Log the error with the problematic message
            logger.error("Failed to process message: {}. Error: {}", input, e.getMessage());

            // Attempt to send the bad message to the badDataTopic
            try {
                context.newOutputMessage(badDataTopic, org.apache.pulsar.client.api.Schema.STRING)
                        .value(input)
                        .send();
                logger.info("Sent bad message to {}", badDataTopic);
            } catch (Exception ex) {
                logger.error("Failed to send bad message to {}: {}", badDataTopic, ex.getMessage());
            }

            // Optionally, you can return null or a specific message
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        // Clean up resources if needed
        logger.info("Closing PulsarJsonProcessorFunction");
    }
}
