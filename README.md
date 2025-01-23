# Using OpenAI ChatGPT 01-mini Model to develop an Apache Pulsar Function

This is an example of using "01-mini" to create an Apache Pulsar Function to process JSON messages.

**Note** This example assume a running Apache Pulsar 4.0.1+ is accessible and running.

The prompt for this app using 01-mini:
```console
Develop an Apache Pulsar Function to read/receive a JSON message from a topic, process the JSON message, 
write the JSON message and payload to a logfile, then send a new JSON message with "Message State":"Received" as the payload, 
along with the original payload.  The function should have robust error handling, log any invalid messages.  
If the JSON message is invalid, send the bad message to another Pulsar topic for bad data. 

You need to determine the proper programming language to use, follow Apache Pulsar Function development best practices
```

**Response from 01-mini**
01-mini determine the language to use is Java.  Below is a snippet of the response for the prompt.  01-mini included fully instructions on howto build the Function, and Pulsar Admin console to deploy the Function.

```console
To develop an Apache Pulsar Function that meets your requirements, we'll use Java as the programming language. Java is a robust, widely-supported language within the Apache Pulsar ecosystem, offering strong performance and extensive libraries for JSON processing, logging, and error handling. Additionally, Pulsar Functions in Java benefit from compile-time checks and seamless integration with Pulsar's APIs.

Below is a comprehensive guide to developing the desired Pulsar Function, including best practices, code implementation, and configuration details.

Table of Contents
Prerequisites
Project Structure
Dependencies
Function Implementation
Imports and Class Definition
Initialization
Processing Messages
Error Handling
Packaging the Function
Deploying the Function
Best Practices Followed
Conclusion
```