package com.main.jms.producers;

import java.util.Hashtable;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * In this JMS 2.0 version we are not handling the connection. This JMSContext
 * will take care of connection.start to send or receive the messages.
 * 
 * @author Surendar
 *
 */
public class JMSQueueProducer {

  public static void main(String[] args) throws NamingException {
    Hashtable<String, String> ht = new Hashtable<>();
    ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
    ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
    Context context = new InitialContext(ht);
    ConnectionFactory connFactory = (ConnectionFactory) context.lookup("jms/TestConnectionFactory");
    Queue queue = (Queue) context.lookup("jms/TestQueue");
    // In JMS 1.1 we will use Session and Connection. In JMS 2.0 we are using
    // JMSContext
    try (JMSContext jmsContext = connFactory.createContext();) {
      // In JMS 1.1 we will use MessageProducer in JMS 2.0 we are using JMSProducer
      JMSProducer producer = jmsContext.createProducer();
      // Now in JMS 2.0 we can able to send the messages with some delay using
      // setDeliveryDelay method. Below message will be delivered after 50 seconds.
      // Producer will put it in the JMS queue. The JMS provider will send the message
      // to the consumer after 50 seconds reached.
      producer.setDeliveryDelay(50000).send(queue, "Hello world !!");
    }
    System.out.println("Message successfully send to the JMSProducer");
  }
}
