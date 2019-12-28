package com.main.jms.consumers;

import java.util.Hashtable;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSQueueConsumer {

  public static void main(String[] args) throws NamingException, JMSException {
    Hashtable<String, String> ht = new Hashtable<>();
    ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
    ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
    Context context = new InitialContext(ht);
    ConnectionFactory connFactory = (ConnectionFactory) context.lookup("jms/TestConnectionFactory");
    Queue queue = (Queue) context.lookup("jms/TestQueue");
    // JMSContext interface extends AutoCloseable feature. So, we can use with
    // try-with resources functionality
    System.out.println("JMSQueueConsumer before creating JMSContext");
    try (JMSContext jmsContext = connFactory.createContext(Session.AUTO_ACKNOWLEDGE);) {
      JMSConsumer consumer = jmsContext.createConsumer(queue);
      TextMessage msg = (TextMessage) consumer.receive();
      System.out.println("Message received from the producer :" + msg.getText());
    }
  }
}
