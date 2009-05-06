/* 
 * Copyright 2009 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fepss.rpc;

import java.io.IOException;
import java.util.Formatter;
import java.util.List;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.services.ServiceActivity;
import org.apache.tapestry5.ioc.services.ServiceActivityScoreboard;
import org.apache.tapestry5.ioc.services.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RPC Application
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcApplication {
	private static final Logger logger = LoggerFactory.getLogger(RpcApplication.class);
	private long startTime;

	private final RegistryBuilder builder = new RegistryBuilder();

	private Registry registry;

	private RpcServer server;
	private long endTime;
	public RpcApplication() {
		this(new Class<?>[0]);
	}
	 RpcApplication(Class<?> ...classes ){
        startTime = System.currentTimeMillis();
        builder.add(RpcCoreModule.class);
        
        builder.add(classes);
        
        registry = builder.build();
		server = registry.getService(RpcServer.class);
		endTime = System.currentTimeMillis();
	}
	public void start() throws IOException{
		server.start();
	}
	public void stop(){
		server.stop();
	}
	public void announceStartup()
    {
        long toFinish = System.currentTimeMillis();


        StringBuilder buffer = new StringBuilder("Startup status:\n\n");
        Formatter f = new Formatter(buffer);

        String appName="fepss rpc core";
		f.format("Application '%s' (Tapestry IOC version %s).\n\n" +
                "Startup time: %,d ms to build IoC Registry, %,d ms start rpc server.\n\n" +
                "Startup services status:\n",
                 appName,
                 "5.1.0.14",
                 endTime- startTime, toFinish - startTime);

        int unrealized = 0;

        ServiceActivityScoreboard scoreboard = registry
                .getService(ServiceActivityScoreboard.class);

        List<ServiceActivity> serviceActivity = scoreboard.getServiceActivity();

        int longest = 0;

        // One pass to find the longest name, and to count the unrealized services.

        for (ServiceActivity activity : serviceActivity)
        {
            Status status = activity.getStatus();

            longest = Math.max(longest, activity.getServiceId().length());

            if (status == Status.DEFINED || status == Status.VIRTUAL) unrealized++;
        }

        String formatString = "%" + longest + "s: %s\n";

        // A second pass to output all the services

        for (ServiceActivity activity : serviceActivity)
        {
            f.format(formatString, activity.getServiceId(), activity.getStatus().name());
        }

        f.format("\n%4.2f%% unrealized services (%d/%d)\n", 100. * unrealized / serviceActivity.size(), unrealized,
                 serviceActivity.size());

        logger.info(buffer.toString());
    }
	public final static void main(String[] args) throws IOException {
		RpcApplication app = new RpcApplication();
		app.start();
		app.announceStartup();
	}
}
