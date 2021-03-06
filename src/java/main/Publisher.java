/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.naming.InitialContext;                                                                          
import javax.jms.Topic;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.jms.DeliveryMode;
import javax.jms.TopicSession;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import Entities.ProductInstance;
import java.util.List;
                                                                           
                                         
public class Publisher
{
    public void activateTopic(List<ProductInstance> temp) throws Exception
    {
        
           // get the initial context
       InitialContext ctx = new InitialContext();
                                                                           
       // lookup the topic object
       Topic topic = (Topic) ctx.lookup("jms/MyTopic");
                                                                           
       // lookup the topic connection factory
       TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.
           lookup("topic/connectionFactory");
                                     
       
       // create a topic connection
       TopicConnection topicConn = connFactory.createTopicConnection();
                                                                           
       // create a topic session
       TopicSession topicSession = topicConn.createTopicSession(false,
           Session.AUTO_ACKNOWLEDGE);
                                                                           
       // create a topic publisher
       TopicPublisher topicPublisher = topicSession.createPublisher(topic);
       topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                                                                           
       TextMessage message = topicSession.createTextMessage();
       message.setText(temp.get(temp.size()-1).getProductName() +"/" + temp.get(temp.size()-1).getBuyer().getUsername() + "/" + temp.get(temp.size()-1).getId());                                                                      
       topicPublisher.publish(message); 
      
       // close the topic connection
       topicConn.close();
  
    }
}