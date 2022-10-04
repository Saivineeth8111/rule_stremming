//package com.qualcomm.rule.service;
//
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletionStage;
//
//import org.junit.Ignore;
//import org.junit.Rule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import com.qualcomm.common.dto.MapperService;
//import com.qualcomm.common.service.DeviceManagementApiInfo;
//import com.qualcomm.stream.TopicPublishResult;
//import com.qualcomm.stream.TopicPublisherClient;
//
//
//@RunWith(MockitoJUnitRunner.Silent.class)
//class RuleEngineProcessorServiceTest {
//	
//	@InjectMocks
//	RuleEngineProcessorService trackerMessageProcessor;
//
//	@Mock
//	RuleEngineProcessorService trackerMessageProcessors;
//
//	@Mock
//	MapperService mapperService;
//
//	@Mock
//	TopicPublisherClient topicPublisherClient;
//
//	@Mock
//	CompletionStage<TopicPublishResult> publishResultFuture;
//
//	@Mock
//	DeviceManagementApiInfo deviceManagementApiInfo;
//	
//	@Mock
//	CompletionStage completionStage;
//
//	@SuppressWarnings("deprecation")
//
//	@Rule
//	ExpectedException expectedException = ExpectedException.none();
//
//	@SuppressWarnings("deprecation")
//
//	@BeforeEach
//	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
//		ReflectionTestUtils.setField(trackerMessageProcessor, "mapperService", new MapperService());
//	}
//	
//	@Test
//	@Ignore("Timezone issues")
//	void testProcessMessage() throws IOException{
//		
//		List<String> message = new ArrayList<String>();
//		message.add("New Message");
//		
//
//		Mockito.when(topicPublisherClient.publish(Mockito.any(), Mockito.any(),
//				 Mockito.any())).thenReturn(completionStage);
//		
//		trackerMessageProcessor.processMessage(message);
//
//		assertNotNull(message);
//	}
//	
////	 @Test 
////	  @Ignore("Timezone issues") void processMessageTestForExpection() throws
////	  IOException {
////	  
////			
////			List<String> message = new ArrayList<String>();
////			message.add("New Message");
////	  
////	  expectedException.expect(Exception.class);
////	  
////	  trackerMessageProcessor.processMessage(message);
////	  assertNotNull(message);
////	  
////	  }
////
////		@Test
////		@Ignore("Timezone issues")
////		void processMessageTestForJsonExcpection() throws IOException {
////			List<String> message = new ArrayList<String>();
////			message.add("New Message");
////	  
////
////			trackerMessageProcessor.processMessage(message);
////
////			assertNotNull(message);
////		}
////		
////		  @Test
////		  
////		  @Ignore("Timezone issues") void processMessageTestForNull() throws
////		  IOException {
////		  
////				
////				List<String> message = new ArrayList<String>();
////				message.add("New Message");
////		  
////			
////		  expectedException.expect(Exception.class);
////		  
////		  trackerMessageProcessor.processMessage(message);
////		  
////		  }
//
//}
